package com.searchengine.searchservice.dto;

import lombok.Data;

/**
 * created by nikunjagarwal on 18-09-2022
 */
@Data
public class SearchRequest {
    private String searchTerm;
    private Integer pageSize;
    private Integer offset;
}
