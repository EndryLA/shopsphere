package com.shopsphere.shopsphere.controllers;

import com.shopsphere.shopsphere.models.Product;
import com.shopsphere.shopsphere.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("")
    public ResponseEntity<List<Product>> getProducts() {

        List<Product> products = productService.getProducts();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        try {
            Product product = productService.getProductById(id);
            return new ResponseEntity<>(product,HttpStatus.OK);

        } catch (EntityNotFoundException exception) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND,exception.getMessage(),exception);

        } catch (Exception exception) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Une erreur inattendue est survenue",exception);

        }
    }

    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            Product savedProduct = productService.createProduct(product);
            return new ResponseEntity<>(savedProduct,HttpStatus.CREATED);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur inattendue est survenue",exception);
        }
    }

}
