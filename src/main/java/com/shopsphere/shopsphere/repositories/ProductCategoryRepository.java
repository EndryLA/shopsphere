package com.shopsphere.shopsphere.repositories;

import com.shopsphere.shopsphere.models.ProductCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {


}
