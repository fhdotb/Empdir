package com.gmail.otb.fhd.mobileappcoursework.utills;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.gmail.otb.fhd.mobileappcoursework.activity.GoogleMapActivity;
import com.gmail.otb.fhd.mobileappcoursework.activity.LoginActivity;
import com.gmail.otb.fhd.mobileappcoursework.RegistrationActivity;
import com.gmail.otb.fhd.mobileappcoursework.activity.EmployeeProfileActivity;
import com.gmail.otb.fhd.mobileappcoursework.activity.MainActivity;
import com.gmail.otb.fhd.mobileappcoursework.activity.VerificationActivity;
import com.gmail.otb.fhd.mobileappcoursework.model.Employee;

import java.util.ArrayList;
import java.util.List;

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
    String jobTitle,String supervisor,String name , String building)
    {
        Intent homeIntent = new Intent(context, MainActivity.class);
        homeIntent.putExtra("userEmail",userEmail);
        homeIntent.putExtra("OfficeID",OfficeID);
        homeIntent.putExtra("photo",photo);
        homeIntent.putExtra("jobTitle",jobTitle);
        homeIntent.putExtra("supervisor",supervisor);
        homeIntent.putExtra("name",name);
        homeIntent.putExtra("building",building);

        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(homeIntent);
    }


    public static void goEmployeeProfile(Context context,String userEmail,String  OfficeID,String photo,
                                    String jobTitle,String supervisor,String name,String manager,String phone , String building, List<Employee> EmployeesList )
    {

        Intent homeIntent = new Intent(context, EmployeeProfileActivity.class);
        homeIntent.putExtra("userEmail",userEmail);
        homeIntent.putExtra("OfficeID",OfficeID);
        homeIntent.putExtra("photo",photo);
        homeIntent.putExtra("jobTitle",jobTitle);
        homeIntent.putExtra("supervisor",supervisor);
        homeIntent.putExtra("name",name);
        homeIntent.putExtra("manager",manager);
        homeIntent.putExtra("phone",phone);
        homeIntent.putExtra("building",building);
        homeIntent.putParcelableArrayListExtra("EmployeesList", (ArrayList<? extends Parcelable>) EmployeesList);


//        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
//                Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(homeIntent);


    }



    public static void goGoogleMap(Context context,String userEmail,String  OfficeID,String photo,
                                         String jobTitle,String supervisor,String name,String manager,String phone , String building)
    {

        Intent homeIntent = new Intent(context, GoogleMapActivity.class);
        homeIntent.putExtra("userEmail",userEmail);
        homeIntent.putExtra("OfficeID",OfficeID);
        homeIntent.putExtra("photo",photo);
        homeIntent.putExtra("jobTitle",jobTitle);
        homeIntent.putExtra("supervisor",supervisor);
        homeIntent.putExtra("name",name);
        homeIntent.putExtra("manager",manager);
        homeIntent.putExtra("phone",phone);
        homeIntent.putExtra("building",building);


//        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
//                Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(homeIntent);


    }



    public static void goVerificationPage(Context context,String userEmail,String  OfficeID,String photo,
                                    String jobTitle,String supervisor,String name , String building)
    {
        Intent homeIntent = new Intent(context, VerificationActivity.class);
        homeIntent.putExtra("userEmail",userEmail);
        homeIntent.putExtra("OfficeID",OfficeID);
        homeIntent.putExtra("photo",photo);
        homeIntent.putExtra("jobTitle",jobTitle);
        homeIntent.putExtra("supervisor",supervisor);
        homeIntent.putExtra("name",name);
        homeIntent.putExtra("building",building);

        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(homeIntent);
    }


}

