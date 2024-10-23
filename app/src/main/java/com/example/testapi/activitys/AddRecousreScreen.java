package com.example.testapi.activitys;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.testapi.apistuff.ApiHandler;
import com.example.testapi.apistuff.FlaskApiService;
import com.example.testapi.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddRecousreScreen extends ActivityClickable {
    private ApiHandler apiHandler = null;
    private Button boldButton;
    private EditText flied1;
    private EditText flied2;
    private  boolean  isBold = false;
    private RelativeSizeSpan span;
    private SpannableString spannable;
    private int previousTextLength = 0;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_eintrag_screen);
        Button button = findViewById(R.id.addButton);
        flied1 = findViewById(R.id.textField1);
        flied2 = findViewById(R.id.textField2);
        boldButton = findViewById(R.id.boldText);
        createApiHandler();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(flied1.getText().toString().isEmpty()||flied2.getText().toString().isEmpty()){
                Toast.makeText(AddRecousreScreen.this, "Please enter both the values", Toast.LENGTH_SHORT).show();
            }else {
                String titel = flied1.getText().toString();
                String beschreibung = flied2.getText().toString();
                postData(titel,beschreibung,MainActivity.userid);
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
    }
    private   void postData(String titel,String beschreibung ,int userid){
        apiHandler.addJsonList(titel,beschreibung,userid);
    }
    private  void  createApiHandler(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://10.0.2.2:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FlaskApiService apiService = retrofit.create(FlaskApiService.class);
        apiHandler = new ApiHandler(this,apiService);
    }
}
