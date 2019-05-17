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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.apiInterfaces.ApiInterface;
import com.example.saravanamurali.farmersgen.paymentgateway.PaymentGatewayActivity;
import com.example.saravanamurali.farmersgen.models.ADDAddessDTO;
import com.example.saravanamurali.farmersgen.modeljsonresponse.JSONResponseADDAddress;
import com.example.saravanamurali.farmersgen.retrofitclient.APIClientToADDAddress;
import com.example.saravanamurali.farmersgen.util.FavStatus;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_Address_Activity extends AppCompatActivity implements View.OnClickListener {

    private String NO_CURRENT_USER = "NO_CURRENT_USER";

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

    private String proceed_FlatNo = "";
    private String proceed_StreetName = "";
    private String proceed_Area = "";
    private String proceed_City = "";
    private String proceed_PinCode = "";
    private String proceed_LandMark = "";
    private String proceed_AlternateMobileNumber = "";

    private Button myLocation_Button_In_Add_Address;
    private LocationManager locationManager;

    private Double lattitude, longitude;

    private Geocoder geocoder;
    private List<Address> geoAddresses;
    private TextInputEditText geoSetFlatNo;
    private TextInputEditText geoSetStreetName;
    private TextInputEditText geoSetArea;
    private TextInputEditText geoSetCity;
    private TextInputEditText geoSetPincode;

    TextView showAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__address_);

        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();
