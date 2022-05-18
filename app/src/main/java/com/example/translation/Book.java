package com.example.translation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.translation.Database_All.Database;
import com.example.translation.Database_All.Database_3;
import com.example.translation.Database_All.Database_Book;
import com.example.translation.Database_All.Database_files;
import com.example.translation.models.Model;
import com.example.translation.models.Model_Book;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Book extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    EditText Book_Text , title;
    String note , Title, dis, coped , date , Screen_mode ;
    ArrayList<String> vv , cc ;
    ClipboardManager clipboardManager ;
    ScrollView scrollView ;
    Database_Book db ;
    Database db_main ;

    int id ;
    SharedPreferences shard ;
    SharedPreferences.Editor editor ;
    TextView trans_text ;
    String [] all_2 ;
    View vi ;
    Database_3 db_3 ;
    Dialog dialog ;

    Button btn_save_sentence ;

    ArrayList<Model> models ;

    All_Functions all_functions ;

    Database_files db_files;

    String [] files_names ;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        getWindow().setStatusBarColor(ContextCompat.getColor(Book.this, R.color.white100));

             db_files = new Database_files(this);
                                                                                     //white200
        all_functions = new All_Functions(Book.this);

        trans_text = findViewById(R.id.Trans_text);
        trans_text.setVisibility(View.INVISIBLE);

        btn_save_sentence = findViewById(R.id.sentence_btn);

        models = new ArrayList<>();

        scrollView = findViewById(R.id.scrollView2);

        vv = new ArrayList<>();   cc = new ArrayList<>();
        Book_Text = findViewById(R.id.textt); title = findViewById(R.id.textView2);
        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        db = new Database_Book(this);
        db_main = new Database(this);
        shard = getSharedPreferences("save file", Context.MODE_PRIVATE);

        editor = shard.edit();

        Model_Book model = getIntent().getParcelableExtra("Note");
        Book_Text.setText(model.getNote());

//        all_2 = model.getNote().split("[ \n,:.]");
//        Book_Text.setMovementMethod(LinkMovementMethod.getInstance());
//        Book_Text.setText(addClickablePart(model.getNote()));

        title.setText(model.getTitle());
        id = model.getId();
        date = model.getDate();
        dis = model.getLock();

        coped = Book_Text.getText().toString();
        editor.putString("coped", coped);
        editor.apply();

        Title = title.getText().toString();

        models = db_main.get_Notes_main();

        Screen_mode = "portrait";

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                 Save();
            } });

        Book_Text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {

                Save();
            }});

        db_3 = new Database_3(this);



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Model_Book model = new Model_Book(id , Book_Text.getText().toString() , title.getText().toString() , dis , "old");
        Intent book = new Intent(Book.this, MainActivity.class);
        book.putExtra("Note",model);

    }

    public void menuee_book(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.menu_book);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.item:
//                save();
//                Intent intent = new Intent(Book.this, Alarm2.class);
//                startActivity(intent);
                return true;
            case R.id.item2:
                Toast.makeText(Book.this, "سيتوفر في التحديث القادم", Toast.LENGTH_SHORT).show();
                Save();
                return true;
            case R.id.item3:
                Toast.makeText(Book.this, "سيتوفر في التحديث القادم", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item4: btn_lock();
                return true ;
            case R.id.item5:
//                if (shrd.getString(num, "").equals("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n")){ S = 1 ; btn_remove();
//                }else {btn_remove();}
                delete();
                return true;
            case R.id.item6:Book_Text.setText(coped);
              //  Trans();
                return true;
//            case R.id.item7: AddToWidget();
//                return true;
            default:
                return false;
        }
    }

    @SuppressLint("WrongConstant")
    public void Trans(View v) {

        if (Book_Text.getVisibility() !=4){
            get_All_Words();

            String text = Book_Text.getText().toString();
            Book_Text.setVisibility(View.INVISIBLE);
            trans_text.setVisibility(View.VISIBLE);

            all_2 = text.split("[ \n,:.]");

            trans_text.setMovementMethod(LinkMovementMethod.getInstance());
            trans_text.setText(addClickablePart(text));

        }else {

            Book_Text.setVisibility(View.VISIBLE);
            trans_text.setVisibility(View.INVISIBLE);

            Toast.makeText(this, "الان يمكن التعديل علي النص", Toast.LENGTH_SHORT).show();
        }

    }

    private void btn_lock() {
        dis = "yes" ;
        Save();
    }

    private void delete() {
        Model_Book model = new Model_Book( id ,note , Title,dis , "old" );
        db.delete_note(model);
        this.finish();

    }

    private void Save() {


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    note  = Book_Text.getText().toString();
                    Title = title.getText().toString();
                    Model_Book model = new Model_Book( id ,note , Title,dis , "old");
                    db.update_note(model);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        t.start();
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
                        dialog = new Dialog(Book.this);
                        vi = all_functions.set_Span(all_2 ,models , finalC , dialog , Screen_mode );
                }

                @Override
                public void updateDrawState(TextPaint ds) {

                    if (all_functions.chek_if_exist(all_2[finalC] , files_names)) {
                        ds.setColor(Color.parseColor("#D2FFD3BD"));
                    }

                    if (all_functions.chek_if_exist(all_2[finalC] , models)) {

                        ds.setColor(Color.parseColor("#BCCFFBD1"));
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
            db_main.insert_word(model);
            models.add(model);

            Toast.makeText(Book.this , "تم حفظ الترجمه" , Toast.LENGTH_SHORT).show();
            dialog.cancel();

        }catch (Exception e){
            Toast.makeText(this, String.valueOf(e), Toast.LENGTH_SHORT).show();
        }
    }

    public void sentence_btn_2(View v){
        models = all_functions.delete_word_from_dp(vi , db_main , models);
        dialog.cancel();
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

    public void get_All_Words(){

        int cc = Integer.parseInt(String.valueOf(db_files.getWordsCount()));
        ArrayList<Model_Book> models = db_files.get_files();

        String words = "";
        for(int c=0; c< cc ; ++ c){
            words += models.get(c).getTitle();
        }
        files_names = words.replace("00-01_10-" , "").split("-01_10-");
        Toast.makeText(this, String.valueOf(files_names.length) + files_names[2], Toast.LENGTH_SHORT).show();
    }

}