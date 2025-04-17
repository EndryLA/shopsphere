package com.shopsphere.shopsphere.controllers;

import com.shopsphere.shopsphere.models.Image;
import com.shopsphere.shopsphere.services.ImageService;
import com.shopsphere.shopsphere.services.ProductService;
import com.shopsphere.shopsphere.utils.ImageUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/")
public class ImageController {



    private final ImageService imageService;
    private final ImageUtils imageUtils;
    private final ProductService productService;

    public ImageController(ImageService imageService, ImageUtils imageUtils, ProductService productService) {
        this.imageService = imageService;
        this.imageUtils = imageUtils;
        this.productService = productService;
    }

    @GetMapping("public/images/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable int id) {
        try {
            Image image = imageService.getImageById(id);

            if (image.getFile() == null) {
                return ResponseEntity.notFound().build();
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            byte[] imageData = imageUtils.convertToPrimitiveBytes(image.getFile());
            headers.setContentLength(imageData.length);

            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("public/images/product/{productId}")
    public List<Image> getProductImages(@PathVariable int productId) {
        return imageService.getImagesByProductId(productId);
    }

    @GetMapping("public/images/product/{productId}/main")
    public ResponseEntity<byte[]> getMainImage(@PathVariable int productId) {
        try {
            Image image = imageService.getMainImageByProductId(productId);

            if (image.getFile() == null) {
                return ResponseEntity.notFound().build();
            }

            byte[] imageData = imageUtils.convertToPrimitiveBytes(image.getFile());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(imageData.length);

            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("images")
    public ResponseEntity<String> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("productId") int productId,
            @RequestParam(value = "isMainImage", defaultValue = "false") boolean isMainImage) {

        try {
            imageUtils.validateImage(file);

            Image imageEntity = new Image();
            imageEntity.setFile(imageUtils.convertToByteObjects(file.getBytes()));
            imageEntity.setMainImage(isMainImage);
            // Set product using ProductService
            imageEntity.setProduct(productService.getProductById(productId));

            imageService.createImage(imageEntity);

            return ResponseEntity.ok("Image téléchargée avec succès");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Échec du traitement de l'image: " + e.getMessage());
        }
    }

    @PutMapping("images/{id}")
    public ResponseEntity<String> updateImage(
            @PathVariable int id,
            @RequestParam(required = false) MultipartFile image,
            @RequestParam(required = false) Boolean main_image) {

        try {
            Image existingImage = imageService.getImageById(id);

            if (image != null) {
                imageUtils.validateImage(image);
                existingImage.setFile(imageUtils.convertToByteObjects(image.getBytes()));
            }

            if (main_image != null) {
                existingImage.setMainImage(main_image);
            }

            imageService.updateImage(existingImage);

            return ResponseEntity.ok("Image mise à jour avec succès");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Échec de la mise à jour de l'image: " + e.getMessage());
        }
    }


    @DeleteMapping("images/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable int id) {
        try {
            imageService.deleteImage(id);
            return ResponseEntity.ok("Image supprimée avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Échec de la suppression de l'image: " + e.getMessage());
        }
    }
}