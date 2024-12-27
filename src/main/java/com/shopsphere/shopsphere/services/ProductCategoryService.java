package com.shopsphere.shopsphere.services;

import com.shopsphere.shopsphere.models.ProductCategory;
import com.shopsphere.shopsphere.repositories.ProductCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public List<ProductCategory> getProductCategories() {
        return productCategoryRepository.findAll();
    }

    public ProductCategory getProductCategoryById (int categoryId) {

        return productCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Catégorie introuvable"));
    }

    public ProductCategory createProductCategory(ProductCategory category) {
        return productCategoryRepository.save(category);
    }

    public ProductCategory updateProductCategory(ProductCategory category, int categoryId) {

        ProductCategory productCategoryToUpdate = productCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Catéorie introuvable"));

        productCategoryToUpdate.setName(category.getName());
        productCategoryToUpdate.setDescription(category.getDescription());

        return productCategoryRepository.save(productCategoryToUpdate);
    }

    public void deleteProductCategory(int categoryId) {

        ProductCategory productToDelete =  productCategoryRepository.findById(categoryId)
                        .orElseThrow(() -> new EntityNotFoundException("Catégorie Introuvable"));


        productCategoryRepository.deleteById(productToDelete.getId());
    }
}
