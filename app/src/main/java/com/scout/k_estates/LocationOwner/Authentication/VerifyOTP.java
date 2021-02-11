package com.scout.k_estates.LocationOwner.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scout.k_estates.HelperClasses.UserHelperClass;
import com.scout.k_estates.R;
import com.scout.k_estates.User.UserDashboard;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class VerifyOTP extends AppCompatActivity {

    PinView pinFromUser;
    String verificationId;
    String fullName, username, email, password, date, gender, phoneNo, whatToDo;
    FirebaseAuth fAuth;
    PhoneAuthProvider.ForceResendingToken token;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    Button resendOTP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verify_o_t_p);

        //remomve landscape mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //hooks
        pinFromUser = findViewById(R.id.pin_view);
        resendOTP = findViewById(R.id.resend_otp);
        fAuth = FirebaseAuth.getInstance();


        //get all data from previous activity
        fullName = getIntent().getStringExtra("fullname");
        username = getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        date = getIntent().getStringExtra("date");
        gender = getIntent().getStringExtra("gender");
        phoneNo = getIntent().getStringExtra("phoneNo");
        whatToDo = getIntent().getStringExtra("whatToDo");

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                authenticateUser(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(VerifyOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationId = s;
                token = forceResendingToken;
                resendOTP.setEnabled(false);
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                resendOTP.setEnabled(true);
            }

        };

        verifyPhoneNumber(phoneNo);
    }

    public void verifyPhoneNumber(String phoneNumber){
        //send otp
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(fAuth)
                .setActivity(this)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(callbacks)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    public void authenticateUser(PhoneAuthCredential credential){

        fAuth.signInWithCredential(credential).addOnSuccessListener(authResult -> {

            //checking if coming from forgot password screen
            if (whatToDo.equals("updateData")) {
                updateOldUserData();
            } else {
                //save data to realtime db
                storeNewUsersData();
            }


        }).addOnFailureListener(e -> Toast.makeText(VerifyOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void updateOldUserData() {
        Intent intent = new Intent(getApplicationContext(), SetNewPassword.class);
        intent.putExtra("phoneNo", phoneNo);
        startActivity(intent);
        finish();
    }

    private void storeNewUsersData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");


        UserHelperClass addNewUser = new UserHelperClass(fullName, username, email, password, date, gender, phoneNo);

        reference.child(phoneNo).setValue(addNewUser);

        startActivity(new Intent(getApplicationContext(), UserDashboard.class));
        finish();
    }

    public void callNextScreenFromOTP(View view) {
        //get otp
        if (Objects.requireNonNull(pinFromUser.getText()).toString().isEmpty()){
            Toast.makeText(this, "Enter the Code sent to you number!", Toast.LENGTH_LONG).show();
            return;
        }

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, pinFromUser.getText().toString());
        authenticateUser(credential);
    }

    public void sendTextAgain(View view) {
        verifyPhoneNumber(phoneNo);
        resendOTP.setEnabled(false);
    }

    public void goToHomeFromOTP(View view) {
        startActivity(new Intent(getApplicationContext(), UserDashboard.class));
        finish();
    }
}