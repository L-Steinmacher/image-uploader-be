package com.example.imageuploaderapp.services;

import com.example.imageuploaderapp.models.ImageModel;
import com.example.imageuploaderapp.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return null;
    }

    @Override
    public ImageModel findImageById(long id)
    {
        return null;
    }

    @Override
    public void delete(long id)
    {

    }

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
