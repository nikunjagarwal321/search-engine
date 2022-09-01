package com.searchengine.indexservice.services;

import software.amazon.awssdk.services.sqs.model.Message;

/**
 * created by nikunjagarwal on 01-09-2022
 */
public interface OrchestratorService {
    public void startOrchestration(Message message) throws Exception ;

}
