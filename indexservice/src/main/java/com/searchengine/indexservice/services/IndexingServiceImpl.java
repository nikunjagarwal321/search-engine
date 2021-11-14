package com.searchengine.indexservice.services;

import com.searchengine.indexservice.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
@Slf4j
public class IndexingServiceImpl implements IndexingService{

    HashSet<String> stopWordsSet;

    public IndexingServiceImpl(){
        this.stopWordsSet = new HashSet(Arrays.stream(Constants.stopWords.split(",")).collect(Collectors.toSet()));
    }

    /**
     * Extracts words from document, converts them to lowercase,
     * filters relevant words, stems words and returns the final list of words
     *
     * @param :
     * @return :
     */
    @Override
    public HashMap<String, Integer> createIndex(String document) {
        LocalDateTime t1 = LocalDateTime.now();
        HashMap<String, Integer> listOfRelevantWords = new HashMap(Arrays.stream(
                        document.split("[\\W]+"))
                .map(s -> s.toLowerCase())
                .filter(s -> isRelevantWord(s))
                .map(s -> stemWord(s))
                .collect(Collectors.groupingBy(s -> s, Collectors.counting())));
        long timeTakenToIndex = Duration.between(t1, LocalDateTime.now()).toMillis();
        log.info("Time taken in millisec : {}", timeTakenToIndex);
        return listOfRelevantWords;
    }

    /**
     * Stems keyword based on different conditions
     *
     * @param :
     * @return :
     */
    private String stemWord(String word) {
        // TODO : add different stemming logics or use a pre-written module
        if(word.endsWith("ing"))
            return word.substring(0, word.length()-3) + 'e';
        return word;
    }

    /**
     * Checks and return if word is relevant or not based on word length and stop words list
     *
     * @param :
     * @return :
     */
    private boolean isRelevantWord(String word) {
        return word.length() > 2 && !stopWordsSet.contains(word);
    }

}
