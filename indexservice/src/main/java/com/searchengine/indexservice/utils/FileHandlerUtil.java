package com.searchengine.indexservice.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
}
