package com.example.gmsproduction.dregypt.ui.activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.ProductAdsRequests.GetProductAdsRequest;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.ProductAdsRequests.SearchProductAdRequest;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.adapters.ProductAdsAdapter;
import com.example.gmsproduction.dregypt.utils.Constants;
import com.example.gmsproduction.dregypt.utils.ProductsModel;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductsActivity extends AppCompatActivity implements  Response.Listener<String>, Response.ErrorListener,BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
//Response.Listener<String>, Response.ErrorListener,
    private RecyclerView mRecyclerView;
    private ProductAdsAdapter mAdapter;
    private ArrayList<ProductsModel> modelArrayList;
    String id, title, description, price, image, status, address, created_at, phone_1, phone_2;
    SliderLayout mDemoSlider;
    MaterialSearchView searchView;
    Map<String, String> body = new HashMap<>();
    String url = "https://dregy01.frb.io/api/product-ads/search";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_products);


        //Filters
        body.put("status", "2");

        //Request for main products
        final SearchProductAdRequest searchProductAdRequest = new SearchProductAdRequest(this,url,this,this);
        searchProductAdRequest.setBody((HashMap) body);
        searchProductAdRequest.start();

        //slider
        mDemoSlider = (SliderLayout) findViewById(R.id.ProductsSilder);

        //recycler View horizon orientation
        mRecyclerView = findViewById(R.id.Recycler_Product);
        final LinearLayoutManager LayoutManagaer = new GridLayoutManager(ProductsActivity.this, 3);
        mRecyclerView.setLayoutManager(LayoutManagaer);
        mRecyclerView.addOnScrollListener(new CustomScrollListener());

        //Custom Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarxx);
        setSupportActionBar(toolbar);

        //Search Related
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setEllipsize(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                body.put("keyword", newText);
                final SearchProductAdRequest searchProductAdRequest = new SearchProductAdRequest(ProductsActivity.this,url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Responsey(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                searchProductAdRequest.setBody((HashMap) body);
                searchProductAdRequest.start();

                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        }

        );


    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("tagyy", error.toString());
    }

    @Override
    public void onResponse(String response) {
        Responsey(response);
    }

    //MAIN slider related
    public void loadIMG(ArrayList<ProductsModel> arryListy) {
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

    //menu option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    //on back press
    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    //still don't know how it's work
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    //Fetching JSON Method
    public void Responsey(String response){
        modelArrayList = new ArrayList<>();

        Log.e("tagyyy", response);
        try {
            JSONObject object = new JSONObject(response);
            JSONArray dataArray = object.getJSONArray("data");
            for (int a = 0; a < dataArray.length(); a++) {
                JSONObject dataObject = dataArray.getJSONObject(a);
                id = dataObject.getString("id");
                title = dataObject.getString("title");
                price = dataObject.getString("price");
                image = Constants.ImgUrl + dataObject.getString("img");
                status = dataObject.getString("status");
                address = dataObject.getString("address");
                created_at = dataObject.getString("created_at");
                description = dataObject.getString("description");
                JSONArray phoneArray = dataObject.getJSONArray("phone");
                phone_1 = (String) phoneArray.get(0);
                phone_2 = (String) phoneArray.get(1);
                modelArrayList.add(new ProductsModel(id, title, description, price, status, image, address, created_at, phone_1, phone_2));
            }
            mAdapter = new ProductAdsAdapter(ProductsActivity.this, modelArrayList);
            mRecyclerView.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }
}
//custom class to detect when the recycleview reach it's end
 class CustomScrollListener extends RecyclerView.OnScrollListener {
    public CustomScrollListener() {
    }

    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        switch (newState) {
            case RecyclerView.SCROLL_STATE_IDLE:
                System.out.println("The RecyclerView is not scrolling");

                break;
            case RecyclerView.SCROLL_STATE_DRAGGING:
                System.out.println("Scrolling now");

                break;
            case RecyclerView.SCROLL_STATE_SETTLING:
                System.out.println("Scroll Settling");
                Log.e("recycler","end3");

                break;

        }

    }

    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (dx > 0) {
            System.out.println("Scrolled Right");
        } else if (dx < 0) {
            System.out.println("Scrolled Left");
        } else {
            System.out.println("No Horizontal Scrolled");
        }

        if (dy > 0) {
            System.out.println("Scrolled Downwards");
        } else if (dy < 0) {
            System.out.println("Scrolled Upwards");
        } else {
            System.out.println("No Vertical Scrolled");
        }
    }
}