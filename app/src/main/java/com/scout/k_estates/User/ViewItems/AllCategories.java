package com.scout.k_estates.User.ViewItems;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.scout.k_estates.R;

public class AllCategories extends AppCompatActivity {

    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_all_categories);

        //Hooks
        backBtn = findViewById(R.id.back_pressed);
        //remomve landscape mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        backBtn.setOnClickListener(view -> AllCategories.super.onBackPressed());
    }

    public void goToPlots(View view) {
        Intent intent = new Intent(getApplicationContext(), Search.class);
        String category = "Plots";
        intent.putExtra("Category", category);
        startActivity(intent);
    }

    public void goToBusinessPlaces(View view) {
        Intent intent = new Intent(getApplicationContext(), Search.class);
        String category = "Business Places";
        intent.putExtra("Category", category);
        startActivity(intent);
    }

    public void goToGuestHouses(View view) {
        Intent intent = new Intent(getApplicationContext(), Search.class);
        String category = "Guest Houses";
        intent.putExtra("Category", category);
        startActivity(intent);
    }

    public void goToApartments(View view) {
        Intent intent = new Intent(getApplicationContext(), Search.class);
        String category = "Apartments";
        intent.putExtra("Category", category);
        startActivity(intent);
    }
}