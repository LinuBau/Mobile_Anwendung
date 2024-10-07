package com.example.testapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyApdatar extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    ArrayList<SchwazesBrettAnzeige>  mylist;

    public MyApdatar(Context context, ArrayList<SchwazesBrettAnzeige> mylist) {
        this.context = context;
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.titelView.setText(mylist.get(position).getAnzeigeName());
        holder.beschreibungView.setText(mylist.get(position).getBeschreibung());
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }
}
