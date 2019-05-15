package com.example.saravanamurali.farmersgen.signin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.fragment.ViewCartActivity;
import com.example.saravanamurali.farmersgen.models.CurrentUserDTO;
import com.example.saravanamurali.farmersgen.models.GetDeliveryAddressDTO;
import com.example.saravanamurali.farmersgen.models.SignInDTO;
import com.example.saravanamurali.farmersgen.models.SignedInJSONResponse;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToGetExistingAddress;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToLogin;
import com.example.saravanamurali.farmersgen.signup.RegisterUserAtCartActivity;
import com.example.saravanamurali.farmersgen.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityForViewCart extends AppCompatActivity {

    private TextInputLayout mobileNumberAtViewCart;
    private TextInputLayout passwordAtViewCart;
    private Button loginButtonAtViewCart;

    private String loginMobileAtViewCart = "";
    private String loginPasswordAtViewCart = "";

    private String logMMobileAtViewCart = "";
    private String logMPasswordAtViewCart = "";

    TextInputEditText editTextMAtViewCart;
    TextInputEditText editTextPAtViewCart;

    String addressIDForViewCartLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_for_view_cart);


        mobileNumberAtViewCart = (TextInputLayout) findViewById(R.id.loginMobileNoAtViewCart);
        passwordAtViewCart = (TextInputLayout) findViewById(R.id.loginPasswordAtViewCart);

        editTextMAtViewCart = findViewById(R.id.editTextLMAtViewCart);
        editTextPAtViewCart = findViewById(R.id.editTextLPAtViewCart);

        // getAddressIDAtViewCartLogin();


    }

    private void getAddressIDAtViewCartLogin() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(LoginActivityForViewCart.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api = APIClientToGetExistingAddress.getAPIInterfaceTOGetExistingAddress();

        SharedPreferences getCurrentUser = getSharedPreferences("CURRENT_USER", MODE_PRIVATE);

        String curUser = getCurrentUser.getString("CURRENTUSER", "NO_CURRENT_USER");
        CurrentUserDTO currentUserDTO = new CurrentUserDTO(curUser);

        Call<GetDeliveryAddressDTO> call = api.getExistingAddress(currentUserDTO);

        call.enqueue(new Callback<GetDeliveryAddressDTO>() {
            @Override
            public void onResponse(Call<GetDeliveryAddressDTO> call, Response<GetDeliveryAddressDTO> response) {
                if (response.isSuccessful()) {

                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }

                    GetDeliveryAddressDTO getDeliveryAddressDTO = response.body();

                    addressIDForViewCartLogin = getDeliveryAddressDTO.getAddressID();

                    SharedPreferences sharedPreferences = getSharedPreferences("ADDRESS_ID", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("ADDRESSID", addressIDForViewCartLogin);
                    editor.commit();

                    //System.out.println("CUreent user Address ID" + addressIDForViewCartLogin);

                }
            }

            @Override
            public void onFailure(Call<GetDeliveryAddressDTO> call, Throwable t) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

            }
        });

    }


    public void OnlickLoginAtViewCart(View view) {

        final String mobileNumberAtCart = editTextMAtViewCart.getText().toString();
        final String passwordAtCart = editTextPAtViewCart.getText().toString();

        if (!TextUtils.isEmpty(mobileNumberAtCart)) {
            if (Utils.isValidMobile(mobileNumberAtCart)) {
                if (!TextUtils.isEmpty(passwordAtCart)) {
                    getLoginUserAtViewCart(mobileNumberAtCart, passwordAtCart);
                } else {
                    Toast.makeText(this, "Enter Password", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Enter Valid Mobile number", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Enter Mobile number", Toast.LENGTH_LONG).show();
        }


    }

    private void getLoginUserAtViewCart(final String mobileNumberAtCart, final String passwordAtCart) {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(LoginActivityForViewCart.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api = APIClientToLogin.getApiInterfaceToLogin();

        SignInDTO signInDTO = new SignInDTO(mobileNumberAtCart, passwordAtCart);

        System.out.println(mobileNumberAtCart + "" + passwordAtCart);

        Call<SignedInJSONResponse> call = api.getLoginUser(signInDTO);

        call.enqueue(new Callback<SignedInJSONResponse>() {
            @Override
            public void onResponse(Call<SignedInJSONResponse> call, Response<SignedInJSONResponse> response) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

                SignedInJSONResponse signedInJSONResponse = response.body();

                if(signedInJSONResponse.getResponseCode()==200)

                {


                        SharedPreferences current_User = getSharedPreferences("CURRENT_USER", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = current_User.edit();
                        editor.putString("CURRENTUSER", signedInJSONResponse.getUser_ID());
                        editor.commit();

                        Intent openViewCartActivity = new Intent(LoginActivityForViewCart.this, ViewCartActivity.class);
                        openViewCartActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(openViewCartActivity);
                        finish();

                    }

                 else if(signedInJSONResponse.getResponseCode()==500)

                {
                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }
                    Toast.makeText(LoginActivityForViewCart.this, "You have Entered Wrong Mobile Number or Password!!!", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<SignedInJSONResponse> call, Throwable t) {

                Toast.makeText(LoginActivityForViewCart.this, "You have Entered Wrong Mobile Number or Password!!!", Toast.LENGTH_LONG).show();

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

            }
        });


    }

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent viewCartActivityFromLoginActivityForViewCart=new Intent(LoginActivityForViewCart.this,ViewCartActivity.class);
        startActivity(viewCartActivityFromLoginActivityForViewCart);
    }*/

    public void onClickLoginForgetPasswordAtViewCart(View view) {
        Intent loginForgetPasswordActivityAtViewCart = new Intent(LoginActivityForViewCart.this, LoginForgetPasswordActivityAtViewCart.class);
        startActivity(loginForgetPasswordActivityAtViewCart);
    }


    public void onClickSignupInLoginAtViewCartActivity(View view) {
        Intent intent = new Intent(LoginActivityForViewCart.this, RegisterUserAtCartActivity.class);
        startActivity(intent);
        finish();
    }
}
