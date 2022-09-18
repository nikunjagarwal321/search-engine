package com.searchengine.indexservice.utils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * created by nikunjagarwal on 19-09-2022
 */
@Service
public class ParserUtil {

    public List<String> getChildUrls(Document document) {
        Elements links = document.select("a[href]");
        List<String> hrefs = new ArrayList<>();
        for (Element link: links) {
            hrefs.add(link.attr("href"));
        }
        return hrefs;
    }
}
