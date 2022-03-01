package com.example.imageuploaderapp.services;

import com.example.imageuploaderapp.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService
{
    /**
     * returns all images
     */
    List<Image> findAllImages();

    /**
     * Find image by name
     */
    Image findImageById(Long id);

    /**
     * deletes an image by it's id
     */
    void delete(long id);

    /**
     * Saves a new image to the S3 database
     */
    Image uploadImage (long id, MultipartFile file);

    public byte[] downloadImage(Long userid, Long imageid);

    public List<Image> findCurrentUserImages(Long userid);

}
