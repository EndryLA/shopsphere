package com.shopsphere.shopsphere.services;

import com.shopsphere.shopsphere.models.ProductInventory;
import com.shopsphere.shopsphere.repositories.ProductInventoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class ProductInventoryService {

    private final ProductInventoryRepository productInventoryRepository;

    public ProductInventoryService(ProductInventoryRepository productInventoryRepository) {
        this.productInventoryRepository = productInventoryRepository;
    }

    public ProductInventory getProductInventoryById(int id) {
        return productInventoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventaire indisponible"));
    }
}
