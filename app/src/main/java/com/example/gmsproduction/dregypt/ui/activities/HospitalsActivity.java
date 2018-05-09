package com.example.gmsproduction.dregypt.ui.activities;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.fragments.HospitalsFragment;

public class HospitalsActivity extends AppCompatActivity {
    float x ;
    ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitals);
        setTitle("Hospitals");

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_hos,new HospitalsFragment(),"HospitalsFragment")
                .commit();


/*

        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setRating(x);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

               */
/* Toast.makeText(HospitalsActivity.this,
                        "Rating changed, current rating " + ratingBar.getRating(),
                        Toast.LENGTH_SHORT).show();*//*



            }
        });

        toggleButton = (ToggleButton) findViewById(R.id.myToggleButton);
        toggleButton.setChecked(false);
        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_black_24dp));
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_favorite_black_24dp_fill));
                else
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_black_24dp));

                //getSupportFragmentManager().beginTransaction().add(new SpecializationFragments(),"spec").commit();
            }
        });
*/

    }
}
