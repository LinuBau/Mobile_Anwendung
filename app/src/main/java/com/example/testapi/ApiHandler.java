package com.example.testapi;

import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiHandler {
    private   MainActivity  parent;
    private  FlaskApiService apiService;
    private  RecyclerView recyclerView;

    public ApiHandler(MainActivity parent, FlaskApiService apiService, RecyclerView recyclerView) {
        this.parent = parent;
        this.apiService = apiService;
        this.recyclerView = recyclerView;
    }

    public  void fetchJsonList(){
    Call<List<SchwazesBrettAnzeige>> call = null;
    try{
         call = apiService.getJsonList();
        Log.d("Retrofit", "Request URL: " + call.request().url());
    }catch(Exception e){
     e.printStackTrace();
        Log.e("Fehler bei Call","Einfehler aufgetrenten" + e.getMessage());
    }
    try {
        call.enqueue(new Callback<List<SchwazesBrettAnzeige>>() {
            @Override
            public void onResponse(Call<List<SchwazesBrettAnzeige>> call, Response<List<SchwazesBrettAnzeige>> response) {
                if(response.isSuccessful()){
                  List<SchwazesBrettAnzeige>  jsonList = response.body();
                    Log.d("Lenght of Array", "onResponse: "+jsonList.size());
                  recyclerView.setAdapter(new MyApdatar(parent.getApplicationContext(),new ArrayList<>(jsonList)));
                }
            }

            @Override
            public void onFailure(Call<List<SchwazesBrettAnzeige>> call, Throwable t) {
                Log.e("Retrofit Fehler", "Fehler beim Abrufen der Daten: " + t.getMessage());
                Toast.makeText(parent, "Fehler beim Abrufen der Daten", Toast.LENGTH_SHORT).show();
            }
        });
    }catch (Exception e){
        e.printStackTrace();
        Log.e("Fehler bei Call","Einfehler aufgetrenten" + e.getMessage());
    }

}


}
