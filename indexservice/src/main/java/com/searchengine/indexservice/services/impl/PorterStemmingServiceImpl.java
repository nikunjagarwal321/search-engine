package com.searchengine.indexservice.services.impl;

import com.searchengine.indexservice.constants.StemmingConstants;
import com.searchengine.indexservice.services.StemmingService;
import com.searchengine.indexservice.utils.PorterStemmingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by nikunjagarwal on 15-11-2021
 */

@Service
@Slf4j
public class PorterStemmingServiceImpl implements StemmingService {
    
    @Autowired
    PorterStemmingUtil porterStemmingUtil;
    

    @Override
    public String stemWord(String word) {
        String stemmedWord = step5(step4(step3(step2(step1(word)))));
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
            word = porterStemmingUtil.replaceEndingCharacters(word, 0, StemmingConstants.eed.length(), "ee");
        } else {
            if (word.endsWith(StemmingConstants.ed) && porterStemmingUtil.wordHasVowel(word.substring(0, newWordSize - StemmingConstants.ed.length())))
                word = step1substep(word.substring(0, newWordSize - StemmingConstants.ed.length()));
            if (word.endsWith(StemmingConstants.ing) && porterStemmingUtil.wordHasVowel(word.substring(0, newWordSize - StemmingConstants.ing.length())))
                word = step1substep(word.substring(0, newWordSize - StemmingConstants.ing.length()));
        }
        if (porterStemmingUtil.wordHasVowel(word) && word.endsWith("y"))
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
        if (porterStemmingUtil.endsWithDoubleConsonant(word) && !(word.endsWith("l") || word.endsWith("s") || word.endsWith("z")))
            return word.substring(0, wordSize - 1);
        if (porterStemmingUtil.getM(word) == 1 && porterStemmingUtil.endsWithCVC(word))
            return word + "e";
        return word;
    }

    private String step2(String word) {
        int wordSize = word.length();
        switch (word.charAt(wordSize - 1)) {
            case 'l':
                if (word.endsWith(StemmingConstants.ational))
                    return porterStemmingUtil.replaceEndingCharacters(word, 0, StemmingConstants.ational.length(), "ate");
                if (word.endsWith(StemmingConstants.tional))
                    return porterStemmingUtil.replaceEndingCharacters(word, 0, StemmingConstants.tional.length(), "tion");
                break;
            case 'i':
                if (word.endsWith(StemmingConstants.enci))
                    return porterStemmingUtil.replaceEndingCharacters(word, 0, StemmingConstants.enci.length(), "ence");
                if (word.endsWith(StemmingConstants.anci))
                    return porterStemmingUtil.replaceEndingCharacters(word, 0, StemmingConstants.anci.length(), "ance");
                if (word.endsWith(StemmingConstants.abli))
                    return porterStemmingUtil.replaceEndingCharacters(word, 0, StemmingConstants.abli.length(), "able");
                if (word.endsWith(StemmingConstants.alli))
                    return porterStemmingUtil.replaceEndingCharacters(word, 0, StemmingConstants.alli.length(), "al");
                if (word.endsWith(StemmingConstants.entli))
                    return porterStemmingUtil.replaceEndingCharacters(word, 0, StemmingConstants.entli.length(), "ent");
                if (word.endsWith(StemmingConstants.eli))
                    return porterStemmingUtil.replaceEndingCharacters(word, 0, StemmingConstants.eli.length(), "e");
                if (word.endsWith(StemmingConstants.ousli))
                    return porterStemmingUtil.replaceEndingCharacters(word, 0, StemmingConstants.ousli.length(), "ous");
                if (word.endsWith(StemmingConstants.aliti))
                    return porterStemmingUtil.replaceEndingCharacters(word, 0, StemmingConstants.aliti.length(), "al");
                if (word.endsWith(StemmingConstants.iviti))
                    return porterStemmingUtil.replaceEndingCharacters(word, 0, StemmingConstants.iviti.length(), "ive");
                if (word.endsWith(StemmingConstants.biliti))
                    return porterStemmingUtil.replaceEndingCharacters(word, 0, StemmingConstants.biliti.length(), "ble");
                break;
            case 'r':
                if (word.endsWith(StemmingConstants.izer))
                    return porterStemmingUtil.replaceEndingCharacters(word, 0, StemmingConstants.izer.length(), "ize");
                if (word.endsWith(StemmingConstants.ator))
                    return porterStemmingUtil.replaceEndingCharacters(word, 0, StemmingConstants.ator.length(), "ate");
                break;
            case 'n':
                if (word.endsWith(StemmingConstants.ization))
                    return porterStemmingUtil.replaceEndingCharacters(word, 0, StemmingConstants.ization.length(), "ize");
                if (word.endsWith(StemmingConstants.ation))
                    return porterStemmingUtil.replaceEndingCharacters(word, 0, StemmingConstants.ation.length(), "ate");
                break;
            case 's':
                if (word.endsWith(StemmingConstants.iveness))
                    return porterStemmingUtil.replaceEndingCharacters(word, 0, StemmingConstants.iveness.length(), "ive");
                if (word.endsWith(StemmingConstants.fulness))
                    return porterStemmingUtil.replaceEndingCharacters(word, 0, StemmingConstants.fulness.length(), "ful");
                if (word.endsWith(StemmingConstants.ousness))
                    return porterStemmingUtil.replaceEndingCharacters(word, 0, StemmingConstants.ousness.length(), "ous");
                break;
            case 'm':
                if (word.endsWith(StemmingConstants.alism))
                    return porterStemmingUtil.replaceEndingCharacters(word, 0, StemmingConstants.alism.length(), "al");
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
                    return porterStemmingUtil.replaceEndingCharacters(word, 0, StemmingConstants.ical.length(), "ic");
                if (word.endsWith(StemmingConstants.ful))
                    return porterStemmingUtil.replaceEndingCharacters(word, 0, StemmingConstants.ful.length(), "");
                break;
            case 'e':
                if (word.endsWith(StemmingConstants.icate))
                    return porterStemmingUtil.replaceEndingCharacters(word, 0, StemmingConstants.icate.length(), "ic");
                if (word.endsWith(StemmingConstants.ative))
                    return porterStemmingUtil.replaceEndingCharacters(word, 0, StemmingConstants.ative.length(), "");
                if (word.endsWith(StemmingConstants.alize))
                    return porterStemmingUtil.replaceEndingCharacters(word, 0, StemmingConstants.alize.length(), "al");
                break;
            case 'i':
                if (word.endsWith(StemmingConstants.iciti))
                    return porterStemmingUtil.replaceEndingCharacters(word, 0, StemmingConstants.iciti.length(), "ic");
                break;
            case 's':
                if (word.endsWith(StemmingConstants.ness))
                    return porterStemmingUtil.replaceEndingCharacters(word, 0, StemmingConstants.ness.length(), "");
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
                    return porterStemmingUtil.replaceEndingCharacters(word, 1, StemmingConstants.al.length(), "");
                break;
            case 'e':
                if (word.endsWith(StemmingConstants.ance))
                    return porterStemmingUtil.replaceEndingCharacters(word, 1, StemmingConstants.ance.length(), "");
                if (word.endsWith(StemmingConstants.ence))
                    return porterStemmingUtil.replaceEndingCharacters(word, 1, StemmingConstants.ence.length(), "");
                if (word.endsWith(StemmingConstants.able))
                    return porterStemmingUtil.replaceEndingCharacters(word, 1, StemmingConstants.able.length(), "");
                if (word.endsWith(StemmingConstants.ible))
                    return porterStemmingUtil.replaceEndingCharacters(word, 1, StemmingConstants.ible.length(), "");
                if (word.endsWith(StemmingConstants.ate))
                    return porterStemmingUtil.replaceEndingCharacters(word, 1, StemmingConstants.ate.length(), "");
                if (word.endsWith(StemmingConstants.ive))
                    return porterStemmingUtil.replaceEndingCharacters(word, 1, StemmingConstants.ive.length(), "");
                if (word.endsWith(StemmingConstants.ize))
                    return porterStemmingUtil.replaceEndingCharacters(word, 1, StemmingConstants.ize.length(), "");
                break;
            case 't':
                if (word.endsWith(StemmingConstants.ant))
                    return porterStemmingUtil.replaceEndingCharacters(word, 1, StemmingConstants.ant.length(), "");
                if (word.endsWith(StemmingConstants.ement))
                    return porterStemmingUtil.replaceEndingCharacters(word, 1, StemmingConstants.ement.length(), "");
                if (word.endsWith(StemmingConstants.ment))
                    return porterStemmingUtil.replaceEndingCharacters(word, 1, StemmingConstants.ment.length(), "");
                if (word.endsWith(StemmingConstants.ent))
                    return porterStemmingUtil.replaceEndingCharacters(word, 1, StemmingConstants.ent.length(), "");
                break;
            case 'r':
                if (word.endsWith(StemmingConstants.er))
                    return porterStemmingUtil.replaceEndingCharacters(word, 1, StemmingConstants.er.length(), "");
                break;
            case 'c':
                if (word.endsWith(StemmingConstants.ic))
                    return porterStemmingUtil.replaceEndingCharacters(word, 1, StemmingConstants.ic.length(), "");
                break;
            case 'n':
                if(wordSize > 3) {
                    char penultimateChar = word.charAt(wordSize - StemmingConstants.ion.length() - 1);
                    if (word.endsWith(StemmingConstants.ion) && (penultimateChar == 's' || penultimateChar == 't'))
                        return porterStemmingUtil.replaceEndingCharacters(word, 1, StemmingConstants.ion.length(), "");
                }
                break;
            case 'u':
                if (word.endsWith(StemmingConstants.ou))
                    return porterStemmingUtil.replaceEndingCharacters(word, 1, StemmingConstants.ou.length(), "");
                break;
            case 'm':
                if (word.endsWith(StemmingConstants.ism))
                    return porterStemmingUtil.replaceEndingCharacters(word, 1, StemmingConstants.ism.length(), "");
                break;
            case 'i':
                if (word.endsWith(StemmingConstants.iti))
                    return porterStemmingUtil.replaceEndingCharacters(word, 1, StemmingConstants.iti.length(), "");
                break;
            case 's':
                if (word.endsWith(StemmingConstants.ous))
                    return porterStemmingUtil.replaceEndingCharacters(word, 1, StemmingConstants.ous.length(), "");
                break;
            default:
                return word;
        }
        return word;
    }

    private String step5(String word) {
        int wordSize = word.length();
        if (word.endsWith("e") && porterStemmingUtil.getM(word.substring(0, wordSize - 1)) > 1) {
            word = word.substring(0, wordSize - 1);
        } else if (!porterStemmingUtil.endsWithCVC(word.substring(0, wordSize - 1)) && word.endsWith("e") && porterStemmingUtil.getM(word.substring(0, wordSize - 1)) == 1) {
            word = word.substring(0, wordSize - 1);
        }
        int newWordSize = word.length();
        if (word.endsWith("l") && porterStemmingUtil.endsWithDoubleConsonant(word) && porterStemmingUtil.getM(word) > 1)
            return word.substring(0, newWordSize - 1);
        return word;
    }

    
}
