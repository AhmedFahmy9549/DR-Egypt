package com.example.gmsproduction.dregypt.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.activities.ProductsActivity;


public class NoInternt_Fragment extends Fragment {
    View view;
    Button reloadbtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.no_internt_layout, container, false);
        reloadbtn = view.findViewById(R.id.NoInternt_BTN);
        ReloadProduct();
        return view;
    }

    public void ReloadProduct(){
        reloadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ProductsActivity)getActivity()).getProducts("");
                ((ProductsActivity)getActivity()).Progressbar();
                getActivity().getSupportFragmentManager().beginTransaction().remove(NoInternt_Fragment.this).commit();
            }
        });
    }



}
