package com.shopsphere.shopsphere.shared.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class ImageUtils {

    private static final long MAX_IMAGE_SIZE = 16 * 1024 * 1024;

    private static final Set<String> ALLOWED_CONTENT_TYPES = new HashSet<>(Arrays.asList(
            "image/jpeg",
            "image/png",
            "image/webp",
            "image/jpg"
    ));


    public void validateImage(MultipartFile image) {

        if (image.isEmpty()) {
            throw new IllegalArgumentException("L'image ne peut pas être vide");
        }


        if (image.getSize() > MAX_IMAGE_SIZE) {
            throw new IllegalArgumentException("La taille de l'image dépasse la limite maximale de 5MB");
        }


        String contentType = image.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType.toLowerCase())) {
            throw new IllegalArgumentException("Type d'image invalide. Types autorisés: JPEG, PNG, JPG et WEBP");
        }
    }

    /**
     * Converts primitive byte array to Byte object array
     */
    public Byte[] convertToByteObjects(byte[] bytes) {
        Byte[] byteObjects = new Byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            byteObjects[i] = bytes[i];
        }
        return byteObjects;
    }

    public byte[] convertToPrimitiveBytes(Byte[] bytes) {
        byte[] primitiveBytes = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            primitiveBytes[i] = bytes[i];
        }
        return primitiveBytes;
    }

}
