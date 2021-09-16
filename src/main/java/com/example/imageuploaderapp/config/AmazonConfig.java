package com.example.imageuploaderapp.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig
{
    @Bean
    public AmazonS3 s3()
    {
        AWSCredentials awsCredentials = new BasicAWSCredentials(
            "AKIASHINJTR7U7SZWAS3",
            "+a11jHhVgQX4flMvGdch4XlK8T8Bo9K59by6Ua29"
        );
        return AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
            .build();
    }
}
