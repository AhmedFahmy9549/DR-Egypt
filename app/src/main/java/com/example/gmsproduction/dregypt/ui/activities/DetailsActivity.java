package com.example.gmsproduction.dregypt.ui.activities;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.gmsproduction.dregypt.Models.HospitalModel;
import com.example.gmsproduction.dregypt.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class DetailsActivity extends AppCompatActivity {
    private String name, address, note, website, email, img, phone1, phone2, createdAt;
    TextView TXTname, TXTaddress, TXTwebsite, TXTemail, TXTphone1, TXTphone2;
    ImageView TXTimg;
    ToggleButton toggleButton;

    private int id, count, favorites;
    private float rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_hospitals);
        Extras();
        Initializing();
        Deploy();
        //display backbutton of action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Extras() {
        Intent extra = getIntent();
        name = extra.getStringExtra("name");
        address = extra.getStringExtra("address");
        note = extra.getStringExtra("note");
        website = extra.getStringExtra("website");
        email = extra.getStringExtra("email");
        img = extra.getStringExtra("image");
        phone1 = extra.getStringExtra("phone1");
        phone2 = extra.getStringExtra("phone2");
    }

    public void Deploy() {

        TXTname.append(" : "+name);
        TXTaddress.append(" : "+address);
        TXTwebsite.append(" : "+website);
        TXTemail.append(" : "+email);
        TXTphone1.append(" : "+phone1);

        Picasso.with(this).load(img).fit().centerCrop().into(TXTimg);
        setTitle(name);

        toggleButton.setChecked(false);
        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_black_24dp));
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_favorite_black_24dp_fill));
                else
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_black_24dp));
            }
        });
    }

    public void Initializing() {

        TXTname = findViewById(R.id.DetailsHospital_name);
        TXTaddress = findViewById(R.id.DetailsHospital_address);
        TXTwebsite = findViewById(R.id.DetailsHospital_webSite);
        TXTemail = findViewById(R.id.DetailsHospital_Email);
        TXTphone1 = findViewById(R.id.DetailsHospital_Phone);
        TXTimg = findViewById(R.id.DetailsHospital_Image);
        toggleButton = findViewById(R.id.DetailsHospital_toggle);


    }
}
