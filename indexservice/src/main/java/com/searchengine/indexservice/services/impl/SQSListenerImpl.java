package com.searchengine.indexservice.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.List;

/**
 * created by nikunjagarwal on 18-09-2022
 */
@Slf4j
@Service
public class SQSListenerImpl {

    @Value("${sqs.htmlmetadata.url}")
    private String htmlMetadataSQSUrl;

    @Value("${sqs.crawlerurls.url}")
    private String crawlerUrlsSQSUrl;

    @Autowired
    private OrchestratorServiceImpl orchestratorService;

    private SqsClient sqsClient = SqsClient.builder().region(Region.AP_SOUTH_1).build();

    /*add once we enable sqs read*/
    @Scheduled(fixedRate = 10000)
    public void poll() {
        receiveMessage();
    }

    public void sendMessage(String message) {
        log.info("Inside sendMessage");

        try {
            SendMessageResponse sqsResponse = sqsClient.sendMessage(
                    SendMessageRequest.builder().queueUrl(crawlerUrlsSQSUrl).messageBody(message).messageGroupId("100").build());
            log.info("Send message : {}", sqsResponse);
        } catch (Exception e) {
            log.error("Error : {}", e);
        }
    }


    public void receiveMessage() {
        log.info("Inside receiveMessage");

        try {
            ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder().queueUrl(htmlMetadataSQSUrl)
                    .maxNumberOfMessages(3).build();
            ReceiveMessageResponse receiveMessageResponse = sqsClient.receiveMessage(receiveMessageRequest);
            List<Message> messages = receiveMessageResponse.messages();
            log.info("Received messages : {}", messages);
            for (Message message : messages) {
                log.info("Message : {}", message);
                orchestratorService.startOrchestration(message);
                deleteMessage(message);
            }
        } catch (Exception e) {
            log.error("Error : {}", e);
        }
    }

    public void deleteMessage(Message message) {
        log.info("Inside deleteMessage");
        try {
            DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder().queueUrl(htmlMetadataSQSUrl)
                    .receiptHandle(message.receiptHandle()).build();
            sqsClient.deleteMessage(deleteMessageRequest);
            log.info("Deleted message : {}", message);
        } catch (Exception e) {
            log.error("Error : {}", e);
        }

    }
}
