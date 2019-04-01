package com.example.saravanamurali.farmersgen.signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView mobileShow;

    private TextView timeShow;
    private TextView resendClick;

    String entered_OTP_AtViewCart;

    long ms;


    String mobileNumberToSendOTP_AtViewCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpfor_view_cart2);

        Intent getMobileNumber=getIntent();
        mobileNumberToSendOTP_AtViewCart =getMobileNumber.getStringExtra("MOBILENOTOSEND_OTPATVIEWCART2");

        pinviewRegistration_AtViewCart = (Pinview) findViewById(R.id.otpForRegistrationAtViewCart2);
        otpButtonRegistration_AtViewCart = (Button) findViewById(R.id.otpRegistrationSubmitAtViewCart2);

        mobileShow=(TextView)findViewById(R.id.otp_MobileNumber);

        mobileShow.setText(mobileNumberToSendOTP_AtViewCart);

        timeShow=(TextView)findViewById(R.id.timeShower);
        resendClick=(TextView)findViewById(R.id.reSend);

        //Getting OTP
        gettingOTP();

        callCountDownTimer();

        //Button is clicked
        otpButtonRegistration_AtViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!TextUtils.isEmpty(entered_OTP_AtViewCart)) {
                    sendMobileNoandOTPForViewCart();
                }
                else{

                    Toast.makeText(OTPActivityForViewCart2.this,"Please enter valid OTP",Toast.LENGTH_LONG).show();
                }

            }
        });




    }

    private void gettingOTP() {
        pinviewRegistration_AtViewCart.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override

            public void onDataEntered(final Pinview pinview, boolean b) {
                entered_OTP_AtViewCart = pinview.getValue();
            }
        });
    }

    private void callCountDownTimer() {
        CountDownTimer countDownTimer=new CountDownTimer(120*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                ms=millisUntilFinished;

                timeShow.setText(""+millisUntilFinished/1000);

            }

            @Override
            public void onFinish() {
                otpButtonRegistration_AtViewCart.setVisibility(View.INVISIBLE);
                resendClick.setVisibility(View.VISIBLE);
                resendClick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        otpButtonRegistration_AtViewCart.setVisibility(View.VISIBLE);
                        resendClick.setVisibility(View.INVISIBLE);
                        callCountDownTimer();
                        gettingOTP();

                    }
                });


            }
        }.start();

    }

    private void sendMobileNoandOTPForViewCart() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(OTPActivityForViewCart2.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api=APIClientToSendMobileNoAndOTP.getApiInterfaceToSendMobileNoAndOTP();


        OTPandMobileNoDTO otPandMobileNoDTO=new OTPandMobileNoDTO(mobileNumberToSendOTP_AtViewCart,entered_OTP_AtViewCart);


        Call<JSONOTPResponseFromOTPActivity> call=api.sendMobileNoandOTPFromOTPActivity(otPandMobileNoDTO);

        call.enqueue(new Callback<JSONOTPResponseFromOTPActivity>() {
            @Override
            public void onResponse(Call<JSONOTPResponseFromOTPActivity> call, Response<JSONOTPResponseFromOTPActivity> response) {
                if(response.isSuccessful()){
                    if(csprogress.isShowing()){
                        csprogress.dismiss();
                    }

                    Intent intent=new Intent(OTPActivityForViewCart2.this,LoginActivityForViewCart.class);
                    startActivity(intent);
                    finish();
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
