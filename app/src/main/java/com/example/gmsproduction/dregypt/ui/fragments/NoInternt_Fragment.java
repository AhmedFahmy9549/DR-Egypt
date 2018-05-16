package com.example.gmsproduction.dregypt.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.gmsproduction.dregypt.ui.activities.CosmeticsActivity;
import com.example.gmsproduction.dregypt.ui.activities.JobsActivity;
import com.example.gmsproduction.dregypt.ui.activities.ProductsActivity;

import static android.content.Context.MODE_PRIVATE;
import static com.example.gmsproduction.dregypt.ui.activities.ProductsActivity.CheckInternet;


public class NoInternt_Fragment extends Fragment {
    View view;
    Button reloadbtn;
    int desired_string;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.no_internt_layout, container, false);
        reloadbtn = view.findViewById(R.id.NoInternt_BTN);
        Bundle arguments = getArguments();
        desired_string = arguments.getInt("duck");
        reloadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (desired_string) {
                    case 55:
                        ReloadProduct();
                        break;
                    case 66:
                        ReloadCosmetics();
                        break;
                    case 77:
                        ReloadJobs();
                        break;
                }
            }
        });
        return view;
    }

    public void ReloadProduct() {

        ((ProductsActivity) getActivity()).getProducts("");
        ((ProductsActivity) getActivity()).Progressbar();
        Toast.makeText(getContext(), "Product", Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().beginTransaction().remove(NoInternt_Fragment.this).commit();

    }

    public void ReloadCosmetics() {

        ((CosmeticsActivity) getActivity()).getCosmetics("");
        //((CosmeticsActivity)getActivity()).Progressbar();
        Toast.makeText(getContext(), "Cosmetic", Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().beginTransaction().remove(NoInternt_Fragment.this).commit();
    }

    public void ReloadJobs() {

        ((JobsActivity) getActivity()).getJobs("");
        //((CosmeticsActivity)getActivity()).Progressbar();
        Toast.makeText(getContext(), "Jobs", Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().beginTransaction().remove(NoInternt_Fragment.this).commit();

    }


}