package com.searchengine.indexservice.services.impl;

import com.mongodb.bulk.BulkWriteResult;
import com.searchengine.indexservice.entity.SearchTermUrlMetadata;
import com.searchengine.indexservice.entity.UrlMapping;
import com.searchengine.indexservice.dto.HtmlDocument;
import com.searchengine.indexservice.services.IndexingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * created by nikunjagarwal on 21-09-2022
 */
@Slf4j
@Service
public class MongoDBIndexingService implements IndexingService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    IndexingHelper indexingHelper;

    /**
     * Creates inverted index and inserts in mongo database
     * Step 1: Tokenize html, filter and stem words
     * Step 2: Iterate over each word and create bulk request which will append urls if word is already present or insert if new word
     * Step 3: Execute bulk request
     * @param htmlDocument
     */
    @Override
    public void createAndInsertInvertedIndexInDB(HtmlDocument htmlDocument) {
        HashMap<String, Long> bodyWordsWithCount = indexingHelper.getTokenizedFilteredStemmedWordsWithCount(htmlDocument.getBody());
        HashMap<String, Long> titleWords = indexingHelper.getTokenizedFilteredStemmedWordsWithCount(htmlDocument.getTitle());
        //TODO: Error Handling
        BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, UrlMapping.class);
        for(Map.Entry<String, Long> wordWithCount : bodyWordsWithCount.entrySet()){
            Query searchTermQuery = Query.query(Criteria.where("searchTerm").is(wordWithCount.getKey()));
            SearchTermUrlMetadata searchTermUrlMetadata = SearchTermUrlMetadata.builder().
                                    urlId(htmlDocument.getUrlId()).
                                    count(wordWithCount.getValue()).
                                    isPresentInTitle(titleWords.containsKey(wordWithCount.getKey())).
                                    build();
            bulkOps.upsert(searchTermQuery, new Update().push("urls", searchTermUrlMetadata));
        }
        log.info("Executing Bulk request : {}",  bulkOps);
        BulkWriteResult bulkWriteResult  = bulkOps.execute();
        log.info("Upsertion executed in bulk; updations={}, insertions={}", bulkWriteResult.getModifiedCount(), bulkWriteResult.getUpserts().stream().count());
    }
}
