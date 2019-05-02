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

public class NewPassAndConfirmPassForLoginForgetPassword_At_ViewCart extends AppCompatActivity {

    String mobileNumberForNewPassAndConfirmPass_AtViewCart;

    String n_Pass_AtViewCart;
    String c_Pass_AtViewCart;

    TextInputLayout newPasswordFromLoginForgetPassword_AtViewCart;
    TextInputLayout confirmPasswordFromLoginForgetPassword_AtViewCart;
    Button buttonFromLoginForgetPassword_AtViewCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pass_and_confirm_pass_for_login_forget_password__at__view_cart);

        Intent intent = getIntent();

        mobileNumberForNewPassAndConfirmPass_AtViewCart = getIntent().getStringExtra("MOBILENO_FOR_NEW_PASS_AND_CONFIRM_PASSWORD_ATVIEWCART");

        newPasswordFromLoginForgetPassword_AtViewCart = (TextInputLayout) findViewById(R.id.newPasswordForLoginForgetPassword_At_ViewCart);
        confirmPasswordFromLoginForgetPassword_AtViewCart = (TextInputLayout) findViewById(R.id.confirmPasswordForLoginForgetPassword_AtViewCart);

    }

    public void onClickSubmitForLoginForgetPassword_AtViewCart(View view) {

        String n_Password_AtViewCart = newPasswordFromLoginForgetPassword_AtViewCart.getEditText().getText().toString().trim();
        String c_Password_AtViewCart = confirmPasswordFromLoginForgetPassword_AtViewCart.getEditText().getText().toString().trim();

        int n_P_ViewCart = n_Password_AtViewCart.length();
        int c_PViewCart = c_Password_AtViewCart.length();

        if (n_Password_AtViewCart.isEmpty() && c_Password_AtViewCart.isEmpty()) {
            Toast.makeText(NewPassAndConfirmPassForLoginForgetPassword_At_ViewCart.this, "Password field should not be Empty", Toast.LENGTH_SHORT).show();
        }

        if (!n_Password_AtViewCart.equals(c_Password_AtViewCart)) {
            Toast.makeText(NewPassAndConfirmPassForLoginForgetPassword_At_ViewCart.this, "Password is not Match", Toast.LENGTH_SHORT).show();
        } else if (n_P_ViewCart != c_PViewCart) {
            Toast.makeText(NewPassAndConfirmPassForLoginForgetPassword_At_ViewCart.this, "Password length is not Match", Toast.LENGTH_SHORT).show();
        } else if (n_P_ViewCart <= 4 && c_PViewCart <= 4) {
            Toast.makeText(NewPassAndConfirmPassForLoginForgetPassword_At_ViewCart.this, "Please Enter More than 4 character", Toast.LENGTH_SHORT).show();

        } else if (n_Password_AtViewCart.equals(c_Password_AtViewCart) && n_P_ViewCart == c_PViewCart) {
            n_Pass_AtViewCart = n_Password_AtViewCart;
            c_Pass_AtViewCart = c_Password_AtViewCart;
            upatePasswordPassword();
        }


    }

    private void upatePasswordPassword() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(NewPassAndConfirmPassForLoginForgetPassword_At_ViewCart.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);

        ApiInterface api = APIClientForNewPassAndConfirmPassFromLoginPassword.getApiInterfaceForNewPassAndConfirmPassFromLoginPassword();

        NewPassAndConfirmPassDTO newPassAndConfirmPassDTO_AtViewCart = new NewPassAndConfirmPassDTO(mobileNumberForNewPassAndConfirmPass_AtViewCart, n_Pass_AtViewCart, c_Pass_AtViewCart);

        Call<JSONResponseForNPasswordAndCPasswrod> call = api.newPasswordAndConfirmPasswordFromLoginPassword(newPassAndConfirmPassDTO_AtViewCart);

        call.enqueue(new Callback<JSONResponseForNPasswordAndCPasswrod>() {
            @Override
            public void onResponse(Call<JSONResponseForNPasswordAndCPasswrod> call, Response<JSONResponseForNPasswordAndCPasswrod> response) {
                if (response.isSuccessful()) {

                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }

                    JSONResponseForNPasswordAndCPasswrod jsonResponseForNPasswordAndCPasswrod = response.body();

                    if (jsonResponseForNPasswordAndCPasswrod.getStatus() == 200) {

                        Intent loginActivity = new Intent(NewPassAndConfirmPassForLoginForgetPassword_At_ViewCart.this, LoginActivityForViewCart.class);
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
