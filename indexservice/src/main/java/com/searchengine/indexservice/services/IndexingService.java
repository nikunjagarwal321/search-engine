package com.searchengine.indexservice.services;

import com.searchengine.indexservice.models.HtmlDocument;
import com.searchengine.indexservice.models.SQSHtmlMetadata;

import java.util.HashMap;

public interface IndexingService {

    void createAndInsertInvertedIndexInDB(SQSHtmlMetadata sqsHtmlMetadata, HtmlDocument htmlDocument);

    HashMap<String, Integer> createInvertedIndexFromDocument(String document);
}
