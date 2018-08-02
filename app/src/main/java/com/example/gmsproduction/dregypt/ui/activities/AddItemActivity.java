package com.example.gmsproduction.dregypt.ui.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.fragments.AddItems.Jobs.AddJobTry;
import com.example.gmsproduction.dregypt.ui.fragments.AddItems.Products.AddProductTry;
import com.example.gmsproduction.dregypt.ui.fragments.AddItems.Jobs.UserJobsListFragment;
import com.example.gmsproduction.dregypt.ui.fragments.AddItems.Products.UserProductsListFragment;
import com.example.gmsproduction.dregypt.utils.Utils;

public class AddItemActivity extends BaseActivity {
    int vald,language;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        language = getIdLANG();
        localization(language);
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
