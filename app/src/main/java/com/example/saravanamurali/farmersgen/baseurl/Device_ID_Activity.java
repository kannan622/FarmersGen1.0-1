package com.example.saravanamurali.farmersgen.baseurl;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.saravanamurali.farmersgen.DummyActivity;
import com.example.saravanamurali.farmersgen.fragment.Product_List_Activity;

public class Device_ID_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadDeviceid();
    }

    private void loadDeviceid() {

        String android_id = Settings.Secure.getString(Device_ID_Activity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Intent intent=new Intent(Device_ID_Activity.this,Product_List_Activity.class);
        intent.putExtra("DEVICE_ID",android_id);


    }
}
