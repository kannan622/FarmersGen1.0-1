package com.example.saravanamurali.farmersgen.signin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONOTPResponseFromOTPActivity;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseToSendOTPFromForgetPasswordDTO;
import com.example.saravanamurali.farmersgen.models.OTPSendToMobileDTOFrom_FP;
import com.example.saravanamurali.farmersgen.models.OTPandMobileNoDTO;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToSendMobileNoAndOTPForLoginForgetPassword;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToSendOTPToMFrom_FP;
import com.goodiebag.pinview.Pinview;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPActivityForLoginForgetPassword extends AppCompatActivity {

    //Mobile Number from login Forget Password
    String mobileNumberForLoginForgetPassword;

    public Pinview pinview_AtLoginForgetPassword;
    private Button otpButton_AtLoginForgetPassword;
    private TextView mobileShow_ForgetPassword;

    private TextView timeShow_ForgetPassword;
    private TextView resendClick_ForgetPassword;


    private TextView errorText_AtLoginForgetPassword;
    private String entered_OTP_AtLoginForgetPassword;

    private long ms;


    private long mTimeLeftInMillies;

    private int minutes;
    private int seconds;

    private String code;
    private SmsVerifyCatcher smsVerifyCatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpfor_login_forget_password);

        Intent intent = getIntent();

        mobileNumberForLoginForgetPassword = intent.getStringExtra("MOBILENO_FOR_LOGIN_FORGET_PASSWORD");

        pinview_AtLoginForgetPassword = (Pinview) findViewById(R.id.otpPinViewForLoginForgetPassword);
        otpButton_AtLoginForgetPassword = (Button) findViewById(R.id.otpSubmitForLoginForgetPassword);
        errorText_AtLoginForgetPassword = (TextView) findViewById(R.id.otpErrorForLoginForgetPassword);

        mobileShow_ForgetPassword = (TextView) findViewById(R.id.otp_MobileNumber_AtForgetPassword);


        mobileShow_ForgetPassword.setText(mobileNumberForLoginForgetPassword);


        timeShow_ForgetPassword = (TextView) findViewById(R.id.timeShower_ForgetPassword);
        resendClick_ForgetPassword = (TextView) findViewById(R.id.reSend_ForgetPassword);

        getOTP();

        countDownTimerAtForgetPassword();


        otpButton_AtLoginForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(entered_OTP_AtLoginForgetPassword)) {
                    sendMobileNoandOTPForLoginForgetPassword();
                } else {
                    Toast.makeText(OTPActivityForLoginForgetPassword.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });


        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                code = parseCode(message);//Parse verification code

                pinview_AtLoginForgetPassword.setValue(code);

                getOTP();
            }
        });

    }

    private void countDownTimerAtForgetPassword() {

        CountDownTimer countDownTimer = new CountDownTimer(120 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                ms = millisUntilFinished;

                seconds = (int) (millisUntilFinished / 1000);
                minutes = seconds / 60;
                seconds = seconds % 60;
                timeShow_ForgetPassword.setText("TIME : " + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
            }

            @Override
            public void onFinish() {
                otpButton_AtLoginForgetPassword.setVisibility(View.INVISIBLE);
                resendClick_ForgetPassword.setVisibility(View.VISIBLE);
                resendClick_ForgetPassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        otpButton_AtLoginForgetPassword.setVisibility(View.VISIBLE);
                        resendClick_ForgetPassword.setVisibility(View.INVISIBLE);
                        countDownTimerAtForgetPassword();
                        getOTP();

                        new android.os.Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                //csprogress.dismiss();
//whatever you want just you have to launch overhere.


                            }
                        }, 1000);

                        sendOTPForResendAtForgetPasswordActivity();


                    }
                });

            }
        }.start();
    }

    private void sendOTPForResendAtForgetPasswordActivity() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(OTPActivityForLoginForgetPassword.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);

        ApiInterface api = APIClientToSendOTPToMFrom_FP.getAPIInterfaceTOSendOTPFrom_FP();

        OTPSendToMobileDTOFrom_FP otpSendToMobileDTOFrom_fp_Login = new OTPSendToMobileDTOFrom_FP(mobileNumberForLoginForgetPassword);

        Call<JSONResponseToSendOTPFromForgetPasswordDTO> call = api.getOTPTOForgetPassword(otpSendToMobileDTOFrom_fp_Login);

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

        pinview_AtLoginForgetPassword.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean b) {
                entered_OTP_AtLoginForgetPassword = pinview.getValue();
            }
        });

    }

    //Send mobile and OTP to server
    private void sendMobileNoandOTPForLoginForgetPassword() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(OTPActivityForLoginForgetPassword.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api = APIClientToSendMobileNoAndOTPForLoginForgetPassword.getApiInterfaceToSendMobileNoAndOTPForLoginForgetPassword();

        Log.d("my otp", entered_OTP_AtLoginForgetPassword);
        OTPandMobileNoDTO otpAndMobileNoDTO = new OTPandMobileNoDTO(entered_OTP_AtLoginForgetPassword, mobileNumberForLoginForgetPassword);

        Call<JSONOTPResponseFromOTPActivity> call = api.sendMobileNoandOTPFromLoginForgetPasswordActivity(otpAndMobileNoDTO);

        call.enqueue(new Callback<JSONOTPResponseFromOTPActivity>() {
            @Override
            public void onResponse(Call<JSONOTPResponseFromOTPActivity> call, Response<JSONOTPResponseFromOTPActivity> response) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

                JSONOTPResponseFromOTPActivity jsonotpResponseFromOTPActivity = response.body();


                if (jsonotpResponseFromOTPActivity.getStatus() == 200) {

                    Intent newPassintent = new Intent(OTPActivityForLoginForgetPassword.this, NewPassAndConfirmPassForLoginForgetPassword.class);
                    newPassintent.putExtra("MOBILENO_FOR_NEW_PASS_AND_CONFIRM_PASSWORD", mobileNumberForLoginForgetPassword);
                    startActivity(newPassintent);
                    finish();

                    //When user enters wrong otp
                } else if (jsonotpResponseFromOTPActivity.getStatus() == 500) {

                    Toast.makeText(OTPActivityForLoginForgetPassword.this, "You have entered wrong OTP", Toast.LENGTH_LONG).show();

                }


            }


            @Override
            public void onFailure(Call<JSONOTPResponseFromOTPActivity> call, Throwable t) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }
                Toast.makeText(OTPActivityForLoginForgetPassword.this, "Failed", Toast.LENGTH_LONG).show();

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        smsVerifyCatcher.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        smsVerifyCatcher.onStop();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private String parseCode(String message) {
        Pattern p = Pattern.compile("\\b\\d{4}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }
}
