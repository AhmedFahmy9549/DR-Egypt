package com.example.gmsproduction.dregypt.ui.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.FiltersRequests.GetRegionsRequest;
import com.example.gmsproduction.dregypt.Models.LocationModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.fragments.AddItems.AddJobFragment;
import com.example.gmsproduction.dregypt.ui.fragments.AddItems.AddJobTry;
import com.example.gmsproduction.dregypt.ui.fragments.AddItems.AddProductFragment;
import com.example.gmsproduction.dregypt.ui.fragments.AddItems.AddProductTry;
import com.example.gmsproduction.dregypt.ui.fragments.AddItems.UserJobsListFragment;
import com.example.gmsproduction.dregypt.ui.fragments.AddItems.UserProductsListFragment;
import com.example.gmsproduction.dregypt.ui.fragments.NoInternt_Fragment;
import com.example.gmsproduction.dregypt.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;

public class AddItemActivity extends AppCompatActivity {
    int vald;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();

        setContentView(R.layout.add_item);
        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent extra = getIntent();
        vald = extra.getIntExtra("Add", 0);

        if (vald == 1001) {
            ProductFragment();
        } else if (vald == 2002) {
            JobFragment();
        } else if (vald == 1012) {
            MyProductFragment();
        } else if (vald == 2012) {
            MyJobsFragment();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ProductFragment() {
        AddProductTry fragment = new AddProductTry();
        Bundle arguments = new Bundle();
        arguments.putInt("duck", 55);
        fragment.setArguments(arguments);
        final android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.additem_Include, fragment, Utils.addproduct);
        ft.commit();
    }

    public void JobFragment() {
        AddJobTry fragment = new AddJobTry();
        Bundle arguments = new Bundle();
        arguments.putInt("duck", 55);
        fragment.setArguments(arguments);
        final android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.additem_Include, fragment, Utils.addJobs);
        ft.commit();
    }

    public void MyProductFragment() {
        UserProductsListFragment fragment = new UserProductsListFragment();
        Bundle arguments = new Bundle();
        arguments.putInt("duck", 55);
        fragment.setArguments(arguments);
        final android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.additem_Include, fragment, Utils.UserProducts);
        ft.commit();
    }

    public void MyJobsFragment() {
        UserJobsListFragment fragment = new UserJobsListFragment();
        Bundle arguments = new Bundle();
        arguments.putInt("duck", 55);
        fragment.setArguments(arguments);
        final android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.additem_Include, fragment, Utils.UserJobs);
        ft.commit();
    }

}
