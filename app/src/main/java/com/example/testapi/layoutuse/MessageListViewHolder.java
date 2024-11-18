package com.example.testapi.layoutuse;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapi.R;

public class MessageListViewHolder extends RecyclerView.ViewHolder {
    TextView messageText;
    TextView messageTime;
    LinearLayout messageBubble;

    public MessageListViewHolder(@NonNull View itemView) {
        super(itemView);
        messageText = itemView.findViewById(R.id.message_text);
        messageTime = itemView.findViewById(R.id.message_time);
        messageBubble = itemView.findViewById(R.id.message_bubble);
    }
}
