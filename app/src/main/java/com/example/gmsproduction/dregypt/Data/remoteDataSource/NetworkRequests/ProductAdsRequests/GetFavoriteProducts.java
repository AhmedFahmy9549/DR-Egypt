package com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.ProductAdsRequests;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.VolleyLIbUtils;
import com.example.gmsproduction.dregypt.utils.Constants;

import java.util.HashMap;

/**
 * Created by Hima on 5/30/2018.
 */

public class GetFavoriteProducts {

    VolleyLIbUtils volleyLIbUtils;
    String url;
    int methodId;


    public GetFavoriteProducts(Context context,int userID, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        setValues(userID);
        volleyLIbUtils = new VolleyLIbUtils(context, methodId, url, listener, errorListener);
    }

    private void setValues(int userID) {
        url = Constants.basicUrl+"/users/"+userID+"/favorites";

        methodId = Request.Method.POST;
    }

    public void setBody(HashMap body) {
        volleyLIbUtils.setParams(body);
    }


    public void start() {
        volleyLIbUtils.start();
    }
}
