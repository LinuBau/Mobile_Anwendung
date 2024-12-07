package com.example.testapi.layoutuse;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapi.R;

public class NoticeViewHolder extends RecyclerView.ViewHolder {
    TextView titelView,beschreibungView;

    public NoticeViewHolder(@NonNull View itemView,ItemViewInterface itemViewInterface) {
        super(itemView);
        titelView = itemView.findViewById(R.id.Titel);
        beschreibungView = itemView.findViewById(R.id.Beschreibung);

        itemView.setOnClickListener(view -> {
            int pos = getAbsoluteAdapterPosition();
            if (itemViewInterface != null && pos != RecyclerView.NO_POSITION) {
                itemViewInterface.onItemClick(pos);
            }
        });
    }
}
