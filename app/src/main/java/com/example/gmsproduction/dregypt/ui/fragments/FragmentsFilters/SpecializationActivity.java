package com.example.gmsproduction.dregypt.ui.fragments.FragmentsFilters;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetHospitalSpecialitiesRequest;
import com.example.gmsproduction.dregypt.Models.LocationModel;
import com.example.gmsproduction.dregypt.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SpecializationActivity extends AppCompatActivity  {
    String TAG = "SpecialClinicsFragment";
    View view;

    ArrayList<LocationModel> arrayList;
    RecyclerView recyclerView;
    AdapterSpecializationRecylcer adapterx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialization);
        recyclerView = findViewById(R.id.special_recycler);


  /*      GetHospitalSpecialitiesRequest getHospitalSpecialitiesRequest = new GetHospitalSpecialitiesRequest(this, this, this);
        getHospitalSpecialitiesRequest.start();*/
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container12,new SpecializationsFragment(),"SpecialClinicsFragment")
                .commit();


    }



}
