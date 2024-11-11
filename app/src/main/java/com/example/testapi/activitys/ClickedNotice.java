package com.example.testapi.activitys;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.testapi.R;
import com.example.testapi.dataobjects.Notice;

public class ClickedNotice extends FragmentClickable {
    TextView titel, inhalt,kontaktflied;
    View view;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  {
        super.onCreateView( inflater,container,savedInstanceState);
        Bundle args = getArguments();
        Notice receivedNotice  =  args.getParcelable("notice");
        view = inflater.inflate(R.layout.clicked_beitrag_full_screen, container, false);
        titel = view.findViewById(R.id.beitagName);
        inhalt = view.findViewById(R.id.beitagInhalt);
        kontaktflied = view.findViewById(R.id.kontaktdatenText);

        titel.setText(receivedNotice.getAnzeigeName());
        inhalt.setText(Html.fromHtml(receivedNotice.getBeschreibung(),Html.FROM_HTML_MODE_LEGACY));
        kontaktflied.setText(receivedNotice.getExtraData());
        titel.setVisibility(View.VISIBLE);
        inhalt.setVisibility(View.VISIBLE);
        kontaktflied.setVisibility(View.VISIBLE);

        return view;
    }
}


