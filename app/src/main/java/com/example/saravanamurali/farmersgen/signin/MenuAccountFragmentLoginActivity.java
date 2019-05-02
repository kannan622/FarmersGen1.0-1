package com.example.saravanamurali.farmersgen.signin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.models.SignInDTO;
import com.example.saravanamurali.farmersgen.models.SignedInJSONResponse;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToLogin;
import com.example.saravanamurali.farmersgen.tappedactivity.HomeActivity;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuAccountFragmentLoginActivity extends AppCompatActivity {

    private TextInputLayout mobileNumberAcc;
    private TextInputLayout passwordAcc;
    private Button loginButtonAcc;

    private String loginMobile = "";
    private String loginPassword = "";

    private String logMMobileAcc = "";
    private String logMPasswordAcc = "";

    TextInputEditText editTextMAcc;
    TextInputEditText editTextPMAcc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_account_fragment_login);

        mobileNumberAcc = findViewById(R.id.loginMobileNoMAcc);
        passwordAcc = findViewById(R.id.loginPasswordAcc);
        loginButtonAcc = (Button) findViewById(R.id.btnLogin);

        editTextMAcc = findViewById(R.id.editTextLMAcc);
        editTextPMAcc = findViewById(R.id.editTextLPAcc);


    }



    private boolean validateMobileNumber() {

        boolean status = false;
        String m_No = mobileNumberAcc.getEditText().getText().toString().trim();
        if (m_No.isEmpty()) {
            mobileNumberAcc.setError("Mobile Field Can't be Empty");
            status = false;
        } else if (m_No.length() != 10) {
            mobileNumberAcc.setError("Pls Enter 10 digit Mobile Number");
            status = false;
        } else if (Pattern.matches("[a-zA-Z]+", m_No)) {

            mobileNumberAcc.setError("Please Enter Valid Mobile Number");
            status = false;

        } else if (!Pattern.matches("[a-zA-Z]+", m_No)) {
            if (m_No.length() == 10) {
                mobileNumberAcc.setError("");
                status = true;

                logMMobileAcc = m_No;

            }
        }

        return status;
    }

    private boolean validatePassword() {
        boolean status = false;

        String m_Password = passwordAcc.getEditText().getText().toString().trim();

        if (m_Password.isEmpty()) {
            passwordAcc.setError("Password Field Cant be Empty");
            status = false;
        } else if (m_Password.length() <= 4) {
            passwordAcc.setError("Please Enter More than 4 character");
            status = false;
        } else if (!m_Password.isEmpty() && m_Password.length() >= 5) {
            passwordAcc.setError("");
            status = true;

            logMPasswordAcc = m_Password;

        }

        return status;
    }











    public void OnlickLoginMenuAccountFragment(View view) {
        if (!validateMobileNumber() | !validatePassword()) {

            return;
        } else {
            getLoginUserFromMenuAccountFragment();
        }


    }

    private void getLoginUserFromMenuAccountFragment() {
        ApiInterface api = APIClientToLogin.getApiInterfaceToLogin();

        SignInDTO signInDTO = new SignInDTO(logMMobileAcc, logMPasswordAcc);

        System.out.println(logMMobileAcc + "" + logMPasswordAcc);

        Call<SignedInJSONResponse> call = api.getLoginUser(signInDTO);

        System.out.println("I am here");

        call.enqueue(new Callback<SignedInJSONResponse>() {
            @Override
            public void onResponse(Call<SignedInJSONResponse> call, Response<SignedInJSONResponse> response) {
                System.out.println("I am inside");

                // JSONResponse jsonResponse=response.body();
                if (response.isSuccessful()) {
                    SignedInJSONResponse signedInJSONResponse=response.body();


                    if(signedInJSONResponse.getUser_ID()!=null) {


                        SharedPreferences current_User = getSharedPreferences("CURRENT_USER", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = current_User.edit();
                        editor.putString("CURRENTUSER", signedInJSONResponse.getUser_ID());
                        editor.commit();

                        System.out.println("Added current user"+current_User);

                        Toast.makeText(MenuAccountFragmentLoginActivity.this,signedInJSONResponse.getUser_ID(),Toast.LENGTH_SHORT).show();


                        Intent menuHomeActivity = new Intent(MenuAccountFragmentLoginActivity.this, HomeActivity.class);

                        menuHomeActivity.putExtra("CUR_USER_ID", signedInJSONResponse.getUser_ID());

                        startActivity(menuHomeActivity);
                    }


                    Toast.makeText(MenuAccountFragmentLoginActivity.this, "Sucesss", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MenuAccountFragmentLoginActivity.this, "Error", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<SignedInJSONResponse> call, Throwable t) {

            }
        });

    }
    private static long back_pressed;

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
