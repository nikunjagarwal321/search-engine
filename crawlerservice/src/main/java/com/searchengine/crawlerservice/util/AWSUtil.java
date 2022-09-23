package com.searchengine.crawlerservice.util;

import com.amazonaws.services.s3.AmazonS3Client;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
