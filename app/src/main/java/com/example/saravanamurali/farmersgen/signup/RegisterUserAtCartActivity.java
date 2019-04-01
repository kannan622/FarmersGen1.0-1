package com.example.saravanamurali.farmersgen.signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.fragment.ViewCartActivity;
import com.example.saravanamurali.farmersgen.models.SignUpDTO;
import com.example.saravanamurali.farmersgen.models.SignUpJSONResponse;
import com.example.saravanamurali.farmersgen.retrofitclient.ApiClientToSignUp;
import com.example.saravanamurali.farmersgen.signup.ExistingUser_ForgetPassword_AtViewCartActivity;
import com.example.saravanamurali.farmersgen.signup.OTPActForSuccRegistrationAtViewCart;
import com.example.saravanamurali.farmersgen.signup.OTPActivityForViewCart2;
import com.example.saravanamurali.farmersgen.signup.SignupActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterUserAtCartActivity extends AppCompatActivity {

    private TextInputLayout mobileNo_ViewCart;
    private TextInputLayout name_ViewCart;
    private TextInputLayout email_ViewCart;
    private TextInputLayout password_ViewCart;
    private Button btnSubmit;

    TextInputEditText editTextM_ViewCart;
    TextInputEditText editTextN_ViewCart;
    TextInputEditText editTextEM_ViewCart;
    TextInputEditText editTextpas_ViewCart;

    private String signUpMobile_ViewCart = "";
    private String signUpName_ViewCart = "";
    private String signUpMail_ViewCart = "";
    private String signUpPassword_ViewCart = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user_at_cart);


        mobileNo_ViewCart = findViewById(R.id.signUpMobileNoAtViewCartActivity);
        name_ViewCart = findViewById(R.id.signUpNameAtViewCartActivity);
        email_ViewCart = findViewById(R.id.signUpEmailAtViewCartActivity);
        password_ViewCart = findViewById(R.id.signUpPasswordAtViewCartActivity);

        editTextM_ViewCart = findViewById(R.id.editTextMAtViewCartActivity);
        editTextN_ViewCart = findViewById(R.id.editTextNAtViewCartActivity);
        editTextEM_ViewCart = findViewById(R.id.editTextEMAtViewCartActivity);
        editTextpas_ViewCart = findViewById(R.id.editTextpasAtViewCartActivity);



    }


    public void onClickSignUpAtViewCart(View view) {

        if (!validateMobileNumber_AtCart() | !validateName_AtCart() | !validateMail_AtCart() | !validatePassword_AtCart()) {
            return;
        } else {

            signUpAtViewCat();
            /*editTextM.setText("");
            editTextN.setText("");
            editTextEM.setText("");
            editTextpas.setText("");
*/
        }
    }

    private void signUpAtViewCat() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(RegisterUserAtCartActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface apiInterface = ApiClientToSignUp.getApiInterface();

        String ANDROID_MOBILE_ID = Settings.Secure.getString(RegisterUserAtCartActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        SignUpDTO signUpDTO = new SignUpDTO(signUpMobile_ViewCart, signUpName_ViewCart, signUpMail_ViewCart, signUpPassword_ViewCart, ANDROID_MOBILE_ID);
        Call<SignUpJSONResponse> call = apiInterface.registerUser(signUpDTO);

        call.enqueue(new Callback<SignUpJSONResponse>() {
            @Override
            public void onResponse(Call<SignUpJSONResponse> call, Response<SignUpJSONResponse> response) {
                if (response.isSuccessful()) {


                    editTextM_ViewCart.setText("");
                    editTextN_ViewCart.setText("");
                    editTextEM_ViewCart.setText("");
                    editTextpas_ViewCart.setText("");

                    if(csprogress.isShowing()){
                        csprogress.dismiss();
                    }

                    SignUpJSONResponse signUpJSONResponse = response.body();

                    if (signUpJSONResponse.getResultStatus() == 200) {
                        System.out.println("You are addded successfully");
                        Toast.makeText(RegisterUserAtCartActivity.this,"Your are going to Add",Toast.LENGTH_LONG).show();

                        Intent registerOTP = new Intent(RegisterUserAtCartActivity.this, OTPActivityForViewCart2.class);
                        registerOTP.putExtra("MOBILENOTOSEND_OTPATVIEWCART2", signUpJSONResponse.getData().getMobile());
                        startActivity(registerOTP);
                        finish();
                    }

                    else if (signUpJSONResponse.getResultStatus() == 500) {

                        Intent forgotPassword = new Intent(RegisterUserAtCartActivity.this, ExistingUser_ForgetPassword_AtViewCartActivity.class);
                        forgotPassword.putExtra("EXISTING_USER_MOBILENO_AT_VIEWCART", signUpJSONResponse.getData().getMobile());
                        startActivity(forgotPassword);
                        finish();

                        System.out.println("Number already exists");


                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpJSONResponse> call, Throwable t) {

                if(csprogress.isShowing()){
                    csprogress.dismiss();
                }

            }
        });


    }


    private boolean validateMobileNumber_AtCart() {

        boolean status = false;
        String m_No = mobileNo_ViewCart.getEditText().getText().toString().trim();
        if (m_No.isEmpty()) {
            mobileNo_ViewCart.setError("Mobile Field Can't be Empty");
            status = false;
        } else if (m_No.length() != 10) {
            mobileNo_ViewCart.setError("Pls Enter 10 digit Mobile Number");
            status = false;
        } else if (Pattern.matches("[a-zA-Z]+", m_No)) {

            mobileNo_ViewCart.setError("Please Enter Valid Mobile Number");
            status = false;

        } else if (!Pattern.matches("[a-zA-Z]+", m_No)) {
            if (m_No.length() == 10) {
                mobileNo_ViewCart.setError("");
                status = true;

                signUpMobile_ViewCart = m_No;

            }
        }

        return status;
    }


    private boolean validateName_AtCart() {
        boolean status = false;

        String m_Name = name_ViewCart.getEditText().getText().toString().trim();

        if (m_Name.isEmpty()) {
            name_ViewCart.setError("Name Should not be empty");
            status = false;
        } else {
            name_ViewCart.setError("");
            status = true;
            signUpName_ViewCart = m_Name;
        }

        return status;
    }

    private boolean validateMail_AtCart() {
        boolean status;
        String m_Mail = email_ViewCart.getEditText().getText().toString().trim();

        if (m_Mail.isEmpty()) {
            email_ViewCart.setError("Mail Field Can't be Empty");
            status = false;
        }

        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern p = Pattern.compile(EMAIL_STRING);
        Matcher m = p.matcher(m_Mail);
        status = m.matches();

        if (!status) {
            email_ViewCart.setError("Enter Valid Email");
            status = false;
        } else {
            email_ViewCart.setError("");
            status = true;
            signUpMail_ViewCart = m_Mail;

        }

        return status;
    }


    private boolean validatePassword_AtCart() {
        boolean status = false;

        String m_Password = password_ViewCart.getEditText().getText().toString().trim();

        if (m_Password.isEmpty()) {
            password_ViewCart.setError("Password Field Cant be Empty");
            status = false;
        } else if (m_Password.length() <= 4) {
            password_ViewCart.setError("Please Enter More than 4 character");
            status = false;
        } else if (!m_Password.isEmpty() && m_Password.length() >= 5) {
            password_ViewCart.setError("");
            status = true;

            signUpPassword_ViewCart = m_Password;

        }

        return status;
    }



    public void arrowbackToLoginAtViewCartActivity(View view) {
    }
}
