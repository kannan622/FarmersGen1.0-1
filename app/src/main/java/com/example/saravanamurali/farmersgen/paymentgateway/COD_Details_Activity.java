package com.example.saravanamurali.farmersgen.paymentgateway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.address.ExistingAddressActivity;

public class COD_Details_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cod__details_);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent getDeliveryAddressActivity=new Intent(COD_Details_Activity.this,ExistingAddressActivity.class);
        startActivity(getDeliveryAddressActivity);
    }
}
