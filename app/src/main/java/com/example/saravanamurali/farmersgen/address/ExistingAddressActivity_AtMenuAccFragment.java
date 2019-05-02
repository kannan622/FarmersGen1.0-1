package com.example.saravanamurali.farmersgen.address;

import android.app.ProgressDialog;
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
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToGetExistingAddress;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExistingAddressActivity_AtMenuAccFragment extends AppCompatActivity {

    TextView flatNo;
    TextView streetName;
    TextView area;
    TextView city;
    TextView pincode;
    TextView state;

    TextView landMarkMenuAcc;
    TextView alternateMenuAcc;

    String addressIDAtMenuCart;

    Button changeAddressAtMenuCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_address__at_menu_acc_fragment);

        flatNo = (TextView) findViewById(R.id.flatNoRightMenuAcc);
        streetName = (TextView) findViewById(R.id.streetNameMenuAcc);
        area = (TextView) findViewById(R.id.areaMenuAcc);
        city = (TextView) findViewById(R.id.cityMenuAcc);
        pincode = (TextView) findViewById(R.id.pinCodeMenuAcc);
        state=(TextView)findViewById(R.id.stateMenuAcc);

        landMarkMenuAcc=(TextView)findViewById(R.id.landmarkViewMenuAcc);
        alternateMenuAcc=(TextView)findViewById(R.id.alternateMobileViewMenuAcc);

        changeAddressAtMenuCart = (Button) findViewById(R.id.changeAddressMenuAcc);

        //Get Delivery Address
        loadDeliverAddressAtMenuAccFragment();

        changeAddressAtMenuCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAddress_AtMenuCartActivity();
            }
        });



    }

    private void changeAddress_AtMenuCartActivity() {

        Intent updateAddress = new Intent(ExistingAddressActivity_AtMenuAccFragment.this, UpdateAddress_MenuAccFragment.class);
        updateAddress.putExtra("ADDRESSID_AT_MENUCART",addressIDAtMenuCart);
        startActivity(updateAddress);


    }

    private void loadDeliverAddressAtMenuAccFragment() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(ExistingAddressActivity_AtMenuAccFragment.this);
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

                    GetDeliveryAddressDTO getDeliveryAddressDTOAtMenuCart = response.body();


                    addressIDAtMenuCart = getDeliveryAddressDTOAtMenuCart.getAddressID();
                    flatNo.setText(getDeliveryAddressDTOAtMenuCart.getFlatNo());
                    streetName.setText(getDeliveryAddressDTOAtMenuCart.getStreetName());
                    area.setText(getDeliveryAddressDTOAtMenuCart.getArea());
                    city.setText(getDeliveryAddressDTOAtMenuCart.getCity());
                    pincode.setText(getDeliveryAddressDTOAtMenuCart.getPincode());
                    landMarkMenuAcc.setText(getDeliveryAddressDTOAtMenuCart.getLandmar());
                    alternateMenuAcc.setText(getDeliveryAddressDTOAtMenuCart.getAlter());


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
