package com.searchengine.indexservice.services;

import com.searchengine.indexservice.dto.HtmlDocument;


public interface IndexingService {

    void createAndInsertInvertedIndexInDB(HtmlDocument htmlDocument);
}
