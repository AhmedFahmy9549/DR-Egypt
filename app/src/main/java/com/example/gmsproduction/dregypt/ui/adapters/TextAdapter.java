package com.example.gmsproduction.dregypt.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.activities.DetailsProducts;
import com.example.gmsproduction.dregypt.utils.ProductsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.MyViewHolder> {
    final String basicImgUrl = "http://gms-sms.com:89";
    private Context mContext;
    private ArrayList<String> mArrayList;
    int LastPosition = -1;



    public TextAdapter(Context mContext, ArrayList<String> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
        Log.e("Texxxxt",""+this.mArrayList.size());
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("Texxxxt",""+this.mArrayList.size());
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_recycler_specialization, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.e("Texxxxt",""+mArrayList.size());



    }


    @Override
    public int getItemCount() {
        Log.e("Texxxxt",""+mArrayList.size());
        return mArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ProductTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            ProductTitle = itemView.findViewById(R.id.special_text);

        }




    }








}

