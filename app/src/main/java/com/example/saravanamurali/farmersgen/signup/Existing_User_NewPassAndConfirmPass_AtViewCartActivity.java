package com.example.saravanamurali.farmersgen.signup;

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
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForNewPassAndConfirmPass;
import com.example.saravanamurali.farmersgen.signin.LoginActivityForViewCart;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Existing_User_NewPassAndConfirmPass_AtViewCartActivity extends AppCompatActivity {
    
    String MobileNo_For_New_And_Confirm_Pass;

    TextInputLayout newPassword_AtViewCart;
    TextInputLayout confirmPassword_AtViewCart;
    Button button_ViewCart;

    private String n_Pass_V_Cart = "";
    private String c_Pass_V_Cart = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing__user__new_pass_and_confirm_pass__at_view_cart);

        Intent get_MobileNo=getIntent();
        MobileNo_For_New_And_Confirm_Pass=get_MobileNo.getStringExtra("MOBILENO_FOR_NEW_AND_CONFIRM_PASSWORD");

        newPassword_AtViewCart = (TextInputLayout) findViewById(R.id.newPassword_AtViewCart);
        confirmPassword_AtViewCart = (TextInputLayout) findViewById(R.id.confirmPassword_AtViewCartActivity);
        button_ViewCart = (Button) findViewById(R.id.confirmButton_AtViewCart);




    }

    public void onClickSubmit_AtViewCartActivity(View view) {

        String n_Password_VCart = newPassword_AtViewCart.getEditText().getText().toString().trim();
        String c_Password_VCart = confirmPassword_AtViewCart.getEditText().getText().toString().trim();

        int n_P_ViewCart = n_Password_VCart.length();
        int c_P_ViewCart = c_Password_VCart.length();

        if(n_Password_VCart.isEmpty() && c_Password_VCart.isEmpty()){
            Toast.makeText(Existing_User_NewPassAndConfirmPass_AtViewCartActivity.this, "Password field should not Empty", Toast.LENGTH_SHORT).show();
        }


        if (!n_Password_VCart.equals(c_Password_VCart)) {
            Toast.makeText(Existing_User_NewPassAndConfirmPass_AtViewCartActivity.this, "Password is not Match", Toast.LENGTH_SHORT).show();
        }
        else if (n_P_ViewCart != c_P_ViewCart) {
            Toast.makeText(Existing_User_NewPassAndConfirmPass_AtViewCartActivity.this, "Password length is not Match", Toast.LENGTH_SHORT).show();

        }

        else if (n_P_ViewCart <= 4 && c_P_ViewCart <= 4) {
            Toast.makeText(Existing_User_NewPassAndConfirmPass_AtViewCartActivity.this, "Please Enter More than 4 character", Toast.LENGTH_SHORT).show();

        }

        else if (n_Password_VCart.equals(c_Password_VCart) && n_P_ViewCart == c_P_ViewCart) {
            n_Pass_V_Cart = n_Password_VCart;
            c_Pass_V_Cart = c_Password_VCart;
            savePassword_AtViewCart();
        }


    }

    private void savePassword_AtViewCart() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(Existing_User_NewPassAndConfirmPass_AtViewCartActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api = APIClientForNewPassAndConfirmPass.getApiInterfaceForNewPasswordAndConfirmPassword();

        NewPassAndConfirmPassDTO newPassAndConfirmPassDTO = new NewPassAndConfirmPassDTO(MobileNo_For_New_And_Confirm_Pass, n_Pass_V_Cart, c_Pass_V_Cart);

        Call<JSONResponseForNPasswordAndCPasswrod> call = api.newPasswordAndConfirmPassword(newPassAndConfirmPassDTO);

        call.enqueue(new Callback<JSONResponseForNPasswordAndCPasswrod>() {
            @Override
            public void onResponse(Call<JSONResponseForNPasswordAndCPasswrod> call, Response<JSONResponseForNPasswordAndCPasswrod> response) {

                if (response.isSuccessful()) {

                    if(csprogress.isShowing()){
                        csprogress.dismiss();
                    }

                    JSONResponseForNPasswordAndCPasswrod jsonResponseForNPasswordAndCPasswrod = response.body();

                    if (jsonResponseForNPasswordAndCPasswrod.getStatus() == 200) {

                        Intent loginActivity = new Intent(Existing_User_NewPassAndConfirmPass_AtViewCartActivity.this, LoginActivityForViewCart.class);
                        startActivity(loginActivity);

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
