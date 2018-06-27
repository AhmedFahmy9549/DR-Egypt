package com.example.gmsproduction.dregypt.ui.fragments.AddItems;

/**
 * Created by Hima on 5/21/2018.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetCitiesRequest;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetProductAdCategoriesRequest;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetRegionsRequest;
import com.example.gmsproduction.dregypt.Models.LocationModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.activities.AddItemActivity;
import com.example.gmsproduction.dregypt.ui.activities.LogInActivity;
import com.example.gmsproduction.dregypt.ui.activities.ProductsActivity;
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
import static com.example.gmsproduction.dregypt.utils.Constants.USER_DETAILS;

public class AddProductFragment extends Fragment {

    private View view;
    private EditText EdTitle, EdPrice, EdDesc, EdAddress, EdPhone, EdPhone2;
    private String getTitle = "", getPrice = "", getDesc = "", getAddress = "", getPhone = "", getPhone2 = "",
            getEncodedImage = "", editTitle = "", editPrice = "", editDesc = "", editPhone1 = "", editPhone2 = "", editAddres = "",PhID1,PhID2;
    private Spinner spinner, spinner1, spinnerCategory;
    ArrayList<String> name_array, name_array2, CategoryNameArray;
    int x, numRate, numStatus = 55, city, area, category, radiogroubValidation = 55, userID;
    ArrayList<LocationModel> arrayModel, array2;
    LinearLayout linearLayout;
    Button AddBTN, imagetestbtn, addphone2;
    public static final int RESULT_IMG = 1;
    private RadioGroup radioGroupStatus;
    private RequestQueue mRequestQueue;
    int MethodID = Request.Method.POST;
    String url = Constants.basicUrl + "/product-ads";
    int desired_Int, ProductID ;
    Map<String, String> map;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_product, container, false);
        //to get user data
        SharedPreferences prefs = getActivity().getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
        userID = prefs.getInt("User_id", 0);

        ((AddItemActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AddItemActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        //get bundle to check if this is add or edit fragment
        getEditData();
        initViews();
        //set title of Activity
        if (desired_Int == 55) {
            getActivity().setTitle("Edit Product");
            //to set data into the EditText for EditFragment
            setEditData();
            MethodID = Request.Method.PUT;
            url = Constants.basicUrl+"/product-ads/"+ProductID;
            AddBTN.setText("Edit Product");

        } else {
            getActivity().setTitle("Add Product");
        }


        Log.e("idAdd", "Pro" + userID);


        getLocation();
        getCategory();
        setStatus();
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


    private void getEditData() {
        Bundle arguments = getArguments();
        desired_Int = arguments.getInt("Edit", 0);
        editTitle = arguments.getString("title");
        editPrice = arguments.getString("price");
        editDesc = arguments.getString("Desc");
        editPhone1 = arguments.getString("phone1");
        editPhone2 = arguments.getString("phone2");

        PhID1 = arguments.getString("phoneID1");
        PhID2 = arguments.getString("phoneID2");

        editAddres = arguments.getString("Addres");
        getEncodedImage = arguments.getString("Img");
        ProductID = arguments.getInt("ProductID", 0);
    }

    private void setEditData() {
        EdTitle.setText(editTitle);
        EdPrice.setText(editPrice);
        EdDesc.setText(editDesc);
        EdPhone.setText(editPhone1);
        EdPhone2.setText(editPhone2);
        EdAddress.setText(editAddres);
    }

    private void initViews() {

        EdTitle = view.findViewById(R.id.Add_product_Title);
        EdPrice = view.findViewById(R.id.Add_product_Price);
        EdPhone = view.findViewById(R.id.Add_product_Phone);
        EdPhone2 = view.findViewById(R.id.Add_product_Phone2);
        EdDesc = view.findViewById(R.id.Add_product_Desc);
        EdAddress = view.findViewById(R.id.Add_product_Adress);
        spinner = view.findViewById(R.id.Add_product_spinner_city);
        spinner1 = view.findViewById(R.id.Add_product_spinner_area);
        spinnerCategory = view.findViewById(R.id.Add_product_spinner_category);
        radioGroupStatus = view.findViewById(R.id.Add_product_radio_group_status);
        linearLayout = view.findViewById(R.id.Add_product_linear_area);
        AddBTN = view.findViewById(R.id.Add_product_FinishBtn);
        imagetestbtn = view.findViewById(R.id.Add_product_Image);

        addphone2 = view.findViewById(R.id.Add_product_Add2ndPhone);

        addphone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EdPhone2.setVisibility(View.VISIBLE);
            }
        });

    }

    private void getLocation() {
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
                    if (getActivity() != null) {
                        NoInternt_Fragment fragment = new NoInternt_Fragment();
                        Bundle arguments = new Bundle();
                        arguments.putInt("duck", 5599);
                        fragment.setArguments(arguments);
                        final android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.additem_Include, fragment, Utils.Error);
                        ft.commit();
                    }
                    /*fragmentManager
                            .beginTransaction()
                            .add(R.id.dodododo, new NoInternt_Fragment(),
                                    Utils.Error).commit();*/


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

    private void getCategory() {
        CategoryNameArray = new ArrayList<>();
        arrayModel = new ArrayList<>();


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
                    if (getActivity() != null) {
                        HintSpinner<String> hintSpinner = new HintSpinner<>(
                                spinnerCategory,
                                // Default layout - You don't need to pass in any layout id, just your hint text and
                                // your list data
                                new HintAdapter<String>(getActivity(), "Choose Category", CategoryNameArray),
                                new HintSpinner.Callback<String>() {
                                    @Override
                                    public void onItemSelected(int position, String itemAtPosition) {
                                        // Here you handle the on item selected event (this skips the hint selected event)
                                        //String item = adapterView.getItemAtPosition(i).toString();

                                        LocationModel locationModel = arrayModel.get(position);
                                        category = locationModel.getLocId();
                                        //toshazly
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
        });
        getProductAdCategoriesRequest.start();


    }

    private void setStatus() {
        // get selected radio button from radioGroup
        radioGroupStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId) {

                    case R.id.Add_product_radio_status1:
                        numStatus = 1;
                        radiogroubValidation = 1;
                        break;
                    case R.id.Add_product_radio_status2:
                        numStatus = 2;
                        radiogroubValidation = 2;
                        break;
                    default:
                        numRate = 55;
                        radiogroubValidation = 55;
                        break;
                }
            }
        });


    }

    private void Validation() {
        getTitle = EdTitle.getText().toString();
        getPrice = EdPrice.getText().toString();
        getDesc = EdDesc.getText().toString();
        getAddress = EdAddress.getText().toString();
        getPhone = EdPhone.getText().toString();
        getPhone2 = EdPhone2.getText().toString();

        String getCity = String.valueOf(city);
        String getArea = String.valueOf(area);
        String getCategory = String.valueOf(category);
        String getNumStatus = String.valueOf(numStatus);
        String getID = String.valueOf(userID);

        //getTitle,getPrice,getDesc,getAddress,getPhone,city,area,category,numStatus,getEncodedImage
        if (getTitle.equals("") || getTitle.length() == 0
                || getPrice.equals("") || getPrice.length() == 0
                || getDesc.equals("") || getDesc.length() == 0
                || getAddress.equals("") || getAddress.length() == 0
                || getPhone.equals("") || getPhone.length() == 0) {
            new CustomToast().Show_Toast(getActivity(), view, "All fields are required.");
        } else if (category == -1) {
            new CustomToast().Show_Toast(getActivity(), view, "Please Select Product Category.");
        } else if (numStatus == 55) {
            new CustomToast().Show_Toast(getActivity(), view, "Please Select Product Status.");
        } else if (getDesc.length() < 21) {
            new CustomToast().Show_Toast(getActivity(), view, "description must be higher than 20 letter");
        }else if (getEncodedImage.equals("") || getEncodedImage.length() == 0){
            new CustomToast().Show_Toast(getActivity(), view, "Please add Image");
        } else {
            Toast.makeText(getActivity(), "Do Do.", Toast.LENGTH_SHORT)
                    .show();
            postProduct(getID, getTitle, getPrice, getDesc, getAddress, getPhone, getPhone2, getCity, getArea, getCategory, getNumStatus, getEncodedImage);
        }


    }

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

    //getTitle,getPrice,getDesc,getAddress,getPhone,city,area,category,numStatus,getEncodedImage
    public void postProduct(String id, String title, String price, String Desc, String addres, String phone, String phone2, String city, String area, String category, String Status, String img) {

        JSONObject jsonobject_one = new JSONObject();
        JSONObject jsonobject_Two = new JSONObject();
        JSONObject phone01 = new JSONObject();
        JSONObject phone02 = new JSONObject();

        JSONArray PhoneArray = new JSONArray();
        try {
            if (desired_Int==55){
                phone01.put("id",PhID1);
                phone01.put("number",phone);
                PhoneArray.put(phone01);

                if (phone2 != null && !phone2.isEmpty()) {
                    phone02.put("id",PhID2);
                    phone02.put("number",phone2);
                    PhoneArray.put(phone02);
                }
            }else {
                PhoneArray.put(phone);
                if (phone2 != null && !phone2.isEmpty()) {
                    PhoneArray.put(phone2);
                }
            }

            jsonobject_one.put("userId", id);
            jsonobject_one.put("title", title);
            jsonobject_one.put("description", Desc);
            jsonobject_one.put("price", price);
            jsonobject_one.put("regionId", city);
            jsonobject_one.put("cityId", area);
            jsonobject_one.put("address", addres);
            jsonobject_one.put("status", Status);
            jsonobject_one.put("categoryId", category);
            jsonobject_one.put("phone", PhoneArray);


            //phone array
            //file object
            jsonobject_Two.put("file", img);
            jsonobject_one.put("img", jsonobject_Two);

            Log.e("gaga", "" + jsonobject_one);
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                    MethodID, url, jsonobject_one,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.e("addpro", "res" + response);
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("addpro", "err" + error);
                    if (error == null || error.networkResponse == null) {
                        return;
                    }

                    String body;
                    //get status code here
                    final String statusCode = String.valueOf(error.networkResponse.statusCode);
                    Log.e("addpro", "statusCode  " + statusCode);

                    //get response body and parse with appropriate encoding
                    try {
                        body = new String(error.networkResponse.data,"UTF-8");
                        Log.e("addpro", "body " + body);
                    } catch (UnsupportedEncodingException e) {
                        // exception
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Accept", "application/json");
                    params.put("Content-Type", "application/json");
                    return params;
                }
            };
            mRequestQueue = Volley.newRequestQueue(getActivity());
            mRequestQueue.add(jsonObjReq);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
