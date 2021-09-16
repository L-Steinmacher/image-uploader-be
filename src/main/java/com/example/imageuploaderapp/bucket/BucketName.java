package com.example.imageuploaderapp.bucket;

public enum BucketName
{
    PROFILE_IMAGE("lucas-image-uploader-123");

    private final String bucketName;

    BucketName(String bucketName)
    {
        this.bucketName = bucketName;
    }

    public String getBucketName()
    {
        return bucketName;
    }
}
