package com.gmail.otb.fhd.mobileappcoursework.utills;

import android.content.Context;
import android.content.Intent;

import com.gmail.otb.fhd.mobileappcoursework.LoginActivity;
import com.gmail.otb.fhd.mobileappcoursework.RegistrationActivity;
import com.gmail.otb.fhd.mobileappcoursework.activity.MainActivity;

/**
 * Created by Fahad on 4/10/18.
 */

public class ActivityManager {


    public static void goLogin(Context context) {
        Intent homeIntent = new Intent(context, LoginActivity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(homeIntent);
    }

    public static void goSignUp(Context context) {
        Intent homeIntent = new Intent(context, RegistrationActivity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(homeIntent);
    }


    public static void goMainScreen(Context context,String userEmail,String  OfficeID,String photo,
    String jobTitle,String supervisor,String name)
    {
        Intent homeIntent = new Intent(context, MainActivity.class);
        homeIntent.putExtra("userEmail",userEmail);
        homeIntent.putExtra("OfficeID",OfficeID);
        homeIntent.putExtra("photo",photo);
        homeIntent.putExtra("jobTitle",jobTitle);
        homeIntent.putExtra("supervisor",supervisor);
        homeIntent.putExtra("name",name);

        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(homeIntent);
    }

}

