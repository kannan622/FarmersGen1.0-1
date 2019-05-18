package com.example.saravanamurali.farmersgen.signup;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToSendMobileNoAndOTP;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToSendOTPToMFrom_FP;
import com.example.saravanamurali.farmersgen.signin.LoginActivity;
import com.example.saravanamurali.farmersgen.signin.OTPActivityForLoginForgetPassword;
import com.example.saravanamurali.farmersgen.util.Network_config;
import com.goodiebag.pinview.Pinview;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPActForSuccRegistrationAtViewCart extends AppCompatActivity {
    public Pinview pinviewRegistration;
    String entered_OTP;
    String mobileNumberToSendOTP;

    private TextView timeShow_ForgetPassword_AtSignup;
    private TextView resendClick_ForgetPassword_AtSignup;

    long ms;


    private Button otpButtonRegistration;
    Dialog dialog;

    private TextView mobileShow_ForgetPassword_Signup;

    private String code;
    private SmsVerifyCatcher smsVerifyCatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpact_for_succ_registration);

        dialog = new Dialog(getApplicationContext());
        Intent getMobileNumber = getIntent();
        mobileNumberToSendOTP = getMobileNumber.getStringExtra("MOBILENOTOLOGIN");

        mobileShow_ForgetPassword_Signup = (TextView) findViewById(R.id.otp_MobileNumber_AtForgetPasswordAtSignUp);

        mobileShow_ForgetPassword_Signup.setText(mobileNumberToSendOTP);

        pinviewRegistration = (Pinview) findViewById(R.id.otpForRegistrationAtSignUp);
        otpButtonRegistration = (Button) findViewById(R.id.otpRegistrationSubmitAtViewCartAtSignUp);

        timeShow_ForgetPassword_AtSignup = (TextView) findViewById(R.id.timeShower_ForgetPasswordAtSignUp);
        resendClick_ForgetPassword_AtSignup = (TextView) findViewById(R.id.reSend_ForgetPasswordAtSignUp);

        getOTPAtSignup();

        countDownTimerAtForgetPasswordAtSignUp();


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

        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {

                code = parseCode(message);
                pinviewRegistration.setValue(code);
                getOTPAtSignup();

            }
        });


    }

    private void countDownTimerAtForgetPasswordAtSignUp() {

        CountDownTimer countDownTimer = new CountDownTimer(120 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                ms = millisUntilFinished;

                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                timeShow_ForgetPassword_AtSignup.setText("TIME : " + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));


            }

            @Override
            public void onFinish() {

                otpButtonRegistration.setVisibility(View.INVISIBLE);
                resendClick_ForgetPassword_AtSignup.setVisibility(View.VISIBLE);

                resendClick_ForgetPassword_AtSignup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        otpButtonRegistration.setVisibility(View.VISIBLE);
                        resendClick_ForgetPassword_AtSignup.setVisibility(View.INVISIBLE);
                        countDownTimerAtForgetPasswordAtSignUp();

                        getOTPAtSignup();

                        new android.os.Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                //csprogress.dismiss();
                                //whatever you want just you have to launch overhere.


                            }
                        }, 1000);


                        reSendOTP();


                    }
                });

            }
        }.start();

    }

    private void reSendOTP() {
        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(OTPActForSuccRegistrationAtViewCart.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);

        ApiInterface api = APIClientToSendOTPToMFrom_FP.getAPIInterfaceTOSendOTPFrom_FP();

        OTPSendToMobileDTOFrom_FP otpSendToMobileDTOFrom_fp_Login = new OTPSendToMobileDTOFrom_FP(mobileNumberToSendOTP);

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

    private void getOTPAtSignup() {

        pinviewRegistration.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override

            public void onDataEntered(final Pinview pinview, boolean b) {
                entered_OTP = pinview.getValue();


            }
        });


    }

    //Send mobile and OTP to server
    private void sendMobileNoandOTP() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(OTPActForSuccRegistrationAtViewCart.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api = APIClientToSendMobileNoAndOTP.getApiInterfaceToSendMobileNoAndOTP();


        OTPandMobileNoDTO otPandMobileNoDTO = new OTPandMobileNoDTO(entered_OTP, mobileNumberToSendOTP);


        Call<JSONOTPResponseFromOTPActivity> call = api.sendMobileNoandOTPFromOTPActivity(otPandMobileNoDTO);

        call.enqueue(new Callback<JSONOTPResponseFromOTPActivity>() {
            @Override
            public void onResponse(Call<JSONOTPResponseFromOTPActivity> call, Response<JSONOTPResponseFromOTPActivity> response) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }


                JSONOTPResponseFromOTPActivity jsonotpResponseFromOTPActivity = response.body();

                if (jsonotpResponseFromOTPActivity.getStatus() == 200) {


                    Intent intent = new Intent(OTPActForSuccRegistrationAtViewCart.this, LoginActivity.class);
                    startActivity(intent);

                } else if (jsonotpResponseFromOTPActivity.getStatus() == 500) {

                    Toast.makeText(OTPActForSuccRegistrationAtViewCart.this, "You have entered wrong OTP", Toast.LENGTH_LONG).show();

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
