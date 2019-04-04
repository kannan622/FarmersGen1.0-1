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

    private TextInputLayout uFlatNo;
    private TextInputLayout uStreetName;
    private TextInputLayout uArea;
    private TextInputLayout uCity;
    private TextInputLayout uPinCode;

    private TextInputLayout uLandMark;
    private TextInputLayout uAlternateMobile;


    private String proceed_FlatNo="";
    private String proceed_StreetName="";
    private String proceed_Area="";
    private String proceed_City="";
    private String proceed_PinCode="";

    private String proceed_LandMark="";
    private String proceed_Alternate_Mobile_Number="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address_);

        Intent getAddressID=getIntent();

        addressID=getAddressID.getStringExtra("ADDRESSID");

        uFlatNo = findViewById(R.id.updateFlatNo);
        uStreetName=findViewById(R.id.updateStreetName);
        uArea=findViewById(R.id.updateArea);
        uCity=findViewById(R.id.updateCity);
        uPinCode=findViewById(R.id.upatePinCode);

        uLandMark=findViewById(R.id.updateLandMark);
        uAlternateMobile=findViewById(R.id.updateAlternateMobile);


    }

    private boolean validateLandMark(){
        boolean status=false;

        String m_LandMark = uLandMark.getEditText().getText().toString().trim();

        if(m_LandMark.isEmpty()){
            uLandMark.setError("Land Mark Can't be Empty");
            status=false;
        }
        else{
            uLandMark.setError("");
            status = true;
            proceed_LandMark = m_LandMark;
        }

        return status;

    }

    private boolean validateAlterMobileNumber(){
        boolean status=false;

        String m_AlternateMobileNumber = uAlternateMobile.getEditText().getText().toString().trim();

        if(m_AlternateMobileNumber.isEmpty()){
            uAlternateMobile.setError("Alternate mobile # Can't be Empty");
            status=false;
        }
        else{
            uAlternateMobile.setError("");
            status = true;
            proceed_Alternate_Mobile_Number = m_AlternateMobileNumber;
        }

        return status;

    }







    private boolean validateFlatNo(){
        boolean status=false;

        String m_FlatNo = uFlatNo.getEditText().getText().toString().trim();

        if(m_FlatNo.isEmpty()){
            uFlatNo.setError("Flat # Can't be Empty");
            status=false;
        }
        else{
            uFlatNo.setError("");
            status = true;
            proceed_FlatNo = m_FlatNo;
        }

        return status;

    }

    private boolean validatStreetName(){
        boolean status=false;

        String m_StreetNAme = uStreetName.getEditText().getText().toString().trim();


        if(m_StreetNAme.isEmpty()){
            uStreetName.setError("Street Name Can't be Empty");
            status=false;
        }
        else{
            uStreetName.setError("");
            status=true;
            proceed_StreetName=m_StreetNAme;
        }
        return status;

    }

    private boolean validateArea(){
        boolean status=false;

        String m_Area = uArea.getEditText().getText().toString().trim();

        if(m_Area.isEmpty()){
            uArea.setError("Area Name Cant be Empty");
            status=false;
        }
        else{
            uArea.setError("");
            status=true;
            proceed_Area=m_Area;
        }

        return status;

    }

    private boolean validateCity(){

        boolean status=false;

        String m_City = uCity.getEditText().getText().toString().trim();

        if(m_City.isEmpty()){
            uCity.setError("City Cant be Empty");
            status=false;
        }

        else{
            uCity.setError("");
            status=true;
            proceed_City=m_City;
        }

        return status;


    }

    private boolean validatePinCode(){

        boolean status=false;

        String m_PinCode = uPinCode.getEditText().getText().toString().trim();

        if(m_PinCode.isEmpty()){
            uPinCode.setError("Pincode Can't be Empty");
            status=false;

        }
        else {
            uPinCode.setError("");
            status=true;
            proceed_PinCode=m_PinCode;
        }

        return status;


    }



    public void onClickUpdateAddress(View view) {


        if (!validateFlatNo() | !validatStreetName() | !validateArea() | !validateCity() | !validatePinCode() | !validateLandMark() | !validateAlterMobileNumber() ) {

            return;
        } else {

            updateAddressFromChangeAddressClick();
        }


    }


     private void updateAddressFromChangeAddressClick() {

        ApiInterface api=APIClientToUpdateAddress.getApiIterfaceToUpdateAddress();

         SharedPreferences getcurrentUser=getSharedPreferences("CURRENT_USER",MODE_PRIVATE);

         String currentUser=getcurrentUser.getString("CURRENTUSER",NO_CURRENT_USER);

        UpdateAddressDTO updateAddressDTO=new UpdateAddressDTO(proceed_FlatNo,proceed_StreetName,proceed_Area,proceed_City,proceed_PinCode,addressID,proceed_LandMark,proceed_Alternate_Mobile_Number,currentUser);
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