*/



        //tvDeliverAddress=(TextView)findViewById(R.id.tVDeliveryAddress);

        mFlatNo = findViewById(R.id.flatNo);
        mStreetName = findViewById(R.id.streetName);
        mArea = findViewById(R.id.area);
        mCity = findViewById(R.id.city);
        mPinCode = findViewById(R.id.pinCode);
        mLandMark = findViewById(R.id.landMark);
        mAlternateMobile = findViewById(R.id.alternateMobile);

        showAddress=(TextView)findViewById(R.id.showAddressAdd);

        geoSetPincode = (TextInputEditText) findViewById(R.id.addPincode);
        geoSetCity = (TextInputEditText) findViewById(R.id.addCity);
        geoSetArea = (TextInputEditText) findViewById(R.id.addArea);
        geoSetStreetName = (TextInputEditText) findViewById(R.id.addStreetName);
        geoSetFlatNo = (TextInputEditText) findViewById(R.id.addFlatNo);

        myLocation_Button_In_Add_Address = (Button) findViewById(R.id.myLocationButtonInaddAddress);

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, FavStatus.REQUEST_LOCATION);

        myLocation_Button_In_Add_Address.setOnClickListener(this);


    }


    public void onClickProceed(View view) {


        if (!validateFlatNo() | !validatStreetName() | !validateArea() | !validateCity() | !validatePinCode() | !validateLandMark() | !validateAlternateMobileNumber()) {
            return;
        } else {

            proceed();
        }

    }

    private void proceed() {

        final ProgressDialog csprogress;
        csprogress = new ProgressDialog(Add_Address_Activity.this);
        csprogress.setMessage("Loading...");
        csprogress.show();
        csprogress.setCanceledOnTouchOutside(false);


        Toast.makeText(Add_Address_Activity.this, "Proceed To Pay,", Toast.LENGTH_LONG).show();

        ApiInterface api = APIClientToADDAddress.getAPIInterfaceForADDAddress();

        SharedPreferences sharedPreferences = getSharedPreferences("CURRENT_USER", MODE_PRIVATE);
        String currentUserForADD_Address = sharedPreferences.getString("CURRENTUSER", "NO_CURRENT_USER");


        ADDAddessDTO addAddessDTO = new ADDAddessDTO(proceed_FlatNo, proceed_StreetName, proceed_Area, proceed_City, proceed_PinCode, proceed_LandMark, proceed_AlternateMobileNumber, currentUserForADD_Address);

        Call<JSONResponseADDAddress> call = api.addAddress(addAddessDTO);

        call.enqueue(new Callback<JSONResponseADDAddress>() {
            @Override
            public void onResponse(Call<JSONResponseADDAddress> call, Response<JSONResponseADDAddress> response) {
                if (response.isSuccessful()) {

                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }

                    JSONResponseADDAddress jsonResponseADDAddress = response.body();

                    if (jsonResponseADDAddress.getResultStatus() == 200) {

                        Intent codIntent = new Intent(Add_Address_Activity.this, PaymentGatewayActivity.class);
                        startActivity(codIntent);
                        finish();

                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponseADDAddress> call, Throwable t) {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }

            }
        });


    }


    private boolean validateFlatNo() {
        boolean status = false;

        String m_FlatNo = mFlatNo.getEditText().getText().toString().trim();

        if (m_FlatNo.isEmpty()) {
            mFlatNo.setError("Flat # Can't be Empty");
        } else {
            mFlatNo.setError("");
            status = true;
            proceed_FlatNo = m_FlatNo;
        }

        return true;

    }

    boolean validatStreetName() {
        boolean status = false;

        String m_StreetNAme = mStreetName.getEditText().getText().toString().trim();


        if (m_StreetNAme.isEmpty()) {
            mStreetName.setError("Street Name Can't be Empty");
        } else {
            mStreetName.setError("");
            status = true;
            proceed_StreetName = m_StreetNAme;
        }
        return true;

    }

    boolean validateArea() {
        boolean status = false;

        String m_Area = mArea.getEditText().getText().toString().trim();

        if (m_Area.isEmpty()) {
            mArea.setError("Area Name Cant be Empty");
        } else {
            mArea.setError("");
            status = true;
            proceed_Area = m_Area;
        }

        return true;

    }

    boolean validateCity() {

        boolean status = false;

        String m_City = mCity.getEditText().getText().toString().trim();

        if (m_City.isEmpty()) {
            mCity.setError("City Cant be Empty");
        } else {
            mCity.setError("");
            status = true;
            proceed_City = m_City;
        }

        return true;


    }

    boolean validatePinCode() {

        boolean status = false;

        String m_PinCode = mPinCode.getEditText().getText().toString().trim();

        if (m_PinCode.isEmpty()) {
            mPinCode.setError("Pincode Can't be Empty");

        } else {
            mPinCode.setError("");
            status = true;
            proceed_PinCode = m_PinCode;
        }

        return true;
    }

    boolean validateLandMark() {

        boolean status = false;

        String m_LandMark = mLandMark.getEditText().getText().toString().trim();

        if (m_LandMark.isEmpty()) {
            mLandMark.setError("LandMark Can't be Empty");

        } else {
            mLandMark.setError("");
            status = true;
            proceed_LandMark = m_LandMark;
        }

        return true;
    }

    boolean validateAlternateMobileNumber() {

        boolean status = false;

        String m_AlternateMobile = mAlternateMobile.getEditText().getText().toString().trim();

        if (m_AlternateMobile.isEmpty()) {
            mAlternateMobile.setError("Alternate Mobile Number Can't be Empty");

        } else {
            mAlternateMobile.setError("");
            status = true;
            proceed_AlternateMobileNumber = m_AlternateMobile;
        }

        return true;
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

        if (ActivityCompat.checkSelfPermission(Add_Address_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (Add_Address_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Add_Address_Activity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FavStatus.REQUEST_LOCATION);

        } else {

            final ProgressDialog csprogress;
            csprogress = new ProgressDialog(Add_Address_Activity.this);
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

                if (geoAddresses == null) {
                    getLocation();
                } else {

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

                    showAddress.setVisibility(View.VISIBLE);
                    showAddress.setText(address + " " + area + " " + city + " " + postalCode);

               /* String doorNo = address;
                String[] d_No = doorNo.split(",", 2);
                geoSetFlatNo.setText(d_No[0]);*/

                    geoSetArea.setText(subLocality);
                    geoSetCity.setText(area);
                    geoSetPincode.setText(postalCode);

                    if (csprogress.isShowing()) {
                        csprogress.dismiss();
                    }
                }

                } catch(IOException e){
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
