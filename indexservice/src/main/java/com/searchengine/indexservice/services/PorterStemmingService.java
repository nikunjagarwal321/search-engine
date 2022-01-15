package com.searchengine.indexservice.services;

import com.searchengine.indexservice.constants.StemmingConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

/**
 * created by nikunjagarwal on 15-11-2021
 */

@Service
@Slf4j
public class PorterStemmingService implements StemmingService {

    public final HashSet<Character> vowels = new HashSet(Arrays.asList('a', 'e', 'i', 'o', 'u'));


    @Override
    public String stemWord(String word) {
        String stemmedWord = step5(step4(step3(step2(step1(word)))));
//        log.info("Initial word : {}, Stemmed word :{}", word, stemmedWord);
        return stemmedWord;
    }

    private String step1(String word) {
        int wordSize = word.length();
        if (word.charAt(wordSize - 1) == 's') {
            if (word.endsWith(StemmingConstants.sses))
                word = word.substring(0, wordSize - StemmingConstants.sses.length()) + "ss";
            else if (word.endsWith(StemmingConstants.ies))
                word = word.substring(0, wordSize - StemmingConstants.ies.length()) + "i";
            else if (word.endsWith(StemmingConstants.ss))
                word = word.substring(0, wordSize - StemmingConstants.ss.length()) + "ss";
            else
                word = word.substring(0, wordSize - StemmingConstants.s.length());
        }

        int newWordSize = word.length();
        if (word.endsWith(StemmingConstants.eed)) {
            word = replaceEndingCharacters(word, 0, StemmingConstants.eed.length(), "ee");
        } else {
            if (word.endsWith(StemmingConstants.ed) && wordHasVowel(word.substring(0, newWordSize - StemmingConstants.ed.length())))
                word = step1substep(word.substring(0, newWordSize - StemmingConstants.ed.length()));
            if (word.endsWith(StemmingConstants.ing) && wordHasVowel(word.substring(0, newWordSize - StemmingConstants.ing.length())))
                word = step1substep(word.substring(0, newWordSize - StemmingConstants.ing.length()));
        }
        if (wordHasVowel(word) && word.endsWith("y"))
            return word.substring(0, word.length() - 1) + "i";
        return word;
    }

    private String step1substep(String word) {
        int wordSize = word.length();
        if (word.endsWith("at"))
            return word.substring(0, wordSize - StemmingConstants.at.length()) + "ate";
        if (word.endsWith("bl"))
            return word.substring(0, wordSize - StemmingConstants.bl.length()) + "ble";
        if (word.endsWith("iz"))
            return word.substring(0, wordSize - StemmingConstants.iz.length()) + "ize";
        if (endsWithDoubleConsonant(word) && !(word.endsWith("l") || word.endsWith("s") || word.endsWith("z")))
            return word.substring(0, wordSize - 1);
        if (getM(word) == 1 && endsWithCVC(word))
            return word + "e";
        return word;
    }

