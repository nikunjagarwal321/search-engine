package com.searchengine.crawlerservice.service;

import com.searchengine.crawlerservice.dto.UrlMapping;

import java.util.List;

public interface CrawlerService {
    public void crawl(List<String> urls);
}
