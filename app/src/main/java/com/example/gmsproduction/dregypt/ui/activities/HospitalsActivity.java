package com.example.gmsproduction.dregypt.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.gmsproduction.dregypt.Data.localDataSource.EndlessRecyclerOnScrollListener;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.HospitalsRequests.SearchHospitalsRequest;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.ProductAdsRequests.GetFavoriteProducts;
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

public class HospitalsActivity extends BaseActivity {

    RecyclerView recyclerView;
    View view;
    String TAG = "HospitalsActivity";
    Map<String, String> body = new HashMap<>();
    ArrayList<HospitalModel> arrayList = new ArrayList<>();
    private ArrayList<Integer> favArray = new ArrayList<>();

    private AdapterHospitalRecylcer adapterx;
    MaterialSearchView searchView;
    public RelativeLayout constraintLayout;
    String MY_PREFS_NAME = "FiltersH";

    String test;

    LinearLayoutManager linearLayoutManager;
    int page = 1;
    int last_page, language;
    ProgressBar progressBar;

    private static final int REQUEST_LOCATION = 1;
    Button button;
    TextView textView;
    String lattitude, longitude;
    private String lang;
    TextView txtFilter , txtSort;

    private String sortKey,sortValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitals);
        language = getIdLANG();
        localization(language);
        lang=checkLanguage(language);
        //setTitle("Hospitals");


        txtFilter = findViewById(R.id.filtering);
        txtSort = findViewById(R.id.sorting);

        txtFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HospitalsActivity.this, FiltersActivity.class);
                intent.putExtra("idFilter", 1);
                startActivity(intent);
                finish();
            }
        });


        txtSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sorting();
            }
        });

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        recyclerView = findViewById(R.id.hospital_recycler);
        constraintLayout = findViewById(R.id.fragment_hospital);
        progressBar = (ProgressBar) findViewById(R.id.pbHeaderProgress);
        progressBar.setVisibility(View.VISIBLE);

        //get all hos
        getHospitalPagenation("");


        //Custom Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_hospital);
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
                    linearLayoutManager = new LinearLayoutManager(HospitalsActivity.this);
                    adapterx = new AdapterHospitalRecylcer(HospitalsActivity.this, arrayList, 99505,favArray);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(adapterx);
                    getHospitalPagenation(test);

                } else {
                    Log.e("YYYYYYYYYYY", "finish()");

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
                Log.e("YYYYYYYYYYY", "onQueryTextSubmit");
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
                Log.e("YYYYYYYYYYY", "onSearchViewShown");


            }

            @Override
            public void onSearchViewClosed() {
                Log.e("YYYYYYYYYYY", "onSearchViewClosed");
                getHospital(test);
            }
        });

        adapterx = new AdapterHospitalRecylcer(this, arrayList, 99505,favArray);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterx);
        setActivityTitle("المستشفيات", "Hospitals");



        getCurrLocation();


        Log.e("duckduck",""+getMyCityName());
        getFavID();

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
                Intent intent = new Intent(HospitalsActivity.this, FiltersActivity.class);
                intent.putExtra("idFilter", 1);
                startActivity(intent);
                finish();

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

        arrayList = new ArrayList<>();

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        int city = prefs.getInt("city", 0); //0 is the default value.
        int area = prefs.getInt("area", 0); //0 is the default value.
        int rate = prefs.getInt("num_rate", 0); //0 is the default value.
        int speciality = prefs.getInt("speciality", 0); //0 is the default value.

        String sort_key= prefs.getString("order_by",""); //0 is the default value.
        String sort_value= prefs.getString("sortingValue",""); //0 is the default value.

        Log.e("CXAAAA", "city" + city + "\n" + "area" + area + "\n" + "rate" + rate + "Specialty=" + speciality);


        body.put("region", String.valueOf(city));
        body.put("city", String.valueOf(area));
        body.put("rate", String.valueOf(rate));
        body.put("speciality", String.valueOf(speciality));
        body.put("keyword", keyword);

        body.put("orderBy",sort_key);
        body.put("sort", sort_value);


        final SearchHospitalsRequest searchHospitalsRequest = new SearchHospitalsRequest(this, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response==", response);
                HospitalResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    NoInternt_Fragment fragment = new NoInternt_Fragment();
                    Bundle arguments = new Bundle();
                    arguments.putInt("duck", 101);
                    fragment.setArguments(arguments);
                    final android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.Hospital_Include, fragment, Utils.Error);
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
        searchHospitalsRequest.setBody((HashMap) body);
        searchHospitalsRequest.start();
    }

    public void HospitalResponse(String response) {
        progressBar.setVisibility(View.GONE);

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String name_hos = object.getString(lang+"_name");
                int id_hos = object.getInt("id");
                String address_hos = object.getString(lang+"_address");
                String note_hos = object.getString(lang+"_note");
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

                Log.e(TAG + "Response=", "" + id_hos);


                HospitalModel model = new HospitalModel(id_hos, name_hos, address_hos, note_hos, website_hos, email_hos, img_hos, phone_hos, phone2_hos, count_hos, rating_hos, fav_hos, createdAt_hos);


                arrayList.add(model);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapterx = new AdapterHospitalRecylcer(this, arrayList, 99505,favArray);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterx);


    }

    public void PagenatonResponse(String response) {
        progressBar.setVisibility(View.GONE);

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

                Log.e(TAG + "Response=", "" + address_hos);


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
                    getHospitalPagenation("");

                }


            }
        });

        adapterx.notifyItemRangeInserted(adapterx.getItemCount(), arrayList.size());


    }

    public void getHospitalPagenation(String keyword) {


        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        int city = prefs.getInt("city", 0); //0 is the default value.
        int area = prefs.getInt("area", 0); //0 is the default value.
        int rate = prefs.getInt("num_rate", 0); //0 is the default value.
        int speciality = prefs.getInt("speciality", 0); //0 is the default value.

        Log.e("CXAAAA", "city" + city + "\n" + "area" + area + "\n" + "rate" + rate + "Specialtion" + speciality);

        body.put("region", String.valueOf(city));
        body.put("city", String.valueOf(area));
        body.put("rate", String.valueOf(rate));
        body.put("speciality", String.valueOf(speciality));
        body.put("keyword", keyword);

        final SearchHospitalsRequest searchHospitalsRequest = new SearchHospitalsRequest(this, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response==", response);
                PagenatonResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    NoInternt_Fragment fragment = new NoInternt_Fragment();
                    Bundle arguments = new Bundle();
                    arguments.putInt("duck", 101);
                    fragment.setArguments(arguments);
                    final android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.Hospital_Include, fragment, Utils.Error);
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
        searchHospitalsRequest.setBody((HashMap) body);
        searchHospitalsRequest.start();
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

        editor.putString("order_by", "");
        editor.putString("sortingValue", "");
        editor.apply();

    }
    @Override
    protected void onStart() {
        super.onStart();
        favArray.clear();
        getFavID();
    }

    private void getFavID() {
        body.put("category", "hospital");
        SharedPreferences prefs = getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
        int userid = prefs.getInt("User_id", 0);
        GetFavoriteProducts getFavId = new GetFavoriteProducts(getApplicationContext(), userid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int a = 0; a < array.length(); a++) {
                        JSONObject object = array.getJSONObject(a);
                        int favourable_id = object.getInt("favourable_id");
                        favArray.add(favourable_id);
                    }
                    adapterx.notifyDataSetChanged();
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

    }

    private void sorting(){
        MaterialDialog dialog = new MaterialDialog.Builder(HospitalsActivity.this)
                .title(R.string.sort)
                .titleColor(getResources().getColor(R.color.black))
                .customView(R.layout.sorting_medical, true)
                .positiveText(R.string.aapply)
                .positiveColor(getResources().getColor(R.color.colorPrimary))

                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {

                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putString("order_by",sortKey);
                        editor.putString("sortingValue",sortValue);
                        editor.apply();

                        getHospital("");
                    }
                })
                .show();
        View views = dialog.getCustomView();

        RadioGroup radioGroupType = views.findViewById(R.id.Cosmetic_rate_RadioGroup);
        radioGroupType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId) {

                    case R.id.CosmeticRadio_LTH:
                        sortKey="rate";
                        sortValue="asc";
                        break;
                    case R.id.CosmeticRadio_HTL:
                        sortKey="rate";
                        sortValue="desc";
                        break;
                    default:
                        sortKey="";
                        sortValue="";
                        break;
                }
            }
        });
    }

}