package com.shopsphere.shopsphere.controllers;

import com.shopsphere.shopsphere.models.Image;
import com.shopsphere.shopsphere.services.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("")
    public ResponseEntity<Image> createImage(@RequestBody Image image) {
        System.out.println(image);

        return new ResponseEntity<Image>(imageService.createImage(image), HttpStatus.CREATED);
    }

}
