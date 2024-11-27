package com.example.testapi.dataobjects;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class UserDataManager {
    private static final String PREF_NAME = "UserPrefs";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public UserDataManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public boolean hasKey(String key) {
        return sharedPreferences.contains(key);
    }
    // Save user data
    public void saveUserData(int userId) {
        editor.putInt("userid", userId);
        editor.apply();
    }

    // Get user data
    public int getUserId() {
        if (hasKey("userid")){
            return sharedPreferences.getInt("userid", 0);
        }
        return 9999;

    }



    // Clear all user data
    public void clearUserData() {
        editor.clear();
        editor.apply();
    }
}
