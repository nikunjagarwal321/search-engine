package com.searchengine.indexservice.services.impl;

import com.searchengine.indexservice.constants.Constants;
import com.searchengine.indexservice.services.StemmingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * created by nikunjagarwal on 02-09-2022
 */
@Slf4j
@Service
public class IndexingHelper {

    HashSet<String> stopWordsSet = new HashSet(Arrays.stream(Constants.STOP_WORDS.split(",")).collect(Collectors.toSet()));;

    @Autowired
    StemmingService stemmingService;


    /**
     * Extracts words from document, converts them to lowercase,
     * filters relevant words, stems words and returns the final list of words
     *
     * @param :
     * @return :
     */
    public HashMap<String, Integer> getTokenizedFilteredStemmedWordsWithCount(String document) {
        log.info("Tokenize filter and stem words from document : {}", document);
        HashMap<String, Integer> stemmedWordAndCount = new HashMap(Arrays.stream(
                        document.split("[\\W]+"))
                .map(s -> s.toLowerCase())
                .filter(s -> isRelevantWord(s))
                .map(s -> stemmingService.stemWord(s))
                .collect(Collectors.groupingBy(s -> s, Collectors.counting())));
        log.info("Tokenized, stemmed words with count : {}", stemmedWordAndCount);
        return stemmedWordAndCount;
    }


    /**
     * Checks and return if word is relevant based on word length and stop words list
     * @param word : word to be checked
     * @return : true if word is relevant
     */
    private boolean isRelevantWord(String word) {
        return word.length() > Constants.MIN_RELEVANT_WORD_LENGTH && !stopWordsSet.contains(word);
    }
}
