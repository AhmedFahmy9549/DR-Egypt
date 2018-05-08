package com.example.gmsproduction.dregypt.ui.activities;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.daimajia.slider.library.SliderLayout;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.CosmeticClinicsRequests.SearchCosmeticClinicsRequest;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.ProductAdsRequests.SearchProductAdRequest;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.adapters.CosmeticClinicsAdapter;
import com.example.gmsproduction.dregypt.ui.adapters.ProductAdsAdapter;
import com.example.gmsproduction.dregypt.utils.Constants;
import com.example.gmsproduction.dregypt.utils.CosmeticModel;
import com.example.gmsproduction.dregypt.utils.ProductsModel;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CosmeticsActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {

    private RecyclerView mRecyclerView;
    private CosmeticClinicsAdapter mAdapter;
    private ArrayList<CosmeticModel> modelArrayList;
    String id, title, description, price, image, status, address, created_at, phone_1, phone_2,rating_count,email,website;
    Double rating_read;
    int rating_counts;
    MaterialSearchView searchView;
    Map<String, String> body = new HashMap<>();
    String url = "https://dregy01.frb.io/api/cosmetic-clinics/search";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetics);

        //Request for main products
        final SearchCosmeticClinicsRequest searchCosmeticClinicsRequest = new SearchCosmeticClinicsRequest(this, this, this);
        searchCosmeticClinicsRequest.setBody((HashMap) body);
        searchCosmeticClinicsRequest.start();

        //recycler View
        mRecyclerView = findViewById(R.id.Recycler_Cosmetic);
        final LinearLayoutManager LayoutManagaer = new LinearLayoutManager(CosmeticsActivity.this);
        mRecyclerView.setLayoutManager(LayoutManagaer);
        mRecyclerView.addOnScrollListener(new CustomScrollListener());


        //Custom Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarxx_cosmetic);
        setSupportActionBar(toolbar);

        //Search Related
        searchView = (MaterialSearchView) findViewById(R.id.search_view_cosmetic);
        searchView.setVoiceSearch(false);
        searchView.setEllipsize(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                body.put("keyword", newText);
                final SearchProductAdRequest searchProductAdRequest = new SearchProductAdRequest(CosmeticsActivity.this, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        CosmeticResponse(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                searchProductAdRequest.setBody((HashMap) body);
                searchProductAdRequest.start();

                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
                                               @Override
                                               public void onSearchViewShown() {

                                               }

                                               @Override
                                               public void onSearchViewClosed() {

                                               }
                                           });

    }

    @Override
    public void onResponse(String response) {
        CosmeticResponse(response);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

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

    //still don't know how it's work
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void CosmeticResponse(String response) {
        modelArrayList = new ArrayList<>();

        Log.e("tagyyy", response);
        try {
            JSONObject object = new JSONObject(response);
            JSONArray dataArray = object.getJSONArray("data");
            for (int a = 0; a < dataArray.length(); a++) {
                JSONObject dataObject = dataArray.getJSONObject(a);
                id = dataObject.getString("id");
                title = dataObject.getString("en_name");
                image = Constants.ImgUrl + dataObject.getString("img");
                address = dataObject.getString("en_address");
                created_at = dataObject.getString("created_at");
                description = dataObject.getString("en_note");
                email = dataObject.getString("email");
                website = dataObject.getString("website");
                JSONArray phoneArray = dataObject.getJSONArray("phone");
                phone_1 = (String) phoneArray.get(0);
                phone_2 = (String) phoneArray.get(1);
                JSONObject rateObject = dataObject.getJSONObject("rate");
                rating_read = rateObject.getDouble("rating");
                rating_counts = rateObject.getInt("count");


                modelArrayList.add(new CosmeticModel(id, title, description, image, address,email,website, created_at, phone_1, phone_2,rating_read,rating_counts));
            }
            mAdapter = new CosmeticClinicsAdapter(CosmeticsActivity.this, modelArrayList);
            mRecyclerView.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }
}
