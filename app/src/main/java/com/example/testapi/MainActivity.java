package com.example.testapi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static int userid;
    private ApiHandler apiHandler = null;
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
        apiHandler.fetchJsonList();
        Button button = findViewById(R.id.plusButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, AddRecousreScreen.class);
            startActivity(intent);
            }
        });
    }
}
