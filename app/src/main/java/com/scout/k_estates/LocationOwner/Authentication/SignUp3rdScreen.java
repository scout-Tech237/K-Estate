package com.scout.k_estates.LocationOwner.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.scout.k_estates.R;

import java.util.Objects;

public class SignUp3rdScreen extends AppCompatActivity {

    ImageView backBtn;
    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up3rd_screen);

        //remomve landscape mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Hooks
        backBtn = findViewById(R.id.signup_back_button);
        countryCodePicker = findViewById(R.id.country_code_picker);
        phoneNumber = findViewById(R.id.signup_phone_number);

        //bact btn function
        backBtn.setOnClickListener(view -> SignUp3rdScreen.super.onBackPressed());
    }

    public void callVerifyOTPScreen(View view) {

        //validate phone number
        if (!validatePhoneNumber()){
            return;
        }

        //get all data from previous activities
        String _fullname = getIntent().getStringExtra("fullname");
        String _username = getIntent().getStringExtra("username");
        String _email = getIntent().getStringExtra("email");
        String _password = getIntent().getStringExtra("password");
        String _date = getIntent().getStringExtra("date");
        String _gender = getIntent().getStringExtra("gender");


        //get phone number
        String _getUserEnteredPhoneNumber = Objects.requireNonNull(phoneNumber.getEditText()).getText().toString().trim();
        String _phoneNo = "+"+countryCodePicker.getFullNumber()+_getUserEnteredPhoneNumber;


        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);

        //check if number already exist in data

        //pass data to next activity
        intent.putExtra("fullname", _fullname);
        intent.putExtra("username", _username);
        intent.putExtra("email", _email);
        intent.putExtra("password", _password);
        intent.putExtra("date", _date);
        intent.putExtra("gender", _gender);
        intent.putExtra("phoneNo", _phoneNo);
        intent.putExtra("whatToDo", "newUser");

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.signup_next_btn) ,"transition_opt_screen");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp3rdScreen.this, pairs);
        startActivity(intent, options.toBundle());



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