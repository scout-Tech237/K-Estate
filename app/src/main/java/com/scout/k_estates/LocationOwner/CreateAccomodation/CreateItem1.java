package com.scout.k_estates.LocationOwner.CreateAccomodation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.scout.k_estates.R;
import com.scout.k_estates.User.UserDashboard;

public class CreateItem1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_create_item1);

        //remomve landscape mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void goBack(View view) {
        startActivity(new Intent(getApplicationContext(), UserDashboard.class));
    }

    public void postApartment(View view) {
        Intent intent1 = new Intent(getApplicationContext(), CreateItem2.class);
        intent1.putExtra("category", "Apartment");
        startActivity(intent1);
        finish();
    }

    public void postGuestHouse(View view) {
        Intent intent0 = new Intent(getApplicationContext(), CreateItem2.class);
        intent0.putExtra("category", "Guest House");
        startActivity(intent0);
        finish();
    }

    public void postBusinessPlace(View view) {
        Intent intent2 = new Intent(getApplicationContext(), CreateItem2.class);
        intent2.putExtra("category", "Business Place");
        startActivity(intent2);
        finish();
    }

    public void postPlot(View view) {
        Intent intent3 = new Intent(getApplicationContext(), CreateItem2.class);
        intent3.putExtra("category", "Plot");
        startActivity(intent3);
        finish();
    }
}