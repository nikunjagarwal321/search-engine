package com.searchengine.searchservice.service.impl;

import com.searchengine.searchservice.model.SearchTermUrlMetadata;
import com.searchengine.searchservice.model.UrlMapping;
import com.searchengine.searchservice.service.DatabaseSearchService;
import com.searchengine.searchservice.service.helper.IndexingHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * created by nikunjagarwal on 21-09-2022
 */
@Slf4j
@Service
public class MongoDatabaseSearchService implements DatabaseSearchService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<UrlMapping> getRelevantUrlsFromSearchTerms(List<String> searchTerms) {
        log.info("Searching in Mongo DB for stemmed searchTerms={}", searchTerms);
        Query query = Query.query(Criteria.where("searchTerm").in(searchTerms));
        List<UrlMapping> searchResults = mongoTemplate.find(query, UrlMapping.class);
        log.info("Found {} search terms, Search results : {}", searchResults.size(), searchResults);
        return searchResults;
    }
}
