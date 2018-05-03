package com.example.gmsproduction.dregypt.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.ProductAdsRequests.GetProductAdsRequest;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.adapters.ProductAdsAdapter;
import com.example.gmsproduction.dregypt.utils.ProductsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener,BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private RecyclerView mRecyclerView;
    private ProductAdsAdapter mAdapter;
    private ArrayList<ProductsModel> modelArrayList;
    String id, title, description, price, image, status,address,created_at,phone_1,phone_2;
    SliderLayout mDemoSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_products);
        GetProductAdsRequest getProductAdsRequest = new GetProductAdsRequest(this, this, this);
        getProductAdsRequest.start();

        mDemoSlider = (SliderLayout) findViewById(R.id.ProductsSilder);

        //arrayLIST
        modelArrayList = new ArrayList<>();

        //recycler View horizon orientation
        mRecyclerView = findViewById(R.id.Recycler_Product);
        LinearLayoutManager LayoutManagaer = new GridLayoutManager(ProductsActivity.this, 3);
        mRecyclerView.setLayoutManager(LayoutManagaer);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
       Log.e("tagyy", error.toString());
    }

    @Override
    public void onResponse(String response) {

        Log.e("tagyyy",response);
        try {

            JSONObject object = new JSONObject(response);
            JSONArray dataArray = object.getJSONArray("data");
            for (int a = 0; a < dataArray.length(); a++) {
                JSONObject dataObject = dataArray.getJSONObject(a);
                id = dataObject.getString("id");
                title = dataObject.getString("title");
                price = dataObject.getString("price");
                image = dataObject.getString("img");
                status = dataObject.getString("status");
                address = dataObject.getString("address");
                created_at = dataObject.getString("created_at");
                description = dataObject.getString("description");
                JSONArray phoneArray =  dataObject.getJSONArray("phone");
                phone_1 = (String) phoneArray.get(0);
                phone_2 = (String) phoneArray.get(1);
                modelArrayList.add(new ProductsModel(id,title,description,price,status,image,address,created_at,phone_1,phone_2));

            }
            loadIMG(modelArrayList);
            mAdapter = new ProductAdsAdapter(ProductsActivity.this, modelArrayList);
            mRecyclerView.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //MAIN slider related
    public void loadIMG(ArrayList<ProductsModel> arryListy){
        for (int i = 0; i < arryListy.size(); i++) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .image(arryListy.get(i).getImage())
                    .setScaleType(BaseSliderView.ScaleType.Fit.CenterCrop)
                    .setOnSliderClickListener(ProductsActivity.this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putInt("currentImg", i);

            mDemoSlider.addSlider(textSliderView);
        }
        // you can change animasi, time page and anythink.. read more on github
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator_duck));
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(ProductsActivity.this);


    }
    //MAIN slider related
    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }
    //MAIN slider related
    @Override
    public void onSliderClick(BaseSliderView slider) {


    }
    //MAIN slider related
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    //MAIN slider related
    @Override
    public void onPageSelected(int position) {


        Log.d("Slider Demo", "Page Changed: " + position);
    }
    //MAIN slider related
    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
