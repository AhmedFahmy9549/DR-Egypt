package com.example.gmsproduction.dregypt.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;

import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.fragments.Filters.ClinicsFilters;
import com.example.gmsproduction.dregypt.ui.fragments.Filters.HospitalFilters;

public class FiltersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        Intent intent=getIntent();

        int id=intent.getIntExtra("idFilter",0);

        switch (id){
            case 1:
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container_filter,new HospitalFilters(),"HospitalFilters")
                        .commit();
                break;
            case 2:
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container_filter,new ClinicsFilters(),"ClinicsFilters")
                        .commit();
                break;
            case 3:
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container_filter,new HospitalFilters(),"HospitalFilters")
                        .commit();
                break;
            case 4:
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container_filter,new HospitalFilters(),"HospitalFilters")
                        .commit();
                break;
            case 5:
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container_filter,new HospitalFilters(),"HospitalFilters")
                        .commit();
                break;
            case 6:
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container_filter,new HospitalFilters(),"HospitalFilters")
                        .commit();
                break;

        }


    }
}
