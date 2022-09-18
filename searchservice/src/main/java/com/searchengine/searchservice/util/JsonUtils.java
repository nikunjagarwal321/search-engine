package com.searchengine.searchservice.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * created by nikunjagarwal on 18-09-2022
 */
public class JsonUtils {
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

    public static <T> T convertStringToObject(String string, TypeReference<T> typeReference) throws IOException {
        return objectMapper.readValue(string, typeReference);
    }
}
