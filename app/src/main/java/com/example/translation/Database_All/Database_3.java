package com.example.translation.Database_All;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.translation.models.Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class Database_3 extends SQLiteOpenHelper {

    public static final String DP_name = "All_Translated_words" ;
    public static final int DP_Vr = 4 ;
    public static final String NOTE_TB_NAME = "All_Words";
    public static final String NOTE_CLN_ID = "id";
    public static final String ARABIC_WORD = "Arabic_word";
    public static final String ENGLISH_WORD = "English_word";
    public static final String EXAMBLE = "Examble";
    public static final String COLOR = "Color";
    int d ;
    Context ct;
    public Database_3(Context context) {
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
    // الاضافه
    public Integer insert_word (Model model){

        SQLiteDatabase dp = getWritableDatabase();
        ContentValues valuse = new ContentValues();
        valuse.put(ARABIC_WORD,model.getArabic_word());
        valuse.put(ENGLISH_WORD,model.getEnglish_word());
        valuse.put(EXAMBLE,model.getExample());
        valuse.put(COLOR,model.getColor());
        long result = dp.insert(NOTE_TB_NAME,null,valuse);

return Integer.parseInt(String.valueOf(result));

    }
    // التعديل

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
    public void delete_word (Model model) {

        SQLiteDatabase dp = getWritableDatabase();
        String args [] = { String.valueOf(model.getId())};
        int result = dp.delete(NOTE_TB_NAME, "id=?",args);
    }

    // ارجاع عدد الصفوف في اي جدول
    public long getWordsCount (){
        SQLiteDatabase dp = getReadableDatabase();
       return DatabaseUtils.queryNumEntries(dp , NOTE_TB_NAME) ;

    }

    public ArrayList<Model> get_Notes(String searsh){
        SQLiteDatabase dp = getReadableDatabase();
        ArrayList<Model> s = new ArrayList<>();
       // Cursor c = dp.rawQuery("SELECT * FROM " +NOTE_TB_NAME+ " WHERE "+ENGLISH_WORD +" Like '" +searsh + "%'", null);
        Cursor c = dp.rawQuery("SELECT * FROM " +NOTE_TB_NAME+ " WHERE "+ENGLISH_WORD+"=?", new String[] {searsh});

        if (c.moveToFirst()){
            do {
                int id = c.getInt(0);
                String note = c.getString(1);
                String title = c.getString(2);
                String lock = c.getString(3);

                Model model = new Model(id ,note , title , lock , "old");
                s.add(new Model(id ,note , title , lock , "old"));

            }while (c.moveToNext());
            c.close();
        }
        return s ;

    }

//  تحميل الكلمات في ملف خارجي
    public void load_words (){

        SQLiteDatabase dp = getReadableDatabase();
        Cursor c = dp.rawQuery("SELECT * FROM " + NOTE_TB_NAME, null);
      try {
        BufferedWriter fos = new BufferedWriter(new FileWriter(Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+"File.txt"));
        fos.write(" ");
        if (c.moveToFirst()){
            do {
                String note = c.getString(1)+"__+__";
                String title = c.getString(2)+"__+__";
                String ex = c.getString(3).replace("\n" , "++-++");
                fos.append("\n").append(note).append(title).append(ex);

            }while (c.moveToNext());
            c.close();
        }

          Toast.makeText(ct, "okkkkkkkkkkk", Toast.LENGTH_LONG).show();

          fos.close();
      } catch (Exception e) {
          Toast.makeText(ct, e.toString(), Toast.LENGTH_SHORT).show();
      }

    }


    public ArrayList<Model> get_Notes_2(String searsh){
        SQLiteDatabase dp = getReadableDatabase();
        ArrayList<Model> s = new ArrayList<>();
         Cursor c = dp.rawQuery("SELECT * FROM " +NOTE_TB_NAME+ " WHERE "+ENGLISH_WORD +" Like '" +searsh + "%'", null);
        //Cursor c = dp.rawQuery("SELECT * FROM " +NOTE_TB_NAME+ " WHERE "+ENGLISH_WORD+"=?", new String[] {searsh});


        if (c.moveToFirst()){
            do {
                int id = c.getInt(0);
                String note = c.getString(1);
                String title = c.getString(2);
                String lock = c.getString(3);
                s.add(new Model(id ,note , title , lock , "old"));

                if(s.size() == 8){

                    break;

                }

            }while (c.moveToNext());
            c.close();
        }
        return s ;
    }
    public ArrayList<Model> get_Notes_22(String search){
        SQLiteDatabase dp = getReadableDatabase();
        ArrayList<Model> s = new ArrayList<>();
      //  Cursor c = dp.rawQuery("SELECT * FROM " +NOTE_TB_NAME+ " WHERE "+ENGLISH_WORD +" Like '" +search + "%'", null);
        Cursor c = dp.rawQuery("SELECT * FROM " +NOTE_TB_NAME+ " WHERE "+ENGLISH_WORD+"=?", new String[] {search});

        if (c.moveToFirst()){
            do {
                int id = c.getInt(0);
                String note = c.getString(1);
                String title = c.getString(2);
                String lock = c.getString(3);

                s.add( new Model(id ,note , title , lock , "old" ));

                if(s.size() == 8){

                    break;

                }

            }while (c.moveToNext());
            c.close();
        }
        return s ;
    }
    public ArrayList<Model> get_Notes_get_english_words(String searsh){
        SQLiteDatabase dp = getReadableDatabase();
        ArrayList<Model> s = new ArrayList<>();
        Cursor c = dp.rawQuery("SELECT * FROM " +NOTE_TB_NAME+ " WHERE "+ARABIC_WORD +" Like '" +searsh + "%'", null);
        //Cursor c = dp.rawQuery("SELECT * FROM " +NOTE_TB_NAME+ " WHERE "+ENGLISH_WORD+"=?", new String[] {searsh});

        if (c.moveToFirst()){
            do {
                int id = c.getInt(0);
                String note = c.getString(1);
                String title = c.getString(2);
                String lock = c.getString(3);
                s.add(new Model(id ,note , title , lock , "old" ));
                if (s.size() == 8){
                    break;
                }

            }while (c.moveToNext());
            c.close();
        }
        return s ;
    }
    public ArrayList<Model> get_Notes_get_english_words2(String search){
        SQLiteDatabase dp = getReadableDatabase();
        ArrayList<Model> s = new ArrayList<>();
       // Cursor c = dp.rawQuery("SELECT * FROM " +NOTE_TB_NAME+ " WHERE "+ARABIC_WORD +" Like '" +searsh + "%'", null);
        Cursor c = dp.rawQuery("SELECT * FROM " +NOTE_TB_NAME+ " WHERE "+ARABIC_WORD+"=?", new String[] {search});

        if (c.moveToFirst()){
            do {
                int id = c.getInt(0);
                String note = c.getString(1);
                String title = c.getString(2);
                String lock = c.getString(3);

                Model model = new Model(id ,note , title , lock , "old" );
                s.add(model);
                if (s.size() == 8){
                    break;
                }

            }while (c.moveToNext());
            c.close();
        }
        return s ;
    }
    public ArrayList<Model> search_En(String english_word){
        SQLiteDatabase dp = getReadableDatabase();
        ArrayList<Model> s = new ArrayList<>();
        Cursor c = dp.rawQuery("SELECT * FROM " +NOTE_TB_NAME+ " WHERE "+ENGLISH_WORD+"=?", new String[] {english_word});
        if (c.moveToFirst()){
            do {
                int id = c.getInt(0);
                String note = c.getString(1);
                String title = c.getString(2);
                String lock = c.getString(3);

                Model model = new Model(id ,note , title , lock , "old" );
                s.add(model);

            }while (c.moveToNext());
            c.close();
        }
        return s ;
    }

    public Model getWords (int x) {
        try {
            SQLiteDatabase dp = getReadableDatabase();
            Cursor c = dp.rawQuery("SELECT * FROM " + NOTE_TB_NAME, null);
            Model model ;
            c.move(x);
            int id = c.getInt(0);
            int vvvv = c.getPosition();
            String note = c.getString(1);
            String title = c.getString(2);
            String lock = c.getString(3);
            model = new Model(id ,note , title , lock , "old" );
            c.close();

            return model;

        }catch (Exception e){

            Toast.makeText(ct, String.valueOf(e), Toast.LENGTH_SHORT).show();
            return null ;
        }
    }


    public ArrayList<Model> mmm(){
        SQLiteDatabase dp = getReadableDatabase();
        ArrayList<Model> s = new ArrayList<>();
        Cursor c = dp.rawQuery("SELECT * FROM " + NOTE_TB_NAME, null);

        if (c.moveToFirst()){
            do {
                int id = c.getInt(0);
                String note = c.getString(1);
                String title = c.getString(2);
                String lock = c.getString(3);

                Model model = new Model(id ,note , title , lock , "old" );
                s.add(model);

            }while (c.moveToNext());
            c.close();
        }
        return s ;
    }

}
