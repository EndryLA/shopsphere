package com.shopsphere.shopsphere.repositories;

import com.shopsphere.shopsphere.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}
