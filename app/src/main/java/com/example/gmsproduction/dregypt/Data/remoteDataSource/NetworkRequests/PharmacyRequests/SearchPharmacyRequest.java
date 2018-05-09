package com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.PharmacyRequests;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.VolleyLIbUtils;
import com.example.gmsproduction.dregypt.utils.Constants;

import java.util.HashMap;

/**
 * Created by mohmed mostafa on 23/04/2018.
 */

public class SearchPharmacyRequest {

    VolleyLIbUtils volleyLIbUtils;
    String url;
    int methodId;

    public SearchPharmacyRequest(Context context, Response.Listener<String> listener, Response.ErrorListener errorListener){
        setValues();
        volleyLIbUtils=new VolleyLIbUtils(context,methodId,url,listener,errorListener);
    }
    public void setBody(HashMap body){
        volleyLIbUtils.setParams(body);
    }

    private void setValues(){
        url= Constants.basicUrl+"/pharmacies/search";
        methodId= Request.Method.POST;
    }

    public void start(){
        volleyLIbUtils.start();
    }
}
