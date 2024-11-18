package com.example.testapi.layoutuse;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapi.R;
import com.example.testapi.dataobjects.Notice;

import java.util.ArrayList;
import java.util.List;

public class ChatListApdatar extends RecyclerView.Adapter<ChatViewHolder> {

    Context context;
    ArrayList<Integer> mylist;
    private  final   ItemViewInterface viewInterface;
    public ChatListApdatar(Context context, ArrayList<Integer> mylist,ItemViewInterface viewInterfac) {
        this.context = context;
        this.mylist = mylist;
        this.viewInterface = viewInterfac;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_item,parent,false),viewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.chat_item_View.setText(mylist.get(position).toString());
    }

    public void updateData(List<Integer> newItems) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return mylist.size();
            }

            @Override
            public int getNewListSize() {
                return newItems.size();
            }

            @Override
            public boolean areItemsTheSame(int oldPos, int newPos) {
                return mylist.get(oldPos).intValue() == newItems.get(newPos).intValue();
            }

            @Override
            public boolean areContentsTheSame(int oldPos, int newPos) {
                Integer oldItem = mylist.get(oldPos);
                Integer newItem = newItems.get(newPos);
                return oldItem.equals(newItem);
            }
        });

        mylist = new ArrayList<>(newItems);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }
}
