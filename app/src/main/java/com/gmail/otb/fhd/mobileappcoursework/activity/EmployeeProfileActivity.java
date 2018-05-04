package com.gmail.otb.fhd.mobileappcoursework.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gmail.otb.fhd.mobileappcoursework.R;
import com.gmail.otb.fhd.mobileappcoursework.model.Employee;
import com.gmail.otb.fhd.mobileappcoursework.utills.ActivityManager;
import com.gmail.otb.fhd.mobileappcoursework.utills.CircleTransform;
import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;
import java.util.List;

public class EmployeeProfileActivity extends AppCompatActivity implements View.OnClickListener {

    public String  OfficeID;
    public String photo;
    public String jobTitle;
    public String supervisor;
    public String name;
    public String manager;
    public String phone;
    public String building;
    public String userEmail;
    public  Bundle extras;
    public List<Employee> EmployeesList;
    public Context context;


    private TextView employeeName;
    private TextView employeePhone;
    private TextView employeeEmail;
    private ImageView imageView;
    private TextView employee_jobTitle;
    private TextView employee_location;
    private TextView employee_manager;
    private ListView employee_list;
    private LinearLayout layout_manager;



    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        extras = getIntent().getExtras();
        if (extras != null) {
            userEmail = extras.getString("userEmail");
            OfficeID = extras.getString("OfficeID");
            photo = extras.getString("photo");
            jobTitle = extras.getString("jobTitle");
            name = extras.getString("name");
            supervisor = extras.getString("supervisor");
            building= extras.getString("building");
            phone= extras.getString("phone");
            manager= extras.getString("manager");
            EmployeesList= extras.getParcelableArrayList("EmployeesList");
            Log.d("current user EmployeesList :", EmployeesList.toString());
        }

        if (name != null) {
            getSupportActionBar().setTitle(name);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        context = this;

        initGui();
    }



    public void initGui() {

        imageView = (ImageView)findViewById(R.id.ImageView_employee);
        employeeName = (TextView) findViewById(R.id.employee_nam);
        employeePhone = (TextView)findViewById(R.id.employee_phone);
        employeeEmail = (TextView)findViewById(R.id.employee_email);
        employee_jobTitle = (TextView)findViewById(R.id.employee_jobTitle);
        employee_location = (TextView)findViewById(R.id.location_employee);
        employee_manager = (TextView)findViewById(R.id.manager_employee);
        employee_list = (ListView) findViewById(R.id.employee_list);
        layout_manager = (LinearLayout)findViewById(R.id.layout_manager);
        layout_manager.setOnClickListener(this);
        employee_location.setOnClickListener(this);
        fillGui();

    }


    public void fillGui() {

        employeeName.setText(name);
        employeePhone.setText(phone);
        employeeEmail.setText(userEmail);
        employee_jobTitle.setText(jobTitle);
        employee_location.setText(building);
        employee_manager.setText(manager);

        if (photo != null) {
            Glide.with(this).load(photo)
                    .thumbnail(0.5f)
                    .crossFade()
                    .transform(new CircleTransform(this))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
            imageView.setColorFilter(null);
        }

        if (EmployeesList != null) {
             List<String> arrayOfemployees = new ArrayList<String>();
            for (Employee e : EmployeesList) {
                if(!e.getEmail().equals(userEmail))
                    arrayOfemployees.add(e.getFirstName());
            }


            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1,
                    arrayOfemployees.toArray(new String[arrayOfemployees.size()]));


            Log.d("adapter::", String.valueOf(adapter));
            employee_list.setAdapter(adapter);
            employee_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent,
                                        View view,
                                        int position,
                                        long id) {

                    String nameinList = adapter.getItem(position);
                    Log.d("name selected:",nameinList);

                    Employee temp = null;

                    for (Employee e : EmployeesList) {
                        if(e.getFirstName().equals(nameinList)) {
                            temp = e;
                            break;
                        }
                    }




                if(temp != null) {
                    String userEma = temp.getEmail();
                    String Office = OfficeID;
                    String photo = temp.getPhoto();
                    String jobTit;

                    if(temp.getRole()!= null)
                         jobTit =  temp.getRole().getJobTitle();
                    else
                        jobTit = "Developer";

                    String superv = supervisor;
                    String nam = temp.getFirstName() + " " + temp.getLastName();
                    String mana = manager;
                    String pho = temp.getMobileNamber();
                    String buil = building;
                    ActivityManager.goEmployeeProfile(
                            context, userEma, Office, photo, jobTit, superv, nam, mana, pho, buil, EmployeesList);
                }
                else
                {
                    Log.d("temp:","is null");
                }

                }
            });

        }//end-if
    }



    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.layout_manager:
                break;
            case R.id.location_employee:
                Alerter.create((Activity) context)
                        .setText("Google api")
                        .setIcon(R.drawable.ic_error_black_24dp)
                        .setIconColorFilter(Color.DKGRAY)
                        .show();


                ActivityManager.goGoogleMap(
                        context, userEmail,OfficeID, photo,jobTitle, supervisor, name,manager,phone,building);
                break;
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
