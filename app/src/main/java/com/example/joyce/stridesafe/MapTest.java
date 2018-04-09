package com.example.joyce.stridesafe;



import android.Manifest;

import android.content.pm.PackageManager;

import android.location.Location;

import android.support.annotation.NonNull;

import android.support.v4.app.ActivityCompat;

import android.support.v4.app.FragmentActivity;

import android.os.Bundle;

import android.util.Log;

import android.widget.Toast;



//import com.google.android.gms.maps.CameraUpdateFactory;

import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;

import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;

//import com.google.android.gms.maps.model.LatLng;

//import com.google.android.gms.maps.model.MarkerOptions;



public class MapTest extends FragmentActivity implements OnMyLocationButtonClickListener,

        OnMyLocationClickListener, OnMapReadyCallback {



    private GoogleMap mMap;



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        Log.d("Map Entry", "I have entered the onCreate Method");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map_test);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()

                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

    }





    /**

     * Manipulates the map once available.

     * This callback is triggered when the map is ready to be used.

     * This is where we can add markers or lines, add listeners or move the camera. In this case,

     * we just add a marker near Sydney, Australia.

     * If Google Play services is not installed on the device, the user will be prompted to install

     * it inside the SupportMapFragment. This method will only be triggered once the user has

     * installed Google Play services and returned to the app.

     */

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



}