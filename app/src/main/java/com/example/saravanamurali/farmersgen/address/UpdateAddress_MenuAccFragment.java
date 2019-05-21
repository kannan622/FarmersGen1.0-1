package com.example.saravanamurali.farmersgen.address;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.models.UpdateAddressDTO;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToUpdateAddress;
import com.example.saravanamurali.farmersgen.tappedactivity.HomeActivity;
import com.example.saravanamurali.farmersgen.util.FavStatus;
import com.example.saravanamurali.farmersgen.util.Network_config;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateAddress_MenuAccFragment extends AppCompatActivity implements View.OnClickListener {

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

    private Button myLocation_Button_Menu;
    private LocationManager locationManager;

    private Double lattitude, longitude;

    private Geocoder geocoder;
    private List<Address> geoAddresses;
    private TextInputEditText geoSetFlatNo;
    private TextInputEditText geoSetStreetName;
    private TextInputEditText geoSetArea;
    private TextInputEditText geoSetCity;
    private TextInputEditText geoSetPincode;

    private TextView showAddresMenu;

    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address__menu_acc_fragment);

        Intent addressID = getIntent();
        addressID_Menu = addressID.getStringExtra("ADDRESSID_AT_MENUCART");

        dialog=new Dialog(UpdateAddress_MenuAccFragment.this);


        update_mFlatNo = findViewById(R.id.updateFlatNoMenuCart);
        update_mStreetName = findViewById(R.id.updateStreetNameMenuCart);
        update_mArea = findViewById(R.id.updateAreaMenuCart);
        update_mCity = findViewById(R.id.updateCityMenuCart);
        update_mPinCode = findViewById(R.id.upatePinCodeMenuCart);

        update_mLandmark = findViewById(R.id.landMarkMenuCart);
        update_mAlternateMobileNumber = findViewById(R.id.alternateMobileMenuCart);

        geoSetPincode = (TextInputEditText) findViewById(R.id.pinCodeMenu);
        geoSetCity = (TextInputEditText) findViewById(R.id.cityMenu);
        geoSetArea = (TextInputEditText) findViewById(R.id.areaMenu);
        geoSetStreetName = (TextInputEditText) findViewById(R.id.streetNameMenu);
        geoSetFlatNo = (TextInputEditText) findViewById(R.id.flatNoMenu);

        showAddresMenu=(TextView)findViewById(R.id.showAddressMenu);

        myLocation_Button_Menu = (Button) findViewById(R.id.myLocationButtonInMenu);

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, FavStatus.REQUEST_LOCATION);

        myLocation_Button_Menu.setOnClickListener(this);


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

        if (!validateFlatNo_ManageAddress() | !validatStreetName_ManageAddress() | !validateArea_ManageAddress() | !validateCity_ManageAddress() | !validatePinCode_ManageAddress() | !validateLandMar_ManageAddress() | !validateAlternate_Mobile_ManageAddress()) {

            return;
        } else {

            if (Network_config.is_Network_Connected_flag(UpdateAddress_MenuAccFragment.this)) {

                updateAddressAtManageAddress();

            }

            else {
                Network_config.customAlert(dialog, UpdateAddress_MenuAccFragment.this, getResources().getString(R.string.app_name),
                        getResources().getString(R.string.connection_message));
            }
        }


    }

    private void updateAddressAtManageAddress() {

        ApiInterface api = APIClientToUpdateAddress.getApiIterfaceToUpdateAddress();

        SharedPreferences getcurrentUser = getSharedPreferences("CURRENT_USER", MODE_PRIVATE);

        String currentUserManageAddress = getcurrentUser.getString("CURRENTUSER", NO_CURRENT_USER);

        UpdateAddressDTO updateAddressDTO = new UpdateAddressDTO(update_proceed_FlatNo, update_proceed_StreetName, update_proceed_Area, update_proceed_City, update_proceed_PinCode, addressID_Menu, update_proceed_Landmark, update_proceed_AlternateMobileNumber, currentUserManageAddress);

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

    @Override
    public void onClick(View v) {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (Network_config.is_Network_Connected_flag(UpdateAddress_MenuAccFragment.this)) {
                getLocation();
            }
            else {
                Network_config.customAlert(dialog, UpdateAddress_MenuAccFragment.this, getResources().getString(R.string.app_name),
                        getResources().getString(R.string.connection_message));
            }
        }

    }

    private void getLocation() {

        geocoder = new Geocoder(this, Locale.getDefault());

        if (ActivityCompat.checkSelfPermission(UpdateAddress_MenuAccFragment.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (UpdateAddress_MenuAccFragment.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(UpdateAddress_MenuAccFragment.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FavStatus.REQUEST_LOCATION);

        } else {

            final ProgressDialog csprogress;
            csprogress = new ProgressDialog(UpdateAddress_MenuAccFragment.this);
            csprogress.setMessage("Loading...");
            csprogress.show();
            csprogress.setCanceledOnTouchOutside(false);


            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (location != null) {
                lattitude = location.getLatitude();
                longitude = location.getLongitude();
                /*lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
*/

            } else if (location1 != null) {
                lattitude = location1.getLatitude();
                longitude = location1.getLongitude();
                /*lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
*/


            } else if (location2 != null) {
                lattitude = location2.getLatitude();
                longitude = location2.getLongitude();
                /*lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
*/

            } else {
                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

                Toast.makeText(this, "Unble to Trace your location", Toast.LENGTH_SHORT).show();

            }

            try {
                geoAddresses = geocoder.getFromLocation(lattitude, longitude, FavStatus.REQUEST_LOCATION);

                if(geoAddresses==null){
                    getLocation();
                }

                else {

                    String address = geoAddresses.get(0).getAddressLine(0);
                    String area = geoAddresses.get(0).getLocality();
                    String city = geoAddresses.get(0).getAdminArea();
                    String country = geoAddresses.get(0).getCountryName();
                    String postalCode = geoAddresses.get(0).getPostalCode();
                    String subAdminArea = geoAddresses.get(0).getSubAdminArea();
                    String subLocality = geoAddresses.get(0).getSubLocality();
                    String premises = geoAddresses.get(0).getPremises();
                    String addressLine = geoAddresses.get(0).getAddressLine(0);

                    // System.out.println("Address"+address+"  "+"area"+area+"  "+"city"+city+"  "+"country"+country+"  "+"postalCode"+postalCode);
                /*System.out.println(address);
                System.out.println(area);
                System.out.println(city);
                System.out.println(country);
                System.out.println(postalCode);
                System.out.println(subAdminArea);
                System.out.println(subLocality);
                System.out.println(premises);
                System.out.println(addressLine);
*/

                    showAddresMenu.setVisibility(View.VISIBLE);
                    showAddresMenu.setText(address + " " + area + " " + city + " " + postalCode);

                /*String doorNo = address;
                String[] d_No = doorNo.split(",", 2);
                geoSetFlatNo.setText(d_No[0]);*/

                    geoSetArea.setText(subLocality);
                    geoSetCity.setText(area);
                    geoSetPincode.setText(postalCode);

                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }

                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

}
