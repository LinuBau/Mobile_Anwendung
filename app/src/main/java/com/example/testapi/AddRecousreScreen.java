package com.example.testapi;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddRecousreScreen extends AppCompatActivity {
    private  ApiHandler  apiHandler = null;
    private EditText flied1;
    private EditText flied2;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_eintrag_screen);
        Button button = findViewById(R.id.addButton);
        flied1 = findViewById(R.id.textField1);
        flied2 = findViewById(R.id.textField2);
        createApiHandler();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(flied1.getText().toString().isEmpty()||flied2.getText().toString().isEmpty()){
                Toast.makeText(AddRecousreScreen.this, "Please enter both the values", Toast.LENGTH_SHORT).show();
            }else {
                String titel = flied1.getText().toString();
                String beschreibung = flied2.getText().toString();
                postData(titel,beschreibung,MainActivity.userid);
            }
            }
        });
    }
    private   void postData(String titel,String beschreibung ,int userid){
        apiHandler.addJsonList(titel,beschreibung,userid);
    }
    private  void  createApiHandler(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://10.0.2.2:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FlaskApiService apiService = retrofit.create(FlaskApiService.class);
        apiHandler = new ApiHandler(this,apiService);
    }
}
