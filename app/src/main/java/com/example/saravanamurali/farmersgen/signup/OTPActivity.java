package com.example.saravanamurali.farmersgen.signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.fragment.NewPassAndConfirmPass;
import com.example.saravanamurali.farmersgen.models.JSONOTPResponseFromOTPActivity;
import com.example.saravanamurali.farmersgen.models.OTPandMobileNoDTO;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToSendMobileNoAndOTP;
import com.goodiebag.pinview.Pinview;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToSendOTP;

public class OTPActivity extends AppCompatActivity {

    public Pinview pinview;
    private Button otpButton;
    private TextView errorText;
    String entered_OTP;
    String correctOTP;

    int optCout;

    String mobileNumberToSendOTP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        Intent intent = getIntent();

        //Validated Mobile number to send OTP
        mobileNumberToSendOTP = intent.getStringExtra("MOBILENUM_TOCONFIR_PASSWORD");

        pinview = (Pinview) findViewById(R.id.otpPinView);
        otpButton = (Button) findViewById(R.id.otpSubmit);
        errorText = (TextView) findViewById(R.id.otpError);

        pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override

            public void onDataEntered(final Pinview pinview, boolean b) {
                entered_OTP = pinview.getValue();

                optCout = entered_OTP.length();


            }
        });

        otpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(entered_OTP)) {
                    sendMobileNoandOTP();
                } else {
                    Toast.makeText(OTPActivity.this,"Please enter OTP",Toast.LENGTH_SHORT).show();
                }


                // int otpCount=Integer.parseInt(entered_OTP);

                /*if(entered_OTP.equals(null)){

                    Toast.makeText(OTPActivity.this,"Please enter OTP",Toast.LENGTH_SHORT).show();

                }

               else if(optCout<6){
                    Toast.makeText(OTPActivity.this,"OTP Length is Short",Toast.LENGTH_SHORT).show();
                }*/




            }
        });


    }

    private void sendMobileNoandOTP() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(OTPActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api = APIClientToSendMobileNoAndOTP.getApiInterfaceToSendMobileNoAndOTP();


        OTPandMobileNoDTO otPandMobileNoDTO = new OTPandMobileNoDTO(mobileNumberToSendOTP, entered_OTP);


        Call<JSONOTPResponseFromOTPActivity> call = api.sendMobileNoandOTPFromOTPActivity(otPandMobileNoDTO);

        call.enqueue(new Callback<JSONOTPResponseFromOTPActivity>() {
            @Override
            public void onResponse(Call<JSONOTPResponseFromOTPActivity> call, Response<JSONOTPResponseFromOTPActivity> response) {

                /*if (response.isSuccessful()) {

                    JSONOTPResponseFromOTPActivity jsonotpResponseFromOTPActivity = response.body();
*/
                    if (response.isSuccessful()) {

                        if(csprogress.isShowing()){
                            csprogress.dismiss();
                        }

                        Intent intent = new Intent(OTPActivity.this, NewPassAndConfirmPass.class);
                        intent.putExtra("MOBILENO_FROM_OTP", mobileNumberToSendOTP);
                        startActivity(intent);
                    } else {
                       // if (jsonotpResponseFromOTPActivity.getStatus() == 500) {

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
