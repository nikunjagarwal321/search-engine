package com.searchengine.indexservice.services;

import com.searchengine.indexservice.models.HtmlDocument;


public interface IndexingService {

    void createAndInsertInvertedIndexInDB(HtmlDocument htmlDocument);
}
