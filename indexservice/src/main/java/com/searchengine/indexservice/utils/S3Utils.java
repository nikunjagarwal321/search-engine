package com.searchengine.indexservice.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * created by nikunjagarwal on 30-08-2022
 */
@Slf4j
@Service
public class S3Utils {

    //TODO: modify the functions if we use file instead of string
    private static S3Client s3Client = S3Client.builder().region(Region.AP_SOUTH_1).build();

    @Value("${bucket}")
    private static String bucketName;

    public static void upload(String s3Filename, String localFilePath){
        log.info("Inside upload");
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Filename)
                .build();

        Path path = Paths.get(localFilePath);
        RequestBody requestBody = RequestBody.fromFile(path);
        log.info("Uploading file : {}", requestBody);
        s3Client.putObject(objectRequest, requestBody);
        log.info("File uploaded successfully");
    }

    public static String download(String s3Filename) {
        log.info("Inside download");
        GetObjectRequest getObject = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Filename)
                .build();
        ResponseInputStream<GetObjectResponse> response = s3Client.getObject(getObject);
        log.info("File downloaded successfully : {}", response.toString());
        return convertS3FileToString(response);
    }

    public static void delete(String s3Filename){
        log.info("Inside delete");
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Filename)
                .build();
        DeleteObjectResponse response = s3Client.deleteObject(deleteObjectRequest);
        log.info("File deleted successfully : {}", response.toString());
    }

    private static String convertS3FileToString(ResponseInputStream<GetObjectResponse> response){
        BufferedReader reader = new BufferedReader(new InputStreamReader(response));
        String line;
        String file = "";
        try {
            while ((line = reader.readLine()) != null) {
                log.info(line);
                file = file + line + "\n" ;
            }
            log.info("File Contents : " + file);
        } catch(Exception e) {
            log.error("Error: {}", e);
        }
        return file;
    }
}
