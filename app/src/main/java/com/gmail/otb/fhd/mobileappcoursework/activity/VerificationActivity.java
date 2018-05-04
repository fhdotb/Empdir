package com.gmail.otb.fhd.mobileappcoursework.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gmail.otb.fhd.mobileappcoursework.R;
import com.gmail.otb.fhd.mobileappcoursework.utills.ActivityManager;
import com.gmail.otb.fhd.mobileappcoursework.utills.HandlerInput;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;




import java.util.Random;
import java.util.concurrent.TimeUnit;


public class VerificationActivity extends AppCompatActivity implements View.OnClickListener {


    private String userEmail;
    private String OfficeID;
    private String photo;
    private String jobTitle;
    private String supervisor;
    private String name;
    private String building;
    private  Bundle extras;

    private EditText textUserInput;
    private TextView user_Email;
    private RelativeLayout layout_send,re_progres;
    private Button send;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;



    private String code;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);


        extras = getIntent().getExtras();
        if (extras != null) {
            userEmail = extras.getString("userEmail");
            OfficeID = extras.getString("OfficeID");
            photo = extras.getString("photo");
            jobTitle = extras.getString("jobTitle");
            name = extras.getString("name");
            supervisor = extras.getString("supervisor");
            building = extras.getString("building");
            Log.d("current user building :", building);
            }


        initGui();



    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser ==null)
        {
            ActivityManager.goLogin(this);
        }

    }


    public void initGui() {

        textUserInput = (EditText)findViewById(R.id.EditTextUserInput);
        user_Email = (TextView)findViewById(R.id.user_Email);
        layout_send = (RelativeLayout)findViewById(R.id.layout_send);
        re_progres = (RelativeLayout)findViewById(R.id.re_progres);
        send = (Button)findViewById(R.id.btn_send);
        send.setOnClickListener(this);


        fillGui();

    }

    void fillGui()
    {
        user_Email.setText(userEmail);
        layout_send.setVisibility(View.VISIBLE);
        re_progres.setVisibility(View.GONE);


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "07445051917",        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks



    }



    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_send:

                if(!HandlerInput.isEmpty(textUserInput) && code.equals(textUserInput.getText().toString()))
                {

                    layout_send.setVisibility(View.GONE);
                    re_progres.setVisibility(View.VISIBLE);

                    mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                        }

                        @Override
                        public void onVerificationFailed(FirebaseException e) {

                        }
                    };



                }
                break;
        }

    }



    public String codeNumber() {
        Random r = new Random( System.currentTimeMillis() );
        return String.valueOf(((1 + r.nextInt(2)) * 10000 + r.nextInt(10000)));
    }
}
