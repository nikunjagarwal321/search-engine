package com.searchengine.indexservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * created by nikunjagarwal on 01-09-2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HtmlDocument {
    private String urlId;
    private String url;
    private String title;
    private String body;
    private List<String> childUrls;
}
