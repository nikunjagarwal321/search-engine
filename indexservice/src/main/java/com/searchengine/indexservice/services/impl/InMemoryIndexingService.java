package com.searchengine.indexservice.services.impl;

import com.searchengine.indexservice.entity.SearchTermUrlMetadata;
import com.searchengine.indexservice.dto.HtmlDocument;
import com.searchengine.indexservice.services.IndexingService;
import com.searchengine.indexservice.utils.FileHandlerUtil;
import com.searchengine.indexservice.utils.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * created by nikunjagarwal on 17-09-2022
 */
@Service
@Slf4j
public class InMemoryIndexingService implements IndexingService {

    public static Map<String, List<SearchTermUrlMetadata>> globalInvertedIndex = new HashMap<>();

    @Autowired
    IndexingHelper indexingHelper;

    @Value("${inverted.index.filepath}")
    String invertedIndexFilepath;


    @Override
    public void createAndInsertInvertedIndexInDB(HtmlDocument htmlDocument) {
        Map<String, SearchTermUrlMetadata> invertedIndex = new HashMap<>();
        HashMap<String, Long> bodyWordsWithCount = indexingHelper.getTokenizedFilteredStemmedWordsWithCount(htmlDocument.getBody());
        HashMap<String, Long> titleWords = indexingHelper.getTokenizedFilteredStemmedWordsWithCount(htmlDocument.getTitle());
        for(Map.Entry<String, Long> wordWithCount : bodyWordsWithCount.entrySet()) {
            SearchTermUrlMetadata urlMetadata = SearchTermUrlMetadata.builder().
                    urlId(htmlDocument.getUrlId()).
                    count(wordWithCount.getValue()).
                    isPresentInTitle(titleWords.containsKey(wordWithCount.getKey())).
                    build();
            invertedIndex.put(wordWithCount.getKey(), urlMetadata);
        }
        updateGlobalInvertedIndex(invertedIndex);
    }

    private void updateGlobalInvertedIndex(Map<String, SearchTermUrlMetadata> invertedIndex) {
        try{
            for(Map.Entry<String, SearchTermUrlMetadata> wordAndMetadata : invertedIndex.entrySet()) {
                if(globalInvertedIndex.containsKey(wordAndMetadata.getKey())) {
                    globalInvertedIndex.get(wordAndMetadata.getKey()).add(wordAndMetadata.getValue());
                } else {
                    List<SearchTermUrlMetadata> newSearchTermUrlList = new ArrayList<>();
                    newSearchTermUrlList.add(wordAndMetadata.getValue());
                    globalInvertedIndex.put(wordAndMetadata.getKey(), newSearchTermUrlList);
                }
            }
            FileHandlerUtil.writeToFile(invertedIndexFilepath, JSONUtils.convertObjectToString(globalInvertedIndex));
        } catch (Exception e) {
            log.error("Error : ", e);
        }
       }
}
