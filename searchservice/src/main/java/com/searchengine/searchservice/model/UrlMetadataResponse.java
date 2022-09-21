package com.searchengine.searchservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * created by nikunjagarwal on 18-09-2022
 */
@Data
@Builder
@AllArgsConstructor
public class UrlMetadataResponse {
    private String url;
    private String urlTitle;
    private Integer rank;
    private Double rankingScore;
}
