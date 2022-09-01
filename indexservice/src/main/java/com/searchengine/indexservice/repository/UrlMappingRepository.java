package com.searchengine.indexservice.repository;

import com.searchengine.indexservice.dto.UrlMapping;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * created by nikunjagarwal on 01-09-2022
 */
@Repository
public interface UrlMappingRepository extends ElasticsearchRepository<UrlMapping, String> {
}
