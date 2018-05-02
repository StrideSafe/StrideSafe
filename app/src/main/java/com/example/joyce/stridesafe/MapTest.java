package com.example.joyce.stridesafe;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MapTest extends FragmentActivity implements OnMyLocationButtonClickListener,



        OnMyLocationClickListener, OnMapReadyCallback {

    private static final LatLng Prescott_A = new LatLng(42.27385542588484, -71.79915435938568);
    private static final LatLng Prescott_B = new LatLng(42.27907885741453, -71.7996264281723);


    private GoogleMap mMap;
    ArrayList<LatLng> markerPoints;
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        Log.d("Map Entry", "I have entered the onCreate Method");

        super.onCreate(savedInstanceState);setContentView(R.layout.activity_map_test);

        // Initializing array List
        markerPoints = new ArrayList<LatLng>();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
//        setUpMapIfNeeded();
    }


        @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        Log.d("Map Entry", "I have entered the onMapReady Method");

        /* Sample Code, We may want to reference later.

        LatLng sydney = new LatLng(-34, 151);

        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/



        boolean check = checkLocationPermission();

        if(!check){

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        }

        mMap.setMyLocationEnabled(true);

        mMap.setOnMyLocationButtonClickListener(this);

        mMap.setOnMyLocationClickListener(this);
        setUpMap();
    }

    public boolean checkLocationPermission() {

        String permission = "android.permission.ACCESS_FINE_LOCATION";

        int res = this.checkCallingOrSelfPermission(permission);

        return (res == PackageManager.PERMISSION_GRANTED);

    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {

            case 1: {

                // If request is cancelled, the result arrays are empty.

                if (grantResults.length > 0

                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {



                } else {

                    // permission denied, boo! Disable the

                    // functionality that depends on this permission.

                }

                return;

            }

        }

    }

    @Override

    public void onMyLocationClick(@NonNull Location location) {

        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();

    }



    @Override

    public boolean onMyLocationButtonClick() {

        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();

        // Return false so that we don't consume the event and the default behavior still occurs

        // (the camera animates to the user's current position).

        return false;

    }

    private void setUpMap() {
        // check if we have got the googleMap already
      //  if (mMap == null) {
      //      mMap = ((SupportMapFragment) getSupportFragmentManager()
      //              .findFragmentById(R.id.map)).getMapAsync(this);
            if (mMap != null) {
                addLines();
            }

            //      mMap.setOnCameraChangeListener(this);
        }

    private void addLines() {

        mMap
                .addPolyline((new PolylineOptions())
                        .add(Prescott_A,Prescott_B).width(5).color(Color.BLUE)
                        .geodesic(true));
        // move camera to zoom on map
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Prescott_A, 13));
    }



    public void CalculationByDistance(View view) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = Prescott_A.latitude;
        double lat2 = Prescott_B.latitude;
        double lon1 = Prescott_A.longitude;
        double lon2 = Prescott_B.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        Double valueResult = Radius * c;
        valueResult = valueResult * 3280.84; // KM To FEET
//        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
//                + " Meter   " + meterInDec);
        
        String dist = valueResult.toString();
        Toast.makeText(this, dist, Toast.LENGTH_SHORT).show();

    }

    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

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