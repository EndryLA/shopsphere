package com.shopsphere.shopsphere.services;

import com.shopsphere.shopsphere.models.Image;
import com.shopsphere.shopsphere.models.Product;
import com.shopsphere.shopsphere.models.ProductInventory;
import com.shopsphere.shopsphere.repositories.ImageRepository;
import com.shopsphere.shopsphere.repositories.ProductInventoryRepository;
import com.shopsphere.shopsphere.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final ProductInventoryRepository productInventoryRepository;

    public ProductService(
            ProductRepository productRepository,
            ImageRepository imageRepository,
            ProductInventoryRepository productInventoryRepository) {

        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
        this.productInventoryRepository = productInventoryRepository;
    }



    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Page<Product> getPagedProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    public Product getProductById(int productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Le produit demandé est introuvable"));
    }

    public List<Product> getProductsByCategoryId(int categoryId) {
        return productRepository.findAllByCategoryId(categoryId);
    }

    public Product createProduct(Product product) {

        return productRepository.save(product);
    }


    public Product updateProduct(Product product, int id) {
        Product productToUpdate = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Le produit demandé est introuvable"));

        productToUpdate.setName(product.getName());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setCategory(product.getCategory());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setInventory(product.getInventory());

        return productRepository.save(productToUpdate);
    }

    public void deleteProduct(int productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Le produit demandé est introuvable"));

        List<Image> images = imageRepository.findByProductId(productId);
        if (!images.isEmpty()) {
            imageRepository.deleteAll(images);
        }

        if (product.getInventory() != null) {
            productInventoryRepository.findById(product.getInventory().getId())
                    .ifPresent(productInventoryRepository::delete);
        }

        productRepository.delete(product);
    }


}
