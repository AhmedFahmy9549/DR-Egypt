package com.example.gmsproduction.dregypt.ui.fragments.AddItems;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetCitiesRequest;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetProductAdCategoriesRequest;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetRegionsRequest;
import com.example.gmsproduction.dregypt.Models.LocationModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.activities.AddItemActivity;
import com.example.gmsproduction.dregypt.ui.fragments.NoInternt_Fragment;
import com.example.gmsproduction.dregypt.utils.Constants;
import com.example.gmsproduction.dregypt.utils.CustomToast;
import com.example.gmsproduction.dregypt.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Hima on 6/25/2018.
 */

public abstract class BaseAddFragment extends Fragment {
    public EditText EdTitle, EdDesc, EdAddress, EdPhone, EdPhone2;
    private Spinner spinner, spinner1;
    public View view;
    private ArrayList<String> name_array, name_array2;
    public int x, city=-1, area=-1, userID;
    public String getEncodedImage = "";
    private String mFragmentName,mFragmentNameAr;
    private ArrayList<LocationModel> arrayModel, array2;
    private LinearLayout linearLayout;
    public Button AddBTN, imagetestbtn, addphone2;
    public static final int RESULT_IMG = 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanseState) {
        view = provideYourFragmentView(inflater, parent, savedInstanseState);
        //get UserID
        SharedPreferences prefs = getActivity().getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
        userID = prefs.getInt("User_id", 0);

        //set Fragment Name
        mFragmentType(mFragmentName,mFragmentNameAr);

        getCity();

        //BTNs
        AddBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation();
            }
        });
        imagetestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImage();
            }
        });
        return view;
    }
    public abstract View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState);

    public void initViews(int title, int phone1, int phone2, int description, int address, int city, int area,
                          int linearAddress, int btnImage, int btnShowPhone2, int btnDo) {

        EdTitle = view.findViewById(title);
        EdPhone = view.findViewById(phone1);
        EdPhone2 = view.findViewById(phone2);
        EdDesc = view.findViewById(description);
        EdAddress = view.findViewById(address);
        spinner = view.findViewById(city);
        spinner1 = view.findViewById(area);
        linearLayout = view.findViewById(linearAddress);
        AddBTN = view.findViewById(btnDo);
        imagetestbtn = view.findViewById(btnImage);

        addphone2 = view.findViewById(btnShowPhone2);

        addphone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EdPhone2.setVisibility(View.VISIBLE);
            }
        });

    }


    //get userId
    public int getUserID() {
        return userID;
    }

    //get and set FragmentName
    public void setmFragmentName(String mFragmentNameEn,String mFragmentNameAr) {
        this.mFragmentName = mFragmentNameEn;
        this.mFragmentNameAr = mFragmentNameAr;
    }
    public void mFragmentType(String en,String ar){
        ((AddItemActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AddItemActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AddItemActivity)getActivity()).setActivityTitle(ar,en);

    }

    //Location methods
    private void getCity() {
        GetRegionsRequest getRegionsRequest = new GetRegionsRequest(getActivity(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("ressss", response);
                arrayModel = new ArrayList<>();
                name_array = new ArrayList<>();

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
                    if (getActivity() != null) {
                        HintSpinner<String> hintSpinner = new HintSpinner<>(
                                spinner,
                                // Default layout - You don't need to pass in any layout id, just your hint text and
                                // your list data
                                new HintAdapter<String>(getActivity(), "City", name_array),
                                new HintSpinner.Callback<String>() {
                                    @Override
                                    public void onItemSelected(int position, String itemAtPosition) {
                                        // Here you handle the on item selected event (this skips the hint selected event)
                                        //String item = adapterView.getItemAtPosition(i).toString();
                                        LocationModel locationModel = arrayModel.get(position);
                                        city = locationModel.getLocId();

                                        Log.e("Ibrahim ateerfffffff al", "x" + x);
                                        linearLayout.setVisibility(View.VISIBLE);
                                        getArea(city);
                                        //to shazly area
                                    }
                                });
                        hintSpinner.init();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    /*if (getActivity() != null) {
                        NoInternt_Fragment fragment = new NoInternt_Fragment();
                        Bundle arguments = new Bundle();
                        arguments.putInt("duck", 5599);
                        fragment.setArguments(arguments);
                        final android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.additem_Include, fragment, Utils.Error);
                        ft.commit();
                    }*/

                } else if (error instanceof AuthFailureError) {
                    //TODO
                    Log.e("3333", "1");

                } else if (error instanceof ServerError) {
                    //TODO
                    Log.e("3333", "2");
                } else if (error instanceof NetworkError) {
                    //TODO
                    Log.e("3333", "3");
                    // Toast.makeText(ProductsActivity.this, "volly no Internet", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    //TODO
                    Log.e("3333", "4");

                }
            }
        });
        getRegionsRequest.start();

    }
    private void getArea(int name) {
        name_array2 = new ArrayList<>();
        array2 = new ArrayList<>();


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
                    if (getActivity() != null) {


                        HintSpinner<String> hintSpinner = new HintSpinner<>(
                                spinner1,
                                // Default layout - You don't need to pass in any layout id, just your hint text and
                                // your list data
                                new HintAdapter<String>(getActivity(), "Area", name_array2),
                                new HintSpinner.Callback<String>() {
                                    @Override
                                    public void onItemSelected(int position, String itemAtPosition) {
                                        // Here you handle the on item selected event (this skips the hint selected event)
                                        //String item = adapterView.getItemAtPosition(i).toString();

                                        LocationModel locationModel = array2.get(position);
                                        area = locationModel.getLocId();
                                        //to shazly area
                                        EdAddress.setVisibility(View.VISIBLE);
                                    }
                                });
                        hintSpinner.init();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, name);
        getCitiesRequest.start();


    }

    //image methods
    private void getImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_IMG);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_IMG && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            final InputStream imageStream;

            try {
                imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                String name;
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                // this is the image Sting
                getEncodedImage = "data:image/jpeg;base64," + encodeImage(selectedImage);
                Log.e("ENCimage", getEncodedImage);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    //validations (used on Submit BTN)
    public void Validation(){

    }

}


