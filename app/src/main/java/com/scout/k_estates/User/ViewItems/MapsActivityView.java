package com.scout.k_estates.User.ViewItems;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.scout.k_estates.R;

import java.util.Objects;

public class MapsActivityView extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String _latitude, _longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_view);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);
        //remomve landscape mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        _latitude = getIntent().getStringExtra("latitude");
        _longitude = getIntent().getStringExtra("longitude");

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
        mMap.clear();

        double latitude = Double.parseDouble(_latitude);
        double longitude = Double.parseDouble(_longitude);

        LatLng location = new LatLng(latitude, longitude);


        // Add a marker in Sydney and move the camera
        mMap.addMarker(new MarkerOptions().position(location).title("This is the location of the apartment"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,14F));
    }

    public void goThere(View view) {

        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q="+_latitude+","+_longitude+"&mode=l"));
        intent.setPackage("com.google.android.apps.maps");

        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }
}