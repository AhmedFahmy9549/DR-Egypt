package com.example.gmsproduction.dregypt.ui.fragments.Filters;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetCitiesRequest;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetClinicSpecialitiesRequest;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetRegionsRequest;
import com.example.gmsproduction.dregypt.Models.LocationModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.activities.ClinicsActivity;
import com.example.gmsproduction.dregypt.ui.activities.FiltersActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ahmed Fahmy on 5/13/2018.
 */

public class ClinicsFilters extends Fragment {
    View view;
    Spinner spinner, spinner1, spinner2;
    ArrayList<LocationModel> array, array2;
    ArrayList<String> name_array, name_array2, SpecialNameArray;
    LinearLayout linearLayout;
    Button applay;
    RadioGroup radioGroup;
    RadioButton radioButton;
    int x, numRate, city, area, speciality,language;
    String MY_PREFS_NAME = "FiltersCli";
    String specName,specName1,specName2;


    TextView manuelTXT, uselessTXT, gpsBtn, gbsText;
    ConstraintLayout constrainLocation;
    LinearLayout linearLocationManuel;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_filter_clinics, container, false);
        spinner = view.findViewById(R.id.spinner_city);
        spinner1 = view.findViewById(R.id.spinner_area);
        spinner2 = view.findViewById(R.id.spinner_speciality);

        linearLayout = view.findViewById(R.id.linear_area);
        applay = view.findViewById(R.id.btn_applay);

        radioGroup = view.findViewById(R.id.radio_group);

        manuelTXT = view.findViewById(R.id.filter_choose_location);
        gpsBtn = view.findViewById(R.id.filter_detect_location);
        constrainLocation = view.findViewById(R.id.filter_location_choice);
        linearLocationManuel = view.findViewById(R.id.filter_location_select);
        uselessTXT = view.findViewById(R.id.uselessCity);
        gbsText = view.findViewById(R.id.gbstext);

        click();

        if (getActivity()!=null){
            language = ((FiltersActivity)getActivity()).getLanguage();
        }


        applay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSpinnerRating();

                Log.e("TTTTTTTTTTTTT", "Num =Rate" + numRate);
                Log.e("FFFFFFFFFFFFF", "City=" + city);
                Log.e("CCCCCCCCCCCCC", "Area=" + area);
                Log.e("YYYYYYYYYYYYY", "Special=" + speciality);


                SharedPreferences.Editor editor = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putInt("num_rate", numRate);
                editor.putInt("city", city);
                editor.putInt("area", area);
                editor.putInt("area", area);
                editor.putInt("speciality", speciality);
                editor.apply();
                Intent intent = new Intent(getActivity(), ClinicsActivity.class);
                startActivity(intent);


            }
        });
        getLocation();
        getSpeciality();
        return view;
    }

    private void getLocation() {
        GetRegionsRequest getRegionsRequest = new GetRegionsRequest(getActivity(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                array = new ArrayList<>();
                name_array = new ArrayList<>();

                if (((FiltersActivity) getActivity()).getLanguage() == 1) {
                    name_array.add("All");

                } else
                    name_array.add("الكل");


                array.add(new LocationModel("", -1));
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        if (((FiltersActivity) getActivity()).getLanguage() == 1) {
                             specName = object.getString("en_name");

                        } else {
                             specName = object.getString("ar_name");

                        }

                        int regionId = object.getInt("id");

                        LocationModel model = new LocationModel(specName, regionId);

                        name_array.add(specName);
                        array.add(model);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (getActivity()!=null){
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
                });}
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

        if(language==1) {
            name_array2.add("All");

        }
        else
            name_array2.add("الكل");

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

                        if(language==1) {
                             specName1 = object.getString("en_name");

                        }
                        else
                             specName1 = object.getString("ar_name");


                        int regionId = object.getInt("id");

                        LocationModel model = new LocationModel(specName1, regionId);


                        array2.add(model);
                        name_array2.add(specName1);

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

    private void getSpeciality() {
        SpecialNameArray = new ArrayList<>();
        array = new ArrayList<>();

        array.add(new LocationModel("", -1));

        if(language==1) {
            SpecialNameArray.add("All");

        }
        else
            SpecialNameArray.add("الكل");


        GetClinicSpecialitiesRequest getClinicSpecialitiesRequest = new GetClinicSpecialitiesRequest(getActivity(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);


                        if(language==1) {
                             specName2 = object.getString("en_name");

                        }
                        else
                             specName2 = object.getString("ar_name");

                        int specId = object.getInt("id");
                        LocationModel model = new LocationModel(specName2, specId);
                        SpecialNameArray.add(specName2);
                        array.add(model);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (getActivity()!=null){
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, SpecialNameArray);
                // Drop down layout style - list view with radio button

                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner
                spinner2.setAdapter(dataAdapter);

                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String item = adapterView.getItemAtPosition(i).toString();

                        if (i == 0) {
                            speciality = 0;


                        } else {
                            LocationModel locationModel = array.get(i);
                            speciality = locationModel.getLocId();


                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        getClinicSpecialitiesRequest.start();


    }

    private void click() {
        manuelTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* constrainLocation.setVisibility(View.GONE);
                uselessTXT.setVisibility(view.GONE);*/
                linearLocationManuel.setVisibility(View.VISIBLE);
                gbsText.setVisibility(View.GONE);


            }
        });


        gpsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLocationManuel.setVisibility(View.GONE);
                gbsText.setVisibility(View.VISIBLE);
                ((FiltersActivity) getActivity()).getCurrLocation();
                String x = ((FiltersActivity) getActivity()).getMyCityName();


                Log.e("MY Location=", "" + x);

                if(language==1) {

                    gbsText.setText("Location: " + x);
                }
                else
                    gbsText.setText("الموقع: " + x);


                SearchInGps(x);


            }
        });
    }

    private void SearchInGps(String gover) {


        try {
            city = (int) getKeyFromValue(((FiltersActivity) getActivity()).init(), gover);

        } catch (Exception e) {


        }

        area = 0;

        Log.e("GETCURRENTLOCATION", "" + city);

    }


    public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }
}


