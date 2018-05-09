package com.example.gmsproduction.dregypt.ui.fragments.Clinincs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.fragments.HospitalsFragment;

public class ClinicsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinics);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_cli,new SpecialClinicsFragment(),"SpecialClinicsFragment")
                .commit();

    }
}
