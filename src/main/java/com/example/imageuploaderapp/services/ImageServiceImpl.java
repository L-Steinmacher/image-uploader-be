package com.example.imageuploaderapp.services;

import com.example.imageuploaderapp.exceptions.ResourceNotFoundException;
import com.example.imageuploaderapp.models.ImageModel;
import com.example.imageuploaderapp.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "ImageService")
public class ImageServiceImpl implements ImageService
{
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private HelperFunctions helperFunctions;

    @Override
    public List<ImageModel> findAllImages()
    {
        List<ImageModel> list = new ArrayList<>();

        imageRepository.findAll()
            .iterator()
            .forEachRemaining(list::add);
        return list;
    }

    @Override
    public ImageModel findImageById(long id)
    {
        return null;
    }

    /**
     * Deletes and removes an image object from the database.
     * @param id The id of the image to be removed from the database.
     */
    @Override
    public void delete(long id)
    {
        if (imageRepository.findById(id).isPresent())
        {
            imageRepository.deleteById(id);
        }else
        {
            throw new ResourceNotFoundException("Image id " + id + " not found!");
        }
    }

    /**
     * Given complete image object, saves that image object in the database.
     * If a primary key is provided, the record is completely replaced
     * If no primary key is provided, one is automatically generated and the record is added to the database.
     * @param image the user object to be saved
     * @return the saved image pbject any auto generated fields
     */
    @Override
    public ImageModel save(ImageModel image)
    {
        byte[] newCompressed = helperFunctions.compressBytes(image.getPicByte());
        ImageModel newImage = new ImageModel();

        newImage.setName(image.getName());
        newImage.setPicByte(newCompressed);
        newImage.setType(image.getType());
        newImage.setUser(userService.findUserById(image.getUser().getUserid()));

        return newImage;
    }


}
