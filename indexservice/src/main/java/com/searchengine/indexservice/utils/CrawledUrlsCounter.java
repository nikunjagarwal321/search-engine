package com.searchengine.indexservice.utils;

import com.searchengine.indexservice.constants.Constants;
import org.springframework.stereotype.Service;


/**
 * created by nikunjagarwal on 23-09-2022
 */
@Service
public class CrawledUrlsCounter {
    private static int crawledUrlCounter = 0;

    public static int getCrawledUrlCounter() {
        return crawledUrlCounter;
    }

    public static void incrementCrawledUrlCounter() {
        crawledUrlCounter++;
    }

    public static boolean canSendForCrawling() {
        if(crawledUrlCounter < Constants.MAX_NUMBER_OF_CRAWLED_PAGES)
            return true;
        else
            return false;
    }
}
