package com.searchengine.searchservice.service;

import com.searchengine.searchservice.model.SearchRequest;
import com.searchengine.searchservice.model.SearchTermUrlMetadata;
import com.searchengine.searchservice.model.UrlMetadataResponse;

import java.util.List;
import java.util.Map;

/**
 * created by nikunjagarwal on 18-09-2022
 */
public interface RankingService {
    public List<UrlMetadataResponse> rankResults(SearchRequest searchRequest, Map<String, List<SearchTermUrlMetadata>> dbSearchResults);
}
