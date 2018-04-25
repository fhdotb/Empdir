package com.gmail.otb.fhd.mobileappcoursework;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.gmail.otb.fhd.mobileappcoursework.utills.ActivityManager;

/**
 * Created by fahadalms3odi on 4/14/18.
 */

public class SplashActivity extends AppCompatActivity {
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    Context context = this;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                ActivityManager.goLogin(context);
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
