package com.example.saravanamurali.farmersgen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.Crashlytics;
import com.example.saravanamurali.farmersgen.tappedactivity.HomeActivity;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);


        initialActivity();

    }

    void initialActivity() {
        Intent loginIntent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(loginIntent);
    }


}
