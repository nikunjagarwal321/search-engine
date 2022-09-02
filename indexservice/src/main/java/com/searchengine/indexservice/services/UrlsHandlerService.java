package com.searchengine.indexservice.services;

import com.searchengine.indexservice.models.HtmlDocument;
import com.searchengine.indexservice.models.SQSHtmlMetadata;

/**
 * created by nikunjagarwal on 01-09-2022
 */
public interface UrlsHandlerService {
    void insertChildUrlsInRdsAndSqs(SQSHtmlMetadata sqsHtmlMetadata, HtmlDocument htmlDocument);
    void updateCrawlingErrorUrls(SQSHtmlMetadata sqsHtmlMetadata);
}
