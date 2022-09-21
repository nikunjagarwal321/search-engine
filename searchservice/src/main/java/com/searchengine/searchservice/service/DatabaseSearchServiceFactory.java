package com.searchengine.searchservice.service;

import com.searchengine.searchservice.constants.DBType;
import com.searchengine.searchservice.service.impl.FileDatabaseSearchService;
import com.searchengine.searchservice.service.impl.MongoDatabaseSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by nikunjagarwal on 21-09-2022
 */
@Service
public class DatabaseSearchServiceFactory {

    @Autowired
    private FileDatabaseSearchService fileDatabaseSearchService;

    @Autowired
    private MongoDatabaseSearchService mongoDatabaseSearchService;

    public DatabaseSearchService getDatabaseSearchService(DBType dbType) {
        switch (dbType) {
            case FILE_BASED:
                return fileDatabaseSearchService;
            case MONGO_DB:
                return mongoDatabaseSearchService;
        }
        return mongoDatabaseSearchService;
    }
}
