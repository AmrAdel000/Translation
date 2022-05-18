package com.example.translation.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
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
import com.example.translation.MainActivity;
import com.example.translation.models.Model;
import com.example.translation.R;
import com.example.translation.Story_Translate;
import com.example.translation.split;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

 //   ArrayList<String> note , title ;
    Context context ;
    Database dp ;
    ArrayList<Model> ss ;
    ItemClickListener itemClickListener ;

    public Adapter(MainActivity mainActivity, ArrayList<Model> models, ItemClickListener itemClickListener) {
        context = mainActivity ;
        ss = models ;

        this.itemClickListener = itemClickListener ;
    }
    public Adapter(split split, ArrayList<Model> models, String s, ItemClickListener itemClickListener) {
        context = split ;
        ss = models ;
        this.itemClickListener = itemClickListener ;
    }

    public Adapter(Story_Translate story_translate, ArrayList<Model> models, ItemClickListener itemClickListener) {
        context = story_translate;
        ss = models ;
        this.itemClickListener = itemClickListener ;
    }

    public Adapter(Context context, ArrayList<Model> array) {
        this.context = context ;
        ss = array ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
           dp = new Database(context);

        View view ;

            view = inflater.inflate(R.layout.row, parent, false);

            return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        int c = 0 ;
        Model model = ss.get(position);

        if (!model.getExample().equals("")){
            c = 100000 ;
        }
        return c ;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {



        Model model = ss.get(position);

        holder.title.setText(model.getArabic_word());
        holder.note.setText(model.getEnglish_word());
        holder.example.setVisibility(View.GONE);
        holder.model_1 = model;

        if(ss.size() == (position+1)) {
            holder.vieww.setVisibility(View.INVISIBLE);
        }
        holder.itemView.setOnClickListener(view -> {
            itemClickListener.onItemClick(model, position);//it will get the position of our item in our resycler vew
        });

        if (holder.getItemViewType() == 100000){
                holder.vieww.setBackgroundColor(Color.parseColor(holder.color));
        }

    }
    public interface ItemClickListener {
        void onItemClick(Model details , int position);
    }

    @Override
    public int getItemCount() {
            return ss.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView note;
        EditText example ;
        ConstraintLayout layout ;
        Model model_1 ;
        View vieww ;
        String color = "#005500";

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textout);
            note = itemView.findViewById(R.id.textinn);
            example = itemView.findViewById(R.id.text_example);
            layout = itemView.findViewById(R.id.layout_2);
            vieww = itemView.findViewById(R.id.view2);

            note.findViewById(R.id.textinn).setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View view) {
                    if (String.valueOf(example.getVisibility()).equals("0")){
                        int v = (example.getVisibility() == View.GONE)? View .VISIBLE: View.GONE ;
                        TransitionManager.beginDelayedTransition(layout, new AutoTransition());
                        example.setVisibility(v);

                        if (!model_1.getExample().equals("")) {
                            vieww.setBackgroundColor(Color.parseColor(color));
                        }
                    }else {
                        int v = (example.getVisibility() == View.GONE)? View .VISIBLE: View.GONE ;
                        TransitionManager.beginDelayedTransition(layout, new AutoTransition());
                        example.setVisibility(v);
                        if (model_1.getExample().equals("")){
                            example.setHint("No Example Found");
                        }else {
                            example.setText(model_1.getExample());
                            vieww.setBackgroundResource(R.color.line_color);
                        }
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
                        Model model = new Model(model_1.getId() , model_1.getArabic_word() , model_1.getEnglish_word() , example.getText().toString() , "normal");
                        Database dp = new Database(example.getContext());
                        dp.update_note(model);
                    }catch (Exception e){
                        Toast.makeText(itemView.getContext(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
