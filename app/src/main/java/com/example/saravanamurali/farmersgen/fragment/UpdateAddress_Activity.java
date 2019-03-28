package com.example.saravanamurali.farmersgen.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.models.UpdateAddressDTO;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToUpdateAddress;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateAddress_Activity extends AppCompatActivity {

    private  String NO_CURRENT_USER="NO_CURRENT_USER";

    String addressID;

    private TextInputLayout mFlatNo;
    private TextInputLayout mStreetName;
    private TextInputLayout mArea;
    private TextInputLayout mCity;
    private TextInputLayout mPinCode;

    private String proceed_FlatNo="";
    private String proceed_StreetName="";
    private String proceed_Area="";
    private String proceed_City="";
    private String proceed_PinCode="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address_);

        Intent getAddressID=getIntent();

        addressID=getAddressID.getStringExtra("ADDRESSID");

        mFlatNo = findViewById(R.id.updateFlatNo);
        mStreetName=findViewById(R.id.updateStreetName);
        mArea=findViewById(R.id.updateArea);
        mCity=findViewById(R.id.updateCity);
        mPinCode=findViewById(R.id.upatePinCode);


    }

    private boolean validateFlatNo(){
        boolean status=false;

        String m_FlatNo = mFlatNo.getEditText().getText().toString().trim();

        if(m_FlatNo.isEmpty()){
            mFlatNo.setError("Flat # Can't be Empty");
            status=false;
        }
        else{
            mFlatNo.setError("");
            status = true;
            proceed_FlatNo = m_FlatNo;
        }

        return status;

    }

    private boolean validatStreetName(){
        boolean status=false;

        String m_StreetNAme = mStreetName.getEditText().getText().toString().trim();


        if(m_StreetNAme.isEmpty()){
            mStreetName.setError("Street Name Can't be Empty");
            status=false;
        }
        else{
            mStreetName.setError("");
            status=true;
            proceed_StreetName=m_StreetNAme;
        }
        return status;

    }

    private boolean validateArea(){
        boolean status=false;

        String m_Area = mArea.getEditText().getText().toString().trim();

        if(m_Area.isEmpty()){
            mArea.setError("Area Name Cant be Empty");
            status=false;
        }
        else{
            mArea.setError("");
            status=true;
            proceed_Area=m_Area;
        }

        return status;

    }

    private boolean validateCity(){

        boolean status=false;

        String m_City = mCity.getEditText().getText().toString().trim();

        if(m_City.isEmpty()){
            mCity.setError("City Cant be Empty");
            status=false;
        }

        else{
            mCity.setError("");
            status=true;
            proceed_City=m_City;
        }

        return status;


    }

    private boolean validatePinCode(){

        boolean status=false;

        String m_PinCode = mPinCode.getEditText().getText().toString().trim();

        if(m_PinCode.isEmpty()){
            mPinCode.setError("Pincode Can't be Empty");
            status=false;

        }
        else {
            mPinCode.setError("");
            status=true;
            proceed_PinCode=m_PinCode;
        }

        return status;


    }



    public void onClickUpdateAddress(View view) {


        if (!validateFlatNo() | !validatStreetName() | !validateArea() | !validateCity() | !validatePinCode()) {

            return;
        } else {

            updateAddressFromChangeAddressClick();
        }


    }


     private void updateAddressFromChangeAddressClick() {

        ApiInterface api=APIClientToUpdateAddress.getApiIterfaceToUpdateAddress();

         SharedPreferences getcurrentUser=getSharedPreferences("CURRENT_USER",MODE_PRIVATE);

         String currentUser=getcurrentUser.getString("CURRENTUSER",NO_CURRENT_USER);

        UpdateAddressDTO updateAddressDTO=new UpdateAddressDTO(proceed_FlatNo,proceed_StreetName,proceed_Area,proceed_City,proceed_PinCode,addressID,currentUser);
        Call<ResponseBody> call =api.updateAddress(updateAddressDTO);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()){

                    Intent CODDetails=new Intent(UpdateAddress_Activity.this,COD_Details_Activity.class);
                    startActivity(CODDetails);

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }





}
