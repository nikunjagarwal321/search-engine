package com.searchengine.indexservice.services.factory;

import com.searchengine.indexservice.constants.IndexingServiceEnum;
import com.searchengine.indexservice.services.IndexingService;
import com.searchengine.indexservice.services.impl.InMemoryIndexingService;
import com.searchengine.indexservice.services.impl.MongoDBIndexingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by nikunjagarwal on 02-09-2022
 */
@Service
public class IndexingServiceFactory {

    @Autowired
    InMemoryIndexingService inMemoryIndexingService;

    @Autowired
    MongoDBIndexingService mongoDBIndexingService;

    public IndexingService getIndexingService(IndexingServiceEnum indexingService) {
        switch (indexingService) {
            case FILE_BASED:
                return inMemoryIndexingService;
            case MONGO_DB:
                return mongoDBIndexingService;
        }
        return mongoDBIndexingService;
    }
}
