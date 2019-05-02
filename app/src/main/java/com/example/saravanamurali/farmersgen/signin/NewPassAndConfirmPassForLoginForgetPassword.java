package com.example.saravanamurali.farmersgen.signin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseForNPasswordAndCPasswrod;
import com.example.saravanamurali.farmersgen.models.NewPassAndConfirmPassDTO;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForNewPassAndConfirmPassFromLoginPassword;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewPassAndConfirmPassForLoginForgetPassword extends AppCompatActivity {

    String mobileNumberForNewPassAndConfirmPass;

    String n_Pass;
    String c_Pass;

    TextInputLayout newPasswordFromLoginForgetPassword;
    TextInputLayout confirmPasswordFromLoginForgetPassword;
    Button buttonFromLoginForgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pass_and_confirm_pass_for_login_forget_password);

        Intent intent=getIntent();

        mobileNumberForNewPassAndConfirmPass=getIntent().getStringExtra("MOBILENO_FOR_NEW_PASS_AND_CONFIRM_PASSWORD");

        newPasswordFromLoginForgetPassword=(TextInputLayout)findViewById(R.id.newPasswordForLoginForgetPassword);
        confirmPasswordFromLoginForgetPassword=(TextInputLayout)findViewById(R.id.confirmPasswordForLoginForgetPassword);


    }

    public void onClickSubmitForLoginForgetPassword(View view) {

        String n_Password = newPasswordFromLoginForgetPassword.getEditText().getText().toString().trim();
        String c_Password = confirmPasswordFromLoginForgetPassword.getEditText().getText().toString().trim();

        int n_P = n_Password.length();
        int c_P = c_Password.length();

        if(n_Password.isEmpty() && c_Password.isEmpty()){
            Toast.makeText(NewPassAndConfirmPassForLoginForgetPassword.this, "Password field should not be Empty", Toast.LENGTH_SHORT).show();
        }

        if (!n_Password.equals(c_Password)) {
            Toast.makeText(NewPassAndConfirmPassForLoginForgetPassword.this, "Password is not Match", Toast.LENGTH_SHORT).show();
        }
        else if (n_P != c_P) {
            Toast.makeText(NewPassAndConfirmPassForLoginForgetPassword.this, "Password length is not Match", Toast.LENGTH_SHORT).show();

        }
        else if (n_P <= 4 && c_P <= 4) {
            Toast.makeText(NewPassAndConfirmPassForLoginForgetPassword.this, "Please Enter More than 4 character", Toast.LENGTH_SHORT).show();

        }
        else if (n_Password.equals(c_Password) && n_P == c_P) {
            n_Pass = n_Password;
            c_Pass = c_Password;
            upatePassword();
        }



    }

    private void upatePassword() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(NewPassAndConfirmPassForLoginForgetPassword.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api =APIClientForNewPassAndConfirmPassFromLoginPassword.getApiInterfaceForNewPassAndConfirmPassFromLoginPassword();

        NewPassAndConfirmPassDTO newPassAndConfirmPassDTO = new NewPassAndConfirmPassDTO(mobileNumberForNewPassAndConfirmPass, n_Pass, c_Pass);

       Call<JSONResponseForNPasswordAndCPasswrod> call =api.newPasswordAndConfirmPasswordFromLoginPassword(newPassAndConfirmPassDTO);

       call.enqueue(new Callback<JSONResponseForNPasswordAndCPasswrod>() {
           @Override
           public void onResponse(Call<JSONResponseForNPasswordAndCPasswrod> call, Response<JSONResponseForNPasswordAndCPasswrod> response) {
               if(response.isSuccessful()){

                   if(csprogress.isShowing()){
                       csprogress.dismiss();
                   }

                   JSONResponseForNPasswordAndCPasswrod jsonResponseForNPasswordAndCPasswrod = response.body();

                   if (jsonResponseForNPasswordAndCPasswrod.getStatus() == 200) {

                       Intent loginActivity = new Intent(NewPassAndConfirmPassForLoginForgetPassword.this, LoginActivity.class);
                       startActivity(loginActivity);
                       finish();

                   }


               }
           }

           @Override
           public void onFailure(Call<JSONResponseForNPasswordAndCPasswrod> call, Throwable t) {
               if(csprogress.isShowing()){
                   csprogress.dismiss();
               }

           }
       });




    }
}
