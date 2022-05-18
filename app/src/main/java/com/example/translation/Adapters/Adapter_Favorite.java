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

import com.example.translation.Database_All.Database_2;
import com.example.translation.Favorite;
import com.example.translation.models.Model;
import com.example.translation.R;

import java.util.ArrayList;

public class Adapter_Favorite extends RecyclerView.Adapter<Adapter_Favorite.ViewHolder> {

 //   ArrayList<String> note , title ;
    Context context ;
    ArrayList<Model> models ;
    ItemClickListener itemClickListener ;
    public Adapter_Favorite(Favorite favorite, ArrayList<Model> models, ItemClickListener onClickListener) {
        context = favorite ;
        this.models = models ;
        itemClickListener = onClickListener ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.row, parent, false);
            return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            Model model=  models.get(position);
            holder.title.setText(model.getArabic_word());
            holder.note .setText(model.getEnglish_word());
            holder.example.setVisibility(View .GONE);
            if(models.size() == (position+1)) {
                holder.view.setVisibility(View.INVISIBLE);
            }
            holder.model_1 = model ;
            holder.itemView.setOnClickListener(view -> {
                itemClickListener.onItemClick(model , position);//it will get the position of our item in our resycler vew
            });
    }
    public interface ItemClickListener {
        void onItemClick(Model details , int position);
    }

    @Override
    public int getItemCount() {
         return models.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView note;
        EditText example ;
        ConstraintLayout layout ;
        Model model_1 ;
        View view ;
        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textout);
            note = itemView.findViewById(R.id.textinn);
            example = itemView.findViewById(R.id.text_example);
            layout = itemView.findViewById(R.id.layout_2);
            view = itemView.findViewById(R.id.view2);
            note.findViewById(R.id.textinn).setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View view) {
                    if (String.valueOf(example.getVisibility()).equals("0")){
                        int v = (example.getVisibility() == View.GONE)? View .VISIBLE: View.GONE ;
                        TransitionManager.beginDelayedTransition(layout, new AutoTransition());
                        example.setVisibility(v);
                    }else {

                        int v = (example.getVisibility() == View.GONE)? View .VISIBLE: View.GONE ;
                        TransitionManager.beginDelayedTransition(layout, new AutoTransition());
                        example.setVisibility(v);
                        if (model_1.getExample().equals("")){
                            example.setHint("No Example Found");
                        }else {
                            example.setText(model_1.getExample());
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
                        Database_2 dp = new Database_2(example.getContext());
                        dp.update_note(model);
                    }catch (Exception e){
                        Toast.makeText(itemView.getContext(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
    }
}
