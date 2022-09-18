package com.searchengine.indexservice.services.impl;

import com.searchengine.indexservice.models.HtmlDocument;
import com.searchengine.indexservice.models.SQSHtmlMetadata;
import com.searchengine.indexservice.services.ParserService;
import com.searchengine.indexservice.utils.ParserUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * created by nikunjagarwal on 01-09-2022
 */
@Slf4j
@Service
public class ParserServiceImpl implements ParserService {

    @Autowired
    ParserUtil parserUtil;

    /**
     * Step 1 : Download File from S3
     * Step 2 : Parse the file to get child urls, title, body
     * @param sqsHtmlMetadata
     * @return
     */
    @Override
    public HtmlDocument parseHtmlDocument(SQSHtmlMetadata sqsHtmlMetadata, String s3File) {
        Document document = Jsoup.parse(s3File);
        HtmlDocument htmlDocument = HtmlDocument.builder().
                url(sqsHtmlMetadata.getUrl()).
                urlId(sqsHtmlMetadata.getUrlId()).
                body(document.text()).
                title(document.title()).
                childUrls(parserUtil.getChildUrls(document)).
                build();
        log.info("Parsed HTMLDocument : {}", htmlDocument);
        return htmlDocument;
    }
}
