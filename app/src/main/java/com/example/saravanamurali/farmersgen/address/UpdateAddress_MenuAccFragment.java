package com.example.saravanamurali.farmersgen.address;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.models.UpdateAddressDTO;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToUpdateAddress;
import com.example.saravanamurali.farmersgen.tappedactivity.HomeActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateAddress_MenuAccFragment extends AppCompatActivity {

    String addressID_Menu;
    private String NO_CURRENT_USER = "NO_CURRENT_USER";
    private TextInputLayout update_mFlatNo;
    private TextInputLayout update_mStreetName;
    private TextInputLayout update_mArea;
    private TextInputLayout update_mCity;
    private TextInputLayout update_mPinCode;

    private TextInputLayout update_mLandmark;
    private TextInputLayout update_mAlternateMobileNumber;

    private String update_proceed_FlatNo = "";
    private String update_proceed_StreetName = "";
    private String update_proceed_Area = "";
    private String update_proceed_City = "";
    private String update_proceed_PinCode = "";

    private String update_proceed_Landmark = "";
    private String update_proceed_AlternateMobileNumber = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address__menu_acc_fragment);

        Intent addressID = getIntent();
        addressID_Menu = addressID.getStringExtra("ADDRESSID_AT_MENUCART");


        update_mFlatNo = findViewById(R.id.updateFlatNoMenuCart);
        update_mStreetName = findViewById(R.id.updateStreetNameMenuCart);
        update_mArea = findViewById(R.id.updateAreaMenuCart);
        update_mCity = findViewById(R.id.updateCityMenuCart);
        update_mPinCode = findViewById(R.id.upatePinCodeMenuCart);

        update_mLandmark = findViewById(R.id.landMarkMenuCart);
        update_mAlternateMobileNumber = findViewById(R.id.alternateMobileMenuCart);


    }

    private boolean validateAlternate_Mobile_ManageAddress() {
        boolean status = false;

        String m_Alternate_Mobile = update_mAlternateMobileNumber.getEditText().getText().toString().trim();

        if (m_Alternate_Mobile.isEmpty()) {
            update_mAlternateMobileNumber.setError("Flat # Can't be Empty");
            status = false;
        } else {
            update_mAlternateMobileNumber.setError("");
            status = true;
            update_proceed_AlternateMobileNumber = m_Alternate_Mobile;
        }

        return status;

    }


    private boolean validateLandMar_ManageAddress() {
        boolean status = false;

        String m_LandMark = update_mLandmark.getEditText().getText().toString().trim();

        if (m_LandMark.isEmpty()) {
            update_mLandmark.setError("Flat # Can't be Empty");
            status = false;
        } else {
            update_mLandmark.setError("");
            status = true;
            update_proceed_Landmark = m_LandMark;
        }

        return status;

    }





    private boolean validateFlatNo_ManageAddress() {
        boolean status = false;

        String m_FlatNo = update_mFlatNo.getEditText().getText().toString().trim();

        if (m_FlatNo.isEmpty()) {
            update_mFlatNo.setError("Flat # Can't be Empty");
            status = false;
        } else {
            update_mFlatNo.setError("");
            status = true;
            update_proceed_FlatNo = m_FlatNo;
        }

        return status;

    }


    private boolean validatStreetName_ManageAddress() {
        boolean status = false;

        String m_StreetNAme = update_mStreetName.getEditText().getText().toString().trim();


        if (m_StreetNAme.isEmpty()) {
            update_mStreetName.setError("Street Name Can't be Empty");
            status = false;
        } else {
            update_mStreetName.setError("");
            status = true;
            update_proceed_StreetName = m_StreetNAme;
        }
        return status;

    }

    private boolean validateArea_ManageAddress() {
        boolean status = false;

        String m_Area = update_mArea.getEditText().getText().toString().trim();

        if (m_Area.isEmpty()) {
            update_mArea.setError("Area Name Cant be Empty");
            status = false;
        } else {
            update_mArea.setError("");
            status = true;
            update_proceed_Area = m_Area;
        }

        return status;

    }

    private boolean validateCity_ManageAddress() {

        boolean status = false;

        String m_City = update_mCity.getEditText().getText().toString().trim();

        if (m_City.isEmpty()) {
            update_mCity.setError("City Cant be Empty");
            status = false;
        } else {
            update_mCity.setError("");
            status = true;
            update_proceed_City = m_City;
        }

        return status;


    }

    private boolean validatePinCode_ManageAddress() {

        boolean status = false;

        String m_PinCode = update_mPinCode.getEditText().getText().toString().trim();

        if (m_PinCode.isEmpty()) {
            update_mPinCode.setError("Pincode Can't be Empty");
            status = false;

        } else {
            update_mPinCode.setError("");
            status = true;
            update_proceed_PinCode = m_PinCode;
        }

        return status;


    }


    public void onClickUpdateAddressAtMenuCart(View view) {

        if (!validateFlatNo_ManageAddress() | !validatStreetName_ManageAddress() | !validateArea_ManageAddress() | !validateCity_ManageAddress() | !validatePinCode_ManageAddress()| !validateLandMar_ManageAddress() | !validateAlternate_Mobile_ManageAddress()  ) {

            return;
        } else {

            updateAddressAtManageAddress();
        }


    }

    private void updateAddressAtManageAddress() {

        ApiInterface api = APIClientToUpdateAddress.getApiIterfaceToUpdateAddress();

        SharedPreferences getcurrentUser = getSharedPreferences("CURRENT_USER", MODE_PRIVATE);

        String currentUserManageAddress = getcurrentUser.getString("CURRENTUSER", NO_CURRENT_USER);

        UpdateAddressDTO updateAddressDTO = new UpdateAddressDTO(update_proceed_FlatNo, update_proceed_StreetName, update_proceed_Area, update_proceed_City, update_proceed_PinCode, addressID_Menu, update_proceed_Landmark,update_proceed_AlternateMobileNumber,currentUserManageAddress);

        Call<ResponseBody> call = api.updateAddress(updateAddressDTO);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Intent menuAcc = new Intent(UpdateAddress_MenuAccFragment.this, HomeActivity.class);
                    startActivity(menuAcc);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }
}
