package com.searchengine.crawlerservice.dto;

/**
 * Created by amankumarkeshu on 3/9/22.
 * The request which is used for crawling pages.
 */
public class CrawlRequest {
    Long urlId;
    String url;
    int retry;

    public CrawlRequest(Long urlId, String url, int retry) {
        this.urlId = urlId;
        this.url = url;
        this.retry = retry;
    }

    public Long getUrlId(){return urlId;}

    public String getUrl() {
        return url;
    }


    public int getRetryCount() {
        return retry;
    }


}
