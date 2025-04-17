package com.shopsphere.shopsphere.controllers;

import com.shopsphere.shopsphere.models.ProductCategory;
import com.shopsphere.shopsphere.models.ProductInventory;
import com.shopsphere.shopsphere.services.ProductCategoryService;
import com.shopsphere.shopsphere.services.ProductInventoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class ProductInventoryController {

    private final ProductInventoryService productInventoryService;

    public ProductInventoryController(ProductInventoryService productInventoryService) {
        this.productInventoryService = productInventoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductInventory> getProductInventory(@PathVariable int id) {
        try {
            ProductInventory productInventory = productInventoryService.getProductInventoryById(id);
            return new ResponseEntity<>(productInventory,HttpStatus.OK);
        } catch (EntityNotFoundException e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);

        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur inattendue est survenue",e);

        }
    }

    @PostMapping("")
    public ResponseEntity<ProductInventory> createProductInventory(@RequestBody ProductInventory productInventory) {

        return new ResponseEntity<>(productInventoryService.createProductInventory(productInventory), HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductInventory> updateProductInventory(
            @RequestBody ProductInventory productInventory,
            @PathVariable int id
    ) {
        try {

            return new ResponseEntity<>(productInventoryService.updateProductInventory(productInventory, id), HttpStatus.CREATED);

        } catch (EntityNotFoundException exception) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND,exception.getMessage(),exception);

        } catch (Exception exception) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur inattendue est survenue",exception);

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductInventory(@PathVariable int id) {
        try  {

            productInventoryService.deleteProductInventory(id);
            return ResponseEntity.noContent().build();

        } catch (EntityNotFoundException exception) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND,exception.getMessage(),exception);

        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Une erreur inattendue est survenue",exception);
        }
    }

}
