package com.shopsphere.shopsphere.controllers;

import com.shopsphere.shopsphere.models.Image;
import com.shopsphere.shopsphere.services.ImageService;
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
@RequestMapping("/api/images")
public class ImageController {

    // Maximum image size (16MB)
    private static final long MAX_IMAGE_SIZE = 16 * 1024 * 1024;

    // Allowed image types
    private static final Set<String> ALLOWED_CONTENT_TYPES = new HashSet<>(Arrays.asList(
            "image/jpeg",
            "image/png",
            "image/webp",
            "image/jpg"
    ));

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    /**
     * Validates an uploaded image
     * @param image The image to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateImage(MultipartFile image) {
        // Check if image is empty
        if (image.isEmpty()) {
            throw new IllegalArgumentException("L'image ne peut pas être vide");
        }

        // Check image size
        if (image.getSize() > MAX_IMAGE_SIZE) {
            throw new IllegalArgumentException("La taille de l'image dépasse la limite maximale de 5MB");
        }

        // Check image type
        String contentType = image.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType.toLowerCase())) {
            throw new IllegalArgumentException("Type d'image invalide. Types autorisés: JPEG, PNG, GIF");
        }
    }

    /**
     * Converts primitive byte array to Byte object array
     */
    private Byte[] convertToByteObjects(byte[] bytes) {
        Byte[] byteObjects = new Byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            byteObjects[i] = bytes[i];
        }
        return byteObjects;
    }

    /**
     * Converts Byte object array to primitive byte array
     */
    private byte[] convertToPrimitiveBytes(Byte[] bytes) {
        byte[] primitiveBytes = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            primitiveBytes[i] = bytes[i];
        }
        return primitiveBytes;
    }

    /**
     * Upload a new image
     * @param image The image file
     * @param product_id The product ID
     * @param main_image Whether this is the main product image
     * @return Response indicating success or failure
     */
    @PostMapping("")
    public ResponseEntity<String> uploadImage(
            @RequestParam("image") MultipartFile image,
            @RequestParam("product_id") int product_id,
            @RequestParam(value = "main_image", defaultValue = "false") boolean main_image) {

        try {
            validateImage(image);

            Image imageEntity = new Image();
            imageEntity.setImage(convertToByteObjects(image.getBytes()));
            imageEntity.setMainImage(main_image);
            // Set product using ProductService
            // imageEntity.setProduct(productService.getProductById(product_id));

            imageService.createImage(imageEntity);

            return ResponseEntity.ok("Image téléchargée avec succès");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Échec du traitement de l'image: " + e.getMessage());
        }
    }

    /**
     * Get an image by its ID
     * @param id The image ID
     * @return The image data with appropriate headers
     */
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable int id) {
        try {
            Image image = imageService.getImageById(id);

            if (image.getImage() == null) {
                return ResponseEntity.notFound().build();
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            byte[] imageData = convertToPrimitiveBytes(image.getImage());
            headers.setContentLength(imageData.length);

            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get all images for a product
     * @param product_id The product ID
     * @return List of images
     */
    @GetMapping("/product/{product_id}")
    public List<Image> getProductImages(@PathVariable int product_id) {
        return imageService.getImagesByProductId(product_id);
    }

    /**
     * Update an existing image
     * @param id The image ID
     * @param image The new image file (optional)
     * @param main_image The new main image status (optional)
     * @return Response indicating success or failure
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateImage(
            @PathVariable int id,
            @RequestParam(required = false) MultipartFile image,
            @RequestParam(required = false) Boolean main_image) {

        try {
            Image existingImage = imageService.getImageById(id);

            if (image != null) {
                validateImage(image);
                existingImage.setImage(convertToByteObjects(image.getBytes()));
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

    @GetMapping("/product/{productId}/main")
    public ResponseEntity<byte[]> getMainImage(@PathVariable int productId) {
        try {
            Image image = imageService.getMainImageByProductId(productId);

            if (image.getImage() == null) {
                return ResponseEntity.notFound().build();
            }

            byte[] imageData = convertToPrimitiveBytes(image.getImage());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(imageData.length);

            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete an image
     * @param id The image ID
     * @return Response indicating success or failure
     */
    @DeleteMapping("/{id}")
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