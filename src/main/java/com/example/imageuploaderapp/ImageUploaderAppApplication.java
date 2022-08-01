package com.example.imageuploaderapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ImageUploaderAppApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(ImageUploaderAppApplication.class,
            args);
    }
}
