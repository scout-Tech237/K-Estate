package com.scout.k_estates.Others;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;
import com.scout.k_estates.R;

public class RateUs extends AppCompatActivity {

    private ReviewManager manager;
    private ReviewInfo reviewInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_rate_us);

        //remomve landscape mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        rate();

    }

    private void rate() {

        manager = ReviewManagerFactory.create(this);
        Task<ReviewInfo> request = manager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // We can get the ReviewInfo object
                reviewInfo = task.getResult();
            } else {
                // There was some problem, continue regardless of the result.
                Toast.makeText(this, "Please due to technical errors we will prefer you rate us on Google Play Store! THANKS", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (reviewInfo!=null) {

            Task<Void> flow = manager.launchReviewFlow(RateUs.this, reviewInfo);
            flow.addOnCompleteListener(task -> {
                // The flow has finished. The API does not indicate whether the user
                // reviewed or not, or even whether the review dialog was shown. Thus, no
                // matter the result, we continue our app flow.
                if (task.isSuccessful()){
                    Toast.makeText(this, "THANKS, Review was successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Please due to technical errors we will prefer you rate us on Google Play Store! THANKS", Toast.LENGTH_SHORT).show();
                }

            });

            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }
}