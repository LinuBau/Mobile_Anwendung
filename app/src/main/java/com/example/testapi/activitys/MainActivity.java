package com.example.testapi.activitys;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.testapi.R;
import com.example.testapi.apistuff.ApiHandler;
import com.example.testapi.apistuff.FlaskApiService;
import com.example.testapi.dataobjects.Notice;
import com.example.testapi.dataobjects.UserDataManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivitySafe {
    private BottomNavigationView tabLayout;
    public  static  ArrayList<Integer> chatsKeys;
    public static int userid = 9999;
    private ApiHandler apiHandler = null;
    public  static ArrayList<Notice> notices = null;
    private Handler handler;
    private UserDataManager userDataManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final long REFRESH_INTERVAL = 60000;
    public  static  boolean isLogtin=false;

    private final Runnable refreshRunnable = new Runnable() {
        @Override
        public void run() {
            refreshData();
            handler.postDelayed(this, REFRESH_INTERVAL);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            replaceFragment(new ListViewFragment());
        }

        tabLayout = findViewById(R.id.bottom_navigation);
        swipeRefreshLayout = findViewById(R.id.swipeToUpdate);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.178.76:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FlaskApiService apiService = retrofit.create(FlaskApiService.class);

        handler = new Handler(Looper.getMainLooper());
        userDataManager = new UserDataManager(getApplicationContext());
        MainActivity.userid  = userDataManager.getUserId();

        apiHandler = new ApiHandler(this,apiService);
        apiHandler.setOrValidUserId();
        setupTabs();




        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        startAutoRefresh();

        trackAchievements();
    }


    public void trackAchievements() {

        String AchievementType = "TotalPosts";
        int targetProgress = 1;

        int currentProgress = userDataManager.getAchievementProgress(AchievementType);


        currentProgress++;
        userDataManager.saveAchievementProgress(AchievementType, currentProgress);
        // Log.d hat einfach nicht funktioniert wtf, aber das klappt fml ich hasse alles ich will sterben.
        // Knapp ne Stunde debuggen weil ich dachte es liegt an mir was soll der scheiß android
        if (currentProgress >= targetProgress) {
            Toast.makeText(this, "trackAchievements() größer als target!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "trackAchievements() kleiner als target", Toast.LENGTH_SHORT).show();
        }
    }
    private void refreshData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            apiHandler.fetchJsonList(ApiHandler.UPDATE_RECYLERVIEW);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            }
        }).start();
    }

    private void startAutoRefresh() {
        handler.postDelayed(refreshRunnable, REFRESH_INTERVAL);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
    public void replaceFragment(Fragment fragment,boolean t) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }
    public void replaceFragment(Fragment fragment,int pos) {
        Bundle args = new Bundle();
        args.putParcelable("notice",MainActivity.notices.get(pos));
        fragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }
    public void replaceFragment(Fragment fragment,int pos,boolean t) {
        Bundle args = new Bundle();
        args.putParcelable("notice",MainActivity.notices.get(pos));
        fragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
    public void replaceFragment(Fragment fragment, ArrayList<Parcelable> list){
        Bundle args = new Bundle();
        args.putParcelableArrayList("ListaData",list);
        fragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer,fragment)
                .addToBackStack(null)
                .commit();
    }
    public void replaceFragment(Fragment fragment, ArrayList<Parcelable> list, int i){
        Bundle args = new Bundle();
        args.putParcelableArrayList("ListaData",list);
        args.putInt("ExtraInt",i);
        fragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer,fragment)
                .addToBackStack(null)
                .commit();
    }
    private void setupTabs() {

        tabLayout.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment;

                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {
                    selectedFragment = new ListViewFragment();
                } else if (itemId == R.id.navigation_message) {
                    selectedFragment = new ChatListFragment();
                } else {
                    return false;
                }

                replaceFragment(selectedFragment);
                return true;
            }
        });
    }
    public  void setNavigationBarTab(int id){
        tabLayout.setSelectedItemId(id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Entferne den Handler beim Zerstören der Activity
        if (handler != null) {
            handler.removeCallbacks(refreshRunnable);
        }
    }
    @Override
    public  void safeUserID(){
        userDataManager.saveUserData(MainActivity.userid);
    }
    public  ApiHandler getApiHandler(){
        return apiHandler;
    }
}
