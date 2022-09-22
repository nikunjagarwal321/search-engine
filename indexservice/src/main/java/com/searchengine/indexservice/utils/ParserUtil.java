package com.searchengine.indexservice.utils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * created by nikunjagarwal on 19-09-2022
 */
@Service
public class ParserUtil {

    public Set<String> getChildUrls(Document document) {
        Elements links = document.select("a[href]");
        Set<String> hrefs = new HashSet<>();
        for (Element link: links) {
            hrefs.add(link.attr("href"));
        }
        return hrefs;
    }
}
