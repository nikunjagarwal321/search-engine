package com.searchengine.indexservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * created by nikunjagarwal on 01-09-2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("search_mapping")
public class UrlMapping {
    private String searchTerm;
    private List<SearchTermUrlMetadata> urls;
}
