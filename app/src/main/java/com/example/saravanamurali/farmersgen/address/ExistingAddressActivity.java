package com.example.saravanamurali.farmersgen.address;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.paymentgateway.PaymentGatewayActivity;
import com.example.saravanamurali.farmersgen.models.CurrentUserDTO;
import com.example.saravanamurali.farmersgen.models.GetDeliveryAddressDTO;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToGetExistingAddress;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExistingAddressActivity extends AppCompatActivity {

   // TextView deliveryNameRight;
    TextView flatNoRight;
    TextView streetNameRight;
    TextView areaRight;
    TextView cityRight;
    TextView pincodeRight;
    TextView state;

    TextView land_Mark;
    TextView alternate;

    String addressID;

    Button changeAddress;
    Button proceedtoPay;

    private Toolbar toolbar;

    String currentUserToGetExistingAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_address);


        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

//        getSupportActionBar().hide();




        //If user is logged in we need to show existing address in the Existing Address Activity

      //  deliveryNameRight = (TextView) findViewById(R.id.deliveryNameRight);
        flatNoRight = (TextView) findViewById(R.id.flatNoRight);
        streetNameRight = (TextView) findViewById(R.id.streetNameRight);
        areaRight = (TextView) findViewById(R.id.areaRight);
        cityRight = (TextView) findViewById(R.id.cityRight);
        pincodeRight = (TextView) findViewById(R.id.pinCode);
        state=(TextView)findViewById(R.id.state);
        land_Mark=(TextView)findViewById(R.id.landmarkView);
        alternate=(TextView)findViewById(R.id.alternateMobileView);

        changeAddress = (Button) findViewById(R.id.changeAddress);
        proceedtoPay = (Button) findViewById(R.id.proceedToPayFromViewCart);

        //Get Delivery Address
        loadDeliverAddress();

        changeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent updateAddress = new Intent(ExistingAddressActivity.this, UpdateAddress_Activity.class);
                updateAddress.putExtra("ADDRESSID",addressID);
                startActivity(updateAddress);
            }
        });

        proceedtoPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent paymentGateway=new Intent(ExistingAddressActivity.this,PaymentGatewayActivity.class);
                startActivity(paymentGateway);
            }
        });

    }


    //Get Registered User Delivery Address
    private void loadDeliverAddress() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(ExistingAddressActivity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        ApiInterface api = APIClientToGetExistingAddress.getAPIInterfaceTOGetExistingAddress();

        SharedPreferences getCurrentUser = getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        String curUser = getCurrentUser.getString("CURRENTUSER", "NO_CURRENT_USER");

        CurrentUserDTO currentUSR = new CurrentUserDTO(curUser);

        Call<GetDeliveryAddressDTO> call = api.getExistingAddress(currentUSR);

        call.enqueue(new Callback<GetDeliveryAddressDTO>() {
            @Override
            public void onResponse(Call<GetDeliveryAddressDTO> call, Response<GetDeliveryAddressDTO> response) {

                if (response.isSuccessful()) {
                     if(csprogress.isShowing()){
                        csprogress.dismiss();
                    }

                    GetDeliveryAddressDTO getDeliveryAddressDTO = response.body();


                    addressID = getDeliveryAddressDTO.getAddressID();
                    flatNoRight.setText(getDeliveryAddressDTO.getFlatNo());
                    streetNameRight.setText(getDeliveryAddressDTO.getStreetName());
                    areaRight.setText(getDeliveryAddressDTO.getArea());
                    cityRight.setText(getDeliveryAddressDTO.getCity());
                    pincodeRight.setText(getDeliveryAddressDTO.getPincode());
                    land_Mark.setText(getDeliveryAddressDTO.getLandmar());
                    alternate.setText(getDeliveryAddressDTO.getAlter());

                }

            }

            @Override
            public void onFailure(Call<GetDeliveryAddressDTO> call, Throwable t) {
                if(csprogress.isShowing()){
                    csprogress.dismiss();
                }

            }
        });


    }

}
