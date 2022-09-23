package com.searchengine.crawlerservice.worker;

import com.amazonaws.services.s3.AmazonS3Client;
import com.searchengine.crawlerservice.Util.AWSUtil;
import com.searchengine.crawlerservice.Util.JSONUtil;
import com.searchengine.crawlerservice.Util.SQSUtil;
import com.searchengine.crawlerservice.dto.CrawlRequest;
import com.searchengine.crawlerservice.dto.SQSHtmlMetadata;
import com.searchengine.crawlerservice.service.impl.SQSListenerImpl;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Slf4j
public class CrawlWorker implements Runnable {

    String s3Bucket = "scraper-beta";

    final SQSUtil sqsUtil;

    final CrawlRequest crawlRequest;

    final AWSUtil awsUtil;

    final String User_Agent_Mozilla = "Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:2.2) Gecko/20110201";

    public CrawlWorker(CrawlRequest crawlRequest, AWSUtil awsUtil, SQSUtil sqsUtil) {
        this.crawlRequest = crawlRequest;
        this.awsUtil = awsUtil;
        this.sqsUtil = sqsUtil;
    }

    @Override
    public void run() {

        int tryCount = 0;
        while (tryCount < 3) {
            try {
                tryCount++;
                Connection.Response response = Jsoup.connect(crawlRequest.getUrl())
                        .userAgent(User_Agent_Mozilla)
                        .execute();

                Document doc = response.parse();
                if (doc.body().toString().length() < 500) {
                    log.info("Retrying page {} ", crawlRequest.getUrl());
                    // Retrying if the page is partial after exponential waiting time EG: 4secs, 8secs, 16 secs
                    Thread.sleep((long) (2000 * Math.pow(2, tryCount)));
                }

                if (response.statusCode() == 200) {
                    //TODO: Add more checks to verify partial pages
                    log.info("Url Crawled Successfully: {}", crawlRequest.getUrl());
                    String s3Path = crawlRequest.getUrlId().toString();
                    awsUtil.addToS3(s3Bucket, s3Path, doc);

                    // 1. Push doc to the s3
                    // 2.event to sqs which would read by parser
                    SQSHtmlMetadata sqsHtmlMetadata = SQSHtmlMetadata.builder().
                            urlId(crawlRequest.getUrlId()).url(crawlRequest.getUrl()).
                            s3Path(s3Path).status(HttpStatus.valueOf(response.statusCode())).
                            redirectedUrl(response.headers().get("location")).
                            lastCrawledTime(new Date().toString()).build();
                    sqsUtil.sendMessage(JSONUtil.convertObjectToString(sqsHtmlMetadata));

                    return;
                } else {
                    log.info("Error in crawling : {} ", crawlRequest.getUrl());
                    // Retrying if page found doesn't have success code
                    Thread.sleep((long) (2000 * Math.pow(2, tryCount)));
                }

            } catch (Exception e) {
                //TODO : send to sqs with error
                log.error(e.getMessage() + crawlRequest.getUrl());
            }
        }
    }



}
