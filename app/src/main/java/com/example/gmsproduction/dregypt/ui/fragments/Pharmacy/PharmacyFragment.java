package com.example.gmsproduction.dregypt.ui.fragments.Pharmacy;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.ClinicRequests.SearchClinicsRequest;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.PharmacyRequests.SearchPharmacyRequest;
import com.example.gmsproduction.dregypt.Models.HospitalModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.fragments.FragmentsFilters.AdapterHospitalRecylcer;
import com.example.gmsproduction.dregypt.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class PharmacyFragment extends Fragment implements Response.Listener<String>, Response.ErrorListener {
    RecyclerView recyclerView;
    View view;
    String TAG = "PharmacyFragment";
    HashMap<String, String> parms = new HashMap<>();
    ArrayList<HospitalModel> arrayList = new ArrayList<>();
    private AdapterHospitalRecylcer adapterx;

    public PharmacyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_hospitals, container, false);

        getActivity().setTitle("Pharmacy");

        recyclerView = view.findViewById(R.id.hospital_recycler);


        SharedPreferences prefs = getActivity().getSharedPreferences("LocationP", MODE_PRIVATE);
        int city_id = prefs.getInt("city_id", 0); //0 is the default value.
        int region_id = prefs.getInt("region_id", 0);

        parms.put("region", String.valueOf(region_id));
        parms.put("city", String.valueOf(city_id));

        Log.e(TAG, "Region=" + region_id + "  City=" + city_id );


        SearchPharmacyRequest searchPharmacyRequest = new SearchPharmacyRequest(getActivity(), this, this);
        searchPharmacyRequest.setBody(parms);
        searchPharmacyRequest.start();
        return view;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(TAG, "Response=" + error.toString());


    }

    @Override
    public void onResponse(String response) {
        Log.e(TAG, "Response=" + response);

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String name_hos = object.getString("en_name");
                int id_hos = object.getInt("id");
                String address_hos = object.getString("en_address");
                String note_hos = object.getString("en_note");
                String website_hos = object.getString("website");
                String email_hos = object.getString("email");
                String img_hos = Constants.ImgUrl+object.getString("img");
                String createdAt_hos = object.getString("created_at");


                JSONObject fav_object = object.getJSONObject("favorites");
                int fav_hos = fav_object.getInt("count");

                JSONObject rate = object.getJSONObject("rate");
                int count_hos = rate.getInt("count");
                float rating_hos = (float) rate.getDouble("rating");

                JSONArray phone = object.getJSONArray("phone");
                String phone_hos = phone.getString(0);
                String phone2_hos = phone.getString(1);

                Log.e(TAG + "Response=", "" + rating_hos);



                HospitalModel model = new HospitalModel(id_hos, name_hos, address_hos, note_hos, website_hos, email_hos, img_hos, phone_hos, phone2_hos, count_hos, rating_hos, fav_hos, createdAt_hos);


                arrayList.add(model);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapterx = new AdapterHospitalRecylcer(getActivity(), arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterx);

    }
}
