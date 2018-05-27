package com.example.gmsproduction.dregypt.ui.fragments.Filters;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetCitiesRequest;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetProductAdCategoriesRequest;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetRegionsRequest;
import com.example.gmsproduction.dregypt.Models.LocationModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.activities.ProductsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ahmed Fahmy on 5/13/2018.
 */

public class ProductFilters extends Fragment {
    View view;
    Spinner spinner, spinner1, spinnerCategory;
    ArrayList<LocationModel> arrayModel, array2;
    ArrayList<String> name_array, name_array2, CategoryNameArray;
    LinearLayout linearLayout;
    Button applay;
    RadioGroup radioGroup, radioGroupStatus;
    int x, numRate, numStatus, city, area, category;
    String MY_PREFS_NAME = "FiltersPro";


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_filter_products, container, false);
        spinner = view.findViewById(R.id.spinner_city);
        spinner1 = view.findViewById(R.id.spinner_area);
        spinnerCategory = view.findViewById(R.id.spinner_category);

        linearLayout = view.findViewById(R.id.linear_area);
        applay = view.findViewById(R.id.btn_applay);

        radioGroup = view.findViewById(R.id.radio_group);
        radioGroupStatus = view.findViewById(R.id.radio_group_status);

        applay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSpinnerStatus();
                setSpinnerRating();

                Log.e("TTTTTTTTTTTTT", "Num =Rate" + numRate);
                Log.e("FFFFFFFFFFFFF", "City=" + city);
                Log.e("CCCCCCCCCCCCC", "Area=" + area);
                Log.e("YYYYYYYYYYYYY", "Category=" + category);
                Log.e("WWWWWWWWWWWWW", "Status=" + numStatus);


                SharedPreferences.Editor editor = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putInt("num_rate", numRate);
                editor.putInt("city", city);
                editor.putInt("area", area);
                editor.putInt("area", area);
                editor.putInt("category", category);
                editor.putInt("status", numStatus);

                editor.apply();
                Intent intent = new Intent(getActivity(), ProductsActivity.class);
                startActivity(intent);


            }
        });
        getLocation();
        getCategory();
        return view;
    }

    private void getLocation() {
        GetRegionsRequest getRegionsRequest = new GetRegionsRequest(getActivity(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                arrayModel = new ArrayList<>();
                name_array = new ArrayList<>();

                name_array.add("All");
                arrayModel.add(new LocationModel("", -1));
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String specName = object.getString("en_name");
                        int regionId = object.getInt("id");

                        LocationModel model = new LocationModel(specName, regionId);

                        name_array.add(specName);
                        arrayModel.add(model);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // Creating adapter for spinner
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, name_array);
                // Drop down layout style - list view with radio button

                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner
                spinner.setAdapter(dataAdapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String item = adapterView.getItemAtPosition(i).toString();

                        if (i == 0) {
                            linearLayout.setVisibility(View.INVISIBLE);
                            city = 0;


                        } else {
                            LocationModel locationModel = arrayModel.get(i);
                            city = locationModel.getLocId();


                            Log.e("Ibrahim ateerfffffff al", "x" + x);
                            linearLayout.setVisibility(View.VISIBLE);
                            getArea(city);


                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        getRegionsRequest.start();

    }

    private void getArea(int name) {
        name_array2 = new ArrayList<>();
        array2 = new ArrayList<>();

        name_array2.add("All");
        array2.add(new LocationModel("", -1));


        GetCitiesRequest getCitiesRequest = new GetCitiesRequest(getActivity(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    JSONArray jsonArray = jsonObject1.getJSONArray("cities");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String specName = object.getString("en_name");
                        int regionId = object.getInt("id");

                        LocationModel model = new LocationModel(specName, regionId);


                        array2.add(model);
                        name_array2.add(specName);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, name_array2);
                dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(dataAdapter1);
                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String item = adapterView.getItemAtPosition(i).toString();

                        if (i == 0) {
                            area = 0;


                        } else {
                            LocationModel locationModel = array2.get(i);
                            area = locationModel.getLocId();


                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, name);
        getCitiesRequest.start();


    }

    private void setSpinnerRating() {
        // get selected radio button from radioGroup
        int selectedId = radioGroup.getCheckedRadioButtonId();


        switch (selectedId) {
            case R.id.radio_rate5:
                Toast.makeText(getActivity(), "Hi from 5", Toast.LENGTH_SHORT).show();
                numRate = 5;
                break;
            case R.id.radio_rate4:
                Toast.makeText(getActivity(), "Hi from 4", Toast.LENGTH_SHORT).show();
                numRate = 4;

                break;
            case R.id.radio_rate3:
                Toast.makeText(getActivity(), "Hi from 3", Toast.LENGTH_SHORT).show();
                numRate = 3;

                break;
            case R.id.radio_rate2:
                Toast.makeText(getActivity(), "Hi from 2", Toast.LENGTH_SHORT).show();
                numRate = 2;

                break;
            case R.id.radio_rate1:
                Toast.makeText(getActivity(), "Hi from 1", Toast.LENGTH_SHORT).show();
                numRate = 1;
                break;


            default:
                numRate = 0;
                break;


        }

    }

    private void getCategory() {
        CategoryNameArray = new ArrayList<>();
        arrayModel = new ArrayList<>();

        arrayModel.add(new LocationModel("", -1));
        CategoryNameArray.add("All");

        GetProductAdCategoriesRequest getProductAdCategoriesRequest = new GetProductAdCategoriesRequest(getActivity(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String categName = object.getString("en_name");
                        int categId = object.getInt("id");
                        LocationModel model = new LocationModel(categName, categId);
                        CategoryNameArray.add(categName);
                        arrayModel.add(model);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, CategoryNameArray);
                // Drop down layout style - list view with radio button

                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner
                spinnerCategory.setAdapter(dataAdapter);

                spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String item = adapterView.getItemAtPosition(i).toString();

                        if (i == 0) {
                            category = 0;


                        } else {
                            LocationModel locationModel = arrayModel.get(i);
                            category = locationModel.getLocId();


                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        getProductAdCategoriesRequest.start();


    }

    private void setSpinnerStatus() {
        // get selected radio button from radioGroup
        int selectedId = radioGroupStatus.getCheckedRadioButtonId();


        switch (selectedId) {
            case R.id.radio_status1:
                Toast.makeText(getActivity(), "Hi from 5", Toast.LENGTH_SHORT).show();
                numStatus = 0;
                break;
            case R.id.radio_status2:
                Toast.makeText(getActivity(), "Hi from 4", Toast.LENGTH_SHORT).show();
                numStatus = 1;

                break;
            case R.id.radio_status3:
                Toast.makeText(getActivity(), "Hi from 3", Toast.LENGTH_SHORT).show();
                numStatus = 2;
            default:
                numRate = 0;
                break;


        }

    }

}


