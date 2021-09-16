package com.example.imageuploaderapp.services;

import com.example.imageuploaderapp.bucket.BucketName;
import com.example.imageuploaderapp.exceptions.ResourceNotFoundException;
import com.example.imageuploaderapp.filestore.FileStore;
import com.example.imageuploaderapp.models.Image;
import com.example.imageuploaderapp.models.User;
import com.example.imageuploaderapp.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

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

    private final FileStore fileStore;

    public ImageServiceImpl(FileStore fileStore)
    {
        this.fileStore = fileStore;
    }

    /**
     * Gets and returns a list of Images to the user
     * @return list
     */
    @Override
    public List<Image> findAllImages()
    {
        List<Image> list = new ArrayList<>();

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
    public Image findImageByName(String name)
    {
//        if (imageRepository.findByName(name).isPresent())
//        {
//            final Optional<ImageModel> retrievedImage = imageRepository.findByName(name);
//            byte[] decompressByte = helperFunctions.decompressBytes(retrievedImage.get().getPicByte());
//            ImageModel decompressImage = new ImageModel(
//                retrievedImage.get().getName(),
//                retrievedImage.get().getType(),
//                decompressByte,
//                retrievedImage.get().getUser()
//            );
//            return decompressImage;
//        }else
//        {
//            throw new ResourceNotFoundException("Image with name " + name + " not found!");
//        }
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
     *  takes the users id and the file and saves it to their S3 bucket
     * @param id of the user(may change to UUID)
     * @param file the file itself
     */
    @Override
    public Image uploadImage(long id, MultipartFile file)
    {
        Image newImage = new Image();

        //check if image is not empty
        isFileEmpty(file);

        //check if file is an image
        isImage(file);

        // The user exists in database
        User user = userService.findUserById(id);

        // Grabs metadata from file if any
        Map<String, String> metadata = extractMetadata(file);

        String path = String.format("%s/%s",
            BucketName.PROFILE_IMAGE.getBucketName(), userService.findUserById(id));
        String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());

        newImage.setName(filename);
        newImage.setUser(user);
        newImage.setLink(path);

        try
        {
            fileStore.save(path,filename,Optional.of(metadata),file.getInputStream());
            user.getImagetables().add(newImage);
            userService.save(user);
        }catch (IOException e)
        {
            throw  new IllegalStateException(e);
        }
        return newImage;
    }

    private Map<String, String> extractMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

    private void isImage(MultipartFile file)
    {
        if (!Arrays.asList(
            IMAGE_JPEG.getMimeType(),
            IMAGE_PNG.getMimeType(),
            IMAGE_GIF.getMimeType()).contains(file.getContentType()))
        {
            throw new ResourceNotFoundException("File must be an image! [" + file.getContentType() + "]");
        }
    }

    private void isFileEmpty(MultipartFile file)
    {
        if (file.isEmpty())
        {
            throw new ResourceNotFoundException("Cannot upload and empty file! [" + file.getSize() + "]");
        }
    }


}
