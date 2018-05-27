package com.example.gmsproduction.dregypt.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.activities.CosmeticsActivity;
import com.example.gmsproduction.dregypt.ui.activities.HospitalsActivity;
import com.example.gmsproduction.dregypt.ui.activities.JobsActivity;
import com.example.gmsproduction.dregypt.ui.activities.ProductsActivity;
import com.example.gmsproduction.dregypt.ui.activities.ClinicsActivity;
import com.example.gmsproduction.dregypt.ui.activities.PharmacyActivity;


public class NoInternt_Fragment extends Fragment {
    View view;
    Button reloadbtn;
    int desired_Int;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.no_internt_layout, container, false);
        reloadbtn = view.findViewById(R.id.NoInternt_BTN);
        Bundle arguments = getArguments();
        desired_Int = arguments.getInt("duck");
        reloadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (desired_Int) {
                    case 55:
                        ReloadProduct();
                        break;
                    case 66:
                        ReloadCosmetics();
                        break;
                    case 77:
                        ReloadJobs();
                        break;
                    case 101:
                        ReloadHospitals();
                        break;
                    case 202:
                        ReloadClinic();
                        break;
                    case 303:
                        ReloadPharmacy();
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
    public void ReloadHospitals() {

        ((HospitalsActivity) getActivity()).getHospital("");
        //((CosmeticsActivity)getActivity()).Progressbar();
        Toast.makeText(getContext(), "Hospital", Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().beginTransaction().remove(NoInternt_Fragment.this).commit();

    }
    public void ReloadClinic() {

        ((ClinicsActivity) getActivity()).getClinicsPagenation("");
        //((CosmeticsActivity)getActivity()).Progressbar();
        Toast.makeText(getContext(), "Clinic", Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().beginTransaction().remove(NoInternt_Fragment.this).commit();

    }
    public void ReloadPharmacy() {

        ((PharmacyActivity) getActivity()).getPharmacy("");
        //((CosmeticsActivity)getActivity()).Progressbar();
        Toast.makeText(getContext(), "Pharmacy", Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().beginTransaction().remove(NoInternt_Fragment.this).commit();

    }


}
