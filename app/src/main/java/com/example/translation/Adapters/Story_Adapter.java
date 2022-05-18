package com.example.translation.Adapters;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.translation.Database_All.Database;
import com.example.translation.File_page;
import com.example.translation.models.Model;
import com.example.translation.R;

import org.jetbrains.annotations.NotNull;

public class Story_Adapter extends RecyclerView.Adapter<Story_Adapter.ViewHolder> {

 // ArrayList<String> note , title ;
    Context context ;
    String [] ss , s ;
    ItemClickListener itemClickListener ;

    public Story_Adapter(Context story_translate, String[] main_words, String[] models, ItemClickListener itemClickListener) {
        context = story_translate ;
        ss = models ;
        s  = main_words;
        this.itemClickListener = itemClickListener ;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.row, parent, false);
            return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {

        try{

            holder.English_Word.setText(ss[position]);
            holder.Arabic_Word.setText(s[position]);
            holder.example.setVisibility(View.GONE);


            if(ss.length == (position+1)) {
                holder.view.setVisibility(View.INVISIBLE);
            }
//            if(ss.length >1){
//                holder.itemView.setVisibility(View.INVISIBLE);
//            }

            holder.itemView.setOnClickListener(view -> {
                itemClickListener.onItemClick(position);//it will get the position of our item in our resycler vew
            });

        }catch (Exception e){
            Toast.makeText(context, String.valueOf(e), Toast.LENGTH_SHORT).show();
        }

    }
    public interface ItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public int getItemCount() {
            return ss.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView English_Word;
        TextView Arabic_Word;
        EditText example;
        ConstraintLayout layout;
        Model model_1;
        View view ;
        public ViewHolder(View itemView) {
            super(itemView);
            English_Word = itemView.findViewById(R.id.textout);
            Arabic_Word = itemView.findViewById(R.id.textinn);

            example = itemView.findViewById(R.id.text_example);
            layout = itemView.findViewById(R.id.layout_2);
            view = itemView.findViewById(R.id.view2);

            Arabic_Word.findViewById(R.id.textinn).setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View view) {
                    try {
                        if (String.valueOf(example.getVisibility()).equals("0")) {
                            int v = (example.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;
                            TransitionManager.beginDelayedTransition(layout, new AutoTransition());
                            example.setVisibility(v);
                        } else {

                            int v = (example.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;
                            TransitionManager.beginDelayedTransition(layout, new AutoTransition());
                            example.setVisibility(v);
                            if (model_1.getExample().equals("")) {
                                example.setHint("No Example Found");
                            } else {
                                example.setText(model_1.getExample());
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            });

            example.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

                    try {
                        Model model = new Model(model_1.getId(), model_1.getArabic_word(), model_1.getEnglish_word(), example.getText().toString(), "normal");
                        Database dp = new Database(example.getContext());
                        dp.update_note(model);
                    } catch (Exception e) {
                        Toast.makeText(itemView.getContext(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}
