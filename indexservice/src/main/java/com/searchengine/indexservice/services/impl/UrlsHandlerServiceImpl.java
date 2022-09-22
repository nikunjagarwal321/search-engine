package com.searchengine.indexservice.services.impl;

import com.searchengine.indexservice.dto.CrawlStatus;
import com.searchengine.indexservice.dto.UrlMetadata;
import com.searchengine.indexservice.models.HtmlDocument;
import com.searchengine.indexservice.models.SQSHtmlMetadata;
import com.searchengine.indexservice.repository.UrlMetadataRepository;
import com.searchengine.indexservice.services.UrlsHandlerService;
import com.searchengine.indexservice.utils.UrlHandlerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * created by nikunjagarwal on 02-09-2022
 */
@Slf4j
@Service
public class UrlsHandlerServiceImpl implements UrlsHandlerService {

    @Autowired
    UrlHandlerUtil urlHandlerUtil;

    @Autowired
    UrlMetadataRepository urlMetadataRepository;


    /**
     * Step 1 : update existing url metadata in the table
     * Step 2 : convert relative child urls to absolute child urls
     * Step 3 : filter in scope child urls
     * Step 4.a : insert new child urls in db and send to sqs for crawling
     * Step 4.b : update pagerank of old child urls
     * @param sqsHtmlMetadata
     * @param htmlDocument
     */
    @Override
    public void insertChildUrlsInRdsAndSqs(SQSHtmlMetadata sqsHtmlMetadata, HtmlDocument htmlDocument) {
        log.info("");
        //TODO : handle redirected url separately

        urlMetadataRepository.updateExistingUrl(sqsHtmlMetadata.getRedirectedUrl(), sqsHtmlMetadata.getS3Path(),
                htmlDocument.getTitle(), CrawlStatus.CRAWLED, sqsHtmlMetadata.getStatus().value(), sqsHtmlMetadata.getUrlId());
        Set<String> childUrls = urlHandlerUtil.parseChildUrls(htmlDocument.getChildUrls(), sqsHtmlMetadata.getRedirectedUrl());//use redirect url as that is final url
        List<String> inScopeChildUrls = urlHandlerUtil.filterInScopeUrls(childUrls);
        sendChildUrlsForCrawling(inScopeChildUrls);

    }

    @Override
    public void updateCrawlingErrorUrls(SQSHtmlMetadata sqsHtmlMetadata) {

    }

    /**
     * Insert new child urls in RDS and send to sqs for crawling.
     * Update pagerank of old child urls
     * @param inScopeChildUrls
     */
    public void sendChildUrlsForCrawling(List<String> inScopeChildUrls) {
        for(String childUrl : inScopeChildUrls) {
            ArrayList<UrlMetadata> childUrlExists = urlMetadataRepository.findByUrl(childUrl);
            if(childUrlExists.isEmpty()) {
                UrlMetadata newUrl = UrlMetadata.builder().url(childUrl).crawlStatus(CrawlStatus.QUEUED).retryCount(0).pageRank(1).build();
                urlMetadataRepository.save(newUrl);
                //TODO: send to sqs
            } else {
                urlMetadataRepository.updatePagerank(childUrlExists.get(0).getPageRank() + 1, childUrl);
            }
        }
    }
}
