package com.example.saravanamurali.farmersgen.address;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saravanamurali.farmersgen.R;
import com.example.saravanamurali.farmersgen.util.FavStatus;
import com.example.saravanamurali.farmersgen.util.SessionManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class UpdateMapAddressActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;
    private LocationCallback locationCallback;
    private View mapView;
    private TextView addressArea;
    private LocationManager locationManager;
    private final float DEFAULT_ZOOM = 18;

    private TextInputLayout uFlatNoo;
    private TextInputEditText geoSetFlatNoo;
    private String proceed_FlatNoo = "";
    private String proceed_Alternate_Mobile_Numberr = "";


    TextInputEditText updateMobilee;
    private TextInputLayout uAlternateMobilee;

    ImageView contactImage;
    SessionManager session;
    String pref_lat;
    String pref_lng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_map_address);

        addressArea = (TextView) findViewById(R.id.addressArea);
        uFlatNoo = findViewById(R.id.updateMapFlat);
        geoSetFlatNoo = (TextInputEditText) findViewById(R.id.SetMapUpdateFlat);
        contactImage = (ImageView) findViewById(R.id.contactImage);
        updateMobilee = findViewById(R.id.setMapUpdateMobile);
        uAlternateMobilee=findViewById(R.id.updateMapAlterMobile);

        session = new SessionManager(UpdateMapAddressActivity.this);

        pref_lat = session.getGpsLatlng().get(SessionManager.KEY_GPS_LAT);
        pref_lng = session.getGpsLatlng().get(SessionManager.KEY_GPS_LNG);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(UpdateMapAddressActivity.this);

        contactImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadContact();

            }
        });


    }

    private void loadContact() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {

            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, FavStatus.PICK_CONTACT);

        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CONTACTS},
                    FavStatus.MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String phoneNumber = "";
        String name = "";

        if (resultCode == Activity.RESULT_OK) {
            Uri contactData = data.getData();
            Cursor c = managedQuery(contactData, null, null, null, null);
            if (c.moveToFirst()) {
                name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));

                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                while (phones.moveToNext()) {

                    phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                }

                updateMobilee.setText(phoneNumber);
                phones.close();

            }


        }
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Double lat = Double.parseDouble(pref_lat);
        Double lon = Double.parseDouble(pref_lng);

        if (lat == 1.0 && lon == 1.0) {

            Double latt = 13.0827;
            Double lonn = 80.2707;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latt, lonn), DEFAULT_ZOOM));
            getAddressFromLatiandLongi(latt, lonn);

        } else if (lat != null && lon != null) {

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), DEFAULT_ZOOM));
            getAddressFromLatiandLongi(lat, lon);

        }

        //Setting Default Location of the map

        if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {

            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 0, 50);

        }

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

                Double nextlatitude = mMap.getProjection().getVisibleRegion().latLngBounds.getCenter().latitude;
                Double nextlongitude = mMap.getProjection().getVisibleRegion().latLngBounds.getCenter().longitude;

                if (nextlatitude != null && nextlongitude != null) {


                    getAddressFromLatiandLongi(nextlatitude, nextlongitude);

                } else {
                    return;
                }
            }
        });

        mMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {

                addressArea.setText("Fetching Location...");

            }
        });

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {


                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    getlocationWhenMyLocationButtonPressed();

                } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                    getDeviceLocation();
                }
                return false;
            }
        });


    }

    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {
        mFusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {

                        if (task.isSuccessful()) {
                            mLastKnownLocation = task.getResult();

                            if (mLastKnownLocation != null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(),
                                        mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));

                                getAddressFromLatiandLongi(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());

                            } else if (mLastKnownLocation == null) {

                                LocationRequest locationRequest = LocationRequest.create();
                                locationRequest.setInterval(10000);
                                locationRequest.setFastestInterval(5000);
                                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                                locationCallback = new LocationCallback() {
                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        super.onLocationResult(locationResult);

                                        if (locationResult == null) {
                                            return;
                                        }
                                        mLastKnownLocation = locationResult.getLastLocation();
                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                                        mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
                                    }
                                };

                                mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);

                            }

                        } else {


                            getDeviceLocation();
                            return;

                        }


                    }
                });
    }


    private void getAddressFromLatiandLongi(Double lat, Double lon) {
        session.createLatLngSession(lat,lon);

        Geocoder geocoder = new Geocoder(UpdateMapAddressActivity.this, Locale.getDefault());
        try {
            List<Address> geoAddresses = geocoder.getFromLocation(lat, lon, 1);

            String address = geoAddresses.get(0).getAddressLine(0);
            String area = geoAddresses.get(0).getLocality();
            String city = geoAddresses.get(0).getAdminArea();
            String country = geoAddresses.get(0).getCountryName();
            String postalCode = geoAddresses.get(0).getPostalCode();
            String subAdminArea = geoAddresses.get(0).getSubAdminArea();
            String subLocality = geoAddresses.get(0).getSubLocality();
            String premises = geoAddresses.get(0).getPremises();
            String addressLine = geoAddresses.get(0).getAddressLine(0);

            // showAddress.setVisibility(View.VISIBLE);
            addressArea.setText(address + " " + area + " " + city + " " + postalCode);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void getlocationWhenMyLocationButtonPressed() {

        buildAlertMessageNoGps();
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

    public void onClickUpdateAddressMap(View view) {
        if (!validateFlatNo() | !validateAlterMobileNumber()) {

            return;
        } else {

            updateAddressFromChangeAddressClick();
        }
    }

    private void updateAddressFromChangeAddressClick() {

        Toast.makeText(UpdateMapAddressActivity.this, "Clicked", Toast.LENGTH_LONG).show();
    }

    private boolean validateAlterMobileNumber() {
        boolean status = false;

        String m_AlternateMobileNumber = uAlternateMobilee.getEditText().getText().toString().trim();

        if (m_AlternateMobileNumber.isEmpty()) {
            uAlternateMobilee.setError("Alternate mobile # Can't be Empty");
            status = false;
        } else {
            uAlternateMobilee.setError("");
            status = true;
            proceed_Alternate_Mobile_Numberr = m_AlternateMobileNumber;
        }

        return status;

    }

    private boolean validateFlatNo() {
        boolean status = false;

        String m_FlatNo = uFlatNoo.getEditText().getText().toString().trim();

        if (m_FlatNo.isEmpty()) {
            uFlatNoo.setError("Flat # Can't be Empty");
            status = false;
        } else {
            uFlatNoo.setError("");
            status = true;
            proceed_FlatNoo = m_FlatNo;
        }

        return status;

    }
}

