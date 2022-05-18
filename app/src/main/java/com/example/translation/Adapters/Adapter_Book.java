package com.example.translation.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.translation.Books_Main;
import com.example.translation.Database_All.Database_Book;
import com.example.translation.R;
import com.example.translation.models.Model_Book;

public class Adapter_Book extends RecyclerView.Adapter<Adapter_Book.ViewHolder> {

 //   ArrayList<String> note , title ;
    Context context ;
    Database_Book dp ;
    ItemClickListener itemClickListener ;
    public Adapter_Book(Books_Main mainActivity, ItemClickListener onClickListener) {
        context = mainActivity ;
        itemClickListener = onClickListener ;
    }

    @NonNull
    @Override
    public Adapter_Book.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.row1, parent, false);
            dp = new Database_Book(context);
            return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Adapter_Book.ViewHolder holder, int position) {

                        Model_Book model=  dp.bitmaps(position+1);

                        holder.title .setText(model.getTitle());
                        holder.note .setText(model.getNote());
                        holder.itemView.setOnClickListener(view -> {
                            itemClickListener.onItemClick(model , position);//it will get the position of our item in our resycler vew
                        });
    }
    public interface ItemClickListener {
        void onItemClick(Model_Book details , int position);
    }

    @Override
    public int getItemCount() {
        try {
            Database_Book d = new Database_Book(context);
            return Integer.parseInt(String.valueOf(d.getNotesCount()));
        }catch (Exception e){
            Toast.makeText(context, String.valueOf(e), Toast.LENGTH_SHORT).show();
           return 0 ;
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView note;
        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.mytext);
            note = itemView.findViewById(R.id.textin);

        }
    }
}
