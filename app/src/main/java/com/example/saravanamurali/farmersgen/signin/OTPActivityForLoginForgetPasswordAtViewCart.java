package com.example.saravanamurali.farmersgen.signin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class OTPActivityForLoginForgetPasswordAtViewCart extends AppCompatActivity {

    //Mobile Number from login Forget Password
    String mobileNumberForLoginForgetPassword_AtViewCart;

    public Pinview pinview_AtLoginForgetPassword_AtViewCart;
    private Button otpButton_AtLoginForgetPassword_AtViewCart;
    private TextView mobileShow_ForgetPassword_AtViewCart;

    private TextView timeShow_ForgetPassword_AtViewCart;
    private TextView resendClick_ForgetPassword_AtViewCart;

    String entered_OTP_AtLoginForgetPassword_AtViewCart;

    private long ms;

    private String code;
   private  SmsVerifyCatcher smsVerifyCatcher;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpfor_login_forget_password_at_view_cart);

        Intent intent = getIntent();

        mobileNumberForLoginForgetPassword_AtViewCart=intent.getStringExtra("MOBILENO_FOR_LOGIN_FORGET_PASSWORD_AT_VIEWCART");

        pinview_AtLoginForgetPassword_AtViewCart = (Pinview) findViewById(R.id.otpPinViewForLoginForgetPassword_AtViewCart);
        otpButton_AtLoginForgetPassword_AtViewCart = (Button) findViewById(R.id.otpSubmitForLoginForgetPassword_AtViewCart);


        mobileShow_ForgetPassword_AtViewCart=(TextView)findViewById(R.id.otp_MobileNumber_AtForgetPassword_AtViewCart);

        mobileShow_ForgetPassword_AtViewCart.setText(mobileNumberForLoginForgetPassword_AtViewCart);

        timeShow_ForgetPassword_AtViewCart=(TextView)findViewById(R.id.timeShower_ForgetPassword_AtviewCart);
        resendClick_ForgetPassword_AtViewCart=(TextView)findViewById(R.id.reSend_ForgetPassword_AtviewCart);
        
        sendOTP();

        countDownTimerAtForgetPassword_AtViewCart();

        otpButton_AtLoginForgetPassword_AtViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(entered_OTP_AtLoginForgetPassword_AtViewCart)) {
                    sendMobileNoandOTPForLoginForgetPassword_AtViewCart();
                } else {
                    Toast.makeText(OTPActivityForLoginForgetPasswordAtViewCart.this,"Please enter OTP",Toast.LENGTH_SHORT).show();
                }
            }
        });

        smsVerifyCatcher =new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                
                code=parseCode(message);
                pinview_AtLoginForgetPassword_AtViewCart.setValue(code);
                sendOTP();



            }
        });




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

    private void sendMobileNoandOTPForLoginForgetPassword_AtViewCart() {
        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(OTPActivityForLoginForgetPasswordAtViewCart.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);

        ApiInterface api=APIClientToSendMobileNoAndOTPForLoginForgetPassword.getApiInterfaceToSendMobileNoAndOTPForLoginForgetPassword();
        OTPandMobileNoDTO otpAndMobileNoDTO_AtViewCart = new OTPandMobileNoDTO( entered_OTP_AtLoginForgetPassword_AtViewCart,mobileNumberForLoginForgetPassword_AtViewCart);
        Call<JSONOTPResponseFromOTPActivity> call=api.sendMobileNoandOTPFromLoginForgetPasswordActivity(otpAndMobileNoDTO_AtViewCart);

        call.enqueue(new Callback<JSONOTPResponseFromOTPActivity>() {
            @Override
            public void onResponse(Call<JSONOTPResponseFromOTPActivity> call, Response<JSONOTPResponseFromOTPActivity> response) {


                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }

                JSONOTPResponseFromOTPActivity jsonotpResponseFromOTPActivity = response.body();

                    if(jsonotpResponseFromOTPActivity.getStatus()==200) {

                        Intent newPassintentAtViewCart = new Intent(OTPActivityForLoginForgetPasswordAtViewCart.this, NewPassAndConfirmPassForLoginForgetPassword_At_ViewCart.class);
                        newPassintentAtViewCart.putExtra("MOBILENO_FOR_NEW_PASS_AND_CONFIRM_PASSWORD_ATVIEWCART", mobileNumberForLoginForgetPassword_AtViewCart);
                        startActivity(newPassintentAtViewCart);
                        finish();
                    }

                    else if (jsonotpResponseFromOTPActivity.getStatus() == 500) {

                        Toast.makeText(OTPActivityForLoginForgetPasswordAtViewCart.this, "You have entered wrong OTP", Toast.LENGTH_LONG).show();

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



    private void countDownTimerAtForgetPassword_AtViewCart() {

        CountDownTimer countDownTimer=new CountDownTimer(120*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                ms=millisUntilFinished;

                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                timeShow_ForgetPassword_AtViewCart.setText("TIME : " + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));

               // Log.d("test", "testing22");

              //  timeShow_ForgetPassword_AtViewCart.setText(""+millisUntilFinished/1000);


            }

            @Override
            public void onFinish() {

                otpButton_AtLoginForgetPassword_AtViewCart.setVisibility(View.INVISIBLE);
                resendClick_ForgetPassword_AtViewCart.setVisibility(View.VISIBLE);
                resendClick_ForgetPassword_AtViewCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        otpButton_AtLoginForgetPassword_AtViewCart.setVisibility(View.VISIBLE);
                        resendClick_ForgetPassword_AtViewCart.setVisibility(View.INVISIBLE);
                        countDownTimerAtForgetPassword_AtViewCart();
                        sendOTP();
                        new android.os.Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                //csprogress.dismiss();
//whatever you want just you have to launch overhere.


                            }
                        }, 1000);
                        sendOTPForResendAtForgetPasswordActivity_AtViewCart();

                    }
                });

            }
        }.start();
    }

    private void sendOTPForResendAtForgetPasswordActivity_AtViewCart() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(OTPActivityForLoginForgetPasswordAtViewCart.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);

        ApiInterface api=APIClientToSendOTPToMFrom_FP.getAPIInterfaceTOSendOTPFrom_FP();

        OTPSendToMobileDTOFrom_FP otpSendToMobileDTOFrom_fp_Login_AtviewCart=new OTPSendToMobileDTOFrom_FP(mobileNumberForLoginForgetPassword_AtViewCart);

        Call<JSONResponseToSendOTPFromForgetPasswordDTO> call= api.getOTPTOForgetPassword(otpSendToMobileDTOFrom_fp_Login_AtviewCart);

        call.enqueue(new Callback<JSONResponseToSendOTPFromForgetPasswordDTO>() {
            @Override
            public void onResponse(Call<JSONResponseToSendOTPFromForgetPasswordDTO> call, Response<JSONResponseToSendOTPFromForgetPasswordDTO> response) {
                if(response.isSuccessful()){
                    if(csprogress.isShowing()){
                        csprogress.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponseToSendOTPFromForgetPasswordDTO> call, Throwable t) {

                if(csprogress.isShowing()){
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

    private void sendOTP() {

        pinview_AtLoginForgetPassword_AtViewCart.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean b) {
                entered_OTP_AtLoginForgetPassword_AtViewCart =pinview.getValue();
            }
        });
    }
}
