package com.example.gmsproduction.dregypt.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.activities.AddItemActivity;
import com.example.gmsproduction.dregypt.ui.activities.CosmeticsActivity;
import com.example.gmsproduction.dregypt.ui.activities.HospitalsActivity;
import com.example.gmsproduction.dregypt.ui.activities.JobsActivity;
import com.example.gmsproduction.dregypt.ui.activities.ProductsActivity;
import com.example.gmsproduction.dregypt.ui.activities.ClinicsActivity;
import com.example.gmsproduction.dregypt.ui.activities.PharmacyActivity;

import static android.content.Context.MODE_PRIVATE;


public class NoInternt_Fragment extends Fragment {
    View view;
    Button reloadbtn;
    TextView nointernetTXT;
    int desired_Int,idLANG;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.no_internt_layout, container, false);



        nointernetTXT = view.findViewById(R.id.NoInternt_TXT);
        reloadbtn = view.findViewById(R.id.NoInternt_BTN);
        lang();
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
                    case 5599:
                        ReloadAddProducts();
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
    public void ReloadAddProducts() {

        ((AddItemActivity) getActivity()).ProductFragment();
        //((CosmeticsActivity)getActivity()).Progressbar();
        Toast.makeText(getContext(), "Add Product", Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().beginTransaction().remove(NoInternt_Fragment.this).commit();

    }
    public void ReloadAddJobs() {

        ((AddItemActivity) getActivity()).JobFragment();
        //((CosmeticsActivity)getActivity()).Progressbar();
        Toast.makeText(getContext(), "Add Job", Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().beginTransaction().remove(NoInternt_Fragment.this).commit();

    }



    private void lang(){
        //the language sharedprefs
        SharedPreferences prefs = getActivity().getSharedPreferences("LangKey", MODE_PRIVATE);

        idLANG = prefs.getInt("languageNum", 1); //0 is the default value.
        //arabic
        if (idLANG==1){
            nointernetTXT.setText("Failed to load, Please check your internet connection\n" +
                    "before proceeding");
            reloadbtn.setText("Retry Again");
        }else if (idLANG==2){
            nointernetTXT.setText("أخفق التحميل ، يرجى التحقق من اتصال الإنترنت قبل المتابعة");
            reloadbtn.setText("إعادة تحميل");
        }
    }
}
