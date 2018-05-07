package com.example.gmsproduction.dregypt.ui.fragments.FragmentsFilters;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.gmsproduction.dregypt.R;

public class RegionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container10,new RegionFragment(),"RegionFragment")
                .commit();

    }
}
