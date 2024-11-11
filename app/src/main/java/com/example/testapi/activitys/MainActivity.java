package com.example.testapi.activitys;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.testapi.R;
import com.example.testapi.apistuff.ApiHandler;
import com.example.testapi.apistuff.FlaskApiService;
import com.example.testapi.dataobjects.Notice;
import com.example.testapi.dataobjects.UserDataManager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivitySafe {
    private TabLayout tabLayout;
    public  static  ArrayList<Integer> chatsKeys;
    public static int userid = 9999;
    private ApiHandler apiHandler = null;
    public  static ArrayList<Notice> notices = null;
    private Handler handler;
    private UserDataManager userDataManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final long REFRESH_INTERVAL = 60000;
    private ArrayList<String> tags ;

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
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragmentContainer, ListViewFragment.class, null)
                    .commit();
        }
        tabLayout = findViewById(R.id.tabLayout);
        swipeRefreshLayout = findViewById(R.id.swipeToUpdate);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://10.0.2.2:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FlaskApiService apiService = retrofit.create(FlaskApiService.class);

        handler = new Handler(Looper.getMainLooper());
        userDataManager = new UserDataManager(getApplicationContext());
        MainActivity.userid  = userDataManager.getUserId();

        apiHandler = new ApiHandler(this,apiService);
        apiHandler.setOrValidUserId();
        setupTabs();
        replaceFragment(new ListViewFragment(),true);



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        startAutoRefresh();

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
        Log.e("List Log :",MainActivity.notices.get(pos).toString());
        Bundle args = new Bundle();
        args.putParcelable("notice",MainActivity.notices.get(pos));
        fragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
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
    private void setupTabs() {
        // Tabs hinzufügen
        tabLayout.addTab(tabLayout.newTab().setText("List View"));
        tabLayout.addTab(tabLayout.newTab().setText("Add Resource"));

        // Listener für Tabwechsel
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(@NonNull TabLayout.Tab tab) {
                Fragment selectedFragment;
                if (tab.getPosition() == 0) {
                    selectedFragment = new ListViewFragment();
                } else {
                    selectedFragment = new ChatListFragment();
                }
                replaceFragment(selectedFragment);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
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
