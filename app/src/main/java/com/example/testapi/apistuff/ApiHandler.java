package com.example.testapi.apistuff;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testapi.dataobjects.Notice;
import com.example.testapi.layoutuse.NoticeListApdatar;
import com.example.testapi.activitys.ActivityClickable;
import com.example.testapi.activitys.MainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiHandler  {
    private ActivityClickable parent;
    private  FlaskApiService apiService;
    private  RecyclerView recyclerView;

    public ApiHandler(ActivityClickable parent, FlaskApiService apiService, RecyclerView recyclerView) {
        this.parent = parent;
        this.apiService = apiService;
        this.recyclerView = recyclerView;
    }
    public ApiHandler(ActivityClickable parent, FlaskApiService apiService){
        this.parent = parent;
        this.apiService = apiService;
    }

    public  void fetchJsonList(int mode){
        if (parent == null || recyclerView == null){
            return;
        }
    Call<List<Notice>> call = null;
    try{
         call = apiService.getJsonList();
        Log.d("Retrofit", "Request URL: " + call.request().url());
    }catch(Exception e){
     e.printStackTrace();
        Log.e("Fehler bei Call","Einfehler aufgetrenten" + e.getMessage());
    }
    try {
        call.enqueue(new Callback<List<Notice>>() {
            @Override
            public void onResponse(Call<List<Notice>> call, Response<List<Notice>> response) {
                if(response.isSuccessful()){
                    List<Notice>  jsonList = response.body();
                    switch (mode){
                        case 1:
                            Log.d("Lenght of Array", "onResponse: "+jsonList.size());
                            recyclerView.setAdapter(new NoticeListApdatar(parent.getApplicationContext(),new ArrayList<>(jsonList),parent));
                            MainActivity.notices = new  ArrayList<>(jsonList);
                        break;
                        case 2:
                            NoticeListApdatar apdatar = (NoticeListApdatar) recyclerView.getAdapter();
                            apdatar.updateData(jsonList);
                        break;
                        default:
                            Toast.makeText(parent, "Mode beim Api Handling nicht vorhanden", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Notice>> call, Throwable t) {
                Log.e("Retrofit Fehler", "Fehler beim Abrufen der Daten: " + t.getMessage());
                Toast.makeText(parent, "Fehler beim Abrufen der Daten", Toast.LENGTH_SHORT).show();
            }
        });
    }catch (Exception e){
        e.printStackTrace();
        Log.e("Fehler bei Call","Einfehler aufgetrenten" + e.getMessage());
    }

}
    public void addJsonList(String titel,String beschreibung,int userid){
        Notice newAnzeige = new Notice(beschreibung,titel,userid);
        Call call = apiService.addJsonObject(newAnzeige);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()){
                    Toast.makeText(parent,"Daten schicken war erfolgreich",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("Retrofit Fehler", "Fehler beim Senden der Daten: " + t.getMessage());
                Toast.makeText(parent, "Fehler beim Senden der Daten", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public  void setOrValidUserId(){
        if (MainActivity.userid == 9999){
            Call<Notice> call = apiService.getUserID();
            call.enqueue(new Callback<Notice>() {
                @Override
                public void onResponse(Call<Notice> call, Response<Notice> response) {
                    if(response.isSuccessful()){
                        Notice temp = response.body();
                        MainActivity.userid = temp.getErstellerId();

                    }
                }

                @Override
                public void onFailure(Call<Notice> call, Throwable t) {
                    MainActivity.userid = 9999;
                    Log.e("Retrofit Fehler", "Fehler beim Abrufen der UserID: " + t.getMessage());
                    Toast.makeText(parent, "UsserID könnte nicht bestimmte werden", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Call<Integer> call = apiService.addUserID(MainActivity.userid);
            call.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.isSuccessful()){
                        Integer handshake = response.body();
                        if (handshake != MainActivity.userid){
                            MainActivity.userid = handshake;
                        }
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    MainActivity.userid = 9999;
                    Log.e("Retrofit Fehler", "Fehler beim Abrufen der UserID: " + t.getMessage());
                    Toast.makeText(parent, "UsserID könnte nicht bestimmte werden", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }



}
