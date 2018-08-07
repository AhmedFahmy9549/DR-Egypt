package com.example.gmsproduction.dregypt.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.fragments.NoInternt_Fragment;
import com.example.gmsproduction.dregypt.utils.Constants;
import com.example.gmsproduction.dregypt.utils.Utils;
import com.google.firebase.messaging.RemoteMessage;
import com.pusher.pushnotifications.PushNotificationReceivedListener;
import com.pusher.pushnotifications.PushNotifications;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * this activity is created to be extended from the activities than need back button .
 */

public class BaseActivity extends AppCompatActivity {

    private int idLANG, userId;
    private String userName, userAvatar,myCityName;
    private static final int REQUEST_LOCATION = 1;
    private LocationManager locationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        localization("en"); // method to keep the app in english position even if the mobile was arabic language preset
        super.onCreate(savedInstanceState);


        //the language sharedprefs
        SharedPreferences prefs = getSharedPreferences("LangKey", MODE_PRIVATE);
        SharedPreferences.Editor editor = getSharedPreferences("notification", MODE_PRIVATE).edit();
        editor.apply();

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
                Log.e("msssg", "" + remoteMessage.getNotification().getTitle());
                Log.e("msssg", "" + remoteMessage.getNotification().getBody());
                notification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());

            }
        });
        premissionLocation();

    }

    private void premissionLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
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

    public int getBackArrow(int language) {
        if (language == 1) {
            return R.drawable.ic_back_arrow;
        } else if (language == 2) {
            return R.drawable.ic_back_arrow_ar;
        }
        return R.drawable.ic_back_arrow;
    }

    public void localization(int lang) {
        if (lang == 1) {
            localization("en");
        } else if (lang == 2) {
            localization("ar");
        }
    }

    private void localization(String languageToLoad) {
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

    public void setActivityTitle(String arabic, String English) {
        if (idLANG == 1) {
            getSupportActionBar().setTitle(English);
        } else if (idLANG == 2) {
            getSupportActionBar().setTitle(arabic);
        }
    }

    //location
    public void getCurrLocation() {


        locationManager = (LocationManager)

                getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.e("GBSLOCATION","buildAlertMessageNoGps");
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.e("GBSLOCATION","getLocation");
            myCityName = getLocation();
        }
    }

    public String getMyCityName() {
        return myCityName;
    }

    private String getLocation() {
        String cityName = null;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();

                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(latti,longi, 1);
                    cityName = addresses.get(0).getAdminArea();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else  if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();



            } else  if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();


            }else{

                Toast.makeText(this,"Unble to Trace your location",Toast.LENGTH_SHORT).show();


            }
        }
        return cityName;
    }

    protected void buildAlertMessageNoGps() {
        Log.e("GBSLOCATION","Here");

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        Log.e("GBSLOCATION","Here");
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
        Log.e("GBSLOCATION","sare");

    }

    public void noInternet(int view , int validation){
        NoInternt_Fragment fragment = new NoInternt_Fragment();
        Bundle arguments = new Bundle();
        arguments.putInt("duck", validation);
        fragment.setArguments(arguments);
        final android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(view, fragment, Utils.Error);
        ft.commitAllowingStateLoss();
    }

    private void notification(String title, String body){
        int x=1;
        SharedPreferences prefs = getSharedPreferences("notification", MODE_PRIVATE);
        x = prefs.getInt("x", 1);
         if (x==2){
            SharedPreferences.Editor editor = getSharedPreferences("notification", MODE_PRIVATE).edit();
            editor.putString("title1", title);
            editor.putString("body1", body);
            editor.putInt("x",1);
            editor.apply();
        }else if (x==1){
            SharedPreferences.Editor editor = getSharedPreferences("notification", MODE_PRIVATE).edit();
            editor.putString("title", title);
            editor.putString("body", body);
            editor.putInt("x",2);
            editor.apply();

        }





    }
}
