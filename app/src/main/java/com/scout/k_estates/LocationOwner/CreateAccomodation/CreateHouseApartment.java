package com.scout.k_estates.LocationOwner.CreateAccomodation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;
import com.scout.k_estates.R;

import java.util.Objects;

public class CreateHouseApartment extends AppCompatActivity {

    TextInputLayout rooms, parlour, internalToilet, price, caution, advancedPayment, apartmentName, phoneNumber, additionalInfo, postTitle;
    RadioGroup externalToilet, postedBy, postType;
    RadioButton externalToiletSelected, postBySelected, postTypeSelected;
    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_create_house_apartment);


        //remomve landscape mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //hooks
        rooms = findViewById(R.id.room_number);
        postTitle = findViewById(R.id.posting_title);
        parlour = findViewById(R.id.palour_number);
        internalToilet = findViewById(R.id.internal_toilet);
        externalToilet = findViewById(R.id.radio_group_external_toilet);
        postType = findViewById(R.id.radio_group_post_type);
        price = findViewById(R.id.month_price);
        caution = findViewById(R.id.caution_fee);
        advancedPayment = findViewById(R.id.advance_payment);
        apartmentName = findViewById(R.id.apartment_name);
        postedBy = findViewById(R.id.radio_group_posted_by);
        countryCodePicker = findViewById(R.id.country_code_picker_apartment);
        phoneNumber = findViewById(R.id.phone_number_apartment);
        additionalInfo = findViewById(R.id.additional_info);
    }

    public void goToNextActivity(View view){

        if ( !validatePostType() | !validatePostTitle() | !validateToilet() | !validatePostedBy() | !validateRooms() | !validatePalour() | !validateInternalToilet() | !validatePrice() | !validateCaution() | !validateAdvancePayment() | !validateApartmentName() | !validatePhoneNumber()){
            return;
        }

        //get data from previous activity
        String _category = getIntent().getStringExtra("category");
        String _region = getIntent().getStringExtra("region");
        String _division = getIntent().getStringExtra("division");
        String _quater = getIntent().getStringExtra("quater");

        //get input data
        String _rooms = Objects.requireNonNull(rooms.getEditText()).getText().toString().trim();
        String _palour = Objects.requireNonNull(parlour.getEditText()).getText().toString().trim();
        String _internalToilet = Objects.requireNonNull(internalToilet.getEditText()).getText().toString().trim();
        String _caution = Objects.requireNonNull(caution.getEditText()).getText().toString().trim();
        String _advancedPayment = Objects.requireNonNull(advancedPayment.getEditText()).getText().toString().trim();
        String _apartmentName = Objects.requireNonNull(apartmentName.getEditText()).getText().toString().trim();
        String _additionalInfo = Objects.requireNonNull(additionalInfo.getEditText()).getText().toString().trim();
        String _postTitle = Objects.requireNonNull(postTitle.getEditText()).getText().toString().trim();

        //getting data from radio groups
        postTypeSelected = findViewById(postType.getCheckedRadioButtonId());
        String _postType = postTypeSelected.getText().toString();

        externalToiletSelected = findViewById(externalToilet.getCheckedRadioButtonId());
        String _externalToilet = externalToiletSelected.getText().toString();

        postBySelected = findViewById(postedBy.getCheckedRadioButtonId());
        String _postedBy = postBySelected.getText().toString();

        //get price and add FCFA
        String _price0 = Objects.requireNonNull(price.getEditText()).getText().toString().trim();
        String _price = _price0+"FCFA";

        //get phone number
        String _getUserEnteredPhoneNumber = Objects.requireNonNull(phoneNumber.getEditText()).getText().toString().trim();
        String _phoneNo = "+"+countryCodePicker.getFullNumber()+_getUserEnteredPhoneNumber;


        Intent intent = new Intent(getApplicationContext(), GeoLocation01.class);

        String comeFrom = "other";
        intent.putExtra("postType", _postType);
        intent.putExtra("rooms", _rooms);
        intent.putExtra("parlour", _palour);
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
        intent.putExtra("phoneNumCall", _phoneNo);
        intent.putExtra("additionalInfo", _additionalInfo);
        intent.putExtra("postTitle", _postTitle);
        intent.putExtra("category", _category);
        intent.putExtra("comingFrom", comeFrom);


        startActivity(intent);


    }

    private boolean validatePostType() {
        if (postType.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please choose the Post Type", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validatePostedBy() {
        if (postedBy.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select who is posting the Ad", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateToilet() {
        if (externalToilet.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please choose if external toilet are available or not", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateRooms() {

        String val = Objects.requireNonNull(rooms.getEditText()).getText().toString().trim();

        if (val.isEmpty()) {
            rooms.setError("Field cannot be empty");
            return false;
        } else {
            rooms.setError(null);
            rooms.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validatePalour() {

        String val = Objects.requireNonNull(parlour.getEditText()).getText().toString().trim();

        if (val.isEmpty()) {
            parlour.setError("Field cannot be empty");
            return false;
        } else {
            parlour.setError(null);
            parlour.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateInternalToilet() {

        String val = Objects.requireNonNull(internalToilet.getEditText()).getText().toString().trim();

        if (val.isEmpty()) {
            internalToilet.setError("Field cannot be empty");
            return false;
        } else {
            internalToilet.setError(null);
            internalToilet.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validatePrice() {

        String val = Objects.requireNonNull(price.getEditText()).getText().toString().trim();
        String checkspaces = "\\A\\w{1,20}\\z";
        if (val.isEmpty()) {
            price.setError("Enter valid phone number");
            return false;
        } else if (!val.matches(checkspaces)) {
            price.setError("No White spaces are allowed!");
            return false;
        } else {
            price.setError(null);
            price.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateCaution() {

        String val = Objects.requireNonNull(caution.getEditText()).getText().toString().trim();

        if (val.isEmpty()) {
            caution.setError("Field cannot be empty");
            return false;
        } else {
            caution.setError(null);
            caution.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateAdvancePayment() {

        String val = Objects.requireNonNull(advancedPayment.getEditText()).getText().toString().trim();

        if (val.isEmpty()) {
            advancedPayment.setError("Field cannot be empty");
            return false;
        } else {
            advancedPayment.setError(null);
            advancedPayment.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateApartmentName() {

        String val = Objects.requireNonNull(apartmentName.getEditText()).getText().toString().trim();

        if (val.isEmpty()) {
            apartmentName.setError("Field cannot be empty");
            return false;
        } else {
            apartmentName.setError(null);
            apartmentName.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validatePostTitle() {

        String val = Objects.requireNonNull(postTitle.getEditText()).getText().toString().trim();

        if (val.isEmpty()) {
            postTitle.setError("Field cannot be empty");
            return false;
        } else {
            postTitle.setError(null);
            postTitle.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validatePhoneNumber() {
        String val = Objects.requireNonNull(phoneNumber.getEditText()).getText().toString().trim();
        String checkspaces = "\\A\\w{1,20}\\z";
        if (val.isEmpty()) {
            phoneNumber.setError("Enter valid phone number");
            return false;
        } else if (!val.matches(checkspaces)) {
            phoneNumber.setError("No White spaces are allowed!");
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }

    public void goBack(View view) {
        startActivity(new Intent(getApplicationContext(), CreateItem2.class));
    }
}