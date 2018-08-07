package com.example.gmsproduction.dregypt.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;

import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.fragments.Filters.ClinicsFilters;
import com.example.gmsproduction.dregypt.ui.fragments.Filters.CosmeticsFilters;
import com.example.gmsproduction.dregypt.ui.fragments.Filters.HospitalFilters;
import com.example.gmsproduction.dregypt.ui.fragments.Filters.JobsFilters;
import com.example.gmsproduction.dregypt.ui.fragments.Filters.PharmacyFilters;
import com.example.gmsproduction.dregypt.ui.fragments.Filters.ProductFilters;

public class FiltersActivity extends BaseActivity {

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
                        .replace(R.id.container_filter,new HospitalFilters(),"HospitalFilters")
                        .commit();
                break;
            case 2:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_filter,new ClinicsFilters(),"ClinicsFilters")
                        .commit();
                break;
            case 3:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_filter,new PharmacyFilters(),"PharmacyFilters")
                        .commit();
                break;
            case 4:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_filter,new ProductFilters(),"ProductFilters")
                        .commit();
                break;
            case 5:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_filter,new CosmeticsFilters(),"CosmeticsFilters")
                        .commit();
                break;
            case 6:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_filter,new JobsFilters(),"JobsFilters")
                        .commit();
                break;

        }


    }
}
