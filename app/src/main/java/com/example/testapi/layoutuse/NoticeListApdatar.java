package com.example.testapi.layoutuse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapi.R;
import com.example.testapi.dataobjects.Notice;

import java.util.ArrayList;

public class NoticeListApdatar extends RecyclerView.Adapter<NoticeViewHolder> {

    Context context;
    ArrayList<Notice>  mylist;
    private  final   ItemViewInterface viewInterface;
    public NoticeListApdatar(Context context, ArrayList<Notice> mylist,ItemViewInterface viewInterfac) {
        this.context = context;
        this.mylist = mylist;
        this.viewInterface = viewInterfac;
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoticeViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item,parent,false),viewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder holder, int position) {
        holder.titelView.setText(mylist.get(position).getAnzeigeName());
        holder.beschreibungView.setText(mylist.get(position).getBeschreibung());
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }
}
