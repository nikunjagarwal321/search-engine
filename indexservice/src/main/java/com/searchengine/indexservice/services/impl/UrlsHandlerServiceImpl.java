package com.searchengine.indexservice.services.impl;

import com.searchengine.indexservice.dto.CrawlStatus;
import com.searchengine.indexservice.dto.UrlMetadata;
import com.searchengine.indexservice.models.CrawlerUrlMetadata;
import com.searchengine.indexservice.models.HtmlDocument;
import com.searchengine.indexservice.models.SQSHtmlMetadata;
import com.searchengine.indexservice.repository.UrlMetadataRepository;
import com.searchengine.indexservice.services.UrlsHandlerService;
import com.searchengine.indexservice.utils.CrawledUrlsCounter;
import com.searchengine.indexservice.utils.JSONUtils;
import com.searchengine.indexservice.utils.UrlHandlerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    @Autowired
    SQSListenerImpl sqsListener;

    @Value("${retry.count}")
    int MAX_RETRY_COUNT;


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
    public void insertChildUrlsInRdsAndSqs(SQSHtmlMetadata sqsHtmlMetadata, HtmlDocument htmlDocument) throws IOException{
        log.info("Inside insertChildUrlsInRdsAndSqs() with htmlMetadata={}, htmlDocument={}", sqsHtmlMetadata, htmlDocument);
        //TODO : handle redirected url separately

        urlMetadataRepository.updateExistingUrl(sqsHtmlMetadata.getRedirectedUrl(), sqsHtmlMetadata.getS3Path(),
                htmlDocument.getTitle(), CrawlStatus.CRAWLED, sqsHtmlMetadata.getStatus().value(), sqsHtmlMetadata.getUrlId());
        Set<String> childUrls = urlHandlerUtil.parseChildUrls(htmlDocument.getChildUrls(), sqsHtmlMetadata.getUrl());//TODO: use redirect url as that is final url
        List<String> inScopeChildUrls = urlHandlerUtil.filterInScopeUrls(childUrls);
        sendChildUrlsForCrawling(inScopeChildUrls);
    }

    /**
     * If 4xx response, dont retry and fail immediately
     * If 5xx response, retry if within retry range, else fail
     * @param sqsHtmlMetadata
     */
    @Override
    public void updateCrawlingErrorUrls(SQSHtmlMetadata sqsHtmlMetadata) throws IOException {
        if(sqsHtmlMetadata.getStatus().is4xxClientError())  {
            urlMetadataRepository.updateFailedUrl(CrawlStatus.FAILED, sqsHtmlMetadata.getStatus().value(), sqsHtmlMetadata.getErrorMessage(), sqsHtmlMetadata.getUrlId());
        } else if (sqsHtmlMetadata.getStatus().is5xxServerError()) {
            Optional<UrlMetadata> urlMetadata = urlMetadataRepository.findById(sqsHtmlMetadata.getUrlId().toString());
            if(urlMetadata.isPresent()) {
                if(urlMetadata.get().getRetryCount() <= MAX_RETRY_COUNT) {
                    urlMetadataRepository.updateRetryCount(urlMetadata.get().getRetryCount()+1,sqsHtmlMetadata.getUrlId());
                    sendToSQSForCrawling(JSONUtils.convertObjectToString(new CrawlerUrlMetadata(sqsHtmlMetadata.getUrlId(), sqsHtmlMetadata.getUrl())));
                } else {
                    urlMetadataRepository.updateFailedUrl(CrawlStatus.FAILED, sqsHtmlMetadata.getStatus().value(), sqsHtmlMetadata.getErrorMessage(), sqsHtmlMetadata.getUrlId());
                }
            }
        }
    }

    /**
     * Insert new child urls in RDS and send to sqs for crawling.
     * Update pagerank of old child urls
     * @param inScopeChildUrls
     */
    public void sendChildUrlsForCrawling(List<String> inScopeChildUrls) throws IOException{
        for(String childUrl : inScopeChildUrls) {
            ArrayList<UrlMetadata> childUrlExists = urlMetadataRepository.findByUrl(childUrl);
            if(childUrlExists.isEmpty()) {
                UrlMetadata newUrl = UrlMetadata.builder().url(childUrl).crawlStatus(CrawlStatus.QUEUED).retryCount(0).pageRank(1).build();
                urlMetadataRepository.save(newUrl);
                sendToSQSForCrawling(JSONUtils.convertObjectToString(new CrawlerUrlMetadata(newUrl.getUrlId(), newUrl.getUrl())));
            } else {
                urlMetadataRepository.updatePagerank(childUrlExists.get(0).getPageRank() + 1, childUrl);
            }
        }
    }

    /**
     * Starts crawling new urls if they does not exist in the database
     * @param urls
     * @throws IOException
     */
    @Override
    public void insertNewUrlsForCrawling(List<String> urls) throws IOException {
        for(String childUrl : urls) {
            ArrayList<UrlMetadata> childUrlExists = urlMetadataRepository.findByUrl(childUrl);
            if(childUrlExists.isEmpty()) {
                UrlMetadata newUrl = UrlMetadata.builder().url(childUrl).crawlStatus(CrawlStatus.QUEUED).retryCount(0).pageRank(1).build();
                urlMetadataRepository.save(newUrl);
                sqsListener.sendMessage(JSONUtils.convertObjectToString(new CrawlerUrlMetadata(newUrl.getUrlId(), newUrl.getUrl())));
            }
        }
    }

    private void sendToSQSForCrawling(String message){
        if(CrawledUrlsCounter.canSendForCrawling()){
            sqsListener.sendMessage(message);
            CrawledUrlsCounter.incrementCrawledUrlCounter();
        }
    }
}
