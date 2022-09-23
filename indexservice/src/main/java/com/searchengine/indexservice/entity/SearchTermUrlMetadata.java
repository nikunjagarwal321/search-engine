package com.searchengine.indexservice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchTermUrlMetadata {
    private Long urlId;
    private Long count;
    private Boolean isPresentInTitle;
    private Double customScore;

}
