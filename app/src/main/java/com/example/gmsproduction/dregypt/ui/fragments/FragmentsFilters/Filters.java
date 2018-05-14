package com.example.gmsproduction.dregypt.ui.fragments.FragmentsFilters;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetCitiesRequest;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetRegionsRequest;
import com.example.gmsproduction.dregypt.Models.LocationModel;
import com.example.gmsproduction.dregypt.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ahmed Fahmy on 5/13/2018.
 */

public class Filters extends Fragment {
    View view;
    Spinner spinner, spinner1;
    ArrayList<LocationModel> array, array2;
    ArrayList<String> name_array, name_array2;
    LinearLayout linearLayout;
    Button applay;
    RadioGroup radioGroup;
    RadioButton radioButton;
    int x, numRate, city, area;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_filter_hospital, container, false);
        spinner = view.findViewById(R.id.spinner_city);
        spinner1 = view.findViewById(R.id.spinner_area);
        linearLayout = view.findViewById(R.id.linear_area);
        applay = view.findViewById(R.id.btn_applay);

        radioGroup = view.findViewById(R.id.radio_group);

        applay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSpinnerRating();

                Log.e("TTTTTTTTTTTTT", "Num Rate=" + numRate);
                Log.e("FFFFFFFFFFFFF", "City=" + city);
                Log.e("CCCCCCCCCCCCC", "Area=" + area);


            }
        });
        getLocation();
        return view;
    }

    private void getLocation() {
        GetRegionsRequest getRegionsRequest = new GetRegionsRequest(getActivity(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                array = new ArrayList<>();
                name_array = new ArrayList<>();

                name_array.add("All");
                array.add(new LocationModel("", -1));
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String specName = object.getString("en_name");
                        int regionId = object.getInt("id");

                        LocationModel model = new LocationModel(specName, regionId);

                        name_array.add(specName);
                        array.add(model);
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
                            LocationModel locationModel = array.get(i);
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
        array2.add(new LocationModel("", 0));



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
                            area=0;


                        } else {
                            LocationModel locationModel = array2.get(i);
                            area = locationModel.getLocId() - 1;


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


}


