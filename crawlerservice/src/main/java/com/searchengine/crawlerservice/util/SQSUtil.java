package com.searchengine.crawlerservice.util;

import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * created by nikunjagarwal on 23-09-2022
 */
@Slf4j
@Service
public class SQSUtil {

    @Value("${sqs.htmlmetadata.url}")
    private String htmlMetadataSQSUrl;

    @Autowired
    private AmazonSQSClient sqsClient;

    public void sendMessage(String message) {
        log.info("Inside sendMessage");

        try {
            SendMessageResult sqsResponse = sqsClient.sendMessage(
                    new SendMessageRequest().withQueueUrl(htmlMetadataSQSUrl).
                            withMessageBody(message).withMessageGroupId("100"));
            log.info("Send message : {}", sqsResponse);
        } catch (Exception e) {
            log.error("Error : {}", e);
        }
    }
}
