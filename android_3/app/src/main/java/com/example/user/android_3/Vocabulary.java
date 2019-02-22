package com.example.user.android_3;

public class Vocabulary {
    private String word;
    private String translate;

    public Vocabulary(String word,String translate)
    {
        this.translate=translate;
        this.word=word;
    }
    /*public Vocabulary() {
        word = new String();
        translate = new String();
    }*/

    public void setWord(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setTranslated(String translate) {
        this.translate = translate;
    }

    public String getTranslated() {
        return translate;
    }

    @Override
    public String toString() {
        return word + " "  + translate;
    }
}
