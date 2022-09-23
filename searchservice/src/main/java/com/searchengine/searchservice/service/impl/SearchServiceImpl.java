package com.searchengine.searchservice.service.impl;

import com.searchengine.searchservice.constants.DBType;
import com.searchengine.searchservice.dto.*;
import com.searchengine.searchservice.entity.SearchTermUrlMetadata;
import com.searchengine.searchservice.entity.UrlMapping;
import com.searchengine.searchservice.entity.UrlMetadata;
import com.searchengine.searchservice.repository.UrlMetadataRepository;
import com.searchengine.searchservice.service.RankingService;
import com.searchengine.searchservice.service.SearchService;
import com.searchengine.searchservice.service.helper.IndexingHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
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
    private DBSearchRepoFactory databaseSearchServiceFactory;

    @Autowired
    private UrlMetadataRepository urlMetadataRepository;

    /**
     * This function does the following steps:
     * Step 1: Formats search term query with the same algo used in indexing as we want to match the results
     * Step 2: Fetches the relevant urls from the inverted index database
     * Step 3: Ranks the urls and returns the results
     * @param searchRequest
     * @return
     */
    public ResponseEntity getSearchResults(SearchRequest searchRequest) {
        log.info("Received search request : {}", searchRequest);
        HashMap<String, Long> searchTermsWithCount = indexingHelper.getTokenizedFilteredStemmedWordsWithCount(searchRequest.getSearchTerm());
        List<String> searchTerms = new ArrayList<>(searchTermsWithCount.keySet());
        List<UrlMapping> dbSearchResults = databaseSearchServiceFactory.getDatabaseSearchService(DBType.MONGO_DB).
                getRelevantUrlsFromSearchTerms(searchTerms);
        Map<Long, UrlMetadata> urlMetadataMap = getRelevantUrlsMetadata(dbSearchResults);
        List<UrlMetadataResponse> urlMetadataResponses = rankingService.rankResults(searchRequest, dbSearchResults, urlMetadataMap);
        SearchResponse searchResponse = new SearchResponse(urlMetadataResponses);
        return new ResponseEntity<>(searchResponse, HttpStatus.OK);
    }

    private Map<Long, UrlMetadata> getRelevantUrlsMetadata(List<UrlMapping> urlMappingList) {
        Set<Long> relevantUrlIds = new HashSet<>();
        for(UrlMapping urlMapping: urlMappingList) {
            relevantUrlIds = urlMapping.getUrls().stream().map(SearchTermUrlMetadata::getUrlId).collect(Collectors.toSet());
        }
        List<UrlMetadata> urlMetadataList = urlMetadataRepository.findByUrlIdIn(relevantUrlIds);
        return urlMetadataList.stream().collect(Collectors.toMap(UrlMetadata::getUrlId, Function.identity()));
    }
}
