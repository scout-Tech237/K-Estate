package com.scout.k_estates.LocationOwner.CreateAccomodation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.scout.k_estates.HelperClasses.CoreHelper;
import com.scout.k_estates.HelperClasses.CustomModel;
import com.scout.k_estates.HelperClasses.ImagesAdapter;
import com.scout.k_estates.HelperClasses.SessionManager;
import com.scout.k_estates.R;
import com.scout.k_estates.User.UserDashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CreateHouseApartment2 extends AppCompatActivity {

    private static final int READ_PERMISSION_CODE = 1;
    private static final int PICK_IMAGE_REQUEST_CODE = 2;
    ImageView no_images;
    FloatingActionButton btnPickImages, btnUploadImages;
    RecyclerView recyclerView;
    List<CustomModel> imagesList;
    List<String> savedImagesUri;
    ImagesAdapter adapter;
    CoreHelper coreHelper;

    int counter;

    String _dimension ,_postType,_longitude,_comingFrom, _latitude, _rooms, _parlour, _internalToilet, _price, _caution, _advancedPayment, _apartmentName, _region, _division, _quater, _externalToilet, _postedBy, _phoneNum, _additionalInfo, _postTitle, _category;

    //new data
    FirebaseDatabase mDatabase;
    DatabaseReference mRef, mRef1, mRef2, mRef3;
    FirebaseStorage mStorage;
    SessionManager sessionManager;
    String phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_create_house_apartment2);

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
        _longitude = getIntent().getStringExtra("longitude");
        _latitude = getIntent().getStringExtra("latitude");
        _dimension = getIntent().getStringExtra("dimension");
        _comingFrom = getIntent().getStringExtra("comingFrom");


        //get session number to use to save to user firebase profile
        sessionManager = new SessionManager(CreateHouseApartment2.this, SessionManager.SESSION_USERSESSION);
        HashMap<String, String> userDetails = sessionManager.getUsersDetailFromSession();

        phoneNum = userDetails.get(SessionManager.KEY_PHONENUMBER);

        // new data
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("Real Estate Items").child("Apartments");
        mRef1 = mDatabase.getReference().child("Real Estate Items").child("Guest Houses");
        mRef2 = mDatabase.getReference().child("Real Estate Items").child("Business Places");
        mRef3 = mDatabase.getReference().child("Real Estate Items").child("Plots");
        mStorage = FirebaseStorage.getInstance();



        savedImagesUri = new ArrayList<>();

        no_images = findViewById(R.id.no_image);
        btnPickImages = findViewById(R.id.fabChooseImage);
        btnUploadImages = findViewById(R.id.fabUploadImage);
        imagesList = new ArrayList<>();
        coreHelper = new CoreHelper(this);
        //Code to show list of images start
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new ImagesAdapter(this, imagesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if (adapter.getItemCount() != 0) {
                    no_images.setVisibility(View.GONE);
                } else {
                    no_images.setVisibility(View.VISIBLE);
                }
            }
        });
        //Code to show list of images end
        btnPickImages.setOnClickListener(view -> verifyPermissionAndPickImage());
        btnUploadImages.setOnClickListener(this::uploadImages);
    }

    private void uploadImages(View view) {
        if (imagesList.size() != 0) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Uploaded 0/"+imagesList.size());
            //progressDialog.setCanceledOnTouchOutside(false); //Remove this line if you want your user to be able to cancel upload
            //progressDialog.setCancelable(false);    //Remove this line if you want your user to be able to cancel upload
            progressDialog.show();
            final StorageReference storageReference = mStorage.getReference();
            for (int i = 0; i < imagesList.size(); i++) {
                final int finalI = i;
                storageReference.child("userData/").child(imagesList.get(i).getImageName()).putFile(imagesList.get(i).getImageURI()).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        storageReference.child("userData/").child(imagesList.get(finalI).getImageName()).getDownloadUrl().addOnCompleteListener(task1 -> {
                            counter++;
                            progressDialog.setMessage("Uploaded "+counter+"/"+imagesList.size());
                            if (task1.isSuccessful()){
                                savedImagesUri.add(task1.getResult().toString());
                            }else{
                                storageReference.child("userData/").child(imagesList.get(finalI).getImageName()).delete();
                                Toast.makeText(CreateHouseApartment2.this, "Couldn't save "+imagesList.get(finalI).getImageName(), Toast.LENGTH_SHORT).show();
                            }
                            if (counter == imagesList.size()){
                                saveImageDataToFirestore(progressDialog);
                            }
                        });
                    }else{
                        progressDialog.setMessage("Uploaded "+counter+"/"+imagesList.size());
                        counter++;
                        Toast.makeText(CreateHouseApartment2.this, "Couldn't upload "+imagesList.get(finalI).getImageName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            coreHelper.createSnackBar(view, "Please add some images first.", "", null, Snackbar.LENGTH_SHORT);
        }
    }

    private void saveImageDataToFirestore(final ProgressDialog progressDialog) {
        progressDialog.setMessage("Saving uploaded images...");
        Map<String, String> dataMap = new LinkedHashMap<>();
        for (int i=0; i<savedImagesUri.size(); i++){
            dataMap.put("image"+i, savedImagesUri.get(i));
        }

        /*Calendar c = Calendar.getInstance();
        Date date  = c.getTime();*/

        switch (_category) {
            case "Apartment": {
                DatabaseReference newPost = mRef.push();
                Map.Entry<String, String> entry = dataMap.entrySet().iterator().next();

                newPost.child("Images").setValue(dataMap);
                newPost.child("Image").setValue(entry.getValue());
                newPost.child("UId").setValue(newPost.getKey());
                newPost.child("PostType").setValue(_postType);
                newPost.child("Rooms").setValue(_rooms);
                newPost.child("Parlour").setValue(_parlour);
                newPost.child("InternalToilet").setValue(_internalToilet);
                newPost.child("Price").setValue(_price);
                newPost.child("Caution").setValue(_caution);
                newPost.child("AdvancePayment").setValue(_advancedPayment);
                newPost.child("ApartmentName").setValue(_apartmentName);
                newPost.child("Region").setValue(_region);
                newPost.child("Division").setValue(_division);
                newPost.child("Neighborhood").setValue(_quater);
                newPost.child("ExternalToilet").setValue(_externalToilet);
                newPost.child("PostedBy").setValue(_postedBy);
                newPost.child("PhoneNumCall").setValue(_phoneNum);
                newPost.child("AdditionInfo").setValue(_additionalInfo);
                newPost.child("PostTitle").setValue(_postTitle);
                newPost.child("Category").setValue(_category);
                newPost.child("Lon").setValue(_longitude);
                newPost.child("Lat").setValue(_latitude);
                newPost.child("PhoneNum").setValue(phoneNum);
                //newPost.child("PostedDate").setValue(date);
                break;
            }
            case "Guest House": {
                DatabaseReference newPost = mRef1.push();
                Map.Entry<String, String> entry = dataMap.entrySet().iterator().next();

                newPost.child("Images").setValue(dataMap);
                newPost.child("Image").setValue(entry.getValue());
                newPost.child("UId").setValue(newPost.getKey());
                newPost.child("PostType").setValue(_postType);
                newPost.child("Rooms").setValue(_rooms);
                newPost.child("Parlour").setValue(_parlour);
                newPost.child("InternalToilet").setValue(_internalToilet);
                newPost.child("Price").setValue(_price);
                newPost.child("Caution").setValue(_caution);
                newPost.child("AdvancePayment").setValue(_advancedPayment);
                newPost.child("ApartmentName").setValue(_apartmentName);
                newPost.child("Region").setValue(_region);
                newPost.child("Division").setValue(_division);
                newPost.child("Neighborhood").setValue(_quater);
                newPost.child("ExternalToilet").setValue(_externalToilet);
                newPost.child("PostedBy").setValue(_postedBy);
                newPost.child("PhoneNumCall").setValue(_phoneNum);
                newPost.child("AdditionInfo").setValue(_additionalInfo);
                newPost.child("PostTitle").setValue(_postTitle);
                newPost.child("Category").setValue(_category);
                newPost.child("Lon").setValue(_longitude);
                newPost.child("Lat").setValue(_latitude);
                newPost.child("PhoneNum").setValue(phoneNum);
                //newPost.child("PostedDate").setValue(date);
                break;
            }
            case "Business Place": {
                DatabaseReference newPost = mRef2.push();
                Map.Entry<String, String> entry = dataMap.entrySet().iterator().next();

                newPost.child("Images").setValue(dataMap);
                newPost.child("Image").setValue(entry.getValue());
                newPost.child("UId").setValue(newPost.getKey());
                newPost.child("PostType").setValue(_postType);
                newPost.child("Rooms").setValue(_rooms);
                newPost.child("Parlour").setValue(_parlour);
                newPost.child("InternalToilet").setValue(_internalToilet);
                newPost.child("Price").setValue(_price);
                newPost.child("Caution").setValue(_caution);
                newPost.child("AdvancePayment").setValue(_advancedPayment);
                newPost.child("ApartmentName").setValue(_apartmentName);
                newPost.child("Region").setValue(_region);
                newPost.child("Division").setValue(_division);
                newPost.child("Neighborhood").setValue(_quater);
                newPost.child("ExternalToilet").setValue(_externalToilet);
                newPost.child("PostedBy").setValue(_postedBy);
                newPost.child("PhoneNumCall").setValue(_phoneNum);
                newPost.child("AdditionInfo").setValue(_additionalInfo);
                newPost.child("PostTitle").setValue(_postTitle);
                newPost.child("Category").setValue(_category);
                newPost.child("Lon").setValue(_longitude);
                newPost.child("Lat").setValue(_latitude);
                newPost.child("PhoneNum").setValue(phoneNum);
                //newPost.child("PostedDate").setValue(date);
                break;
            }
            case "Plot": {
                DatabaseReference newPost = mRef3.push();
                Map.Entry<String, String> entry = dataMap.entrySet().iterator().next();

                newPost.child("Images").setValue(dataMap);
                newPost.child("Image").setValue(entry.getValue());
                newPost.child("UId").setValue(newPost.getKey());
                newPost.child("PostType").setValue(_postType);
                newPost.child("Price").setValue(_price);
                newPost.child("Region").setValue(_region);
                newPost.child("Division").setValue(_division);
                newPost.child("Neighborhood").setValue(_quater);
                newPost.child("PostedBy").setValue(_postedBy);
                newPost.child("PhoneNumCall").setValue(_phoneNum);
                newPost.child("AdditionInfo").setValue(_additionalInfo);
                newPost.child("PostTitle").setValue(_postTitle);
                newPost.child("Category").setValue(_category);
                newPost.child("Lon").setValue(_longitude);
                newPost.child("Lat").setValue(_latitude);
                newPost.child("Lat").setValue(_latitude);
                newPost.child("PhoneNum").setValue(phoneNum);
                newPost.child("Dimension").setValue(_dimension);
                //newPost.child("PostedDate").setValue(date);
                break;
            }
        }

        goToHomePage();
    }

    private void verifyPermissionAndPickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //If permission is granted
                pickImage();
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_PERMISSION_CODE);
            }
        } else {
            //no need to check permissions in android versions lower then marshmallow
            pickImage();
        }
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        Uri uri = clipData.getItemAt(i).getUri();
                        imagesList.add(new CustomModel(coreHelper.getFileNameFromUri(uri), uri));
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Uri uri = data.getData();
                    imagesList.add(new CustomModel(coreHelper.getFileNameFromUri(uri), uri));
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    private void goToHomePage(){
        startActivity(new Intent(getApplicationContext(), UserDashboard.class));
        finish();
    }
}