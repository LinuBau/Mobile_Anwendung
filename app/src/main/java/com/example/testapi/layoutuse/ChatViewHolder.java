package com.example.testapi.layoutuse;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapi.R;

public class ChatViewHolder extends RecyclerView.ViewHolder {
    TextView chat_item_View;

    public ChatViewHolder(@NonNull View itemView, ItemViewInterface itemViewInterface) {
        super(itemView);
        chat_item_View = itemView.findViewById(R.id.chat_item);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemViewInterface != null){
                    int pos = getAbsoluteAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        itemViewInterface.onItemClick(pos);
                    }
                }
            }
        });
    }
}
