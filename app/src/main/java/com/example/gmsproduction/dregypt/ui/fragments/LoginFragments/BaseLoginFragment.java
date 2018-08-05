package com.example.gmsproduction.dregypt.ui.fragments.LoginFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.activities.LogInActivity;
import com.example.gmsproduction.dregypt.ui.activities.MainActivity;
import com.example.gmsproduction.dregypt.utils.Constants;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Hima on 8/5/2018.
 */

public class BaseLoginFragment extends Fragment {
    public View view;
    private Button facebookbtnLog;
    private  CallbackManager callbackManager;
    private List<String> permissionNeeds = Arrays.asList("user_photos", "email",
            "user_birthday", "public_profile");
    private String id, name, email, profile_pic;
    private RequestQueue mRequestQueue;

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getActivity());

        return view;
    }

    public void setFacebookbtn(int id){
        //facebook login button
        facebookbtnLog = view.findViewById(id);
        facebookbtnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    facebookMethod();
            }
        });

    }

    private void facebookMethod() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(
                this,
                permissionNeeds
        );
        LoginManager.getInstance().registerCallback(
                callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // Handle success
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object,
                                                            GraphResponse response) {
                                        Log.e("graph", response.toString());

                                        try {
                                            id = object.getString("id");
                                            name = object.getString("name");
                                            email = object.getString("email");
                                            profile_pic = "http://graph.facebook.com/" + id + "/picture?type=large";
                                            postyy(id, name, email, profile_pic);
                                        } catch (JSONException e) {

                                            e.printStackTrace();
                                        }
                                    }
                                });


                        Bundle parameters = new Bundle();
                        parameters.putString("fields",
                                "id,name,email,gender,birthday");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Log.e("LoginActivity",
                                "onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.e("LoginActivity",
                                exception.toString());
                    }
                }
        );

    }

    //this method will post the data of user to the server
    //facebookLogin
    private void postyy(String id, String name, String email, String avatar) {

        JSONObject jsonobject_one = new JSONObject();
        try {
            jsonobject_one.put("name", name);
            jsonobject_one.put("email", email);
            jsonobject_one.put("id", id);
            jsonobject_one.put("avatar", avatar);

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                    Request.Method.POST, Constants.basicUrl+"/auth/facebook/callback", jsonobject_one,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("lol2", response.toString());
                            int id;
                            String name, email, avatar;
                            try {
                                id = response.getInt("id");
                                name = response.getString("name");
                                email = response.getString("email");
                                try {
                                    avatar = response.getString("avatar");
                                } catch (Exception e) {
                                    avatar = "";
                                }

                                ((LogInActivity) getActivity()).SharedPref(id, name, email, avatar);
                                Intent intent = new Intent(getActivity(),MainActivity.class);
                                startActivity(intent);

                                //SharedPref(id, name, email, avatar);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("lol2", "" + error.getMessage());

                    //VolleyLog.d("error", "Error: " + error.getMessage());

                }
            });
            mRequestQueue = Volley.newRequestQueue(getActivity());
            mRequestQueue.add(jsonObjReq);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
