package com.searchengine.searchservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.searchengine.searchservice.model.SearchTermUrlMetadata;
import com.searchengine.searchservice.util.FileHandlerUtil;
import com.searchengine.searchservice.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by nikunjagarwal on 18-09-2022
 */
@Service
public class FileDatabaseSearchService implements DatabaseSearchService{

    @Autowired
    private IndexingHelper indexingHelper;

    private Map<String, List<SearchTermUrlMetadata>> globalSearchIndex;

    FileDatabaseSearchService(@Value("${inverted.index.filepath}") String filePath) throws IOException {
        String invertedIndexString = FileHandlerUtil.readFromFile(filePath);
        this.globalSearchIndex = JsonUtils.convertStringToObject(invertedIndexString, new TypeReference<Map<String, List<SearchTermUrlMetadata>>>() {});
    }

    @Override
    public Map<String, List<SearchTermUrlMetadata>> getRelevantUrlsFromSearchTerms(String searchQuery) {
        HashMap<String, Long>  searchTermsWithCount = indexingHelper.getTokenizedFilteredStemmedWordsWithCount(searchQuery);
        Map<String, List<SearchTermUrlMetadata>> relevantUrlAndSearchTerms = new HashMap<>();
        for (Map.Entry<String, Long> searchTerm : searchTermsWithCount.entrySet()) {
            List<SearchTermUrlMetadata> relevantUrls = globalSearchIndex.get(searchTerm.getKey());
            relevantUrlAndSearchTerms.put(searchTerm.getKey(), relevantUrls);
        }
        return relevantUrlAndSearchTerms;
    }
}
