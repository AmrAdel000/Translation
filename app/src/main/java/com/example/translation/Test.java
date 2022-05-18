package com.example.translation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.translation.All_Translated_Words.Control;
import com.example.translation.Database_All.Data_Test;
import com.example.translation.Database_All.Database_3;
import com.example.translation.models.Model;
import com.example.translation.models.mod;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Test extends AppCompatActivity {


    EditText name , age , search ;
    Data_Test db ;

    ArrayList<mod> mods ;
    Database_3 db_3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        name = findViewById(R.id.editTextTextMultiLine);
        age = findViewById(R.id.editTextTextMultiLine2);
        search = findViewById(R.id.editTextTextMultiLine3);
        db= new Data_Test(this);
        db_3 = new Database_3(this);
        mods = new ArrayList<>();

    }

    public void inset(View v){
       // db.insert_model(new Model(name.getText().toString() , age.getText().toString() , "" , ""));
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, 0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
// TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri targetUri = data.getData();
            Bitmap bitmap;



            try {

                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));



               String s = TextExtractUtil.getText(this, bitmap);

                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();




            } catch (Exception e) {
                // TODO Auto-generated catch block

                Toast.makeText(this,e.toString(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    public String get_val(String position){
       String text = db.search(position);
        return text ;
    }


    public void get(View view) {
        Toast.makeText(this,get_val(search.getText().toString()), Toast.LENGTH_SHORT).show();
    }


    public void loaf_to_firebasee(View view){

     //   WriteText();

       db_3.load_words();

    }

    public void get_data (View view){

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                    Control control = new Control();
                    String [] s = control.getSplit();
                    //Toast.makeText(Test.this, String.valueOf(s.length), Toast.LENGTH_SHORT).show();
                    for (int c=0 ; c<s.length ; ++c){
                        String [] ss = s[c].replace("+" , "").split("----");
                        int finalC = c;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    db.insert_model(new Model(ss[0] , ss[1] , "sc" , "axa"));
                                    name.setText(String.valueOf(finalC));
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                    // Toast.makeText(Test.this,"خلصصصص", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });
            t.start();

    }

    private void writeToFile(String data, Context context) throws FileNotFoundException {
//        try {
//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("coonfig.txt", Context.MODE_PRIVATE));
//            outputStreamWriter.write(data);
//            outputStreamWriter.close();
//        }
//        catch (IOException e) {
//            Log.e("Exception", "File write failed: " + e.toString());
//        }
////////////////////////////////////
//        File path = context.getExternalFilesDir(null);
//
//        File file = new File(path, "my-file-name.txt");
//
//
//        FileOutputStream stream = new FileOutputStream(file);
//        try {
//            stream.write("text-to-write".getBytes());
//            stream.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//        }
    }

    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    public void count(View view) {
        Toast.makeText(this, String.valueOf(db.getWordsCount()), Toast.LENGTH_SHORT).show();
    }


    public static class Demo00001 {
        public Demo00001() {
            // Create a text file in Java
            try( PrintWriter pw = new PrintWriter(
                    new File( "SomeFile.txt" ) ) ) {
                pw.println( "This is SomeFile.txt" );
                pw.println();
                pw.println( "This is a test of text file." );
                pw.println( "Hello everybody." );
                pw.println( "Happy programming." );
            } catch( IOException ex ) {
                ex.printStackTrace();
            }

        }
        public static void main( String... args ) {
            new Demo00001();
        }
    }

    public void createFile(String sFileName, String sBody){
        try
        {
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
        }
        catch(IOException e)
        {
            e.printStackTrace();

        }
    }

    public void WriteText() {
        try {
            BufferedWriter fos = new BufferedWriter(new FileWriter(Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+"File.txt"));
            fos.write("Aaalsmscldmvl");
            fos.append("Ali");
            fos.close();
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG);
        } catch (Exception e) {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG);
        }
    }


    public void ssss () {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd");
        Date now = new Date();
        String fileName = formatter.format(now) + ".txt";//like 2016_01_12.txt

        try {
            File root = new File(Environment.getExternalStorageDirectory() + File.separator + "Music_Folder", "Report Files");
            //File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, fileName);

            FileWriter writer = new FileWriter(gpxfile, true);
            writer.append("sBody" + "\n\n");
            writer.flush();
            writer.close();
            Toast.makeText(this, "Data has been written to Report File", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();

        }


    }
}