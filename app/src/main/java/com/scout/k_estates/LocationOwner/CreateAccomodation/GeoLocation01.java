package com.scout.k_estates.LocationOwner.CreateAccomodation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.scout.k_estates.R;

public class GeoLocation01 extends AppCompatActivity {

    String _postType, _rooms, _parlour, _internalToilet,_comingFrom, _price, _caution, _advancedPayment,_dimension, _apartmentName, _region, _division, _quater, _externalToilet,  _postedBy, _phoneNum, _additionalInfo, _postTitle, _category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_geo_location01);

        //remomve landscape mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
    }

    public void geoLocation(View view) {

        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);

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
        }


        startActivity(intent);

    }

    public void goToNextPage(View view) {

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
        }

        startActivity(intent);

    }
}