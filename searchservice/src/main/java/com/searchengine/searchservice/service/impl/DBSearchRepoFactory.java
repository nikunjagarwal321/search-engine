package com.searchengine.searchservice.service.impl;

import com.searchengine.searchservice.constants.DBType;
import com.searchengine.searchservice.repository.InvertedIndexSearchRepo;
import com.searchengine.searchservice.repository.FileDBSearchRepo;
import com.searchengine.searchservice.repository.MongoDBSearchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by nikunjagarwal on 21-09-2022
 */
@Service
public class DBSearchRepoFactory {

    @Autowired
    private FileDBSearchRepo fileDatabaseSearchService;

    @Autowired
    private MongoDBSearchRepo mongoDatabaseSearchService;

    public InvertedIndexSearchRepo getDatabaseSearchService(DBType dbType) {
        switch (dbType) {
            case FILE_BASED:
                return fileDatabaseSearchService;
            case MONGO_DB:
                return mongoDatabaseSearchService;
        }
        return mongoDatabaseSearchService;
    }
}
