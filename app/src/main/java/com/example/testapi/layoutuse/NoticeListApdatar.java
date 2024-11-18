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
import java.util.Collections;
import java.util.List;

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
        holder.beschreibungView.setText(Html.fromHtml(mylist.get(position).getBeschreibung(), Html.FROM_HTML_MODE_LEGACY));
    }
    public void updateData(List<Notice> newItems) {
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
                return mylist.get(oldPos).getErstellerId() == newItems.get(newPos).getErstellerId();
            }

            @Override
            public boolean areContentsTheSame(int oldPos, int newPos) {
                Notice oldItem = mylist.get(oldPos);
                Notice newItem = newItems.get(newPos);
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
