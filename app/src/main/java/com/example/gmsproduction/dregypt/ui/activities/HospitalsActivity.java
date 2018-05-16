package com.example.gmsproduction.dregypt.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
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
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.HospitalsRequests.SearchHospitalsRequest;
import com.example.gmsproduction.dregypt.Models.HospitalModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.fragments.FragmentsFilters.AdapterHospitalRecylcer;
import com.example.gmsproduction.dregypt.utils.Constants;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HospitalsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    View view;
    String TAG = "HospitalsActivity";
    Map<String, String> body = new HashMap<>();
    ArrayList<HospitalModel> arrayList;
    private AdapterHospitalRecylcer adapterx;
    MaterialSearchView searchView;
    public ConstraintLayout constraintLayout;

    String MY_PREFS_NAME = "FiltersH";


    String test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitals);
        setTitle("Hospitals");
        recyclerView = findViewById(R.id.hospital_recycler);
        constraintLayout=findViewById(R.id.fragment_hospital);

        //get all hos
        getHospital("");

        //Custom Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_hospital);
        setSupportActionBar(toolbar);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (test != null && !test.isEmpty()) {
                    test = "";
                    getHospital(test);
                } else {
                    finish();
                }
            }
        });


        //Search Related
        searchView = (MaterialSearchView) findViewById(R.id.search_view_hospital);
        searchView.setVoiceSearch(false);
        searchView.setEllipsize(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                test = query;
                getHospital(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getHospital(newText);
                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                getHospital(test);
            }
        });













        /*SharedPreferences prefs = getSharedPreferences("Location", MODE_PRIVATE);
        int city_id = prefs.getInt("city_id", 0); //0 is the default value.
        int region_id = prefs.getInt("region_id", 0);
        int special_id = prefs.getInt("specialId", 0); */


    }

    //menu option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int items = item.getItemId();

        switch (items) {


            case R.id.action_filters:
                Intent intent =new Intent(HospitalsActivity.this,FiltersActivity.class);
                intent.putExtra("idFilter",1);
                startActivity(intent);
                break;
        }
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


    public void getHospital(String keyword) {

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        int city = prefs.getInt("city", 0); //0 is the default value.
        int area = prefs.getInt("area", 0); //0 is the default value.
        int rate = prefs.getInt("num_rate", 0); //0 is the default value.

        Log.e("CXAAAA","city"+city+"\n"+"area"+area+"\n"+"rate"+rate);


        body.put("region", String.valueOf(city));
        body.put("city", String.valueOf(area));
        body.put("rate", String.valueOf(rate));
        body.put("keyword", keyword);

        final SearchHospitalsRequest searchHospitalsRequest = new SearchHospitalsRequest(this, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response==",response);
                HospitalResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Response==",""+error.getMessage());


            }
        });
        searchHospitalsRequest.setBody((HashMap) body);
        searchHospitalsRequest.start();
    }
    public void HospitalResponse(String response) {
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

                Log.e(TAG + "Response=", "" + phone);


                HospitalModel model = new HospitalModel(id_hos, name_hos, address_hos, note_hos, website_hos, email_hos, img_hos, phone_hos, phone2_hos, count_hos, rating_hos, fav_hos, createdAt_hos);


                arrayList.add(model);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapterx = new AdapterHospitalRecylcer(this, arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterx);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("onStop","onStop");
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt("num_rate", 0);
        editor.putInt("city", 0);
        editor.putInt("area", 0);
        editor.apply();

    }
}