    private String step2(String word) {
        int wordSize = word.length();
        switch (word.charAt(wordSize - 1)) {
            case 'l':
                if (word.endsWith(StemmingConstants.ational))
                    return replaceEndingCharacters(word, 0, StemmingConstants.ational.length(), "ate");
                if (word.endsWith(StemmingConstants.tional))
                    return replaceEndingCharacters(word, 0, StemmingConstants.tional.length(), "tion");
                break;
            case 'i':
                if (word.endsWith(StemmingConstants.enci))
                    return replaceEndingCharacters(word, 0, StemmingConstants.enci.length(), "ence");
                if (word.endsWith(StemmingConstants.anci))
                    return replaceEndingCharacters(word, 0, StemmingConstants.anci.length(), "ance");
                if (word.endsWith(StemmingConstants.abli))
                    return replaceEndingCharacters(word, 0, StemmingConstants.abli.length(), "able");
                if (word.endsWith(StemmingConstants.alli))
                    return replaceEndingCharacters(word, 0, StemmingConstants.alli.length(), "al");
                if (word.endsWith(StemmingConstants.entli))
                    return replaceEndingCharacters(word, 0, StemmingConstants.entli.length(), "ent");
                if (word.endsWith(StemmingConstants.eli))
                    return replaceEndingCharacters(word, 0, StemmingConstants.eli.length(), "e");
                if (word.endsWith(StemmingConstants.ousli))
                    return replaceEndingCharacters(word, 0, StemmingConstants.ousli.length(), "ous");
                if (word.endsWith(StemmingConstants.aliti))
                    return replaceEndingCharacters(word, 0, StemmingConstants.aliti.length(), "al");
                if (word.endsWith(StemmingConstants.iviti))
                    return replaceEndingCharacters(word, 0, StemmingConstants.iviti.length(), "ive");
                if (word.endsWith(StemmingConstants.biliti))
                    return replaceEndingCharacters(word, 0, StemmingConstants.biliti.length(), "ble");
                break;
            case 'r':
                if (word.endsWith(StemmingConstants.izer))
                    return replaceEndingCharacters(word, 0, StemmingConstants.izer.length(), "ize");
                if (word.endsWith(StemmingConstants.ator))
                    return replaceEndingCharacters(word, 0, StemmingConstants.ator.length(), "ate");
                break;
            case 'n':
                if (word.endsWith(StemmingConstants.ization))
                    return replaceEndingCharacters(word, 0, StemmingConstants.ization.length(), "ize");
                if (word.endsWith(StemmingConstants.ation))
                    return replaceEndingCharacters(word, 0, StemmingConstants.ation.length(), "ate");
                break;
            case 's':
                if (word.endsWith(StemmingConstants.iveness))
                    return replaceEndingCharacters(word, 0, StemmingConstants.iveness.length(), "ive");
                if (word.endsWith(StemmingConstants.fulness))
                    return replaceEndingCharacters(word, 0, StemmingConstants.fulness.length(), "ful");
                if (word.endsWith(StemmingConstants.ousness))
                    return replaceEndingCharacters(word, 0, StemmingConstants.ousness.length(), "ous");
                break;
            case 'm':
                if (word.endsWith(StemmingConstants.alism))
                    return replaceEndingCharacters(word, 0, StemmingConstants.alism.length(), "al");
                break;
            default:
                return word;
        }

        return word;
    }

    private String step3(String word) {
        int wordSize = word.length();
        switch (word.charAt(wordSize - 1)) {
            case 'l':
                if (word.endsWith(StemmingConstants.ical))
                    return replaceEndingCharacters(word, 0, StemmingConstants.ical.length(), "ic");
                if (word.endsWith(StemmingConstants.ful))
                    return replaceEndingCharacters(word, 0, StemmingConstants.ful.length(), "");
                break;
            case 'e':
                if (word.endsWith(StemmingConstants.icate))
                    return replaceEndingCharacters(word, 0, StemmingConstants.icate.length(), "ic");
                if (word.endsWith(StemmingConstants.ative))
                    return replaceEndingCharacters(word, 0, StemmingConstants.ative.length(), "");
                if (word.endsWith(StemmingConstants.alize))
                    return replaceEndingCharacters(word, 0, StemmingConstants.alize.length(), "al");
                break;
            case 'i':
                if (word.endsWith(StemmingConstants.iciti))
                    return replaceEndingCharacters(word, 0, StemmingConstants.iciti.length(), "ic");
                break;
            case 's':
                if (word.endsWith(StemmingConstants.ness))
                    return replaceEndingCharacters(word, 0, StemmingConstants.ness.length(), "");
                break;
            default:
                return word;
        }
        return word;
    }

