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
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForNewPassAndConfirmPass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPassAndConfirmPass extends AppCompatActivity {

    String mobileNumber;

    TextInputLayout newPassword;
    TextInputLayout confirmPassword;
    Button button;

    private String mNewPassword = "";
    private String mConfirmPassword = "";

    private String logNewPassword = "";
    private String logConfirmPassword = "";

    private String nPass = "";
    private String cPass = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pass_and_confirm_pass);

        Intent getMobileNumber = getIntent();

        mobileNumber=getMobileNumber.getStringExtra("MOBILENO_FROM_OTP");

        newPassword = (TextInputLayout) findViewById(R.id.newPassword);
        confirmPassword = (TextInputLayout) findViewById(R.id.confirmPassword);
        button = (Button) findViewById(R.id.confirmButton);

    }

    public void onClickSibmit(View view) {

        String nPassword = newPassword.getEditText().getText().toString().trim();
        String cPassword = confirmPassword.getEditText().getText().toString().trim();

        int nP = nPassword.length();
        int cP = cPassword.length();

        if(nPassword.isEmpty() && cPassword.isEmpty()){
            Toast.makeText(NewPassAndConfirmPass.this, "Password field should not Empty", Toast.LENGTH_SHORT).show();
        }

        if (!nPassword.equals(cPassword)) {
            Toast.makeText(NewPassAndConfirmPass.this, "Password is not Match", Toast.LENGTH_SHORT).show();
        } else if (nP != cP) {
            Toast.makeText(NewPassAndConfirmPass.this, "Password length is not Match", Toast.LENGTH_SHORT).show();

        } else if (nP <= 4 && cP <= 4) {
            Toast.makeText(NewPassAndConfirmPass.this, "Please Enter More than 4 character", Toast.LENGTH_SHORT).show();

        } else if (nPassword.equals(cPassword) && nP == cP) {
            nPass = nPassword;
            cPass = cPassword;
            savePassword();
        }


    }

    private void savePassword() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(NewPassAndConfirmPass.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        Toast.makeText(NewPassAndConfirmPass.this, "Your are entered", Toast.LENGTH_SHORT).show();

        ApiInterface api = APIClientForNewPassAndConfirmPass.getApiInterfaceForNewPasswordAndConfirmPassword();

        NewPassAndConfirmPassDTO newPassAndConfirmPassDTO = new NewPassAndConfirmPassDTO(mobileNumber, nPass, cPass);

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

                        Intent loginActivity = new Intent(NewPassAndConfirmPass.this, LoginActivity.class);
                        startActivity(loginActivity);
                        finish();

                    }

                }
            }

            @Override
            public void onFailure(Call<JSONResponseForNPasswordAndCPasswrod> call, Throwable t) {

            }
        });


    }

    /*private boolean validateNewPassword() {
        boolean status = false;

        String m_Password = newPassword.getEditText().getText().toString().trim();

        if (m_Password.isEmpty()) {
            newPassword.setError("Password Field Cant be Empty");
            status = false;
        } else if (m_Password.length() <= 4) {
            newPassword.setError("Please Enter More than 4 character");
            status = false;
        } else if (!m_Password.isEmpty() && m_Password.length() >= 5) {
            newPassword.setError("");
            status = true;

            logNewPassword = m_Password;

        }

        return status;
    }

    private boolean validateConfirmPassword(){
        boolean status = false;

        String m_Password = confirmPassword.getEditText().getText().toString().trim();

        if (m_Password.isEmpty()) {
            newPassword.setError("Password Field Cant be Empty");
            status = false;
        } else if (m_Password.length() <= 4) {
            newPassword.setError("Please Enter More than 4 character");
            status = false;
        } else if (!m_Password.isEmpty() && m_Password.length() >= 5) {
            newPassword.setError("");
            status = true;

            logConfirmPassword = m_Password;

        }

        return status;

    }
*/
}
