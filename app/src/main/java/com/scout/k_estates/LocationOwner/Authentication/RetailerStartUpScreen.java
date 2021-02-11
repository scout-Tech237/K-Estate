package com.scout.k_estates.LocationOwner.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.scout.k_estates.Common.OnBoarding;
import com.scout.k_estates.R;

public class RetailerStartUpScreen extends AppCompatActivity {

    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_retailer_start_up_screen);

        //remomve landscape mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Hooks
        backBtn = findViewById(R.id.back_pressed);

        backBtn.setOnClickListener(view -> RetailerStartUpScreen.super.onBackPressed());

    }


    public void callLoginScreen(View view){

        Intent intent = new Intent(getApplicationContext(), Login.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.signup_btn) ,"transition_login");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RetailerStartUpScreen.this, pairs);
        startActivity(intent, options.toBundle());
    }

    public void callSignupScreen(View view){

        Intent intent = new Intent(getApplicationContext(), SignUp.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.signup_btn) ,"transition_signup");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RetailerStartUpScreen.this, pairs);
        startActivity(intent, options.toBundle());

    }

    public void goToBoarding(View view) {
        startActivity(new Intent(getApplicationContext(), OnBoarding.class));
    }
}