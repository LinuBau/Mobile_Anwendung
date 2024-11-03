package com.example.testapi.activitys;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapi.apistuff.ApiHandler;
import com.example.testapi.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListViewFragment extends FragmentClickable {
    ApiHandler apiHandler;
    View view;
    MainActivity parent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView( inflater,container,savedInstanceState);
        apiHandler = ((MainActivity) requireActivity()).getApiHandler();
        parent = (MainActivity) requireActivity();
        view = inflater.inflate(R.layout.list_fragment_view, container, false);

        // RecyclerView Setup
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        apiHandler.setRecyclerListView(recyclerView);
        apiHandler.setItemViewInterface(this);
        apiHandler.fetchJsonList(ApiHandler.CREATE_RECYLERVIEW);

        // Button Setup
        FloatingActionButton button = view.findViewById(R.id.plusButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent.replaceFragment(new AddResourceFragment(), true);
            }
        });

        return view;
    }


    @Override
    public void onItemClick(int position) {
        parent.replaceFragment(new ClickedNotice(),position);
    }

}


