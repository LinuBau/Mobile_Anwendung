package com.example.testapi.activitys;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapi.R;
import com.example.testapi.apistuff.ApiHandler;

import java.util.ArrayList;

public class ChatListFragment extends  FragmentClickable{
    ApiHandler apiHandler ;
    View view;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        apiHandler = ((MainActivity) requireActivity()).getApiHandler();
        view = inflater.inflate(R.layout.list_of_chats,container,false);
        recyclerView = view.findViewById(R.id.chat_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        apiHandler.setRecyclerChatView(recyclerView);
        apiHandler.setItemChatInterface(this);
        apiHandler.fetchChatKey();
        return  view;
    }

    @Override
    public void onItemClick(int position) {
        apiHandler.createChatFragment(MainActivity.chatsKeys.get(position));
    }
}
