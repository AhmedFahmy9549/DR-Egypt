package com.example.gmsproduction.dregypt.ui.fragments.Favorite;

/**
 * Created by Hima on 5/20/2018.
 */

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.ProductAdsRequests.DisplayProFavoriteByID;
import com.example.gmsproduction.dregypt.Models.ProductsModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class FavProduct extends Fragment {
    int userid;
    Map<String, String> body = new HashMap<>();
    int favourable_id;
    ArrayList<Integer> fav_List;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite_products, container, false);
        SharedPreferences prefs = getActivity().getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
        userid = prefs.getInt("User_id", 0);
        //getFavID();
        DisplayFav();
        return rootView;
    }

    /*private void getFavID() {
        fav_List = new ArrayList<>();
        body.put("category", "product");
        GetFavoriteProducts getFavId = new GetFavoriteProducts(getActivity(), userid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("FavPage", "pro" + response);
                try {
                    JSONArray array = new JSONArray(response);
                    for (int a = 0; a<array.length(); a++){
                        JSONObject object = array.getJSONObject(a);
                        favourable_id = object.getInt("favourable_id");
                        fav_List.add(favourable_id);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        getFavId.setBody((HashMap) body);
        getFavId.start();
    }*/
    private void DisplayFav() {
        DisplayProFavoriteByID disp = new DisplayProFavoriteByID(getContext(), userid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int a = 0; a < array.length(); a++) {
                        JSONObject dataObject = array.getJSONObject(a);
                        String id = dataObject.getString("id");
                        String title = dataObject.getString("title");
                        String price = dataObject.getString("price");
                        String image = Constants.ImgUrl + dataObject.getString("img");
                        String status = dataObject.getString("status");
                        String address = dataObject.getString("address");
                        String created_at = dataObject.getString("created_at");
                        String description = dataObject.getString("description");

                      //  modelArrayList.add(new ProductsModel(id, title, category, description, price, status, image, address, created_at, phone_1, phone_2));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }

}
