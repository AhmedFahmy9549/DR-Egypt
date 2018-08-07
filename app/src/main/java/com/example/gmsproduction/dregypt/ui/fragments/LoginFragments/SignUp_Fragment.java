package com.example.gmsproduction.dregypt.ui.fragments.LoginFragments;

import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.activities.LogInActivity;
import com.example.gmsproduction.dregypt.utils.Constants;
import com.example.gmsproduction.dregypt.utils.CustomToast;
import com.example.gmsproduction.dregypt.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp_Fragment extends Fragment implements OnClickListener {
    private View view;
    private EditText fullName, emailId, mobileNumber, location,
            password, confirmPassword;
    private TextView login;
    private Button signUpButton;
    private CheckBox terms_conditions;
    private RequestQueue mRequestQueue;

    public SignUp_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signup_layout, container, false);
        getActivity().setTitle("Sign Up");
        ((LogInActivity) getActivity()).setActivityTitle("إنشاء حساب", "Sign Up");

        initViews();
        setListeners();
        return view;
    }

    // Initialize all views
    private void initViews() {
        fullName = (EditText) view.findViewById(R.id.fullName);
        emailId = (EditText) view.findViewById(R.id.userEmailId);
        /*mobileNumber = (EditText) view.findViewById(R.id.mobileNumber);*/
        /*location = (EditText) view.findViewById(R.id.location);*/
        password = (EditText) view.findViewById(R.id.password);
        confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
        signUpButton = (Button) view.findViewById(R.id.signUpBtn);
        login = (TextView) view.findViewById(R.id.already_user);
        terms_conditions = (CheckBox) view.findViewById(R.id.terms_conditions);

    }

    // Set Listeners
    private void setListeners() {
        signUpButton.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpBtn:

                // Call checkValidation method
                checkValidation();
                break;

            case R.id.already_user:

                // Replace login fragment
                new LogInActivity().replaceLoginFragment();
                break;
        }

    }

    // Check Validation Method
    private void checkValidation() {

        // Get all edittext texts
        String getFullName = fullName.getText().toString();
        String getEmailId = emailId.getText().toString();
        /*String getMobileNumber = mobileNumber.getText().toString();
        String getLocation = location.getText().toString();*/
        String getPassword = password.getText().toString();
        String getConfirmPassword = confirmPassword.getText().toString();

        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        // Check if all strings are null or not
        if (getFullName.equals("") || getFullName.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
				/*|| getMobileNumber.equals("") || getMobileNumber.length() == 0
				|| getLocation.equals("") || getLocation.length() == 0*/
                || getPassword.equals("") || getPassword.length() == 0
                || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0)

            new CustomToast().Show_Toast(getActivity(), view,
                    "All fields are required.");

            // Check if email id valid or not
        else if (!m.find())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Your Email Id is Invalid.");

            // Check if both password should be equal
        else if (!getConfirmPassword.equals(getPassword))
            new CustomToast().Show_Toast(getActivity(), view,
                    "Both password doesn't match.");

            // Make sure user should check Terms and Conditions checkbox
        else if (!terms_conditions.isChecked())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Please select Terms and Conditions.");

            // Else do signup or do your stuff
        else {
            postyy(getFullName, getEmailId, getPassword, getConfirmPassword);
            signUpButton.setEnabled(false);
        }


    }


    public void postyy(String name, String email, String password1, String password2) {

        JSONObject jsonobject_one = new JSONObject();
        try {
            jsonobject_one.put("name", name);
            jsonobject_one.put("email", email);
            jsonobject_one.put("password", password1);
            jsonobject_one.put("password_confirmation", password2);

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                    Request.Method.POST, Constants.basicUrl + "/register", jsonobject_one,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("SignUp", response.toString());
                            signUpButton.setEnabled(true);
                            new CustomToast().Show_Toast_Success(getActivity(),view,"Sign up successfully");

                            ((LogInActivity)getActivity()).replaceLoginFragment();

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    //get status code here


                    //get response body and parse with appropriate encoding
                    try {
                        String body;
                        final String statusCode = String.valueOf(error.networkResponse.statusCode);
                        Log.e("Signup", "statusCode  " + statusCode);
                        if (statusCode.equals("422")){
                            new CustomToast().Show_Toast(getActivity(),view,"The email has already been taken.");
                        }
                        body = new String(error.networkResponse.data, "UTF-8");
                        Log.e("Signup", "body " + body);
                    } catch (UnsupportedEncodingException e) {
                        // exception
                    }
                    signUpButton.setEnabled(true);

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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
