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
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.scout.k_estates.R;

import java.util.Objects;

public class ForgotPassword extends AppCompatActivity {

    private TextInputLayout phoneNumberTextField;
    private CountryCodePicker countryCodePicker;
    RelativeLayout progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot_password);

        //remomve landscape mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //hooks
        phoneNumberTextField = findViewById(R.id.forgot_password_phone_number);
        countryCodePicker = findViewById(R.id.forgot_password_country_code_picker);
        ImageView backBtn = findViewById(R.id.forgot_password_back_btn);

        backBtn.setOnClickListener(view -> ForgotPassword.super.onBackPressed());
    }


    public void verifyPhoneNumber(View view){

        //check internet
        if (!isConnected(this)){
            showCustomDialog();
        }

        //validate number
        if (!validatePhoneNumber()){
            return;
        }
        //set progress bar

        //get data from fields
        String _phoneNumber = Objects.requireNonNull(phoneNumberTextField.getEditText()).getText().toString().trim();
        if (_phoneNumber.charAt(0) == '0') {
            _phoneNumber = _phoneNumber.substring(1);
        }
        final String _completePhoneNumber = "+"+countryCodePicker.getFullNumber()+_phoneNumber;

        //database
        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_completePhoneNumber);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    phoneNumberTextField.setError(null);
                    phoneNumberTextField.setErrorEnabled(false);

                    Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);
                    intent.putExtra("phoneNo", _completePhoneNumber);
                    intent.putExtra("whatToDo", "updateData");
                    startActivity(intent);
                    finish();

                }
                else {
                    progressbar.setVisibility(View.GONE);
                    phoneNumberTextField.setError("No such user exist");
                    phoneNumberTextField.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressbar.setVisibility(View.GONE);
                Toast.makeText(ForgotPassword.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    private boolean validatePhoneNumber() {
        String val = Objects.requireNonNull(phoneNumberTextField.getEditText()).getText().toString().trim();
        String checkspaces = "\\A\\w{1,20}\\z";
        if (val.isEmpty()) {
            phoneNumberTextField.setError("Enter valid phone number");
            return false;
        } else if (!val.matches(checkspaces)) {
            phoneNumberTextField.setError("No White spaces are allowed!");
            return false;
        } else {
            phoneNumberTextField.setError(null);
            phoneNumberTextField.setErrorEnabled(false);
            return true;
        }
    }

    private boolean isConnected(ForgotPassword forgotPassword) {

        ConnectivityManager connectivityManager = (ConnectivityManager) forgotPassword.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected());

    }

    private void showCustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassword.this);
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

}