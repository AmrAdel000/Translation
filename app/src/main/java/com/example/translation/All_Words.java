package com.example.translation;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.translation.Adapters.Adapter_Words;
import com.example.translation.Database_All.Database;
import com.example.translation.Database_All.Database_3;
import com.example.translation.models.Model;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

public class All_Words extends AppCompatActivity {
    RecyclerView recyclerView ;
    RecyclerView.LayoutManager layoutManager ;
    CollapsingToolbarLayout collapsingToolbarLayout ;
    Adapter_Words adapter ;
    Database_3 db;
    String  ba ;
    int f ;
    TextView textView ;
    ArrayList<Model> models ;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_words);

        collapsingToolbarLayout=findViewById(R.id.collapsing_toolbarrrrrr);
        recyclerView = findViewById(R.id.nestedd);
        textView = findViewById(R.id.textView3);
        db = new Database_3(this);
        swith();
        show();
        textView.setText(String.valueOf(db.getWordsCount()));
    }

    public void Load_Models(){
        models = db.mmm();
    }

    public void show (){
        Load_Models();
        adapter = new Adapter_Words(this  , models  , new Adapter_Words.ItemClickListener() {
            @Override
            public void onItemClick(Model model , int position) {

                //   speak(model);

            }
        });

        layoutManager = new StaggeredGridLayoutManager( 2 , StaggeredGridLayoutManager.VERTICAL);
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
                Model model = db.getWords(Integer.parseInt(String.valueOf(db.getWordsCount())) - (viewHolder.getAdapterPosition()));
                db.delete_word(model);

                adapter.notifyDataSetChanged();
                Toast.makeText(All_Words.this ,  "تم الحذف", Toast.LENGTH_SHORT).show();
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

                Database database = new Database(All_Words.this);

                database.insert_word(model);

                db.delete_word(model);

                adapter.notifyDataSetChanged();
                Toast.makeText(All_Words.this ,  "تم اداجها ضمن القائمة الرئيسية", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


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

        switch (color) {
            case "normal": {

                getWindow().setStatusBarColor(ContextCompat.getColor(All_Words.this, R.color.white100));
                View vew = this.getWindow().getDecorView();
                vew.setBackgroundColor(ContextCompat.getColor(All_Words.this, R.color.white100));
                collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(All_Words.this, R.color.white100));
                //   vew.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.back2));
                break;
            }
            case "materal": {

                getWindow().setStatusBarColor(Color.parseColor("#7B8DAB"));
                View vew = this.getWindow().getDecorView();
                vew.setBackground(ContextCompat.getDrawable(All_Words.this, R.drawable.packgroundwall));
                //  navigationView.setBackgroundColor(ContextCompat.getColor(MainActivity.this , R.color.good3));
                collapsingToolbarLayout.setContentScrimColor(Color.parseColor("#7B8DAB"));
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                break;
            }
            case "image":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                getWindow().setStatusBarColor(ContextCompat.getColor(All_Words.this, R.color.facecolor));
                View veww = this.getWindow().getDecorView();
                collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(All_Words.this, R.color.facecolor));
                veww.setBackgroundColor(ContextCompat.getColor(All_Words.this, R.color.facecolor));
                break;
            case "night": {

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                getWindow().setStatusBarColor(ContextCompat.getColor(All_Words.this, R.color.normal_theme_color_1));
                View vew = this.getWindow().getDecorView();
                vew.setBackgroundColor(ContextCompat.getColor(All_Words.this, R.color.normal_theme_color_1));
                collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(All_Words.this, R.color.normal_theme_color_1));
                break;
            }
            case "day": {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                getWindow().setStatusBarColor(ContextCompat.getColor(All_Words.this, R.color.normal_theme_color_1));
                View vew = this.getWindow().getDecorView();
                vew.setBackgroundColor(ContextCompat.getColor(All_Words.this, R.color.normal_theme_color_1));
                collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(All_Words.this, R.color.normal_theme_color_1));

                break;
            }
            default: {

                getWindow().setStatusBarColor(Color.parseColor(color));
                View vew = this.getWindow().getDecorView();
                vew.setBackgroundColor(Color.parseColor(color));
                collapsingToolbarLayout.setContentScrimColor(Color.parseColor(color));
                break;
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
       // adapter.notifyDataSetChanged();
    }
}
