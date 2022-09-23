package com.searchengine.indexservice.utils;

import com.searchengine.indexservice.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * created by nikunjagarwal on 23-09-2022
 */
@Slf4j
@Service
public class CrawledUrlsCounter {
    private static int crawledUrlCounter = 0;

    public static void incrementCrawledUrlCounter() {
        crawledUrlCounter++;
        log.info("Total number of pages sent for crawling : {}", crawledUrlCounter);
    }

    public static boolean canSendForCrawling() {
        if(crawledUrlCounter < Constants.MAX_NUMBER_OF_CRAWLED_PAGES)
            return true;
        else
            return false;
    }
}
