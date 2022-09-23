package com.searchengine.crawlerservice.service.impl;

import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.*;
import com.searchengine.crawlerservice.util.JSONUtil;
import com.searchengine.crawlerservice.dto.CrawlerUrlMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * created by nikunjagarwal on 18-09-2022
 */
@Slf4j
@Service
public class SQSListenerImpl {


    @Value("${sqs.crawlerurls.url}")
    private String crawlerUrlsSQSUrl;

    @Autowired
    private CrawlerServiceImpl crawlerService;

    @Autowired
    private AmazonSQSClient sqsClient;

    /**to add once sqs in created */
    @Scheduled(fixedRate = 10000)
    public void poll() {
        receiveMessage();
    }


    public void receiveMessage() {
        log.info("Inside receiveMessage");

        try {
            ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest().withQueueUrl(crawlerUrlsSQSUrl)
                    .withMaxNumberOfMessages(3);
            ReceiveMessageResult receiveMessageResponse = sqsClient.receiveMessage(receiveMessageRequest);
            List<Message> messages = receiveMessageResponse.getMessages();
            log.info("Received messages : {}", messages);
            List<CrawlerUrlMetadata> crawlerUrlMetadataList = new ArrayList<>();
            for (Message message : messages) {
                log.info("Message : {}", message);
                crawlerUrlMetadataList.add(JSONUtil.convertStringToObject(message.getBody(), CrawlerUrlMetadata.class));
                deleteMessage(message);
            }
            crawlerService.crawl(crawlerUrlMetadataList);
        } catch (Exception e) {
            log.error("Error : {}", e);
        }
    }

    public void deleteMessage(Message message) {
        log.info("Inside deleteMessage");
        try {
            DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest().withQueueUrl(crawlerUrlsSQSUrl)
                    .withReceiptHandle(message.getReceiptHandle());
            sqsClient.deleteMessage(deleteMessageRequest);
            log.info("Deleted message : {}", message);
        } catch (Exception e) {
            log.error("Error : {}", e);
        }

    }
}
