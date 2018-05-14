package com.example.gmsproduction.dregypt.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.JobAdsRequests.SearchJobAdRequest;
import com.example.gmsproduction.dregypt.Models.JobsModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.adapters.JobAdsAdapter;
import com.example.gmsproduction.dregypt.utils.Constants;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JobsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private JobAdsAdapter mAdapter;
    private ArrayList<JobsModel> modelArrayList;
    String id,userId, title, description,salary, image, status, address, created_at, phone_1, phone_2,category,experience,education_level,employment_type;
    MaterialSearchView searchView;
    Map<String, String> body = new HashMap<>();
    String url = Constants.basicUrl+"/job-ads/search";
    String test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);

        //Request for main Jobs
        /*final SearchJobAdRequest searchJobAdRequest = new SearchJobAdRequest(this,url,this,this);
        searchJobAdRequest.setBody((HashMap) body);
        searchJobAdRequest.start();*/

        getJobs("");

        //Recycle
        mRecyclerView = findViewById(R.id.Recycler_Jobs);
        final LinearLayoutManager LayoutManagaer = new LinearLayoutManager(JobsActivity.this);
        mRecyclerView.setLayoutManager(LayoutManagaer);

        //Custom Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_Jobs);
        setSupportActionBar(toolbar);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (test != null && !test.isEmpty()){
                    test = "";
                    getJobs(test);
                }else {
                    finish();
                }
            }
        });

        //Search Related
        searchView = (MaterialSearchView) findViewById(R.id.search_view_Jobs);
        searchView.setVoiceSearch(false);
        searchView.setEllipsize(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                test = query;
                getJobs(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getJobs(newText);
                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                getJobs(test);
            }
        });




    }

    //menu option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    //on back press
    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }





    public void getJobs(String keyword){
        body.put("keyword", keyword);
        final SearchJobAdRequest searchJobAdRequest = new SearchJobAdRequest(JobsActivity.this,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JobsResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        searchJobAdRequest.setBody((HashMap) body);
        searchJobAdRequest.start();
    }

    public void JobsResponse(String response){
        modelArrayList = new ArrayList<>();

        Log.e("tagyyy", response);
        try {
            JSONObject object = new JSONObject(response);
            JSONArray dataArray = object.getJSONArray("data");
            for (int a = 0; a < dataArray.length(); a++) {
                JSONObject dataObject = dataArray.getJSONObject(a);
                id = dataObject.getString("id");
                userId = dataObject.getString("user_id");
                title = dataObject.getString("title");
                description = dataObject.getString("description");
                salary = dataObject.getString("salary");
                image = Constants.ImgUrl + dataObject.getString("img");
                address = dataObject.getString("address");
                created_at = dataObject.getString("created_at");
                JSONArray phoneArray = dataObject.getJSONArray("phone");
                phone_1 = (String) phoneArray.get(0);
                phone_2 = (String) phoneArray.get(1);
                JSONObject categoryObject = dataObject.getJSONObject("category");
                category = categoryObject.getString("en_name");
                JSONObject experienceObject = dataObject.getJSONObject("experience_level");
                experience = experienceObject.getString("en_name");
                JSONObject educationObject = dataObject.getJSONObject("education_level");
                education_level = educationObject.getString("en_name");
                JSONObject employmentObject = dataObject.getJSONObject("employment_type");
                employment_type = employmentObject.getString("en_name");




                modelArrayList.add(new JobsModel(id,userId, title, description,salary, image, status, address, created_at, phone_1, phone_2,category,experience,education_level,employment_type));
            }
            mAdapter = new JobAdsAdapter(JobsActivity.this, modelArrayList);
            mRecyclerView.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }
}
