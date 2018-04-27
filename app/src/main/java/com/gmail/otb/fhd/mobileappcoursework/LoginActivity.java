package com.gmail.otb.fhd.mobileappcoursework;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gmail.otb.fhd.mobileappcoursework.NetworkLayer.API;
import com.gmail.otb.fhd.mobileappcoursework.NetworkLayer.ApiClient;
import com.gmail.otb.fhd.mobileappcoursework.adapters.ProfileAdapter;
import com.gmail.otb.fhd.mobileappcoursework.model.Employee;
import com.gmail.otb.fhd.mobileappcoursework.model.EmployeeOffice;
import com.gmail.otb.fhd.mobileappcoursework.model.JsonResponse;
import com.gmail.otb.fhd.mobileappcoursework.utills.ActivityManager;
import com.gmail.otb.fhd.mobileappcoursework.utills.HandlerInput;
import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
    private RelativeLayout loginLayout;
    private RelativeLayout progLayout;

    private String userEmail;
    private String password;
    private String  OfficeID;

    private EmployeeOffice[] offices;
    private List<EmployeeOffice> listoffices;
    private List<Employee> employeesInOneOffice =new ArrayList<Employee>();




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
        loginLayout = (RelativeLayout) findViewById(R.id.re_login);
        loginLayout.setVisibility(View.VISIBLE);
        progLayout = (RelativeLayout) findViewById(R.id.re_progres);
        progLayout.setVisibility(View.GONE);



    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_login:
                if(!HandlerInput.isEmpty(input_email) && !HandlerInput.isEmpty(input_password)) {
                    progLayout.setVisibility(View.VISIBLE);
                    loginLayout.setVisibility(View.GONE);
                    login();
                }
                break;
            case R.id.link_signup:
                ActivityManager.goSignUp(context);
                break;
        }

    }



    private void login() {
//        swipeRefreshLayout.setRefreshing(true);

        API apiService =
                ApiClient.getClient().create(API.class);

        Call<JsonResponse> call = apiService.getDetails();
        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {

                // clear the inbox
                offices = null;
                offices =  response.body().getOffices();
                listoffices = Arrays.asList(offices);
                checkUser ();






            }



            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {

                Alerter.create((Activity) context)
                        .setText("No internet connection")
                        .setIcon(R.drawable.ic_error_black_24dp)
                        .setIconColorFilter(Color.DKGRAY)
                        .show();

                progLayout.setVisibility(View.GONE);
                loginLayout.setVisibility(View.VISIBLE);

            }




        });
    }



    private void checkUser ()
    {
        boolean bool = false;
        userEmail = "";
        password = "";
        OfficeID="";

        for (EmployeeOffice e :listoffices)
        {
            OfficeID = e.getOfficeID();
            employeesInOneOffice = Arrays.asList(e.getEmployees());
            for (Employee em :employeesInOneOffice )
            {
                if (em != null) {
                    userEmail = em.getEmail();
                    password = em.getPassword();
                    if (input_email.getText().toString().trim().equals(userEmail)
                            && input_password.getText().toString().trim().equals(password)) {
                        bool = true;
                        break;
                    }
                }

            }
            if(bool)
                break;
        }


        if(bool) {
            ActivityManager.goMainScreen(context,userEmail,OfficeID );
            }
            else
            {

                Alerter.create((Activity) context)
                        .setText("Invalid username and password please try again")
                        .setIcon(R.drawable.ic_error_black_24dp)
                        .setIconColorFilter(0)
                        .show();

                progLayout.setVisibility(View.GONE);
                loginLayout.setVisibility(View.VISIBLE);
            }

    }





}
