package com.example.saravanamurali.farmersgen.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.models.JSONOTPResponseFromOTPActivity;
import com.example.saravanamurali.farmersgen.models.OTPandMobileNoDTO;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToSendMobileNoAndOTP;
import com.example.saravanamurali.farmersgen.signin.LoginActivityForViewCart;
import com.goodiebag.pinview.Pinview;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPActivityForViewCart2 extends AppCompatActivity {

    public Pinview pinviewRegistration_AtViewCart;
    private Button otpButtonRegistration_AtViewCart;

    String entered_OTP_AtViewCart;


    String mobileNumberToSendOTP_AtViewCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpfor_view_cart2);

        Intent getMobileNumber=getIntent();
        mobileNumberToSendOTP_AtViewCart =getMobileNumber.getStringExtra("MOBILENOTOSEND_OTPATVIEWCART2");

        pinviewRegistration_AtViewCart = (Pinview) findViewById(R.id.otpForRegistrationAtViewCart2);
        otpButtonRegistration_AtViewCart = (Button) findViewById(R.id.otpRegistrationSubmitAtViewCart2);

        pinviewRegistration_AtViewCart.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override

            public void onDataEntered(final Pinview pinview, boolean b) {
                entered_OTP_AtViewCart = pinview.getValue();



            }
        });

        otpButtonRegistration_AtViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println(entered_OTP_AtViewCart);


                if(!TextUtils.isEmpty(entered_OTP_AtViewCart)) {
                    sendMobileNoandOTPForViewCart();
                }

            }
        });




    }

    private void sendMobileNoandOTPForViewCart() {

        ApiInterface api=APIClientToSendMobileNoAndOTP.getApiInterfaceToSendMobileNoAndOTP();


        OTPandMobileNoDTO otPandMobileNoDTO=new OTPandMobileNoDTO(mobileNumberToSendOTP_AtViewCart,entered_OTP_AtViewCart);


        Call<JSONOTPResponseFromOTPActivity> call=api.sendMobileNoandOTPFromOTPActivity(otPandMobileNoDTO);

        call.enqueue(new Callback<JSONOTPResponseFromOTPActivity>() {
            @Override
            public void onResponse(Call<JSONOTPResponseFromOTPActivity> call, Response<JSONOTPResponseFromOTPActivity> response) {
                if(response.isSuccessful()){
                    Intent intent=new Intent(OTPActivityForViewCart2.this,LoginActivityForViewCart.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<JSONOTPResponseFromOTPActivity> call, Throwable t) {

            }
        });

    }
}
