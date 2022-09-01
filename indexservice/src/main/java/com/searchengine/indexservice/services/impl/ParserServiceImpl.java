package com.searchengine.indexservice.services.impl;

import com.searchengine.indexservice.models.HtmlDocument;
import com.searchengine.indexservice.models.SQSHtmlMetadata;
import com.searchengine.indexservice.services.ParserService;
import org.springframework.stereotype.Service;

/**
 * created by nikunjagarwal on 01-09-2022
 */
@Service
public class ParserServiceImpl implements ParserService {


    /**
     * Step 1 : Download File from S3
     * Step 2 : Parse the file to get child urls, title, body
     * @param sqsHtmlMetadata
     * @return
     */
    @Override
    public HtmlDocument parseHtmlDocument(SQSHtmlMetadata sqsHtmlMetadata, String s3File) {
        return null;
    }


}
