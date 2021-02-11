package com.scout.k_estates.LocationOwner.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.scout.k_estates.R;

import java.util.Objects;

public class SignUp extends AppCompatActivity {

    //Variable
    ImageView backBtn;
    Button next, login;
    TextView titleText;

    //Get Data Variable
    TextInputLayout fullname, username, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);


        //remove landscape mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Hooks
        backBtn = findViewById(R.id.signup_back_button);
        next = findViewById(R.id.signup_next_btn);
        login = findViewById(R.id.signup_login_btn);
        titleText = findViewById(R.id.signup_title_text);

        //Hooks for getting data
        fullname = findViewById(R.id.sigup_fullname);
        username = findViewById(R.id.signup_username);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);

        //back btn function
        backBtn.setOnClickListener(view -> SignUp.super.onBackPressed());
    }

    public void callNextSignupScreen(View view) {

        //checking validation
        if (!validateFullName() | !validateUsername() | !validateEmail() | !validatePassword()){
            return;
        }

        //data to be passed
        String fullnamepassed = Objects.requireNonNull(fullname.getEditText()).getText().toString().trim();
        String usernamepassed = Objects.requireNonNull(username.getEditText()).getText().toString().trim();
        String emailpassed = Objects.requireNonNull(email.getEditText()).getText().toString().trim();
        String passwordpassed = Objects.requireNonNull(password.getEditText()).getText().toString().trim();

        Intent intent = new Intent(getApplicationContext(), SignUp2ndClass.class);

        intent.putExtra("fullname", fullnamepassed);
        intent.putExtra("username", usernamepassed);
        intent.putExtra("email", emailpassed);
        intent.putExtra("password", passwordpassed);

        //Add Transition
        Pair[] pairs = new Pair[4];
        pairs[0] = new Pair<View, String>(backBtn, "transition_back_arrow_btn");
        pairs[1] = new Pair<View, String>(next, "transition_next_btn");
        pairs[2] = new Pair<View, String>(login, "transition_login_btn");
        pairs[3] = new Pair<View, String>(titleText, "transition_title_text");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
        startActivity(intent, options.toBundle());
    }

    /* Validation functions*/
    private boolean validateFullName() {

        String val = Objects.requireNonNull(fullname.getEditText()).getText().toString().trim();

        if (val.isEmpty()) {
            fullname.setError("Field cannot be empty");
            return false;
        } else {
            fullname.setError(null);
            fullname.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateUsername() {

        String val = Objects.requireNonNull(username.getEditText()).getText().toString().trim();
        String checkspaces = "\\A\\w{1,20}\\z";

        if (val.isEmpty()) {
            username.setError("Field cannot be empty");
            return false;
        } else if (val.length() > 20) {
            username.setError("Username is too long!");
            return false;
        } else if (!val.matches(checkspaces)) {
            username.setError("No empty(white) spaces are allowed");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateEmail() {

        String val = Objects.requireNonNull(email.getEditText()).getText().toString().trim();
        String checkemail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(checkemail)) {
            email.setError("Invalid Email");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validatePassword() {

        String val = Objects.requireNonNull(password.getEditText()).getText().toString().trim();
        String checkpassword = "^" +
                "(?=.*[a-zA-Z])" + //Any letter
                "(?=\\S+$)" +      //no white spaces
                ".{4,}" +          //at least 4 characters
                "$";

        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(checkpassword)) {
            password.setError("Password should contain 4 characters!");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }

    }
}