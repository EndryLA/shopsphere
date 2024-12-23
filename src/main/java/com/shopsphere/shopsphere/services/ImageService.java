package com.shopsphere.shopsphere.services;

import com.shopsphere.shopsphere.models.Image;
import com.shopsphere.shopsphere.repositories.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image getImageById(int imageId) {
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException("L'image demandée est introuvable"));
    }

    public List<Image> getImagesByProductId(int productId) {
        return imageRepository.findByProductId(productId);
    }

    public Image createImage(Image image) {
        return imageRepository.save(image);
    }

    public Image updateImage(Image image) {
        Image productToUpdate = imageRepository.findById(image.getId())
                .orElseThrow(() -> new EntityNotFoundException("L'image demandée est introuvable"));

        productToUpdate.setImage(image.getImage());
        productToUpdate.setMainImage(image.getMainImage());
        productToUpdate.setProduct(image.getProduct());

        return imageRepository.save(productToUpdate);
    }

    public void deleteImage(int imageId) {
        imageRepository.deleteById(imageId);
    }
}
