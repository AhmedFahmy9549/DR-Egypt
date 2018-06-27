package com.example.gmsproduction.dregypt.ui.fragments.AddItems;

import android.content.SharedPreferences;
import android.graphics.Rect;
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
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.JobAdsRequests.GetUserJobs;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.ProductAdsRequests.GetUserProducts;
import com.example.gmsproduction.dregypt.Models.JobsModel;
import com.example.gmsproduction.dregypt.Models.PhoneModel;
import com.example.gmsproduction.dregypt.Models.ProductsModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.adapters.JobAdsAdapter;
import com.example.gmsproduction.dregypt.ui.adapters.ProductAdsAdapter;
import com.example.gmsproduction.dregypt.ui.adapters.UserJobAdapter;
import com.example.gmsproduction.dregypt.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class UserJobsListFragment extends Fragment {

    private View view;
    int id;
    String userName = "Dr.Egypt", userAvatar = "";
    private RecyclerView mRecyclerView;
    private UserJobAdapter mAdapter;
    private ArrayList<JobsModel> modelArrayList;
    String phone_1, phone_2,favcount, viewCount;
    LinearLayoutManager LayoutManagaer;
    private ArrayList<String> mArraylist, mArraylist2;
    private ArrayList<PhoneModel> phoneArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_jobs_list, container, false);
        getActivity().setTitle("My Jobs");
        SharedPreferences prefs = getActivity().getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
        id = prefs.getInt("User_id", 0);
        userName = prefs.getString("User_name", "Dr.Egypt");
        userAvatar = prefs.getString("User_avatar", null);
        initViews();
        getJobs(id);

        return view;
    }

    private void getJobs(int UserId) {
        GetUserJobs getUser = new GetUserJobs(getContext(), UserId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("kkkkccc",response);
                modelArrayList = new ArrayList<>();
                mArraylist = new ArrayList<>();
                mArraylist2 = new ArrayList<>();
                phoneArrayList = new ArrayList<>();

                try {

                    JSONArray dataArray = new JSONArray(response);
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

                        //add phone
                        //get phone id and num
                        JSONArray phoneArray = dataObject.getJSONArray("phone_numbers");
                        JSONObject phoneObject01 = phoneArray.getJSONObject(0);
                        String phone01 = phoneObject01.getString("number");
                        String Phone_id01 = phoneObject01.getString("id");
                        String phone02 = null,Phone_id02 = null;
                        try {
                            JSONObject phoneObject02 = phoneArray.getJSONObject(1);
                            phone02 = phoneObject02.getString("number");
                            Phone_id02 = phoneObject02.getString("id");
                        } catch (Exception e) {
                        }

                        phoneArrayList.add(new PhoneModel(Phone_id01, phone01, Phone_id02, phone02));

                        JSONObject categoryObject = dataObject.getJSONObject("category");
                        String category = categoryObject.getString("en_name");
                        JSONObject experienceObject = dataObject.getJSONObject("experience_level");
                        String experience = experienceObject.getString("en_name");
                        JSONObject educationObject = dataObject.getJSONObject("education_level");
                        String education_level = educationObject.getString("en_name");
                        JSONObject employmentObject = dataObject.getJSONObject("employment_type");
                        String employment_type = employmentObject.getString("en_name");

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


                        modelArrayList.add(new JobsModel(id, userId, title, description, salary, image, "", address, created_at, phone_1, phone_2, category, experience, education_level, employment_type));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
                mAdapter = new UserJobAdapter(getContext(), modelArrayList,mArraylist, mArraylist2,phoneArrayList);
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
        mRecyclerView = view.findViewById(R.id.Recycler_UserJobs);
    }
}
