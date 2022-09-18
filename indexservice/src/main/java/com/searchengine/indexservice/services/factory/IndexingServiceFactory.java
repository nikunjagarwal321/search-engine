package com.searchengine.indexservice.services.factory;

import com.searchengine.indexservice.constants.IndexingServiceEnum;
import com.searchengine.indexservice.services.IndexingService;
import com.searchengine.indexservice.services.impl.ElasticSearchIndexingService;
import com.searchengine.indexservice.services.impl.InMemoryIndexingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by nikunjagarwal on 02-09-2022
 */
@Service
public class IndexingServiceFactory {

    @Autowired
    ElasticSearchIndexingService elasticSearchIndexingService;

    @Autowired
    InMemoryIndexingService inMemoryIndexingService;

    public IndexingService getIndexingService(IndexingServiceEnum indexingService) {
        switch (indexingService) {
            case ELASTIC_SEARCH:
                return elasticSearchIndexingService;
            case FILE_BASED:
                return inMemoryIndexingService;
        }
        return elasticSearchIndexingService;
    }
}
