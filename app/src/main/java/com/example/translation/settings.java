package com.example.translation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class settings extends AppCompatActivity {

    SharedPreferences shrd;
    SharedPreferences.Editor editor;

    Button button , button2 , button3 , button4 ;
    Switch aSwitch ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        shrd = getSharedPreferences("save file", Context.MODE_PRIVATE);
        editor = shrd.edit();

        button  = findViewById(R.id.button27);
        button2 = findViewById(R.id.button30gbng);
        button3 = findViewById(R.id.button31);
        button4 = findViewById(R.id.button32);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editor.putString("Accent" , "US");
                editor.apply();
                Toast.makeText(settings.this, "تم تغيير اللهجه الي الامريكية", Toast.LENGTH_SHORT).show();

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("Accent" , "UK");
                editor.apply();
                Toast.makeText(settings.this, "تم تغيير اللهجه الي البريطانية", Toast.LENGTH_SHORT).show();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("Span" , "1");
                editor.apply();
                Toast.makeText(settings.this, "تم تغيير", Toast.LENGTH_SHORT).show();
            }
        });


        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("Span" , "2");
                editor.apply();
                Toast.makeText(settings.this, "تم تغيير", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
