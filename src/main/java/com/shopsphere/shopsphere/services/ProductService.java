package com.shopsphere.shopsphere.services;

import com.shopsphere.shopsphere.models.Image;
import com.shopsphere.shopsphere.models.Product;
import com.shopsphere.shopsphere.models.ProductInventory;
import com.shopsphere.shopsphere.repositories.ImageRepository;
import com.shopsphere.shopsphere.repositories.ProductInventoryRepository;
import com.shopsphere.shopsphere.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final ProductInventoryRepository productInventoryRepository;

    public ProductService(ProductRepository productRepository, ImageRepository imageRepository, ProductInventoryRepository productInventoryRepository) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
        this.productInventoryRepository = productInventoryRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(int productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Le produit demandé est introuvable"));
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        Product productToUpdate = productRepository.findById(product.getId())
                .orElseThrow(() -> new EntityNotFoundException("Le produit demandé est introuvable"));

        productToUpdate.setName(product.getName());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setCategory(product.getCategory());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setQuantity(product.getQuantity());

        return productRepository.save(productToUpdate);
    }

    public void deleteProduct(int productId) {
        // Fetch the product or throw an exception if not found
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Produit introuvable"));

        // Handle associated images
        List<Image> images = imageRepository.findByProductId(productId);
        for (Image image : images) {
            imageRepository.delete(image); // Delete each associated image
        }

        // Handle associated inventory
        if (product.getQuantity() != null) {
            productInventoryRepository.findById(product.getQuantity().getId())
                    .ifPresent(productInventoryRepository::delete); // Delete inventory if it exists
        }

        // Delete the product
        productRepository.deleteById(productId);
    }


}
