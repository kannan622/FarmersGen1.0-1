package com.example.saravanamurali.farmersgen.signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.models.JSONOTPResponseFromOTPActivity;
import com.example.saravanamurali.farmersgen.models.JSONResponseToSendOTPFromForgetPasswordDTO;
import com.example.saravanamurali.farmersgen.models.OTPSendToMobileDTOFrom_FP;
import com.example.saravanamurali.farmersgen.models.OTPandMobileNoDTO;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToSendMobileNoAndOTP;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToSendOTPToMFrom_FP;
import com.goodiebag.pinview.Pinview;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExistingUser_ForgetPassword_OTP_AtViewCartActivity extends AppCompatActivity {

    public Pinview pinview_OTP;
    String entered_OTP_AtViewCart;
    String MobileNumberFrom_Existing_User_Forget_Password;
    private Button otpButton_OTP;
    private TextView errorText_OTP;
    private TextView mobileNoShow;
    private TextView timeShow_existingUser;
    private TextView resendClick_existingUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_user__forget_password__otp__at_view_cart);

        Intent getMobileNumber = getIntent();
        MobileNumberFrom_Existing_User_Forget_Password = getMobileNumber.getStringExtra("MOBILE_NO_TO_CONFIRM_PASSWORD_FOR_EXISTING_USER");

        pinview_OTP = (Pinview) findViewById(R.id.existing_User_otpPinView_AtViewCart);
        otpButton_OTP = (Button) findViewById(R.id.existingUser_otpSubmit_ViewCart);
        errorText_OTP = (TextView) findViewById(R.id.existing_User_otpErroratVCart);

        mobileNoShow = (TextView) findViewById(R.id.otp_MobileNumber_existing_User);

        mobileNoShow.setText(MobileNumberFrom_Existing_User_Forget_Password);

        timeShow_existingUser = (TextView) findViewById(R.id.timeShower_existing_User);
        resendClick_existingUser = (TextView) findViewById(R.id.reSend_existing_User);

        getOTP();

        CallCountDown_Timer();

      


        otpButton_OTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(entered_OTP_AtViewCart)) {
                    sendMobileNoandOTPForConfirmPassword();
                } else {
                    Toast.makeText(ExistingUser_ForgetPassword_OTP_AtViewCartActivity.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void CallCountDown_Timer() {

        CountDownTimer countDownTimer = new CountDownTimer(120 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {


                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
               timeShow_existingUser.setText("TIME : " + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));

                Log.d("test", "testing");

                // timeShow_existingUser.setText(""+millisUntilFinished/1000);

            }

            @Override
            public void onFinish() {

                otpButton_OTP.setVisibility(View.INVISIBLE);
                resendClick_existingUser.setVisibility(View.VISIBLE);

                resendClick_existingUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        otpButton_OTP.setVisibility(View.VISIBLE);
                        resendClick_existingUser.setVisibility(View.INVISIBLE);
                        CallCountDown_Timer();
                        getOTP();

                        new android.os.Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                //csprogress.dismiss();
//whatever you want just you have to launch overhere.


                            }
                        }, 1000);

                        sendOTPForResendSer();


                    }
                });

            }
        }.start();
    }



    private void sendOTPForResendSer() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(ExistingUser_ForgetPassword_OTP_AtViewCartActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api = APIClientToSendOTPToMFrom_FP.getAPIInterfaceTOSendOTPFrom_FP();

        OTPSendToMobileDTOFrom_FP otpSendToMobileDTOFrom_fp = new OTPSendToMobileDTOFrom_FP(MobileNumberFrom_Existing_User_Forget_Password);

        Call<JSONResponseToSendOTPFromForgetPasswordDTO> call = api.getOTPTOForgetPassword(otpSendToMobileDTOFrom_fp);

        call.enqueue(new Callback<JSONResponseToSendOTPFromForgetPasswordDTO>() {
            @Override
            public void onResponse(Call<JSONResponseToSendOTPFromForgetPasswordDTO> call, Response<JSONResponseToSendOTPFromForgetPasswordDTO> response) {

                if (response.isSuccessful()) {
                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }

                }

            }

            @Override
            public void onFailure(Call<JSONResponseToSendOTPFromForgetPasswordDTO> call, Throwable t) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }


            }
        });

    }

    private void getOTP() {

        pinview_OTP.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean b) {

                entered_OTP_AtViewCart = pinview_OTP.getValue();

            }
        });

    }

    private void sendMobileNoandOTPForConfirmPassword() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(ExistingUser_ForgetPassword_OTP_AtViewCartActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api = APIClientToSendMobileNoAndOTP.getApiInterfaceToSendMobileNoAndOTP();


        OTPandMobileNoDTO otPandMobileNoDTO = new OTPandMobileNoDTO(MobileNumberFrom_Existing_User_Forget_Password, entered_OTP_AtViewCart);

        Call<JSONOTPResponseFromOTPActivity> call = api.sendMobileNoandOTPFromOTPActivity(otPandMobileNoDTO);

        call.enqueue(new Callback<JSONOTPResponseFromOTPActivity>() {
            @Override
            public void onResponse(Call<JSONOTPResponseFromOTPActivity> call, Response<JSONOTPResponseFromOTPActivity> response) {

                if (response.isSuccessful()) {

                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }

                    Toast.makeText(ExistingUser_ForgetPassword_OTP_AtViewCartActivity.this, "Here", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ExistingUser_ForgetPassword_OTP_AtViewCartActivity.this, Existing_User_NewPassAndConfirmPass_AtViewCartActivity.class);
                    intent.putExtra("MOBILENO_FOR_NEW_AND_CONFIRM_PASSWORD", MobileNumberFrom_Existing_User_Forget_Password);
                    startActivity(intent);
                } else {
                    // if (jsonotpResponseFromOTPActivity.getStatus() == 500) {

                    //Resend OTP

                }
            }


            @Override
            public void onFailure(Call<JSONOTPResponseFromOTPActivity> call, Throwable t) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

            }
        });

    }


}
