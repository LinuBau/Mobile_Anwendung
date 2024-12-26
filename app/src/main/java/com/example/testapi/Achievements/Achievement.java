package com.example.testapi.Achievements;


import android.content.Context;
import android.widget.Toast;

import com.example.testapi.dataobjects.UserDataManager;

import org.threeten.bp.Duration;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.LocalDate;

public class Achievement {

    private UserDataManager userDataManager;
    private Context context;

    private Achievement(Context context,UserDataManager dataManager) {  // Konstruktor bleibt private
        this.context = context;
        this.userDataManager = dataManager;
    }
    public static Achievement createInstance(Context context,UserDataManager dataManager) {
        return new Achievement(context,dataManager);
    }

    public void totalPosts(int progress) {
        //Toast.makeText(context, "ES FUNKT " + progress, Toast.LENGTH_SHORT).show();
        switch (progress) {
            case 1:
                Toast.makeText(context, "Total Posts" + ": Erster Posts!", Toast.LENGTH_SHORT).show();
                break;
            case 5:
                Toast.makeText(context, "Total Posts" + ": Interessant unterwegs! 5 Posts", Toast.LENGTH_SHORT).show();
                break;
            case 10:
                Toast.makeText(context, "Total Posts" + ": Veteran. 10 Posts", Toast.LENGTH_SHORT).show();
                break;

            default:
                System.err.println();
        }
    }

    public void dailyStreak() {
        int streak = userDataManager.getAchievementProgress("Streak");
        if (streak > 0) {
            Toast.makeText(context, "Daily Streak" + "Du bist schon " + streak + " Tage dabei!", Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "Streaklänge " + streak, Toast.LENGTH_SHORT).show();
        }
    }

    // Method to convert Date to integer -- 2024.01.02 --> 20240102
    public void Date2Integer(int year, int month, int day, int minutes) {
        String convDate = Integer.toString(year) + Integer.toString(month) + Integer.toString(day) + Integer.toString(minutes);
        int dateInt = Integer.parseInt(convDate);
        checkConsDays(dateInt);
    }


    // Method to check for consecutive days
    // Wenn Streak gebrochen, dann Achievement "Date" löschen, um Cache zu sparen
    public void checkConsDays(int date) {
        int streak = userDataManager.getAchievementProgress("Streak");
        if (userDataManager.getAchievementProgress("Date") == 0) {
            userDataManager.saveAchievementProgress("Date", date);
        } else {
            if ((date - 1) == userDataManager.getAchievementProgress("Date")) {
                streak++;
                userDataManager.saveAchievementProgress("Streak", streak);
            } else {
                userDataManager.resetAchievementProgress("Streak"); // Clear Cache
                userDataManager.resetAchievementProgress("Date"); // Clear Cache
            }
        }
    }
    public  void checkAndUpdateLastLogin(){
        Long date = userDataManager.getAchievementProgress("Date",true);
        long now = Instant.now().toEpochMilli();
        if(date == null){
            userDataManager.saveAchievementProgress("Date",Instant.now().toEpochMilli());
            return;
        }
        LocalTime old_date = Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).toLocalTime();
        LocalTime new_date = Instant.ofEpochMilli(now).atZone(ZoneId.systemDefault()).toLocalTime();
        if (Duration.between(old_date, new_date).toMinutes() >= 1) {
            System.out.println("A minute has passed.");
        } else {
            System.out.println("Less than a minute has passed.");
        }
    }


    public void totalChats (int progress) {

    }
}
