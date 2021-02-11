package com.scout.k_estates.LocationOwner.CreatePlot;

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
import com.scout.k_estates.LocationOwner.CreateAccomodation.CreateItem2;
import com.scout.k_estates.LocationOwner.CreateAccomodation.GeoLocation01;
import com.scout.k_estates.R;

import java.util.Objects;

public class CreatePlot1 extends AppCompatActivity {

    RadioGroup postType, postedBy;
    RadioButton postTypeSelected, postBySelected;
    TextInputLayout postTitle, dimension, price, phoneNumber, additionalInfo;
    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_create_plot1);

        //remomve landscape mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //hooks
        postTitle = findViewById(R.id.posting_title_plot);
        dimension = findViewById(R.id.posting_dimensions);
        price = findViewById(R.id.month_price_plot);
        phoneNumber = findViewById(R.id.phone_number_plot);
        additionalInfo = findViewById(R.id.additional_info_plot);
        postedBy = findViewById(R.id.radio_group_posted_by_plot);
        postType = findViewById(R.id.radio_group_post_type_plot);
        countryCodePicker = findViewById(R.id.country_code_picker_plot);
    }

    public void goBack(View view) {
        startActivity(new Intent(getApplicationContext(), CreateItem2.class));
    }

    public void goToCreatePlot2(View view) {

        //validate fields
        if (!validateDimension() | !validatePhoneNumber() | !validatePostedBy() | !validatePostTitle() | !validatePostType() | !validatePrice()) {
            return;
        }

        //get data from previous activity
        String _category = getIntent().getStringExtra("category");
        String _region = getIntent().getStringExtra("region");
        String _division = getIntent().getStringExtra("division");
        String _quater = getIntent().getStringExtra("quater");

        //get input data
        String _additionalInfo = Objects.requireNonNull(additionalInfo.getEditText()).getText().toString().trim();
        String _postTitle = Objects.requireNonNull(postTitle.getEditText()).getText().toString().trim();
        String _dimension = Objects.requireNonNull(dimension.getEditText()).getText().toString().trim();

        //getting data from radio groups
        postTypeSelected = findViewById(postType.getCheckedRadioButtonId());
        String _postType = postTypeSelected.getText().toString();

        postBySelected = findViewById(postedBy.getCheckedRadioButtonId());
        String _postedBy = postBySelected.getText().toString();

        //get price and add FCFA
        String _price0 = Objects.requireNonNull(price.getEditText()).getText().toString().trim();
        String _price = _price0 + "FCFA";

        //get phone number
        String _getUserEnteredPhoneNumber = Objects.requireNonNull(phoneNumber.getEditText()).getText().toString().trim();
        String _phoneNo = "+" + countryCodePicker.getFullNumber() + _getUserEnteredPhoneNumber;

        //go to next activity
        Intent intent = new Intent(getApplicationContext(), GeoLocation01.class);

        intent.putExtra("postedBy", _postedBy);
        intent.putExtra("phoneNumCall", _phoneNo);
        intent.putExtra("additionalInfo", _additionalInfo);
        intent.putExtra("postTitle", _postTitle);
        intent.putExtra("category", _category);
        intent.putExtra("region", _region);
        intent.putExtra("division", _division);
        intent.putExtra("quater", _quater);
        intent.putExtra("price", _price);
        intent.putExtra("postType", _postType);
        intent.putExtra("dimension", _dimension);
        intent.putExtra("comingFrom", "plot");

        startActivity(intent);

    }

    //vallidations of fields
    private boolean validatePostType() {
        if (postType.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please choose the Post Type", Toast.LENGTH_SHORT).show();
            return false;
        } else {
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

    private boolean validateDimension() {

        String val = Objects.requireNonNull(dimension.getEditText()).getText().toString().trim();

        if (val.isEmpty()) {
            dimension.setError("Field cannot be empty");
            return false;
        } else {
            dimension.setError(null);
            dimension.setErrorEnabled(false);
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

    private boolean validatePostedBy() {
        if (postedBy.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select who is posting the Ad", Toast.LENGTH_SHORT).show();
            return false;
        } else {
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
}