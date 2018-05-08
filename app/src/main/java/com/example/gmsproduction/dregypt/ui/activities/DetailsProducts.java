package com.example.gmsproduction.dregypt.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.utils.ProductsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class DetailsProducts extends AppCompatActivity {
    ImageView imageView;
    TextView TXTdescription, TXTprice, TXTaddress, TXTcreated_at, TXTphone_1;
    String idz, titlez, description, price, status, image, address, created_at, phone_1, phone_2;
    ProductsActivity productsActivity;
    private ArrayList<ProductsModel> modelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_products);
        Initialize();
        getExtra();
        Deploy();
        setTitle(titlez);

       /* toggleButton.setChecked(false);
        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_black_24dp));
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_favorite_black_24dp_fill));
                else
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_black_24dp));
            }
        });*/

    }

    private void Initialize() {
        imageView = findViewById(R.id.IMG_ProductDetails);
        TXTdescription = findViewById(R.id.Desc_ProductDetails);
        TXTprice = findViewById(R.id.Price_ProductDetails);
        TXTaddress = findViewById(R.id.Address_ProductDetails);
        TXTcreated_at = findViewById(R.id.Date_ProductDetails);
        TXTphone_1 = findViewById(R.id.Phone_ProductDetails);
    }

    private void getExtra() {
        Intent intent = getIntent();
        idz = intent.getStringExtra("pro_id");
        titlez = intent.getStringExtra("pro_title");
        description = intent.getStringExtra("pro_description");
        price = intent.getStringExtra("pro_price");
        status = intent.getStringExtra("pro_status");
        image = intent.getStringExtra("pro_image");
        address = intent.getStringExtra("pro_address");
        created_at = intent.getStringExtra("pro_created_at");
        phone_1 = intent.getStringExtra("pro_phone_1");
        phone_2 = intent.getStringExtra("pro_phone_2");

    }

    private void Deploy() {
        TXTdescription.setText(description);
        TXTprice.setText(price + "$");
        TXTaddress.setText(address);
        TXTcreated_at.setText(created_at);
        TXTphone_1.setText(phone_1);
        Picasso.with(this).load(image).fit().centerInside().into(imageView);
    }
}
