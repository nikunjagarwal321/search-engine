package com.searchengine.searchservice.entity;

import lombok.Data;

/**
 * created by nikunjagarwal on 18-09-2022
 */
@Data
public class SearchTermUrlMetadata {
    private Long urlId;
    private Long count;
    private Boolean isPresentInTitle;
    private Double customScore;
}
