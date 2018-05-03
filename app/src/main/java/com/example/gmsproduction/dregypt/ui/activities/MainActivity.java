package com.example.gmsproduction.dregypt.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.gmsproduction.dregypt.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Response.Listener<String>, Response.ErrorListener, View.OnClickListener {
    Toolbar toolbar;
    FloatingActionButton fab;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    LinearLayout medicalCard, productsCard, jobsCard, cosmeticsCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();
        setSupportActionBar(toolbar);


        fab.setOnClickListener(this);
        medicalCard.setOnClickListener(this);
        productsCard.setOnClickListener(this);
        jobsCard.setOnClickListener(this);
        cosmeticsCard.setOnClickListener(this);


        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        //VolleyLIbUtils volleyLIbUtils=new VolleyLIbUtils(this, Request.Method.GET,"http://192.168.9.69/dregy/public/api/hospitals",this,this);
        //volleyLIbUtils.setListener(this);
        //volleyLIbUtils.setErrorListener(this);
        //volleyLIbUtils.start();

       /* GetHospitalsRequest getHospitalsRequest=new GetHospitalsRequest(this,this,this);
        getHospitalsRequest.start();*/

       /* AddHospitalToFavouriteRequest addHospitalToFavouriteRequest = new AddHospitalToFavouriteRequest(this, 3, 3, this, this);
        addHospitalToFavouriteRequest.setBody(hi);
        addHospitalToFavouriteRequest.start();*/

       /* GetHospitalsRequest getHospitalsRequest=new GetHospitalsRequest(this,this,this);
        getHospitalsRequest.start();
*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent;
        int id = item.getItemId();


        if (id == R.id.nav_log) {
            intent = new Intent(this, LogInActivity.class);
            startActivity(intent);

        }
/*
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onResponse(String response) {
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
        Log.d("Response", "onResponse: ," + response);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
    }


    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        medicalCard = findViewById(R.id.card_medical);
        productsCard = findViewById(R.id.card_products);
        jobsCard = findViewById(R.id.card_jobs);
        cosmeticsCard = findViewById(R.id.card_cosmetics);


    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        Log.e("Hi From", "" + id);
        Intent intent;

        switch (id) {

            case R.id.fab:
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent i = new Intent(MainActivity.this, LogInActivity.class);
                startActivity(i);
                break;

            case R.id.card_medical:
                intent = new Intent(this, MedicalGuideActivity.class);
                startActivity(intent);
                break;

            case R.id.card_products:

                intent = new Intent(this, ProductsActivity.class);
                startActivity(intent);
                break;

            case R.id.card_jobs:

                intent = new Intent(this, JobsActivity.class);
                startActivity(intent);
                break;

            case R.id.card_cosmetics:

                intent = new Intent(this, CosmeticsActivity.class);
                startActivity(intent);
                break;


        }
    }
}
