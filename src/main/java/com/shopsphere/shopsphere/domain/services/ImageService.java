package com.shopsphere.shopsphere.domain.services;

import com.shopsphere.shopsphere.domain.models.Image;
import com.shopsphere.shopsphere.repositories.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

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
        List<Image> images = imageRepository.findByProductId(productId);

        for (Image image : images) {
            image.setFile(null);
        }

        return images;
    }

    public Image getMainImageByProductId(int productId) {
        List<Image> images = imageRepository.findByProductId(productId);
        return images.stream()
                .filter(image -> Boolean.TRUE.equals(image.getMainImage()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("L'image principale pour ce produit est introuvable"));
    }


    public Image createImage(Image image) {
        return imageRepository.save(image);
    }

    public Image updateImage(Image image) {
        Image productToUpdate = imageRepository.findById(image.getId())
                .orElseThrow(() -> new EntityNotFoundException("L'image demandée est introuvable"));

        productToUpdate.setFile(image.getFile());
        productToUpdate.setMainImage(image.getMainImage());
        productToUpdate.setProduct(image.getProduct());
        productToUpdate.setFilename(image.getFilename());

        return imageRepository.save(productToUpdate);
    }

    public void deleteImage(int imageId) {
        imageRepository.deleteById(imageId);
    }

    // Utils

}
