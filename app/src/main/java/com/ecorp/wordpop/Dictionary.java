package com.ecorp.wordpop;

/**
 * Created by benho on 03/06/2018.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Dictionary {
    static ArrayList<Word> list;

    static void init(){
        if(list == null)
            list = new ArrayList<Word>();
    }

    static void addWord(String w, String t) {
        Word word = new Word(w,t);
        list.add(word);
        Collections.sort(list, new Comparator<Word>() {
            @Override
            public int compare(Word item, Word t1) {
                String s1 = item.getWord();
                String s2 = t1.getWord();
                return s1.compareToIgnoreCase(s2);
            }
        });
    }

    static void deleteWord(Word word) {
        list.remove(word);
    }


    static int size() {
        return list.size();
    }

    public static Word[] quiz() {
        int IndexArr[] = {-1,-1,-1,-1};
        IndexArr[0] = randomWithRange();


        int tmp; boolean in;
        for(int i=1; i<4; i++) {
            in = false;
            tmp = randomWithRange();
            for(int j=0; j<IndexArr.length; j++) {
                if(IndexArr[j] == tmp) {
                    in = true;
                    break;
                }
            }

            if(!in) {
                IndexArr[i] = tmp;
            }
            else i--;
        }

        Word[] tra = {list.get(IndexArr[0]),list.get(IndexArr[1]),list.get(IndexArr[2]),list.get(IndexArr[3])};
        return tra;
    }

    static int randomWithRange()
    {
        int range = list.size();
        return (int)(Math.random() * range);
    }


}