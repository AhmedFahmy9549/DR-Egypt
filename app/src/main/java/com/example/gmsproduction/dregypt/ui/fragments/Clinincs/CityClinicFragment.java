package com.example.gmsproduction.dregypt.ui.fragments.Clinincs;

import android.content.SharedPreferences;
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
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetCitiesRequest;
import com.example.gmsproduction.dregypt.Models.LocationModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.adapters.AdapterLocationClinicRecylcer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ahmed Fahmy on 5/6/2018.
 */

public class CityClinicFragment extends Fragment implements Response.Listener<String>, Response.ErrorListener {


    String TAG = "CityFragment";
    ArrayList<LocationModel> arrayList;
    View view;
    RecyclerView recyclerView;
    AdapterLocationClinicRecylcer adapterx;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.city_fragment, container, false);
        recyclerView = view.findViewById(R.id.city_recycler);

        SharedPreferences prefs = getActivity().getSharedPreferences("Location", MODE_PRIVATE);
            int idName = prefs.getInt("region_id", 0); //0 is the default value.

        GetCitiesRequest getCitiesRequest = new GetCitiesRequest(getActivity(), this, this,idName);
        getCitiesRequest.start();
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Select City");
    }

    @Override
    public void onResponse(String response) {
        Log.e(TAG, "onResponse: ," + response);
        arrayList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            JSONArray jsonArray=jsonObject1.getJSONArray("cities");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String specName = object.getString("en_name");
                int regionId = object.getInt("id");

                Log.e(TAG,"cityNames="+specName);
                Log.e(TAG,"cityId="+regionId);

                LocationModel model = new LocationModel(specName, regionId);


                arrayList.add(model);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapterx = new AdapterLocationClinicRecylcer(getActivity(), arrayList, 2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterx);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Log.e(TAG, "onResponse: ," + error.toString());

    }
}
