package com.example.testapi;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView titelView,beschreibungView;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        titelView = itemView.findViewById(R.id.Titel);
        beschreibungView = itemView.findViewById(R.id.Beschreibung);
    }
}
