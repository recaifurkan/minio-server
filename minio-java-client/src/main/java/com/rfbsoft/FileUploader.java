package com.rfbsoft;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;

import io.minio.MinioClient;
import io.minio.errors.MinioException;

public class FileUploader {
  public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeyException {
    try {
      // Create a minioClient with the MinIO Server name, Port, Access key and Secret key.
      MinioClient minioClient = MinioClient.builder()
              .endpoint("http://localhost:9000")
              .credentials("minio-access-key", "minio-secret-key")
              .build();

      // Check if the bucket already exists.
      boolean isExist = 
        minioClient.bucketExists(BucketExistsArgs.builder().bucket("asiatrip").build());
      if(isExist) {
        System.out.println("Bucket already exists.");
      } else {
        // Make a new bucket called asiatrip to hold a zip file of photos.
        minioClient.makeBucket(MakeBucketArgs.builder().bucket("asiatrip").build());
      }

      // Upload the zip file to the bucket with putObject
      minioClient.putObject("asiatrip","test.txt", "test.txt", null);
      System.out.println("/home/user/Photos/asiaphotos.zip is successfully uploaded as asiaphotos.zip to `asiatrip` bucket.");
    } catch(MinioException e) {
      System.out.println("Error occurred: " + e);
    }
  }
}