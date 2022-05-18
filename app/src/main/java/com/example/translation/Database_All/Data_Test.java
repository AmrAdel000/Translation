package com.example.translation.Database_All;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.translation.models.Model;

public class Data_Test extends SQLiteOpenHelper {

    public static final String DB_NAME = "Test";
    public static int Id = 2;

    public static final String DB_TP_NAME = "Test_1";
    public static final String NOTE_CLN_ID = "Id";
    public static final String ARABIC_WORD = "Arabic_word";
    public static final String ENGLISH_WORD = "English_word";
    public static final String EXAMBLE = "Examble";
    public static final String COLOR = "Color";
    Context context ;


    public Data_Test(@Nullable Context context) {
        super(context, DB_NAME, null, Id );
        this.context = context ;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE "+DB_TP_NAME +"(" + NOTE_CLN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ARABIC_WORD +" TEXT, "+ ENGLISH_WORD +" TEXT, " + EXAMBLE + " TEXT , " + COLOR + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DB_TP_NAME);
        onCreate(db);
    }

    public long getWordsCount() {
        SQLiteDatabase dp = getReadableDatabase();
        return DatabaseUtils.queryNumEntries(dp, DB_TP_NAME);
    }

    public void insert_model(Model model){
        SQLiteDatabase dp = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ARABIC_WORD, model.getArabic_word());
        values.put(ENGLISH_WORD, model.getEnglish_word());
        values.put(EXAMBLE, model.getExample());
        values.put(COLOR, model.getColor());
        dp.insert(DB_TP_NAME, null, values);

        Toast.makeText(context, String.valueOf(getWordsCount()), Toast.LENGTH_SHORT).show();

    }

    public String search(String search) {
        SQLiteDatabase dp = getReadableDatabase();
        Cursor c = dp.rawQuery("SELECT * FROM " + DB_TP_NAME + " WHERE " + ENGLISH_WORD + "=?", new String[]{search});
        String n = "m";
        if (c.moveToFirst()){
            String note = c.getString(1);
            n = note ;
            c.close();
        }
        return n;
    }


}
