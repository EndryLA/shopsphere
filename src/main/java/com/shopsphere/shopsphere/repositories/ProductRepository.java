package com.shopsphere.shopsphere.repositories;

import com.shopsphere.shopsphere.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    public List<Product> findAllByCategoryId(int categoryId);

    Page<Product> findAll(Pageable pageable);

}
