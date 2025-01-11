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

    public void saveBoolean(String key,Boolean bool){
        editor.putBoolean(key,bool);
        editor.apply();
    }
    public  Boolean getBoolean(String key){
        return sharedPreferences.getBoolean(key,true);
    }

    // Get user data
    public int getUserId() {
        return sharedPreferences.getInt("userid", 9999);
    }

    // Clear all user data
    public void clearUserData() {
        editor.clear();
        editor.apply();
    }

    // Achievementskram
    public void saveAchievementProgress(String achievementName, int progress) {
        editor.putInt(achievementName, progress);
        editor.apply();
    }
    public void saveAchievementProgress(String achievementName, long progress) {
        editor.putLong(achievementName, progress);
        editor.apply();
    }

    public int getAchievementProgress(String achievementName) {
            return sharedPreferences.getInt(achievementName, 0);
    }

    public Long getAchievementProgress(String achievementName,boolean x) {
        if (hasKey(achievementName)){
            return sharedPreferences.getLong(achievementName, 0);
        }
        return  null;
    }

    public void resetAchievementProgress(String achievementName) {
        editor.remove(achievementName);
        editor.apply();
    }

    public void clearAllAchievements() {
        for (String key : sharedPreferences.getAll().keySet()) {
            if (!key.equals("userid")) {
                editor.remove(key);
            }
        }
        editor.apply();
    }
}
