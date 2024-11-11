package com.example.testapi.activitys;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapi.R;
import com.example.testapi.dataobjects.Message;
import com.example.testapi.layoutuse.MessageListApdatar;

import java.util.ArrayList;

public class ChatViewFragment extends FragmentClickable{
    private ArrayList<Message> chat;
    private  MainActivity parent;
    private  View view;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        parent = (MainActivity) requireActivity();
        chat = savedInstanceState.getParcelableArrayList("ListaData");
        view = inflater.inflate(R.layout.chat_list,container,false);
        recyclerView = view.findViewById(R.id.chat_recycler_view);
        recyclerView.setAdapter(new MessageListApdatar(parent.getApplicationContext(),chat,MainActivity.userid));
        return  view;
    }
}
