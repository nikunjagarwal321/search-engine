package com.searchengine.searchservice.repository;

import com.searchengine.searchservice.entity.SearchTermUrlMetadata;
import com.searchengine.searchservice.entity.UrlMapping;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * created by nikunjagarwal on 18-09-2022
 */
@Service
public class FileDBSearchRepo implements InvertedIndexSearchRepo {


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
