package com.example.gmsproduction.dregypt.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.gmsproduction.dregypt.Models.HospitalModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.fragments.FragmentsFilters.AdapterHospitalRecylcer;
import com.example.gmsproduction.dregypt.ui.fragments.NoInternt_Fragment;
import com.example.gmsproduction.dregypt.utils.Constants;
import com.example.gmsproduction.dregypt.utils.Utils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HospitalsActivity extends BaseActivity {

    RecyclerView recyclerView;
    View view;
    String TAG = "HospitalsActivity";
    Map<String, String> body = new HashMap<>();
    ArrayList<HospitalModel> arrayList = new ArrayList<>();
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
    LocationManager locationManager;
    String lattitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitals);
        language = getIdLANG();
        localization(language);
        //setTitle("Hospitals");


        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        getCurrLocation();

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
                    adapterx = new AdapterHospitalRecylcer(HospitalsActivity.this, arrayList, 99505);
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

        adapterx = new AdapterHospitalRecylcer(this, arrayList, 99505);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterx);
        setActivityTitle("المستشفيات", "Hospitals");
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

        Log.e("CXAAAA", "city" + city + "\n" + "area" + area + "\n" + "rate" + rate + "Specialty=" + speciality);


        body.put("region", String.valueOf(city));
        body.put("city", String.valueOf(area));
        body.put("rate", String.valueOf(rate));
        body.put("speciality", String.valueOf(speciality));
        body.put("keyword", keyword);

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

                Log.e(TAG + "Response=", "" + id_hos);


                HospitalModel model = new HospitalModel(id_hos, name_hos, address_hos, note_hos, website_hos, email_hos, img_hos, phone_hos, phone2_hos, count_hos, rating_hos, fav_hos, createdAt_hos);


                arrayList.add(model);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapterx = new AdapterHospitalRecylcer(this, arrayList, 99505);
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

                Log.e(TAG + "Response=", "" + id_hos);


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

        adapterx.notifyItemRangeInserted(adapterx.getItemCount(), arrayList.size() - 1);


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
        editor.apply();

    }

    private void getCurrLocation() {


        locationManager = (LocationManager)

                getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))

        {        Log.e("GBSLOCATION","buildAlertMessageNoGps");

            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))

        {
            Log.e("GBSLOCATION","getLocation");
            getLocation();
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                Log.e("GBSLOCATION","Your current location is"+ "\n" + "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude+"NETWORK_PROVIDER");




                Geocoder gcd = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = gcd.getFromLocation(latti, longi, 1);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (addresses.size() > 0) {
                    System.out.println(addresses.get(0).getLocality());
                }
                else {
                    // do your stuff
                }


            /*    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(latti,longi, 1);
                    String cityName = addresses.get(0).getAddressLine(0);
                    String stateName = addresses.get(0).getAddressLine(0);
                    String countryName = addresses.get(0).getAddressLine(0);
                    Log.e("CityName",""+cityName);
                    Log.e("countryName",""+countryName);
                    Log.e("stateName",""+stateName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
*/

            } else  if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                Log.e("GBSLOCATION","Your current location is"+ "\n" + "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude+"GPS_PROVIDER");


            } else  if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                Log.e("GBSLOCATION","Your current location is"+ "\n" + "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude+"PASSIVE_PROVIDER");

            }else{

                Toast.makeText(this,"Unble to Trace your location",Toast.LENGTH_SHORT).show();


            }
        }
    }

    protected void buildAlertMessageNoGps() {
        Log.e("GBSLOCATION","Here");

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        Log.e("GBSLOCATION","Here");
                        getLocation();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
        Log.e("GBSLOCATION","sare");

    }
}