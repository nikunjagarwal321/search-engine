package com.searchengine.indexservice.services.impl;

import com.searchengine.indexservice.models.HtmlDocument;
import com.searchengine.indexservice.models.SQSHtmlMetadata;
import com.searchengine.indexservice.services.UrlsHandlerService;
import org.springframework.stereotype.Service;

/**
 * created by nikunjagarwal on 02-09-2022
 */
@Service
public class UrlsHandlerServiceImpl implements UrlsHandlerService {
    @Override
    public void insertChildUrlsInRdsAndSqs(SQSHtmlMetadata sqsHtmlMetadata, HtmlDocument htmlDocument) {

    }

    @Override
    public void updateCrawlingErrorUrls(SQSHtmlMetadata sqsHtmlMetadata) {

    }
}
