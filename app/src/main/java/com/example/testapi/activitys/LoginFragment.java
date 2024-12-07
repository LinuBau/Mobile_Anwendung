package com.example.testapi.activitys;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.testapi.R;
import com.example.testapi.dataobjects.Notice;

public class LoginFragment extends  FragmentClickable{
    private   View view;
    private  MainActivity parent;
    private boolean isClickedNotice;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView( inflater,container,savedInstanceState);
        view = inflater.inflate(R.layout.login_scren,container,false);
        parent = (MainActivity) requireActivity();
        isClickedNotice = false;
        Bundle args = getArguments();
        isClickedNotice = args != null;
        EditText usserNameTextFlied = view.findViewById(R.id.etUsername);
        EditText passwordTextFlied = view.findViewById(R.id.etPassword);
        Button loginButton = view.findViewById(R.id.btnLogin);
        loginButton.setOnClickListener(View -> {
            MainActivity.isLogtin = true;
            if (isClickedNotice){
                Notice n = args.getParcelable("notice");
                parent.replaceFragment(new ClickedNotice(),MainActivity.notices.indexOf(n),true);
            }else {
                parent.replaceFragment(new ListViewFragment());
            }
        });
        return  view;
    }

}
