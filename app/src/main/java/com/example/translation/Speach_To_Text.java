package com.example.translation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.SupportActionModeWrapper;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class Speach_To_Text extends AppCompatActivity {


    private ImageView iv_mic;
    private TextView tv_Speech_to_text;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speach_to_text);
        iv_mic = findViewById(R.id.iv_mic);
        tv_Speech_to_text = findViewById(R.id.tv_speech_to_text);

        iv_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent
                        = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());  //اللغة
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");


                onActionModeFinished(new ActionMode() {
                    @Override
                    public void setTitle(CharSequence title) {

                    }

                    @Override
                    public void setTitle(int resId) {

                    }

                    @Override
                    public void setSubtitle(CharSequence subtitle) {

                    }

                    @Override
                    public void setSubtitle(int resId) {

                    }

                    @Override
                    public void setCustomView(View view) {

                    }

                    @Override
                    public void invalidate() {

                    }

                    @Override
                    public void finish() {

                    }

                    @Override
                    public Menu getMenu() {
                        return null;
                    }

                    @Override
                    public CharSequence getTitle() {
                        return null;
                    }

                    @Override
                    public CharSequence getSubtitle() {
                        return null;
                    }

                    @Override
                    public View getCustomView() {
                        return null;
                    }

                    @Override
                    public MenuInflater getMenuInflater() {
                        return null;
                    }
                });

                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
                } catch (Exception e) {
                    Toast
                            .makeText(Speach_To_Text.this, " " + e.getMessage(),
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                tv_Speech_to_text.setText(
                        Objects.requireNonNull(result).get(0));
            }
        }
    }
}