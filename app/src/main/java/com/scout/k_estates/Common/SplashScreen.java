package com.scout.k_estates.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.scout.k_estates.R;
import com.scout.k_estates.User.UserDashboard;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_TIMER = 5000;

    //variables
    ImageView backgroundImage;
    TextView poweredByLine;

    //variable for animation
    Animation sideAnim, bottomAnim;
    SharedPreferences onBoardingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);


        //remomve landscape mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //hooks
        backgroundImage = findViewById(R.id.background_image);
        poweredByLine = findViewById(R.id.powered_by_line);

        //Animation hooks
        sideAnim = AnimationUtils.loadAnimation(this, R.anim.side_anim);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        //set Animations on elements
        backgroundImage.setAnimation(sideAnim);
        poweredByLine.setAnimation(bottomAnim);

        new Handler().postDelayed(() -> {

            onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);

            boolean isFirstime = onBoardingScreen.getBoolean("firstTime", true);

            if (isFirstime){

                SharedPreferences.Editor editor = onBoardingScreen.edit();
                editor.putBoolean("firstTime", false);
                editor.apply();



                Intent intent = new Intent(getApplicationContext(), OnBoarding.class);
                startActivity(intent);

            }
            else {
                Intent intent = new Intent(getApplicationContext(), UserDashboard.class);
                startActivity(intent);
            }
            finish();

        }, SPLASH_TIMER);

    }

}