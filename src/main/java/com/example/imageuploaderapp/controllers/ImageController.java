package com.example.imageuploaderapp.controllers;

import com.example.imageuploaderapp.services.ImageService;
import com.example.imageuploaderapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "image")
public class ImageController
{
    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;


}
