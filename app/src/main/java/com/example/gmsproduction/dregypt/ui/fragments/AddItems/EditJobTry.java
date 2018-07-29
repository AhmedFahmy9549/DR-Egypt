package com.example.gmsproduction.dregypt.ui.fragments.AddItems;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.example.gmsproduction.dregypt.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hima on 6/26/2018.
 */

public class EditJobTry extends BaseJobFragment {
    String editTitle = "", editSalary = "", editDesc = "", editPhone1 = "", editPhone2 = "", editAddres = "", PhID1, PhID2;
    int MethodID = Request.Method.PUT;
    String url;

    private JSONObject mJson() {
        JSONObject jsonobject_one = new JSONObject();
        JSONObject jsonobject_Two = new JSONObject();
        JSONObject phone01 = new JSONObject();
        JSONObject phone02 = new JSONObject();
        JSONArray PhoneArray = new JSONArray();

        try {
            phone01.put("id", PhID1);
            phone01.put("number", getPhone);
            PhoneArray.put(phone01);

            if (getPhone2 != null && !getPhone2.isEmpty()) {
                phone02.put("id", "null");
                phone02.put("number", getPhone2);
                PhoneArray.put(phone02);
            } else {
                phone02.put("id", PhID2);
                phone02.put("number", getPhone2);
                PhoneArray.put(phone02);
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
        Bundle arguments = getArguments();
        int JobID = arguments.getInt("JobID", 0);
        url = Constants.basicUrl + "/job-ads/" + JobID;
        setMethodID(MethodID);
        setUrl(url);
        setObject(mJson());
    }

    @Override
    public void onCreateCustom() {
        //fragment name
        setmFragmentName("Edit Job","تعديل وظيفة");
        getEditData();
        setEditData();
    }

    private void getEditData() {
        Bundle arguments = getArguments();
        editTitle = arguments.getString("title");
        editSalary = arguments.getString("salary");
        editDesc = arguments.getString("Desc");
        editPhone1 = arguments.getString("phone1");
        editPhone2 = arguments.getString("phone2");

        PhID1 = arguments.getString("phoneID1");
        PhID2 = arguments.getString("phoneID2");

        editAddres = arguments.getString("Addres");
        getEncodedImage = arguments.getString("Img");
    }

    private void setEditData() {
        EdTitle.setText(editTitle);
        EdSalary.setText(editSalary);
        EdDesc.setText(editDesc);
        EdPhone.setText(editPhone1);
        EdPhone2.setText(editPhone2);
        EdAddress.setText(editAddres);
    }


}
