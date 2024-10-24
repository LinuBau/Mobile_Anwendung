package com.example.testapi.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapi.apistuff.ApiHandler;
import com.example.testapi.apistuff.FlaskApiService;
import com.example.testapi.R;
import com.example.testapi.dataobjects.Notice;

import java.util.ArrayList;
import java.util.Random;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends ActivityClickable {
    public static int userid = 9999;
    private ApiHandler apiHandler = null;
    public  static ArrayList<Notice> notices = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Random random = new Random();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://10.0.2.2:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FlaskApiService apiService = retrofit.create(FlaskApiService.class);
        RecyclerView recyclerView = findViewById(R.id.FristList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apiHandler = new ApiHandler(this,apiService,recyclerView);
        apiHandler.fetchJsonList(1);
        apiHandler.setOrValidUserId();
        Button button = findViewById(R.id.plusButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, AddRecousreScreen.class);
            startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this,ClickedNotice.class);
        intent.putExtra("notice",MainActivity.notices.get(position));
        Log.d("Onclick", "onItemClick() called with: position = [" + position + MainActivity.notices.get(position).toString()+"]");
        startActivity(intent);
    }
}
