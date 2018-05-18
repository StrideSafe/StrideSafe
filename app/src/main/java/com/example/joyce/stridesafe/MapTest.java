package com.example.joyce.stridesafe;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class MapTest extends FragmentActivity implements OnMyLocationButtonClickListener, OnMapReadyCallback {


    private GoogleMap mMap;
    ArrayList<LatLng> markerPoints;
    int mapLocation = 0;
    private LocationRequest mLocationRequest;

    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    public static ArrayList <LatLng> list = new ArrayList<LatLng>();
    public static String loc1;
    public static String loc2;

    public static String num1;
    public static String num2;
    public static String num3;

    /**
     * Initiates the map activity, and unpacks the bundle with the contact information.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);setContentView(R.layout.activity_map_test);

        // Initializing array List
        markerPoints = new ArrayList<LatLng>();

        try {


            Bundle bundle = getIntent().getExtras();

            num1 = bundle.getString("firstNum");
            num2 = bundle.getString("secNum");
            num3 = bundle.getString("answer");

            Log.d("MainActivity","the first number pls " + num1);

        } catch(NullPointerException e) {
            Toast.makeText(getApplicationContext(),"Please enter your emergency contacts",Toast.LENGTH_SHORT).show();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        //setUpMapIfNeeded();
    }

    /**
     * Checks permissions for user location and calls methods to create the map
     * fragment.
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        boolean check = checkLocationPermission();
        if(!check){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        mMap.setMyLocationEnabled(true);

        mMap.setOnMyLocationButtonClickListener(this);

        setUpMap();
        getLastLocation();
        startLocationUpdates();
    }

    /**
     * Method used to check if location permissions are given.
     * @return a boolean specifying if permissions have been given.
     */
    public boolean checkLocationPermission() {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * Tbh I don't know if this is even used anywhere.
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
                else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    /**
     * This is here cause Iti won't let me delete it.
     * @param location
     */
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    /**
     * When the button is clicked the camera moves to the user's location.
     * Base code from Andrew Yuan, Idk where he got it from
     * @return
     */
    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    /**
     * The location is updated every 10 seconds.
     */
    // Trigger new location updates at interval
    protected void startLocationUpdates() {
        // Create the location request to start receiving updates
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        boolean check = checkLocationPermission();
        if (!check) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        // do work here
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());
    }

    /**
     * Calculates the perpendicular distance between the user and the path.
     * @param location
     */
    public void onLocationChanged(Location location) {
        //Calculating the distances between user and the path
        boolean alert = false;

        //Getting the starting, destination, and user latlong
        double xstart = list.get(0).latitude;
        double ystart = list.get(0).longitude;
        double xdest = list.get(1).latitude;
        double ydest = list.get(1).longitude;
        double xuser = location.getLatitude();
        double yuser = location.getLongitude();

        //Calculating the slope of the path and the slope of the line perpendicular to the path from the user
        double slopepath = (ydest-ystart)/(xdest-xstart);
        double b = ystart-slopepath*xstart;
        double slopeuser = -(1/slopepath);
        double buser = yuser-slopeuser*xuser;

        //Calculating the latlong of a coordinate on the path closest to the user
        double xpath = (buser-b)/(slopepath-slopeuser);
        double ypath = slopepath*xpath+b;

        //Calculating the distance from the closest latlong to the user
        //double distance = Math.sqrt((Math.pow((xpath-xuser),2.0))+(Math.pow((ypath-yuser),2.0)));
        int Radius = 6371;
        double dLat = Math.toRadians(xpath - xuser);
        double dLon = Math.toRadians(ypath - yuser);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(xuser))
                * Math.cos(Math.toRadians(xpath)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        Double valueResult = Radius * c;
        valueResult = valueResult * 3280.84; // KM To FEET

        //String msg1 = "User distance: " + valueResult.toString();
        //Toast.makeText(this, msg1, Toast.LENGTH_SHORT).show();

        if(valueResult > 250)
        {
            Intent intentHelp = new Intent(this, HelpActivity.class);
            intentHelp.putExtra("firstNum", num1);
            intentHelp.putExtra("secNum", num2);
            intentHelp.putExtra("answer", num3);
            startActivity(intentHelp);
        }

        // New location has now been determined
        //String msg = "Updated Location: " +
                //Double.toString(location.getLatitude()) + "," +
                //Double.toString(location.getLongitude());
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Retrieves the user's location.
     */
    public void getLastLocation() {
        // Get last known recent location using new Google Play Services SDK (v11+)
        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(this);
        boolean check = checkLocationPermission();
        if (!check) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        locationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // GPS location can be null if GPS is switched off
                        if (location != null) {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            onLocationChanged(location);
                            Log.d("mapActivity", "New Location Coordinates: " + latLng);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("mapActivity", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }

    /**
     * Creates the map fragment and unpacks the bundle with the starting and
     * ending coordinates of the user's route.
     */
    private void setUpMap() {

        Bundle bundle = getIntent().getExtras();

        loc1 = bundle.getString("UserStartLoc");
        loc2 = bundle.getString("UserEndLoc");
        LatLng start = getLocationFromAddress(loc1);
        Log.d("MapTest","Starting LOC COORDINATES:"+start.toString());
        LatLng end = getLocationFromAddress(loc2);
        list.add(0, start);
        Log.d("MapTest","STARTING POSITION IN LIST:" + list.get(0).toString());
        list.add(1,end);

        if(mMap !=null) {
            addLines();
        }
    }

    /**
     * Creates the user's straight line route.
     */
    private void addLines() {
        mMap.addPolyline((new PolylineOptions()).add(list.get(0), list.get(1)).width(5).color(Color.BLUE).geodesic(true));
        // move camera to zoom on map
        float zoomLevel = 16.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(list.get(0), zoomLevel));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Prescott_A, 13));

    }

    /**
     * Calculates the distance from the starting location to the ending location.
     * @param view
     */
    public void CalculationByDistance(View view) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = list.get(0).latitude;
        double lat2 = list.get(1).latitude;
        double lon1 = list.get(0).longitude;
        double lon2 = list.get(1).longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        Double valueResult = Radius * c;
        valueResult = valueResult * 3280.84; // KM To FEET
        //Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec + " Meter   " + meterInDec);
        String dist = "Distance to Destination: " + valueResult.toString();
        Toast.makeText(this, dist, Toast.LENGTH_SHORT).show();
    }

    /**
     * Converts an address to a LatLng object.
     * @param strAddress
     * @return The LatLng object.
     */
    public LatLng getLocationFromAddress(String strAddress) {
        Geocoder geocoder = new Geocoder(this);

        try {
            // May throw an IOException
            List<Address> addresses = geocoder.getFromLocationName(strAddress, 1);
            Address address = addresses.get(0);

            if (addresses.size() > 0) {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                LatLng p1 = new LatLng(latitude, longitude);
                return p1;
            }

        } catch (IOException ex) {

            ex.printStackTrace();
        }
        return null;
    }
}