package com.searchengine.indexservice.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * created by nikunjagarwal on 01-09-2022
 */
public class JSONUtils {
   private static ObjectMapper objectMapper = new ObjectMapper();

   public static <T> T convertStringToObject(String string, Class<T> className) throws IOException {
       return objectMapper.readValue(string, className);
   }

    public static <T> String convertObjectToString(T object) throws IOException{
        return objectMapper.writeValueAsString(object);
    }
}
