package com.example.translation.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.translation.Database_All.Database_files;
import com.example.translation.MainActivity;
import com.example.translation.R;
import com.example.translation.models.Model;
import com.example.translation.models.Model_Book;

import java.util.ArrayList;

public class Adapter_Files extends RecyclerView.Adapter<Adapter_Files.ViewHolder> {

 //   ArrayList<String> note , title ;
    Context context ;

    ArrayList<Model_Book> model_books;
    ItemClickListener itemClickListener ;
    public Adapter_Files(Context mainActivity, ArrayList<Model_Book> model_books , ItemClickListener onClickListener) {
        context = mainActivity ;
        itemClickListener = onClickListener ;
        this.model_books = model_books ;
    }

    @NonNull
    @Override
    public Adapter_Files.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        if (position == model_books.size()) c =200 ;
        return c ;
    }

    @Override
    public void onBindViewHolder(Adapter_Files.ViewHolder holder, int position) {

        try {
            if (holder.getItemViewType() != 200){

                Model_Book model=  model_books.get(position);
                holder.title.setText(model.getDate());
                holder.itemView.setOnClickListener(view -> {
                    itemClickListener.onItemClick(position , model);//it will get the position of our item in our resycler vew
                });
            }else {
                holder.itemView.setOnClickListener(view -> {
                    Model_Book model = new Model_Book(0 , "" ,"" ,"" , "1010101");
                    itemClickListener.onItemClick(position , model);//it will get the position of our item in our resycler vew
                });
            }


        }catch (Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }


    }
    public interface ItemClickListener {
        void onItemClick(int position, Model_Book model);
    }

    @Override
    public int getItemCount() {
        try {
            Database_files d = new Database_files(context);
            return Integer.parseInt(String.valueOf(d.getWordsCount()))+1;
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
