package com.searchengine.searchservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * created by nikunjagarwal on 18-09-2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {
    private List<UrlMetadataResponse> response;
}
