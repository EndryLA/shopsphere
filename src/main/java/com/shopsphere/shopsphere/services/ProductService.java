package com.shopsphere.shopsphere.services;

import com.shopsphere.shopsphere.models.Product;
import com.shopsphere.shopsphere.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
        productRepository.deleteById(productId);
    }

}
