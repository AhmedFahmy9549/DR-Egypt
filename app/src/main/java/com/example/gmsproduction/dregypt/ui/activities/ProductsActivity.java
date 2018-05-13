package com.example.gmsproduction.dregypt.ui.activities;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
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
import com.example.gmsproduction.dregypt.ui.fragments.LoginFragments.BasePage;
import com.example.gmsproduction.dregypt.ui.fragments.ProductBannerFragment;
import com.example.gmsproduction.dregypt.utils.Constants;
import com.example.gmsproduction.dregypt.utils.ProductsModel;
import com.example.gmsproduction.dregypt.utils.Utils;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductsActivity extends AppCompatActivity {
//Response.Listener<String>, Response.ErrorListener,
    private RecyclerView mRecyclerView;
    private ProductAdsAdapter mAdapter;
    private ArrayList<ProductsModel> modelArrayList;
    String id, title, description, price, image, status, address, created_at, phone_1, phone_2,category;
    SliderLayout mDemoSlider;
    MaterialSearchView searchView;
    Map<String, String> body = new HashMap<>();
    String url = Constants.basicUrl+"/product-ads/search";
    private  FragmentManager fragmentManager;
    DetailsProducts detailsProducts;
    String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_products);
        fragmentManager = getSupportFragmentManager();

        //add the banner
        fragmentManager
                .beginTransaction()
                .replace(R.id.RelativeBunner, new ProductBannerFragment(),
                        Utils.banner).commit();

        //Filters
        //body.put("status", "2");

        //Request for main products
        getProducts("");


        //recycler View horizon orientation
        mRecyclerView = findViewById(R.id.Recycler_Product);
        final LinearLayoutManager LayoutManagaer = new GridLayoutManager(ProductsActivity.this, 2);

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view); // item position
                int spanCount = 2;
                int spacing = 2;//spacing between views in grid

                if (position >= 0) {
                    int column = position % spanCount; // item column

                    outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                    outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                    if (position < spanCount) { // top edge
                            outRect.top = spacing;
                    }
                    outRect.bottom = spacing; // item bottom
                } else {
                    outRect.left = 0;
                    outRect.right = 0;
                    outRect.top = 0;
                    outRect.bottom = 0;
                }
            }
        });
        mRecyclerView.setLayoutManager(LayoutManagaer);
        mRecyclerView.addOnScrollListener(new CustomScrollListener());

        //Custom Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarxx);
        setSupportActionBar(toolbar);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (test != null && !test.isEmpty()){
                    test = "";
                    getProducts(test);
                }else {
                    finish();
                }            }
        });

        //Search Related
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setEllipsize(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                test = query;
                getProducts(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getProducts(newText);

                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                getProducts(test);
            }
        });

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
                JSONObject categoryObject = dataObject.getJSONObject("category");
                category = categoryObject.getString("en_name");

                modelArrayList.add(new ProductsModel(id, title,category, description, price, status, image, address, created_at, phone_1, phone_2));
            }
            mAdapter = new ProductAdsAdapter(ProductsActivity.this, modelArrayList);
            mRecyclerView.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }
    public void getProducts(String keyword){
        body.put("keyword", keyword);
        final SearchProductAdRequest searchProductAdRequest = new SearchProductAdRequest(ProductsActivity.this, new Response.Listener<String>() {
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