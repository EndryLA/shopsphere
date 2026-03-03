package com.shopsphere.shopsphere.repositories;

import com.shopsphere.shopsphere.domain.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {


}
