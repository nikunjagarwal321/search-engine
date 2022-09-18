package com.searchengine.crawlerservice.dto;

/**
 * Created by amankumarkeshu on 3/9/22.
 * The request which is used for crawling pages.
 */
public class CrawlRequest {
    String url;
    int retry;
    int deepCrawlStage = 0;

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


}
