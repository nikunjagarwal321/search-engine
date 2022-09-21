package com.searchengine.searchservice.service.impl;

import com.searchengine.searchservice.constants.DBType;
import com.searchengine.searchservice.model.*;
import com.searchengine.searchservice.service.DatabaseSearchService;
import com.searchengine.searchservice.service.DatabaseSearchServiceFactory;
import com.searchengine.searchservice.service.RankingService;
import com.searchengine.searchservice.service.SearchService;
import com.searchengine.searchservice.service.helper.IndexingHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * created by nikunjagarwal on 18-09-2022
 */
@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private IndexingHelper indexingHelper;

    @Autowired
    private RankingService rankingService;

    @Autowired
    private DatabaseSearchServiceFactory databaseSearchServiceFactory;

    public ResponseEntity getSearchResults(SearchRequest searchRequest) {
        log.info("Received search request : {}", searchRequest);
        HashMap<String, Long> searchTermsWithCount = indexingHelper.getTokenizedFilteredStemmedWordsWithCount(searchRequest.getSearchTerm());
        List<String> searchTerms = new ArrayList<>(searchTermsWithCount.keySet());
        List<UrlMapping> dbSearchResults = databaseSearchServiceFactory.getDatabaseSearchService(DBType.MONGO_DB).
                getRelevantUrlsFromSearchTerms(searchTerms);
        List<UrlMetadataResponse> urlMetadataResponses = rankingService.rankResults(searchRequest, dbSearchResults);
        SearchResponse searchResponse = new SearchResponse(urlMetadataResponses);
        return new ResponseEntity<>(searchResponse, HttpStatus.OK);
    }
}
