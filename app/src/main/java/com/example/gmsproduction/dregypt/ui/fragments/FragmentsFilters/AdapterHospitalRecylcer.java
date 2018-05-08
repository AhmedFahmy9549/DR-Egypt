package com.example.gmsproduction.dregypt.ui.fragments.FragmentsFilters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.gmsproduction.dregypt.Models.HospitalModel;
import com.example.gmsproduction.dregypt.Models.LocationModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.activities.HospitalsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ahmed Fahmy on 12/5/2017.
 */

public class AdapterHospitalRecylcer extends RecyclerView.Adapter<AdapterHospitalRecylcer.Myholder> {
    private Context context;
    private ArrayList<HospitalModel> arrayList;
    String TAG = "HospitalsFragment";
    Intent intent;

    public AdapterHospitalRecylcer(Context context, ArrayList<HospitalModel> arrayList) {

        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_hospitals, parent, false);
        return new Myholder(view);

    }

    @Override
    public void onBindViewHolder(final Myholder holder, final int position) {

        HospitalModel model=arrayList.get(position);

        holder.textName.setText(model.getName());
        holder.textAddreess.setText(model.getAddress());
        holder.textEmail.setText(model.getEmail());
        holder.textWebsite.setText(model.getWebsite());
        holder.textFav.setText(model.getFavorites());

        Picasso.with(context).load(model.getImg()).into(holder.imageView);



    }

    @Override
    public int getItemCount() {
        return arrayList.size();


    }

    class Myholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textName,textFav,textAddreess,textEmail,textWebsite;
        ToggleButton toggleFav;


        public Myholder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_hospital);
            textName=itemView.findViewById(R.id.text_name);
            textAddreess=itemView.findViewById(R.id.text_address);
            textEmail=itemView.findViewById(R.id.text_email);
            textWebsite=itemView.findViewById(R.id.text_website);
            textFav=itemView.findViewById(R.id.text_fav);
            toggleFav=itemView.findViewById(R.id.myToggleButton);


        }
    }
}
