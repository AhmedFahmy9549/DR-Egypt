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

import com.example.gmsproduction.dregypt.Models.ProductsModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.activities.DetailsProducts;
import com.example.gmsproduction.dregypt.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Hima on 8/7/2018.
 */

public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.MyViewHolder> {
    private Context mContext;
    private ArrayList<ProductsModel> mArrayList;



    public AdapterNotification(Context mContext, ArrayList<ProductsModel> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
    }

    @Override
    public AdapterNotification.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notification, parent, false);
        return new AdapterNotification.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterNotification.MyViewHolder holder, final int position) {
        ProductsModel currentItem = mArrayList.get(position);


    }

    @Override
    public int getItemCount() {
        if (mArrayList == null || mArrayList.size() == 0)
            return 0;
        return mArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView body,title;

        public MyViewHolder(View itemView) {
            super(itemView);
            body = itemView.findViewById(R.id.row_noti_body);
            title = itemView.findViewById(R.id.row_noti_title);

        }

    }



}
