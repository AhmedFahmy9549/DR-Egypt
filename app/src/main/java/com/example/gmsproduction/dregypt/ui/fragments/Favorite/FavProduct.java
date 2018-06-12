package com.example.gmsproduction.dregypt.ui.fragments.Favorite;

/**
 * Created by Hima on 5/20/2018.
 */

import android.content.SharedPreferences;
import android.graphics.Rect;
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
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.ProductAdsRequests.DisplayProFavoriteByID;
import com.example.gmsproduction.dregypt.Models.ProductsModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.activities.ProductsActivity;
import com.example.gmsproduction.dregypt.ui.adapters.ProductAdsAdapter;
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
    String phone_1, phone_2;
    private RecyclerView mRecyclerView;
    private ProductAdsAdapter mAdapter;
    private ArrayList<ProductsModel> modelArrayList;
    LinearLayoutManager LayoutManagaer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite_products, container, false);
        SharedPreferences prefs = getActivity().getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
        userid = prefs.getInt("User_id", 0);
        mRecyclerView = rootView.findViewById(R.id.FavPro_RecycleView);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view); // item position
                int spanCount = 2;
                int spacing = 2;//spacing between views in grid

                if (position >= 0) {
                    int column = position % spanCount; // item column

                    outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                    outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                    if (position < spanCount) { // top edge
                        outRect.top = spacing;
                    }
                    outRect.bottom = spacing; // item bottom
                } else {
                    outRect.left = 0;
                    outRect.right = 0;
                    outRect.top = 0;
                    outRect.bottom = 0;
                }
            }
        });

        DisplayFav();
        return rootView;
    }

    private void DisplayFav() {
        final DisplayProFavoriteByID disp = new DisplayProFavoriteByID(getContext(), userid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                modelArrayList = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("data");
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
                        try {
                            JSONArray phoneArray = dataObject.getJSONArray("phone");
                            phone_1 = (String) phoneArray.get(0);
                            phone_2 = (String) phoneArray.get(1);
                        } catch (Exception e) {
                            JSONArray phoneArray = dataObject.getJSONArray("phone");
                            phone_1 = (String) phoneArray.get(0);
                            phone_2 = "No phone has been added";
                        }
                        JSONObject categoryObject = dataObject.getJSONObject("category");
                        String category = categoryObject.getString("en_name");

                        modelArrayList.add(new ProductsModel(id, title, category, description, price, status, image, address, created_at, phone_1, phone_2));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
      /*          mAdapter = new ProductAdsAdapter(getContext(), modelArrayList,modelArrayList);
                LayoutManagaer = new GridLayoutManager(getContext(), 2);
                mRecyclerView.setLayoutManager(LayoutManagaer);
                mRecyclerView.setAdapter(mAdapter);*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        disp.start();
    }

}
