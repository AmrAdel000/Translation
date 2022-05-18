package com.example.translation;

import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class Words {

    String All_text ;
    String [] split ;

    public String [] GetAllWords(){
        load_words();
        split = All_text.split("\n");
       return split ;

    }

    private void load_words() {
    }

    public String cheek_of_char(String s){

        String ln = "en";
        if (s.length() >40){
          ln = "sentence";  
        }else {
            try {
                char c = s.charAt(0);

                switch (c) {
                    case 'ج':
                        ln = "ar";
                        break;
                    case 'ض':
                        ln = "ar";
                        break;
                    case 'ص':
                        ln = "ar";
                        break;
                    case 'ث':
                        ln = "ar";
                        break;
                    case 'ق':
                        ln = "ar";
                        break;
                    case 'ف':
                        ln = "ar";
                        break;
                    case 'غ':
                        ln = "ar";
                        break;
                    case 'ع':
                        ln = "ar";
                        break;
                    case 'ه':
                        ln = "ar";
                        break;
                    case 'خ':
                        ln = "ar";
                        break;
                    case 'ح':
                        ln = "ar";
                        break;
                    case 'د':
                        ln = "ar";
                        break;
                    case 'ش':
                        ln = "ar";
                        break;
                    case 'س':
                        ln = "ar";
                        break;
                    case 'ي':
                        ln = "ar";
                        break;
                    case 'ب':
                        ln = "ar";
                        break;
                    case 'ل':
                        ln = "ar";
                        break;
                    case 'ا':
                        ln = "ar";
                        break;
                    case 'ت':
                        ln = "ar";
                        break;
                    case 'ن':
                        ln = "ar";
                        break;
                    case 'م':
                        ln = "ar";
                        break;
                    case 'ك':
                        ln = "ar";
                        break;
                    case 'ط':
                        ln = "ar";
                        break;
                    case 'ئ':
                        ln = "ar";
                        break;
                    case 'ء':
                        ln = "ar";
                        break;
                    case 'ؤ':
                        ln = "ar";
                        break;
                    case 'ر':
                        ln = "ar";
                        break;
                    case 'ى':
                        ln = "ar";
                        break;
                    case 'ة':
                        ln = "ar";
                        break;
                    case 'و':
                        ln = "ar";
                        break;
                    case 'ز':
                        ln = "ar";
                        break;
                    case 'ذ':
                        ln = "ar";
                        break;
                    case 'ظ':
                        ln = "ar";
                        break;
                    case 'أ':
                        ln = "ar";
                        break;
                    case 'إ':
                        ln = "ar";
                        break;
                    case 'آ':
                        ln = "ar";
                        break;

                }



            } catch (Exception e) {

            }

            if (s.equals("") || s.equals(" ")){
                ln = "";
            }
        }
      return  ln ;
    }
}
