package com.example.testapi.activitys;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapi.R;
import com.example.testapi.apistuff.ApiHandler;
import com.example.testapi.dataobjects.Message;
import com.example.testapi.layoutuse.MessageListApdatar;

import java.util.ArrayList;

public class ChatViewFragment extends FragmentClickable{
    private ArrayList<Message> chat;
    private  int thisChat;
    private  MainActivity parent;
    private Handler handler;
    private  View view;
    private ImageButton imageButton;
    private EditText editText;
    private ApiHandler apiHandler ;
    RecyclerView recyclerView;
    private static final long REFRESH_INTERVAL = 2000;


    private final Runnable refreshRunnable = new Runnable() {
        @Override
        public void run() {
            refreshData();
            handler.postDelayed(this, REFRESH_INTERVAL);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.chat_list,container,false);
        parent = (MainActivity) requireActivity();
        apiHandler = parent.getApiHandler();
        handler = new Handler(Looper.getMainLooper());

        Bundle args = getArguments();
        chat =  args.getParcelableArrayList("ListaData");
        thisChat = args.getInt("ExtraInt");
        recyclerView = view.findViewById(R.id.chat_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new MessageListApdatar(parent.getApplicationContext(),chat,MainActivity.userid));
        editText = view.findViewById(R.id.message_input);
        imageButton = view.findViewById(R.id.send_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editText.getText().toString().isEmpty()){
                    apiHandler.sendMessage(new Message(MainActivity.userid,thisChat,editText.getText().toString()));
                    apiHandler.updateChat((MessageListApdatar) recyclerView.getAdapter(),thisChat);
                    editText.setText("");
                }
            }
        });
        startAutoRefresh();
        return  view;
    }

    private void refreshData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    parent.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            apiHandler.updateChat((MessageListApdatar) recyclerView.getAdapter(),thisChat);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    parent.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            apiHandler.updateChat((MessageListApdatar) recyclerView.getAdapter(),thisChat);
                        }
                    });
                }
            }
        }).start();
    }
    private void startAutoRefresh() {
        handler.postDelayed(refreshRunnable, REFRESH_INTERVAL);
    }
    @Override
    public   void onDestroyView() {
        super.onDestroyView();
        if (handler != null) {
            handler.removeCallbacks(refreshRunnable);
        }
    }
}
