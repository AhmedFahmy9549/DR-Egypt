package com.example.gmsproduction.dregypt.ui.fragments.FragmentsFilters;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.gmsproduction.dregypt.R;

public class CityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container13,new CityFragment(),"CityFragment")
                .commit();




        try{
            SharedPreferences prefs = getSharedPreferences("City", MODE_PRIVATE);
            String name = prefs.getString("specialName", "No name defined");//"No name defined" is the default value.
            Log.e("Sammy",name);
        }
        catch (Exception e){}


    }
}
