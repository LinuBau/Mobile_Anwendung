package com.example.testapi.Achievements;


import android.content.Context;
import android.widget.Toast;

import com.example.testapi.dataobjects.UserDataManager;

public class Achievement {

    private UserDataManager userDataManager;
    private Context context;

    private Achievement(Context context) {  // Konstruktor bleibt private
        this.context = context;
    }
    public static Achievement createInstance(Context context) {
        return new Achievement(context);
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


    public void totalChats (int progress) {

    }
}
