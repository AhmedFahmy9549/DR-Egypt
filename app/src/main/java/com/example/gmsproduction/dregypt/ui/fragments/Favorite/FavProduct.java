package com.example.gmsproduction.dregypt.ui.fragments.Favorite;

/**
 * Created by Hima on 5/20/2018.
 */

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.ProductAdsRequests.GetFavoriteProducts;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.utils.Constants;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class FavProduct extends Fragment {
    int userid;
    Map<String, String> body = new HashMap<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite_products, container, false);
        SharedPreferences prefs = getActivity().getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
        userid = prefs.getInt("User_id", 0);
        getFavID();

        return rootView;
    }

    private void getFavID() {
        body.put("category", "product");
        GetFavoriteProducts getFavId = new GetFavoriteProducts(getActivity(), userid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("FavPage","pro"+response);
                JSONObject object = new JSONObject();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        getFavId.setBody((HashMap) body);
        getFavId.start();
    }
}
