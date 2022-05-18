package com.example.translation.All_Translated_Words;

public class Control {

    Collection_one one = new Collection_one();
    Collection_two two = new Collection_two();
    Collection_three three = new Collection_three();
    Collection_for four = new Collection_for();
    Collection_five five = new Collection_five();

    String all = one.one + two.one + three.one + four.one +five.one ;

   public String [] split = all.split("\n");

    public String[] getSplit() {
        return split;
    }

}
