package com.example.saravanamurali.farmersgen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

//import com.crashlytics.android.Crashlytics;
import com.example.saravanamurali.farmersgen.fragment.Add_Address_Activity;
import com.example.saravanamurali.farmersgen.fragment.ExistingAddressActivity;
import com.example.saravanamurali.farmersgen.fragment.ForgetPassword;
import com.example.saravanamurali.farmersgen.fragment.NewPassAndConfirmPass;
import com.example.saravanamurali.farmersgen.signin.LoginActivity;
import com.example.saravanamurali.farmersgen.signup.OTPActivity;
import com.example.saravanamurali.farmersgen.signup.SignupActivity;
import com.example.saravanamurali.farmersgen.tappedactivity.HomeActivity;
//import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);


        initialActivity();

    }

    void initialActivity() {
        Intent loginIntent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(loginIntent);
    }


}
