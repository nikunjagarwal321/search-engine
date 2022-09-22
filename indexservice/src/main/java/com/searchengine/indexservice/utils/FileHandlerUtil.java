package com.searchengine.indexservice.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * created by nikunjagarwal on 18-09-2022
 */
@Slf4j
@Service
public class FileHandlerUtil {

    public static void writeToFile(String fileName, String fileContents) throws IOException {
        File file = new File(fileName);
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(fileContents);
        bw.close();
    }

    public static String readFromFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        Stream<String> lines = Files.lines(path);
        String fileString = lines.collect(Collectors.joining("\n"));
        lines.close();
        return fileString;
    }
}
