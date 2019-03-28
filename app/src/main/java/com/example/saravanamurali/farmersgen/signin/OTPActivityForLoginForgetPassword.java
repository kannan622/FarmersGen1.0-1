package com.example.saravanamurali.farmersgen.signin;

import android.content.Intent;
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
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToSendMobileNoAndOTPForLoginForgetPassword;
import com.example.saravanamurali.farmersgen.signup.OTPActivity;
import com.goodiebag.pinview.Pinview;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPActivityForLoginForgetPassword extends AppCompatActivity {

    //Mobile Number from login Forget Password
    String mobileNumberForLoginForgetPassword;

    public Pinview pinview_AtLoginForgetPassword;
    private Button otpButton_AtLoginForgetPassword;
    private TextView errorText_AtLoginForgetPassword;
    String entered_OTP_AtLoginForgetPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpfor_login_forget_password);

        Intent intent = getIntent();

         mobileNumberForLoginForgetPassword=intent.getStringExtra("MOBILENO_FOR_LOGIN_FORGET_PASSWORD");

        pinview_AtLoginForgetPassword = (Pinview) findViewById(R.id.otpPinViewForLoginForgetPassword);
        otpButton_AtLoginForgetPassword = (Button) findViewById(R.id.otpSubmitForLoginForgetPassword);
        errorText_AtLoginForgetPassword = (TextView) findViewById(R.id.otpErrorForLoginForgetPassword);


        pinview_AtLoginForgetPassword.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean b) {
                entered_OTP_AtLoginForgetPassword =pinview.getValue();
            }
        });

        otpButton_AtLoginForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(entered_OTP_AtLoginForgetPassword)) {
                    sendMobileNoandOTPForLoginForgetPassword();
                } else {
                    Toast.makeText(OTPActivityForLoginForgetPassword.this,"Please enter OTP",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void sendMobileNoandOTPForLoginForgetPassword() {
        ApiInterface api=APIClientToSendMobileNoAndOTPForLoginForgetPassword.getApiInterfaceToSendMobileNoAndOTPForLoginForgetPassword();

        OTPandMobileNoDTO otpAndMobileNoDTO = new OTPandMobileNoDTO(mobileNumberForLoginForgetPassword, entered_OTP_AtLoginForgetPassword);

        Call<JSONOTPResponseFromOTPActivity> call=api.sendMobileNoandOTPFromLoginForgetPasswordActivity(otpAndMobileNoDTO);

        call.enqueue(new Callback<JSONOTPResponseFromOTPActivity>() {
            @Override
            public void onResponse(Call<JSONOTPResponseFromOTPActivity> call, Response<JSONOTPResponseFromOTPActivity> response) {

               /* if(response.isSuccessful()){

                    JSONOTPResponseFromOTPActivity jsonotpResponseFromOTPActivity = response.body();
*/
                    if(response.isSuccessful())  {
                        //(jsonotpResponseFromOTPActivity.getStatus()=="200")
                        Intent newPassintent = new Intent(OTPActivityForLoginForgetPassword.this, NewPassAndConfirmPassForLoginForgetPassword.class);
                        newPassintent.putExtra("MOBILENO_FOR_NEW_PASS_AND_CONFIRM_PASSWORD", mobileNumberForLoginForgetPassword);
                        startActivity(newPassintent);
                    }
                    else  {
                        //if (jsonotpResponseFromOTPActivity.getStatus()=="500")

                        //Resend OTP

                        //sendMobileNoandOTPForLoginForgetPassword();

                        Toast.makeText(OTPActivityForLoginForgetPassword.this,"500 Eror",Toast.LENGTH_LONG).show();

                    }
                }


            @Override
            public void onFailure(Call<JSONOTPResponseFromOTPActivity> call, Throwable t) {

            }
        });


    }
}
