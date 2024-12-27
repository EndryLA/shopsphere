package com.shopsphere.shopsphere.controllers;

import com.shopsphere.shopsphere.models.Product;
import com.shopsphere.shopsphere.models.ProductCategory;
import com.shopsphere.shopsphere.services.ProductCategoryService;
import com.shopsphere.shopsphere.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("")
    public ResponseEntity<List<ProductCategory>> getCategories() {

        List<ProductCategory> categories = productCategoryService.getProductCategories();

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ProductCategory> createCategory(@RequestBody ProductCategory productCategory) {

        return new ResponseEntity<>(productCategoryService.createProductCategory(productCategory), HttpStatus.CREATED);

    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ProductCategory> updateCategory(
            @RequestBody ProductCategory productCategory,
            @PathVariable int categoryId
    ) {
        try {

            return new ResponseEntity<>(productCategoryService.updateProductCategory(productCategory, categoryId), HttpStatus.CREATED);

        } catch (EntityNotFoundException exception) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND,exception.getMessage(),exception);

        } catch (Exception exception) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur inattendue est survenue",exception);

        }
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int categoryId) {
        try  {

         productCategoryService.deleteProductCategory(categoryId);
         return ResponseEntity.noContent().build();

        } catch (EntityNotFoundException exception) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND,exception.getMessage(),exception);

        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Une erreur inattendue est survenue",exception);
        }
    }

}

