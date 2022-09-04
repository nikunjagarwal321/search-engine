package com.searchengine.crawlerservice.dto;

/**
 * Created by Aman K on 3/9/22.
 * The request which is used for crawling pages.
 */
public class CrawlRequest {
    String url;
    int retry;

    public CrawlRequest(String url, int retry) {
        this.url = url;
        this.retry = retry;
    }

    public String getUrl() {
        return url;
    }


    public int getRetryCount() {
        return retry;
    }


    public enum CrawlerResult {
        SUCCESS, ERROR
    }
}
