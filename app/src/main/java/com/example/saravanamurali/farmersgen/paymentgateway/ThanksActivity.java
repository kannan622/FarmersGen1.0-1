package com.example.saravanamurali.farmersgen.paymentgateway;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.tappedactivity.HomeActivity;

public class ThanksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },1000);

        callHomeActivity();

    }

    private void callHomeActivity() {

        startActivity(new Intent(ThanksActivity.this,HomeActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homeActivity=new Intent(ThanksActivity.this,HomeActivity.class);
        startActivity(homeActivity);
        finish();
    }
}
