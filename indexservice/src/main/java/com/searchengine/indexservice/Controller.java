package com.searchengine.indexservice;

import com.searchengine.indexservice.models.SQSHtmlMetadata;
import com.searchengine.indexservice.services.UrlsHandlerService;
import com.searchengine.indexservice.services.impl.OrchestratorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TODO: Remove this class later
 * created by nikunjagarwal on 18-09-2022
 */
@RestController
@RequestMapping("/api/v1")
public class Controller {

    @Autowired
    OrchestratorServiceImpl orchestratorService;

    @Autowired
    UrlsHandlerService urlsHandlerService;


    @PostMapping("/start")
    public void insertNewUrlsForCrawling(@RequestBody List<String> newUrls) throws Exception {
        urlsHandlerService.insertNewUrlsForCrawling(newUrls);
    }

    @PostMapping("/test")
    public void test(@RequestBody SQSHtmlMetadata message) throws Exception{
        orchestratorService.startOrchestration(message);
    }
}
