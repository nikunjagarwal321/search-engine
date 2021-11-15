package com.searchengine.indexservice.services;

import com.searchengine.indexservice.constants.StemmingConstants;
import org.springframework.stereotype.Service;

/**
 *  created by nikunjagarwal on 15-11-2021
 */

@Service
public class PorterStemmingService implements StemmingService {

    @Override
    public String stemWord(String word) {
        int m = 2;
        return step5(step4(step3(step2(step1(word,m),m),m),m),m);
    }
    private String step1(String word, Integer m) {
        int wordSize = word.length();
        if(word.charAt(wordSize-1) == 's') {
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
        if(word.endsWith(StemmingConstants.eed) && m>0) {
            return word.substring(0, newWordSize - StemmingConstants.eed.length()) + "ee";
        }

        if(word.endsWith(StemmingConstants.ed))//TODO: add other condition
            return step1substep(word.substring(0, newWordSize - StemmingConstants.ed.length()), m);
        if(word.endsWith(StemmingConstants.ing))//TODO: add other condition
            return step1substep(word.substring(0, newWordSize - StemmingConstants.ing.length()), m);

        return word;
    }

    private String step1substep(String word, Integer m) {
        //TODO: add logic
        return word;
    }

    private String step2(String word, Integer m) {
        int wordSize = word.length();
        if(m > 0) {
            switch(word.charAt(wordSize-1)){
                case 'l':
                    if(word.endsWith(StemmingConstants.ational))
                        return word.substring(0, wordSize-StemmingConstants.ational.length())+"ate";
                    if(word.endsWith(StemmingConstants.tional))
                        return word.substring(0, wordSize-StemmingConstants.tional.length())+"tion";
                    break;
                case 'i':
                    if(word.endsWith(StemmingConstants.enci))
                        return word.substring(0, wordSize-StemmingConstants.enci.length())+"ence";
                    if(word.endsWith(StemmingConstants.anci))
                        return word.substring(0, wordSize-StemmingConstants.anci.length())+"ance";
                    if(word.endsWith(StemmingConstants.abli))
                        return word.substring(0, wordSize-StemmingConstants.abli.length())+"able";
                    if(word.endsWith(StemmingConstants.alli))
                        return word.substring(0, wordSize-StemmingConstants.alli.length())+"al";
                    if(word.endsWith(StemmingConstants.entli))
                        return word.substring(0, wordSize-StemmingConstants.entli.length())+"ent";
                    if(word.endsWith(StemmingConstants.eli))
                        return word.substring(0, wordSize-StemmingConstants.eli.length())+"e";
                    if(word.endsWith(StemmingConstants.ousli))
                        return word.substring(0, wordSize-StemmingConstants.ousli.length())+"ous";
                    if(word.endsWith(StemmingConstants.aliti))
                        return word.substring(0, wordSize-StemmingConstants.aliti.length())+"al";
                    if(word.endsWith(StemmingConstants.iviti))
                        return word.substring(0, wordSize-StemmingConstants.iviti.length())+"ive";
                    if(word.endsWith(StemmingConstants.biliti))
                        return word.substring(0, wordSize-StemmingConstants.biliti.length())+"ble";
                    break;
                case 'r':
                    if(word.endsWith(StemmingConstants.izer))
                        return word.substring(0, wordSize-StemmingConstants.izer.length())+"ize";
                    if(word.endsWith(StemmingConstants.ator))
                        return word.substring(0, wordSize-StemmingConstants.ator.length())+"ate";
                    break;
                case 'n':
                    if(word.endsWith(StemmingConstants.ization))
                        return word.substring(0, wordSize-StemmingConstants.ization.length())+"ize";
                    if(word.endsWith(StemmingConstants.ation))
                        return word.substring(0, wordSize-StemmingConstants.ation.length())+"ate";
                    break;
                case 's':
                    if(word.endsWith(StemmingConstants.iveness))
                        return word.substring(0, wordSize-StemmingConstants.iveness.length())+"ive";
                    if(word.endsWith(StemmingConstants.fulness))
                        return word.substring(0, wordSize-StemmingConstants.fulness.length())+"ful";
                    if(word.endsWith(StemmingConstants.ousness))
                        return word.substring(0, wordSize-StemmingConstants.ousness.length())+"ous";
                    break;
                case 'm':
                    if(word.endsWith(StemmingConstants.alism))
                        return word.substring(0, wordSize-StemmingConstants.alism.length())+"al";
                    break;
                default:
                    return word;
            }
        }
        return word;
    }

    private String step3(String word, Integer m) {
        int wordSize = word.length();
        if(m > 0) {
            switch(word.charAt(wordSize-1)){
                case 'l':
                    if(word.endsWith(StemmingConstants.ical))
                        return word.substring(0, wordSize-StemmingConstants.ical.length())+"ic";
                    if(word.endsWith(StemmingConstants.ful))
                        return word.substring(0, wordSize-StemmingConstants.ful.length());
                    break;
                case 'e':
                    if(word.endsWith(StemmingConstants.icate))
                        return word.substring(0, wordSize-StemmingConstants.icate.length())+"ic";
                    if(word.endsWith(StemmingConstants.ative))
                        return word.substring(0, wordSize-StemmingConstants.ative.length());
                    if(word.endsWith(StemmingConstants.alize))
                        return word.substring(0, wordSize-StemmingConstants.alize.length()) + "al";
                    break;
                case 'i':
                    if(word.endsWith(StemmingConstants.iciti))
                        return word.substring(0, wordSize-StemmingConstants.iciti.length())+"ic";
                    break;
                case 's':
                    if(word.endsWith(StemmingConstants.ness))
                        return word.substring(0, wordSize-StemmingConstants.ness.length());
                      break;
                default:
                    return word;
            }
        }
        return word;
    }

    private String step4(String word, Integer m) {
        int wordSize = word.length();
        if(m > 1) {
            switch(word.charAt(wordSize-1)){
                case 'l':
                    if(word.endsWith(StemmingConstants.al))
                        return word.substring(0, wordSize-StemmingConstants.al.length());
                    break;
                case 'e':
                    if(word.endsWith(StemmingConstants.ance))
                        return word.substring(0, wordSize-StemmingConstants.ance.length());
                    if(word.endsWith(StemmingConstants.ence))
                        return word.substring(0, wordSize-StemmingConstants.ence.length());
                    if(word.endsWith(StemmingConstants.able))
                        return word.substring(0, wordSize-StemmingConstants.able.length());
                    if(word.endsWith(StemmingConstants.ible))
                        return word.substring(0, wordSize-StemmingConstants.ible.length());
                    if(word.endsWith(StemmingConstants.ate))
                        return word.substring(0, wordSize-StemmingConstants.ate.length());
                    if(word.endsWith(StemmingConstants.ive))
                        return word.substring(0, wordSize-StemmingConstants.ive.length());
                    if(word.endsWith(StemmingConstants.ize))
                        return word.substring(0, wordSize-StemmingConstants.ize.length());
                    break;
                case 't':
                    if(word.endsWith(StemmingConstants.ant))
                        return word.substring(0, wordSize-StemmingConstants.ant.length());
                    if(word.endsWith(StemmingConstants.ement))
                        return word.substring(0, wordSize-StemmingConstants.ement.length());
                    if(word.endsWith(StemmingConstants.ment))
                        return word.substring(0, wordSize-StemmingConstants.ment.length());
                    if(word.endsWith(StemmingConstants.ent))
                        return word.substring(0, wordSize-StemmingConstants.ent.length());
                    break;
                case 'r':
                    if(word.endsWith(StemmingConstants.er))
                        return word.substring(0, wordSize-StemmingConstants.er.length());
                    break;
                case 'c':
                    if(word.endsWith(StemmingConstants.ic))
                        return word.substring(0, wordSize-StemmingConstants.ic.length());
                    break;
                case 'n':
                    if(word.endsWith(StemmingConstants.ion))
                        //TODO: additional condition
                        return word.substring(0, wordSize-StemmingConstants.ion.length());
                    break;
                case 'u':
                    if(word.endsWith(StemmingConstants.ou))
                        return word.substring(0, wordSize-StemmingConstants.ou.length());
                    break;
                case 'm':
                    if(word.endsWith(StemmingConstants.ism))
                        return word.substring(0, wordSize-StemmingConstants.ism.length());
                    break;
                case 'i':
                    if(word.endsWith(StemmingConstants.iti))
                        return word.substring(0, wordSize-StemmingConstants.iti.length());
                    break;
                case 's':
                    if(word.endsWith(StemmingConstants.ous))
                        return word.substring(0, wordSize-StemmingConstants.ous.length());
                    break;
                default:
                    return word;
            }
        }
        return word;
    }

    private String step5(String word, Integer m) {
        int wordSize = word.length();
        if(m > 1) {
            if(word.endsWith("e"))
                return word.substring(0, wordSize-1);
        }
        //TODO : add 2 conditions
        return word;
    }
           
}
