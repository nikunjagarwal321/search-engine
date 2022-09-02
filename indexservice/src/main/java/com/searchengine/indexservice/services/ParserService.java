package com.searchengine.indexservice.services;

import com.searchengine.indexservice.models.HtmlDocument;
import com.searchengine.indexservice.models.SQSHtmlMetadata;

/**
 * created by nikunjagarwal on 30-08-2022
 */
public interface ParserService {
    public HtmlDocument parseHtmlDocument(SQSHtmlMetadata sqsHtmlMetadata, String s3File);
}
