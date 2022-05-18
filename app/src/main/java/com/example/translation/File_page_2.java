package com.example.translation;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.translation.Adapters.Adapter_Files_2;
import com.example.translation.Adapters.Story_Adapter;
import com.example.translation.Database_All.Database;
import com.example.translation.Database_All.Database_2;
import com.example.translation.Database_All.Database_3;
import com.example.translation.Database_All.Database_files;
import com.example.translation.models.Model;
import com.example.translation.models.Model_Book;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class File_page_2 extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    TextToSpeech textToSpeech;
    EditText text_set, text_get;
    TextView drawer_text , sentence_text ;
    ImageButton button_copy ;
    Button button_save  , button_del;
    ScrollView scrollView ;
    Database db ;
    Database_3 db_3 ;
    Database_files db_files;
    CollapsingToolbarLayout collapsingToolbarLayout ;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView , recyclerView_2;
    Story_Adapter adapter  ;
    Adapter_Files_2 adapter_files ;
    SharedPreferences shard ;
    SharedPreferences.Editor editor ;
    NavigationView navigationView ;
    private DrawerLayout drawerLayout ;
    String  ba ,Screen_mode , english , one , kind_of_word ;
    ClipboardManager clipboard ;
    Words words ;
    String [] wor,all_2 ,All_Words , Main_words , Main_words_2 ;
    int co , ccc , Count , D, f, id , read_count , position ,c ;
    ArrayList<Model> models , models_2 ;
    CoordinatorLayout layout ;
    View vi , vi_2 ;
    Model_Book model ;
    AppBarLayout appBarLayout ;
    Dialog dialog ;

    SharedPreferences shard_2 ;
    SharedPreferences.Editor editor_2 ;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_page2);
        layout = findViewById(R.id.cvbn);
        models = new ArrayList<>();
        drawerLayout = findViewById(R.id.drwerrf);
        navigationView = findViewById(R.id.drawer2);

        recyclerView = findViewById(R.id.nested); //textView = findViewById(R.id.text_notes_count);
        recyclerView_2 = findViewById(R.id.res_2);
        collapsingToolbarLayout=findViewById(R.id.collapsing_toolbarr);
        appBarLayout = findViewById(R.id.barr);
        button_save = findViewById(R.id.buttoncv);
        button_save.setVisibility(View.INVISIBLE);
        button_copy = findViewById(R.id.buttonvv); button_copy.setEnabled(false);

        scrollView = findViewById(R.id.nestedc);
        scrollView.setVisibility(View.INVISIBLE);

        sentence_text = findViewById(R.id.texevew2a2);
        text_get =findViewById(R.id.texevew1);  text_set =findViewById(R.id.text11);

        shard = getSharedPreferences("save file", Context.MODE_PRIVATE);
        shard_2 = getSharedPreferences("Words", Context.MODE_PRIVATE);

        editor = shard.edit();
        editor_2 = shard_2.edit();
        words = new Words();

        db_3 = new Database_3(this);
        db_files = new Database_files(this);

        Screen_mode = "portrait";
        text_get.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                String text = text_get.getText().toString();
                if (words.cheek_of_char(text).equals("ar") && !kind_of_word.equals("sentence")) {

                    scrollView.setVisibility(View.INVISIBLE);
                    sentence_text.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);

                    // Toast.makeText(MainActivity.this, "aaaaa", Toast.LENGTH_SHORT).show();
                    search_2(text);
                    search_22(text);
                    kind_of_word = "ar";

                }else if(words.cheek_of_char(text).equals("en") && !kind_of_word.equals("sentence")){
                    scrollView.setVisibility(View.INVISIBLE);
                    sentence_text.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);

                    search_en(text);
                    search_en2(text);
                    kind_of_word = "en";
                    //  delete(text);
                }else if(words.cheek_of_char(text).equals("sentence") && !kind_of_word.equals("sentence")) {
                    scrollView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                    sentence_text.setVisibility(View.VISIBLE);
                    all_2 = text.split("[ \n,:.?()_’-]");
                    sentence_text.setMovementMethod(LinkMovementMethod.getInstance());
                    sentence_text.setText(addClickablePart(text));
                    kind_of_word = "sentence";
                    text_get.setText("إضغط علي كلمة ليتم ترجمتها");
                    appBarLayout.setExpanded(false, true);
                }else if(words.cheek_of_char(text).equals("")){

                    text_set.setText("");
                }
            }
        });
        text_set.addTextChangedListener(new TextWatcher() {
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

        //  View view = getWindow().getDecorView().findViewById(android.R.id.content);

//        layout.setOnTouchListener(new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//                Toast toast = Toast.makeText(
//                        getApplicationContext(),
//                        "View touched",
//                        Toast.LENGTH_LONG
//                );
//                toast.show();
//              //  autoClick(311.0f , 1362.0f);
//
//                return true;
//            }
//        });

        co = shard.getInt("db_count", 1);
        Count = shard.getInt("db_count", 1);
        // co = 1 ;
//        clipboard = (ClipboardManager) this.getSystemService(CLIPBOARD_SERVICE);
//        clipboard.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
//            public void onPrimaryClipChanged() {
//                String coped = clipboard.getText().toString();
//            //    editor = shard.edit();
//
//            //    String en = search_En(wor[co]);
////
////                if (!en.equals(wor[co])) {
////                    if (ccc != 1) {
////                        if (words.cheek_of_char(coped).equals("ar")) {
////                            Model model = new Model(english, coped, "", "normal");
////                         //   String saerched = search_by_word(a);
////                          //  if (!saerched.equals(a)) {
////                                speak(model.getArabic_word());
////                                db_3.insert_word(model);
////                                Toast.makeText(MainActivity.this, String.valueOf(shard.getInt("db_count", 1)), Toast.LENGTH_SHORT).show();
////
////                         //   } else {
////                            //    Toast.makeText(MainActivity.this, "مكررة", Toast.LENGTH_SHORT).show();
////                         //   }
////                        }
////                        ccc = 0;
////                        copy(wor[co]);
////                        co++;
////                        editor.putInt("db_count", co);
////                        editor.apply();
////                    } else {
////
////                        ccc = 0;
////
////                    }
////                }else {
////
////                    co++;
////                    editor.putInt("db_count", co);
////                    editor.apply();
////                    copy(wor[co]);
////                    Toast.makeText(MainActivity.this,  String.valueOf(shard.getInt("db_count", 1))+"الكلمة الانجليزية موجودة", Toast.LENGTH_SHORT).show();
////                }
//
////                if (words.cheek_of_char(coped).equals("ar")) {
////
////                    Model model = new Model(english, coped, "", "normal");
////
////                    if (model_exist(model, All_Words[Count])) {
////                        Toast.makeText(MainActivity.this, "موجود", Toast.LENGTH_SHORT).show();
////                    } else {
////                        db_3.insert_word(model);
////                        speak(english);
////                        Toast.makeText(MainActivity.this, String.valueOf(shard.getInt("db_count", 1)), Toast.LENGTH_SHORT).show();
////
////                    }
////
////                    new_word();
////
////                }
//
////                String[] split  = coped.split("- ");
////                if(split.length == 2){
////                    one = coped ;
////                }else {
////                    start(coped);
////                    Toast.makeText(MainActivity.this, "تم الارسال", Toast.LENGTH_SHORT).show();
////                }
//
//                String[] split_2  = coped.split("\n");
//                String[] split  = coped.split("\n\n");
//
//                if(split_2.length>1) {
//                  //  last(split);
//                    from_arabic_to_english_app(split_2);
//                }
//
//
//            }
//        });
        //  sex();


        c =  getIntent().getIntExtra("position",4);
        kind_of_word = "";
        models = db.get_Notes_main();
        Load_Main_Words();
        // show();
        //  show_2();
        swith();
        // loaf_to_firebase();
        //  get_comments_count ();



    }

    public void Load_Main_Words (){

        try {
            String one =  getIntent().getStringExtra("words-1");
            String tow =  getIntent().getStringExtra("words-2");

            Main_words = one.replace("00-01_10-" , "").split("-01_10-");
            Main_words_2 = tow.replace("00-01_10-" , "").split("-01_10-");
            show();
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }
    public void Load_Another_Words (){
        int size = models.size() ;
        Main_words = new String[size];
        Main_words_2 = new String[size];

        for (int c=0 ; c<size ; ++c){
            Model model = models.get(c);
            Main_words   [c] = model.getEnglish_word();
            Main_words_2 [c] = model.getArabic_word();
        }
        show();
    }

    public void show (){
        try {
            adapter = new Story_Adapter(this, Main_words,Main_words_2, new Story_Adapter.ItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    shard.getString("Accent", "UK");
                    speak(Main_words_2[position], shard.getString("Accent" , "UK"));
                    //  Toast.makeText(File_page.this, String.valueOf(wor.length), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(File_page_2.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
        }
        layoutManager = new StaggeredGridLayoutManager( 2 , StaggeredGridLayoutManager.VERTICAL);
        //   GridLayoutManager gridLayoutManager = new GridLayoutManager(this , 2);
        if (shard.getString("Span" , "2").equals("2")){

            ////                @Override
            ////                public int getSpanSize(int position) {
            ////
            ////                    Model model = models.get(position);
            ////                    String s  = model.getEnglish_word();
            ////                    if (s.length() >10){
            ////                        return 2 ;
            ////                    }else {
            ////                        return 1;
            ////                    }
            ////
            ////                }
            ////            });youtManager gridLayoutManager = new GridLayoutManager(this , 2);


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
                List<String> list = new ArrayList<>(Arrays.asList(Main_words));
                List<String> list1 = new ArrayList<>(Arrays.asList(Main_words_2));
                list.remove(viewHolder.getAdapterPosition());
                list1.remove(viewHolder.getAdapterPosition());
                Main_words = list.toArray(new String[Main_words.length]);
                Main_words_2 = list1.toArray(new String[Main_words_2.length]);
                String one = Main_words[0]; String two = Main_words_2[0];
                for(int c=1; c<Main_words.length-1 ; ++c){
                    one = one+"-01_10-"+Main_words[c];
                    two = two+"-01_10-"+Main_words_2[c];
                }
                Model model_1 = new Model( position , one,two, "",model.getDate());
                db_files.update_note(model_1);
                show();
                Toast.makeText(File_page_2.this ,   String.valueOf(Main_words.length)+"تم الحذف", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull  RecyclerView.ViewHolder viewHolder, int direction) {



                Model model_2 = new Model(Main_words_2[viewHolder.getAdapterPosition()] ,Main_words [viewHolder.getAdapterPosition()], "ax" ,"asx");// db.getWords(viewHolder.getAdapterPosition()+1);
                Database_2 database_2 = new Database_2(File_page_2.this);
                database_2.insert_word(model_2);

                List<String> list = new ArrayList<>(Arrays.asList(Main_words));
                List<String> list1 = new ArrayList<>(Arrays.asList(Main_words_2));
                list.remove(viewHolder.getAdapterPosition());
                list1.remove(viewHolder.getAdapterPosition());
                Main_words = list.toArray(new String[Main_words.length]);
                Main_words_2 = list1.toArray(new String[Main_words_2.length]);
                String one = Main_words[0]; String two = Main_words_2[0];
                for(int c=1; c<Main_words.length-1 ; ++c){
                    one = one+"-01_10-"+Main_words[c];
                    two = two+"-01_10-"+Main_words_2[c];
                }




                Model model_1 = new Model( position , one,two, "",model.getDate());
                db_files.update_note(model_1);

                Load_Main_Words();
                Toast.makeText(File_page_2.this ,  "تم ادراجها ضمن الكلمات المحفوظه", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

    }

    private SpannableStringBuilder addClickablePart(String str) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);

        int one = 0 ; int two = 0;
        for(int c=0 ; c<all_2.length ;++c) {
            int finalC = c;
            String word = all_2[c];

            if ( c == 0){

                one = 0 ;
                two =  word.length();;
            }else {
                one =  two +1;
                two ++ ;
                two = two + word.length();
            }

            ssb.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
//                    View view = openDialog();
//                    TextView textView = view.findViewById(R.id.textView);
//                    TextView textView1 = view.findViewById(R.id.textView4);
//                    textView.setText(all_2[finalC]);
//                    textView1.setText( search_en2(all_2[finalC].toLowerCase()));
                    if(!all_2[finalC].equals("")) {

                        All_Functions all_functions = new All_Functions(File_page_2.this);

                        dialog = new Dialog(File_page_2.this);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(File_page_2.this);
                        View view = layoutInflaterAndroid.inflate(R.layout.lay, null);
                        button_del = view.findViewById(R.id.sentence_btn_2);
                        button_del.setVisibility(View.INVISIBLE);

                        TextView textView = view.findViewById(R.id.textView);
                        textView.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onClick(View v) {
                                speak(textView.getText().toString());
                            }
                        });

                        if (chek_if_exist(all_2[finalC])) {

                            Button button_sav = view.findViewById(R.id.sentence_btn);
                            button_sav.setVisibility(View.INVISIBLE);
                            button_del.setVisibility(View.VISIBLE);
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
                        vi = view;

                    }
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    if (chek_if_exist(all_2[finalC])) {
                        super.updateDrawState(ds);
                    }
                }
            },one, two, 0);
        }

        return ssb;
    }

    public boolean chek_if_exist(String word){

        boolean bol = false ;
        for (String main_word : Main_words_2) {

            if (word.equals(main_word)) {
                bol = true;
                break;
            }

        }
        return bol;
    }

    public int get_word_position_in_Main_Words_2(String word){

        int c = 0;
        for (String main_word : Main_words_2) {
            c++ ;
            if (word.equals(main_word)) {
                break;
            }
        }

        return c;
    }

    public void sentence_btn_2(View v){
        try {


            String name =  getIntent().getStringExtra("name");

//            String words_1 =  shard_2.getString(name ,"");
//            String words_2 =  shard_2.getString(name+"2" ,"");







            TextView textView1 =vi.findViewById(R.id.textView);
            String text = textView1.getText().toString() ;

            List<String> list = new ArrayList<>(Arrays.asList(Main_words));
            List<String> list1 = new ArrayList<>(Arrays.asList(Main_words_2));

            int p = get_word_position_in_Main_Words_2(text);

            list.remove(p-1);
            list1.remove(p-1);

            Main_words = list.toArray(new String[Main_words.length]);
            Main_words_2 = list1.toArray(new String[Main_words_2.length]);

            String one = Main_words[0];
            String two = Main_words_2[0];

            for(int c=1; c<Main_words.length-1 ; ++c){
                one = one+"-01_10-"+Main_words[c];
                two = two+"-01_10-"+Main_words_2[c];
            }
            editor_2.putString(name , one);
            editor_2.putString(name+"2" , two);
            editor_2.apply();

            dialog.cancel();
            Toast.makeText(this, "تم الحذف", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("SetTextI18n")
    public void cccv(View view) {
        try {

            TextView textView = vi.findViewById(R.id.textView);
            TextView textView1 = vi.findViewById(R.id.textView4);

            Model_Book model_1  = db_files.getWords(c+1);
            Main_words = model_1.getNote().split("-01_10-");
            Main_words_2 = model_1.getTitle().split("-01_10-");

            Model model ; String english = textView1.getText().toString() , arabic =textView.getText().toString() ;
            String one = Main_words[0]; String two = Main_words_2[0];
            for(int c=0; c<Main_words.length-1 ; ++c){
                one = one+"-01_10-"+Main_words[c+1];
                two = two+"-01_10-"+Main_words_2[c+1];
            }
            model = new Model( position , one+"-01_10-"+english,two+"-01_10-"+ arabic, "", this.model.getDate());
            db_files.update_note(model);
            dialog.cancel();
            List<String> list = new ArrayList<>(Arrays.asList(Main_words));
            List<String> list1 = new ArrayList<>(Arrays.asList(Main_words_2));
            list.add(english);
            list1.add(arabic);
            Main_words = list.toArray(new String[Main_words.length]);
            Main_words_2 = list1.toArray(new String[Main_words_2.length]);
            Toast.makeText(File_page_2.this , "تم حفظ الترجمه" , Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
        }
    }

    public void search_enn22(String text) {
        models = db_3.get_Notes_get_english_words2(text);
        if (models.size() != 0) {

            Model model_2;
            String one = "";
            for (int c = 0; c < models.size(); ++c) {
                model_2 = models.get(c);
                one = one + "," + model_2.getEnglish_word();
            }

            Toast.makeText(this, one.replaceFirst(",", ""), Toast.LENGTH_SHORT).show();

        } else {
            // text_set.setText("");
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void last(String[] split) {

        Model model = new Model(split[0] ,split[1] ,"" , "normal" );

        //  db.insert_word(model);
        db_3.insert_word(model);
        speak(split[0]);
    }

    ///////////////////////////////////////////////////////////////////

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void start(String saved){
        String[] split  = one.split("- ");
        String cccc = split[1].replace("ُ","").replace("َ","").replace("ِ","").replace("ّ","").replace("ْ","");

        String [] split_2 = cccc.split("/");

        if(split_2.length>0){
            for (int c=0 ; c<split_2.length ; ++c) {
                cccc = split_2[c].replace("ُ","").replace("َ","").replace("ِ","");

                Toast.makeText(this, cccc, Toast.LENGTH_SHORT).show();

                StringBuilder s = new StringBuilder(cccc);
                if(cccc.charAt(cccc.length()-1) == ' ') {
                    s.deleteCharAt(cccc.length()-1);
                }
                speak(split[0]);
                insert(s.toString() , split , saved);
            }

        }else {

            speak(split[0]);
            insert(cccc , split , saved);
        }

    }

    public void insert(String s , String [] split , String saved){
        String new_s = s.toString();
        Model model = new Model(split[0] ,new_s ,saved , "normal" );

        db.insert_word(model);
        db_3.insert_word(model);

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

        button_save.setVisibility(View.INVISIBLE);
        button_copy.setVisibility(View.INVISIBLE);


        String name =  getIntent().getStringExtra("name");

        String words_1 =  shard_2.getString(name ,"");
        String words_2 =  shard_2.getString(name+"2" ,"");

        Main_words = words_1.split("-01_10-");
        Main_words_2 = words_2.split("-01_10-");

        String one = Main_words[0]; String two = Main_words_2[0];
        for(int c=0; c<Main_words.length-1 ; ++c){
            one = one+"-01_10-"+Main_words[c+1];
            two = two+"-01_10-"+Main_words_2[c+1];
        }

        if(kind_of_word.equals("ar")) {
          one = one+"-01_10-"+text_get.getText().toString() ;
          two = two+"-01_10-"+text_set.getText().toString();

        }else {
            one = one+"-01_10-"+text_set.getText().toString() ;
            two = two+"-01_10-"+text_get.getText().toString();
        }

        editor_2.putString(name , one);
        editor_2.putString(name+"2" , two);

        editor_2.apply();


        Toast.makeText(File_page_2.this , "تم حفظ الترجمه" , Toast.LENGTH_SHORT).show();
        kind_of_word="";
        text_get.setText("");
        text_set.setText("");
      //  Load_Main_Words();
        //show();
    }


    public void save(String Arabic , String English){




    }
    private void speak(String model , String accent) {
        textToSpeech.setLanguage(new Locale("en" , accent ));
        textToSpeech.speak(model, TextToSpeech.QUEUE_FLUSH, null);
    }


    public void if_text_found (){

        if (text_get.getText().toString().isEmpty() || text_set.getText().toString().isEmpty()){
            button_copy.setEnabled(false);
            button_save.setVisibility(View.INVISIBLE);
            // text_set.setText("");
            /// show();
            Load_Main_Words();
        }else{
            button_save.setVisibility(View.VISIBLE);
            button_copy.setEnabled(true);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    public void swith  (){

        SharedPreferences shrdd = getSharedPreferences("trans file", Context.MODE_PRIVATE);
        ba = shrdd.getString("theme_number" , "day");
        switch (ba){
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

            getWindow().setStatusBarColor(ContextCompat.getColor(File_page_2.this, R.color.white100));
            View vew = this.getWindow().getDecorView();
            vew.setBackgroundColor(ContextCompat.getColor(File_page_2.this, R.color.white100));
            collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(File_page_2.this, R.color.white100));
            //   vew.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.back2));
        }else if (color.equals("materal")){

            getWindow().setStatusBarColor(Color.parseColor("#7B8DAB"));
            View vew = this.getWindow().getDecorView();
            vew.setBackground(ContextCompat.getDrawable(File_page_2.this, R.drawable.packgroundwall));
            //  navigationView.setBackgroundColor(ContextCompat.getColor(MainActivity.this , R.color.good3));
            collapsingToolbarLayout.setContentScrimColor(Color.parseColor("#7B8DAB"));

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            navigationView.setBackground(ContextCompat.getDrawable(File_page_2.this, R.drawable.materialtextback));

        } else if (color.equals("image")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            getWindow().setStatusBarColor(ContextCompat.getColor(File_page_2.this, R.color.facecolor));
            View veww = this.getWindow().getDecorView();
            collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(File_page_2.this, R.color.facecolor));
            veww.setBackgroundColor(ContextCompat.getColor(File_page_2.this, R.color.facecolor));
        }else if (color.equals("night")){

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            getWindow().setStatusBarColor(ContextCompat.getColor(File_page_2.this, R.color.normal_theme_color_1));
            View vew = this.getWindow().getDecorView();
            vew.setBackgroundColor(ContextCompat.getColor(File_page_2.this, R.color.normal_theme_color_1));
            collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(File_page_2.this, R.color.normal_theme_color_1));
        }else if (color.equals("day")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            getWindow().setStatusBarColor(ContextCompat.getColor(File_page_2.this, R.color.normal_theme_color_1));
            View vew = this.getWindow().getDecorView();
            vew.setBackgroundColor(ContextCompat.getColor(File_page_2.this, R.color.normal_theme_color_1));
            collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(File_page_2.this, R.color.normal_theme_color_1));

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
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home0:
                        Intent intent = new Intent(File_page_2.this, theme.class);
                        startActivity(intent);break;
                    case R.id.home2:
                        Intent i_2 = new Intent(File_page_2.this, settings.class);
                        startActivity(i_2);;break;
                    case R.id.home5:
                        Intent intent1 = new Intent(File_page_2.this ,Story_Translate.class) ;
                        startActivity(intent1);break;
                    case R.id.home4:
                        Intent intentt = new Intent(File_page_2.this, Books_Main.class);
                        startActivity(intentt);break;
                    case R.id.home7:
                        Intent intenttt = new Intent(File_page_2.this, All_Words.class);
                        startActivity(intenttt);break;

                    //Intent intentttr = new Intent(MainActivity.this, Students.class);
                    //startActivity(intentttr);break;
                }
                return true;
            }
        });
    }

    public void Favorite_page(View view) {
        PopupMenu popupMenu = new PopupMenu(this , view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.menu_test2);
        popupMenu.show();

    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.btn_ubdate:
                // bbbbb = "Arabic" ;textViewvv.setText("ترجمه الي اللغة العربيه");
                update_file_name();
                return true ;
            case R.id.btn_delete:
                try{
                    Model_Book model_book = getIntent().getParcelableExtra("Note");
                    db_files.delete_word(model_book);
                    Toast.makeText(this, "تم الحذف", Toast.LENGTH_SHORT).show();
                    finish();
                    return true ;
                }catch (Exception e){
                    Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                };

            case R.id.btn_settings:
                Toast.makeText(this, "سيتوفر في التحديث القادم", Toast.LENGTH_SHORT).show();
                return true ;
            default :
                return false ;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void read_button(View view) {

        if(textToSpeech.isSpeaking()) {
            textToSpeech.stop();
        }else{
            int count = Main_words_2.length;
            for (int c = 0; c < count; c++) {
                speak(Main_words_2[c]);
            }
        }
        //  autoClick(167.0f,1087.0f);

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
        //  adapter.notifyDataSetChanged();
    }
    public void search_2(String text){
        models = db_3.get_Notes_2(text);
        // show();
        //  Load_Main_Words();
        Load_Another_Words();
    }
    @SuppressLint("WrongConstant")
    @Override
    public void onBackPressed() {

        if (scrollView.getVisibility() == 0) {
            appBarLayout.setExpanded(true, true);
            kind_of_word = "";

            //  kind_of_word = "ar";
            sentence_text.setText("");
            text_get.setText("");
            sentence_text.setVisibility(View.INVISIBLE);
            scrollView.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            // show();
            Load_Main_Words();
        }else {
            super.onBackPressed();
        }
    }
    public void search_22(String text){
        String one = "";
        models = db_3.get_Notes_22(text);
        Toast.makeText(File_page_2.this, "aaaaa", Toast.LENGTH_SHORT).show();

        if(models.size()!=0) {
            Model model_2 ;
            String tow ;
            for(int c=0 ; c<models.size() ; ++c) {
                model_2 = models.get(c);
                tow = model_2.getArabic_word();
                one = one + "," +tow ;
            }
            Toast.makeText(File_page_2.this, "aaaaa", Toast.LENGTH_SHORT).show();

            text_set.setText(one.replaceFirst(",",""));
        }else {
            text_set.setText("");
        }

    }
    public void search_en(String text){
        models = db_3.get_Notes_get_english_words(text);
        //    Load_Main_Words();
        // show();
        Load_Another_Words ();
    }
    public String search_en2(String text) {
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
            text_set.setText(t);
        }else {
            text_set.setText("");
        }
        return  t ;
    }
    public ArrayList<Model> search(String text){
        try {
            models = db_3.get_Notes(text);
            if(models.size()!=0) {

                Model model_2 = models.get(0);
                text_set.setText(model_2.getArabic_word());
                Toast.makeText(this, String.valueOf(models.size()), Toast.LENGTH_SHORT).show();
                // show();
                Load_Main_Words();

            }else {
                //  show();
                Load_Main_Words();
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
            for (int c=5; c<=Integer.parseInt(String.valueOf(db.getWordsCount())) ;++c ){

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
                text_set.setText(model_2.getArabic_word());
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


    public void get_comments_count (){

        FirebaseDatabase.getInstance().getReference("Words").child("models").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                long sss = snapshot.getChildrenCount();
                Toast.makeText(File_page_2.this, String.valueOf(sss), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(File_page_2.this, "يجب ان تكون متصلا بالانترنت", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void from_arabic_to_english_app(String[] s){

        String cc = "s";
        if(s.length>1){

            for(int c=1 ; c < s.length; ++c) {

                String ss = s[c].replace("َ","").replace("ِ","").replace("ّ","").replace("ْ","");
                inset_from_en_to_ar_app(s[0] , ss);

            }
        }else {
            inset_from_en_to_ar_app(s[0] , s[1]);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void inset_from_en_to_ar_app (String english , String ar ){

        if (!if_model_exit(ar , english)) {
            Model model = new Model(english, ar, "", "normal");
            db_3.insert_word(model);
            speak("a");
        }
    }

    public boolean if_model_exit(String ar , String english ){

        boolean bo  = false ;
        try {
            ArrayList<Model> models = search(ar);
            if (models.size()>0) {
                for(int x=0 ; x<models.size() ; ++x) {
                    Model model = models.get(x);
                    if (model.getEnglish_word().equals(ar) && model.getArabic_word().equals(english)) {
                        bo = true;
                    } else {
                        bo = false;
                    }
                }
            }else {
                bo = false ;
            }
        }catch (Exception e){
            Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
        }

        return bo ;

    }


    public void copy(View view) {
        ClipData clip = ClipData.newPlainText("Edit text" , text_set.getText().toString());
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager .setPrimaryClip(clip);
        clip.getDescription();
        Toast.makeText(File_page_2.this ,"تم الحفظ", Toast.LENGTH_SHORT).show();
        button_copy.setEnabled(false);

    }

    public void change_language(View view) {
        Toast.makeText(this, "سيتوفر اكثر من لغة في التحديث القادم", Toast.LENGTH_SHORT).show();
    }
    // حذف الموديلات المكررة
    public void delete(String word){

        ArrayList<Model> models = db_3.get_Notes_get_english_words(word);
        if (models.size() >1){
            for(int c=0 ; c <models.size() ; ++c){
                Model model = models.get(c);
                String ar , en , ex ;
                ar = model.getEnglish_word();
                en = model.getArabic_word();
                ex = model.getExample();
                models.remove(c);

                for(int x=0 ; x < models.size() ; ++x){

                    Model model_2 = models.get(x);
                    String ar_2 , en_2 , ex_2 ;
                    ar_2 = model_2.getEnglish_word();
                    en_2 = model_2.getArabic_word();
                    ex_2 = model_2.getExample();

                    if (ar.equals(ar_2) && en.equals(en_2) && ex.equals(ex_2)){

                        this.models.add(model_2);
                        db_3.delete_word(model_2);
                        //show();
                        Load_Main_Words();
                    }

                }

            }

            Toast.makeText(this, String.valueOf(this.models.size()), Toast.LENGTH_SHORT).show();

        }

    }
    public void get_text_from_photo(View view) {
//        Intent intent = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intent, 0);
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
// TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri targetUri = data.getData();
            Bitmap bitmap;
            try {
                if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    Uri resultUri = result.getUri();
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(resultUri));
                    String s = TextExtractUtil.getText(this, bitmap);
                    text_get.setText(s);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Toast.makeText(this,e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void update_file_name() {
        dialog = new Dialog(File_page_2.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(File_page_2.this);
        View view_2 = layoutInflaterAndroid.inflate(R.layout.update_file_name_layoue, null);
        All_Functions.setMargins(dialog,180 , 180, 150, 1050);
        dialog.setContentView(view_2);
        dialog.show();
        vi_2 = view_2 ;

    }

    public void update_file_name(View view){
        EditText editText = vi_2.findViewById(R.id.textView);

        if (!editText.getText().toString().isEmpty()) {

            Model_Book model_1  = db_files.getWords(c+1);
            Model m = new Model( position , model_1.getNote(), model_1.getTitle(), model_1.getLock(), editText.getText().toString());
            db_files.update_note(m);

            Toast.makeText(this, "تم التعديل", Toast.LENGTH_SHORT).show();
            dialog.cancel();
        }else {
            Toast.makeText(this, "يجب وضع اسم للملف", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onConfigurationChanged(@NotNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Screen_mode = "landscape" ;
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Screen_mode = "portrait" ;
        }
    }
    // في الاخر لازم يعمل بحث الانجليزي لوير
    // ولازم احولهم كلههم لوير
    // ولازم ابدل الهمزه و الاسبيس في العربي

}
