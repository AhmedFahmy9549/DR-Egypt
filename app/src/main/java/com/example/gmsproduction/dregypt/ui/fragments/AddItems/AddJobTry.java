package com.example.gmsproduction.dregypt.ui.fragments.AddItems;

import android.util.Log;

import com.android.volley.Request;
import com.example.gmsproduction.dregypt.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hima on 6/26/2018.
 */

public class AddJobTry extends BaseJobFragment {
    int MethodID = Request.Method.POST;
    String url = Constants.basicUrl + "/job-ads";

    private JSONObject mJson(){
        JSONObject jsonobject_one = new JSONObject();
        JSONObject jsonobject_Two = new JSONObject();
        JSONArray PhoneArray = new JSONArray();
        try {

                PhoneArray.put(getPhone);
                if (getPhone2 != null && !getPhone2.isEmpty()) {
                    PhoneArray.put(getPhone2);
                }

            jsonobject_one.put("userId", getUserID());
            jsonobject_one.put("title", getTitle);
            jsonobject_one.put("description", getDesc);
            jsonobject_one.put("salary", getSalary);
            jsonobject_one.put("regionId", city);
            jsonobject_one.put("cityId", area);
            jsonobject_one.put("address", getAddress);
            jsonobject_one.put("jobTypeId", numType);
            jsonobject_one.put("categoryId", category);
            jsonobject_one.put("experienceLevelId", expLevel);
            jsonobject_one.put("educationLevelId", eduLevel);
            jsonobject_one.put("employmentTypeId", EmpType);


            //phone array
            jsonobject_one.put("phone", PhoneArray);
            //file object
            jsonobject_Two.put("file", getEncodedImage);
            jsonobject_one.put("img", jsonobject_Two);


            Log.e("gaga", "" + jsonobject_one);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonobject_one;
    }

    @Override
    public void submit() {
        setMethodID(MethodID);
        setUrl(url);
        setObject(mJson());
    }

    @Override
    public void onCreateCustom() {
        //fragment name
        setmFragmentName("Add Job");
    }
}
