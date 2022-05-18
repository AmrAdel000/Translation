package com.example.translation.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.translation.Database_All.Database_files;
import com.example.translation.R;
import com.example.translation.models.Model;
import com.example.translation.models.Model_Book;

import java.util.ArrayList;

public class Adapter_Files_2 extends RecyclerView.Adapter<Adapter_Files_2.ViewHolder> {

    Context context ;

    String [] names;
    SharedPreferences shard ;
    ItemClickListener itemClickListener ;
    public Adapter_Files_2(Context mainActivity, String [] names , ItemClickListener onClickListener) {
        context = mainActivity ;
        shard = mainActivity.getSharedPreferences("Words", Context.MODE_PRIVATE);
        itemClickListener = onClickListener ;
        this.names = names ;
    }

    @NonNull
    @Override
    public Adapter_Files_2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);

        View view ;
        if ( viewType != 200){
            view = inflater.inflate(R.layout.row_files, parent, false);
        }else {
            view = inflater.inflate(R.layout.row_add_folder, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        int c =0;
        if (position == names.length) c =200 ;
        return c ;
    }

    @Override
    public void onBindViewHolder(Adapter_Files_2.ViewHolder holder, int position) {

        try {
            if (holder.getItemViewType() != 200){

                String  s =  names[position];

                String words_1 =  shard.getString(s ,"");
                String words_2 =  shard.getString(s+"2" ,"");

                holder.title.setText(s);
                holder.itemView.setOnClickListener(view -> {
                    Model_Book model = new Model_Book(0 , "" ,"" ,"" , "1010101");
                    itemClickListener.onItemClick(position , words_1 , words_2 , s);//it will get the position of our item in our resycler vew
                });
            }else {
                holder.itemView.setOnClickListener(view -> {
                    Model_Book model = new Model_Book(0 , "" ,"" ,"" , "1010101");
                    itemClickListener.onItemClick(position ,"1010101" , "axa" , "ss");//it will get the position of our item in our resycler vew
                });
            }


        }catch (Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }


    }
    public interface ItemClickListener {
        void onItemClick(int position, String words_1 , String words_2 , String name);
    }

    @Override
    public int getItemCount() {
        try {
           return names.length+1;
        }catch (Exception e){
            Toast.makeText(context, String.valueOf(e), Toast.LENGTH_SHORT).show();
           return 0 ;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.file_name);
        }
    }
    public static class ViewHolder_2 extends RecyclerView.ViewHolder {
        TextView title;
        public ViewHolder_2(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.file_name);
        }
    }
}
