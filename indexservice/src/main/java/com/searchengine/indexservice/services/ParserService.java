package com.searchengine.indexservice.services;

import com.searchengine.indexservice.dto.HtmlDocument;
import com.searchengine.indexservice.dto.SQSHtmlMetadata;

/**
 * created by nikunjagarwal on 30-08-2022
 */
public interface ParserService {
    HtmlDocument parseHtmlDocument(SQSHtmlMetadata sqsHtmlMetadata, String s3File);
}
