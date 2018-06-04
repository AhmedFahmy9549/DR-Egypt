package com.example.gmsproduction.dregypt.ui.fragments.Clinincs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetRegionsRequest;
import com.example.gmsproduction.dregypt.Models.LocationModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.adapters.AdapterLocationClinicRecylcer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ahmed Fahmy on 5/6/2018.
 */

public class RegionClinicFragment extends Fragment implements Response.Listener<String>,Response.ErrorListener{
    View view;
    String TAG = "RegionFragment";
    ArrayList<LocationModel> arrayList;
    RecyclerView recyclerView;
    AdapterLocationClinicRecylcer adapterx;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.region_fragment, container, false);
        getActivity().setTitle("Select Region");
        recyclerView = view.findViewById(R.id.region_recycler);

        GetRegionsRequest getRegionsRequest = new GetRegionsRequest(getActivity(), this, this);
        getRegionsRequest.start();
        return view;
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
        arrayList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String specName = object.getString("en_name");
                int regionId = object.getInt("id");

                LocationModel model=new LocationModel(specName,regionId);


                arrayList.add(model);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapterx= new AdapterLocationClinicRecylcer(getActivity(),arrayList,1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterx);
    }
}
