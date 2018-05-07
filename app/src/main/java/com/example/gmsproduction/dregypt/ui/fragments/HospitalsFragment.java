package com.example.gmsproduction.dregypt.ui.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetCitiesRequest;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.HospitalsRequests.GetHospitalsRequest;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.HospitalsRequests.SearchHospitalsRequest;
import com.example.gmsproduction.dregypt.R;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class HospitalsFragment extends Fragment implements Response.Listener<String>, Response.ErrorListener {
    RecyclerView recyclerView;
    View view;
    String TAG = "HospitalsFragment";
    HashMap<String, String> parms = new HashMap<>();

    public HospitalsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_hospitals, container, false);
        recyclerView = view.findViewById(R.id.hospital_recycler);


        SharedPreferences prefs = getActivity().getSharedPreferences("Location", MODE_PRIVATE);
        int city_id = prefs.getInt("city_id", 0); //0 is the default value.
        int region_id = prefs.getInt("region_id", 0);
        int special_id = prefs.getInt("specialId", 0);

        parms.put("region", String.valueOf(region_id));
        parms.put("city", String.valueOf(city_id));
        parms.put("speciality",String.valueOf(special_id));

        Log.e(TAG, "Region=" + region_id + "  City=" + city_id + "    SpecialName=" + special_id);


        SearchHospitalsRequest searchHospitalsRequest = new SearchHospitalsRequest(getActivity(), this, this);
        searchHospitalsRequest.setBody(parms);
        searchHospitalsRequest.start();
        return view;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(TAG, "Response=" + error.toString());

    }

    @Override
    public void onResponse(String response) {
        Log.e(TAG, "Response=" + response);


    }
}