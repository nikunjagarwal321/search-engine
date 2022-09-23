package com.searchengine.searchservice.util;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

/**
 * created by nikunjagarwal on 30-08-2022
 */
@Service
public class PorterStemmingUtil {
    public final HashSet<Character> vowels = new HashSet(Arrays.asList('a', 'e', 'i', 'o', 'u'));

    public String trimVC(String word) {
        int startingConsonants = 0, endingVowels = word.length() - 1;
        while (startingConsonants < word.length() && !isVowel(word, startingConsonants))
            startingConsonants++;
        while (endingVowels >= 0 && isVowel(word, endingVowels))
            endingVowels--;
        if (startingConsonants <= endingVowels)
            return word.substring(startingConsonants, endingVowels);
        return "";
    }

    public Integer getM(String word) {
        word = trimVC(word);
        int currentChar = 0, m = 0;
        while (currentChar < word.length()) {
            while (currentChar < word.length() && isVowel(word, currentChar))
                currentChar++;
            while (currentChar < word.length() && !isVowel(word, currentChar))
                currentChar++;
            m++;
        }
        return m;
    }

    public boolean isVowel(String word, int index) {
        char currentChar = word.charAt(index);
        if (currentChar == 'a' || currentChar == 'e' || currentChar == 'i' || currentChar == 'o' || currentChar == 'u')
            return true;
        if (currentChar == 'y' && index != 0 && !vowels.contains(word.charAt(index - 1)))
            return true;
        return false;
    }

    public boolean wordHasVowel(String word) {
        return word.contains("a") || word.contains("e") || word.contains("i") || word.contains("o") || word.contains("u");
    }

    public boolean endsWithDoubleConsonant(String word) {
        int wordSize = word.length();
        if (wordSize < 2)
            return false;
        if (word.charAt(wordSize - 1) != word.charAt(wordSize - 2))
            return false;
        if (vowels.contains(word.charAt(wordSize - 1)))
            return false;
        return true;
    }

    public boolean endsWithCVC(String word) {
        int wordSize = word.length();
        if (wordSize < 3 || isVowel(word, wordSize - 1) ||
                !isVowel(word, wordSize - 2) || isVowel(word, wordSize - 3))
            return false;
        char thirdLastChar = word.charAt(wordSize - 3);
        if (thirdLastChar == 'w' || thirdLastChar == 'x' || thirdLastChar == 'y')
            return false;
        return true;
    }

    public String replaceEndingCharacters(String word, Integer mCheckValue, Integer checkStringSize, String replaceString) {
        int wordSize = word.length();
        if (getM(word.substring(0, wordSize - checkStringSize)) > mCheckValue)
            return word.substring(0, wordSize - checkStringSize) + replaceString;
        return word;
    }

}
