package com.searchengine.searchservice.service;

import com.searchengine.searchservice.model.UrlMapping;

import java.util.List;

/**
 * created by nikunjagarwal on 18-09-2022
 */
public interface DatabaseSearchService {
    List<UrlMapping> getRelevantUrlsFromSearchTerms(List<String> searchTerms);
}
