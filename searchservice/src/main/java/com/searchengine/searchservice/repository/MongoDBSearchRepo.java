package com.searchengine.searchservice.repository;

import com.searchengine.searchservice.entity.UrlMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by nikunjagarwal on 21-09-2022
 */
@Slf4j
@Service
public class MongoDBSearchRepo implements InvertedIndexSearchRepo {

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
