package com.example.translation;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.translation.Adapters.Adapter;
import com.example.translation.Database_All.Database;
import com.example.translation.Database_All.Database_2;
import com.example.translation.Database_All.Database_3;
import com.example.translation.models.Model;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Locale;

public class split extends AppCompatActivity {

    TextToSpeech textToSpeech;
    EditText textset , textget  ;
    TextView drawer_text ;
    ImageButton buttonnn  ;
    Button button, buttonn;
    Database db ;
    Database_3 db_3 ;
    CollapsingToolbarLayout collapsingToolbarLayout ;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    Adapter adapter ;
    int id ;
    SharedPreferences shard ;
    SharedPreferences.Editor editor ;
    int D , f;

    NavigationView navigationView ;
    private DrawerLayout drawerLayout ;
    String  ba , rn , english , en_test;
    ClipboardManager clipboard ;
    Words words ;
    Words_2 words_2 ;
    String [] wor , ad ;
    String[] All_Words ;
    int co , ccc , Count;
    ArrayList<Model> models ;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split);

        models = new ArrayList<>();
        drawerLayout = findViewById(R.id.drwerrf);
        navigationView = findViewById(R.id.drawer2);

        recyclerView = findViewById(R.id.nested); //textView = findViewById(R.id.text_notes_count);
        collapsingToolbarLayout=findViewById(R.id.collapsing_toolbarr);

        buttonn= findViewById(R.id.buttoncv);
        //  button.setVisibility(View.INVISIBLE);
        buttonn.setVisibility(View.INVISIBLE);
        buttonnn = findViewById(R.id.buttonvv); buttonnn.setVisibility(View.INVISIBLE);

        textget=findViewById(R.id.texevew1);  textset=findViewById(R.id.text11);

        shard = getSharedPreferences("save file", Context.MODE_PRIVATE);
        editor = shard.edit();

        words = new Words();
        words_2 = new Words_2() ;
        wor = words_2.get_words();
        All_Words = words_2.get_words();

        db_3 = new Database_3(this);

        textget.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if_text_found();
                search(textget.getText().toString());

                //   delete_test(textget.getText().toString());
            }
        });
        textset.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if_text_found();
            }
        });

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
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

        if (navigationView != null && drawerLayout instanceof DrawerLayout) {
            drawerLayout = (DrawerLayout)drawerLayout;
            drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(View view, float v) { }
                @Override
                public void onDrawerOpened(View view) { }
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onDrawerClosed(View view) {
                    swith();
                }
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onDrawerStateChanged(int i) {

                    int currentNightMode = getResources().getConfiguration().uiMode
                            & Configuration.UI_MODE_NIGHT_MASK;

                    if (D == 0 || D==1) {
                        switch (f) {
                            case 1 :if (currentNightMode == Configuration.UI_MODE_NIGHT_YES){
                                getWindow().setStatusBarColor(Color.parseColor("#111113"));
                                break;
                            }else{getWindow().setStatusBarColor(Color.parseColor("#616161")); break;}
                            case 2 : getWindow().setStatusBarColor(Color.parseColor("#313843")); break;
                            case 3 : getWindow().setStatusBarColor(Color.parseColor("#070707")); break;
                        }   D ++ ; }  else {
                        swith();
                        if (D==2){  D ++ ;}else { D = 0 ; }
                    }
                }
            });
        }

        db=new Database(this);
        id = Integer.parseInt(String.valueOf(db.getWordsCount()));
        try {
            //   copy(wor[co+2]);
        }catch (Exception e){
            Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
        }

        co = shard.getInt("db_count" , 1);
        Count = shard.getInt("db_count" , 1);
        // co = 1 ;
        clipboard = (ClipboardManager) this.getSystemService(CLIPBOARD_SERVICE);
        clipboard.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onPrimaryClipChanged() {
                String coped = clipboard.getText().toString();
                editor = shard.edit();

                String en = search_En(wor[co]);

                if (!en.equals(wor[co])) {
                    if (ccc != 1) {
                        if (words.cheek_of_char(coped).equals("ar")) {
                            Model model = new Model(english, coped, "", "normal");
                            //   String saerched = search_by_word(a);
                            //  if (!saerched.equals(a)) {
                            speak(model.getArabic_word());
                            db_3.insert_word(model);
                            Toast.makeText(split.this, String.valueOf(shard.getInt("db_count", 1)), Toast.LENGTH_SHORT).show();

                            //   } else {
                            //    Toast.makeText(MainActivity.this, "مكررة", Toast.LENGTH_SHORT).show();
                            //   }
                        }
                        ccc = 0;
                        copy(wor[co]);
                        co++;
                        editor.putInt("db_count", co);
                        editor.apply();
                    } else {

                        ccc = 0;

                    }
                }else {

                    co++;
                    editor.putInt("db_count", co);
                    editor.apply();
                    copy(wor[co]);
                    Toast.makeText(split.this,  String.valueOf(shard.getInt("db_count", 1))+"الكلمة الانجليزية موجودة", Toast.LENGTH_SHORT).show();
                }

//                if (words.cheek_of_char(coped).equals("ar")) {
//
//                    Model model = new Model(english, coped, "", "normal");
//
//                    if (model_exist(model, All_Words[Count])) {
//                        Toast.makeText(MainActivity.this, "موجود", Toast.LENGTH_SHORT).show();
//                    } else {
//                        db_3.insert_word(model);
//                        speak(english);
//                        Toast.makeText(MainActivity.this, String.valueOf(shard.getInt("db_count", 1)), Toast.LENGTH_SHORT).show();
//
//                    }
//
//                    new_word();
//
//                }


            }
        });

        show("1");
        swith();
    }
    ////////////////////////////////////////////////////////////////
    public boolean model_exist(Model model , String text){

        ArrayList<Model> s = new ArrayList<>();
        s = db_3.search_En(text);

        if(s.size()>0) {
            Compare_Models c = new Compare_Models(s.get(0), model);

            Toast.makeText(this, String.valueOf(c.isaBoolean()), Toast.LENGTH_SHORT).show();
            return c.isaBoolean();

        }else {
            return false ;
        }
    }

    public void new_word(){
        Count++;
        editor.putInt("db_count", Count);
        editor.apply();
        copy(All_Words[Count]);

    }

    private void copy(String text) {
        ccc = 1 ;
        english = text ;

        ClipData clip = ClipData.newPlainText("Edit text" , text );
        clipboard.setPrimaryClip(clip);
        clip.getDescription();
    }

    public void btn_savee(View view) {

        buttonn.setVisibility(View.INVISIBLE);
        buttonnn.setVisibility(View.INVISIBLE);
String sp = textget.getText().toString();
//String[] spp = sp.split("[ّ !~ٌ ِ َ ٌ ْ ;,.\n\"]");
        String[] spp = sp.split("_ [ّ !~ٌ ِ َ ٌ ْ ;,.]");
String c ;String v = "" ;
for(int x=0 ; x<spp.length ; x++){
    c = spp[x];
    v = v+""+c ;
        }
textset.setText(v);
        Toast.makeText(this, String.valueOf(spp.length), Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();


    }
    public void show (String s){

        adapter = new Adapter(this , models , s , new Adapter.ItemClickListener() {
            @Override
            public void onItemClick(Model model , int position ) {
                shard.getString("Accent" , "UK");
                speak(model , shard.getString("Accent" , "UK"));

                Toast.makeText(split.this, String.valueOf(wor.length), Toast.LENGTH_SHORT).show();

            }
        });

        if (shard.getString("Span" , "2").equals("2")){
            layoutManager = new StaggeredGridLayoutManager( 2 , StaggeredGridLayoutManager.VERTICAL);
        }else {
            layoutManager = new StaggeredGridLayoutManager( 1 , StaggeredGridLayoutManager.VERTICAL);
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull  RecyclerView.ViewHolder viewHolder, int direction) {
                Model model = db.getWords(viewHolder.getAdapterPosition()+1);
                db.delete_word(model);

                adapter.notifyDataSetChanged();
                Toast.makeText(split.this ,  "تم الحذف", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull  RecyclerView.ViewHolder viewHolder, int direction) {
                Model model = db.getWords(viewHolder.getAdapterPosition()+1);

                Database_2 database_2 = new Database_2(split.this);

                database_2.insert_word(model);

                db.delete_word(model);

                adapter.notifyDataSetChanged();
                Toast.makeText(split.this ,  "تم ادراجها ضمن الكلمات المحفوظه", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


    }

    private void speak(Model model , String accent) {
        textToSpeech.setLanguage(new Locale("en" , accent ));
        textToSpeech.speak(model.getArabic_word(), TextToSpeech.QUEUE_FLUSH, null);
    }


    public void if_text_found (){
        String text_get = textget . getText().toString();
        String text_set = textset . getText().toString();
        if (text_get.isEmpty() || text_set.isEmpty()){
            buttonnn.setVisibility(View.INVISIBLE);
            //   button.setVisibility(View.INVISIBLE);
            buttonn.setVisibility(View.INVISIBLE);
        }else{

            buttonn.setVisibility(View.VISIBLE);
            buttonnn.setVisibility(View.VISIBLE);
            //   button.setVisibility(View.VISIBLE);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    public void swith  (){

        SharedPreferences shrdd = getSharedPreferences("trans file", Context.MODE_PRIVATE);
        ba = shrdd.getString("theme_number" , "day");
        switch (ba){

            case "one"   : Set_Activity_color("#7E91AF"); break;
            case "tow"   : Set_Activity_color("#141E37"); break;
            case "three" : Set_Activity_color("#4563C7"); break;
            case "normal" : Set_Activity_color("normal") ;  f = 1;break;
            case "materal" : Set_Activity_color("materal"); f = 2;break;
            case "image" : Set_Activity_color("image") ;    f = 3;break;
            case "day" : Set_Activity_color("day") ;        f = 1;break;
            case "night" : Set_Activity_color("night") ;    f = 1;break;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    public void Set_Activity_color (String color){

        if (color.equals("normal")) {

            getWindow().setStatusBarColor(ContextCompat.getColor(split.this, R.color.white100));
            View vew = this.getWindow().getDecorView();
            vew.setBackgroundColor(ContextCompat.getColor(split.this, R.color.white100));
            collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(split.this, R.color.white100));
            //   vew.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.back2));
        }else if (color.equals("materal")){

            getWindow().setStatusBarColor(Color.parseColor("#7B8DAB"));
            View vew = this.getWindow().getDecorView();
            vew.setBackground(ContextCompat.getDrawable(split.this, R.drawable.packgroundwall));
            //  navigationView.setBackgroundColor(ContextCompat.getColor(MainActivity.this , R.color.good3));
            collapsingToolbarLayout.setContentScrimColor(Color.parseColor("#7B8DAB"));
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            navigationView.setBackground(ContextCompat.getDrawable(split.this, R.drawable.materialtextback));

        } else if (color.equals("image")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            getWindow().setStatusBarColor(ContextCompat.getColor(split.this, R.color.facecolor));
            View veww = this.getWindow().getDecorView();
            collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(split.this, R.color.facecolor));
            veww.setBackgroundColor(ContextCompat.getColor(split.this, R.color.facecolor));
        }else if (color.equals("night")){

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            getWindow().setStatusBarColor(ContextCompat.getColor(split.this, R.color.normal_theme_color_1));
            View vew = this.getWindow().getDecorView();
            vew.setBackgroundColor(ContextCompat.getColor(split.this, R.color.normal_theme_color_1));
            collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(split.this, R.color.normal_theme_color_1));
        }else if (color.equals("day")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            getWindow().setStatusBarColor(ContextCompat.getColor(split.this, R.color.normal_theme_color_1));
            View vew = this.getWindow().getDecorView();
            vew.setBackgroundColor(ContextCompat.getColor(split.this, R.color.normal_theme_color_1));
            collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(split.this, R.color.normal_theme_color_1));

        } else{

            getWindow().setStatusBarColor(Color.parseColor(color));
            View vew = this.getWindow().getDecorView();
            vew.setBackgroundColor(Color.parseColor(color));
            collapsingToolbarLayout.setContentScrimColor(Color.parseColor(color));
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void main_menuer(View view) {
        OpenDrawer();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void OpenDrawer() {
        navigationView = findViewById(R.id.drawer2);
        drawerLayout.openDrawer(Gravity.RIGHT);
        drawer_text =  findViewById(R.id.sicoo);
        drawer_text.setText("Words Count "+String.valueOf(db.getWordsCount()));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home0:
                        Intent intent = new Intent(split.this, theme.class);
                        startActivity(intent);break;
                    case R.id.home2:
                        Intent i_2 = new Intent(split.this, settings.class);
                        startActivity(i_2);;break;
                    case R.id.home5:
                        //Intent intent1 = new Intent(MainActivity.this ,Amr.class) ;
                        //startActivity(intent1);break;
                    case R.id.home4:
                        //Intent intentt = new Intent(MainActivity.this, ChangePassword.class);
                        //startActivity(intentt);break;
                    case R.id.home7:
                        Intent intenttt = new Intent(split.this, All_Words.class);
                        startActivity(intenttt);break;

                    //Intent intentttr = new Intent(MainActivity.this, Students.class);
                    //startActivity(intentttr);break;
                }
                return true;
            }
        });
    }

    public void Favorite_page(View view) {
        Intent intent = new Intent(split.this , Favorite.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void read_button(View view) {

        int count = Integer.parseInt(String.valueOf(db.getWordsCount()));
        for (int c=1 ; c <= count ; c++ ){
            Model model = db.getWords(c);
            speak(model.getArabic_word());

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void speak(String text) {
        textToSpeech.setLanguage(new Locale("en" , shard.getString("Accent" , "UK")));
        textToSpeech.speak(text, TextToSpeech.STOPPED, null);
    }

    @SuppressLint("RtlHardcoded")
    @Override
    protected void onStop() {
        super.onStop();
        drawerLayout.closeDrawer(Gravity.RIGHT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    public ArrayList<Model> search(String text){
        try {
            models = db_3.get_Notes(text);
            if(models.size()!=0) {

                Model model_2 = models.get(0);
                textset.setText(model_2.getArabic_word());
                Toast.makeText(this, String.valueOf(models.size()), Toast.LENGTH_SHORT).show();
                show("2");

            }else {
                show("1");
            }
        }catch (Exception e){

            Toast.makeText(this,String.valueOf(e), Toast.LENGTH_SHORT).show();
        }
        return models ;
    }

    public void sex (){

        try {
            // حذف حدد الكلمات المطلوبة من الكلمات ال dp
            // او نقلها الي Database_3
            Database_3 database_3 = new Database_3(this);
            for (int c=5; c<=100 ;++c ){

                Model model = db.getWords(c);
                //  database_3.insert_word(model);   // للنقل
                db.delete_word(model); // للحذف

            }
        }catch (Exception e){
            Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
        }

    }

    public String search_by_word(String text){
        ArrayList<Model> s = new ArrayList<>();

        String ss  = "a" ;
        try {
            s = db_3.get_Notes(text);
            if(s.size()!=0) {

                Model model_2 = s.get(0);
                ss = model_2.getEnglish_word();
                textset.setText(model_2.getArabic_word());
                Toast.makeText(this, String.valueOf(s.size()), Toast.LENGTH_SHORT).show();

            }
        }catch (Exception e){

            Toast.makeText(this,String.valueOf(e), Toast.LENGTH_SHORT).show();
        }
        return ss ;
    }
    public String search_En(String english){
        ArrayList<Model> s = new ArrayList<>();

        String ss  = "a" ;
        try {
            s = db_3.search_En(english);
            if(s.size()!=0) {
                Model model_2 = s.get(0);
                ss = model_2.getArabic_word();
            }
        }catch (Exception e){

            Toast.makeText(this,String.valueOf(e), Toast.LENGTH_SHORT).show();
        }
        return ss ;
    }

    public void delete_test (String text){
        // حذف الكلمات المكررة في ال Database_3
        Database_3 database_3 = new Database_3(this);
        ArrayList<Model> models  ;
        for(int x=1 ; x <Integer.parseInt(String.valueOf(database_3.getWordsCount())) ; ++x ) {
            Model model = database_3.getWords(x);
            models = search(model.getEnglish_word());
            for (int c = 1; c < models.size(); ++c) {

                database_3.delete_word(models.get(c));

            }
        }
    }
}
