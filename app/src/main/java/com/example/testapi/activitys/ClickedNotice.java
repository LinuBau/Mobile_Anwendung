package com.example.testapi.activitys;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.testapi.R;
import com.example.testapi.dataobjects.Notice;

public class ClickedNotice extends  ActivityClickable{
    TextView titel, inhalt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Notice receivedNotice  =  getIntent().getParcelableExtra("notice");
        setContentView(R.layout.clicked_beitrag_full_screen);
        titel = findViewById(R.id.beitagName);
        inhalt = findViewById(R.id.beitagInhalt);

        titel.setText(receivedNotice.getAnzeigeName());
        inhalt.setText(receivedNotice.getBeschreibung());
        titel.setVisibility(View.VISIBLE);
        inhalt.setVisibility(View.VISIBLE);
        Log.d("Display", "Titel: " + titel.getText().toString());
        Log.d("Display", "Inhalt: " + inhalt.getText().toString());

        //Log.e("Display", "Displaye of Objekt: Not not Happing is null " );



    }
}


