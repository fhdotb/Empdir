package com.gmail.otb.fhd.mobileappcoursework;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toolbar;

import com.gmail.otb.fhd.mobileappcoursework.utills.ActivityManager;

/**
 * Created by fahadalms3odi on 4/13/18.
 */

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText input_name;
    private EditText input_address;
    private EditText input_email;
    private EditText input_mobile;
    private EditText input_password;
    private EditText input_reEnterPassword;

    private View btn_signup;
    private View link_login;
    private Context context = this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }



        initGui();
    }



    public void initGui() {

        input_name = (EditText) findViewById(R.id.input_name);
        input_address = (EditText) findViewById(R.id.input_address);
        input_email = (EditText) findViewById(R.id.input_email);
        input_mobile = (EditText) findViewById(R.id.input_mobile);
        input_password = (EditText) findViewById(R.id.input_password);
        input_reEnterPassword = (EditText) findViewById(R.id.input_reEnterPassword);

        btn_signup = findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(this);

        link_login = findViewById(R.id.link_login);
        link_login.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_signup:
                break;
            case R.id.link_login:
                ActivityManager.goLogin(context);
                break;
        }

    }
}
