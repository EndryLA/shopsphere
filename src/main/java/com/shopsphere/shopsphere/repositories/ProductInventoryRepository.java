package com.shopsphere.shopsphere.repositories;

import com.shopsphere.shopsphere.models.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInventoryRepository extends JpaRepository<ProductInventory, Integer> {
}
