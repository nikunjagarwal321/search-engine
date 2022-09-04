package com.searchengine.crawlerservice.worker;

import com.searchengine.crawlerservice.dto.CrawlRequest;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Slf4j
public class CrawlWorker implements Runnable{

    final CrawlRequest crawlRequest;

    public CrawlWorker(CrawlRequest crawlRequest) {
        this.crawlRequest = crawlRequest;
    }

    @Override
    public void run () {
        try {
            Document doc = Jsoup.connect(crawlRequest.getUrl()).get();
            if (doc.childNodeSize() > 50) {
                log.info("Crawled url: {} title {}",crawlRequest.getUrl(), doc.title());

            } else {
                log.info("Partial page");
            }

        } catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
