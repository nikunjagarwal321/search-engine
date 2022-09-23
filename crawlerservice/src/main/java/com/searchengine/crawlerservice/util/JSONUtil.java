package com.searchengine.crawlerservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * created by nikunjagarwal on 23-09-2022
 */
public class JSONUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T convertStringToObject(String string, Class<T> className) throws IOException {
        return objectMapper.readValue(string, className);
    }

    public static <T> String convertObjectToString(T object) throws IOException {
        return objectMapper.writeValueAsString(object);
    }

    public static <T> T convertObjectToObject(Object object, Class<T> className) {
        return objectMapper.convertValue(object, className);
    }
}
