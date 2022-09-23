package com.searchengine.searchservice.repository;

import com.searchengine.searchservice.entity.UrlMapping;

import java.util.List;

/**
 * created by nikunjagarwal on 18-09-2022
 */
public interface InvertedIndexSearchRepo {
    List<UrlMapping> getRelevantUrlsFromSearchTerms(List<String> searchTerms);
}
