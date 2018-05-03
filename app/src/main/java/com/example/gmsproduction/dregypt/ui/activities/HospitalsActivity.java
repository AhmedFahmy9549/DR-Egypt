package com.example.gmsproduction.dregypt.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.gmsproduction.dregypt.R;

public class HospitalsActivity extends AppCompatActivity {
    float x ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.row_hospitals);


        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setRating(x);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

               /* Toast.makeText(HospitalsActivity.this,
                        "Rating changed, current rating " + ratingBar.getRating(),
                        Toast.LENGTH_SHORT).show();*/


            }
        });

    }
}
