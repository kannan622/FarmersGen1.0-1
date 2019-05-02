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

public class LoginForgetPasswordActivity extends AppCompatActivity {

    private TextInputLayout loginForgetPasswordMobileNumber;

    private String logMMobileForForgetMobile ="";

    TextInputEditText editTextMobileLoginForgetPassword;

    TextInputEditText editTextMLForgetMobile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forget_password);

        loginForgetPasswordMobileNumber = (TextInputLayout) findViewById(R.id.MobileNoForForgetPassword);

        editTextMLForgetMobile = findViewById(R.id.editTextLMForForgetPassword);

    }

    private boolean validateMobileNumber() {

        boolean status = false;
        String m_No = loginForgetPasswordMobileNumber.getEditText().getText().toString().trim();
        if (m_No.isEmpty()) {
            loginForgetPasswordMobileNumber.setError("Mobile Field Can't be Empty");
            status = false;
        } else if (m_No.length() != 10) {
            loginForgetPasswordMobileNumber.setError("Pls Enter 10 digit Mobile Number");
            status = false;
        } else if (Pattern.matches("[a-zA-Z]+", m_No)) {

            loginForgetPasswordMobileNumber.setError("Please Enter Valid Mobile Number");
            status = false;

        } else if (!Pattern.matches("[a-zA-Z]+", m_No)) {
            if (m_No.length() == 10) {
                loginForgetPasswordMobileNumber.setError("");
                logMMobileForForgetMobile = m_No;
                status = true;

            }
        }

        return status;
    }

    public void OnlickLoginForgetPassword(View view) {
       /* final String ForgetPasswordLoginMobileNumber = editTextMobileLoginForgetPassword.getText().toString();

        if (!TextUtils.isEmpty(ForgetPasswordLoginMobileNumber)) {
            if (Utils.isValidMobile(ForgetPasswordLoginMobileNumber)) {
                sendMobileNumberForOTPInLoginForgetPassword(ForgetPasswordLoginMobileNumber);
            } else {
                Toast.makeText(this, "Enter Valid Mobile number", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Enter Mobile number", Toast.LENGTH_LONG).show();
        }*/

       if (!validateMobileNumber()) {

            return;
        } else {
           editTextMLForgetMobile.setText("");
           sendMobileNumberForOTPInLoginForgetPassword(logMMobileForForgetMobile);
        }


    }

    private void sendMobileNumberForOTPInLoginForgetPassword(String forgetPasswordLoginMobileNumber) {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(LoginForgetPasswordActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api=APIClientToSendMobileNoFromLoginForgetPassword.getAPIInterfaceTOSendMobileNoFromLoginForgetPassword();

        OTPSendToMobileDTOFrom_FP otpSendToMobileDTOFrom_Login_FP=new OTPSendToMobileDTOFrom_FP(forgetPasswordLoginMobileNumber);

        Call<JSONResponseToSendMobileNoFromLoginForgetPasswordDTO>  call =api.sendMobileNumberFromLoginForgetPassword(otpSendToMobileDTOFrom_Login_FP);

        call.enqueue(new Callback<JSONResponseToSendMobileNoFromLoginForgetPasswordDTO>() {
            @Override
            public void onResponse(Call<JSONResponseToSendMobileNoFromLoginForgetPasswordDTO> call, Response<JSONResponseToSendMobileNoFromLoginForgetPasswordDTO> response) {

                if(response.isSuccessful()){

                    if(csprogress.isShowing()){
                         csprogress.dismiss();
                    }

                    JSONResponseToSendMobileNoFromLoginForgetPasswordDTO jsonResponseToSendMobileNoFromLoginForgetPasswordDTO=response.body();


                    if(jsonResponseToSendMobileNoFromLoginForgetPasswordDTO.getStatus()==200) {

                        Toast.makeText(LoginForgetPasswordActivity.this, "Success", Toast.LENGTH_LONG).show();
                        Intent otpActivityforLoginForgetPasssord = new Intent(LoginForgetPasswordActivity.this, OTPActivityForLoginForgetPassword.class);
                        otpActivityforLoginForgetPasssord.putExtra("MOBILENO_FOR_LOGIN_FORGET_PASSWORD", logMMobileForForgetMobile);
                        startActivity(otpActivityforLoginForgetPasssord);
                        finish();
                    }

                    else if(jsonResponseToSendMobileNoFromLoginForgetPasswordDTO.getStatus() == 500){

                        Toast.makeText(LoginForgetPasswordActivity.this,"We dont have your mobile number please Signup",Toast.LENGTH_LONG).show();
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
