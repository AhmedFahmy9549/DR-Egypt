package com.example.gmsproduction.dregypt.ui.fragments.LoginFragments;

import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.Login.ForgetPassword;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.activities.LogInActivity;
import com.example.gmsproduction.dregypt.utils.CustomToast;
import com.example.gmsproduction.dregypt.utils.Utils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPassword_Fragment extends Fragment implements
        OnClickListener {
    private View view;
    private Map<String, String> body = new HashMap<>();
    private EditText emailId;
    private TextView submit, back;

    public ForgotPassword_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forgotpassword_layout, container,
                false);
        ((LogInActivity) getActivity()).setActivityTitle("نسيت كلمة السر", "Forgot Password");

        initViews();
        setListeners();
        return view;
    }

    // Initialize the views
    private void initViews() {
        emailId = (EditText) view.findViewById(R.id.registered_emailid);
        submit = (TextView) view.findViewById(R.id.forgot_button);
        back = (TextView) view.findViewById(R.id.backToLoginBtn);
    }

    // Set Listeners over buttons
    private void setListeners() {
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backToLoginBtn:

                // Replace Login Fragment on Back Presses
                new LogInActivity().replaceLoginFragment();
                break;

            case R.id.forgot_button:

                // Call Submit button task
                submitButtonTask();
                break;

        }

    }

    private void submitButtonTask() {
        String getEmailId = emailId.getText().toString();

        // Pattern for email id validation
        Pattern p = Pattern.compile(Utils.regEx);

        // Match the pattern
        Matcher m = p.matcher(getEmailId);

        // First check if email id is not null else show error toast
        if (getEmailId.equals("") || getEmailId.length() == 0)

            new CustomToast().Show_Toast(getActivity(), view,
                    "Please enter your Email Id.");

            // Check if email id is valid or not
        else if (!m.find())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Your Email Id is Invalid.");

            // Else submit email id and fetch passwod or do your stuff
        else
        postMethod(getEmailId);
        submit.setEnabled(false);
    }

    private void postMethod(String msg){
        body.put("email",msg);
        ForgetPassword forgetPassword = new ForgetPassword(getActivity(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("forgetEmail","msg: "+response);
                new CustomToast().Show_Toast_Success(getActivity(),view,"Password reset has been sent to your email.");
                emailId.setText("");
                submit.setEnabled(true);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("forgetEmail","msg: "+error);

                if (error == null || error.networkResponse == null) {
                    return;
                }

                try {
                    String body;
                    //get status code here
                    final String statusCode = String.valueOf(error.networkResponse.statusCode);
                    Log.e("forgetEmail", "statusCode  " + statusCode);

                    if (statusCode.equals("404")){
                        new CustomToast().Show_Toast_Fail(getActivity(),view,"There is no account associated with this email");
                    }

                    body = new String(error.networkResponse.data, "UTF-8");
                    Log.e("forgetEmail", "body " + body);
                } catch (UnsupportedEncodingException e) {
                    // exception
                }
                submit.setEnabled(true);

            }
        });
        forgetPassword.setBody((HashMap) body);
        forgetPassword.start();
    }
}