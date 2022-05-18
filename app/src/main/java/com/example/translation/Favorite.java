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
import android.widget.Toast;

import com.example.translation.Adapters.Adapter_Favorite;
import com.example.translation.Database_All.Database;
import com.example.translation.Database_All.Database_2;
import com.example.translation.models.Model;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

public class Favorite extends AppCompatActivity {
    RecyclerView recyclerView ;
    RecyclerView.LayoutManager layoutManager ;
    CollapsingToolbarLayout collapsingToolbarLayout ;
    Adapter_Favorite adapter ;
    Database_2 db;
    String  ba ;
    ArrayList<Model> models ;
    int f ;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        collapsingToolbarLayout=findViewById(R.id.collapsing_toolbarrrrrr);
        recyclerView = findViewById(R.id.nestedd);

        db = new Database_2(this);
        swith();
        show();
    }

    public void show (){
        models = db.get_Notes_main();
        adapter = new Adapter_Favorite(this , models , new Adapter_Favorite.ItemClickListener() {
            @Override
            public void onItemClick(Model model , int position) {

             //   speak(model);

            }
        });

        layoutManager = new StaggeredGridLayoutManager( 1 , StaggeredGridLayoutManager.VERTICAL);
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
                Toast.makeText(Favorite.this ,  "تم الحذف", Toast.LENGTH_SHORT).show();
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

                Database database = new Database(Favorite.this);

                database.insert_word(model);

                db.delete_word(model);

                adapter.notifyDataSetChanged();
                Toast.makeText(Favorite.this ,  "تم اداجها ضمن القائمة الرئيسية", Toast.LENGTH_SHORT).show();
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

        if (color.equals("normal")) {

            getWindow().setStatusBarColor(ContextCompat.getColor(Favorite.this, R.color.white100));
            View vew = this.getWindow().getDecorView();
            vew.setBackgroundColor(ContextCompat.getColor(Favorite.this, R.color.white100));
            collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(Favorite.this, R.color.white100));
            //   vew.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.back2));
        }else if (color.equals("materal")){

            getWindow().setStatusBarColor(Color.parseColor("#7B8DAB"));
            View vew = this.getWindow().getDecorView();
            vew.setBackground(ContextCompat.getDrawable(Favorite.this, R.drawable.packgroundwall));
            //  navigationView.setBackgroundColor(ContextCompat.getColor(MainActivity.this , R.color.good3));
            collapsingToolbarLayout.setContentScrimColor(Color.parseColor("#7B8DAB"));
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        } else if (color.equals("image")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            getWindow().setStatusBarColor(ContextCompat.getColor(Favorite.this, R.color.facecolor));
            View veww = this.getWindow().getDecorView();
            collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(Favorite.this, R.color.facecolor));
            veww.setBackgroundColor(ContextCompat.getColor(Favorite.this, R.color.facecolor));
        }else if (color.equals("night")){

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            getWindow().setStatusBarColor(ContextCompat.getColor(Favorite.this, R.color.normal_theme_color_1));
            View vew = this.getWindow().getDecorView();
            vew.setBackgroundColor(ContextCompat.getColor(Favorite.this, R.color.normal_theme_color_1));
            collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(Favorite.this, R.color.normal_theme_color_1));
        }else if (color.equals("day")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            getWindow().setStatusBarColor(ContextCompat.getColor(Favorite.this, R.color.normal_theme_color_1));
            View vew = this.getWindow().getDecorView();
            vew.setBackgroundColor(ContextCompat.getColor(Favorite.this, R.color.normal_theme_color_1));
            collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(Favorite.this, R.color.normal_theme_color_1));

        } else{

            getWindow().setStatusBarColor(Color.parseColor(color));
            View vew = this.getWindow().getDecorView();
            vew.setBackgroundColor(Color.parseColor(color));
            collapsingToolbarLayout.setContentScrimColor(Color.parseColor(color));
        }
    }
}