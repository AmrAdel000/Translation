package com.example.translation.Database_All;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.translation.models.Model;
import com.example.translation.models.Model_Book;

import java.util.ArrayList;

public class Database_files extends SQLiteOpenHelper {

    public static final String DP_name = "Files_words" ;
    public static final int DP_Vr = 4 ;
    public static final String NOTE_TB_NAME = "file_words";
    public static final String NOTE_CLN_ID = "id";
    public static final String ARABIC_WORD = "Arabic_word";
    public static final String ENGLISH_WORD = "English_word";
    public static final String EXAMBLE = "Examble";
    public static final String COLOR = "Color";
    int d ;
    Context ct;
    public Database_files(Context context) {
 super (context , DP_name , null , DP_Vr );
     ct = context ;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE "+ NOTE_TB_NAME +" ("+ NOTE_CLN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ARABIC_WORD +" TEXT, "+ ENGLISH_WORD +" TEXT, "+ EXAMBLE +" TEXT , "+ COLOR +" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NOTE_TB_NAME);
        onCreate(sqLiteDatabase);
    }
    public Integer insert_word (Model model){
        SQLiteDatabase dp = getWritableDatabase();
        ContentValues valuse = new ContentValues();
        valuse.put(ARABIC_WORD,model.getArabic_word());
        valuse.put(ENGLISH_WORD,model.getEnglish_word());
        valuse.put(EXAMBLE,model.getExample());
        valuse.put(COLOR,model.getColor());
        long result = dp.insert(NOTE_TB_NAME,null,valuse);
return Integer.parseInt(String.valueOf(result)); }

    public void update_note (Model model) {
        SQLiteDatabase dp = getWritableDatabase();
        ContentValues valuse = new ContentValues();
        valuse.put(ARABIC_WORD, model.getArabic_word());
        valuse.put(ENGLISH_WORD, model.getEnglish_word());
        valuse.put(EXAMBLE, model.getExample());
        valuse.put(COLOR, model.getColor());
        String args []= { String.valueOf(model.getId())};
        int result = dp.update(NOTE_TB_NAME, valuse, "id=?", args);
    }
    public void delete_word (Model_Book model) {

        SQLiteDatabase dp = getWritableDatabase();
        String args [] = { String.valueOf(model.getId())};
        int result = dp.delete(NOTE_TB_NAME, "id=?",args);
    }

    // ارجاع عدد الصفوف في اي جدول
    public long getWordsCount (){
        SQLiteDatabase dp = getReadableDatabase();
       return DatabaseUtils.queryNumEntries(dp , NOTE_TB_NAME) ;

    }

    public ArrayList<Model_Book> get_files(){

        SQLiteDatabase dp = getReadableDatabase();
        ArrayList<Model_Book> s = new ArrayList<>();
        Cursor c = dp.rawQuery("SELECT * FROM " + NOTE_TB_NAME, null);
        try {
            if (c.moveToFirst()){
                do {

                    int id = c.getInt(0);
                    String note = c.getString(1);
                    String title = c.getString(2);
                    String lock = c.getString(3);
                    String color = c.getString(4);
                    Model_Book model_book = new Model_Book(id ,note , title , lock , color);
                    s.add(model_book);

                }while (c.moveToNext());
                c.close();
            }

        }catch (Exception e){
            Toast.makeText(ct, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return s ;
    }

    public Model_Book getWords (int x) {
        try {
            SQLiteDatabase dp = getReadableDatabase();
            Cursor c = dp.rawQuery("SELECT * FROM " + NOTE_TB_NAME, null);
            c.move(x);
            int id = c.getInt(0);
            String note = c.getString(1);
            String title = c.getString(2);
            String lock = c.getString(3);
            String color = c.getString(4);
            Model_Book model_book = new Model_Book(id ,note , title , lock , color);
            c.close();
            return model_book;

        }catch (Exception e){

            Toast.makeText(ct, String.valueOf(e), Toast.LENGTH_SHORT).show();
            return null ;
        }
    }
}
