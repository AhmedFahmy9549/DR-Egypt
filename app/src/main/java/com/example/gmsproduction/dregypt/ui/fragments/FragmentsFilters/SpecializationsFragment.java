package com.example.gmsproduction.dregypt.ui.fragments.FragmentsFilters;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetHospitalSpecialitiesRequest;
import com.example.gmsproduction.dregypt.R;

/**
 * Created by Ahmed Fahmy on 5/6/2018.
 */

public class SpecializationsFragment extends Fragment implements Response.Listener<String>, Response.ErrorListener {

    String TAG="SpecializationsFragment";
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView;
        recyclerView = getActivity().findViewById(R.id.special_recycler);
        return inflater.inflate(R.layout.specialization_fragment, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        GetHospitalSpecialitiesRequest getHospitalSpecialitiesRequest = new GetHospitalSpecialitiesRequest(getActivity(), this, this);
        getHospitalSpecialitiesRequest.start();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(TAG, "onResponse: ," + error);

    }

    @Override
    public void onResponse(String response) {
        Log.e(TAG, "onResponse: ," + response);
    }
}
