package com.example.gmsproduction.dregypt.ui.fragments.FragmentsFilters;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetRegionsRequest;
import com.example.gmsproduction.dregypt.Models.LocationModel;
import com.example.gmsproduction.dregypt.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ahmed Fahmy on 5/13/2018.
 */

public class Filters extends Fragment implements Response.Listener<String>,Response.ErrorListener{
    View view;
Spinner spinner;
ArrayList<LocationModel>array;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_filter_hospital, container, false);
        spinner=view.findViewById(R.id.spinner_city);


        GetRegionsRequest getRegionsRequest = new GetRegionsRequest(getActivity(), this, this);
        getRegionsRequest.start();



        return  view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {
        array = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String specName = object.getString("en_name");
                int regionId = object.getInt("id");

                LocationModel model=new LocationModel(specName,regionId);


                array.add(model);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Creating adapter for spinner
        ArrayAdapter<LocationModel> dataAdapter = new ArrayAdapter<LocationModel>(getActivity(), android.R.layout.simple_spinner_item, array);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }


}
