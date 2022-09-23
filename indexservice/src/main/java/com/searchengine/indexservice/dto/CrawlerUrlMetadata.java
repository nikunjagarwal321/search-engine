package com.searchengine.indexservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by nikunjagarwal on 23-09-2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrawlerUrlMetadata {
    private Long urlId;
    private String url;
}
