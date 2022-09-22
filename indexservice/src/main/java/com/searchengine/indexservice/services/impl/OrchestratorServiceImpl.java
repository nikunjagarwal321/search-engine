package com.searchengine.indexservice.services.impl;

import com.searchengine.indexservice.constants.IndexingServiceEnum;
import com.searchengine.indexservice.models.HtmlDocument;
import com.searchengine.indexservice.models.SQSHtmlMetadata;
import com.searchengine.indexservice.services.OrchestratorService;
import com.searchengine.indexservice.services.UrlsHandlerService;
import com.searchengine.indexservice.services.factory.IndexingServiceFactory;
import com.searchengine.indexservice.services.factory.ParserFactory;
import com.searchengine.indexservice.utils.FileHandlerUtil;
import com.searchengine.indexservice.utils.JSONUtils;
import com.searchengine.indexservice.utils.S3Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.model.Message;


/**
 * created by nikunjagarwal on 01-09-2022
 */
@Service
public class OrchestratorServiceImpl implements OrchestratorService {
    @Value("${bucket}")
    private String bucketName;

    @Autowired
    UrlsHandlerService urlsHandlerService;

    @Autowired
    ParserFactory parserFactory;

    @Autowired
    IndexingServiceFactory indexingServiceFactory;

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
        if(sqsHtmlMetadata.getStatus().isError()) {
            urlsHandlerService.updateCrawlingErrorUrls(sqsHtmlMetadata);
            return;
        }
        String s3FileContents = S3Utils.download(bucketName, sqsHtmlMetadata.getS3Path());
        HtmlDocument htmlDocument = parserFactory.getParser(sqsHtmlMetadata.getUrl()).parseHtmlDocument(sqsHtmlMetadata, s3FileContents);
        urlsHandlerService.insertChildUrlsInRdsAndSqs(sqsHtmlMetadata, htmlDocument);
        indexingServiceFactory.getIndexingService(IndexingServiceEnum.MONGO_DB).createAndInsertInvertedIndexInDB(htmlDocument);
    }

    //TODO : only for testing, remove later
    public void startOrchestration(SQSHtmlMetadata sqsHtmlMetadata) throws Exception {
        if(sqsHtmlMetadata.getStatus().isError()) {
            urlsHandlerService.updateCrawlingErrorUrls(sqsHtmlMetadata);
            return;
        }
//        String s3FileContents = S3Utils.download(bucketName, sqsHtmlMetadata.getS3Path());
        String s3FileContents = FileHandlerUtil.readFromFile(sqsHtmlMetadata.getS3Path());
        HtmlDocument htmlDocument = parserFactory.getParser(sqsHtmlMetadata.getUrl()).parseHtmlDocument(sqsHtmlMetadata, s3FileContents);
        urlsHandlerService.insertChildUrlsInRdsAndSqs(sqsHtmlMetadata, htmlDocument);
//        indexingServiceFactory.getIndexingService(IndexingServiceEnum.MONGO_DB).createAndInsertInvertedIndexInDB(htmlDocument);
    }
}
