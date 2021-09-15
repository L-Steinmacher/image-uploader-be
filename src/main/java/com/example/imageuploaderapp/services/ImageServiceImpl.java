package com.example.imageuploaderapp.services;

import com.example.imageuploaderapp.exceptions.ResourceNotFoundException;
import com.example.imageuploaderapp.models.ImageModel;
import com.example.imageuploaderapp.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    /**
     * Gets and returns a list of Images to the user
     * @return list
     */
    @Override
    public List<ImageModel> findAllImages()
    {
        List<ImageModel> list = new ArrayList<>();

        imageRepository.findAll()
            .iterator()
            .forEachRemaining(list::add);
        return list;
    }

    /**
     * Gets and returns a single image from the database to the user.
     * @param name of the image
     * @return a single image to the user
     */
    @Override
    public ImageModel findImageByName(String name)
    {
        if (imageRepository.findByName(name).isPresent())
        {
            final Optional<ImageModel> retrievedImage = imageRepository.findByName(name);
            byte[] decompressByte = helperFunctions.decompressBytes(retrievedImage.get().getPicByte());
            ImageModel decompressImage = new ImageModel(
                retrievedImage.get().getName(),
                retrievedImage.get().getType(),
                decompressByte,
                retrievedImage.get().getUser()
            );
            return decompressImage;
        }else
        {
            throw new ResourceNotFoundException("Image with name " + name + " not found!");
        }
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
        byte[] newCompressedByte = helperFunctions.compressBytes(image.getPicByte());
        ImageModel newImage = new ImageModel();

        newImage.setName(image.getName());
        newImage.setPicByte(newCompressedByte);
        newImage.setType(image.getType());
        newImage.setUser(userService.findUserById(image.getUser().getUserid()));

        return newImage;
    }


}
