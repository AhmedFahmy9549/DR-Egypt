package com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.PharmacyRequests;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.VolleyLIbUtils;
import com.example.gmsproduction.dregypt.utils.Constants;

/**
 * Created by Hima on 5/31/2018.
 */

public class DisplayPhaFavoriteByID {

    VolleyLIbUtils volleyLIbUtils;
    String url;
    int methodId;


    public DisplayPhaFavoriteByID(Context context, int userID, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        setValues(userID);
        volleyLIbUtils = new VolleyLIbUtils(context, methodId, url, listener, errorListener);
    }

    private void setValues(int userID) {
        url = Constants.basicUrl+"/account/"+userID+"/favorite-pharmacies";

        methodId = Request.Method.GET;
    }

    public void start() {
        volleyLIbUtils.start();
    }
}
