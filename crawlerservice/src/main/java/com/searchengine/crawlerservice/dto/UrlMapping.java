package com.searchengine.crawlerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * created by nikunjagarwal on 01-09-2022
 */
@Data
@AllArgsConstructor
public class UrlMapping {

    public UrlMapping() {
        this.urls = new ArrayList<>();
    }

    private List<CrawlRequest> urls;
}
