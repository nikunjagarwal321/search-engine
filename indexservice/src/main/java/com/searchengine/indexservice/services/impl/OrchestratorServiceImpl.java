package com.searchengine.indexservice.services.impl;

import com.searchengine.indexservice.models.HtmlDocument;
import com.searchengine.indexservice.models.SQSHtmlMetadata;
import com.searchengine.indexservice.services.IndexingService;
import com.searchengine.indexservice.services.OrchestratorService;
import com.searchengine.indexservice.services.ParserService;
import com.searchengine.indexservice.services.UrlsHandlerService;
import com.searchengine.indexservice.utils.JSONUtils;
import com.searchengine.indexservice.utils.S3Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.model.Message;


/**
 * created by nikunjagarwal on 01-09-2022
 */
@Service
public class OrchestratorServiceImpl implements OrchestratorService {
    @Autowired
    ParserService parserService;
    @Autowired
    UrlsHandlerService urlsHandlerService;
    @Autowired
    IndexingService indexingService;

    /**
     * Step 1 : Download html file from s3
     * Step 2 : Parse the document to get title, body, child urls
     * Step 3 : Insert Child Urls in RDS and SQS
     * Step 4 : Updates inverted index in DB
     * @param message
     * @throws Exception
     */
    @Override
    public void startOrchestration(Message message) throws Exception {
        SQSHtmlMetadata sqsHtmlMetadata = JSONUtils.convertStringToObject(message.body(), SQSHtmlMetadata.class);
        String s3FileContents = S3Utils.download(sqsHtmlMetadata.getS3Path());
        HtmlDocument htmlDocument = parserService.parseHtmlDocument(sqsHtmlMetadata, s3FileContents);
        urlsHandlerService.insertChildUrlsInRdsAndSqs(htmlDocument);
        indexingService.createAndInsertInvertedIndexInDB(sqsHtmlMetadata, htmlDocument);
        return;
    }
}
