package com.example.imageuploaderapp.controllers;

import com.example.imageuploaderapp.services.ImageService;
import com.example.imageuploaderapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "http://localhost:2019")
@RequestMapping(path = "image")
public class ImageController
{
    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

//    @PostMapping(value = "/upload")
//    public ResponseEntity.BodyBuilder uploadImage(
//        @RequestParam("imageFile") MultipartFile file)
//        throws IOException
//    {
//        System.out.println("Original Image Byte Size - " + file.getBytes().length);
//        ImageModel newImg = new ImageModel();
//        imageService.save(newImg);
//        return ResponseEntity.status(HttpStatus.OK);
//    }
//    public BodyBuilder uplaodImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
//        System.out.println("Original Image Byte Size - " + file.getBytes().length);
//        ImageModel img = new ImageModel(file.getOriginalFilename(), file.getContentType(),
//            compressBytes(file.getBytes()));
//        imageRepository.save(img);
//        return ResponseEntity.status(HttpStatus.OK);


//    @GetMapping(path = { "/{imageName}" })
//    public ImageModel getImage(
//        @PathVariable("imageName") String imageName)
//        throws IOException
//    {
//        ImageModel retrievedImage = imageService.findImageByName(imageName);
//        return retrievedImage;
//    }
//        ImageModel img = new ImageModel(retrievedImage.get().getName(), retrievedImage.get().getType(),
//            decompressBytes(retrievedImage.get().getPicByte()),
//            retrievedImage.get().getUser());
//        return img;
//    }

    // compress the image bytes before storing it in the database
//    public static byte[] compressBytes(byte[] data) {
//        Deflater deflater = new Deflater();
//        deflater.setInput(data);
//        deflater.finish();
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
//        byte[] buffer = new byte[1024];
//        while (!deflater.finished()) {
//            int count = deflater.deflate(buffer);
//            outputStream.write(buffer, 0, count);
//        }
//        try {
//            outputStream.close();
//        } catch (IOException e) {
//        }
//        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
//        return outputStream.toByteArray();
//    }
//
//    // uncompress the image bytes before returning it to the angular application
//    public static byte[] decompressBytes(byte[] data) {
//        Inflater inflater = new Inflater();
//        inflater.setInput(data);
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
//        byte[] buffer = new byte[1024];
//        try {
//            while (!inflater.finished()) {
//                int count = inflater.inflate(buffer);
//                outputStream.write(buffer, 0, count);
//            }
//            outputStream.close();
//        } catch (IOException ioe) {
//        } catch (DataFormatException e) {
//        }
//        return outputStream.toByteArray();
//    }

}
