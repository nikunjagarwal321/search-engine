package com.searchengine.searchservice.service.impl;

import com.searchengine.searchservice.model.SearchRequest;
import com.searchengine.searchservice.model.SearchTermUrlMetadata;
import com.searchengine.searchservice.model.UrlMetadataResponse;
import com.searchengine.searchservice.service.RankingService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * created by nikunjagarwal on 18-09-2022
 */
@Service
public class RankingServiceImpl implements RankingService {
    @Override
    public List<UrlMetadataResponse> rankResults(SearchRequest searchRequest, Map<String, List<SearchTermUrlMetadata>> dbSearchResults) {
        List<UrlMetadataResponse> urlMetadataResponses = new ArrayList<>();
        for(Map.Entry<String, List<SearchTermUrlMetadata>> dbSearchTermResult : dbSearchResults.entrySet()) {
            List<SearchTermUrlMetadata> searchTermRelevantUrls = dbSearchTermResult.getValue();
            for(SearchTermUrlMetadata searchTermUrlMetadata : searchTermRelevantUrls) {
                urlMetadataResponses.add(new UrlMetadataResponse(searchTermUrlMetadata.getUrl(), null, 1));
            }
        }
        return urlMetadataResponses;
    }
}
