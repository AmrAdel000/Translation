package com.example.translation.models;

import android.os.Parcel;

public class mod {

    int id ; String arabic_word ,english_word , example , color ;

    public mod(int id, String arabic_word, String english_word, String example, String color) {
        this.id = id;
        this.arabic_word = arabic_word;
        this.english_word = english_word;
        this.example = example;
        this.color = color;

    }


    public int getId() {
        return id;
    }

    public String getArabic_word() {
        return arabic_word;
    }

    public String getEnglish_word() {
        return english_word;
    }

    public String getExample() {
        return example;
    }

    public String getColor() {
        return color;
    }

}
