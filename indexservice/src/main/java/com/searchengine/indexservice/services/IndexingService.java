package com.searchengine.indexservice.services;

import java.util.HashMap;

public interface IndexingService {

    HashMap<String, Integer>  createIndex(String document);
}
