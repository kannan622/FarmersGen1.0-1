package com.example.saravanamurali.farmersgen.signin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseToSendOTPFromForgetPasswordDTO;
import com.example.saravanamurali.farmersgen.models.OTPSendToMobileDTOFrom_FP;
import com.example.saravanamurali.farmersgen.models.SignInDTO;
import com.example.saravanamurali.farmersgen.models.SignedInJSONResponse;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToLogin;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToSendOTPToMFrom_FP;
import com.example.saravanamurali.farmersgen.signup.OTPActivity;
import com.example.saravanamurali.farmersgen.tappedactivity.HomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassword extends AppCompatActivity {

    private TextInputLayout password;
    private TextView forgotPasswordText;
    private Button next;

    String confirmPassword="";

    String forgetPasswordMobileNumber="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        Intent intent = getIntent();

        forgetPasswordMobileNumber=intent.getStringExtra("MOBILENO");


        password =(TextInputLayout) findViewById(R.id.forgotPassword);
        //Text
        forgotPasswordText=(TextView)findViewById(R.id.forgotMyPassword);
        next=(Button)findViewById(R.id.nextButton);


        //When the user clicks I forgot my password text need to send Mobile Number to Server
       /* forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent otpIntent=new Intent(ForgetPassword.this,OTPActivity.class);
                otpIntent.putExtra("MOBILENUM_TOCONFIR_PASSWORD",forgetPasswordMobileNumber);

                ApiInterface api=APIClientToSendOTPToMFrom_FP.getAPIInterfaceTOSendOTPFrom_FP();

                OTPSendToMobileDTOFrom_FP otpSendToMobileDTOFrom_fp=new OTPSendToMobileDTOFrom_FP(forgetPasswordMobileNumber);

              Call<JSONResponseToSendOTPFromForgetPasswordDTO> call= api.getOTPTOForgetPassword(otpSendToMobileDTOFrom_fp);

              call.enqueue(new Callback<JSONResponseToSendOTPFromForgetPasswordDTO>() {
                  @Override
                  public void onResponse(Call<JSONResponseToSendOTPFromForgetPasswordDTO> call, Response<JSONResponseToSendOTPFromForgetPasswordDTO> response) {
                      if(response.isSuccessful()){

                      }
                  }

                  @Override
                  public void onFailure(Call<JSONResponseToSendOTPFromForgetPasswordDTO> call, Throwable t) {

                  }
              });





            }
        });*/



    }



    public void forgetPassword(View view) {

        if ( !validatePassword()) {
            return;
        } else {

            login();


        }
    }

    private void login() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(ForgetPassword.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api = APIClientToLogin.getApiInterfaceToLogin();

        SignInDTO signInDTO = new SignInDTO(forgetPasswordMobileNumber, confirmPassword);

        Call<SignedInJSONResponse> call = api.getLoginUser(signInDTO);

        call.enqueue(new Callback<SignedInJSONResponse>() {
            @Override
            public void onResponse(Call<SignedInJSONResponse> call, Response<SignedInJSONResponse> response) {
                System.out.println("I am inside");

                // JSONResponse jsonResponse=response.body();
                if (response.isSuccessful()) {

                    if(csprogress.isShowing()){
                        csprogress.dismiss();
                    }

                    SignedInJSONResponse signedInJSONResponse = response.body();


                    if (signedInJSONResponse.getUser_ID() != null) {


                        SharedPreferences current_User = getSharedPreferences("CURRENT_USER", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = current_User.edit();
                        editor.putString("CURRENTUSER", signedInJSONResponse.getUser_ID());
                        editor.commit();

                        System.out.println("Added current user" + current_User);

                        Toast.makeText(ForgetPassword.this, signedInJSONResponse.getUser_ID(), Toast.LENGTH_SHORT).show();


                        Intent menuHomeActivity = new Intent(ForgetPassword.this, HomeActivity.class);

                        menuHomeActivity.putExtra("CUR_USER_ID", signedInJSONResponse.getUser_ID());

                        startActivity(menuHomeActivity);
                        finish();
                    }


                    Toast.makeText(ForgetPassword.this, "Sucesss", Toast.LENGTH_LONG).show();
                } else {
                    if(csprogress.isShowing()){
                        csprogress.dismiss();
                    }
                    Toast.makeText(ForgetPassword.this, "You have Entered Wrong Password", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<SignedInJSONResponse> call, Throwable t) {

                if(csprogress.isShowing()){
                    csprogress.dismiss();
                }

            }

        });
    }

        private boolean validatePassword() {
        boolean status = false;
        String m_Password = password.getEditText().getText().toString().trim();

        if (m_Password.isEmpty()) {
            password.setError("Password Field Cant be Empty");
            status = false;
        } else if (m_Password.length() <= 4) {
            password.setError("Please Enter More than 4 character");
            status = false;
        } else if (!m_Password.isEmpty() && m_Password.length() >= 5) {
            password.setError("");
            status = true;

            confirmPassword = m_Password;

        }
        return status;
    }


    public void I_Forget_My_Password(View view) {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(ForgetPassword.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        Toast.makeText(ForgetPassword.this,"Clicked",Toast.LENGTH_SHORT).show();


        ApiInterface api=APIClientToSendOTPToMFrom_FP.getAPIInterfaceTOSendOTPFrom_FP();

        OTPSendToMobileDTOFrom_FP otpSendToMobileDTOFrom_fp=new OTPSendToMobileDTOFrom_FP(forgetPasswordMobileNumber);

        Call<JSONResponseToSendOTPFromForgetPasswordDTO> call= api.getOTPTOForgetPassword(otpSendToMobileDTOFrom_fp);

        call.enqueue(new Callback<JSONResponseToSendOTPFromForgetPasswordDTO>() {
            @Override
            public void onResponse(Call<JSONResponseToSendOTPFromForgetPasswordDTO> call, Response<JSONResponseToSendOTPFromForgetPasswordDTO> response) {
                if(response.isSuccessful()){

                    if(csprogress.isShowing()){
                        csprogress.dismiss();
                    }

                    Intent otpIntent=new Intent(ForgetPassword.this,OTPActivity.class);
                    otpIntent.putExtra("MOBILENUM_TOCONFIR_PASSWORD",forgetPasswordMobileNumber);
                    startActivity(otpIntent);
                    finish();

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
}
