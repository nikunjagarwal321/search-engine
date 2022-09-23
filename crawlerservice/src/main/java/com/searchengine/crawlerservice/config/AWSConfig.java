package com.searchengine.crawlerservice.config;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.sqs.AmazonSQSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by amankumarkeshu on 11/9/22.
 * AWS Config class.
 */
@Configuration
public class AWSConfig {

    @Bean
    public AmazonSQSClient amazonSQSClient() {
        AmazonSQSClient sqsClient = new AmazonSQSClient();
        sqsClient.setRegion(Region.getRegion(Regions.AP_SOUTH_1));
        return sqsClient;
    }

    @Bean
    public AmazonS3Client amazonS3Client () {
        AmazonS3Client s3Client = new AmazonS3Client();
        s3Client.listBuckets();
        return s3Client;
    }
}
