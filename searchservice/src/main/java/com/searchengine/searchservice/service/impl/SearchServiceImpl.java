package com.searchengine.searchservice.service.impl;

import com.searchengine.searchservice.model.SearchRequest;
import com.searchengine.searchservice.model.SearchResponse;
import com.searchengine.searchservice.model.SearchTermUrlMetadata;
import com.searchengine.searchservice.model.UrlMetadataResponse;
import com.searchengine.searchservice.service.DatabaseSearchService;
import com.searchengine.searchservice.service.RankingService;
import com.searchengine.searchservice.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * created by nikunjagarwal on 18-09-2022
 */
@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private RankingService rankingService;

    @Autowired
    private DatabaseSearchService databaseSearchService;

    public ResponseEntity getSearchResults(SearchRequest searchRequest) {
        Map<String, List<SearchTermUrlMetadata>> dbSearchResults = databaseSearchService.getRelevantUrlsFromSearchTerms(searchRequest.getSearchTerm());
        List<UrlMetadataResponse> urlMetadataResponses = rankingService.rankResults(searchRequest, dbSearchResults);
        SearchResponse searchResponse = new SearchResponse(urlMetadataResponses);
        return new ResponseEntity<>(searchResponse, HttpStatus.OK);
    }
}
