package com.gmail.otb.fhd.mobileappcoursework;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.gmail.otb.fhd.mobileappcoursework.utills.ActivityManager;


/**
 * Created by fahadalms3odi on 4/10/18.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText input_email;
    private EditText input_password;
    private View loginButton;
    private View signup;
    private Context context = this;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setContentView(R.layout.login_activity);
        initGui();
    }



    @SuppressLint("CutPasteId")
    public void initGui() {
        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);
        loginButton = findViewById(R.id.btn_login);
        loginButton.setOnClickListener(this);
        signup = findViewById(R.id.link_signup);
        signup.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_login:
                ActivityManager.goMainScreen(context);
                break;
            case R.id.link_signup:
                ActivityManager.goSignUp(context);
                break;
        }

    }

}
