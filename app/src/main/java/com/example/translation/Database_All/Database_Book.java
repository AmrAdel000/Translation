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

public class Database_Book extends SQLiteOpenHelper {

    public static final String DP_name = "notes" ;
    public static final int DP_Vr = 3 ;
    public static final String NOTE_TB_NAME = "Note";
    public static final String NOTE_CLN_ID = "id";
    public static final String NOTE_CLN_NOTE = "note";
    public static final String NOTE_CLN_TITLE = "title";
    public static final String NOTE_CLN_LOCK = "lock";
    Context ct;

    public Database_Book(Context context) {
 super (context , DP_name , null , DP_Vr );
     ct = context ;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE "+ NOTE_TB_NAME +" ("+ NOTE_CLN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NOTE_CLN_NOTE +" TEXT, "+ NOTE_CLN_TITLE +" TEXT, "+ NOTE_CLN_LOCK +" TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NOTE_TB_NAME);
        onCreate(sqLiteDatabase);

    }
    // الاضافه
    public Integer insert_note (Model_Book model){

        SQLiteDatabase dp = getWritableDatabase();
        ContentValues valuse = new ContentValues();
        valuse.put(NOTE_CLN_NOTE,model.getNote());
        valuse.put(NOTE_CLN_TITLE,model.getTitle());
        valuse.put(NOTE_CLN_LOCK,model.getLock());

        long result = dp.insert(NOTE_TB_NAME,null,valuse);

return Integer.parseInt(String.valueOf(result));

    }
    // التعديل

    public void update_note (Model_Book model) {

        SQLiteDatabase dp = getWritableDatabase();

        ContentValues valuse = new ContentValues();
        valuse.put(NOTE_CLN_NOTE, model.getNote());
        valuse.put(NOTE_CLN_TITLE, model.getTitle());
        valuse.put(NOTE_CLN_LOCK, model.getLock());

        String args []= { String.valueOf(model.getId())};
        int result = dp.update(NOTE_TB_NAME, valuse, "id=?", args);


    }
    public void delete_note (Model_Book model) {

        SQLiteDatabase dp = getWritableDatabase();

        String args [] = { String.valueOf(model.getId())};
        int result = dp.delete(NOTE_TB_NAME, "id=?",args);
    }



    // ارجاع عدد الصفوف في اي جدول
    public long getNotesCount (){
        SQLiteDatabase dp = getReadableDatabase();
       return DatabaseUtils.queryNumEntries(dp , NOTE_TB_NAME) ;

    }

    public Integer cheek_if_exists (int position){
        SQLiteDatabase dp = getReadableDatabase();
        Cursor c = dp.rawQuery("SELECT * FROM " + NOTE_TB_NAME, null);
               c.move(position);
        int if_exists = c.getInt(0);
        return if_exists ;
    }


    public ArrayList<Model> get_Notes(){
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

    public Model_Book bitmaps (int x) {
        try {
            SQLiteDatabase dp = getReadableDatabase();
            Cursor c = dp.rawQuery("SELECT * FROM " + NOTE_TB_NAME, null);
            Model_Book model ;
            c.move(x);
            int id = c.getInt(0);
            int vvvv = c.getPosition();
            String note = c.getString(1);
            String title = c.getString(2);
            String lock = c.getString(3);
            model = new Model_Book(id ,note , title , lock , "old" );
            c.close();

            return model;

        }catch (Exception e){

            Toast.makeText(ct, String.valueOf(e), Toast.LENGTH_SHORT).show();
            return null ;
        }
    }

    public void change(Model_Book model) {

        Model_Book model1 = bitmaps(2);
        SQLiteDatabase dp = getWritableDatabase();
        ContentValues valuse = new ContentValues();
        valuse.put(NOTE_CLN_NOTE,model.getNote());
        valuse.put(NOTE_CLN_TITLE,model.getTitle());
        valuse.put(NOTE_CLN_LOCK,model.getLock());

        String[] args = { String.valueOf(model.getId())};

       // int result =  dp.updateWithOnConflict(NOTE_TB_NAME , valuse , "id=?" , args , 1);
       // Toast.makeText(ct, String.valueOf(result), Toast.LENGTH_SHORT).show()
        dp.insertWithOnConflict(NOTE_TB_NAME , null , valuse ,1);
        delete_note(model);

    }



}
