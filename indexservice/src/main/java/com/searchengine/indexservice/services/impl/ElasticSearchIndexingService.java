package com.searchengine.indexservice.services.impl;

import com.searchengine.indexservice.dto.UrlMapping;
import com.searchengine.indexservice.dto.UrlMetadata;
import com.searchengine.indexservice.models.HtmlDocument;
import com.searchengine.indexservice.services.IndexingService;
import com.searchengine.indexservice.utils.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ElasticSearchIndexingService implements IndexingService {

    @Autowired
    IndexingHelper indexingHelper;

    @Autowired
    RestHighLevelClient client;

    @Value("${elasticsearch.index}")
    String index;


    /**
     * Creates inverted index and inserts the inverted index in DB
     * @param htmlDocument
     */
    @Override
    public void createAndInsertInvertedIndexInDB(HtmlDocument htmlDocument){
        HashMap<String, Long> bodyWordsWithCount = indexingHelper.getTokenizedFilteredStemmedWordsWithCount(htmlDocument.getBody());
        HashMap<String, Long> titleWords = indexingHelper.getTokenizedFilteredStemmedWordsWithCount(htmlDocument.getTitle());
        BulkRequest bulkRequest = new BulkRequest();
        for(Map.Entry<String, Long> wordWithCount : bodyWordsWithCount.entrySet()){
            UrlMapping urlMapping = new UrlMapping(
                    Collections.singletonList(
                            UrlMetadata.builder().
                                    url(htmlDocument.getUrl()).
                                    count(wordWithCount.getValue()).
                                    isPresentInTitle(titleWords.containsKey(wordWithCount.getKey())).
                                    build()
                    ));
            Map<String, Object> urlMappingMap = JSONUtils.convertObjectToObject(urlMapping, HashMap.class);
            Script script = new Script(ScriptType.INLINE, "painless","ctx._source.urls.add(params.urls)", urlMappingMap);
            UpdateRequest updateRequest = new UpdateRequest(index, wordWithCount.getKey()).upsert(urlMappingMap).script(script);
            bulkRequest.add(updateRequest);
        }
        log.info("Final Bulk request : {}", bulkRequest);
        //TODO : refactor the function, check the parsing of response
        try {
            BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("Exception : ", e);
        }

    }

}
