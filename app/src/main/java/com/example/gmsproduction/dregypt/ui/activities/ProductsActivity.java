package com.example.gmsproduction.dregypt.ui.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
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
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.daimajia.slider.library.SliderLayout;
import com.example.gmsproduction.dregypt.Data.localDataSource.EndlessRecyclerOnScrollListener;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.ProductAdsRequests.GetFavoriteProducts;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.ProductAdsRequests.SearchProductAdRequest;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.adapters.ProductAdsAdapter;
import com.example.gmsproduction.dregypt.ui.fragments.NoInternt_Fragment;
import com.example.gmsproduction.dregypt.ui.fragments.ProductBannerFragment;
import com.example.gmsproduction.dregypt.utils.Constants;
import com.example.gmsproduction.dregypt.Models.ProductsModel;
import com.example.gmsproduction.dregypt.utils.Utils;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductsActivity extends BaseActivity {

    String MY_PREFS_NAME = "FiltersPro";

    //Response.Listener<String>, Response.ErrorListener,
    private RecyclerView mRecyclerView;
    private ProductAdsAdapter mAdapter;
    private ArrayList<ProductsModel> modelArrayList = new ArrayList<>();
    private ArrayList<Integer> favArray;

    String id, title, description, price, image, status, address, created_at,created_by, phone_1, phone_2, category;
    int userid,language;
    MaterialSearchView searchView;
    Map<String, String> body = new HashMap<>();
    String url = Constants.basicUrl + "/product-ads/search";
    private FragmentManager fragmentManager;
    String test;
    ProgressBar progressBar;
    LinearLayoutManager LayoutManagaer;
    int page = 1;
    int last_page;
    private String lang;
    TextView txtFilter , txtSort;
    private String sortKey,sortValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_products);
        favArray = new ArrayList<>();

        SharedPreferences prefs = getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
        userid = prefs.getInt("User_id", 0);
        language = getIdLANG();
        localization(language);
        lang=checkLanguage(language);

        txtFilter = findViewById(R.id.filtering);
        txtSort = findViewById(R.id.sorting);
        txtFilter.setText(R.string.nameActivity_Filters);
        txtSort.setText(R.string.sorting);
        txtFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductsActivity.this, FiltersActivity.class);
                intent.putExtra("idFilter", 4);
                startActivity(intent);
            }
        });


        txtSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sorting();
            }
        });


        progressBar = (ProgressBar) findViewById(R.id.pbHeaderProgress);
        progressBar.setVisibility(View.VISIBLE);
        fragmentManager = getSupportFragmentManager();
        //add the banner
        fragmentManager
                .beginTransaction()
                .replace(R.id.RelativeBunner, new ProductBannerFragment(),
                        Utils.banner).commit();


        //recycler View horizon orientation
        mRecyclerView = findViewById(R.id.Recycler_Product);


        getProductsPagenation("");

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

        //Custom Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarxx);
        setSupportActionBar(toolbar);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(getBackArrow(language)));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (test != null && !test.isEmpty()) {
                    test = "";
                    modelArrayList = new ArrayList<>();
                    favArray = new ArrayList<>();

                    page = 1;
                    mAdapter = new ProductAdsAdapter(ProductsActivity.this, modelArrayList,favArray);
                    LayoutManagaer = new GridLayoutManager(ProductsActivity.this, 2);
                    mRecyclerView.setLayoutManager(LayoutManagaer);
                    mRecyclerView.setAdapter(mAdapter);
                    getProductsPagenation(test);

                } else {
                    finish();
                }
            }
        });

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
        LayoutManagaer = new GridLayoutManager(ProductsActivity.this, 2);
        mAdapter = new ProductAdsAdapter(ProductsActivity.this, modelArrayList,favArray);
        mRecyclerView.setLayoutManager(LayoutManagaer);
        mRecyclerView.setAdapter(mAdapter);

        //tool bar title changer
        setActivityTitle("المنتجات","Products");


    }



    @Override
    protected void onStart() {
        super.onStart();
        favArray.clear();
        getFavID();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int items = item.getItemId();

        switch (items) {
            case R.id.action_filters:
                Intent intent = new Intent(ProductsActivity.this, FiltersActivity.class);
                intent.putExtra("idFilter", 4);
                startActivity(intent);
                break;
            case R.id.action_AddPro:
                if (userid==0){
                    intent = new Intent(this, LogInActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(this, AddItemActivity.class);
                    intent.putExtra("Add", 1001);
                    startActivity(intent);
                }
                break;
        }
        return true;

    }

    //menu option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        MenuItem itemAdd = menu.findItem(R.id.action_AddPro);
        itemAdd.setVisible(true);
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

    //RESPONSE
    public void ProductResponse(String response) {
        modelArrayList = new ArrayList<>();
        progressBar.setVisibility(View.GONE);
        Log.e("tagyyy", response);
        responseProduct(response,0);
        getFavID();
        mAdapter = new ProductAdsAdapter(ProductsActivity.this, modelArrayList,favArray);
        LayoutManagaer = new GridLayoutManager(ProductsActivity.this, 2);
        mRecyclerView.setLayoutManager(LayoutManagaer);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void getProducts(String keyword) {

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        int city = prefs.getInt("city", 0); //0 is the default value.
        int area = prefs.getInt("area", 0); //0 is the default value.
        int rate = prefs.getInt("num_rate", 0); //0 is the default value.
        int category = prefs.getInt("category", 0); //0 is the default value.
        int status = prefs.getInt("status", 0); //0 is the default value.

        String sort_key= prefs.getString("order_by",""); //0 is the default value.
        String sort_value= prefs.getString("sortingValue",""); //0 is the default value.

        Log.e("CXAAAA", "city=" + city + "area=" + area + "rate=" + rate + "Category=" + category + "Status=" + status);


        body.put("region", String.valueOf(city));
        body.put("city", String.valueOf(area));
        body.put("rate", String.valueOf(rate));
        body.put("category", String.valueOf(category));
        body.put("status", String.valueOf(status));
        body.put("keyword", keyword);

        body.put("orderBy",sort_key);
        body.put("sort", sort_value);

        final SearchProductAdRequest searchProductAdRequest = new SearchProductAdRequest(ProductsActivity.this, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ProductResponse(response);
                Log.e("4444", response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    NoInternt_Fragment fragment = new NoInternt_Fragment();
                    Bundle arguments = new Bundle();
                    arguments.putInt("duck", 55);
                    fragment.setArguments(arguments);
                    final android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.dodododo, fragment, Utils.Error);
                    ft.commitAllowingStateLoss();
                    /*fragmentManager
                            .beginTransaction()
                            .add(R.id.dodododo, new NoInternt_Fragment(),
                                    Utils.Error).commit();*/


                } else if (error instanceof AuthFailureError) {
                    //TODO
                    Log.e("3333", "1");

                } else if (error instanceof ServerError) {
                    //TODO
                    Log.e("3333", "2");
                } else if (error instanceof NetworkError) {
                    //TODO
                    Log.e("3333", "3");
                    Toast.makeText(ProductsActivity.this, "volly no Internet", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    //TODO
                    Log.e("3333", "4");

                }
            }
        }, 0);
        searchProductAdRequest.setBody((HashMap) body);
        searchProductAdRequest.start();
    }

    public void getProductsPagenation(String keyword) {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        int city = prefs.getInt("city", 0); //0 is the default value.
        int area = prefs.getInt("area", 0); //0 is the default value.
        int rate = prefs.getInt("num_rate", 0); //0 is the default value.
        int category = prefs.getInt("category", 0); //0 is the default value.
        int status = prefs.getInt("status", 0); //0 is the default value.

        Log.e("CXAAAA", "city=" + city + "area=" + area + "rate=" + rate + "Category=" + category + "Status=" + status);


        body.put("region", String.valueOf(city));
        body.put("city", String.valueOf(area));
        body.put("rate", String.valueOf(rate));
        body.put("category", String.valueOf(category));
        body.put("status", String.valueOf(status));
        body.put("keyword", keyword);

        final SearchProductAdRequest searchProductAdRequest = new SearchProductAdRequest(ProductsActivity.this, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PagenationResponse(response);
                Log.e("4444", response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    NoInternt_Fragment fragment = new NoInternt_Fragment();
                    Bundle arguments = new Bundle();
                    arguments.putInt("duck", 55);
                    fragment.setArguments(arguments);
                    final android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.dodododo, fragment, Utils.Error);
                    ft.commitAllowingStateLoss();
                    /*fragmentManager
                            .beginTransaction()
                            .add(R.id.dodododo, new NoInternt_Fragment(),
                                    Utils.Error).commit();*/


                } else if (error instanceof AuthFailureError) {
                    //TODO
                    Log.e("3333", "1");

                } else if (error instanceof ServerError) {
                    //TODO
                    Log.e("3333", "2");
                } else if (error instanceof NetworkError) {
                    //TODO
                    Log.e("3333", "3");
                    Toast.makeText(ProductsActivity.this, "volly no Internet", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    //TODO
                    Log.e("3333", "4");

                }
            }
        }, page);
        searchProductAdRequest.setBody((HashMap) body);
        searchProductAdRequest.start();
    }

    public void PagenationResponse(String response) {
        progressBar.setVisibility(View.GONE);
        Log.e("tagyyy", response);

        responseProduct(response,1);

        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(LayoutManagaer) {
            @Override
            public void onLoadMore(int current_page) {

                page++;

                Log.e("PageeeNext=", "" + page);
                if (page > last_page) {

                } else {
                    getProductsPagenation("");

                }


            }
        });

        mAdapter.notifyItemRangeInserted(mAdapter.getItemCount(), modelArrayList.size());

    }

    public void Progressbar() {
        progressBar.setVisibility(View.VISIBLE); //to show

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("onStop", "onStop");
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt("num_rate", 0);
        editor.putInt("city", 0);
        editor.putInt("area", 0);
        editor.putInt("category", 0);
        editor.putInt("status", 0);

        editor.putString("order_by", "");
        editor.putString("sortingValue", "");
        editor.apply();


    }

    private void getFavID() {
        body.put("category", "product");
        GetFavoriteProducts getFavId = new GetFavoriteProducts(ProductsActivity.this, userid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("FavPage", "pro" + response);
                try {
                    JSONArray array = new JSONArray(response);
                    for (int a = 0; a < array.length(); a++) {
                        JSONObject object = array.getJSONObject(a);
                        int favourable_id = object.getInt("favourable_id");
                        favArray.add(favourable_id);
                        Log.e("HiFrom", "1" + favArray);
                    }
                        mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("FavPage", "Err pro : " + response);

                }
                Log.e("HiFrom", "2" + favArray);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        getFavId.setBody((HashMap) body);
        getFavId.start();
        Log.e("HiFrom", "3" + favArray);

    }


    public void responseProduct(String response,int check){
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
                created_by = dataObject.getString("user_name");
                description = dataObject.getString("description");
                try {
                    JSONArray phoneArray = dataObject.getJSONArray("phone");
                    phone_1 = (String) phoneArray.get(0);
                    phone_2 = (String) phoneArray.get(1);
                } catch (Exception e) {
                    JSONArray phoneArray = dataObject.getJSONArray("phone");
                    phone_1 = (String) phoneArray.get(0);
                    phone_2 = "No phone has been added";
                }
                JSONObject categoryObject = dataObject.getJSONObject("category");
                category = categoryObject.getString(lang+"_name");
                modelArrayList.add(new ProductsModel(id, title, category, description, price, status, image, address, created_at,created_by, phone_1, phone_2));
            }

            if (check == 1){
            JSONObject meta = object.getJSONObject("meta");
            last_page = meta.getInt("last_page");
            Log.e("PageeeCurrent=", page + "");
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("error", e.toString());

        }

    }

    private void sorting(){
        MaterialDialog dialog = new MaterialDialog.Builder(ProductsActivity.this)
                .title(R.string.sort)
                .titleColor(getResources().getColor(R.color.black))
                .customView(R.layout.sorting_products, true)
                .positiveText(R.string.aapply)
                .positiveColor(getResources().getColor(R.color.colorPrimary))

                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {

                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putString("order_by",sortKey);
                        editor.putString("sortingValue",sortValue);
                        editor.apply();

                        getProducts("");


                    }
                })
                .show();
        View views = dialog.getCustomView();

        final RadioGroup radioGroupType = views.findViewById(R.id.Pro_rate_RadioGroup);

        radioGroupType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId) {

                    case R.id.ProRadio_Oldest:
                        sortKey="updated_at";
                        sortValue="asc";
                        break;
                    case R.id.ProRadio_Newest:
                        sortKey="updated_at";
                        sortValue="desc";
                        break;

                    case R.id.priceRadio_LTH:
                        sortKey="price";
                        sortValue="asc";
                        break;
                    case R.id.priceRadio_HTL:
                        sortKey="price";
                        sortValue="desc";
                        break;


                    default:
                        sortKey="";
                        sortValue="";
                        break;
                }
            }
        });


    }
}


