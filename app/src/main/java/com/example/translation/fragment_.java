package com.example.translation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.translation.Adapters.Adapter;
import com.example.translation.models.Model;

import java.util.ArrayList;

public class fragment_ extends Fragment {

View view ;
    ArrayList<Model> array ;
    RecyclerView recyclerView ;
    RecyclerView.LayoutManager layoutManager;
    Adapter adapter ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {
            view = inflater.inflate(R.layout.fragment_, container, false);

            array = new ArrayList<>();
            recyclerView = view.findViewById(R.id.res);

            Model m = new Model("scs" , "sc" , "sc" ,"sc");
            array.add(m);
            array.add(m);
            array.add(m);
            array.add(m);

            adapter = new Adapter( this.getContext() , array);
            layoutManager= new GridLayoutManager(this.getContext(), 1);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);



        }catch (Exception e){
            Toast.makeText(this.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

        return view ;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<String> ss = new ArrayList<>();
        ss.add("ll");
        ss.add("ss");
        ss.add("dd");

        Toast.makeText(getActivity(), ss.get(1), Toast.LENGTH_SHORT).show();



    }
}