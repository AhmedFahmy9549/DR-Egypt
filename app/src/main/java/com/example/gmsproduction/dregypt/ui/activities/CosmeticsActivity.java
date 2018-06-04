package com.example.gmsproduction.dregypt.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.gmsproduction.dregypt.Data.localDataSource.EndlessRecyclerOnScrollListener;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.CosmeticClinicsRequests.SearchCosmeticClinicsRequest;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.adapters.CosmeticClinicsAdapter;
import com.example.gmsproduction.dregypt.ui.fragments.NoInternt_Fragment;
import com.example.gmsproduction.dregypt.utils.Constants;
import com.example.gmsproduction.dregypt.Models.CosmeticModel;
import com.example.gmsproduction.dregypt.utils.Utils;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CosmeticsActivity extends AppCompatActivity {


    String MY_PREFS_NAME = "FiltersCos";

    private RecyclerView mRecyclerView;
    private CosmeticClinicsAdapter mAdapter;
    private ArrayList<CosmeticModel> modelArrayList=new ArrayList<>();
    String id, title, description, price, image, status, address, created_at, phone_1, phone_2, rating_count, email, website;
    Double rating_read;
    int rating_counts;
    MaterialSearchView searchView;
    Map<String, String> body = new HashMap<>();
    String url = Constants.basicUrl + "/cosmetic-clinics/search";
    String test;


    LinearLayoutManager linearLayoutManager;
    int page = 1;
    int last_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetics);
        //fragmentManager = getSupportFragmentManager();

        //Request for all Main products

        //recycdler View
        mRecyclerView = findViewById(R.id.Recycler_Cosmetic);

        getCosmeticsPagenatin("");

        //Custom Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarxx_cosmetic);
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
                    modelArrayList = new ArrayList<>();
                    page = 1;
                    mAdapter = new CosmeticClinicsAdapter(CosmeticsActivity.this, modelArrayList);
                    linearLayoutManager = new LinearLayoutManager(CosmeticsActivity.this);
                    mRecyclerView.setLayoutManager(linearLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);
                    getCosmeticsPagenatin(test);
                } else {
                    finish();
                }
            }
        });

        //Search Related
        searchView = (MaterialSearchView) findViewById(R.id.search_view_cosmetic);
        searchView.setVoiceSearch(false);
        searchView.setEllipsize(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                test = query;
                getCosmetics(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getCosmetics(newText);
                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
            }

            @Override
            public void onSearchViewClosed() {
                getCosmetics(test);
            }
        });


        linearLayoutManager = new LinearLayoutManager(CosmeticsActivity.this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new CosmeticClinicsAdapter(CosmeticsActivity.this, modelArrayList);
        mRecyclerView.setAdapter(mAdapter);
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
                Intent intent = new Intent(CosmeticsActivity.this, FiltersActivity.class);
                intent.putExtra("idFilter", 5);
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

                modelArrayList.add(new CosmeticModel(id, title, description, image, address, email, website, created_at, phone_1, phone_2, rating_read, rating_counts));
            }

        } catch (JSONException e) {
            e.printStackTrace();


        }
        linearLayoutManager = new LinearLayoutManager(CosmeticsActivity.this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new CosmeticClinicsAdapter(CosmeticsActivity.this, modelArrayList);
        mRecyclerView.setAdapter(mAdapter);
    }

    //filters will be added here
    public void getCosmetics(String keyword) {

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        int city = prefs.getInt("city", 0); //0 is the default value.
        int area = prefs.getInt("area", 0); //0 is the default value.
        int rate = prefs.getInt("num_rate", 0); //0 is the default value.
        int speciality = prefs.getInt("speciality", 0); //0 is the default value.

        Log.e("CXAAAA", "city=" + city + "area=" + area + "rate=" + rate + "Specialty=" + speciality);


        body.put("region", String.valueOf(city));
        body.put("city", String.valueOf(area));
        body.put("rate", String.valueOf(rate));
        body.put("speciality", String.valueOf(speciality));
        body.put("keyword", keyword);

        final SearchCosmeticClinicsRequest searchProductAdRequest = new SearchCosmeticClinicsRequest(CosmeticsActivity.this, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                CosmeticResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    /*fragmentManager
                            .beginTransaction()
                            .add(R.id.Cosmetic_Include, new NoInternt_Fragment(),
                                    Utils.Error).commit();*/
                    NoInternt_Fragment fragment = new NoInternt_Fragment();
                    Bundle arguments = new Bundle();
                    arguments.putInt("duck", 66);
                    fragment.setArguments(arguments);
                    final android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.Cosmetic_Include, fragment, Utils.Error);
                    ft.commit();

                } else if (error instanceof AuthFailureError) {
                    //TODO

                } else if (error instanceof ServerError) {
                    //TODO
                } else if (error instanceof NetworkError) {
                    //TODO
                } else if (error instanceof ParseError) {
                    //TODO

                }

            }
        }, 0);
        searchProductAdRequest.setBody((HashMap) body);
        searchProductAdRequest.start();
    }

    public void getCosmeticsPagenatin(String keyword) {

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        int city = prefs.getInt("city", 0); //0 is the default value.
        int area = prefs.getInt("area", 0); //0 is the default value.
        int rate = prefs.getInt("num_rate", 0); //0 is the default value.
        int speciality = prefs.getInt("speciality", 0); //0 is the default value.

        Log.e("CXAAAA", "city=" + city + "area=" + area + "rate=" + rate + "Specialty=" + speciality);


        body.put("region", String.valueOf(city));
        body.put("city", String.valueOf(area));
        body.put("rate", String.valueOf(rate));
        body.put("speciality", String.valueOf(speciality));
        body.put("keyword", keyword);

        final SearchCosmeticClinicsRequest searchProductAdRequest = new SearchCosmeticClinicsRequest(CosmeticsActivity.this, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PagenationResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    /*fragmentManager
                            .beginTransaction()
                            .add(R.id.Cosmetic_Include, new NoInternt_Fragment(),
                                    Utils.Error).commit();*/
                    NoInternt_Fragment fragment = new NoInternt_Fragment();
                    Bundle arguments = new Bundle();
                    arguments.putInt("duck", 66);
                    fragment.setArguments(arguments);
                    final android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.Cosmetic_Include, fragment, Utils.Error);
                    ft.commitAllowingStateLoss();

                } else if (error instanceof AuthFailureError) {
                    //TODO

                } else if (error instanceof ServerError) {
                    //TODO
                } else if (error instanceof NetworkError) {
                    //TODO
                } else if (error instanceof ParseError) {
                    //TODO

                }

            }
        }, page);
        searchProductAdRequest.setBody((HashMap) body);
        searchProductAdRequest.start();
    }

    public void PagenationResponse(String response) {

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

                modelArrayList.add(new CosmeticModel(id, title, description, image, address, email, website, created_at, phone_1, phone_2, rating_read, rating_counts));
            }

            JSONObject meta = object.getJSONObject("meta");
            last_page = meta.getInt("last_page");


            Log.e("laaaast_page=", last_page + "");



        } catch (JSONException e) {
            e.printStackTrace();




        }
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {

                page++;

                Log.e("PageeeNext=", "" + page);
                if (page > last_page) {

                } else {
                    getCosmeticsPagenatin("");

                }


            }
        });

        mAdapter.notifyItemRangeInserted(mAdapter.getItemCount(), modelArrayList.size() - 1);


    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("onStop", "onStop");
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt("num_rate", 0);
        editor.putInt("city", 0);
        editor.putInt("area", 0);
        editor.putInt("speciality", 0);

        editor.apply();

    }
}
