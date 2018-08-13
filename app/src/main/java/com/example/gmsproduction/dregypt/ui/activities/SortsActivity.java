package com.example.gmsproduction.dregypt.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.fragments.Filters.ClinicsFilters;
import com.example.gmsproduction.dregypt.ui.fragments.Filters.CosmeticsFilters;
import com.example.gmsproduction.dregypt.ui.fragments.Filters.HospitalFilters;
import com.example.gmsproduction.dregypt.ui.fragments.Filters.JobsFilters;
import com.example.gmsproduction.dregypt.ui.fragments.Filters.PharmacyFilters;
import com.example.gmsproduction.dregypt.ui.fragments.Filters.ProductFilters;

import java.util.HashMap;

public class SortsActivity extends BaseActivity {
    public int language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        Intent intent = getIntent();

        getLanguage();

        language = getIdLANG();
        localization(language);
        setActivityTitle("ترتيب","Sorts");


        int id = intent.getIntExtra("idSorts", 0);

        switch (id) {
            case 1:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_sorts, new HospitalFilters(), "HospitalSorts")
                        .commit();
                break;
            case 2:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_sorts, new ClinicsFilters(), "ClinicsSorts")
                        .commit();
                break;
            case 3:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_sorts, new PharmacyFilters(), "PharmacySorts")
                        .commit();
                break;
            case 4:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_sorts, new ProductFilters(), "ProductSorts")
                        .commit();
                break;
            case 5:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_sorts, new CosmeticsFilters(), "CosmeticsSorts")
                        .commit();
                break;
            case 6:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_sorts, new JobsFilters(), "JobsSorts")
                        .commit();
                break;

        }

    }

    public int getLanguage() {
        language = getIdLANG();
        localization(language);

        return language;
    }


}
