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
    static final String AWS_ACCESS_KEY = System.getenv("ACCESSKEYID");
    static final String AWS_SECRET_KEY = System.getenv("ACCESSKEYSECRET");

    @Bean
    public AmazonS3 s3()
    {
        AWSCredentials awsCredentials = new BasicAWSCredentials(
            AWS_ACCESS_KEY,
            AWS_SECRET_KEY
        );

        return AmazonS3ClientBuilder
            .standard()
            .withRegion("us-west-2")
            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
            .build();
    }
}
