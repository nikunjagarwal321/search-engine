package com.searchengine.searchservice.service;

import com.searchengine.searchservice.model.SearchTermUrlMetadata;

import java.util.List;
import java.util.Map;

/**
 * created by nikunjagarwal on 18-09-2022
 */
public interface DatabaseSearchService {
    public Map<String, List<SearchTermUrlMetadata>> getRelevantUrlsFromSearchTerms(String searchQuery);
}
