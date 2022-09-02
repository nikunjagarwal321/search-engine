package com.searchengine.indexservice.services.factory;

import com.searchengine.indexservice.services.ParserService;
import com.searchengine.indexservice.services.impl.ParserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by nikunjagarwal on 01-09-2022
 */
@Service
public class ParserFactory {

    @Autowired
    ParserServiceImpl parserServiceImpl;

    /**
     * Since we can have different parsing for different websites in future, therefor different parser
     * for different types of websites and content
     * @param url
     * @return
     */
    public ParserService getParser(String url) {
        //TODO: modify the switch case based on the requirement(domain or something else)
        switch(url) {
            case "common":
                return parserServiceImpl;
        }
        return parserServiceImpl;
    }
}
