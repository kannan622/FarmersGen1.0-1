package com.example.saravanamurali.farmersgen.signup;

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
import com.example.saravanamurali.farmersgen.signin.LoginActivityForViewCart;
import com.example.saravanamurali.farmersgen.signin.OTPActivityForLoginForgetPassword;
import com.goodiebag.pinview.Pinview;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private String code;
    private SmsVerifyCatcher smsVerifyCatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpfor_view_cart2);

        Intent getMobileNumber = getIntent();
        mobileNumberToSendOTP_AtViewCart = getMobileNumber.getStringExtra("MOBILENOTOSEND_OTPATVIEWCART2");

        pinviewRegistration_AtViewCart = (Pinview) findViewById(R.id.otpForRegistrationAtViewCart2);
        otpButtonRegistration_AtViewCart = (Button) findViewById(R.id.otpRegistrationSubmitAtViewCart2);

        mobileShow = (TextView) findViewById(R.id.otp_MobileNumber);

        mobileShow.setText(mobileNumberToSendOTP_AtViewCart);

        timeShow = (TextView) findViewById(R.id.timeShower);
        resendClick = (TextView) findViewById(R.id.reSend);

        //Getting OTP
        gettingOTP();

        callCountDownTimer();

        //Button is clicked
        otpButtonRegistration_AtViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!TextUtils.isEmpty(entered_OTP_AtViewCart)) {
                    sendMobileNoandOTPForViewCart();
                } else {

                    Toast.makeText(OTPActivityForViewCart2.this, "Please enter valid OTP", Toast.LENGTH_LONG).show();
                }

            }
        });

        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {

                code = parseCode(message);
                pinviewRegistration_AtViewCart.setValue(code);
                gettingOTP();


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
        CountDownTimer countDownTimer = new CountDownTimer(120 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                ms = millisUntilFinished;

                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                timeShow.setText("TIME : " + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));


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

                        new android.os.Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                //csprogress.dismiss();
//whatever you want just you have to launch overhere.


                            }
                        }, 1000);


                        sendOTPForResend();

                    }
                });


            }
        }.start();

    }

    private void sendOTPForResend() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(OTPActivityForViewCart2.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api = APIClientToSendOTPToMFrom_FP.getAPIInterfaceTOSendOTPFrom_FP();

        OTPSendToMobileDTOFrom_FP otpSendToMobileDTOFrom_fp = new OTPSendToMobileDTOFrom_FP(mobileNumberToSendOTP_AtViewCart);

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

    private void sendMobileNoandOTPForViewCart() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(OTPActivityForViewCart2.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api = APIClientToSendMobileNoAndOTP.getApiInterfaceToSendMobileNoAndOTP();


        OTPandMobileNoDTO otPandMobileNoDTO = new OTPandMobileNoDTO(entered_OTP_AtViewCart, mobileNumberToSendOTP_AtViewCart);


        Call<JSONOTPResponseFromOTPActivity> call = api.sendMobileNoandOTPFromOTPActivity(otPandMobileNoDTO);

        call.enqueue(new Callback<JSONOTPResponseFromOTPActivity>() {
            @Override
            public void onResponse(Call<JSONOTPResponseFromOTPActivity> call, Response<JSONOTPResponseFromOTPActivity> response) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

                JSONOTPResponseFromOTPActivity jsonotpResponseFromOTPActivity = response.body();


                if (jsonotpResponseFromOTPActivity.getStatus() == 200) {

                    Intent intent = new Intent(OTPActivityForViewCart2.this, LoginActivityForViewCart.class);
                    startActivity(intent);
                    finish();
                } else if (jsonotpResponseFromOTPActivity.getStatus() == 500) {

                    Toast.makeText(OTPActivityForViewCart2.this, "You have entered wrong OTP", Toast.LENGTH_LONG).show();

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
