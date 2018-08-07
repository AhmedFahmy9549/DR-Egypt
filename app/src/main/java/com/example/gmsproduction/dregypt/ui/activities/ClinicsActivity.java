package com.example.gmsproduction.dregypt.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.gmsproduction.dregypt.Data.localDataSource.EndlessRecyclerOnScrollListener;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.ClinicRequests.SearchClinicsRequest;
import com.example.gmsproduction.dregypt.Models.HospitalModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.fragments.FragmentsFilters.AdapterHospitalRecylcer;
import com.example.gmsproduction.dregypt.ui.fragments.NoInternt_Fragment;
import com.example.gmsproduction.dregypt.utils.Constants;
import com.example.gmsproduction.dregypt.utils.Utils;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClinicsActivity extends BaseActivity {

    RecyclerView recyclerView;
    String TAG = "ClinicsActivity";

    HashMap<String, String> parms = new HashMap<>();
    ArrayList<HospitalModel> arrayList = new ArrayList<>();

    private AdapterHospitalRecylcer adapterx;
    MaterialSearchView searchView;
    Map<String, String> body = new HashMap<>();
    String test;
    String MY_PREFS_NAME = "FiltersCli";

    LinearLayoutManager linearLayoutManager;
    int page = 1;
    int last_page,language;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinics);
        language=getIdLANG();
        localization(language);
        recyclerView = findViewById(R.id.hospital_recycler);
        progressBar = (ProgressBar) findViewById(R.id.pbHeaderProgress);
        progressBar.setVisibility(View.VISIBLE);

        getClinicsPagenation("");

        //Custom Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_Clinic);
        setSupportActionBar(toolbar);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(getBackArrow(language)));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (test != null && !test.isEmpty()) {
                    test = "";
                    Log.e("YYYYYYYYYYY", "test != null && !test.isEmpty()");

                    arrayList = new ArrayList<>();
                    page = 1;
                    adapterx = new AdapterHospitalRecylcer(ClinicsActivity.this, arrayList,99404);
                    linearLayoutManager = new LinearLayoutManager(ClinicsActivity.this);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(adapterx);
                    getClinicsPagenation(test);

                } else {
                    Log.e("YYYYYYYYYYY", "finish()");

                    finish();
                }
            }
        });

        //Search Related
        searchView = (MaterialSearchView) findViewById(R.id.search_view_Clinic);
        searchView.setVoiceSearch(false);
        searchView.setEllipsize(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("YYYYYYYYYYY", "onQueryTextSubmit");
                test = query;
                getClinics(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                getClinics(newText);

                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                Log.e("YYYYYYYYYYY", "onSearchViewShown");


            }

            @Override
            public void onSearchViewClosed() {
                Log.e("YYYYYYYYYYY", "onSearchViewClosed");
                getClinics(test);
            }
        });

        adapterx = new AdapterHospitalRecylcer(this, arrayList,99404);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterx);
        setActivityTitle("العيادات","Clinics");
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
                Intent intent = new Intent(ClinicsActivity.this, FiltersActivity.class);
                intent.putExtra("idFilter", 2);
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
            Log.e("YYYYYYYYYYY", "searchView.isSearchOpen()");

        } else {
            Log.e("YYYYYYYYYYY", " super.onBackPressed()");

            super.onBackPressed();
        }
    }


    public void getClinics(String keyword) {

        arrayList = new ArrayList<>();
        Log.e("NumPAge=", page + "");

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


        final SearchClinicsRequest searchClinicsRequest = new SearchClinicsRequest(ClinicsActivity.this, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ClinicsResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    NoInternt_Fragment fragment = new NoInternt_Fragment();
                    Bundle arguments = new Bundle();
                    arguments.putInt("duck", 202);
                    fragment.setArguments(arguments);
                    final android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.Clinic_Include, fragment, Utils.Error);
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
        }, 0);
        searchClinicsRequest.setBody((HashMap) body);
        searchClinicsRequest.start();
    }


    public void ClinicsResponse(String response) {
        progressBar.setVisibility(View.GONE);

        arrayList = new ArrayList<>();
        Log.e(TAG, "Response=" + response);
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

                Log.e(TAG + "Response=", "" + rating_hos);


                HospitalModel model = new HospitalModel(id_hos, name_hos, address_hos, note_hos, website_hos, email_hos, img_hos, phone_hos, phone2_hos, count_hos, rating_hos, fav_hos, createdAt_hos);


                arrayList.add(model);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapterx = new AdapterHospitalRecylcer(this, arrayList,99404);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterx);


    }


    public void getClinicsPagenation(String keyword) {
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


        final SearchClinicsRequest searchClinicsRequest = new SearchClinicsRequest(ClinicsActivity.this, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PagenationResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    NoInternt_Fragment fragment = new NoInternt_Fragment();
                    Bundle arguments = new Bundle();
                    arguments.putInt("duck", 202);
                    fragment.setArguments(arguments);
                    final android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.Clinic_Include, fragment, Utils.Error);
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
        searchClinicsRequest.setBody((HashMap) body);
        searchClinicsRequest.start();
    }

    public void PagenationResponse(String response) {
        progressBar.setVisibility(View.GONE);

        Log.e(TAG, "Response=" + response);
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

                Log.e(TAG + "Response=", "" + rating_hos);


                HospitalModel model = new HospitalModel(id_hos, name_hos, address_hos, note_hos, website_hos, email_hos, img_hos, phone_hos, phone2_hos, count_hos, rating_hos, fav_hos, createdAt_hos);


                arrayList.add(model);

            }
            JSONObject meta = jsonObject.getJSONObject("meta");
            last_page = meta.getInt("last_page");


            Log.e("PageeeCurrent=", page + "");


        } catch (JSONException e) {
            e.printStackTrace();
        }


        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {

                page++;

                Log.e("PageeeNext=", "" + page);
                if (page > last_page) {

                } else {
                    getClinicsPagenation("");

                }


            }
        });

        adapterx.notifyItemRangeInserted(adapterx.getItemCount(), arrayList.size() - 1);


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
