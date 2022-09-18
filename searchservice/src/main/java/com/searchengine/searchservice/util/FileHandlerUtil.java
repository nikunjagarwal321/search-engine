package com.searchengine.searchservice.util;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * created by nikunjagarwal on 18-09-2022
 */
@Service
public class FileHandlerUtil {
    public static String readFromFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        BufferedReader reader = Files.newBufferedReader(path);
        String fileString = reader.readLine();
        reader.close();
        return fileString;
    }
}
