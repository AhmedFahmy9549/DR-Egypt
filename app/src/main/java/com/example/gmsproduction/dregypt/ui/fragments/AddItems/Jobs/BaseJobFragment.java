package com.example.gmsproduction.dregypt.ui.fragments.AddItems.Jobs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetJobAdCategoriesRequest;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetJobEducationLevelsRequest;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetJobEmploymentTypesRequest;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetJobExperienceLevelsRequest;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetProductAdCategoriesRequest;
import com.example.gmsproduction.dregypt.Models.LocationModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.activities.AddItemActivity;
import com.example.gmsproduction.dregypt.ui.fragments.AddItems.BaseAddFragment;
import com.example.gmsproduction.dregypt.utils.CustomToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;

public abstract class BaseJobFragment extends BaseAddFragment {
    public EditText EdSalary;
    ArrayList<String> CategoryNameArray, ExpLebelNameArray, EduLevelNameArray, EmpTypeNameArray;
    ArrayList<LocationModel> arrayModel, getArrayExiLevelModel, arrayEduLevelModel, EmpTypeModel;
    public Spinner spinnerCategory, spinnerExpLevel, spinnerEducLevel, spinnerEmpType;
    public int category=-1, numType=-1, expLevel=-1, eduLevel=-1, EmpType=-1;
    public String getTitle, getSalary, getDesc, getAddress, getPhone, getPhone2;
    public RadioGroup radioGroupType;
    private RequestQueue mRequestQueue;
    private String url;
    private int MethodID;
    private JSONObject object;
    public HintAdapter adapterCategory;

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_job, parent, false);

        //Now specific components here (you can initialize Buttons etc)
        initProductExtras();
        initViews(R.id.Add_Job_Title, R.id.Add_Job_Phone, R.id.Add_Job_Phone2,
                R.id.Add_Job_Desc, R.id.Add_Job_Adress, R.id.Add_Job_spinner_city,
                R.id.Add_Job_spinner_area, R.id.Add_Job_linear_area, R.id.Add_Job_Image,
                R.id.Add_Job_Add2ndPhone, R.id.Add_Job_FinishBtn);
        onCreateCustom();
        getCategory();
        getExperienceLevel();
        getEducationLevel();
        getEmpType();
        setAdType();

        return view;
    }
    public void useless(){
        adapterCategory.notifyDataSetChanged();
    }
    // setters
    public void setUrl(String url) {
        this.url = url;
    }

    public void setMethodID(int methodID) {
        MethodID = methodID;
    }

    public void setObject(JSONObject object) {
        this.object = object;
    }

    // request
    public void postJob(String url, final int MethodID, JSONObject object) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                MethodID, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("addJob", "res" + response);
                        if (MethodID== Request.Method.POST){
                            new CustomToast().Show_Toast_Success(getActivity(), view, "Job has been added and waiting for review");

                        }else if (MethodID== Request.Method.PUT){
                            new CustomToast().Show_Toast_Success(getActivity(), view, "Job has been updated and waiting for review");
                        }
                        getActivity().finish();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                AddBTN.setEnabled(true);
                Log.e("addJob", "err" + error);
                if (error == null || error.networkResponse == null) {
                    return;
                }

                String body;
                //get status code here
                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                Log.e("addJob", "statusCode  " + statusCode);

                //get response body and parse with appropriate encoding
                try {
                    body = new String(error.networkResponse.data, "UTF-8");
                    Log.e("addJob", "body " + body);
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
    }

    //submit btn method
    @Override
    public void Validation() {

        getTitle = EdTitle.getText().toString();
        getSalary = EdSalary.getText().toString();
        getDesc = EdDesc.getText().toString();
        getAddress = EdAddress.getText().toString();
        getPhone = EdPhone.getText().toString();
        getPhone2 = EdPhone2.getText().toString();


        String getID = String.valueOf(userID);

        //getTitle,getPrice,getDesc,getAddress,getPhone,city,area,category,numStatus,getEncodedImage
        if (getTitle.equals("") || getTitle.length() == 0
                || getSalary.equals("") || getSalary.length() == 0
                || getDesc.equals("") || getDesc.length() == 0
                || getAddress.equals("") || getAddress.length() == 0
                || getPhone.equals("") || getPhone.length() == 0) {
            new CustomToast().Show_Toast(getActivity(), view, "All fields are required.");
        }else if (numType == -1) {
            new CustomToast().Show_Toast(getActivity(), view, "Please Select Job Type.");
        }else if (getDesc.length() < 21) {
            new CustomToast().Show_Toast(getActivity(), view, "description must be higher than 20 letter");
        }else if (city == -1) {
            new CustomToast().Show_Toast(getActivity(), view, "Please Choose City.");
        }else if (area == -1) {
            new CustomToast().Show_Toast(getActivity(), view, "Please Choose Area.");
        } else if (category == -1) {
            new CustomToast().Show_Toast(getActivity(), view, "Please Choose Job Category.");
        }else if (expLevel == -1) {
            new CustomToast().Show_Toast(getActivity(), view, "Please Choose Experience Level.");
        }else if (eduLevel == -1) {
            new CustomToast().Show_Toast(getActivity(), view, "Please Choose Education Level.");
        }else if (EmpType == -1) {
            new CustomToast().Show_Toast(getActivity(), view, "Please Choose Employment Type.");
        }  else if (getEncodedImage.equals("") || getEncodedImage.length() == 0) {
            new CustomToast().Show_Toast(getActivity(), view, "Please add Image");
        } else {
            AddBTN.setEnabled(false);
            submit();
            postJob(url, MethodID, object);
        }
    }

    public abstract void submit();

    //spinner
    private void getCategory() {
        CategoryNameArray = new ArrayList<>();
        arrayModel = new ArrayList<>();
        adapterCategory = new HintAdapter<String>(getActivity(), "Choose Category", CategoryNameArray);
        GetJobAdCategoriesRequest getJobAdCategoriesRequest = new GetJobAdCategoriesRequest(getActivity(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String categName = object.getString(((AddItemActivity)getActivity()).lang+"_name");
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
                                adapterCategory,
                                new HintSpinner.Callback<String>() {
                                    @Override
                                    public void onItemSelected(int position, String itemAtPosition) {
                                        // Here you handle the on item selected event (this skips the hint selected event)
                                        //String item = adapterView.getItemAtPosition(i).toString();
                                        LocationModel locationModel = arrayModel.get(position);
                                        category = locationModel.getLocId();
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
                        String categName = object.getString(((AddItemActivity)getActivity()).lang+"_name");
                        int categId = object.getInt("id");
                        LocationModel model = new LocationModel(categName, categId);
                        ExpLebelNameArray.add(categName);
                        getArrayExiLevelModel.add(model);
                    }
                    if (getActivity() != null) {
                        HintSpinner<String> hintSpinner = new HintSpinner<>(
                                spinnerExpLevel,
                                // Default layout - You don't need to pass in any layout id, just your hint text and
                                // your list data
                                new HintAdapter<String>(getActivity(), "Experience Level", ExpLebelNameArray),
                                new HintSpinner.Callback<String>() {
                                    @Override
                                    public void onItemSelected(int position, String itemAtPosition) {
                                        // Here you handle the on item selected event (this skips the hint selected event)
                                        //String item = adapterView.getItemAtPosition(i).toString();
                                        LocationModel locationModel = getArrayExiLevelModel.get(position);
                                        expLevel = locationModel.getLocId();
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
                        String categName = object.getString(((AddItemActivity)getActivity()).lang+"_name");
                        int categId = object.getInt("id");
                        LocationModel model = new LocationModel(categName, categId);
                        EduLevelNameArray.add(categName);
                        arrayEduLevelModel.add(model);
                    }
                    if (getActivity() != null) {
                        HintSpinner<String> hintSpinner = new HintSpinner<>(
                                spinnerEducLevel,
                                // Default layout - You don't need to pass in any layout id, just your hint text and
                                // your list data
                                new HintAdapter<String>(getActivity(), "Education Level", EduLevelNameArray),
                                new HintSpinner.Callback<String>() {
                                    @Override
                                    public void onItemSelected(int position, String itemAtPosition) {
                                        // Here you handle the on item selected event (this skips the hint selected event)
                                        //String item = adapterView.getItemAtPosition(i).toString();
                                        LocationModel locationModel = arrayEduLevelModel.get(position);
                                        eduLevel = locationModel.getLocId();

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
                        String categName = object.getString(((AddItemActivity)getActivity()).lang+"_name");
                        int categId = object.getInt("id");
                        LocationModel model = new LocationModel(categName, categId);
                        EmpTypeNameArray.add(categName);
                        EmpTypeModel.add(model);
                    }
                    if (getActivity() != null) {
                        HintSpinner<String> hintSpinner = new HintSpinner<>(
                                spinnerEmpType,
                                // Default layout - You don't need to pass in any layout id, just your hint text and
                                // your list data
                                new HintAdapter<String>(getActivity(), "Employment Type", EmpTypeNameArray),
                                new HintSpinner.Callback<String>() {
                                    @Override
                                    public void onItemSelected(int position, String itemAtPosition) {
                                        // Here you handle the on item selected event (this skips the hint selected event)
                                        //String item = adapterView.getItemAtPosition(i).toString();
                                        LocationModel locationModel = EmpTypeModel.get(position);
                                        EmpType = locationModel.getLocId();

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

    public void initProductExtras() {
        EdSalary = view.findViewById(R.id.Add_Job_Salary);
        spinnerCategory = view.findViewById(R.id.Add_Job_spinner_category);
        spinnerExpLevel = view.findViewById(R.id.Add_Job_spinner_EXPLVL);
        spinnerEducLevel = view.findViewById(R.id.Add_Job_spinner_EduLVL);
        spinnerEmpType = view.findViewById(R.id.Add_Job_spinner_EmpType);
        radioGroupType = view.findViewById(R.id.Add_Job_radio_group_AdType);

    }

    public abstract void onCreateCustom();

}
