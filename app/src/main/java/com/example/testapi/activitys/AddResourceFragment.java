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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testapi.apistuff.ApiHandler;
import com.example.testapi.R;

public class AddResourceFragment extends FragmentClickable {
    private ApiHandler apiHandler = null;
    View view;
    private Button boldButton;
    private EditText flied1;
    private EditText flied2;
    private  boolean  isBold = false;
    private RelativeSizeSpan span;
    private SpannableString spannable;
    private int previousTextLength = 0;
    MainActivity parent ;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView( inflater,container,savedInstanceState);
        parent = (MainActivity) requireActivity();
        view = inflater.inflate(R.layout.add_eintrag_screen, container, false);
        Button button = view.findViewById(R.id.addButton);
        flied1 = view.findViewById(R.id.textField1);
        flied2 = view.findViewById(R.id.textField2);
        boldButton = view.findViewById(R.id.boldText);
        createApiHandler();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(flied1.getText().toString().isEmpty()||flied2.getText().toString().isEmpty()){
                Toast.makeText(parent.getApplicationContext(), "Please enter both the values", Toast.LENGTH_SHORT).show();
            }else {
                String titel = flied1.getText().toString();
                Spannable beschreibung = flied2.getText();
                postData(titel,beschreibung, MainActivity.userid);
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
        return  view;
    }
    private   void postData(String titel,Spannable beschreibung ,int userid){
        apiHandler.addJsonList(titel, Html.toHtml(beschreibung,Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE),userid);
    }
    private  void  createApiHandler(){
        apiHandler = ((MainActivity) requireActivity()).getApiHandler();
    }
}
