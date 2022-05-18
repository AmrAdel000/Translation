package com.example.translation;

import com.example.translation.models.Model;

public class Compare_Models {

   String arabic_1 , english_1 , arabic_2 , english_2 ;
   boolean aBoolean ;

    public boolean isaBoolean() {
        return aBoolean;
    }

    public Compare_Models(Model one, Model tow) {
        this.arabic_1 = one.getEnglish_word() ;
        this.english_1 = one.getArabic_word();

        this.arabic_2 = tow.getEnglish_word() ;
        this.english_2 = tow.getArabic_word();

       aBoolean = co(one , tow);

    }

    public boolean co (Model one, Model tow){

        this.arabic_1 = one.getEnglish_word() ;
        this.english_1 = one.getArabic_word();

        this.arabic_2 = tow.getEnglish_word() ;
        this.english_2 = tow.getArabic_word();


        return arabic_1.equals(arabic_2) && english_1.equals(english_2);
    }

}
