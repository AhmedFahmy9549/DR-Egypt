package com.example.gmsproduction.dregypt.ui.activities;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.utils.Constants;
import com.google.firebase.messaging.RemoteMessage;
import com.pusher.pushnotifications.PushNotificationReceivedListener;
import com.pusher.pushnotifications.PushNotifications;

import java.util.Locale;


/**
 * this activity is created to be extended from the activities than need back button .
 */

public class BaseActivity extends AppCompatActivity {

    private int idLANG,userId;
    private String userName,userAvatar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        localization("en"); // method to keep the app in english position even if the mobile was arabic language preset
        super.onCreate(savedInstanceState);

        //the language sharedprefs
        SharedPreferences prefs = getSharedPreferences("LangKey", MODE_PRIVATE);

            idLANG = prefs.getInt("languageNum", 1); //0 is the default value.

        SharedPreferences prefs2 = getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
        userId = prefs.getInt("User_id", 0);
        userName = prefs2.getString("User_name", "Dr.Egypt");
        userAvatar = prefs2.getString("User_avatar", null);
        //push notification
        PushNotifications.start(getApplicationContext(), "f436d206-bd0a-4438-ad06-c3b10d485420");
        PushNotifications.subscribe(String.valueOf(userId));
        PushNotifications.subscribe(String.valueOf("all"));
        PushNotifications.setOnMessageReceivedListenerForVisibleActivity(this, new PushNotificationReceivedListener() {
            @Override
            public void onMessageReceived(RemoteMessage remoteMessage) {
                Log.e("msssg",""+remoteMessage.getNotification().getTitle());
                Log.e("msssg",""+remoteMessage.getNotification().getBody());

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public int getBackArrow(int language){
        if (language==1){
            return R.drawable.ic_back_arrow;
        }else if (language==2){
            return R.drawable.ic_back_arrow_ar;
        }
        return R.drawable.ic_back_arrow;
    }
    public void localization(int lang){
        if (lang==1){
            localization("en");
        }else if (lang==2){
            localization("ar");
        }
    }

    private void localization(String languageToLoad){
                                 // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    public int getIdLANG() {
        return idLANG;
    }
    //back button for all the activities

    public void setActivityTitle(String arabic,String English){
        if (idLANG ==1){
            getSupportActionBar().setTitle(English);
        }else if (idLANG==2){
            getSupportActionBar().setTitle(arabic);
        }
    }

}
