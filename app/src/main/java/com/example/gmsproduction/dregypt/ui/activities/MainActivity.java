package com.example.gmsproduction.dregypt.ui.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pusher.pushnotifications.PushNotifications;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.ProductAdsRequests.DeleteUserProductRequest;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.utils.Constants;
import com.squareup.picasso.Picasso;
import com.txusballesteros.bubbles.BubbleLayout;
import com.txusballesteros.bubbles.BubblesManager;
import com.txusballesteros.bubbles.OnInitializedCallback;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.gmsproduction.dregypt.utils.Constants.USER_DETAILS;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, Response.Listener<String>, Response.ErrorListener, View.OnClickListener {
    Toolbar toolbar;
    FloatingActionButton fab;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    CircleImageView Menu_pic;
    TextView Menu_title, medical_guideTXT, productTXT, jobsTXT, cosmeticsTXT, Menu_Email;
    int id, language;
    String userName = "Dr.Egypt", userAvatar = "", userEmail;
    View header;
    ConstraintLayout menu_Notification;
    LinearLayout medicalCard, productsCard, jobsCard, cosmeticsCard;
    private static final int REQUEST_LOCATION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        premissionLocation();
        //initializeBubblesManager();


        init();
        language = getIdLANG();
        setTitle("Dr.Egypt");
        SharedPreferences prefs = getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
        id = prefs.getInt("User_id", 0);
        userName = prefs.getString("User_name", "Dr.Egypt");
        userAvatar = prefs.getString("User_avatar", null);
        userEmail = prefs.getString("User_email", "Email");

           /* PushNotifications.start(getApplicationContext(), "f436d206-bd0a-4438-ad06-c3b10d485420");
            PushNotifications.subscribe(String.valueOf(id));
            PushNotifications.subscribe(String.valueOf("all"));*/


        setSupportActionBar(toolbar);

        fab.setOnClickListener(this);
        medicalCard.setOnClickListener(this);
        productsCard.setOnClickListener(this);
        jobsCard.setOnClickListener(this);
        cosmeticsCard.setOnClickListener(this);


        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                SetUser(MainActivity.this);
                Log.e("drawer", "bebe");
                // Do whatever you want here
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                SetUser(MainActivity.this);
                Log.e("drawer", "dodo");

                // Do whatever you want here
            }
        };


        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        lang(language);

        menu_Notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,NotificationActivity.class);
                startActivity(intent);
            }
        });


        SetUser(MainActivity.this);


    }


    private void premissionLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
    }


    private void LogOut() {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_log).setVisible(false);
        nav_Menu.findItem(R.id.nav_logOut).setVisible(true);
        nav_Menu.findItem(R.id.nav_myJobs).setVisible(true);
        nav_Menu.findItem(R.id.nav_myProduct).setVisible(true);
        nav_Menu.findItem(R.id.nav_Favorite).setVisible(true);

    }

    private void LogIn() {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_log).setVisible(true);
        nav_Menu.findItem(R.id.nav_logOut).setVisible(false);
        nav_Menu.findItem(R.id.nav_myJobs).setVisible(false);
        nav_Menu.findItem(R.id.nav_myProduct).setVisible(false);
        nav_Menu.findItem(R.id.nav_Favorite).setVisible(false);
        /*Menu_title.setVisibility(View.VISIBLE);
        Menu_Email.setVisibility(View.GONE);
        Menu_pic.setVisibility(View.VISIBLE);*/
    }


    public void SetUser(Context context) {

        Glide.with(MainActivity.this)
                .load(userAvatar)
                .placeholder(R.drawable.user2)
                .into(Menu_pic);
        Menu_title.setText(userName);
        Menu_Email.setText(userEmail);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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
        if (id == R.id.action_ar) {

            SharedPreferences.Editor editor = getSharedPreferences("LangKey", MODE_PRIVATE).edit();
            editor.putInt("languageNum", 2);
            editor.apply();
            lang(2);

        } else if (id == R.id.action_en) {

            SharedPreferences.Editor editor = getSharedPreferences("LangKey", MODE_PRIVATE).edit();
            editor.putInt("languageNum", 1);
            editor.apply();
            lang(1);


        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent;
        int idz = item.getItemId();


        switch (idz) {
            case R.id.nav_log:
                intent = new Intent(this, LogInActivity.class);
                startActivity(intent);
                break;
            case R.id.Nav_Medical:
                intent = new Intent(this, MedicalGuideActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_pro:
                intent = new Intent(this, ProductsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_job:
                intent = new Intent(this, JobsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_cos:
                intent = new Intent(this, CosmeticsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_Favorite:
                intent = new Intent(this, FavoriteActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_AddProduct:
                intent = new Intent(this, AddItemActivity.class);
                intent.putExtra("Add", 1001);
                startActivity(intent);
                break;
            case R.id.nav_AddJob:
                intent = new Intent(this, AddItemActivity.class);
                intent.putExtra("Add", 2002);
                startActivity(intent);
                break;
            case R.id.nav_myProduct:
                intent = new Intent(this, AddItemActivity.class);
                intent.putExtra("Add", 1012);
                startActivity(intent);
                break;
            case R.id.nav_myJobs:
                intent = new Intent(this, AddItemActivity.class);
                intent.putExtra("Add", 2012);
                startActivity(intent);
                break;
            case R.id.nav_logOut:
                new AlertDialog.Builder(this)
                        .setTitle("Log Out!")
                        .setMessage("Do you really want to Logout?")
                        .setIcon(android.R.drawable.ic_delete)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent intent;
                                SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE).edit();
                                editor.clear().apply();
                                intent = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();


                break;
            case R.id.nav_cont:
                intent = new Intent(this, ContactUsActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_abo:
                intent = new Intent(this, AboutUsActivity.class);
                startActivity(intent);
                break;


        }

/*
        /*if (id == R.id.nav_camera) {
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
        header = navigationView.getHeaderView(0);

        medicalCard = findViewById(R.id.card_medical);
        productsCard = findViewById(R.id.card_products);
        jobsCard = findViewById(R.id.card_jobs);
        cosmeticsCard = findViewById(R.id.card_cosmetics);

        Menu_pic = header.findViewById(R.id.Menu_imageView);
        Menu_title = header.findViewById(R.id.Menu_Title);
        Menu_Email = header.findViewById(R.id.Menu_userEmail);
        menu_Notification = header.findViewById(R.id.Menu_Notification);


        medical_guideTXT = findViewById(R.id.main_medicalGuide);
        productTXT = findViewById(R.id.main_product);
        jobsTXT = findViewById(R.id.main_jobs);
        cosmeticsTXT = findViewById(R.id.main_cosmetics);

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

    private void lang(int lang) {



        if (lang == 1) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer);
            //navigationView.setTextDirection(View.TEXT_DIRECTION_FIRST_STRONG_LTR);
            navigationView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            Log.e("navigationView", "english");


            medical_guideTXT.setText("Medical Guide");
            productTXT.setText("Products");
            jobsTXT.setText("Jobs");
            cosmeticsTXT.setText("Cosmetics");

            setTitle("Dr.Egypt");

        } else if (lang == 2) {

            Log.e("navigationView", "arabic");
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer_ar);
            //navigationView.setTextDirection(View.TEXT_DIRECTION_ANY_RTL);
            navigationView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);



            medical_guideTXT.setText("الدليل الطبي");
            productTXT.setText("المنتجات");
            jobsTXT.setText("الوظائف");
            cosmeticsTXT.setText("عيادات التجميل");
            setTitle("دكتور ايجيبت");

        }

        if (id == 0) {
            LogIn();
        } else {
            LogOut();
        }

    }

}
