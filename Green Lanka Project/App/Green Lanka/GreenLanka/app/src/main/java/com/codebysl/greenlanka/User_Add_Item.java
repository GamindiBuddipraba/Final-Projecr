package com.codebysl.greenlanka;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class User_Add_Item extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    Button setLocation, seeOnMap, send, cancel;
    TextView longitude, latitude;
    EditText fullAddress, description, contact;
    Spinner spinner;
    String unm;
    private ProgressDialog pd;

    FusedLocationProviderClient mFusedLocationClient;

    int PERMISSION_ID = 44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_item);

        Intent i = getIntent();
        unm = i.getStringExtra("uname");

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setLocation = (Button)findViewById(R.id.btn_set_location);
        seeOnMap = (Button)findViewById(R.id.btnSeeOnMapUAI);
        send = (Button)findViewById(R.id.btnSendUAI);
        cancel = (Button)findViewById(R.id.btnCancelUAI);
        latitude = (TextView)findViewById(R.id.tv_latUAI);
        longitude = (TextView)findViewById(R.id.tv_longtUAI);
        fullAddress = (EditText) findViewById(R.id.txtAddressUAI);
        description = (EditText) findViewById(R.id.txtQuantityUAI);
        spinner = (Spinner)findViewById(R.id.spinnerUAI);
        contact = (EditText) findViewById(R.id.txtContactUAI);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Menu menu = navigationView.getMenu();

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        latitude.setText("-");
        longitude.setText("-");

        setLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastLocation();
            }
        });

        seeOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (latitude.getText().toString().isEmpty() || longitude.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please set Location first", Toast.LENGTH_SHORT).show();

                }else{
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.putExtra("lat",latitude.getText().toString());
                    intent.putExtra("longti",longitude.getText().toString());
                    startActivity(intent);
                }

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (latitude.getText().toString().isEmpty() || longitude.getText().toString().isEmpty() || fullAddress.getText().toString().isEmpty() || description.getText().toString().isEmpty() || contact.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please fill all fields!", Toast.LENGTH_SHORT).show();
                }else{


                    final String lat = latitude.getText().toString().trim();
                    final String longti = longitude.getText().toString().trim();
                    final String addrs = fullAddress.getText().toString().trim();
                    final String type = spinner.getSelectedItem().toString().trim();
                    final String desc = description.getText().toString().trim();
                    final String cno = contact.getText().toString().trim();

                    insertData(lat, longti,addrs,type, desc, cno);

                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

    }

    private void clear(){
        latitude.setText("-");
        longitude.setText("-");
        fullAddress.setText(null);
        fullAddress.setHint("Enter Address");
        description.setText(null);
        description.setHint("Enter Description");
        contact.setText(null);
        contact.setHint("Enter Contact Number");
    }

    private void insertData(String elatitude, String elongitude, String eaddress, String etype, String edescription, String contact){


        HashMap<String,String> item=new HashMap<>();

        item.put("Latitude", elatitude);
        item.put("Longitude", elongitude);
        item.put("Address", eaddress);
        item.put("Type", etype);
        item.put("Description", edescription);
        item.put("Contact", contact);
        item.put("Status", "0");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference tasksRef = rootRef.child("Items").child(unm).child(currentDateandTime);
        tasksRef.setValue(item);
        clear();


    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                Intent intent2 = new Intent(getApplicationContext(), User_Home.class);
                intent2.putExtra("uname", unm);
                startActivity(intent2);
                break;
            case R.id.nav_additem:
                Toast.makeText(this, "Add Item", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_addcomplain:
                Intent intent = new Intent(getApplicationContext(), User_Add_Complain.class);
                intent.putExtra("uname", unm);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                Intent intent3 = new Intent(getApplicationContext(), User_Login.class);
                startActivity(intent3);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            latitude.setText(String.valueOf(location.getLatitude()));
                            longitude.setText(String.valueOf(location.getLongitude()));

                            String setAdd = getCompleteAddressString(location.getLatitude(),location.getLongitude());
                            fullAddress.setText(setAdd);
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();

            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private final LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();

            latitude.setText(String.valueOf(mLastLocation.getLatitude()));
            longitude.setText(String.valueOf(mLastLocation.getLongitude()));
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                //Toast.makeText(this, "My Current loction address", Toast.LENGTH_SHORT).show();

                //Log.w("My Current loction address", strReturnedAddress.toString());
            } else {
                //Toast.makeText(this, "Add Item", Toast.LENGTH_SHORT).show();

                //Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(this, "Add Item", Toast.LENGTH_SHORT).show();

            //Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }
}