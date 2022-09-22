package com.searchengine.indexservice.services.impl;

import com.mongodb.bulk.BulkWriteResult;
import com.searchengine.indexservice.dto.SearchTermUrlMetadata;
import com.searchengine.indexservice.dto.UrlMapping;
import com.searchengine.indexservice.models.HtmlDocument;
import com.searchengine.indexservice.services.IndexingService;
import com.searchengine.indexservice.services.helper.IndexingHelper;
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

    @Override
    public void createAndInsertInvertedIndexInDB(HtmlDocument htmlDocument) {
        HashMap<String, Long> bodyWordsWithCount = indexingHelper.getTokenizedFilteredStemmedWordsWithCount(htmlDocument.getBody());
        HashMap<String, Long> titleWords = indexingHelper.getTokenizedFilteredStemmedWordsWithCount(htmlDocument.getTitle());
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
