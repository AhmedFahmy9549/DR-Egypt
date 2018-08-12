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
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.JobAdsRequests.DisplayJobFavoriteByID;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.ProductAdsRequests.DisplayProFavoriteByID;
import com.example.gmsproduction.dregypt.Models.JobsModel;
import com.example.gmsproduction.dregypt.Models.ProductsModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.activities.JobsActivity;
import com.example.gmsproduction.dregypt.ui.adapters.JobAdsAdapter;
import com.example.gmsproduction.dregypt.ui.adapters.ProductAdsAdapter;
import com.example.gmsproduction.dregypt.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class FavJobs extends Fragment {
    private RecyclerView mRecyclerView;
    private JobAdsAdapter mAdapter;
    private ArrayList<JobsModel> modelArrayList;
    private ArrayList<Integer> favArray = new ArrayList<>();

    LinearLayoutManager LayoutManagaer;
    int userid;
    String phone_1, phone_2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite_jobs, container, false);
        SharedPreferences prefs = getActivity().getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
        userid = prefs.getInt("User_id", 0);
        mRecyclerView = rootView.findViewById(R.id.FavJob_RecycleView);

        DisplayFav();

        return rootView;
    }

    private void DisplayFav() {
        final DisplayJobFavoriteByID disp = new DisplayJobFavoriteByID(getContext(), userid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                modelArrayList = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray dataArray = object.getJSONArray("data");
                    for (int a = 0; a < dataArray.length(); a++) {
                        JSONObject dataObject = dataArray.getJSONObject(a);
                        String id = dataObject.getString("id");
                        String userId = dataObject.getString("user_id");
                        String title = dataObject.getString("title");
                        String description = dataObject.getString("description");
                        String salary = dataObject.getString("salary");
                        String image = Constants.ImgUrl + dataObject.getString("img");
                        String address = dataObject.getString("address");
                        String created_at = dataObject.getString("created_at");
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
                        JSONObject experienceObject = dataObject.getJSONObject("experience_level");
                        String experience = experienceObject.getString("en_name");
                        JSONObject educationObject = dataObject.getJSONObject("education_level");
                        String education_level = educationObject.getString("en_name");
                        JSONObject employmentObject = dataObject.getJSONObject("employment_type");
                        String employment_type = employmentObject.getString("en_name");


                        modelArrayList.add(new JobsModel(id, userId, title, description, salary, image, "", address, created_at, phone_1, phone_2, category, experience, education_level, employment_type));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
                LayoutManagaer = new LinearLayoutManager(getContext());
                mRecyclerView.setLayoutManager(LayoutManagaer);
                mAdapter = new JobAdsAdapter(getContext(), modelArrayList,favArray);
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
