package com.example.gmsproduction.dregypt.ui.fragments.FragmentsFilters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.gmsproduction.dregypt.Models.HospitalModel;
import com.example.gmsproduction.dregypt.Models.LocationModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.activities.DetailsActivity;
import com.example.gmsproduction.dregypt.ui.activities.HospitalsActivity;
import com.example.gmsproduction.dregypt.ui.activities.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

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

        final HospitalModel model = arrayList.get(position);


        holder.textName.setText(model.getName());
        holder.textAddreess.setText(model.getAddress());
        holder.textEmail.setText(model.getEmail());
        holder.textWebsite.setText(model.getWebsite());
        Log.e(TAG, "Favorite= " + model.getFavorites());
        holder.textFav.setText("" + model.getFavorites());
        Picasso.with(context).load(model.getImg()).into(holder.imageView);

        holder.toggleFav.setChecked(false);
        holder.toggleFav.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_black_24dp));
        holder.toggleFav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    holder.toggleFav.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_black_24dp_fill));
                else
                    holder.toggleFav.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_black_24dp));
            }
        });

        holder.ratingBar.setRating(model.getRating());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("name", model.getName());
                intent.putExtra("address", model.getAddress());
                intent.putExtra("email", model.getEmail());
                intent.putExtra("website", model.getWebsite());
                intent.putExtra("phone1", model.getPhone1());
                intent.putExtra("phone2", model.getPhone2());
                intent.putExtra("image", model.getImg());
                intent.putExtra("note", model.getNote());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();


    }

    class Myholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textName, textFav, textAddreess, textEmail, textWebsite;
        ToggleButton toggleFav;
        RatingBar ratingBar;
        CardView cardView;


        public Myholder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_hospital);
            textName = itemView.findViewById(R.id.text_name);
            textAddreess = itemView.findViewById(R.id.text_address);
            textEmail = itemView.findViewById(R.id.text_email);
            textWebsite = itemView.findViewById(R.id.text_website);
            textFav = itemView.findViewById(R.id.text_fav);
            toggleFav = itemView.findViewById(R.id.myToggleButton);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            cardView = itemView.findViewById(R.id.cardView);


        }
    }
}
