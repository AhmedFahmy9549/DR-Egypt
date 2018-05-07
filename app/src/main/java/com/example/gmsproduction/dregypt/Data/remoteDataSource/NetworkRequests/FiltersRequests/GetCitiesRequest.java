package com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.VolleyLIbUtils;
import com.example.gmsproduction.dregypt.utils.Constants;

/**
 * Created by mohmed mostafa on 23/04/2018.
 */

public class GetCitiesRequest {

    VolleyLIbUtils volleyLIbUtils;
    String url;
    int methodId;

    public GetCitiesRequest(Context context, Response.Listener<String> listener, Response.ErrorListener errorListener,int regionId){
        setValues(regionId);
        volleyLIbUtils=new VolleyLIbUtils(context,methodId,url,listener,errorListener);
    }
    private void setValues(int regionId){
        url= Constants.basicUrl+"/regions/"+regionId;
        methodId= Request.Method.GET;
    }

    public void start(){
        volleyLIbUtils.start();
    }
}
