package com.example.saravanamurali.farmersgen.address;

import android.Manifest;
import android.app.AlertDialog;
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
import android.provider.Settings;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.cancelorder.CancelOrderActivity;
import com.example.saravanamurali.farmersgen.models.GetGeoAddress;
import com.example.saravanamurali.farmersgen.paymentgateway.PaymentGatewayActivity;
import com.example.saravanamurali.farmersgen.models.UpdateAddressDTO;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToUpdateAddress;
import com.example.saravanamurali.farmersgen.util.FavStatus;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateAddress_Activity extends AppCompatActivity implements View.OnClickListener {

    private String NO_CURRENT_USER = "NO_CURRENT_USER";

    String addressID;

    private TextInputLayout uFlatNo;
    private TextInputLayout uStreetName;
    private TextInputLayout uArea;
    private TextInputLayout uCity;
    private TextInputLayout uPinCode;
    private TextInputLayout uLandMark;
    private TextInputLayout uAlternateMobile;


    private String proceed_FlatNo = "";
    private String proceed_StreetName = "";
    private String proceed_Area = "";
    private String proceed_City = "";
    private String proceed_PinCode = "";

    private String proceed_LandMark = "";
    private String proceed_Alternate_Mobile_Number = "";

    private Button myLocation_Button;
    private LocationManager locationManager;

    private Double lattitude, longitude;

    private Geocoder geocoder;
    private List<Address> geoAddresses;
    private TextInputEditText geoSetFlatNo;
    private TextInputEditText geoSetStreetName;
    private TextInputEditText geoSetArea;
    private TextInputEditText geoSetCity;
    private TextInputEditText geoSetPincode;

    private TextView showAddress;

    Toolbar updateAddressToolBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address_);

        Intent getAddressID = getIntent();

        addressID = getAddressID.getStringExtra("ADDRESSID");

        uFlatNo = findViewById(R.id.updateFlatNo);
        uStreetName = findViewById(R.id.updateStreetName);
        uArea = findViewById(R.id.updateArea);
        uCity = findViewById(R.id.updateCity);
        uPinCode = findViewById(R.id.upatePinCode);

        geoSetPincode = (TextInputEditText) findViewById(R.id.getSetPinCode);
        geoSetCity = (TextInputEditText) findViewById(R.id.getSetCity);
        geoSetArea = (TextInputEditText) findViewById(R.id.getSetArea);
        geoSetStreetName = (TextInputEditText) findViewById(R.id.getSetStreetName);
        geoSetFlatNo = (TextInputEditText) findViewById(R.id.getSetFlat);

        showAddress = (TextView) findViewById(R.id.showAddress);

        uLandMark = findViewById(R.id.updateLandMark);
        uAlternateMobile = findViewById(R.id.updateAlternateMobile);

        myLocation_Button = (Button) findViewById(R.id.myLocationButton);
        updateAddressToolBar = (Toolbar) findViewById(R.id.updateAddressTootlBar);
        setSupportActionBar(updateAddressToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, FavStatus.REQUEST_LOCATION);

        myLocation_Button.setOnClickListener(this);
    }


    public void onClickUpdateAddress(View view) {


        if (!validateFlatNo() | !validatStreetName() | !validateArea() | !validateCity() | !validatePinCode() | !validateLandMark() | !validateAlterMobileNumber()) {

            return;
        } else {

            updateAddressFromChangeAddressClick();
        }


    }


    private void updateAddressFromChangeAddressClick() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(UpdateAddress_Activity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);

        ApiInterface api = APIClientToUpdateAddress.getApiIterfaceToUpdateAddress();

        SharedPreferences getcurrentUser = getSharedPreferences("CURRENT_USER", MODE_PRIVATE);

        String currentUser = getcurrentUser.getString("CURRENTUSER", NO_CURRENT_USER);

        UpdateAddressDTO updateAddressDTO = new UpdateAddressDTO(proceed_FlatNo, proceed_StreetName, proceed_Area, proceed_City, proceed_PinCode, addressID, proceed_LandMark, proceed_Alternate_Mobile_Number, currentUser);
        Call<ResponseBody> call = api.updateAddress(updateAddressDTO);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {

                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }

                    Intent paymentGateActivity = new Intent(UpdateAddress_Activity.this, PaymentGatewayActivity.class);
                    startActivity(paymentGateActivity);

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

            }
        });

    }


    @Override
    public void onClick(View v) {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        }
    }

    private void getLocation() {
        geocoder = new Geocoder(this, Locale.getDefault());

        if (ActivityCompat.checkSelfPermission(UpdateAddress_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (UpdateAddress_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(UpdateAddress_Activity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FavStatus.REQUEST_LOCATION);

        } else {

            final ProgressDialog csprogress;
            csprogress = new ProgressDialog(UpdateAddress_Activity.this);
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
                longitude = String.valueOf(longi);*/


            } else if (location1 != null) {
                lattitude = location1.getLatitude();
                longitude = location1.getLongitude();
                /*lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);*/


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

                if (lattitude != null && longitude != null) {
                    geoAddresses = geocoder.getFromLocation(lattitude, longitude, FavStatus.REQUEST_LOCATION);


                    String address = geoAddresses.get(0).getAddressLine(0);
                    String area = geoAddresses.get(0).getLocality();
                    String city = geoAddresses.get(0).getAdminArea();
                    String country = geoAddresses.get(0).getCountryName();
                    String postalCode = geoAddresses.get(0).getPostalCode();
                    String subAdminArea = geoAddresses.get(0).getSubAdminArea();
                    String subLocality = geoAddresses.get(0).getSubLocality();
                    String premises = geoAddresses.get(0).getPremises();
                    String addressLine = geoAddresses.get(0).getAddressLine(0);

                    showAddress.setVisibility(View.VISIBLE);
                    showAddress.setText(address + " " + area + " " + city + " " + postalCode);

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
                    String doorNo = address;
                    String[] d_No = doorNo.split(",", 2);
                    if (d_No.length >= 7) {
                        geoSetFlatNo.setText("");
                    } else {
                        geoSetFlatNo.setText(d_No[0]);
                    }

                    geoSetArea.setText(subLocality);
                    geoSetCity.setText(area);
                    geoSetPincode.setText(postalCode);

                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }


                } else {
                    Toast.makeText(UpdateAddress_Activity.this, "Unble to Fetch your location", Toast.LENGTH_LONG).show();
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
                .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private boolean validateLandMark() {
        boolean status = false;

        String m_LandMark = uLandMark.getEditText().getText().toString().trim();

        if (m_LandMark.isEmpty()) {
            uLandMark.setError("Land Mark Can't be Empty");
            status = false;
        } else {
            uLandMark.setError("");
            status = true;
            proceed_LandMark = m_LandMark;
        }

        return status;

    }

    private boolean validateAlterMobileNumber() {
        boolean status = false;

        String m_AlternateMobileNumber = uAlternateMobile.getEditText().getText().toString().trim();

        if (m_AlternateMobileNumber.isEmpty()) {
            uAlternateMobile.setError("Alternate mobile # Can't be Empty");
            status = false;
        } else {
            uAlternateMobile.setError("");
            status = true;
            proceed_Alternate_Mobile_Number = m_AlternateMobileNumber;
        }

        return status;

    }

    private boolean validateFlatNo() {
        boolean status = false;

        String m_FlatNo = uFlatNo.getEditText().getText().toString().trim();

        if (m_FlatNo.isEmpty()) {
            uFlatNo.setError("Flat # Can't be Empty");
            status = false;
        } else {
            uFlatNo.setError("");
            status = true;
            proceed_FlatNo = m_FlatNo;
        }

        return status;

    }

    private boolean validatStreetName() {
        boolean status = false;

        String m_StreetNAme = uStreetName.getEditText().getText().toString().trim();


        if (m_StreetNAme.isEmpty()) {
            uStreetName.setError("Street Name Can't be Empty");
            status = false;
        } else {
            uStreetName.setError("");
            status = true;
            proceed_StreetName = m_StreetNAme;
        }
        return status;

    }

    private boolean validateArea() {
        boolean status = false;

        String m_Area = uArea.getEditText().getText().toString().trim();

        if (m_Area.isEmpty()) {
            uArea.setError("Area Name Cant be Empty");
            status = false;
        } else {
            uArea.setError("");
            status = true;
            proceed_Area = m_Area;
        }

        return status;

    }

    private boolean validateCity() {

        boolean status = false;

        String m_City = uCity.getEditText().getText().toString().trim();

        if (m_City.isEmpty()) {
            uCity.setError("City Cant be Empty");
            status = false;
        } else {
            uCity.setError("");
            status = true;
            proceed_City = m_City;
        }

        return status;


    }

    private boolean validatePinCode() {

        boolean status = false;

        String m_PinCode = uPinCode.getEditText().getText().toString().trim();

        if (m_PinCode.isEmpty()) {
            uPinCode.setError("Pincode Can't be Empty");
            status = false;

        } else {
            uPinCode.setError("");
            status = true;
            proceed_PinCode = m_PinCode;
        }

        return status;


    }

}
