package com.searchengine.searchservice.service;

import com.searchengine.searchservice.model.SearchRequest;
import com.searchengine.searchservice.model.UrlMapping;
import com.searchengine.searchservice.model.UrlMetadataResponse;

import java.util.List;

/**
 * created by nikunjagarwal on 18-09-2022
 */
public interface RankingService {
    List<UrlMetadataResponse> rankResults(SearchRequest searchRequest, List<UrlMapping> dbSearchResults);
}
