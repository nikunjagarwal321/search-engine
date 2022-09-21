package com.searchengine.searchservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * created by nikunjagarwal on 21-09-2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("search_mapping")
public class UrlMapping {
    @Indexed
    private String searchTerm;
    private List<SearchTermUrlMetadata> urls;
}