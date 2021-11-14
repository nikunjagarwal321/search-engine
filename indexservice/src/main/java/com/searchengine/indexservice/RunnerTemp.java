package com.searchengine.indexservice;

import com.searchengine.indexservice.services.IndexingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class RunnerTemp implements CommandLineRunner {
    String sampleDoc = "Full-Text Search is one of those tools people use every day without realizing it. If you ever googled \"golang coverage report\" or tried to find \"indoor wireless camera\" on an e-commerce website, you used some kind of full-text search.\n" +
            "\n" +
            "Full-Text Search (FTS) is a technique for searching text in a collection of documents. A document can refer to a web page, a newspaper article, an email message, or any structured text.\n" +
            "\n" +
            "Today we are going to build our own FTS engine. By the end of this post, we'll be able to search across millions of documents in less than a millisecond. We'll start with simple search queries like \"give me all documents that contain the word cat\" and we'll extend the engine to support more sophisticated boolean queries.";

    @Autowired
    IndexingService indexingService;

    @Override
    public void run(String... args) {
        HashMap<String, Integer> listOfWords = indexingService.createIndex(sampleDoc);
        log.info("List of words : {}", listOfWords);
    }
}
