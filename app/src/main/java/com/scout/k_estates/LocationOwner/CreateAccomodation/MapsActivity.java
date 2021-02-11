package com.scout.k_estates.LocationOwner.CreateAccomodation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.scout.k_estates.R;

public class MapsActivity extends AppCompatActivity {

    SupportMapFragment smf;
    FusedLocationProviderClient client;
    ExtendedFloatingActionButton next;
    String _postType, _rooms, _parlour, _internalToilet,_comingFrom, _dimension, _price, _caution, _advancedPayment, _apartmentName, _region, _division, _quater, _externalToilet, _postedBy, _phoneNum, _additionalInfo, _postTitle, _category;
    String msg, msg1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_maps);

        //hooks
        next = findViewById(R.id.next_map);

        //remomve landscape mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //tell user to wait for 10 seconds
        Toast.makeText(this, "Please wait for 10 seconds as we get your current location.", Toast.LENGTH_LONG).show();

        //get data from previous activity
        _postType = getIntent().getStringExtra("postType");
        _rooms = getIntent().getStringExtra("rooms");
        _parlour = getIntent().getStringExtra("parlour");
        _internalToilet = getIntent().getStringExtra("internalToilet");
        _price = getIntent().getStringExtra("price");
        _caution = getIntent().getStringExtra("caution");
        _advancedPayment = getIntent().getStringExtra("advancedPayment");
        _apartmentName = getIntent().getStringExtra("apartmentName");
        _region = getIntent().getStringExtra("region");
        _division = getIntent().getStringExtra("division");
        _quater = getIntent().getStringExtra("quater");
        _externalToilet = getIntent().getStringExtra("externalToilet");
        _postedBy = getIntent().getStringExtra("postedBy");
        _phoneNum = getIntent().getStringExtra("phoneNumCall");
        _additionalInfo = getIntent().getStringExtra("additionalInfo");
        _postTitle = getIntent().getStringExtra("postTitle");
        _category = getIntent().getStringExtra("category");
        _comingFrom = getIntent().getStringExtra("comingFrom");

        //get data from plot activity
        _postType = getIntent().getStringExtra("postType");
        _price = getIntent().getStringExtra("price");
        _region = getIntent().getStringExtra("region");
        _division = getIntent().getStringExtra("division");
        _quater = getIntent().getStringExtra("quater");
        _postedBy = getIntent().getStringExtra("postedBy");
        _phoneNum = getIntent().getStringExtra("phoneNumCall");
        _additionalInfo = getIntent().getStringExtra("additionalInfo");
        _postTitle = getIntent().getStringExtra("postTitle");
        _category = getIntent().getStringExtra("category");
        _dimension = getIntent().getStringExtra("dimension");



        smf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        client = LocationServices.getFusedLocationProviderClient(this);

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        getMyLocation();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();

    }

    private void getMyLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(location -> smf.getMapAsync(googleMap -> {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("You are here!!");

            googleMap.addMarker(markerOptions);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));

            msg = Double.toString(location.getLatitude());
            msg1 = Double.toString(location.getLongitude());

            //get lat and long and send to next activity
            nextActivity();


        }));

    }

    private void nextActivity() {
        next.setVisibility(View.VISIBLE);
        next.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CreateHouseApartment2.class);

            if (_comingFrom.equals("other")){
                //send data to next activity
                intent.putExtra("postType", _postType);
                intent.putExtra("rooms", _rooms);
                intent.putExtra("parlour", _parlour);
                intent.putExtra("internalToilet", _internalToilet);
                intent.putExtra("price", _price);
                intent.putExtra("caution", _caution);
                intent.putExtra("advancedPayment", _advancedPayment);
                intent.putExtra("apartmentName", _apartmentName);
                intent.putExtra("region", _region);
                intent.putExtra("division", _division);
                intent.putExtra("quater", _quater);
                intent.putExtra("externalToilet", _externalToilet);
                intent.putExtra("postedBy", _postedBy);
                intent.putExtra("phoneNumCall", _phoneNum);
                intent.putExtra("additionalInfo", _additionalInfo);
                intent.putExtra("postTitle", _postTitle);
                intent.putExtra("category", _category);
                intent.putExtra("comingFrom", _comingFrom);
                intent.putExtra("longitude", msg1);
                intent.putExtra("latitude", msg);
            } else if (_comingFrom.equals("plot")){
                //send data to next activity
                intent.putExtra("postType", _postType);
                intent.putExtra("price", _price);
                intent.putExtra("region", _region);
                intent.putExtra("division", _division);
                intent.putExtra("quater", _quater);
                intent.putExtra("postedBy", _postedBy);
                intent.putExtra("phoneNumCall", _phoneNum);
                intent.putExtra("additionalInfo", _additionalInfo);
                intent.putExtra("postTitle", _postTitle);
                intent.putExtra("category", _category);
                intent.putExtra("dimension", _dimension);
                intent.putExtra("comingFrom", _comingFrom);
                intent.putExtra("longitude", msg1);
                intent.putExtra("latitude", msg);
            }

            startActivity(intent);
            finish();
        });
    }


}