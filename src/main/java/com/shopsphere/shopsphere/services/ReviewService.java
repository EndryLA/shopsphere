package com.shopsphere.shopsphere.services;

import com.shopsphere.shopsphere.models.Product;
import com.shopsphere.shopsphere.models.Review;
import com.shopsphere.shopsphere.repositories.ProductRepository;
import com.shopsphere.shopsphere.repositories.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    public ReviewService(ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }


    public List<Review> getReviewsByProductId(int id) {
        return reviewRepository.findByProductId(id);
    }

    public Review getReviewsById(int id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aucun commentaire"));
    }

    public Review createReview(Review review) {

        int productId = review.getProduct().getId();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        review.setProduct(product);

        Review savedReview = reviewRepository.save(review);

        double averageRating = calculateAverageRating(productId);
        product.setAverageRating(averageRating);

        productRepository.save(product);

        return review;
    }


    //

    private double calculateAverageRating(int productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        if (reviews.isEmpty()) {
            return 0.0;
        } else {
            double total = 0;
            for (Review review : reviews) {
                total += review.getRating();
            }

            return total / reviews.size();
        }
    }

}
