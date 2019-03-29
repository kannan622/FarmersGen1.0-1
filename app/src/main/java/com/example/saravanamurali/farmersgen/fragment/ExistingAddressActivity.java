package com.example.saravanamurali.farmersgen.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.models.CurrentUserDTO;
import com.example.saravanamurali.farmersgen.models.GetDeliveryAddressDTO;
import com.example.saravanamurali.farmersgen.models.GetExistingAddressDTO;
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

    String addressID;

    Button changeAddress;
    Button proceedtoPay;

    String currentUserToGetExistingAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_address);


        //If user is logged in we need to show existing address in the Existing Address Activity

      //  deliveryNameRight = (TextView) findViewById(R.id.deliveryNameRight);
        flatNoRight = (TextView) findViewById(R.id.flatNoRight);
        streetNameRight = (TextView) findViewById(R.id.streetNameRight);
        areaRight = (TextView) findViewById(R.id.areaRight);
        cityRight = (TextView) findViewById(R.id.cityRight);
        pincodeRight = (TextView) findViewById(R.id.pinCode);
        state=(TextView)findViewById(R.id.state);

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

        ApiInterface api = APIClientToGetExistingAddress.getAPIInterfaceTOGetExistingAddress();

        SharedPreferences getCurrentUser = getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        String curUser = getCurrentUser.getString("CURRENTUSER", "NO_CURRENT_USER");

        CurrentUserDTO currentUSR = new CurrentUserDTO(curUser);

        Call<GetDeliveryAddressDTO> call = api.getExistingAddress(currentUSR);

        call.enqueue(new Callback<GetDeliveryAddressDTO>() {
            @Override
            public void onResponse(Call<GetDeliveryAddressDTO> call, Response<GetDeliveryAddressDTO> response) {

                if (response.isSuccessful()) {

                    GetDeliveryAddressDTO getDeliveryAddressDTO = response.body();


                    addressID = getDeliveryAddressDTO.getAddressID();
                    flatNoRight.setText(getDeliveryAddressDTO.getFlatNo());
                    streetNameRight.setText(getDeliveryAddressDTO.getStreetName());
                    areaRight.setText(getDeliveryAddressDTO.getArea());
                    cityRight.setText(getDeliveryAddressDTO.getCity());
                    pincodeRight.setText(getDeliveryAddressDTO.getPincode());

                }

            }

            @Override
            public void onFailure(Call<GetDeliveryAddressDTO> call, Throwable t) {

            }
        });


    }

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent viewCartActivity=new Intent(ExistingAddressActivity.this,ViewCartActivity.class);
        startActivity(viewCartActivity);
    }*/
}