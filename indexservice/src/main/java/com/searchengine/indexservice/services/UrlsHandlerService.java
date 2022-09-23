package com.searchengine.indexservice.services;

import com.searchengine.indexservice.models.HtmlDocument;
import com.searchengine.indexservice.models.SQSHtmlMetadata;

import java.io.IOException;
import java.util.List;

/**
 * created by nikunjagarwal on 01-09-2022
 */
public interface UrlsHandlerService {
    void insertChildUrlsInRdsAndSqs(SQSHtmlMetadata sqsHtmlMetadata, HtmlDocument htmlDocument) throws IOException;
    void updateCrawlingErrorUrls(SQSHtmlMetadata sqsHtmlMetadata) throws IOException;
    void insertNewUrlsForCrawling(List<String> urls) throws IOException;

}
