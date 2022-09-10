package com.searchengine.crawlerservice.worker;

import com.searchengine.crawlerservice.dto.CrawlRequest;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Slf4j
public class CrawlWorker implements Runnable {

    final CrawlRequest crawlRequest;

    final String User_Agent_Mozilla = "Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:2.2) Gecko/20110201";

    public CrawlWorker(CrawlRequest crawlRequest) {
        this.crawlRequest = crawlRequest;
    }

    @Override
    public void run() {

        int tryCount = 0;
        while (tryCount < 3) {
            try {
                tryCount++;
                Connection.Response response = Jsoup.connect(crawlRequest.getUrl())
                        .userAgent(User_Agent_Mozilla)
                        .execute();

                Document doc = response.parse();
                if (doc.body().toString().length() < 500) {
                    log.info("Retrying page {} ", crawlRequest.getUrl());
                    // Retrying if the page is partial after exponential waiting time EG: 4secs, 8secs, 16 secs
                    Thread.sleep((long) (2000 * Math.pow(2, tryCount)));
                }

                if (response.statusCode() == 200) {
                    //TODO: Add more checks to verify partial pages
                    log.info("Url Crawled Successfully: {}", crawlRequest.getUrl());
                    return;
                } else {
                    log.info("Error in crawling : {} ", crawlRequest.getUrl());
                    // Retrying if page found doesn't have success code
                    Thread.sleep((long) (2000 * Math.pow(2, tryCount)));
                }

            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
}
