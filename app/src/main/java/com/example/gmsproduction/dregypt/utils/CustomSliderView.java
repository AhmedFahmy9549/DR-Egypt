package com.example.gmsproduction.dregypt.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.example.gmsproduction.dregypt.R;

/**
 * Created by Hima on 7/24/2018.
 */

public class CustomSliderView extends BaseSliderView {
    public CustomSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.custom_sliderview,null);
        ImageView target = (ImageView)v.findViewById(R.id.slider_image);
        TextView description = (TextView)v.findViewById(R.id.sliderdescription);
        description.setText(getDescription());
        bindEventAndShow(v, target);
        return v;
    }
}