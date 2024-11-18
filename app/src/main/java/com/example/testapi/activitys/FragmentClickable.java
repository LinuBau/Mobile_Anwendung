package com.example.testapi.activitys;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.testapi.layoutuse.ItemViewInterface;

public abstract class FragmentClickable extends Fragment implements ItemViewInterface {


    @Override
    public void onItemClick(int position) {}
    public  void safeUserID(){}
}
