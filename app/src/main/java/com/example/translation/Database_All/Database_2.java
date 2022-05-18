package com.example.translation.Database_All;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.translation.models.Model;

import java.util.ArrayList;

public class Database_2 extends SQLiteOpenHelper {

    public static final String DP_name = "Translate_Favorite" ;
    public static final int DP_Vr = 4 ;
    public static final String NOTE_TB_NAME = "Words_Favorite";
    public static final String NOTE_CLN_ID = "id";
    public static final String ARABIC_WORD = "Arabic_word";
    public static final String ENGLISH_WORD = "English_word";
    public static final String EXAMBLE = "Examble";
    public static final String COLOR = "Color";

    Context ct;

    public Database_2(Context context) {
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

    public ArrayList<Model> get_Notes_main() {
        SQLiteDatabase dp = getReadableDatabase();
        ArrayList<Model> s = new ArrayList<>();
        Cursor c = dp.rawQuery("SELECT * FROM " + NOTE_TB_NAME, null);
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(0);
                String note = c.getString(1);
                String title = c.getString(2);
                String lock = c.getString(3);

                Model model = new Model(id, note, title, lock, "old");
                s.add(model);
            } while (c.moveToNext());
            c.close();
        }
        return s;
    }

}
