package com.example.testapi.activitys;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.testapi.apistuff.ApiHandler;
import com.example.testapi.R;
import com.example.testapi.dataobjects.UserDataManager;

import java.util.ArrayList;

public class AddResourceFragment extends FragmentClickable {
    private ApiHandler apiHandler = null;
    View view;
    private Button boldButton;
    private EditText flied1;
    private EditText flied2;
    private  EditText contactFlied;
    private  boolean  isBold = false;
    private RelativeSizeSpan span;
    private SpannableString spannable;
    private int previousTextLength = 0;
    MainActivity parent ;
    Spinner spinner1, spinner2;
    ArrayAdapter<String> adapter1, adapter2;
    ArrayList<String> items1, items2;
    private UserDataManager userDataManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView( inflater,container,savedInstanceState);
        parent = (MainActivity) requireActivity();
        view = inflater.inflate(R.layout.add_eintrag_screen, container, false);
        Button button = view.findViewById(R.id.addButton);
        flied1 = view.findViewById(R.id.textField1);
        flied2 = view.findViewById(R.id.textField2);
        contactFlied = view.findViewById(R.id.contactField);
        boldButton = view.findViewById(R.id.boldText);
        createApiHandler();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(flied1.getText().toString().isEmpty()||flied2.getText().toString().isEmpty()){
                Toast.makeText(parent.getApplicationContext(), "Please enter both the values", Toast.LENGTH_SHORT).show();
            }else {
                String contactData = contactFlied.getText().toString();
                if (contactData.isEmpty()){
                    contactData = " ";
                }
                String titel = flied1.getText().toString();
                Spannable beschreibung = flied2.getText();
                postData(titel,beschreibung, contactData,MainActivity.userid);
                apiHandler.fetchJsonList(ApiHandler.UPDATE_RECYLERVIEW);
                parent.replaceFragment(new ListViewFragment());

            }
            }
        });
        flied2.addTextChangedListener(new TextWatcher() {
            boolean isUpdatingText = false;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                previousTextLength = charSequence.length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Hier muss nichts geändert werden
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isBold && !isUpdatingText) {
                    isUpdatingText = true;

                    int beginIndex = previousTextLength;
                    int endIndex = editable.length();

                    // Aktualisiert den Text im EditText mit fett gedrucktem neuen Text
                    if (beginIndex < endIndex) {
                        SpannableString spannableString = new SpannableString(flied2.getText());
                        spannableString.setSpan(new StyleSpan(Typeface.BOLD), beginIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        flied2.setText(spannableString);
                        flied2.setSelection(endIndex);
                    }
                    isUpdatingText = false;
                }

            }

                    });
        boldButton.setOnClickListener(view -> {
            int start = flied2.getSelectionStart();
            int end = flied2.getSelectionEnd();

            if (start > end) {

                int temp = start;
                start = end;
                end = temp;
            }
            if (start != end) {
                SpannableString spannableString = new SpannableString(flied2.getText());
                spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                flied2.setText(spannableString);
                flied2.setSelection(start, end);
                boldButton.setTextColor(Color.WHITE);
            }else {
                isBold = !isBold;
                if (isBold){
                    boldButton.setTextColor(Color.BLACK);
                }else {
                    boldButton.setTextColor(Color.WHITE);
                }

            }
        });
        setUpSpinners();
        return  view;
    }


    private   void postData(String titel,Spannable beschreibung ,String extraData,int userid){
        ArrayList<Integer> tagList = new ArrayList<>();
        int spinnerValue1 = ListViewFragment.tagSortingLabel.indexOf((String) spinner1.getSelectedItem());
        if (spinnerValue1 != 0){
            tagList.add(spinnerValue1);
        }
        int spinnerValue2 = ListViewFragment.tagSortingLabel.indexOf((String) spinner2.getSelectedItem());
        if (spinnerValue2 !=0){
            tagList.add(spinnerValue2);
        }
        if(tagList.isEmpty()){
            tagList.add(-1);
        }
        apiHandler.addJsonList(titel, Html.toHtml(beschreibung,Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE),extraData,userid,tagList);

        parent.trackAchievements("TotalPosts");
    }
    private  void  createApiHandler(){
        apiHandler = ((MainActivity) requireActivity()).getApiHandler();
    }
    private  void setUpSpinners(){
        spinner1 = view.findViewById(R.id.spinner1);
        spinner2 = view.findViewById(R.id.spinner2);
        items1 = new ArrayList<>(ListViewFragment.tagSortingLabel);
        items2 = new ArrayList<>(ListViewFragment.tagSortingLabel);

        adapter1 = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, items1);
        adapter2 = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, items2);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);

// Listener für den ersten Spinner
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0){
                    String selectedItem = items1.get(position);
                    // Entferne  dasausgewählte Element aus dem zweiten Spinner
                    items2.clear();
                    items2.addAll(ListViewFragment.tagSortingLabel);
                    items2.remove(selectedItem);
                    adapter2.notifyDataSetChanged();
                }else {
                    items2.clear();
                    items2.addAll(ListViewFragment.tagSortingLabel);
                    adapter1.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

// Listener für den zweiten Spinner
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position !=0){
                    String selectedItem = items2.get(position);
                    // Entferne das ausgewählte Element aus dem ersten Spinner
                    items1.clear();
                    items1.addAll(ListViewFragment.tagSortingLabel);
                    items1.remove(selectedItem);
                    adapter1.notifyDataSetChanged();
                }else {
                    items1.clear();
                    items1.addAll(ListViewFragment.tagSortingLabel);
                    adapter1.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