    private String step4(String word) {
        int wordSize = word.length();
        switch (word.charAt(wordSize - 1)) {
            case 'l':
                if (word.endsWith(StemmingConstants.al))
                    return replaceEndingCharacters(word, 1, StemmingConstants.al.length(), "");
                break;
            case 'e':
                if (word.endsWith(StemmingConstants.ance))
                    return replaceEndingCharacters(word, 1, StemmingConstants.ance.length(), "");
                if (word.endsWith(StemmingConstants.ence))
                    return replaceEndingCharacters(word, 1, StemmingConstants.ence.length(), "");
                if (word.endsWith(StemmingConstants.able))
                    return replaceEndingCharacters(word, 1, StemmingConstants.able.length(), "");
                if (word.endsWith(StemmingConstants.ible))
                    return replaceEndingCharacters(word, 1, StemmingConstants.ible.length(), "");
                if (word.endsWith(StemmingConstants.ate))
                    return replaceEndingCharacters(word, 1, StemmingConstants.ate.length(), "");
                if (word.endsWith(StemmingConstants.ive))
                    return replaceEndingCharacters(word, 1, StemmingConstants.ive.length(), "");
                if (word.endsWith(StemmingConstants.ize))
                    return replaceEndingCharacters(word, 1, StemmingConstants.ize.length(), "");
                break;
            case 't':
                if (word.endsWith(StemmingConstants.ant))
                    return replaceEndingCharacters(word, 1, StemmingConstants.ant.length(), "");
                if (word.endsWith(StemmingConstants.ement))
                    return replaceEndingCharacters(word, 1, StemmingConstants.ement.length(), "");
                if (word.endsWith(StemmingConstants.ment))
                    return replaceEndingCharacters(word, 1, StemmingConstants.ment.length(), "");
                if (word.endsWith(StemmingConstants.ent))
                    return replaceEndingCharacters(word, 1, StemmingConstants.ent.length(), "");
                break;
            case 'r':
                if (word.endsWith(StemmingConstants.er))
                    return replaceEndingCharacters(word, 1, StemmingConstants.er.length(), "");
                break;
            case 'c':
                if (word.endsWith(StemmingConstants.ic))
                    return replaceEndingCharacters(word, 1, StemmingConstants.ic.length(), "");
                break;
            case 'n':
                if(wordSize > 3) {
                    char penultimateChar = word.charAt(wordSize - StemmingConstants.ion.length() - 1);
                    if (word.endsWith(StemmingConstants.ion) && (penultimateChar == 's' || penultimateChar == 't'))
                        return replaceEndingCharacters(word, 1, StemmingConstants.ion.length(), "");
                }
                break;
            case 'u':
                if (word.endsWith(StemmingConstants.ou))
                    return replaceEndingCharacters(word, 1, StemmingConstants.ou.length(), "");
                break;
            case 'm':
                if (word.endsWith(StemmingConstants.ism))
                    return replaceEndingCharacters(word, 1, StemmingConstants.ism.length(), "");
                break;
            case 'i':
                if (word.endsWith(StemmingConstants.iti))
                    return replaceEndingCharacters(word, 1, StemmingConstants.iti.length(), "");
                break;
            case 's':
                if (word.endsWith(StemmingConstants.ous))
                    return replaceEndingCharacters(word, 1, StemmingConstants.ous.length(), "");
                break;
            default:
                return word;
        }
        return word;
    }

    private String step5(String word) {
        int wordSize = word.length();
        if (word.endsWith("e") && getM(word.substring(0, wordSize - 1)) > 1) {
            word = word.substring(0, wordSize - 1);
        } else if (!endsWithCVC(word.substring(0, wordSize - 1)) && word.endsWith("e") && getM(word.substring(0, wordSize - 1)) == 1) {
            word = word.substring(0, wordSize - 1);
        }
        int newWordSize = word.length();
        if (word.endsWith("l") && endsWithDoubleConsonant(word) && getM(word) > 1)
            return word.substring(0, newWordSize - 1);
        return word;
    }

    private String trimVC(String word) {
        int startingConsonants = 0, endingVowels = word.length() - 1;
        while (startingConsonants < word.length() && !isVowel(word, startingConsonants))
            startingConsonants++;
        while (endingVowels >= 0 && isVowel(word, endingVowels))
            endingVowels--;
        if (startingConsonants <= endingVowels)
            return word.substring(startingConsonants, endingVowels);
        return "";
    }

    private Integer getM(String word) {
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

    private boolean isVowel(String word, int index) {
        char currentChar = word.charAt(index);
        if (currentChar == 'a' || currentChar == 'e' || currentChar == 'i' || currentChar == 'o' || currentChar == 'u')
            return true;
        if (currentChar == 'y' && index != 0 && !vowels.contains(word.charAt(index - 1)))
            return true;
        return false;
    }

    private boolean wordHasVowel(String word) {
        return word.contains("a") || word.contains("e") || word.contains("i") || word.contains("o") || word.contains("u");
    }

    private boolean endsWithDoubleConsonant(String word) {
        int wordSize = word.length();
        if (wordSize < 2)
            return false;
        if (word.charAt(wordSize - 1) != word.charAt(wordSize - 2))
            return false;
        if (vowels.contains(word.charAt(wordSize - 1)))
            return false;
        return true;
    }

    private boolean endsWithCVC(String word) {
        int wordSize = word.length();
        if (wordSize < 3 || isVowel(word, wordSize - 1) ||
                !isVowel(word, wordSize - 2) || isVowel(word, wordSize - 3))
            return false;
        char thirdLastChar = word.charAt(wordSize - 3);
        if (thirdLastChar == 'w' || thirdLastChar == 'x' || thirdLastChar == 'y')
            return false;
        return true;
    }

    private String replaceEndingCharacters(String word, Integer mCheckValue, Integer checkStringSize, String replaceString) {
        int wordSize = word.length();
        if (getM(word.substring(0, wordSize - checkStringSize)) > mCheckValue)
            return word.substring(0, wordSize - checkStringSize) + replaceString;
        return word;
    }
}
