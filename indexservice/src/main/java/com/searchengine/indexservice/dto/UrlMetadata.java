package com.searchengine.indexservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by nikunjagarwal on 01-09-2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UrlMetadata {
    //TODO: Change this to url id post integration with RDS
    private String url;
    private Long count;
    private Boolean isPresentInTitle;
    private Double customScore;

}
