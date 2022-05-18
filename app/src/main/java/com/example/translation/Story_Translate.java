package com.example.translation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipboardManager;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.translation.Adapters.Story_Adapter;
import com.example.translation.Database_All.Database;
import com.example.translation.Database_All.Database_3;
import com.example.translation.models.Model;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Story_Translate extends AppCompatActivity {

    String [] all , all_2 , space  ;
    String text ;

    TextToSpeech textToSpeech;
    EditText text_set, text_get;
    TextView drawer_text ;
    ImageButton button_copy;
    Button button_save;
    Database db ;
    Database_3 db_3 ;
    CollapsingToolbarLayout collapsingToolbarLayout ;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    Story_Adapter adapter ;
    SharedPreferences shard ;
    SharedPreferences.Editor editor ;
    NavigationView navigationView ;
    private DrawerLayout drawerLayout ;
    String  ba , rn , english , one , kind_of_word ;
    ClipboardManager clipboard ;
    Words words ;
    Words_2 words_2 ;
    String [] wor,ad,All_Words ;
    ArrayList<Model> models , models_2 ;
    CoordinatorLayout layout ;
    int ccoo , r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_translate);

        models = new ArrayList<>();

        words = new Words();
        words_2 = new Words_2() ;
        wor = words_2.get_words();
        All_Words = words_2.get_words();

        db_3 = new Database_3(this);

        text = "the expression of or the ability to express thoughts and feelings by articulate sounds\n" +
                "he was born deaf and without the power of speech\n" +
                "synonyms: speaking, talking," ;
       // all = text.split("\n");
//        text.replaceAll("[\n]" , " ");
//        text.replace("," , "");
//        text.replace("  " , " ");
        all_2 = text.split("[ \n,:]");
        Toast.makeText(this, String.valueOf(all_2.length), Toast.LENGTH_SHORT).show();


        TextView textView = findViewById(R.id.texevew1);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(addClickablePart(text));

      // show();
    }

    public void show (){

//        adapter = new Story_Adapter(this , all_2, new Story_Adapter.ItemClickListener() {
//            @Override
//            public void onItemClick(int position ) {
//
//                try {
//                    Toast.makeText(Story_Translate.this, all_2[position] + ";;;;" + position, Toast.LENGTH_SHORT).show();
//                }catch (Exception e){
//                    Toast.makeText(Story_Translate.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });

       // layoutManager = new StaggeredGridLayoutManager( 2 , StaggeredGridLayoutManager.VERTICAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this , 28);

         gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
             @Override
             public int getSpanSize(int position) {
                 ccoo ++;


                 String ss = "";

                 try {
                     if (position <25) {
                         String s = all[0];
                         ss = s;
                         space = s.split(" ");
                         if (!ss.equals("")) {
                             Toast.makeText(Story_Translate.this, String.valueOf(space.length), Toast.LENGTH_SHORT).show();
                         }

                         return 3 ;
                     }else {
                         return 0;
                     }
                 }catch (Exception e){
                     Toast.makeText(Story_Translate.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                     return 1;
                 }


             }
         });

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

    private SpannableStringBuilder addClickablePart(String str) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);

        int three =0 ;
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

                    //Toast.makeText(Story_Translate.this, all_2[finalC], Toast.LENGTH_SHORT).show();

                    search_en2(all_2[finalC]);


                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setColor(Color.GREEN);
                }
            },one, two, 0);
        }

        return ssb;
    }

    public void search_en2(String text) {
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
}