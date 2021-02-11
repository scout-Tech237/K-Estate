package com.scout.k_estates.LocationOwner.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scout.k_estates.R;

import java.util.Objects;

public class SetNewPassword extends AppCompatActivity {

    TextInputLayout password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_set_new_password);

        //remomve landscape mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //hooks
        password = findViewById(R.id.new_password_1);
        confirmPassword = findViewById(R.id.new_password_2);



        //do animation
    }

    public void setNewPasswordBtn(View view){

        //check internet

        //validate password
        if (!validateNewPassword() | !validateConfirmPassword()){
            return;
        }
        //set progressbar visibility visible


        //get data
        String _newPassword = Objects.requireNonNull(password.getEditText()).getText().toString().trim();
        String _phoneNo = getIntent().getStringExtra("phoneNo");

        //Update to database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(_phoneNo).child("password").setValue(_newPassword);

        startActivity(new Intent(getApplicationContext(), ForgotPasswordSuccessMessage.class));
        finish();


    }

    //validate new password
    private boolean validateNewPassword() {

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

    private boolean validateConfirmPassword() {

        String val = Objects.requireNonNull(confirmPassword.getEditText()).getText().toString().trim();
        String checkpassword = "^" +
                "(?=.*[a-zA-Z])" + //Any letter
                "(?=\\S+$)" +      //no white spaces
                ".{4,}" +          //at least 4 characters
                "$";

        if (val.isEmpty()) {
            confirmPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(checkpassword)) {
            confirmPassword.setError("Password should contain 4 characters!");
            return false;
        } else {
            confirmPassword.setError(null);
            confirmPassword.setErrorEnabled(false);
            return true;
        }

    }
}