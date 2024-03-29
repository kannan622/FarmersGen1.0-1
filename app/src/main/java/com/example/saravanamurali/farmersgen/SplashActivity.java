package com.example.saravanamurali.farmersgen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.example.saravanamurali.farmersgen.signin.LoginActivity;
import com.example.saravanamurali.farmersgen.tappedactivity.HomeActivity;

import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity {

    private String NO_CURRENT_USER = "NO_CURRENT_USER";

    String deviceID = null;
    String currentUserAtSplashScreen;


    private static SplashActivity instance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Fabric.with(this, new Crashlytics());
        instance = this;

        new SplashDownCountDown(3000, 1000).start();


    }

    private class SplashDownCountDown extends CountDownTimer {

        SplashDownCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long milliSecond) {

        }

        @Override
        public void onFinish() {

            deviceID = Settings.Secure.getString(SplashActivity.this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            SharedPreferences getCurrentUser = getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
            currentUserAtSplashScreen = getCurrentUser.getString("CURRENTUSER", "NO_CURRENT_USER");
            final Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);

            //Logged in user(User is there)
            if (currentUserAtSplashScreen != NO_CURRENT_USER) {
                final Intent intentHome = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intentHome);
                finish();
                //Logged Out and First Time user
            } else if (currentUserAtSplashScreen == NO_CURRENT_USER) {
                final Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }

            /*if(deviceID!=null){
                final Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);

            }*/

        }
    }
}

