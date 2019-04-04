package com.example.saravanamurali.farmersgen.signup;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import com.example.saravanamurali.farmersgen.signin.LoginActivity;
import com.example.saravanamurali.farmersgen.util.Network_config;
import com.goodiebag.pinview.Pinview;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPActForSuccRegistrationAtViewCart extends AppCompatActivity {
    public Pinview pinviewRegistration;
    String entered_OTP;
    String mobileNumberToSendOTP;
    private Button otpButtonRegistration;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpact_for_succ_registration);

        dialog = new Dialog(getApplicationContext());
        Intent getMobileNumber = getIntent();
        mobileNumberToSendOTP = getMobileNumber.getStringExtra("MOBILENOTOLOGIN");

        pinviewRegistration = (Pinview) findViewById(R.id.otpForRegistrationAtViewCart);
        otpButtonRegistration = (Button) findViewById(R.id.otpRegistrationSubmitAtViewCart);

        pinviewRegistration.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override

            public void onDataEntered(final Pinview pinview, boolean b) {
                entered_OTP = pinview.getValue();


            }
        });


        otpButtonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Network_config.is_Network_Connected_flag(getApplicationContext())) {
                    System.out.println(entered_OTP);


                    if (!TextUtils.isEmpty(entered_OTP)) {
                        sendMobileNoandOTP();
                    }
                } else {
                    Network_config.customAlert(dialog, getApplicationContext(), getResources().getString(R.string.app_name),
                            getResources().getString(R.string.connection_message));
                }


            }
        });


    }

    private void sendMobileNoandOTP() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(OTPActForSuccRegistrationAtViewCart.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api = APIClientToSendMobileNoAndOTP.getApiInterfaceToSendMobileNoAndOTP();


        OTPandMobileNoDTO otPandMobileNoDTO = new OTPandMobileNoDTO(mobileNumberToSendOTP, entered_OTP);


        Call<JSONOTPResponseFromOTPActivity> call = api.sendMobileNoandOTPFromOTPActivity(otPandMobileNoDTO);

        call.enqueue(new Callback<JSONOTPResponseFromOTPActivity>() {
            @Override
            public void onResponse(Call<JSONOTPResponseFromOTPActivity> call, Response<JSONOTPResponseFromOTPActivity> response) {

                // if(response.isSuccessful()){

                // JSONOTPResponseFromOTPActivity jsonotpResponseFromOTPActivity=response.body();

                if (response.isSuccessful()) {

                    if(csprogress.isShowing()){
                        csprogress.dismiss();
                    }

                    Intent intent = new Intent(OTPActForSuccRegistrationAtViewCart.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    //if(jsonotpResponseFromOTPActivity.getStatus()==500)

                    //Resend OTP

                }
            }


            @Override
            public void onFailure(Call<JSONOTPResponseFromOTPActivity> call, Throwable t) {

                if(csprogress.isShowing()){
                    csprogress.dismiss();
                }

            }
        });


    }
}
