package com.example.gmsproduction.dregypt.ui.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.utils.Constants;
import com.squareup.picasso.Picasso;

public class UserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;

    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    ImageView Menu_pic;
    TextView Menu_title;
    View header;
    int id;
    String userName = "Dr.Egypt", userAvatar = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        setSupportActionBar(toolbar);


        SharedPreferences prefs = getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
        id = prefs.getInt("User_id", 0);
        userName = prefs.getString("User_name", "Dr.Egypt");
        userAvatar = prefs.getString("User_avatar", null);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                Log.e("drawer","bebe");
                // Do whatever you want here
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Log.e("drawer","dodo");

                // Do whatever you want here
            }
        };

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

    }
    public void SetUser(Context context) {
        Picasso.with(context).load(userAvatar).fit().placeholder(R.drawable.icon).into(Menu_pic);
        Menu_title.setText(userName);
    }

    private void Ini(){
        navigationView =findViewById(R.id.nav_view_User);
        header = navigationView.getHeaderView(0);
        Menu_pic = header.findViewById(R.id.Menu_imageView);
        Menu_title = header.findViewById(R.id.Menu_Title);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
