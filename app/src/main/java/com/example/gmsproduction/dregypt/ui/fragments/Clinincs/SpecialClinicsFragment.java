package com.example.gmsproduction.dregypt.ui.fragments.Clinincs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetClinicSpecialitiesRequest;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetHospitalSpecialitiesRequest;
import com.example.gmsproduction.dregypt.Models.LocationModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.fragments.FragmentsFilters.AdapterSpecializationRecylcer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ahmed Fahmy on 5/6/2018.
 */

public class SpecialClinicsFragment extends Fragment implements Response.Listener<String>, Response.ErrorListener {
    String TAG = "SpecialClinicsFragment";
    View view;

    ArrayList<LocationModel> arrayList;
    RecyclerView recyclerView;
    AdapterSpecialClinicsRecylcer adapterx;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.specialization_fragment, container, false);

        getActivity().setTitle("Select Specialization");

        recyclerView = (RecyclerView) view.findViewById(R.id.special_recycler);
        GetClinicSpecialitiesRequest getClinicSpecialitiesRequest = new GetClinicSpecialitiesRequest(getActivity(), this, this);
        getClinicSpecialitiesRequest.start();
        return view;
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(TAG, "onResponse: ," + error);

    }

    @Override
    public void onResponse(String response) {

        Log.e(TAG, "onResponse: ," + response);
        arrayList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String specName = object.getString("en_name");
                int specId = object.getInt("id");
                LocationModel model = new LocationModel(specName, specId);

                arrayList.add(model);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapterx = new AdapterSpecialClinicsRecylcer(getActivity(), arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterx);
    }
}
