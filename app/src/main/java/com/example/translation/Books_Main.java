package com.example.translation;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.translation.Adapters.Adapter_Book;
import com.example.translation.Database_All.Database_Book;
import com.example.translation.models.Model;
import com.example.translation.models.Model_Book;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Books_Main extends AppCompatActivity {

    TextView textView ;
    Database_Book db ;
    CollapsingToolbarLayout collapsingToolbarLayout ;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    Adapter_Book adapter ;
    int id ;
    Model main_model ;
    SharedPreferences shard ;
    SharedPreferences.Editor editor ;
    private DrawerLayout drawerLayout ;
    NavigationView navigationView ;
    private boolean mTimetcount;
    CountDownTimer mcountDownTimer;
    int z, a , D , f;
    String  ba ;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_main);

        collapsingToolbarLayout=findViewById(R.id.collapsing_toolbar);

        getWindow().setStatusBarColor(ContextCompat.getColor(Books_Main.this, R.color.white100));
        View vew = this.getWindow().getDecorView();
        vew.setBackgroundColor(ContextCompat.getColor(Books_Main.this, R.color.white100));
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(Books_Main.this, R.color.white100));
        recyclerView = findViewById(R.id.nested); textView = findViewById(R.id.text_notes_count);
        drawerLayout = findViewById(R.id.drwerrf);
        navigationView = findViewById(R.id.drawer2);

        shard = getSharedPreferences("save file", Context.MODE_PRIVATE);

        if (navigationView != null && drawerLayout instanceof DrawerLayout) {
            drawerLayout = (DrawerLayout)drawerLayout;
            drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(View view, float v) { }
                @Override
                public void onDrawerOpened(View view) { }
                @Override
                public void onDrawerClosed(View view) {

                    if (mTimetcount){
                        mcountDownTimer.cancel();
                    }
                }
                @Override
                public void onDrawerStateChanged(int i) {

                    int currentNightMode = getResources().getConfiguration().uiMode
                            & Configuration.UI_MODE_NIGHT_MASK;

                    if (D == 0 || D==1) {
                        switch (f) {
                            case 1 :if (currentNightMode == Configuration.UI_MODE_NIGHT_YES){break;
                            }else{getWindow().setStatusBarColor(Color.parseColor("#616161")); break;}
                            case 2 : getWindow().setStatusBarColor(Color.parseColor("#313843")); break;
                            case 3 : getWindow().setStatusBarColor(Color.parseColor("#070707")); break;
                        }   D ++ ; }  else {swith();if (D==2){  D ++ ;}else { D = 0 ; }
                    }
                }
            });
        }


        db=new Database_Book(this);
        id = Integer.parseInt(String.valueOf(db.getNotesCount()));

        show() ;

    }

    public void show (){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                adapter = new Adapter_Book(Books_Main.this  , new Adapter_Book.ItemClickListener() {
                    @Override
                    public void onItemClick(Model_Book model , int position) {
                        editor = shard.edit();

                        Intent book = new Intent(Books_Main.this, Book.class);
                        book.putExtra("Note",model);
                        id = position+1;
                        editor.putInt("id" ,position+1);
                        editor.apply();
                        startActivity(book);

                    }
                });
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        layoutManager = new GridLayoutManager(Books_Main.this, 2);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        });
        t.start();
    }

    public void btn_new(View view) {
        try {

            editor = shard.edit();
            Intent book = new Intent(Books_Main.this, Book.class);
            Model_Book model1 = new Model_Book("" , "العنوان" ,"no");
            int c = db.insert_note(model1);
            Model_Book model = new Model_Book(c ,"" , "العنوان" , "no" , "new");
            id = Integer.parseInt(String.valueOf(db.getNotesCount()));
            editor.putInt("id" ,id );
            editor.apply();
            book.putExtra("Note",model);
            startActivity(book);

        }catch (Exception e){
            Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        try {
            String coped = getShard("coped" , "");
            int id = shard.getInt("id",1);

            Model_Book model = db.bitmaps(id);
            String note = model.getNote();

            if (!note.equals(coped)){
                db.change(model);
                save();
            }else if (note.equals("")){
                db.delete_note(model);
            }else {



            }

        }catch (Exception e){
            Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
        textView.setText("ملاحظه "+db.getNotesCount());
    }

    public void setEditor(String name , String value){
        editor = shard.edit();
        editor.putString(name , value);
        editor.apply();
    }
    public String  getShard (String name , String value){
        return shard.getString(name , value);
    }

    public void save (){

        int id = shard.getInt("id",1);
        Model_Book model = db.bitmaps(id);

        ArrayList<String> cc = new ArrayList<>();

        String alltext  = model.getNote();
        String b  ; String n = "" ;

        for (int v=0 ; v< 60 ; ++v ){
            cc.add("\n");
            b = n + cc.get(v);
            n = b ;

            if (alltext.equals(b)){
                Toast.makeText(this, "المفروض تتحزف", Toast.LENGTH_SHORT).show();
                db.delete_note(model); ;
                break;
            }
        }
    }

    private void remove_note(Model_Book model) {
        db.delete_note(model);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void main_menuer(View view) {
        OpenDrawer();
        //  start_alarm("a");

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void OpenDrawer() {
        if (mTimetcount){
            mcountDownTimer.cancel();
        }
        navigationView = findViewById(R.id.drawer2);
        drawerLayout.openDrawer(Gravity.LEFT);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    //    case R.id.home0:  theme_activity();
                    //        break;
                    case R.id.home2:
                        // Intent intent = new Intent(MainActivity.this, Alarm2.class);
                        // startActivity(intent);;break;
                        //  case R.id.home5: Intent intent1 = new Intent(MainActivity.this ,Amr.class) ;
                        // startActivity(intent1);break;
                    case R.id.home4:
                        // Intent intentt = new Intent(MainActivity.this, ChangePassword.class);
                        // startActivity(intentt);break;
                    case R.id.home6:
                        // Intent intenttt = new Intent(MainActivity.this, Amr_bage.class);
                        // startActivity(intenttt);break;
                    case R.id.home7:
                        //   Intent intentttr = new Intent(MainActivity.this, Students.class);
                        // startActivity(intentttr);break;

                }
                return true;
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void swith  (){

        SharedPreferences shrdd = getSharedPreferences("trans file", Context.MODE_PRIVATE);

        ba = shrdd .getString("theme_number" , "day");

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

            getWindow().setStatusBarColor(ContextCompat.getColor(Books_Main.this, R.color.white100));
            View vew = this.getWindow().getDecorView();
            vew.setBackgroundColor(ContextCompat.getColor(Books_Main.this, R.color.white100));
            collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(Books_Main.this, R.color.white100));
            //   vew.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.back2));
        }else if (color.equals("materal")){


            getWindow().setStatusBarColor(Color.parseColor("#7B8DAB"));
            View vew = this.getWindow().getDecorView();
            // vew.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.packgroundwall));
            //  navigationView.setBackgroundColor(ContextCompat.getColor(MainActivity.this , R.color.good3));
            collapsingToolbarLayout.setContentScrimColor(Color.parseColor("#7B8DAB"));
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            //   navigationView.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.materialtextback));

        } else if (color.equals("image")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            getWindow().setStatusBarColor(ContextCompat.getColor(Books_Main.this, R.color.facecolor));
            View veww = this.getWindow().getDecorView();
            collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(Books_Main.this, R.color.facecolor));
            veww.setBackgroundColor(ContextCompat.getColor(Books_Main.this, R.color.facecolor));
        }else if (color.equals("night")){

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            getWindow().setStatusBarColor(ContextCompat.getColor(Books_Main.this, R.color.white100));
            View vew = this.getWindow().getDecorView();
            vew.setBackgroundColor(ContextCompat.getColor(Books_Main.this, R.color.white100));
            collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(Books_Main.this, R.color.white100));
        }else if (color.equals("day")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            getWindow().setStatusBarColor(ContextCompat.getColor(Books_Main.this, R.color.white100));
            View vew = this.getWindow().getDecorView();
            vew.setBackgroundColor(ContextCompat.getColor(Books_Main.this, R.color.white100));
            collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(Books_Main.this, R.color.white100));
        }

        else{

            getWindow().setStatusBarColor(Color.parseColor(color));
            View vew = this.getWindow().getDecorView();
            vew.setBackgroundColor(Color.parseColor(color));
            collapsingToolbarLayout.setContentScrimColor(Color.parseColor(color));
        }
    }
    public void btn_translate(View view){
        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }
}