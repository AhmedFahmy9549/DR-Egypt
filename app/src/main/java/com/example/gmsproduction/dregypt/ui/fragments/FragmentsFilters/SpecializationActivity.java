package com.example.gmsproduction.dregypt.ui.fragments.FragmentsFilters;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.gmsproduction.dregypt.R;

public class SpecializationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialization);

        getSupportFragmentManager().beginTransaction().add(new SpecializationsFragment(),"SpecializationsFragment").commit();
    }
}
