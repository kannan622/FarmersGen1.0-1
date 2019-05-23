package com.example.saravanamurali.farmersgen.signup;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.signin.ForgetPassword;
import com.example.saravanamurali.farmersgen.models.SignUpDTO;
import com.example.saravanamurali.farmersgen.models.SignUpJSONResponse;
import com.example.saravanamurali.farmersgen.retrofitclient.ApiClientToSignUp;
import com.example.saravanamurali.farmersgen.signin.LoginActivity;
import com.example.saravanamurali.farmersgen.util.Network_config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    TextInputEditText editTextM;
    TextInputEditText editTextN;
    TextInputEditText editTextEM;
    TextInputEditText editTextpas;
    private TextInputLayout mobileNumber;
    private TextInputLayout name;
    private TextInputLayout email;
    private TextInputLayout password;
    private Button btnSubmit;
    private String signUpMobile = "";
    private String signUpName = "";
    private String signUpMail = "";
    private String signUpPassword = "";
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        dialog = new Dialog(getApplicationContext());
        mobileNumber = findViewById(R.id.signUpMobileNo);
        name = findViewById(R.id.signUpName);
        email = findViewById(R.id.signUpEmail);
        password = findViewById(R.id.signUpPassword);

        editTextM = findViewById(R.id.editTextM);
        editTextN = findViewById(R.id.editTextN);
        editTextEM = findViewById(R.id.editTextEM);
        editTextpas = findViewById(R.id.editTextpas);


    }


    public void onClickSignUp(View view) {

        if (Network_config.is_Network_Connected_flag(getApplicationContext())) {
            if (!validateMobileNumber() | !validateName() | !validateMail() | !validatePassword()) {
                return;
            } else {

                signUp();
            /*editTextM.setText("");
            editTextN.setText("");
            editTextEM.setText("");
            editTextpas.setText("");
*/
            }
        } else {
            Network_config.customAlert(dialog, getApplicationContext(), getResources().getString(R.string.app_name),
                    getResources().getString(R.string.connection_message));
        }


    }


    private void signUp() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(SignupActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface apiInterface = ApiClientToSignUp.getApiInterface();

        String ANDROID_MOBILE_ID = Settings.Secure.getString(SignupActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        SignUpDTO signUpDTO = new SignUpDTO(signUpMobile, signUpName, signUpMail, signUpPassword, ANDROID_MOBILE_ID);

        Call<SignUpJSONResponse> call = apiInterface.registerUser(signUpDTO);

        call.enqueue(new Callback<SignUpJSONResponse>() {
            @Override
            public void onResponse(Call<SignUpJSONResponse> call, Response<SignUpJSONResponse> response) {

                editTextM.setText("");
                editTextN.setText("");
                editTextEM.setText("");
                editTextpas.setText("");

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

                SignUpJSONResponse signUpJSONResponse = response.body();

                if (signUpJSONResponse.getResultStatus() == 200) {
                    //System.out.println("You are addded successfully");
                    // Toast.makeText(SignupActivity.this, "Your are going to Add", Toast.LENGTH_LONG).show();

                    Intent registerOTP = new Intent(SignupActivity.this, OTPActForSuccRegistrationAtViewCart.class);
                    registerOTP.putExtra("MOBILENOTOLOGIN", signUpJSONResponse.getData().getMobile());
                    startActivity(registerOTP);
                } else if (signUpJSONResponse.getResultStatus() == 500) {

                    Intent forgotPassword = new Intent(SignupActivity.this, ForgetPassword.class);
                    forgotPassword.putExtra("MOBILENO", signUpJSONResponse.getData().getMobile());
                    startActivity(forgotPassword);

                    //System.out.println("Number already exists");


                }


            }

            @Override
            public void onFailure(Call<SignUpJSONResponse> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void arrowbackToLogin(View view) {
        Intent loginIntent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }


    private boolean validateMobileNumber() {

        boolean status = false;
        String m_No = mobileNumber.getEditText().getText().toString().trim();
        if (m_No.isEmpty()) {
            mobileNumber.setError("Mobile Field Can't be Empty");
            status = false;
        } else if (m_No.length() != 10) {
            mobileNumber.setError("Pls Enter 10 digit Mobile Number");
            status = false;
        } else if (Pattern.matches("[a-zA-Z]+", m_No)) {

            mobileNumber.setError("Please Enter Valid Mobile Number");
            status = false;

        } else if (!Pattern.matches("[a-zA-Z]+", m_No)) {
            if (m_No.length() == 10) {
                mobileNumber.setError("");
                status = true;

                signUpMobile = m_No;

            }
        }

        return status;
    }

    private boolean validateName() {
        boolean status = false;

        String m_Name = name.getEditText().getText().toString().trim();

        if (m_Name.isEmpty()) {
            name.setError("Name Should not be empty");
            status = false;
        } else {
            name.setError("");
            status = true;
            signUpName = m_Name;
        }

        return status;
    }

    private boolean validateMail() {
        boolean status;
        String m_Mail = email.getEditText().getText().toString().trim();

        if (m_Mail.isEmpty()) {
            email.setError("Mail Field Can't be Empty");
            status = false;
        }

        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern p = Pattern.compile(EMAIL_STRING);
        Matcher m = p.matcher(m_Mail);
        status = m.matches();

        if (!status) {
            email.setError("Enter Valid Email");
            status = false;
        } else {
            email.setError("");
            status = true;
            signUpMail = m_Mail;

        }

        return status;
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

            signUpPassword = m_Password;

        }

        return status;
    }


}
