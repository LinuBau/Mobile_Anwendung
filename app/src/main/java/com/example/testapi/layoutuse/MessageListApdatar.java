package com.example.testapi.layoutuse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapi.R;
import com.example.testapi.dataobjects.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageListApdatar extends RecyclerView.Adapter<MessageListViewHolder> {
    private static final int TYPE_SENT = 1;
    private static final int TYPE_RECEIVED = 2;

    private ArrayList<Message> messages;
    private Context context;
    private int currentUserId; // To identify sent vs received messages

    public MessageListApdatar(Context context, ArrayList<Message> messages, int currentUserId) {
        this.context = context;
        this.messages = messages;
        this.currentUserId = currentUserId;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if (message.getSender() ==currentUserId) {
            return TYPE_SENT;
        }
        return TYPE_RECEIVED;
    }

    public void updateMessages(ArrayList<Message> newMessages) {
        this.messages.clear();
        this.messages.addAll(newMessages);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_bubble, parent, false);
        return new MessageListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageListViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.messageText.setText(message.getMessage());
        holder.messageTime.setText(message.getMessage());

        // Set layout parameters based on message type
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.messageBubble.getLayoutParams();

        if (getItemViewType(position) == TYPE_SENT) {
            params.addRule(RelativeLayout.ALIGN_PARENT_END);
            params.removeRule(RelativeLayout.ALIGN_PARENT_START);
            holder.messageBubble.setBackgroundResource(R.drawable.sent_message_bubble);
            params.setMarginStart(64); // Add margin to the start for sent messages
            params.setMarginEnd(8);
        } else {
            params.addRule(RelativeLayout.ALIGN_PARENT_START);
            params.removeRule(RelativeLayout.ALIGN_PARENT_END);
            holder.messageBubble.setBackgroundResource(R.drawable.received_message_bubble);
            params.setMarginStart(8);
            params.setMarginEnd(64); // Add margin to the end for received messages
        }

        holder.messageBubble.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

}
