package com.searchengine.searchservice.model;

import lombok.Data;

/**
 * created by nikunjagarwal on 18-09-2022
 */
@Data
public class SearchTermUrlMetadata {
    private String url;
    private Long count;
    private Boolean isPresentInTitle;
    private Double customScore;
}
