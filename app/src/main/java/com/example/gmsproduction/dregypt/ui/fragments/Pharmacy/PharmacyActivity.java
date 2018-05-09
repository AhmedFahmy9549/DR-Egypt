package com.example.gmsproduction.dregypt.ui.fragments.Pharmacy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.fragments.Clinincs.SpecialClinicsFragment;

public class PharmacyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_pha,new RegionPharmacyFragment(),"RegionPharmacyFragment")
                .commit();
    }
}
