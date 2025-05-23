package com.example.testapi.activitys;

import android.annotation.SuppressLint;
import android.content.Context;
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

import java.util.Date;
import com.example.testapi.Achievements.Achievement;
import com.example.testapi.R;
import com.example.testapi.apistuff.ApiHandler;
import com.example.testapi.apistuff.FlaskApiService;
import com.example.testapi.dataobjects.Notice;
import com.example.testapi.dataobjects.UserDataManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.ArrayList;
import android.widget.Toast;
import java.util.Calendar;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * MainActivity ist die main Klasse der Anwendung. Diese Klasse wird bei Appstart gestartet und
 * ist für die Funktion der gesamten App notwendig
 */
public class MainActivity extends AppCompatActivitySafe {
    public BottomNavigationView tabLayout;
    public  static  ArrayList<Integer> chatsKeys;
    public static int userid = 9999;
    private ApiHandler apiHandler = null;
    public  static ArrayList<Notice> notices = null;
    private Handler handler;
    private UserDataManager userDataManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final long REFRESH_INTERVAL = 60000;
    public  static  boolean isLogtin=false;
    private Achievement achievement;




    private final Runnable refreshRunnable = new Runnable() {
        @Override
        public void run() {
            refreshData();
            handler.postDelayed(this, REFRESH_INTERVAL);

        }
    };


    // ------------------------------------------------------------------------------------------------------
    private Context context;
    public void currentDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int minutes = calendar.get(Calendar.MINUTE);

        achievement.Date2Integer(year, month, day, minutes);
        Toast.makeText(context, "Streaklänge ", Toast.LENGTH_SHORT).show(); // zum debuggen
    }
    // -------------------------------------------------------------------------------------------------------



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            replaceFragment(new ListViewFragment());
        }

        AndroidThreeTen.init(this);

        tabLayout = findViewById(R.id.bottom_navigation);
        swipeRefreshLayout = findViewById(R.id.swipeToUpdate);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.188.150:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FlaskApiService apiService = retrofit.create(FlaskApiService.class);

        handler = new Handler(Looper.getMainLooper());
        userDataManager = new UserDataManager(getApplicationContext());
        MainActivity.userid  = userDataManager.getUserId();
        achievement = Achievement.createInstance(this,userDataManager);
        apiHandler = new ApiHandler(this,apiService);
        apiHandler.setOrValidUserId();
        achievement.checkAndUpdateLastLogin();
        setupTabs();




        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        startAutoRefresh();

    }


    public void trackAchievements(String AchievementType) {

        int currentProgress = userDataManager.getAchievementProgress(AchievementType);
        currentProgress++;
        userDataManager.saveAchievementProgress(AchievementType, currentProgress);
        achievement.totalPosts(currentProgress);
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

    @Override
    protected void onResume() {
        super.onResume();
        achievement.checkAndUpdateLastLogin();
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

    public  UserDataManager getUserDataManager(){
        return  userDataManager;
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
