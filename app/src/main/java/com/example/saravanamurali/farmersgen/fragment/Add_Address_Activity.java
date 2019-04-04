package com.example.saravanamurali.farmersgen.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.models.ADDAddessDTO;
import com.example.saravanamurali.farmersgen.models.JSONResponseADDAddress;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToADDAddress;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_Address_Activity extends AppCompatActivity {

    private  String NO_CURRENT_USER="NO_CURRENT_USER";

    /*TextView tvDeliverAddress;
    TextView flatNo;
    TextView StreetName;
    TextView Area;
    TextView City;
    TextView PinCode;

    EditText eflatNo;
    EditText eStreetName;
    EditText eArea;
    EditText eCity;
    EditText ePincode;
*/

    private TextInputLayout mFlatNo;
    private TextInputLayout mStreetName;
    private TextInputLayout mArea;
    private TextInputLayout mCity;
    private TextInputLayout mPinCode;
    private TextInputLayout mLandMark;
    private TextInputLayout mAlternateMobile;

    private String proceed_FlatNo="";
    private String proceed_StreetName="";
    private String proceed_Area="";
    private String proceed_City="";
    private String proceed_PinCode="";
    private String proceed_LandMark="";
    private String proceed_AlternateMobileNumber="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__address_);

        //tvDeliverAddress=(TextView)findViewById(R.id.tVDeliveryAddress);

        mFlatNo = findViewById(R.id.flatNo);
        mStreetName=findViewById(R.id.streetName);
        mArea=findViewById(R.id.area);
        mCity=findViewById(R.id.city);
        mPinCode=findViewById(R.id.pinCode);
        mLandMark=findViewById(R.id.landMark);
        mAlternateMobile=findViewById(R.id.alternateMobile);



    }

    public void onClickProceed(View view) {


        if (!validateFlatNo() | !validatStreetName() | !validateArea() | !validateCity() | !validatePinCode() | !validateLandMark() | !validateAlternateMobileNumber()) {
            return;
        } else {

            proceed();
        }

    }

    private void proceed() {
        Toast.makeText(Add_Address_Activity.this,"Proceed To Pay,",Toast.LENGTH_LONG).show();

        ApiInterface api=APIClientToADDAddress.getAPIInterfaceForADDAddress();

        SharedPreferences sharedPreferences=getSharedPreferences("CURRENT_USER",MODE_PRIVATE);
        String currentUserForADD_Address=sharedPreferences.getString("CURRENTUSER","NO_CURRENT_USER");



        ADDAddessDTO addAddessDTO=new ADDAddessDTO(proceed_FlatNo,proceed_StreetName,proceed_Area,proceed_City,proceed_PinCode,proceed_LandMark,proceed_AlternateMobileNumber,currentUserForADD_Address);

        Call<JSONResponseADDAddress> call =api.addAddress(addAddessDTO);

        call.enqueue(new Callback<JSONResponseADDAddress>() {
            @Override
            public void onResponse(Call<JSONResponseADDAddress> call, Response<JSONResponseADDAddress> response) {
                if (response.isSuccessful()){
                    JSONResponseADDAddress jsonResponseADDAddress= response.body();

                    if(jsonResponseADDAddress.getResultStatus()==200){

                        Intent codIntent=new Intent(Add_Address_Activity.this,PaymentGatewayActivity.class);
                        startActivity(codIntent);
                        finish();

                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponseADDAddress> call, Throwable t) {

            }
        });


    }

    private boolean validateFlatNo(){
        boolean status=false;

        String m_FlatNo = mFlatNo.getEditText().getText().toString().trim();

        if(m_FlatNo.isEmpty()){
            mFlatNo.setError("Flat # Can't be Empty");
        }
        else{
            mFlatNo.setError("");
            status = true;
            proceed_FlatNo = m_FlatNo;
        }

        return true;

    }

    boolean validatStreetName(){
        boolean status=false;

        String m_StreetNAme = mStreetName.getEditText().getText().toString().trim();


        if(m_StreetNAme.isEmpty()){
            mStreetName.setError("Street Name Can't be Empty");
        }
        else{
            mStreetName.setError("");
            status=true;
            proceed_StreetName=m_StreetNAme;
        }
        return true;

    }

    boolean validateArea(){
        boolean status=false;

        String m_Area = mArea.getEditText().getText().toString().trim();

        if(m_Area.isEmpty()){
            mArea.setError("Area Name Cant be Empty");
        }
        else{
            mArea.setError("");
            status=true;
            proceed_Area=m_Area;
        }

        return true;

    }

   boolean validateCity(){

        boolean status=false;

       String m_City = mCity.getEditText().getText().toString().trim();

       if(m_City.isEmpty()){
           mCity.setError("City Cant be Empty");
       }

       else{
           mCity.setError("");
           status=true;
           proceed_City=m_City;
       }

        return true;


    }

    boolean validatePinCode(){

        boolean status=false;

        String m_PinCode = mPinCode.getEditText().getText().toString().trim();

        if(m_PinCode.isEmpty()){
            mPinCode.setError("Pincode Can't be Empty");

        }
        else {
            mPinCode.setError("");
            status=true;
            proceed_PinCode=m_PinCode;
        }

        return true;
    }

    boolean validateLandMark(){

        boolean status=false;

        String m_LandMark = mLandMark.getEditText().getText().toString().trim();

        if(m_LandMark.isEmpty()){
            mLandMark.setError("LandMark Can't be Empty");

        }
        else {
            mLandMark.setError("");
            status=true;
            proceed_LandMark=m_LandMark;
        }

        return true;
    }

    boolean validateAlternateMobileNumber(){

        boolean status=false;

        String m_AlternateMobile = mAlternateMobile.getEditText().getText().toString().trim();

        if(m_AlternateMobile.isEmpty()){
            mAlternateMobile.setError("Alternate Mobile Number Can't be Empty");

        }
        else {
            mAlternateMobile.setError("");
            status=true;
            proceed_AlternateMobileNumber=m_AlternateMobile;
        }

        return true;
    }



}
