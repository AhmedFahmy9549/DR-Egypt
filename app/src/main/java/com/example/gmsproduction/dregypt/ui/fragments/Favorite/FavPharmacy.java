package com.example.gmsproduction.dregypt.ui.fragments.Favorite;

/**
 * Created by Hima on 5/20/2018.
 */

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.HospitalsRequests.DisplayHosFavoriteByID;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.PharmacyRequests.DisplayPhaFavoriteByID;
import com.example.gmsproduction.dregypt.Models.HospitalModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.fragments.FragmentsFilters.AdapterHospitalRecylcer;
import com.example.gmsproduction.dregypt.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class FavPharmacy extends Fragment {
    int userid;
    ArrayList<HospitalModel> arrayList;
    private AdapterHospitalRecylcer adapterx;
    private ArrayList<Integer> favArray = new ArrayList<>();

    LinearLayoutManager linearLayoutManager;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite_pharmacy, container, false);
        SharedPreferences prefs = getActivity().getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
        userid = prefs.getInt("User_id", 0);
        mRecyclerView = rootView.findViewById(R.id.FavPha_RecycleView);
        DisplayFav();
        return rootView;
    }

    private void DisplayFav() {
        final DisplayPhaFavoriteByID disp = new DisplayPhaFavoriteByID(getContext(), userid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                arrayList = new ArrayList<>();
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
                        String img_hos = Constants.ImgUrl + object.getString("img");
                        String createdAt_hos = object.getString("created_at");


                        JSONObject fav_object = object.getJSONObject("favorites");
                        int fav_hos = fav_object.getInt("count");

                        JSONObject rate = object.getJSONObject("rate");
                        int count_hos = rate.getInt("count");
                        float rating_hos = (float) rate.getDouble("rating");

                        JSONArray phone = object.getJSONArray("phone");
                        String phone_hos = phone.getString(0);
                        String phone2_hos = phone.getString(1);

                        HospitalModel model = new HospitalModel(id_hos, name_hos, address_hos, note_hos, website_hos, email_hos, img_hos, phone_hos, phone2_hos, count_hos, rating_hos, fav_hos, createdAt_hos);


                        arrayList.add(model);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterx = new AdapterHospitalRecylcer(getContext(), arrayList, 99303,favArray);
                linearLayoutManager = new LinearLayoutManager(getContext());
                mRecyclerView.setLayoutManager(linearLayoutManager);
                mRecyclerView.setAdapter(adapterx);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        disp.start();
    }

}
