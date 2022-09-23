package com.searchengine.indexservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * created by nikunjagarwal on 01-09-2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HtmlDocument {
    private Long urlId;
    private String url;
    private String title;
    private String body;
    private Set<String> childUrls;
}
