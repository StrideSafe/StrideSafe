package com.example.joyce.stridesafe;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;


public class MapTest extends FragmentActivity implements OnMyLocationButtonClickListener,



        OnMyLocationClickListener, OnMapReadyCallback {

    private static final LatLng NU_CAFE = new LatLng(42.2627, -71.8265);
    private static final LatLng HOPE_CEMETARY = new LatLng(42.2362, -71.8270);


    private GoogleMap mMap;
    ArrayList<LatLng> markerPoints;
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        Log.d("Map Entry", "I have entered the onCreate Method");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_test);

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
                        .add(NU_CAFE,HOPE_CEMETARY).width(5).color(Color.BLUE)
                        .geodesic(true));
        // move camera to zoom on map
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(NU_CAFE, 13));
    }

}