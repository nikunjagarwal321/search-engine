package com.searchengine.searchservice.repository;

import com.searchengine.searchservice.entity.UrlMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * created by nikunjagarwal on 24-09-2022
 */
@Repository
public interface UrlMetadataRepository extends JpaRepository<UrlMetadata, Long> {
    List<UrlMetadata> findByUrlIdIn(Set<Long> urlIds);
}
