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

public class ViewsIncrementForPharmacyRequest {

    VolleyLIbUtils volleyLIbUtils;
    String url;
    int methodId;

    public ViewsIncrementForPharmacyRequest(Context context,int id, Response.Listener<String> listener, Response.ErrorListener errorListener){
        setValues(id);
        volleyLIbUtils=new VolleyLIbUtils(context,methodId,url,listener,errorListener);
    }
    private void setValues(int id){
        url= Constants.basicUrl+"/pharmacies/"+id+"/view";
        methodId= Request.Method.POST;
    }

    public void setBody(HashMap body){
        volleyLIbUtils.setParams(body);
    }

    public void start(){
        volleyLIbUtils.start();
    }
}
