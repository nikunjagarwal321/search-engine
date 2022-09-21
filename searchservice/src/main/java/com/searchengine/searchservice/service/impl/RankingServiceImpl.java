package com.searchengine.searchservice.service.impl;

import com.searchengine.searchservice.model.*;
import com.searchengine.searchservice.service.RankingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * created by nikunjagarwal on 18-09-2022
 */
@Slf4j
@Service
public class RankingServiceImpl implements RankingService {

    @Override
    public List<UrlMetadataResponse> rankResults(SearchRequest searchRequest, List<UrlMapping> dbSearchResults) {
        log.info("Started Ranking results");
        List<UrlMetadataResponse> urlMetadataResponses = new ArrayList<>();
        List<UrlRankingMetadata> urlRankingMetadataList = getCombinedUrlRankingMetadata(dbSearchResults);
        updateWordCountRank(urlRankingMetadataList);
        updateTitlePresenceCountRank(urlRankingMetadataList);
        updatePagerankRank(urlRankingMetadataList);
        computeWeightedRank(urlRankingMetadataList);
        urlRankingMetadataList.sort(Comparator.comparing(UrlRankingMetadata::getWeightedRank));
        log.info("Urls after computing weighted rank and sorting : {}", urlRankingMetadataList);
        for(UrlRankingMetadata urlRankingMetadata: urlRankingMetadataList) {
            urlMetadataResponses.add(
                    UrlMetadataResponse.builder().
                            url(urlRankingMetadata.getUrlId().toString()).
                            rankingScore(urlRankingMetadata.getWeightedRank()).
                            build()
            );
        }
        log.info("Final Response :{}", urlMetadataResponses);
        return urlMetadataResponses;
    }

    /**
     * Since, we can get multiple search terms in query, this function combines the urls result for multiple search terms
     * @param dbSearchResults
     * @return
     */
    private List<UrlRankingMetadata> getCombinedUrlRankingMetadata(List<UrlMapping> dbSearchResults) {
        log.info("Getting Combined Url Metadata");
        Map<Long, UrlRankingMetadata> urlRankingMetadataMap = new HashMap<>();
        for (UrlMapping urlMapping: dbSearchResults) {
            for (SearchTermUrlMetadata searchTermUrlMetadata: urlMapping.getUrls()) {
                UrlRankingMetadata urlRankingMetadata;
                //can also update page rank here in future
                if(urlRankingMetadataMap.containsKey(searchTermUrlMetadata.getUrlId())) {
                    urlRankingMetadata = urlRankingMetadataMap.get(searchTermUrlMetadata.getUrlId());
                    urlRankingMetadata.setWordCount(urlRankingMetadata.getWordCount() + searchTermUrlMetadata.getCount());
                    if(searchTermUrlMetadata.getIsPresentInTitle())
                        urlRankingMetadata.setTitlePresenceCount(urlRankingMetadata.getTitlePresenceCount() + 1);
                } else {
                    urlRankingMetadata = UrlRankingMetadata.builder().
                            urlId(searchTermUrlMetadata.getUrlId()).
                            wordCount(searchTermUrlMetadata.getCount()).
                            titlePresenceCount(searchTermUrlMetadata.getIsPresentInTitle() ? (long) 1 : 0).
                            pageRankCount((long)0). //TODO: change in future
                            build();
                }
                urlRankingMetadataMap.put(urlRankingMetadata.getUrlId(), urlRankingMetadata);
            }
        }
        return new ArrayList<>(urlRankingMetadataMap.values());
    }

    private void updateWordCountRank(List<UrlRankingMetadata> urlRankingMetadataList) {
        log.info("Updating Word Count Rank for the urls");
        urlRankingMetadataList.sort(Comparator.comparing(UrlRankingMetadata::getWordCount).reversed());
        int lastWordCountValue = Integer.MAX_VALUE, currentWordRank = 0;
        for (UrlRankingMetadata urlRankingMetadata : urlRankingMetadataList) {
            if (lastWordCountValue > urlRankingMetadata.getWordCount()) {
                lastWordCountValue = urlRankingMetadata.getWordCount().intValue();
                currentWordRank++;
            }
            urlRankingMetadata.setWordCountRank((long) (currentWordRank));
        }
    }

    private void updatePagerankRank(List<UrlRankingMetadata> urlRankingMetadataList) {
        log.info("Updating Pagerank Rank for the urls");
        urlRankingMetadataList.sort(Comparator.comparing(UrlRankingMetadata::getPageRankCount).reversed());
        int lastPageRankValue = Integer.MAX_VALUE, currentPagerankRank = 0;
        for (UrlRankingMetadata urlRankingMetadata : urlRankingMetadataList) {
            if (lastPageRankValue > urlRankingMetadata.getPageRankCount()) {
                lastPageRankValue = urlRankingMetadata.getPageRankCount().intValue();
                currentPagerankRank++;
            }
            urlRankingMetadata.setPageRankCountRank((long) (currentPagerankRank));
        }
    }

    private void updateTitlePresenceCountRank(List<UrlRankingMetadata> urlRankingMetadataList) {
        log.info("Updating TitlePresenceCount Rank for the urls");
        urlRankingMetadataList.sort(Comparator.comparing(UrlRankingMetadata::getTitlePresenceCount).reversed());
        int lastTitlePresenceCountValue = Integer.MAX_VALUE, currentTitlePresenceRank = 0;
        for (UrlRankingMetadata urlRankingMetadata : urlRankingMetadataList) {
            if (lastTitlePresenceCountValue > urlRankingMetadata.getTitlePresenceCount()) {
                lastTitlePresenceCountValue = urlRankingMetadata.getTitlePresenceCount().intValue();
                currentTitlePresenceRank++;
            }
            urlRankingMetadata.setTitlePresenceCountRank((long) (currentTitlePresenceRank));
        }
    }

    private void computeWeightedRank(List<UrlRankingMetadata> urlRankingMetadataList) {
        log.info("Computing final weighted rank for the given set of urls = {}", urlRankingMetadataList);
        for (UrlRankingMetadata urlRankingMetadata : urlRankingMetadataList) {
            Double weightRank = 0.5 * urlRankingMetadata.getTitlePresenceCountRank() +
                    1.0 * urlRankingMetadata.getWordCountRank() +
                    1.2 * urlRankingMetadata.getPageRankCountRank();
            urlRankingMetadata.setWeightedRank(weightRank);
        }
    }
}
