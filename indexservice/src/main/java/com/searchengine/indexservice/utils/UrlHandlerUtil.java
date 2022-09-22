package com.searchengine.indexservice.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * created by nikunjagarwal on 22-09-2022
 */
@Service
@Slf4j
public class UrlHandlerUtil {

    @Value("${domain.scope}")
    public Set<String> inScopeDomain;

    /**
     * Convert relative and absolute urls to absolute urls
     * Reference : https://www.seoclarity.net/resources/knowledgebase/difference-relative-absolute-url-15325/
     * 1. Starts with http: --> add directly
     * 2. Starts with / --> add after base url
     * 3. Starts with normal character --> add after base url
     * 4. Starts with # --> dont add
     * 5. Starts with ./ --> add in the same path
     * 6. Starts with .. --> compute relative path and then add
     */
    public Set<String> parseChildUrls(Set<String> childUrls, String parentUrl) {
        Set<String> parsedChildUrls = new HashSet<>();
        try {
            String baseUrl = getBaseUrlFromCompleteUrl(parentUrl);
            String urlWithoutQuery = getUrlWithoutQuery(parentUrl);
            for (String childUrl: childUrls) {
                if(childUrl.startsWith("http")) {
                    parsedChildUrls.add(childUrl);
                } else if (childUrl.startsWith("/")) {
                    parsedChildUrls.add(baseUrl + childUrl);
                } else if(Character.isAlphabetic(childUrl.charAt(0))){
                    parsedChildUrls.add(baseUrl + "/" + childUrl);
                } else if (childUrl.startsWith("./")) {
                    parsedChildUrls.add(urlWithoutQuery + childUrl.substring(1));
                } else if(childUrl.startsWith("#")) {
                    // Do nothing as same page
                } else if(childUrl.startsWith("..")) {
                    //TODO: add the logic of relative path traversal
                }
            }
        } catch (URISyntaxException e) {
            log.error("URI Syntax Exception:{}", e);
        }
        return parsedChildUrls;
    }

    public List<String> filterInScopeUrls(Set<String> childUrls) {
        List<String> inScopeValidUrls = new ArrayList<>();
        for(String childUrl : childUrls) {
            try{
                String childUrlHost = getHost(childUrl);
                if(inScopeDomain.contains(childUrlHost)) {
                    inScopeValidUrls.add(childUrl);
                }
            } catch (URISyntaxException e) {
                log.error("Invalid child url : {}", childUrl);
            }
        }
        return inScopeValidUrls;
    }

    public String getBaseUrlFromCompleteUrl(String completeUrl) throws URISyntaxException {
        URI url = new URI(completeUrl);
        return url.getScheme() + "://" + url.getHost();
    }

    public String getHost(String completeUrl) throws URISyntaxException {
        URI url = new URI(completeUrl);
        return url.getHost();
    }

    public String getUrlWithoutQuery(String completeUrl) throws URISyntaxException {
        URI url = new URI(completeUrl);
        if(url.getPath().isEmpty())
            return getBaseUrlFromCompleteUrl(completeUrl);
        return url.getScheme() + "://" + url.getHost() + url.getPath();
    }
}
