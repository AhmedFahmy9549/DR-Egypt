package com.example.gmsproduction.dregypt.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.ProductAdsRequests.GetFavoriteProducts;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.activities.DetailsProducts;
import com.example.gmsproduction.dregypt.Models.ProductsModel;
import com.example.gmsproduction.dregypt.ui.activities.ProductsActivity;
import com.example.gmsproduction.dregypt.utils.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class ProductAdsAdapter extends RecyclerView.Adapter<ProductAdsAdapter.MyViewHolder> {
    final String basicImgUrl = "http://gms-sms.com:89";
    private Context mContext;
    private ArrayList<ProductsModel> mArrayList;
    int LastPosition = -1, userid,idLANG;
    RecyclerViewClickListener ClickListener;
    String CheckStatus;
    String status;
    ArrayList<Integer> favArray;


    public ProductAdsAdapter(Context mContext, ArrayList<ProductsModel> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
        this.favArray = favArray;
        Log.e("HiFrom", "" + favArray);
    }

    public void setClickListener(RecyclerViewClickListener clickListener) {
        this.ClickListener = clickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_products, parent, false);
        SharedPreferences prefs = mContext.getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
        userid = prefs.getInt("User_id", 0);
        SharedPreferences prefs2 = mContext.getSharedPreferences("LangKey", MODE_PRIVATE);

        idLANG = prefs2.getInt("languageNum", 1);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ProductsModel currentItem = mArrayList.get(position);
        final String id = currentItem.getIdz();
        final String title = currentItem.getTitlez();
        final String description = currentItem.getDescription();
        final String price = currentItem.getPrice();
        CheckStatus = currentItem.getStatus();
        if (CheckStatus.equals("1")) {
            if (idLANG == 1) {
                status = "New";
            }else if (idLANG==2){
                status = "جديد";
            }
        } else {
            if (idLANG == 1) {
                status = "Used";
            }else if (idLANG==2){
                status = "مستعمل";
            }
        }
        final String image = currentItem.getImage();
        final String address = currentItem.getAddress();
        final String created_at = currentItem.getCreated_at();
        final String phone_1 = currentItem.getPhone_1();
        final String phone_2 = currentItem.getPhone_2();
        final String Category = currentItem.getCategory();

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailsProducts.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("arrz", mArrayList);
                intent.putExtras(bundle);
                intent.putExtra("position", position);

                mContext.startActivity(intent);
            }
        });

        holder.ProductCategory.setText(Category);
        holder.ProductTitle.setText(title);
        if (idLANG==1){
            holder.ProductPrice.setText(price + " L.E ");

        }else if (idLANG==2){
            holder.ProductPrice.setText(price+" ج.م ");

        }

        holder.ProductStatus.setText(status);
        Picasso.with(mContext).load(image).fit().centerInside().into(holder.imageView);

        holder.toggleButton.setChecked(false);
        holder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_black_24dp));
        holder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    holder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_black_24dp_fill));
                else
                    holder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_black_24dp));
            }
        });

        //setAnimation(holder.cardView,position);

    }


    @Override
    public int getItemCount() {
        if (mArrayList == null || mArrayList.size() == 0)
            return 0;
        return mArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView ProductTitle, ProductCategory, ProductPrice, ProductStatus, ProductsMoreDetails;
        ImageView imageView;
        LinearLayout cardView;
        ToggleButton toggleButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            ProductTitle = itemView.findViewById(R.id.Products_title);
            ProductPrice = itemView.findViewById(R.id.Products_Price);
            ProductStatus = itemView.findViewById(R.id.Products_Status);
            imageView = itemView.findViewById(R.id.Products_Img);
            cardView = itemView.findViewById(R.id.Products_cardView);
            ProductCategory = itemView.findViewById(R.id.Products_Category);
            toggleButton = itemView.findViewById(R.id.Products_ToggleButton);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (ClickListener != null)
                ClickListener.ItemClicked(view, getAdapterPosition());
        }

        public void clearAnimation() {
            cardView.clearAnimation();
        }
    }

    public interface RecyclerViewClickListener {

        public void ItemClicked(View v, int position);
    }

    private void setAnimation(View viewToAnimate, int position) {

        if (position > LastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            LastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }


}

