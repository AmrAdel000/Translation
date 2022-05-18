package com.example.translation;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.translation.Database_All.Database;
import com.example.translation.Database_All.Database_3;
import com.example.translation.models.Model;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

public class All_Functions {

    Context context ;
    @SuppressLint("StaticFieldLeak")
    static Database_3 db_3 ;
    static ArrayList<Model> models ;
    TextToSpeech textToSpeech;

    SharedPreferences shard ;
    SharedPreferences.Editor editor ;

    Button btn_save_sentence ;


    public All_Functions(Context context) {
        this.context = context;
        db_3 = new Database_3(context) ;

        shard = context.getSharedPreferences("save file", Context.MODE_PRIVATE);
        editor = shard.edit();

        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                if (i == TextToSpeech.SUCCESS){
                    int result =   textToSpeech.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS" , "Language not supported");
                    }
                }else {
                    Log.e("TTS" , "Initialization Filed");
                }
            }
        });

    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void speak(String text) {
        textToSpeech.setLanguage(new Locale("en" , shard.getString("Accent" , "UK")));
        textToSpeech.speak(text, TextToSpeech.STOPPED, null);
    }


    public void loaf_to_firebase(){
        DatabaseReference mood = FirebaseDatabase.getInstance().getReference("Words").child("All_words");

        try {

            for (int x = 1; x < Integer.parseInt(String.valueOf(db_3.getWordsCount())); x++) {
                Model model = db_3.getWords(x);
                mood.push().setValue(model);

            }
        }catch (Exception e){
            Toast.makeText(context, String.valueOf(e), Toast.LENGTH_SHORT).show();
        }
    }

    public void delete_from_firebase(){
        FirebaseDatabase.getInstance().getReference("Words").removeValue();

    }

    public static String search_en2(String text) {

        models = db_3.get_Notes_get_english_words2(text);
        String t = "null" ;
        if (models.size() != 0) {

            Model model_2;
            String one = "";
            for (int c = 0; c < models.size(); ++c) {
                model_2 = models.get(c);
                one = one + "," + model_2.getEnglish_word();
            }
            t = one.replaceFirst(",", "");
        }
        return  t ;
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Dialog setMargins(Dialog dialog, int marginLeft, int marginTop, int marginRight, int marginBottom )
    {
        Window window = dialog.getWindow();
        if ( window == null ) return dialog;

        Context context = dialog.getContext();
        // set dialog to fullscreen
        RelativeLayout root = new RelativeLayout( context );
        root.setLayoutParams( new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT ) );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( root );
        // set background to get rid of additional margins
        // window.setBackgroundDrawable( new ColorDrawable( Color.WHITE ) );

        // apply left and top margin directly
        window.setGravity( Gravity.RIGHT|Gravity.TOP);
        //window.setGravity( Gravity.CENTER_HORIZONTAL);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.x = marginLeft;
        attributes.y = marginTop;
        window.setAttributes( attributes );

        // set right and bottom margin implicitly by calculating width and height of dialog
        Point displaySize = getDisplayDimensions( context );
        int width = displaySize.x - marginLeft - marginRight;
        int height = displaySize.y - marginTop - marginBottom;
        window.setLayout( width, height );

        return dialog;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @NonNull
    public static Point getDisplayDimensions(Context context )
    {
        WindowManager wm = ( WindowManager ) context.getSystemService( Context.WINDOW_SERVICE );
        Display display = wm.getDefaultDisplay();

        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics( metrics );
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;

        // find out if status bar has already been subtracted from screenHeight
        display.getRealMetrics( metrics );
        int physicalHeight = metrics.heightPixels;
        int statusBarHeight = getStatusBarHeight( context );
        int navigationBarHeight = getNavigationBarHeight( context );
        int heightDelta = physicalHeight - screenHeight;
        if ( heightDelta == 0 || heightDelta == navigationBarHeight )
        {
            screenHeight -= statusBarHeight;
        }

        return new Point( screenWidth, screenHeight );
    }
    public static int getStatusBarHeight( Context context )
    {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier( "status_bar_height", "dimen", "android" );
        return ( resourceId > 0 ) ? resources.getDimensionPixelSize( resourceId ) : 0;
    }

    public static int getNavigationBarHeight( Context context )
    {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier( "navigation_bar_height", "dimen", "android" );
        return ( resourceId > 0 ) ? resources.getDimensionPixelSize( resourceId ) : 0;
    }
    private View openDialog() {
        Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
        View view = layoutInflaterAndroid.inflate(R.layout.lay, null);
        dialog.setContentView(view);
        dialog.show();
        return  view ;
    }



    public int get_word_position_in_models(String word , ArrayList<Model> models){
        int c = 0;
        for (Model model : models) {
            c ++ ;
            if (word.equals(model.getArabic_word())) {
                break;
            }
        }
        return c;
    }


    public ArrayList<Model>  delete_word_from_dp(View vi , Database db ,ArrayList<Model> models ){

        TextView textView1 =vi.findViewById(R.id.textView);
        String text = textView1.getText().toString() ;
        Model model = db.search_for_en(text);
        db.delete_word(model);
        models.remove(get_word_position_in_models(text , models)-1);
        Toast.makeText(context ,  "تم الحذف", Toast.LENGTH_SHORT).show();
        return models ;
    }

    public boolean chek_if_exist(String word , ArrayList<Model> models){

        boolean bol = false ;
        for (Model model : models) {
            if (word.equals(model.getArabic_word())) {
                bol = true;
                break;
            }
        }
        return bol;
    }
    public boolean chek_if_exist(String word , String [] all){

        boolean bol = false ;
        for (String text : all) {
            if (word.equals(text)) {
                bol = true;
                break;
            }
        }
        return bol;
    }


    public View set_Span(String [] all_2 , ArrayList<Model> models , int finalC , Dialog dialog , String Screen_mode){

        if(!all_2[finalC].equals("")) {

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);

            View view = layoutInflaterAndroid.inflate(R.layout.lay, null);


            Button btn_delete_sentence = view.findViewById(R.id.sentence_btn_2);
            btn_delete_sentence.setVisibility(View.INVISIBLE);

            TextView textView = view.findViewById(R.id.textView);
            textView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    speak(textView.getText().toString());
                }
            });

            btn_save_sentence = view.findViewById(R.id.sentence_btn);
            if (chek_if_exist(all_2[finalC] , models)) {
                btn_delete_sentence = view.findViewById(R.id.sentence_btn_2);
                btn_save_sentence.setVisibility(View.INVISIBLE);
                btn_delete_sentence.setVisibility(View.VISIBLE);
            }

            TextView textView1 = view.findViewById(R.id.textView4);

            textView.setText(all_2[finalC]);
            textView1.setText(All_Functions.search_en2(all_2[finalC].toLowerCase()));

            if (Screen_mode.equals("portrait")){
                All_Functions.setMargins(dialog, 280, 50, 280, 1380);
            }else {
                All_Functions.setMargins(dialog, 800, 50, 800, 300);
            }
            dialog.setContentView(view);
            dialog.show();
           return view;
        }
        return null ;
    }

}
