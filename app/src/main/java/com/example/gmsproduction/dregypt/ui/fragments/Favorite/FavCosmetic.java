package com.example.gmsproduction.dregypt.ui.fragments.Favorite;

/**
 * Created by Hima on 5/20/2018.
 */

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.CosmeticClinicsRequests.DisplayCosFavoriteByID;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.ProductAdsRequests.DisplayProFavoriteByID;
import com.example.gmsproduction.dregypt.Models.CosmeticModel;
import com.example.gmsproduction.dregypt.Models.ProductsModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.activities.CosmeticsActivity;
import com.example.gmsproduction.dregypt.ui.adapters.CosmeticClinicsAdapter;
import com.example.gmsproduction.dregypt.ui.adapters.ProductAdsAdapter;
import com.example.gmsproduction.dregypt.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class FavCosmetic extends Fragment {
    int userid;
    private RecyclerView mRecyclerView;
    private CosmeticClinicsAdapter mAdapter;
    private ArrayList<CosmeticModel> modelArrayList;
    LinearLayoutManager linearLayoutManager;
    String phone_1, phone_2;
    private ArrayList<Integer> favArray = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite_cosmetic, container, false);
        SharedPreferences prefs = getActivity().getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
        userid = prefs.getInt("User_id", 0);
        mRecyclerView = rootView.findViewById(R.id.FavCos_RecycleView);
        DisplayFav();

        return rootView;
    }

    private void DisplayFav() {
        final DisplayCosFavoriteByID disp = new DisplayCosFavoriteByID(getContext(), userid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                modelArrayList = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray dataArray = object.getJSONArray("data");
                    for (int a = 0; a < dataArray.length(); a++) {
                        JSONObject dataObject = dataArray.getJSONObject(a);
                        String id = dataObject.getString("id");
                        String title = dataObject.getString("en_name");
                        String image = Constants.ImgUrl + dataObject.getString("img");
                        String address = dataObject.getString("en_address");
                        String created_at = dataObject.getString("created_at");
                        String description = dataObject.getString("en_note");
                        String email = dataObject.getString("email");
                        String website = dataObject.getString("website");
                        try {
                            JSONArray phoneArray = dataObject.getJSONArray("phone");
                            phone_1 = (String) phoneArray.get(0);
                            phone_2 = (String) phoneArray.get(1);
                        } catch (Exception e) {
                            JSONArray phoneArray = dataObject.getJSONArray("phone");
                            phone_1 = (String) phoneArray.get(0);
                            phone_2 = "No phone has been added";
                        }
                        JSONObject rateObject = dataObject.getJSONObject("rate");
                        Double rating_read = rateObject.getDouble("rating");
                        int rating_counts = rateObject.getInt("count");

                        modelArrayList.add(new CosmeticModel(id, title, description, image, address, email, website, created_at, phone_1, phone_2, rating_read, rating_counts));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();


                }
                linearLayoutManager = new LinearLayoutManager(getContext());
                mRecyclerView.setLayoutManager(linearLayoutManager);
                mAdapter = new CosmeticClinicsAdapter(getContext(), modelArrayList,favArray);
                mRecyclerView.setAdapter(mAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        disp.start();
    }

}
