package com.example.gmsproduction.dregypt.ui.fragments.FragmentsFilters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gmsproduction.dregypt.Models.LocationModel;
import com.example.gmsproduction.dregypt.R;

import java.util.ArrayList;

/**
 * Created by Ahmed Fahmy on 12/5/2017.
 */

public class AdapterSpecializationRecylcer extends RecyclerView.Adapter<AdapterSpecializationRecylcer.Myholder> {
    private Context context;
    ArrayList<LocationModel> arrayList;

    public AdapterSpecializationRecylcer(Context context, ArrayList<LocationModel> arrayList) {

        this.context = context;
        this.arrayList = arrayList;
        Log.e("Context", "Context=" + context);
        Log.e("Adapter", "adapter" + arrayList.size());
    }

    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_recycler_specialization, parent, false);
        return new Myholder(view);

    }

    @Override
    public void onBindViewHolder(final Myholder holder, final int position) {

        final LocationModel model = arrayList.get(position);
        holder.textView.setText(model.getLocName());
        holder.imgaeView.setImageDrawable(context.getResources().getDrawable(R.drawable.tooth));

        holder.textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                holder.textView.setBackgroundColor(context.getResources().getColor(R.color.bluoo));

                return false;
            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = context.getSharedPreferences("Location", context.MODE_PRIVATE).edit();
                editor.putInt("specialId", model.getLocId());
                editor.apply();

                Intent intent = new Intent(context, RegionActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();


    }

    class Myholder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imgaeView;
        LinearLayout linearLayout;


        public Myholder(View itemView) {
            super(itemView);
            imgaeView = itemView.findViewById(R.id.special_image);
            textView = itemView.findViewById(R.id.special_text);
            linearLayout = itemView.findViewById(R.id.special_linear);


        }
    }
}
