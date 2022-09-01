package com.searchengine.indexservice.services.impl;

import com.searchengine.indexservice.models.HtmlDocument;
import com.searchengine.indexservice.services.IndexingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ElasticSearchIndexingService implements IndexingService {

    @Autowired
    IndexingHelper indexingHelper;


    /**
     * Creates inverted index and inserts the inverted index in DB
     * @param htmlDocument
     */
    @Override
    public void createAndInsertInvertedIndexInDB(HtmlDocument htmlDocument){
        HashMap<String, Integer> bodyWordWithCount = indexingHelper.getTokenizedFilteredStemmedWordsWithCount(htmlDocument.getBody());
        HashMap<String, Integer> titleWordWithCount = indexingHelper.getTokenizedFilteredStemmedWordsWithCount(htmlDocument.getTitle());
    }

}
