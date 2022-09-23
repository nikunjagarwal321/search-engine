package com.searchengine.searchservice.controller;

import com.searchengine.searchservice.dto.SearchRequest;
import com.searchengine.searchservice.service.impl.SearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by nikunjagarwal on 18-09-2022
 */
@RestController
@RequestMapping("/api/v1")
public class Controller {

    @Autowired
    private SearchServiceImpl searchService;

    @PostMapping("/search")
    public ResponseEntity getSearchResults(@RequestBody SearchRequest searchRequest) {
        return searchService.getSearchResults(searchRequest);
    }
}
