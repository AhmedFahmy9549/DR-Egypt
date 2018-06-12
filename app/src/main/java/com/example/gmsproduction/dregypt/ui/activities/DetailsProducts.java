package com.example.gmsproduction.dregypt.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.ProductAdsRequests.AddProductsAdsToFavouriteRequest;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.ProductAdsRequests.DeleteProductAdFromFavouriteRequest;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.ProductAdsRequests.ViewsIncrementForProductAdRequest;
import com.example.gmsproduction.dregypt.Models.ProductsModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.utils.Constants;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailsProducts extends AppCompatActivity {
    ImageView imageView;
    TextView TXTdescription, TXTprice, TXTaddress, TXTcreated_at, TXTphone_1, texttestr;
    int position, userid, productID;
    String idz, titlez, description, price, status, image, address, created_at, phone_1, phone_2, cz;
    ToggleButton toggleButton;
    Button btnNext, btnPrevious;
    LinearLayout Dial;
    ExpandableRelativeLayout expandableLayout1;
    ArrayList<ProductsModel> models;
    Map<String, String> body = new HashMap<>();
    Map<String, String> Posty = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_products);
        SharedPreferences prefs = getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
        userid = prefs.getInt("User_id", 0);
        cz = String.valueOf(userid);



        //intent and bundle
        extras();
        //find view by id
        Initialize();
        //put data in Strings
        getExtra();
        //put strings into Views
        Deploy();
        //set tool bar title
        setTitle(titlez);
        Views();

    }
    /*public void expandableButton1(View view) {
        expandableLayout1 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout);
        expandableLayout1.toggle(); // toggle expand and collapse
    }*/

    //back btn
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Views(){
        if (userid==0){

        }else {
            Posty.put("userId", cz);
        }
        ViewsIncrementForProductAdRequest increment = new ViewsIncrementForProductAdRequest(this, productID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        increment.setBody((HashMap)Posty);
        increment.start();
    }

    private void Initialize() {
        imageView = findViewById(R.id.IMG_ProductDetails);
        TXTdescription = findViewById(R.id.Desc_ProductDetails);
        TXTprice = findViewById(R.id.Price_ProductDetails);
        TXTaddress = findViewById(R.id.Address_ProductDetails);
        TXTcreated_at = findViewById(R.id.Date_ProductDetails);
        TXTphone_1 = findViewById(R.id.Phone_ProductDetails);
        toggleButton = (ToggleButton) findViewById(R.id.Details_ToggleButton);
        Dial = findViewById(R.id.DetailsProduct_Dial);
        btnNext = findViewById(R.id.DetailsProduct_Next);
        btnPrevious = findViewById(R.id.DetailsProduct_previous);
        //to display the back btn
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void getExtra() {
        idz = models.get(position).getIdz();
        productID = Integer.valueOf(idz);
        titlez = models.get(position).getTitlez();
        description = models.get(position).getDescription();
        price = models.get(position).getPrice();
        status = models.get(position).getStatus();
        image = models.get(position).getImage();
        address = models.get(position).getAddress();
        created_at = models.get(position).getCreated_at();
        phone_1 = models.get(position).getPhone_1();
        phone_2 = models.get(position).getPhone_2();
    }

    private void extras() {
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        Bundle getextra = getIntent().getExtras();
        models = (ArrayList<ProductsModel>) getextra.getSerializable("arrz");
    }

    private void Deploy() {
        TXTdescription.setText(description);
        TXTprice.setText(price + "$");
        TXTaddress.setText(address);
        TXTcreated_at.setText(created_at);
        TXTphone_1.setText(phone_1);
        Picasso.with(this).load(image).fit().centerCrop().into(imageView);
        toggleButton.setChecked(false);
        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_black_24dp));
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_black_24dp_fill));
                    body.put("", "");
                    AddProductsAdsToFavouriteRequest addFav = new AddProductsAdsToFavouriteRequest(DetailsProducts.this, userid, productID, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("fav", response);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    addFav.setBody((HashMap) body);
                    addFav.start();

                } else {
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_black_24dp));
                    body.put("", "");
                    DeleteProductAdFromFavouriteRequest addFav = new DeleteProductAdFromFavouriteRequest(DetailsProducts.this, userid, productID, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("favDe", response);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    addFav.setBody((HashMap) body);
                    addFav.start();

                }
            }
        });

        Dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneCall(phone_1);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (position < models.size() - 1) {
                    position++;
                    getExtra();
                    Deploy();
                    setTitle(titlez);
                }
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position > 0) {
                    position--;
                    getExtra();
                    Deploy();
                    setTitle(titlez);
                }
            }
        });
    }

    public void phoneCall(String data) {
        Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", data, null));
        startActivity(intent1);
    }


    /*linearLayout = findViewById(R.id.Product_test);
        texttestr = findViewById(R.id.spinner1);
        texttestr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation in = AnimationUtils.loadAnimation(DetailsProducts.this, android.R.anim.fade_in);
                linearLayout.startAnimation(in);

                linearLayout.setVisibility(View.VISIBLE);
            }
        });*/
}
