package com.searchengine.indexservice.services;

import com.searchengine.indexservice.models.HtmlDocument;

/**
 * created by nikunjagarwal on 01-09-2022
 */
public interface UrlsHandlerService {
    public void insertChildUrlsInRdsAndSqs(HtmlDocument htmlDocument);
}
