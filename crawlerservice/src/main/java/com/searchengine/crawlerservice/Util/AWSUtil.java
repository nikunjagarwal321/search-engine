package com.searchengine.crawlerservice.Util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.sqs.AmazonSQSClient;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class AWSUtil {

    @Autowired
    AmazonS3Client amazonS3Client;


    public Boolean addToS3(String container, String key, Document doc)
            throws  IllegalArgumentException {

        log.info("{} container name  Keyname: {}", container, key);

        try {
            String file = doc.toString();
            amazonS3Client.putObject(container, key, file);
            return true;
        } catch (Exception e) {
            log.error("Error = {} while uploading file", e.getMessage());
            return false;
        }
    }

}
