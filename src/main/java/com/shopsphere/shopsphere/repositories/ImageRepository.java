package com.shopsphere.shopsphere.repositories;

import com.shopsphere.shopsphere.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    public List<Image> findByProductId(int productId);

}
