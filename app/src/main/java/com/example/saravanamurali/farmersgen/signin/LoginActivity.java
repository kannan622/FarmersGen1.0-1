package com.example.saravanamurali.farmersgen.signin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.saravanamurali.farmersgen.DummyActivity;
import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.models.FcmTokenDTO;
import com.example.saravanamurali.farmersgen.models.SignInDTO;
import com.example.saravanamurali.farmersgen.models.SignedInJSONResponse;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToLogin;
import com.example.saravanamurali.farmersgen.retrofitclient.ApiClientToSendFcmTokenToServer;
import com.example.saravanamurali.farmersgen.signup.OTPActForSuccRegistrationAtViewCart;
import com.example.saravanamurali.farmersgen.signup.SignupActivity;
import com.example.saravanamurali.farmersgen.tappedactivity.HomeActivity;
import com.example.saravanamurali.farmersgen.util.Network_config;
import com.example.saravanamurali.farmersgen.util.SessionManager;
import com.example.saravanamurali.farmersgen.util.Utils;

import java.util.regex.Pattern;

import io.fabric.sdk.android.Fabric;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText editTextM;
    TextInputEditText editTextP;
    Dialog dialog;
    private TextInputLayout mobileNumber;
    private TextInputLayout password;
    private Button loginButton;
    private String loginMobile = "";
    private String loginPassword = "";
    private String logMMobile = "";
    private String logMPassword = "";

    SessionManager sessionForPushNotification;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Fabric.with(this, new Crashlytics());


        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();
*/

        sessionForPushNotification=new SessionManager(LoginActivity.this);

        dialog = new Dialog(getApplicationContext());
        mobileNumber = (TextInputLayout) findViewById(R.id.loginMobileNo);
        password = (TextInputLayout) findViewById(R.id.loginPassword);
        loginButton = (Button) findViewById(R.id.btnLogin);

        editTextM = findViewById(R.id.editTextLM);
        editTextP = findViewById(R.id.editTextLP);

    }


    public void OnlickLogin(View view) {

        if (Network_config.is_Network_Connected_flag(getApplicationContext())) {


            final String mobileNumber = editTextM.getText().toString();
            final String password = editTextP.getText().toString();
            if (!TextUtils.isEmpty(mobileNumber)) {
                if (Utils.isValidMobile(mobileNumber)) {
                    if (!TextUtils.isEmpty(password)) {
                        getLoginUser(mobileNumber, password);
                    } else {
                        Toast.makeText(this, "Enter Password", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Enter Valid Mobile number", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Enter Mobile number", Toast.LENGTH_LONG).show();
            }
        /*if (!validateMobileNumber() && !validatePassword()) {

            return;
        } else {
            editTextM.setText("");
            editTextP.setText("");
            getLoginUser();
        }*/
        } else {
            Network_config.customAlert(dialog, getApplicationContext(), getResources().getString(R.string.app_name),
                    getResources().getString(R.string.connection_message));
        }

    }

    public void getLoginUser(final String logMMobile, final String logMPassword) {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(LoginActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.setCancelable(false);
        csprogress.setCanceledOnTouchOutside(false);
        csprogress.show();

        ApiInterface api = APIClientToLogin.getApiInterfaceToLogin();

        SignInDTO signInDTO = new SignInDTO(logMMobile, logMPassword);

        System.out.println(logMMobile + "" + logMPassword);

        Call<SignedInJSONResponse> call = api.getLoginUser(signInDTO);


        call.enqueue(new Callback<SignedInJSONResponse>() {
            @Override
            public void onResponse(Call<SignedInJSONResponse> call, Response<SignedInJSONResponse> response) {

                SignedInJSONResponse signedInJSONResponse = response.body();


                if (signedInJSONResponse.getResponseCode() == 200) {


                    SharedPreferences current_User = getSharedPreferences("CURRENT_USER", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = current_User.edit();
                    editor.putString("CURRENTUSER", signedInJSONResponse.getUser_ID());
                    editor.commit();

                    String fcm_Token=sessionForPushNotification.getFcmToken().get(SessionManager.KEY_FCM_TOKEN);

                    if(fcm_Token!=null){

                        System.out.println("TokenNew"+fcm_Token);
                        sendFCMTokenToServer(signedInJSONResponse.getUser_ID(),fcm_Token);

                    }
                    else if(fcm_Token==null){
                        System.out.println();
                    }

                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }


                    Intent menuHomeActivity = new Intent(LoginActivity.this, HomeActivity.class);

                    menuHomeActivity.putExtra("CUR_USER_ID", signedInJSONResponse.getUser_ID());

                    startActivity(menuHomeActivity);

                } else if (signedInJSONResponse.getResponseCode() == 500) {
                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }
                    Toast.makeText(LoginActivity.this, "You have Entered Wrong Mobile Number or Password!!!", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<SignedInJSONResponse> call, Throwable t) {
                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }
            }
        });


    }

    private void sendFCMTokenToServer(String userID,String fcm_token) {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(LoginActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.setCancelable(false);
        csprogress.setCanceledOnTouchOutside(false);
        csprogress.show();


        ApiInterface apiInterface=ApiClientToSendFcmTokenToServer.getApiInterfaceToSendFcmTokenToServer();

        FcmTokenDTO fcmTokenDTO=new FcmTokenDTO(userID,fcm_token);

        Call<ResponseBody> call=apiInterface.sendFcmTokenToServer(fcmTokenDTO);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

                System.out.println("Token Inserted Successfully");

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

            }
        });

    }

    public void signupLink(View view) {
        Intent signupIntent = new Intent(LoginActivity.this, DummyActivity.class);
        startActivity(signupIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

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
                logMMobile = m_No;
                status = true;

            }
        }

        return status;
    }

    private boolean validatePassword() {
        boolean status = false;

        String m_Password = editTextP.getText().toString().trim();

        if (m_Password.isEmpty()) {
            password.setError("Password Field Cant be Empty");
            status = false;
        } else if (m_Password.length() <= 4) {
            password.setError("Please Enter More than 4 character");
            status = false;
        } else if (!m_Password.isEmpty() && m_Password.length() >= 5) {
            password.setError("");
            logMPassword = m_Password;
            status = true;

        }

        return status;
    }


    public void onClickLoginForgetPassword(View view) {
        Intent loginForgetPasswordActivity = new Intent(LoginActivity.this, LoginForgetPasswordActivity.class);
        startActivity(loginForgetPasswordActivity);
    }

    public void onClickSignupInLogin(View view) {
        Intent signUpActivity = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(signUpActivity);

    }

    public void skip(View view) {
        Intent homeActivity = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(homeActivity);
    }
}
