package com.example.saravanamurali.farmersgen.signin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseToSendMobileNoFromLoginForgetPasswordDTO;
import com.example.saravanamurali.farmersgen.models.OTPSendToMobileDTOFrom_FP;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToSendMobileNoFromLoginForgetPassword;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginForgetPasswordActivityAtViewCart extends AppCompatActivity {

    private TextInputLayout loginForgetPasswordMobileNumber_AtviewCart;

    private String logMMobileForForgetMobile_AtviewCart ="";

    TextInputEditText editTextMobileLoginForgetPassword_AtviewCart;

    TextInputEditText editTextMLForgetMobile_AtviewCart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forget_password_at_view_cart);

        loginForgetPasswordMobileNumber_AtviewCart = (TextInputLayout) findViewById(R.id.MobileNoForForgetPasswordAtViewCart);

        editTextMLForgetMobile_AtviewCart = findViewById(R.id.editTextLMForForgetPasswordAtViewCart);

    }

    private boolean validateMobileNumber() {

        boolean status = false;
        String m_No = loginForgetPasswordMobileNumber_AtviewCart.getEditText().getText().toString().trim();
        if (m_No.isEmpty()) {
            loginForgetPasswordMobileNumber_AtviewCart.setError("Mobile Field Can't be Empty");
            status = false;
        } else if (m_No.length() != 10) {
            loginForgetPasswordMobileNumber_AtviewCart.setError("Pls Enter 10 digit Mobile Number");
            status = false;
        } else if (Pattern.matches("[a-zA-Z]+", m_No)) {

            loginForgetPasswordMobileNumber_AtviewCart.setError("Please Enter Valid Mobile Number");
            status = false;

        } else if (!Pattern.matches("[a-zA-Z]+", m_No)) {
            if (m_No.length() == 10) {
                loginForgetPasswordMobileNumber_AtviewCart.setError("");
                logMMobileForForgetMobile_AtviewCart = m_No;
                status = true;

            }
        }

        return status;
    }


    public void OnlickLoginForgetPasswordAtViewCart(View view) {

        if (!validateMobileNumber()) {

            return;
        } else {
            editTextMLForgetMobile_AtviewCart.setText("");
            sendMobileNumberForOTPInLoginForgetPassword_AtViewCartActivity(logMMobileForForgetMobile_AtviewCart);
        }
    }

    private void sendMobileNumberForOTPInLoginForgetPassword_AtViewCartActivity(String logMMobileForForgetMobile_atviewCart) {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(LoginForgetPasswordActivityAtViewCart.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);

        ApiInterface api=APIClientToSendMobileNoFromLoginForgetPassword.getAPIInterfaceTOSendMobileNoFromLoginForgetPassword();

        OTPSendToMobileDTOFrom_FP otpSendToMobileDTOFrom_Login_FP_AtViewCart=new OTPSendToMobileDTOFrom_FP(logMMobileForForgetMobile_atviewCart);

        Call<JSONResponseToSendMobileNoFromLoginForgetPasswordDTO> call =api.sendMobileNumberFromLoginForgetPassword(otpSendToMobileDTOFrom_Login_FP_AtViewCart);

        call.enqueue(new Callback<JSONResponseToSendMobileNoFromLoginForgetPasswordDTO>() {
            @Override
            public void onResponse(Call<JSONResponseToSendMobileNoFromLoginForgetPasswordDTO> call, Response<JSONResponseToSendMobileNoFromLoginForgetPasswordDTO> response) {
                if (response.isSuccessful()) {
                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }

                    JSONResponseToSendMobileNoFromLoginForgetPasswordDTO jsonResponseToSendMobileNoFromLoginForgetPasswordDTO = response.body();

                    if (jsonResponseToSendMobileNoFromLoginForgetPasswordDTO.getStatus() == 200) {

                        Intent otpActivityforLoginForgetPasssordAtViewCart = new Intent(LoginForgetPasswordActivityAtViewCart.this, OTPActivityForLoginForgetPasswordAtViewCart.class);
                        otpActivityforLoginForgetPasssordAtViewCart.putExtra("MOBILENO_FOR_LOGIN_FORGET_PASSWORD_AT_VIEWCART", logMMobileForForgetMobile_AtviewCart);
                        startActivity(otpActivityforLoginForgetPasssordAtViewCart);
                        finish();
                    }

                    else if(jsonResponseToSendMobileNoFromLoginForgetPasswordDTO.getStatus() == 500){

                        Toast.makeText(LoginForgetPasswordActivityAtViewCart.this,"We dont have your mobile number please Signup",Toast.LENGTH_LONG).show();
                    }
                }
            }


            @Override
            public void onFailure(Call<JSONResponseToSendMobileNoFromLoginForgetPasswordDTO> call, Throwable t) {

                if(csprogress.isShowing()){
                    csprogress.dismiss();
                }

            }
        });


    }
}
