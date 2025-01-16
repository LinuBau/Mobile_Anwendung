package com.example.testapi.activitys;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapi.apistuff.ApiHandler;
import com.example.testapi.R;
import com.example.testapi.dataobjects.Notice;
import com.example.testapi.layoutuse.NoticeListAdaptar;
import com.example.testapi.onboarding.Onboarding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * ListViewFragment zeigt die Elemente im Homescreen (Posts) in Listenform an.
 */
public class ListViewFragment extends FragmentClickable {
    private ApiHandler apiHandler;
    public RecyclerView recyclerView;
    public static ArrayList<Integer> userTags;
    public  Button loginButton;
    private View view;
    public FloatingActionButton addButton;
    private MainActivity parent;
    public  static  ArrayList<String> tagSortingLabel;
    public Spinner fristSpinner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView( inflater,container,savedInstanceState);
        apiHandler = ((MainActivity) requireActivity()).getApiHandler();
        parent = (MainActivity) requireActivity();
        view = inflater.inflate(R.layout.list_fragment_view, container, false);

        fristSpinner = view.findViewById(R.id.tagSpinner1);


        // RecyclerView Setup
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        apiHandler.setRecyclerListView(recyclerView);
        apiHandler.setItemViewInterface(this);
        apiHandler.fetchJsonList(ApiHandler.CREATE_RECYLERVIEW);

        // Button Setup
        addButton = view.findViewById(R.id.plusButton);
        addButton.setOnClickListener(View -> {
            parent.replaceFragment(new AddResourceFragment(),true);
        });
        setUpDropdownMenu();
        loginButton = view.findViewById(R.id.start_login);
        if (!MainActivity.isLogtin) {
            loginButton.setVisibility(View.VISIBLE);
            loginButton.setOnClickListener(View -> {
                MainActivity.isLogtin = true;
                parent.replaceFragment(new LoginFragment(),true);

            });
        } else {
            loginButton.setVisibility(View.GONE);
        }
        Onboarding onboarding = new Onboarding(parent,parent.getUserDataManager());
        onboarding.showOnboarding(this);

        return view;
    }
    public  void setUpDropdownMenu(){
        ListViewFragment.userTags = new ArrayList<>();
        ListViewFragment.tagSortingLabel = new ArrayList<>();
        ListViewFragment.tagSortingLabel.add("Nichts");
        ListViewFragment.tagSortingLabel.add("MIT");
        ListViewFragment.tagSortingLabel.add("Wohnen");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(),android.R.layout.simple_spinner_item,ListViewFragment.tagSortingLabel);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fristSpinner.setAdapter(adapter);
        fristSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NoticeListAdaptar apdatar = (NoticeListAdaptar) recyclerView.getAdapter();
                if (position == 0){
                    ListViewFragment.userTags.clear();
                    apiHandler.fetchJsonList(ApiHandler.UPDATE_RECYLERVIEW);
                }else {
                    ListViewFragment.userTags.clear();
                    ListViewFragment.userTags.add(position);
                }
                if (ListViewFragment.userTags == null || ListViewFragment.userTags.isEmpty()){


                }else {
                    apdatar.updateData(new ArrayList<>(Arrays.asList(
                            MainActivity.notices.stream()
                                    .filter(n -> n.getTags().stream().anyMatch(ListViewFragment.userTags::contains))
                                    .toArray(Notice[]::new)
                    )));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // This method is called when no item is selected
            }
        });
    }


    @Override
    public void onItemClick(int position) {
        parent.replaceFragment(new ClickedNotice(),position);
    }

}


