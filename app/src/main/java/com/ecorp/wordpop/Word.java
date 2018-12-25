package com.ecorp.wordpop;

/**
 * Created by benho on 03/06/2018.
 */
public class Word {
    String word;
    String translation;

    public String getWord() {
        return this.word;
    }
    public String getTranslation() {
        return this.translation;
    }



    public Word(String w, String t) {
        this.word = w;
        this.translation = t;
    }
}