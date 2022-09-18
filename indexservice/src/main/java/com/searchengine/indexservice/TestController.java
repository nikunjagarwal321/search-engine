package com.searchengine.indexservice;

import com.searchengine.indexservice.models.SQSHtmlMetadata;
import com.searchengine.indexservice.services.impl.OrchestratorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: Remove this class later
 * created by nikunjagarwal on 18-09-2022
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    OrchestratorServiceImpl orchestratorService;


    @PostMapping("/v1")
    public void test(@RequestBody SQSHtmlMetadata message) throws Exception{
        orchestratorService.startOrchestration(message);
    }
}
