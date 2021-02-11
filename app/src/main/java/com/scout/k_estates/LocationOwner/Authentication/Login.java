package com.scout.k_estates.LocationOwner.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.scout.k_estates.HelperClasses.SessionManager;
import com.scout.k_estates.R;
import com.scout.k_estates.User.UserDashboard;

import java.util.HashMap;
import java.util.Objects;

public class Login extends AppCompatActivity {

    ImageView backBtn;
    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumber, password;
    RelativeLayout progressbar;
    CheckBox rememberMe;
    TextInputEditText phoneNumberEditText, passwordEditText ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //remomve landscape mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Hooks
        backBtn = findViewById(R.id.login_back_button);
        countryCodePicker = findViewById(R.id.login_country_code_picker);
        phoneNumber = findViewById(R.id.login_phone_number);
        password = findViewById(R.id.login_password);
        progressbar = findViewById(R.id.login_progress_bar);
        rememberMe = findViewById(R.id.remember_me);
        phoneNumberEditText = findViewById(R.id.login_phone_number_editText);
        passwordEditText = findViewById(R.id.login_password_editText);
        //make progressbar


        //check weather phone number and password is already saved in shared Prefrences or not
        SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_REMEMBERME);
        if (sessionManager.checkRememberMe()){
            HashMap<String, String> rememberMeDetails = sessionManager.getRememberMeDetailFromSession();
            phoneNumberEditText.setText(rememberMeDetails.get(SessionManager.KEY_SESSIONPHONENUMBER));
            passwordEditText.setText(rememberMeDetails.get(SessionManager.KEY_SESSIONPASSWORD));
        }

        backBtn.setOnClickListener(view -> Login.super.onBackPressed());

    }

    public void letTheUserLogIn(View view) {

        //check internet
        if (!isConnected(this)){
            showCustomDialog();
        }

        //validate fields
        if (!validateFields() | !validateFields1()) {
            return;
        }

        progressbar.setVisibility(View.VISIBLE);
        //get data
        String _phoneNumber = Objects.requireNonNull(phoneNumber.getEditText()).getText().toString().trim();
        String _password = Objects.requireNonNull(password.getEditText()).getText().toString().trim();

        /*if (_phoneNumber.charAt(0) == '0') {
            _phoneNumber = _phoneNumber.substring(1);
        }*/

        String _completePhoneNumber = "+" + countryCodePicker.getFullNumber() + _phoneNumber;

        if (rememberMe.isChecked()){
            SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_REMEMBERME);
            sessionManager.createRememberMeSession(_phoneNumber, _password);
        }

        //database
        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_completePhoneNumber);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    phoneNumber.setError(null);
                    phoneNumber.setErrorEnabled(false);

                    String systemPassword = snapshot.child(_completePhoneNumber).child("password").getValue(String.class);
                    if (Objects.equals(systemPassword, _password)) {
                        password.setError(null);
                        password.setErrorEnabled(false);

                        String _fullname = snapshot.child(_completePhoneNumber).child("fullName").getValue(String.class);
                        String _username = snapshot.child(_completePhoneNumber).child("username").getValue(String.class);
                        String _email = snapshot.child(_completePhoneNumber).child("email").getValue(String.class);
                        String _dateOfBirth = snapshot.child(_completePhoneNumber).child("date").getValue(String.class);
                        String _gender = snapshot.child(_completePhoneNumber).child("gender").getValue(String.class);
                        String _password = snapshot.child(_completePhoneNumber).child("password").getValue(String.class);
                        String _phoneNo = snapshot.child(_completePhoneNumber).child("phoneNo").getValue(String.class);

                        //create session
                        SessionManager sessionManager = new SessionManager(Login.this, SessionManager.SESSION_USERSESSION);
                        sessionManager.createLoginSession(_fullname, _username, _email, _phoneNo,_password,_dateOfBirth,_gender);

                        startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                        finish();

                        progressbar.setVisibility(View.GONE);


                    } else {
                        progressbar.setVisibility(View.GONE);
                        Toast.makeText(Login.this, "Password not correct", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressbar.setVisibility(View.GONE);
                    Toast.makeText(Login.this, "No such user exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressbar.setVisibility(View.GONE);
                Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    //check internet connection
    private boolean isConnected(Login login) {

        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected());

    }

    private void showCustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setMessage("Please connect to internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", (dialogInterface, i) -> startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)))
                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    startActivity(new Intent(getApplicationContext(), RetailerStartUpScreen.class));
                    finish();
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //validate fields
    private boolean validateFields() {

        String _phoneNumber = Objects.requireNonNull(phoneNumber.getEditText()).getText().toString().trim();

        if (_phoneNumber.isEmpty()) {
            phoneNumber.setError("Phone number cannot be empty");
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateFields1() {

        String _password = Objects.requireNonNull(password.getEditText()).getText().toString().trim();

        if (_password.isEmpty()) {
            password.setError("Phone number cannot be empty");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }

    }

    //forgot password function
    public void callForgotPassword(View view){
        startActivity(new Intent(getApplicationContext(), ForgotPassword.class));
    }

    public void goToSignUp(View view) {
        startActivity(new Intent(getApplicationContext(), SignUp.class));
        finish();
    }
}