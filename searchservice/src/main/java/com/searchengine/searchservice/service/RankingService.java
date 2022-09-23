package com.searchengine.searchservice.service;

import com.searchengine.searchservice.dto.SearchRequest;
import com.searchengine.searchservice.entity.UrlMapping;
import com.searchengine.searchservice.dto.UrlMetadataResponse;
import com.searchengine.searchservice.entity.UrlMetadata;

import java.util.List;
import java.util.Map;

/**
 * created by nikunjagarwal on 18-09-2022
 */
public interface RankingService {
    List<UrlMetadataResponse> rankResults(SearchRequest searchRequest, List<UrlMapping> dbSearchResults, Map<Long, UrlMetadata> urlMetadataMap);
}
