package com.example.imageuploaderapp.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.example.imageuploaderapp.bucket.BucketName;
import com.example.imageuploaderapp.exceptions.ResourceNotFoundException;
import com.example.imageuploaderapp.filestore.FileStore;
import com.example.imageuploaderapp.models.Image;
import com.example.imageuploaderapp.models.User;
import com.example.imageuploaderapp.repository.ImageRepository;
import com.example.imageuploaderapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
    private UserRepository userRepository;

    @Autowired
    private HelperFunctions helperFunctions;

    @Autowired
    AmazonS3 amazonS3;

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
     * Finds an image by it's id from the database
     * @param id The id of the image to be retrieved
     * @return the image from the database
     */
    @Override
    public Image findImageById(Long id)
    {
        return imageRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Image with id " + id + " not found!"));
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

    @Transactional
    public void deleteImage(Long id)
    {
        if (imageRepository.findById(id).isPresent())
        {
            deleteFile(imageRepository.findById(id).get().getLink());
            imageRepository.deleteById(id);
        }else
        {
            throw new ResourceNotFoundException("Image with id: " + id + " not found!");
        }
    }

    @Transactional
    @Override
    public byte[] downloadImage(Long userid, Long imageid)
    {
        User user = userRepository.findById(userid)
            .orElseThrow(() -> new ResourceNotFoundException("User id " + userid + " not found!"));
        Image image = findImageById(imageid);
        String path = String.format("%s/%s",
            BucketName.PROFILE_IMAGE.getBucketName(),
            user.getId());
        String key = image.getLink();
        return fileStore.download(path,key);
    }
    @Async
    public void deleteFile(final String keyName) {
        String bucketName = BucketName.PROFILE_IMAGE.getBucketName();
        final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, keyName);
        amazonS3.deleteObject(deleteObjectRequest);
    }

}
