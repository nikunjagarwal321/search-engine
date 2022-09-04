package com.searchengine.crawlerservice.controller;

import com.searchengine.crawlerservice.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.searchengine.crawlerservice.dto.UrlMapping;

import java.util.List;

@RestController
@RequestMapping("/api/crawler")
public class crawlerController {

    @Autowired
    public CrawlerService crawlerService;


    @PostMapping("/triggerCrawl")
    public ResponseEntity<String> sendForcrawl( @RequestBody List<String> urls) {

        crawlerService.crawl(urls);
        ResponseEntity<String> responseEntity = new ResponseEntity("Sent for crawling", HttpStatus.OK);
        return responseEntity;

    }
}
