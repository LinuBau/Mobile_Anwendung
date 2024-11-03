package com.example.testapi.apistuff;

import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testapi.activitys.AppCompatActivitySafe;
import com.example.testapi.activitys.MainActivity;
import com.example.testapi.dataobjects.Notice;
import com.example.testapi.layoutuse.ChatListApdatar;
import com.example.testapi.layoutuse.ItemViewInterface;
import com.example.testapi.layoutuse.NoticeListApdatar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiHandler  {
    private AppCompatActivitySafe parent;
    private  FlaskApiService apiService;
    private  RecyclerView recyclerListView;
    private RecyclerView recyclerChatView ;
    private  ItemViewInterface chatInterface;
    private ItemViewInterface itemViewInterface;
    public  static  final int CREATE_RECYLERVIEW = 1;
    public  static  final int UPDATE_RECYLERVIEW = 2;

    public ApiHandler(AppCompatActivitySafe parent, FlaskApiService apiService, RecyclerView recyclerListView) {
        this.parent = parent;
        this.apiService = apiService;
        this.recyclerListView = recyclerListView;
    }
    public ApiHandler(AppCompatActivitySafe parent, FlaskApiService apiService){
        this.parent = parent;
        this.apiService = apiService;
    }

    public  void fetchJsonList(int mode){
        if (parent == null || recyclerListView == null){
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
                    List<Notice>  tjsonList = response.body();
                    ArrayList<Notice> jsonList = new ArrayList<>(tjsonList);
                    jsonList.sort(Notice.NoticeDescendingTime);
                    switch (mode){
                        case 1:
                            if (recyclerListView != null){
                                recyclerListView.setAdapter(new NoticeListApdatar(parent.getApplicationContext(),new ArrayList<>(jsonList),itemViewInterface));
                                MainActivity.notices = new  ArrayList<>(jsonList);
                            }
                        break;
                        case 2:
                            if (recyclerListView != null) {
                                parent.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        NoticeListApdatar apdatar = (NoticeListApdatar) recyclerListView.getAdapter();
                                        MainActivity.notices = new ArrayList<>(jsonList);
                                        assert apdatar != null;
                                        apdatar.updateData(jsonList);
                                    }
                                });
                            }

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
        Notice newAnzeige = new Notice(beschreibung,titel,userid,0);
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
                        parent.safeUserID();
                    }
                }

                @Override
                public void onFailure(Call<Notice> call, Throwable t) {
                    MainActivity.userid = 9999;
                    parent.safeUserID();
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
                            parent.safeUserID();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    MainActivity.userid = 9999;
                    parent.safeUserID();
                    Log.e("Retrofit Fehler", "Fehler beim Abrufen der UserID: " + t.getMessage());
                    Toast.makeText(parent, "UsserID könnte nicht bestimmte werden", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    public void fetchChatKey(){
        Call<List<Integer>> call = apiService.getChatKeys(MainActivity.userid);
        call.enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                if (response.isSuccessful()){
                    List<Integer> keyList = response.body();
                    MainActivity.chatsKeys = new ArrayList<>(keyList);
                    recyclerChatView.setAdapter(new ChatListApdatar(parent.getApplicationContext(),MainActivity.chatsKeys,chatInterface));
                }
            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {
                Log.e("Retrofit Fehler", "Fehler beim Abrufen der ChatKeys: " + t.getMessage());
                Toast.makeText(parent, "Keys könnte nicht bestimmte werden", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public  void setRecyclerListView(RecyclerView rv){
        this.recyclerListView = rv ;
    }
    public void setItemViewInterface(ItemViewInterface ivt){
        this.itemViewInterface = ivt;
    }
    public  void setRecyclerChatView(RecyclerView rv){
        this.recyclerChatView = rv ;
    }
    public void setItemChatInterface(ItemViewInterface ivt){
        this.chatInterface = ivt;
    }



}
