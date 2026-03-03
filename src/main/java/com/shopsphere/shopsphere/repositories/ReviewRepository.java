package com.shopsphere.shopsphere.repositories;

import com.shopsphere.shopsphere.domain.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findByProductId(int productId);

}
