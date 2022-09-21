package com.searchengine.searchservice.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.searchengine.searchservice.model.SearchTermUrlMetadata;
import com.searchengine.searchservice.model.UrlMapping;
import com.searchengine.searchservice.service.DatabaseSearchService;
import com.searchengine.searchservice.service.helper.IndexingHelper;
import com.searchengine.searchservice.util.FileHandlerUtil;
import com.searchengine.searchservice.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by nikunjagarwal on 18-09-2022
 */
@Service
public class FileDatabaseSearchService implements DatabaseSearchService {


    private Map<String, List<SearchTermUrlMetadata>> globalSearchIndex;

    //Have commented it out since we'll be using mongo db
//    FileDatabaseSearchService(@Value("${inverted.index.filepath}") String filePath) throws IOException {
//        String invertedIndexString = FileHandlerUtil.readFromFile(filePath);
//        this.globalSearchIndex = JsonUtils.convertStringToObject(invertedIndexString, new TypeReference<Map<String, List<SearchTermUrlMetadata>>>() {});
//    }

    @Override
    public List<UrlMapping> getRelevantUrlsFromSearchTerms(List<String> searchTerms) {
        List<UrlMapping> relevantUrlAndSearchTerms = new ArrayList<>();
        for (String searchTerm : searchTerms) {
            List<SearchTermUrlMetadata> relevantUrls = globalSearchIndex.get(searchTerm);
            relevantUrlAndSearchTerms.add(new UrlMapping(searchTerm, relevantUrls));
        }
        return relevantUrlAndSearchTerms;
    }
}
