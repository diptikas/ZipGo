package com.zipgo.diptika.zipgoassignment.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.zipgo.diptika.zipgoassignment.R;

/**
 * Created by Diptika on 22/09/16.
 */

public class SplashActivity extends AppCompatActivity implements Runnable {

    private static final int SPLASH_TIME_OUT = 3000;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler = new Handler();
        mHandler.postDelayed(this, SPLASH_TIME_OUT);
    }

    @Override
    protected void onDestroy() {
        if (mHandler != null) {
            mHandler.removeCallbacks(this);
        }
        super.onDestroy();
    }

    @Override
    public void run() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String accessToken=pref.getString(getString(R.string.access_token),null);
        if(accessToken==null) {
            startActivity(new Intent(this, LoginActivity.class));
        }else {
            startActivity(new Intent(this, RoutesActivity.class));
        }
        finish();
    }


}

