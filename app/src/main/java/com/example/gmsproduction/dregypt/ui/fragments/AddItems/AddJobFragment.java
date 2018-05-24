package com.example.gmsproduction.dregypt.ui.fragments.AddItems;

/**
 * Created by Hima on 5/21/2018.
 */

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetCitiesRequest;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetJobAdCategoriesRequest;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetJobEducationLevelsRequest;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetJobEmploymentTypesRequest;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetJobExperienceLevelsRequest;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetProductAdCategoriesRequest;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetRegionsRequest;
import com.example.gmsproduction.dregypt.Models.LocationModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.utils.CustomToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.example.gmsproduction.dregypt.utils.Constants.USER_DETAILS;

public class AddJobFragment extends Fragment {

    private View view;
    private EditText EdTitle, EdSalary, EdDesc, EdAddress,EdPhone,Edphone2;
    private String getTitle, getSalary, getDesc, getAddress, getPhone,getPhone2, getEncodedImage, userID;
    Spinner spinner, spinner1, spinnerCategory, spinnerExpLevel, spinnerEducLevel,spinnerEmpType;
    ArrayList<String> name_array, name_array2, CategoryNameArray, ExpLebelNameArray, EduLevelNameArray,EmpTypeNameArray;
    int x, numRate, numType=55, city, area, category, expLevel, eduLevel,EmpType;
    ArrayList<LocationModel> arrayModel, array2, getArrayExiLevelModel, arrayEduLevelModel,EmpTypeModel;
    LinearLayout linearLayout;
    Button AddBTN, imagetestbtn,addphone2;
    public static final int RESULT_IMG = 1;
    RadioGroup radioGroup, radioGroupType;
    private RequestQueue mRequestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_job, container, false);
        getActivity().setTitle("Add Job");

         SharedPreferences prefs = getActivity().getSharedPreferences(USER_DETAILS, MODE_PRIVATE);
        userID = prefs.getString("id", null);

        initViews();
        getLocation();
        getExperienceLevel();
        getEducationLevel();
        setAdType();
        getCategory();
        getEmpType();
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


    private void initViews() {

        EdTitle = view.findViewById(R.id.Add_Job_Title);
        EdSalary = view.findViewById(R.id.Add_Job_Salary);
        EdPhone = view.findViewById(R.id.Add_Job_Phone);
        Edphone2 = view.findViewById(R.id.Add_Job_Phone2);
        EdDesc = view.findViewById(R.id.Add_Job_Desc);
        EdAddress = view.findViewById(R.id.Add_Job_Adress);

        spinner = view.findViewById(R.id.Add_Job_spinner_city);
        spinner1 = view.findViewById(R.id.Add_Job_spinner_area);
        spinnerCategory = view.findViewById(R.id.Add_Job_spinner_category);
        spinnerExpLevel = view.findViewById(R.id.Add_Job_spinner_EXPLVL);
        spinnerEducLevel = view.findViewById(R.id.Add_Job_spinner_EduLVL);
        spinnerEmpType = view.findViewById(R.id.Add_Job_spinner_EmpType);

        radioGroupType = view.findViewById(R.id.Add_Job_radio_group_AdType);


        linearLayout = view.findViewById(R.id.Add_Job_linear_area);
        AddBTN = view.findViewById(R.id.Add_Job_FinishBtn);
        imagetestbtn = view.findViewById(R.id.Add_Job_Image);
        addphone2 = view.findViewById(R.id.Add_Job_Add2ndPhone);

        addphone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edphone2.setVisibility(View.VISIBLE);
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
                    // Creating adapter for spinner
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, name_array);
                    // Drop down layout style - list view with radio button

                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // attaching data adapter to spinner
                    spinner.setAdapter(dataAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String item = adapterView.getItemAtPosition(i).toString();
                        LocationModel locationModel = arrayModel.get(i);
                        city = locationModel.getLocId();


                        Log.e("Ibrahim ateerfffffff al", "x" + x);
                        linearLayout.setVisibility(View.VISIBLE);
                        getArea(city);
                        //to shazly area


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
                    ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, name_array2);
                    dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner1.setAdapter(dataAdapter1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String item = adapterView.getItemAtPosition(i).toString();

                        LocationModel locationModel = array2.get(i);
                        area = locationModel.getLocId();
                        //to shazly area
                        EdAddress.setVisibility(View.VISIBLE);
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

    private void getCategory() {
        CategoryNameArray = new ArrayList<>();
        arrayModel = new ArrayList<>();

        GetJobAdCategoriesRequest getJobAdCategoriesRequest = new GetJobAdCategoriesRequest(getActivity(), new Response.Listener<String>() {
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
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, CategoryNameArray);
                    // Drop down layout style - list view with radio button

                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // attaching data adapter to spinner
                    spinnerCategory.setAdapter(dataAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }



                spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String item = adapterView.getItemAtPosition(i).toString();

                            LocationModel locationModel = arrayModel.get(i);
                            category = locationModel.getLocId();

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
        getJobAdCategoriesRequest.start();


    }

    private void getExperienceLevel() {
        ExpLebelNameArray = new ArrayList<>();
        getArrayExiLevelModel = new ArrayList<>();

        GetJobExperienceLevelsRequest getJobExperienceLevelsRequest = new GetJobExperienceLevelsRequest(getActivity(), new Response.Listener<String>() {
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
                        ExpLebelNameArray.add(categName);
                        getArrayExiLevelModel.add(model);
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ExpLebelNameArray);
                    // Drop down layout style - list view with radio button

                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // attaching data adapter to spinner
                    spinnerExpLevel.setAdapter(dataAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                spinnerExpLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String item = adapterView.getItemAtPosition(i).toString();

                            LocationModel locationModel = getArrayExiLevelModel.get(i);
                            expLevel = locationModel.getLocId();


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
        getJobExperienceLevelsRequest.start();


    }

    private void getEducationLevel() {
        EduLevelNameArray = new ArrayList<>();
        arrayEduLevelModel = new ArrayList<>();

        GetJobEducationLevelsRequest getJobEducationLevelsRequest = new GetJobEducationLevelsRequest(getActivity(), new Response.Listener<String>() {
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
                        EduLevelNameArray.add(categName);
                        arrayEduLevelModel.add(model);
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, EduLevelNameArray);
                    // Drop down layout style - list view with radio button

                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // attaching data adapter to spinner
                    spinnerEducLevel.setAdapter(dataAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }



                spinnerEducLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String item = adapterView.getItemAtPosition(i).toString();

                            LocationModel locationModel = arrayEduLevelModel.get(i);
                            eduLevel = locationModel.getLocId();

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
        getJobEducationLevelsRequest.start();


    }

    //ful - part time
    private void getEmpType() {
        EmpTypeNameArray = new ArrayList<>();
        EmpTypeModel = new ArrayList<>();

        GetJobEmploymentTypesRequest getJobEducationLevelsRequest = new GetJobEmploymentTypesRequest(getActivity(), new Response.Listener<String>() {
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
                        EmpTypeNameArray.add(categName);
                        EmpTypeModel.add(model);
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, EmpTypeNameArray);
                    // Drop down layout style - list view with radio button

                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // attaching data adapter to spinner
                    spinnerEmpType.setAdapter(dataAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }



                spinnerEmpType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String item = adapterView.getItemAtPosition(i).toString();

                        LocationModel locationModel = EmpTypeModel.get(i);
                        EmpType = locationModel.getLocId();

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
        getJobEducationLevelsRequest.start();


    }

    // job seeker
    private void setAdType() {
        // get selected radio button from radioGroup
        radioGroupType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId) {

                    case R.id.Add_Job_radio_status1:
                        numType = 1;
                        break;
                    case R.id.Add_Job_radio_status2:
                        numType = 2;
                        break;
                    default:
                        numType = 55;
                        break;
                }
            }
        });


    }

    private void Validation() {

        getTitle = EdTitle.getText().toString();
        getSalary = EdSalary.getText().toString();
        getDesc = EdDesc.getText().toString();
        getAddress = EdAddress.getText().toString();
        getPhone = EdPhone.getText().toString();
        getPhone2 = Edphone2.getText().toString();

        String getCity = String.valueOf(city);
        String getArea = String.valueOf(area);
        String getCategory = String.valueOf(category);
        String getJobTybe = String.valueOf(numType);
        String getExpLvl = String.valueOf(expLevel);
        String getEduLvl = String.valueOf(eduLevel);
        String getEmpType = String.valueOf(EmpType);

        String getID = String.valueOf(103);
        //userID

        //getTitle,getPrice,getDesc,getAddress,getPhone,city,area,category,numStatus,getEncodedImage
        if (getTitle.equals("") || getTitle.length() == 0
                || getSalary.equals("") || getSalary.length() == 0
                || getDesc.equals("") || getDesc.length() == 0
                || getAddress.equals("") || getAddress.length() == 0
                || getPhone.equals("") || getPhone.length() == 0) {
            new CustomToast().Show_Toast(getActivity(), view, "All fields are required.");
        } else if (category == -1) {
            new CustomToast().Show_Toast(getActivity(), view, "Please Select Product Category.");
        } /*else if (numType == 55) {
            new CustomToast().Show_Toast(getActivity(), view, "Please Select Product Status.");
        }*/ else {
            Toast.makeText(getActivity(), "Do Do.", Toast.LENGTH_SHORT)
                    .show();
            postJob(getID,getTitle,getSalary,getDesc,getAddress,getPhone,getPhone2,getCity,getArea,getCategory,getJobTybe,getExpLvl,getEduLvl,getEmpType,getEncodedImage);
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
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                // this is the image Sting
                getEncodedImage = encodeImage(selectedImage);
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
    public void postJob(String id, String title, String salary, String Desc, String addres, String phone,
                            String phone2, String city, String area, String category,
                        String jobType,String experienceLeve,String educationLevel,String employmentType, String img) {

        JSONObject jsonobject_one = new JSONObject();
        JSONObject jsonobject_Two = new JSONObject();
        JSONArray PhoneArray = new JSONArray();
        try {
            PhoneArray.put(phone);
            if (phone2!=null && !phone2.isEmpty()){
                PhoneArray.put(phone2);
            }
            jsonobject_one.put("userId", id);
            jsonobject_one.put("title", title);
            jsonobject_one.put("description", Desc);
            jsonobject_one.put("salary", salary);
            jsonobject_one.put("regionId",city);
            jsonobject_one.put("cityId",area);
            jsonobject_one.put("address", addres);
            jsonobject_one.put("jobTypeId", jobType);
            jsonobject_one.put("categoryId", category);
            jsonobject_one.put("experienceLevelId", experienceLeve);
            jsonobject_one.put("educationLevelId", educationLevel);
            jsonobject_one.put("employmentTypeId", employmentType);


            //phone array
            jsonobject_one.put("phone",PhoneArray);
            //file object
            jsonobject_Two.put("file", "data:image/jpeg;base64,"+img);
            jsonobject_one.put("img",jsonobject_Two);





            Log.e("gaga",""+jsonobject_one);
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                    Request.Method.POST, "https://dregy01.frb.io/api/job-ads", jsonobject_one,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.e("addJob", "res" + response);
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("addJob", "err" + error);
                }
            });
            mRequestQueue = Volley.newRequestQueue(getActivity());
            mRequestQueue.add(jsonObjReq);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
