package com.shopsphere.shopsphere.api;

import com.shopsphere.shopsphere.domain.models.Product;
import com.shopsphere.shopsphere.domain.models.Review;
import com.shopsphere.shopsphere.domain.services.ProductService;
import com.shopsphere.shopsphere.domain.services.ReviewService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ProductController {

    private final ProductService productService;
    private final ReviewService reviewService;

    public ProductController(ProductService productService, ReviewService reviewService) {
        this.productService = productService;
        this.reviewService = reviewService;
    }


    @GetMapping("public/products/unpaged")
    public ResponseEntity<List<Product>> getProducts() {

        List<Product> products = productService.getProducts();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("public/products")
    public ResponseEntity<Page<Product>> getProductsPaged(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size) {

        try {
            Page<Product> products = productService.getPagedProducts(page, size);

            return new ResponseEntity<>(products,HttpStatus.OK);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Une erreur inattendue est survenue");
        }

    }

    @GetMapping("public/products/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategoryId(@PathVariable int categoryId){
        List<Product> products = productService.getProductsByCategoryId(categoryId);

        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @GetMapping("public/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        try {
            Product product = productService.getProductById(id);
            return new ResponseEntity<>(product,HttpStatus.OK);

        } catch (EntityNotFoundException exception) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND,exception.getMessage(),exception);

        } catch (Exception exception) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Une erreur inattendue est survenue",exception);

        }
    }

    @GetMapping("public/products/reviews/{productId}")
    public ResponseEntity<List<Review>> getProductReviews(@PathVariable int productId) {

        List<Review> reviews =  reviewService.getReviewsByProductId(productId);

        return new ResponseEntity<>(reviews,HttpStatus.OK);
    }

    @PostMapping("products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {

            System.out.println(product.getInventory());

            Product savedProduct = productService.createProduct(product);
            return new ResponseEntity<>(savedProduct,HttpStatus.CREATED);

        } catch (Exception exception) {
            System.out.println(product);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur inattendue est survenue",exception);
        }
    }

    @PutMapping("products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product product) {

        try {
            Product productToUpdate = productService.updateProduct(product,id);

            return new ResponseEntity<>(productToUpdate,HttpStatus.OK);

        } catch (EntityNotFoundException e) {

            throw new  ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);

        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur inattendue est survenue",e);

        }
    }

    @DeleteMapping("products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);

        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur inattendue est survenue",e);

        }

    }


}
