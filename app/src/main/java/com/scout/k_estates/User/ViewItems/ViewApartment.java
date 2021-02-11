package com.scout.k_estates.User.ViewItems;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.scout.k_estates.HelperClasses.ViewApartmentKeyValue;
import com.scout.k_estates.HelperClasses.ViewApartmentKeyValueAdapter;
import com.scout.k_estates.LocationOwner.MyProperties;
import com.scout.k_estates.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewApartment extends AppCompatActivity {

    ExtendedFloatingActionButton delete_item, see_location, call_owner;

    //new data
    FirebaseDatabase mDatabase;
    DatabaseReference mRef, mRef1,mRef2,mRef3, mRef4, mRef5, mRef6, mRef7;
    FirebaseStorage mStorage;
    ListView listView;
    //private AdView mAdView;

    //slider
    ImageSlider mainSlider;

    String activity ,sessionId, _longitude, _latitude, _postType, _rooms, _parlour, _internalToilet, _price, _caution, _advancePayment, _apartmentName, _region, _division, _quater, _externalToilet,  _postedBy, _phoneNum, _additionalInfo, _postTitle, _category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_apartment);

        //remomve landscape mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //hooks
        listView = findViewById(R.id.view_apartment_listview);
        see_location = findViewById(R.id.floatingbtn1);
        call_owner = findViewById(R.id.floatingbtn);
        delete_item = findViewById(R.id.floatingbtn2);
        mainSlider = findViewById(R.id.slider);


        //get id from userdashboard
        sessionId = getIntent().getStringExtra("ApartmentKey");

        //get data from previous activity
        _postType = getIntent().getStringExtra("postType");
        _rooms = getIntent().getStringExtra("rooms");
        _parlour = getIntent().getStringExtra("parlour");
        _internalToilet = getIntent().getStringExtra("internalToilet");
        _price = getIntent().getStringExtra("price");
        _caution = getIntent().getStringExtra("caution");
        _advancePayment = getIntent().getStringExtra("advancePayment");
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
        _longitude = getIntent().getStringExtra("longitude");
        _latitude = getIntent().getStringExtra("latitude");
        activity = getIntent().getStringExtra("Activity");
        //String _postedDate = getIntent().getStringExtra("postedDate");

        //create various key-value pair to add to list

        ViewApartmentKeyValue price = new ViewApartmentKeyValue("Price", _price);
        ViewApartmentKeyValue advancePayment = new ViewApartmentKeyValue("First Rent Installment", _advancePayment);
        ViewApartmentKeyValue caution = new ViewApartmentKeyValue("Caution fee", _caution);
        ViewApartmentKeyValue rooms = new ViewApartmentKeyValue("Number of rooms", _rooms);
        ViewApartmentKeyValue parlour = new ViewApartmentKeyValue("Number of parlours", _parlour);
        ViewApartmentKeyValue internalToilet = new ViewApartmentKeyValue("Internal Toilet", _internalToilet);
        ViewApartmentKeyValue externalToilet = new ViewApartmentKeyValue("External Toilet", _externalToilet);
        ViewApartmentKeyValue apartmentName = new ViewApartmentKeyValue("Name of Apartment", _apartmentName);
        ViewApartmentKeyValue region = new ViewApartmentKeyValue("Region", _region);
        ViewApartmentKeyValue division = new ViewApartmentKeyValue("Division", _division);
        ViewApartmentKeyValue quater = new ViewApartmentKeyValue("Neighborhood", _quater);
        ViewApartmentKeyValue postedBy = new ViewApartmentKeyValue("Posted by", _postedBy);

        //now add to an arraylist
        ArrayList<ViewApartmentKeyValue> viewApartmentKeyValueArrayList = new ArrayList<>();
        viewApartmentKeyValueArrayList.add(price);
        if (_advancePayment != null){
            viewApartmentKeyValueArrayList.add(advancePayment);
        }
        if (_caution != null){
            viewApartmentKeyValueArrayList.add(caution);
        }
        if (_rooms != null){
            viewApartmentKeyValueArrayList.add(rooms);
        }
        if (_parlour != null){
            viewApartmentKeyValueArrayList.add(parlour);
        }
        if (_internalToilet != null){
            viewApartmentKeyValueArrayList.add(internalToilet);
        }
        if (_externalToilet != null){
            viewApartmentKeyValueArrayList.add(externalToilet);
        }
        if (_apartmentName != null){
            viewApartmentKeyValueArrayList.add(apartmentName);
        }
        viewApartmentKeyValueArrayList.add(region);
        viewApartmentKeyValueArrayList.add(division);
        viewApartmentKeyValueArrayList.add(quater);
        viewApartmentKeyValueArrayList.add(postedBy);

        //create adapter
        ViewApartmentKeyValueAdapter adapter1 = new ViewApartmentKeyValueAdapter(this, R.layout.view_apartment_listview_item, viewApartmentKeyValueArrayList);
        listView.setAdapter(adapter1);



        // database hooks
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("Real Estate Items").child("Apartments").child(sessionId).child("Images");
        mRef1 = mDatabase.getReference().child("Real Estate Items").child("Guest Houses").child(sessionId).child("Images");
        mRef2 = mDatabase.getReference().child("Real Estate Items").child("Business Places").child(sessionId).child("Images");
        mRef6 = mDatabase.getReference().child("Real Estate Items").child("Plots").child(sessionId).child("Images");
        List<SlideModel> imageList = new ArrayList<>();

        //get images and set slider
        switch (_category) {
            case "Apartment":

                imageList.clear();
                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot image_snapshot : snapshot.getChildren()) {

                            imageList.add(new SlideModel(Objects.requireNonNull(image_snapshot.getValue()).toString(), _postTitle, ScaleTypes.CENTER_INSIDE));
                        }

                        mainSlider.setImageList(imageList, ScaleTypes.CENTER_INSIDE);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                break;
            case "Guest House":

                imageList.clear();
                mRef1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot image_snapshot : snapshot.getChildren()) {

                            imageList.add(new SlideModel(Objects.requireNonNull(image_snapshot.getValue()).toString(), _postTitle, ScaleTypes.CENTER_INSIDE));
                        }

                        mainSlider.setImageList(imageList, ScaleTypes.CENTER_INSIDE);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                break;
            case "Business Place":

                imageList.clear();
                mRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot image_snapshot : snapshot.getChildren()) {

                            imageList.add(new SlideModel(Objects.requireNonNull(image_snapshot.getValue()).toString(), _postTitle, ScaleTypes.CENTER_INSIDE));
                        }

                        mainSlider.setImageList(imageList, ScaleTypes.CENTER_INSIDE);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                break;
            case "Plot":

                imageList.clear();
                mRef6.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot image_snapshot : snapshot.getChildren()) {

                            imageList.add(new SlideModel(Objects.requireNonNull(image_snapshot.getValue()).toString(), _postTitle, ScaleTypes.CENTER_INSIDE));
                        }

                        mainSlider.setImageList(imageList, ScaleTypes.CENTER_INSIDE);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                break;

        }



        if (activity.equals("MyProperty")){
            call_owner.setVisibility(View.INVISIBLE);
            see_location.setVisibility(View.INVISIBLE);
            delete_item.setVisibility(View.VISIBLE);

            //delete item
            delete_item.setOnClickListener(view -> {

                //check to see category
                switch (_category) {
                    case "Apartment":

                        mRef3 = mDatabase.getReference().child("Real Estate Items").child("Apartments");
                        mRef3.child(sessionId).setValue(null);
                        startActivity(new Intent(getApplicationContext(), MyProperties.class));
                        break;
                    case "Guest House":

                        mRef4 = mDatabase.getReference().child("Real Estate Items").child("Guest Houses");
                        mRef4.child(sessionId).setValue(null);
                        startActivity(new Intent(getApplicationContext(), MyProperties.class));
                        break;
                    case "Business Place":

                        mRef5 = mDatabase.getReference().child("Real Estate Items").child("Business Places");
                        mRef5.child(sessionId).setValue(null);
                        startActivity(new Intent(getApplicationContext(), MyProperties.class));
                        break;
                    case "Plot":

                        mRef7 = mDatabase.getReference().child("Real Estate Items").child("Plots");
                        mRef7.child(sessionId).setValue(null);
                        startActivity(new Intent(getApplicationContext(), MyProperties.class));
                        break;
                }

            });

        } else {
            call_owner.setVisibility(View.VISIBLE);
            delete_item.setVisibility(View.INVISIBLE);

            if (_latitude != null | _longitude != null){
                see_location.setVisibility(View.VISIBLE);
            }else {
                see_location.setVisibility(View.INVISIBLE);
            }

        }

        //see location on map
        see_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(getApplicationContext(), MapsActivityView.class);
                intent.putExtra("latitude", _latitude);
                intent.putExtra("longitude", _longitude);
                startActivity(intent);

            }
        });

        //call owner
        call_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+_phoneNum));
                startActivity(intent);
            }
        });

    }


}