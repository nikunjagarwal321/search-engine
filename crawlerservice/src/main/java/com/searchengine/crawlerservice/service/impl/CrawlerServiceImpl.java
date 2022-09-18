package com.searchengine.crawlerservice.service.impl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.searchengine.crawlerservice.Util.AWSUtil;
import com.searchengine.crawlerservice.config.ConfigFactory;
import com.searchengine.crawlerservice.dto.CrawlRequest;
import com.searchengine.crawlerservice.dto.UrlMapping;
import com.searchengine.crawlerservice.service.CrawlerService;
import com.searchengine.crawlerservice.worker.CrawlWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Service("CrawlerService")
public class CrawlerServiceImpl implements CrawlerService {

    @Autowired
    public ConfigFactory configFactory;

    @Autowired
    public AWSUtil awsUtil;

    @Override
    public void crawl(List<String> urls) {
        UrlMapping urlMapping = createUrlMapping(urls);

        ThreadPoolExecutor crawlWorkerExecutor = configFactory.getThreadPool("crawlWorker");

        for (CrawlRequest crawlRequest : urlMapping.getUrls()) {
            if (crawlRequest.getRetryCount() >= 3) {
                log.info("Failed to crawl : {} after {} retries ", crawlRequest.getUrl(), crawlRequest.getRetryCount());
                return;
            }
            CrawlWorker crawlWorker = new CrawlWorker(crawlRequest, awsUtil);
            crawlWorkerExecutor.submit(crawlWorker);
            log.info("Sent url : {} for Crawling", crawlRequest.getUrl());
        }

    }

    private UrlMapping createUrlMapping(List<String> urls) {
        UrlMapping urlMapping = new UrlMapping();
        for (String url : urls) {
            CrawlRequest crawlRequest = new CrawlRequest(url, 0);
            urlMapping.getUrls().add(crawlRequest);
        }
        return urlMapping;
    }
}
