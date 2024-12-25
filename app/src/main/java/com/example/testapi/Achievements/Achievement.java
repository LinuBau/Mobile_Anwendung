package com.example.testapi.Achievements;


import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

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
        }
    }


    public void totalChats (int progress) {

    }
}
