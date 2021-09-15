package com.example.imageuploaderapp.services;

import com.example.imageuploaderapp.models.ImageModel;

import java.util.List;

public interface ImageService
{
    /**
     * returns all images
     */
    List<ImageModel> findAllImages();

    /**
     * Find image by id
     */
    ImageModel findImageByName(String name);

    /**
     * deletes an image
     */
    void delete(long id);

    /**
     * Saves a new image to
     */
    ImageModel save (ImageModel image);

}
