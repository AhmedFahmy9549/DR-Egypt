package com.example.gmsproduction.dregypt.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.fragments.FragmentsFilters.Filters;

public class FiltersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_filter,new Filters(),"Filters")
                .commit();
    }
}
