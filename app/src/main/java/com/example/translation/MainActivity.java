package com.example.translation;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.translation.Adapters.Adapter;
import com.example.translation.Adapters.Adapter_Files;
import com.example.translation.All_Translated_Words.Control;
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
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText text_set, text_get;
    TextView drawer_text , sentence_text ;
    ImageButton button_copy ;
    Button button_save , btn_save_sentence ;
    ScrollView scrollView ;
    Database db ;
    Database_3 db_3 ;
    Database_files db_files;
    CollapsingToolbarLayout collapsingToolbarLayout ;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView , recyclerView_2;
    Adapter adapter  ;
    Adapter_Files adapter_files ;
    SharedPreferences shard ;
    SharedPreferences.Editor editor ;
    NavigationView navigationView ;
    private DrawerLayout drawerLayout ;
    String  ba , rn , english , one , kind_of_word ;
    ClipboardManager clipboard ;
    Words words ;
    String [] all_2 ,All_Words  ;
    int ccc , Count , D, f, id , read_count , d  , frist ;
    ArrayList<Model> models ;
    View vi , vi_2 ;
    AppBarLayout appBarLayout ;
    Dialog dialog ;
    String Screen_mode ;

    All_Functions all_functions ;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"ResourceAsColor", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        all_functions = new All_Functions(MainActivity.this);

        appBarLayout = findViewById(R.id.barr);
        models = new ArrayList<>();
        drawerLayout = findViewById(R.id.drwerrf);
        navigationView = findViewById(R.id.drawer2);

        recyclerView = findViewById(R.id.nested); //textView = findViewById(R.id.text_notes_count);
        recyclerView_2 = findViewById(R.id.res_2);
        collapsingToolbarLayout=findViewById(R.id.collapsing_toolbarr);

        button_save = findViewById(R.id.buttoncv);
        button_save.setVisibility(View.INVISIBLE);
        button_copy = findViewById(R.id.buttonvv); button_copy.setEnabled(false);
        btn_save_sentence = findViewById(R.id.sentence_btn);

        scrollView = findViewById(R.id.nestedc);
        scrollView.setVisibility(View.INVISIBLE);

        sentence_text = findViewById(R.id.texevew2a2);
        text_get =findViewById(R.id.texevew1);  text_set =findViewById(R.id.text11);

        shard = getSharedPreferences("save file", Context.MODE_PRIVATE);
        editor = shard.edit();
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
                if (words.cheek_of_char(text).equals("ar")) {
                    scrollView.setVisibility(View.INVISIBLE);
                    sentence_text.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                   // Toast.makeText(MainActivity.this, "aaaaa", Toast.LENGTH_SHORT).show();
                    search_2(text);
                    search_22(text);
                    kind_of_word = "ar";

                }else if(words.cheek_of_char(text).equals("en")){
                      scrollView.setVisibility(View.INVISIBLE);
                      sentence_text.setVisibility(View.INVISIBLE);
                      recyclerView.setVisibility(View.VISIBLE);
                         search_en(text);
                         search_en2(text);
                         kind_of_word = "en";

                }else if(words.cheek_of_char(text).equals("sentence")) {
                    scrollView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                    sentence_text.setVisibility(View.VISIBLE);
                    all_2 = text.split("[ \n,:.?()_’-]");
                    sentence_text.setMovementMethod(LinkMovementMethod.getInstance());
                    sentence_text.setText(addClickablePart(text));

                    kind_of_word = "sentence";
                    appBarLayout.setExpanded(false, true);

                }else if(words.cheek_of_char(text).equals("")){
                    Load_Main_Words();
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



        if (navigationView != null && drawerLayout instanceof DrawerLayout) {
            drawerLayout = (DrawerLayout)drawerLayout;
            drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(View view, float v) { }
                @Override
                public void onDrawerOpened(View view) { }
                @Override
                public void onDrawerClosed(View view) {
                 swith();
                }
                @Override
                public void onDrawerStateChanged(int i) {

                    int currentNightMode = getResources().getConfiguration().uiMode
                            & Configuration.UI_MODE_NIGHT_MASK;

                    if (D == 0 || D==1) {
                        switch (f) {
                            case 1 :if (currentNightMode == Configuration.UI_MODE_NIGHT_YES){
                                getWindow().setStatusBarColor(Color.parseColor("#000000"));break;
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

        Count = shard.getInt("db_count", 1);

        kind_of_word = "";
        models = db.get_Notes_main();
        show();
        show_2();
        swith();
    }

    private void show_2() {

        Database_files dp_files = new Database_files(this);
        ArrayList<Model_Book> model_books = dp_files.get_files();

        adapter_files = new Adapter_Files(this , model_books , new Adapter_Files.ItemClickListener() {
            @Override
            public void onItemClick(int position, Model_Book model) {
                try {
                    if (!model.getDate().equals("1010101")){
                        Intent page = new Intent(MainActivity.this,File_page.class);
                        page.putExtra("Note",model);
                        page.putExtra("position" , position);
                        startActivity(page);
                    }else {
                        dialog = new Dialog(MainActivity.this);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(MainActivity.this);
                        View view_2 = layoutInflaterAndroid.inflate(R.layout.new_file_layout, null);
                        All_Functions.setMargins(dialog,180 , 180, 150, 1050);
                        dialog.setContentView(view_2);
                        dialog.show();
                        vi_2 = view_2 ;                    }

                }catch (Exception e){
                    Toast.makeText(MainActivity.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                };
            }
        });
        layoutManager = new StaggeredGridLayoutManager( 1 , StaggeredGridLayoutManager.HORIZONTAL);
        recyclerView_2.setLayoutManager(layoutManager);
        recyclerView_2.setHasFixedSize(true);
        recyclerView_2.setAdapter(adapter_files);
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
                        dialog = new Dialog(MainActivity.this);
                        vi = all_functions.set_Span(all_2 ,models , finalC , dialog , Screen_mode );
                        frist = 1;
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                   // ds.setColor(Color.GREEN);
                        if (all_functions.chek_if_exist(all_2[finalC] , models)) {
                            super.updateDrawState(ds);
                        }
                }
            },one, two, 0);
        }
        return ssb;
    }




    @SuppressLint("SetTextI18n")
    public void cccv(View view) {
        try {

            TextView textView = vi.findViewById(R.id.textView);
            TextView textView1 = vi.findViewById(R.id.textView4);

            Model model ;
            model = new Model(textView.getText().toString(), textView1.getText().toString(), "", "normal");
            db.insert_word(model);
            models.add(model);


            Toast.makeText(MainActivity.this , "تم حفظ الترجمه" , Toast.LENGTH_SHORT).show();
            view.setEnabled(false);
            dialog.cancel();

        }catch (Exception e){
            Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
        }
    }


    public void sentence_btn_2(View v){
            models = all_functions.delete_word_from_dp(vi , db , models);
            dialog.cancel();
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

        String arabic = text_set.getText().toString() , english = text_get.getText().toString() ;

        Model model ;
        if(kind_of_word.equals("ar")) {
                model = new Model(arabic,english, "", "normal");
        }else {
                model = new Model(english,arabic, "", "normal");
        }
            db.insert_word(model);
            Toast.makeText(MainActivity.this , "تم حفظ الترجمه" , Toast.LENGTH_SHORT).show();
            kind_of_word="";
            text_get.setText("");text_set.setText("");
            Load_Main_Words();
            show();
            //  adapter.notifyDataSetChanged();
          //  sex();
        //   loaf_to_firebase();
      //   delete_from_firebase();
    //   load_to_models_array();
    }




    public void Load_Main_Words(){

       // button_save.setVisibility(View.INVISIBLE);
        kind_of_word = "";
        models = db.get_Notes_main();
      //  recyclerView.setBackgroundResource(R.drawable.shape3);
        show();
    }
    public void show (){


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    adapter = new Adapter(MainActivity.this , models , new Adapter.ItemClickListener() {
                        @Override
                        public void onItemClick(Model model , int position ) {
                            shard.getString("Accent" , "UK");
                            speak(model , shard.getString("Accent" , "UK"));
                            if (kind_of_word.equals("ar")){
                                text_set.setText(model.getArabic_word());
                                text_get.setText(model.getEnglish_word());
                                search_22(model.getEnglish_word());
                                kind_of_word = "";
                                Load_Main_Words();
                            }else if (kind_of_word.equals("en")){
                                text_set.setText(model.getEnglish_word());
                                text_get.setText(model.getArabic_word());
                                search_en2(model.getArabic_word());
                                kind_of_word = "";
                                Load_Main_Words();
                            }
                        }

                    });

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            layoutManager = new StaggeredGridLayoutManager( 2 , StaggeredGridLayoutManager.VERTICAL);
                            //   GridLayoutManager gridLayoutManager = new GridLayoutManager(this , 2);
                            if (shard.getString("Span" , "2").equals("2")){

//                                                @Override
//                                                public int getSpanSize(int position) {
//
//                                                    Model model = models.get(position);
//                                                    String s  = model.getEnglish_word();
//                                                    if (s.length() >10){
//                                                        return 2 ;
//                                                    }else {
//                                                        return 1;
//                                                    }
//                                                }
//                                            });youtManager gridLayoutManager = new GridLayoutManager(this , 2);


                                layoutManager = new StaggeredGridLayoutManager( 2 , StaggeredGridLayoutManager.VERTICAL);
                            }else {
                                layoutManager = new StaggeredGridLayoutManager( 1 , StaggeredGridLayoutManager.VERTICAL);
                            }

                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(adapter);
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        t.start();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull  RecyclerView.ViewHolder viewHolder, int direction) {
                Model model = db.getWords(viewHolder.getAdapterPosition()+1);
                db.delete_word(model);
                models.remove(viewHolder.getAdapterPosition());
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this ,  "تم الحذف", Toast.LENGTH_SHORT).show();
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

                Database_2 database_2 = new Database_2(MainActivity.this);

                database_2.insert_word(model);

                db.delete_word(model);
                models.remove(viewHolder.getAdapterPosition());
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this ,  "تم ادراجها ضمن الكلمات المحفوظه", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

    }

    private void speak(Model model , String accent) {
        all_functions.textToSpeech.setLanguage(new Locale("en" , accent ));
        all_functions.textToSpeech.speak(model.getArabic_word(), TextToSpeech.QUEUE_FLUSH, null);
    }


    public void if_text_found (){

        if (text_get.getText().toString().isEmpty() || text_set.getText().toString().isEmpty()){
            button_copy.setEnabled(false);
            button_save.setVisibility(View.INVISIBLE);
            Load_Main_Words();
            show();
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

            getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.white100));
            View vew = this.getWindow().getDecorView();
            vew.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.white100));
            collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(MainActivity.this, R.color.white100));
            //   vew.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.back2));
        }else if (color.equals("materal")){

            getWindow().setStatusBarColor(Color.parseColor("#7B8DAB"));
            View vew = this.getWindow().getDecorView();
            vew.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.packgroundwall));
             navigationView.setBackgroundColor(ContextCompat.getColor(MainActivity.this , R.color.good3));
            collapsingToolbarLayout.setContentScrimColor(Color.parseColor("#7B8DAB"));
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            navigationView.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.materialtextback));

        } else if (color.equals("image")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.facecolor));
            View veww = this.getWindow().getDecorView();
            collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(MainActivity.this, R.color.facecolor));
            veww.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.facecolor));
        }else if (color.equals("night")){

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.normal_theme_color_1));
            View vew = this.getWindow().getDecorView();
            vew.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.normal_theme_color_1));
            collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(MainActivity.this, R.color.normal_theme_color_1));
        }else if (color.equals("day")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.normal_theme_color_1));
            View vew = this.getWindow().getDecorView();
            vew.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.normal_theme_color_1));
            collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(MainActivity.this, R.color.normal_theme_color_1));

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
                        Intent intent = new Intent(MainActivity.this, theme.class);
                        startActivity(intent);break;
                    case R.id.home2:
                        Intent i_2 = new Intent(MainActivity.this, settings.class);
                        startActivity(i_2);;break;
                    case R.id.home5:
                        Intent intent1 = new Intent(MainActivity.this ,Story_Translate.class) ;
                        startActivity(intent1);break;
                    case R.id.home4:
                            Intent intentt = new Intent(MainActivity.this, Test.class);
                            startActivity(intentt);break;
                    case R.id.home7:
                        Intent intenttt = new Intent(MainActivity.this, All_Words.class);
                        startActivity(intenttt);break;
                }
                return true;
            }
        });
    }

    public void Favorite_page(View view) {
        Intent intent = new Intent(MainActivity.this , Favorite.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void read_button(View view) {

        if(all_functions.textToSpeech.isSpeaking()) {
            all_functions.textToSpeech.stop();
        }else{
            int count = Integer.parseInt(String.valueOf(db.getWordsCount()));
            for (int c = 1; c <= count; c++) {
                Model model = db.getWords(c);
                speak(model.getArabic_word());
            }
        }
      //  autoClick(167.0f,1087.0f);

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void speak(String text) {
        all_functions.textToSpeech.setLanguage(new Locale("en" , shard.getString("Accent" , "UK")));
        all_functions.textToSpeech.speak(text, TextToSpeech.STOPPED, null);
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
        adapter_files.notifyDataSetChanged();
    }
    public void search_2(String text){
            models = db_3.get_Notes_2(text);
       // recyclerView.setBackgroundResource(R.drawable.res_shape);
            show();
    }

    public void search_22(String text){
        String one = "";
            models = db_3.get_Notes_22(text);
        if(models.size()!=0) {
                Model model_2 ;
                String tow ;
                for(int c=0 ; c<models.size() ; ++c)
                {
                    model_2 = models.get(c);
                    tow = model_2.getArabic_word();
                    one = one + "," +tow ;
                }

            String finalOne = one;
            runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text_set.setText(finalOne.replaceFirst(",",""));
                    }
                });

        }else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text_set.setText("");
                }
            });
        }
    }
    public void search_en(String text){

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    models = db_3.get_Notes_get_english_words(text);
                }catch (Exception e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        show();
                    }
                });
            }
        });
        t.start();
          //  recyclerView.setBackgroundResource(R.drawable.res_shape);
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
                show();

            }else {
                show();
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
                Toast.makeText(MainActivity.this, String.valueOf(sss), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                Toast.makeText(MainActivity.this, "يجب ان تكون متصلا بالانترنت", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(MainActivity.this ,"تم الحفظ", Toast.LENGTH_SHORT).show();
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
                        show();
                    }

                }

            }

            Toast.makeText(this, String.valueOf(this.models.size()), Toast.LENGTH_SHORT).show();

        }

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
            adapter.notifyDataSetChanged();
        }else {
            super.onBackPressed();
        }
    }

    private void replacefragment(Fragment fragment) {

        FragmentManager Manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = Manager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_2 , fragment);
        fragmentTransaction.commit();

    }

    public void new_file_2(View view){
        EditText editText = vi_2.findViewById(R.id.textView);
        if (!editText.getText().toString().isEmpty()) {
            Model m = new Model("00", "00", "axc", editText.getText().toString());
            db_files.insert_word(m);
            Toast.makeText(this, "تم الاضافة", Toast.LENGTH_SHORT).show();
            dialog.cancel();
            show_2();
        }else {
            Toast.makeText(this, "يجب وضع اسم للملف", Toast.LENGTH_SHORT).show();
        }
    }



    public void btn_translate_2(View view) {
        Intent i = new Intent(this, Books_Main.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    public void get_text_from_photo(View view) {
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

    // في الاخر لازم يعمل بحث الانجليزي لوير
    // ولازم احولهم كلههم لوير
    // ولازم ابدل الهمزه و الاسبيس في العربي


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

}

