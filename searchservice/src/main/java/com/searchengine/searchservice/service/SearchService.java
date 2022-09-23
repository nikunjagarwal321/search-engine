package com.searchengine.searchservice.service;

import com.searchengine.searchservice.dto.SearchRequest;
import org.springframework.http.ResponseEntity;

/**
 * created by nikunjagarwal on 18-09-2022
 */
public interface SearchService {

    public ResponseEntity getSearchResults(SearchRequest searchRequest);
}
