package com.example.saravanamurali.farmersgen.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.models.CurrentUserDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseProfileEdit;
import com.example.saravanamurali.farmersgen.models.UpdateNameEmailDTO;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientForProfileEdit;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToUpdateNameAndEmail;
import com.example.saravanamurali.farmersgen.tappedactivity.HomeActivity;
import com.example.saravanamurali.farmersgen.util.Network_config;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileUpdateActivity extends AppCompatActivity {

    private final String NO_CURRENT_USER = "null";

    TextView t_MobileNo;
    TextInputLayout t_Name;
    TextInputLayout t_Email;

    TextInputEditText u_Name;
    TextInputEditText u_Email;

    Button updateProfileButton;

    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);

        dialog=new Dialog(ProfileUpdateActivity.this);

        t_MobileNo = (TextView) findViewById(R.id.fragment_update_profile_mobile_edt_input);
        t_Name = (TextInputLayout) findViewById(R.id.fragment_update_profile_name_edt_input);
        t_Email = (TextInputLayout) findViewById(R.id.fragment_update_profile_email_edt_input);

        u_Name = (TextInputEditText) findViewById(R.id.fragment_update_profile_name);
        u_Email = (TextInputEditText) findViewById(R.id.fragment_update_profile_email);

        updateProfileButton = (Button) findViewById(R.id.updateButton);

        profileEdit();

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = u_Name.getText().toString();
                String mail = u_Email.getText().toString();

                if (Network_config.is_Network_Connected_flag(ProfileUpdateActivity.this)) {

                    if (name.isEmpty() && mail.isEmpty()) {
                        Toast.makeText(ProfileUpdateActivity.this, "Name or Email Field Should not be Empty", Toast.LENGTH_LONG).show();
                    } else {

                        updateNameandMail();
                    }
                }

                else {
                    Network_config.customAlert(dialog,ProfileUpdateActivity.this , getResources().getString(R.string.app_name),
                            getResources().getString(R.string.connection_message));
                }
            }
        });


    }


    private void profileEdit() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(ProfileUpdateActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        SharedPreferences updateProfileForCurrentUser = getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        String updateProfile = updateProfileForCurrentUser.getString("CURRENTUSER", NO_CURRENT_USER);

        ApiInterface api = APIClientForProfileEdit.getApiInterfaceToEditProfile();

        CurrentUserDTO currentUserDTO = new CurrentUserDTO(updateProfile);

        Call<JSONResponseProfileEdit> call = api.editProfile(currentUserDTO);

        call.enqueue(new Callback<JSONResponseProfileEdit>() {
            @Override
            public void onResponse(Call<JSONResponseProfileEdit> call, Response<JSONResponseProfileEdit> response) {

                if (response.isSuccessful()) {

                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }

                    JSONResponseProfileEdit jsonResponseProfileEdit = response.body();

                    t_MobileNo.setText(jsonResponseProfileEdit.getProfileMobileNo());
                    u_Name.setText(jsonResponseProfileEdit.getProfileName());
                    u_Email.setText(jsonResponseProfileEdit.getProfileEmail());

                }

            }

            @Override
            public void onFailure(Call<JSONResponseProfileEdit> call, Throwable t) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

                Toast.makeText(ProfileUpdateActivity.this, "Cant Fetch data now!", Toast.LENGTH_SHORT).show();

            }
        });


    }


    private void updateNameandMail() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(ProfileUpdateActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        UpdateNameEmailDTO updateNameEmailDTO = new UpdateNameEmailDTO();


        updateNameEmailDTO.setU_name(u_Name.getText().toString());
        updateNameEmailDTO.setU_email(u_Email.getText().toString());
        SharedPreferences updateProfileForCurrentUser = getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        String updateUserID = updateProfileForCurrentUser.getString("CURRENTUSER", NO_CURRENT_USER);

        UpdateNameEmailDTO updateN = new UpdateNameEmailDTO(updateNameEmailDTO.getU_name(), updateNameEmailDTO.getU_email(), updateUserID);

        ApiInterface api = APIClientToUpdateNameAndEmail.getApiIterfaceToUpdateNameAndMail();

        Call<ResponseBody> call = api.getUpdateNameAndMail(updateN);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }

                    Toast.makeText(ProfileUpdateActivity.this, "Profile Updated Successfully", Toast.LENGTH_LONG).show();
                    Intent redirectToHomeActivity = new Intent(ProfileUpdateActivity.this, HomeActivity.class);
                    startActivity(redirectToHomeActivity);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }
                Toast.makeText(ProfileUpdateActivity.this, "Check Internet Connection", Toast.LENGTH_LONG).show();

            }
        });


    }

}

