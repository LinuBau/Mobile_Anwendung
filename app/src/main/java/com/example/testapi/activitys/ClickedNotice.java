package com.example.testapi.activitys;

import android.os.Bundle;
import android.text.Html;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.example.testapi.R;
import com.example.testapi.dataobjects.Notice;

public class ClickedNotice extends FragmentClickable {
    MainActivity parent;
    TextView titel, inhalt,kontaktflied;
    Button messageButton;
    View view;
    Notice receivedNotice;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  {
        super.onCreateView( inflater,container,savedInstanceState);
        parent = (MainActivity) requireActivity();
        Bundle args = getArguments();
        assert args != null;
        receivedNotice  =  args.getParcelable("notice");
        view = inflater.inflate(R.layout.clicked_beitrag_full_screen, container, false);
        titel = view.findViewById(R.id.beitagName);
        messageButton = view.findViewById(R.id.messag_Button);
        inhalt = view.findViewById(R.id.beitagInhalt);
        kontaktflied = view.findViewById(R.id.kontaktdatenText);

        titel.setText(receivedNotice.getAnzeigeName());
        inhalt.setText(Html.fromHtml(receivedNotice.getBeschreibung(),Html.FROM_HTML_MODE_LEGACY));
        kontaktflied.setText(receivedNotice.getExtraData());
        titel.setVisibility(View.VISIBLE);
        inhalt.setVisibility(View.VISIBLE);
        kontaktflied.setVisibility(View.VISIBLE);

        messageButton.setOnClickListener(view -> {
            parent.setNavigationBarTab(R.id.navigation_message);
            parent.getApiHandler().createChatFragment(receivedNotice.getErstellerId());
        });

        return view;
    }
}


