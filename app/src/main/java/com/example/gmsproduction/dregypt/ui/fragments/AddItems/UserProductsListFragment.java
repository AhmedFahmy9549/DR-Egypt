package com.example.gmsproduction.dregypt.ui.fragments.AddItems;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.ProductAdsRequests.GetUserProducts;
import com.example.gmsproduction.dregypt.Models.PhoneModel;
import com.example.gmsproduction.dregypt.Models.ProductsModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.adapters.ProductAdsAdapter;
import com.example.gmsproduction.dregypt.ui.adapters.UserProductAdapter;
import com.example.gmsproduction.dregypt.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class UserProductsListFragment extends Fragment {

    private View view;
    int id;
    String userName = "Dr.Egypt", userAvatar = "";
    private RecyclerView mRecyclerView;
    private UserProductAdapter mAdapter;
    private ArrayList<ProductsModel> modelArrayList;
    private ArrayList<PhoneModel> phoneArrayList;
    private ArrayList<String> mArraylist, mArraylist2;
    String phone_1, phone_2, favcount, viewCount;
    LinearLayoutManager LayoutManagaer;
    String[][][] c;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_products_list, container, false);
        getActivity().setTitle("My Products");
        SharedPreferences prefs = getActivity().getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
        id = prefs.getInt("User_id", 0);
        userName = prefs.getString("User_name", "Dr.Egypt");
        userAvatar = prefs.getString("User_avatar", null);
        initViews();
        getProducts(id);

        return view;
    }

    private void getProducts(int UserId) {
        GetUserProducts getUser = new GetUserProducts(getContext(), UserId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("kkkkccc", response);
                modelArrayList = new ArrayList<>();
                mArraylist = new ArrayList<>();
                mArraylist2 = new ArrayList<>();
                phoneArrayList = new ArrayList<>();

                try {
                    //JSONObject object = new JSONObject(response);
                    JSONArray array = new JSONArray(response);
/*
                    c = new String[array.length()][2];
*/
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

                        //get phone id and num
                        JSONArray phoneArray = dataObject.getJSONArray("phone_numbers");
                        JSONObject phoneObject01 = phoneArray.getJSONObject(0);
                        String phone01 = phoneObject01.getString("number");
                        String Phone_id01 = phoneObject01.getString("id");
                        JSONObject phoneObject02 = phoneArray.getJSONObject(1);
                        String phone02 = phoneObject02.getString("number");
                        String Phone_id02 = phoneObject02.getString("id");

                        phoneArrayList.add(new PhoneModel(Phone_id01, phone01, Phone_id02, phone02));

                        JSONObject categoryObject = dataObject.getJSONObject("category");
                        String category = categoryObject.getString("en_name");

                        try {
                            JSONObject favObject = dataObject.getJSONObject("favorites");
                            favcount = favObject.getString("count");
                        } catch (Exception e) {
                            favcount = "0";
                        }
                        try {
                            JSONObject ViewsObject = dataObject.getJSONObject("views");
                            viewCount = ViewsObject.getString("count");
                        } catch (Exception e) {
                            viewCount = "0";
                        }

                        mArraylist.add(favcount);
                        mArraylist2.add(viewCount);

                        modelArrayList.add(new ProductsModel(id, title, category, description, price, status, image, address, created_at, phone01, phone02));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mAdapter = new UserProductAdapter(getContext(), modelArrayList, mArraylist, mArraylist2, phoneArrayList);
                LayoutManagaer = new LinearLayoutManager(getContext());
                mRecyclerView.setLayoutManager(LayoutManagaer);
                mRecyclerView.setAdapter(mAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        getUser.start();
    }

    private void initViews() {
        mRecyclerView = view.findViewById(R.id.Recycler_UserProduct);


    }
}
