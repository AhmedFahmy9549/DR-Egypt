package com.example.gmsproduction.dregypt.ui.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Switch;

import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.fragments.Filters.ClinicsFilters;
import com.example.gmsproduction.dregypt.ui.fragments.Filters.CosmeticsFilters;
import com.example.gmsproduction.dregypt.ui.fragments.Filters.HospitalFilters;
import com.example.gmsproduction.dregypt.ui.fragments.Filters.JobsFilters;
import com.example.gmsproduction.dregypt.ui.fragments.Filters.PharmacyFilters;
import com.example.gmsproduction.dregypt.ui.fragments.Filters.ProductFilters;

import java.util.HashMap;

public class FiltersActivity extends BaseActivity {
     public int language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        Intent intent = getIntent();

        getLanguage();

        language = getIdLANG();
        localization(language);
        setActivityTitle("فلاتر","Filters");


        int id = intent.getIntExtra("idFilter", 0);

        switch (id) {
            case 1:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_filter, new HospitalFilters(), "HospitalFilters")
                        .commit();
                break;
            case 2:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_filter, new ClinicsFilters(), "ClinicsFilters")
                        .commit();
                break;
            case 3:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_filter, new PharmacyFilters(), "PharmacyFilters")
                        .commit();
                break;
            case 4:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_filter, new ProductFilters(), "ProductFilters")
                        .commit();
                break;
            case 5:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_filter, new CosmeticsFilters(), "CosmeticsFilters")
                        .commit();
                break;
            case 6:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_filter, new JobsFilters(), "JobsFilters")
                        .commit();
                break;

        }
        init();

    }

    public int getLanguage() {
        language = getIdLANG();
        localization(language);
        Log.e("LAAAAAA","hi+"+language);
        return language;
    }

    public HashMap init() {
        HashMap<Integer, String> meMap = new HashMap<Integer, String>();
        meMap.put(4, "El Beheira Governorate");
        meMap.put(2, "Aswan Governorate");
        meMap.put(14, "Luxor Governorate");
        meMap.put(22, "Qena Governorate");
        meMap.put(25, "Sohag Governorate");
        meMap.put(23, "Red Sea Governorate");
        meMap.put(3, " Assiut Governorate");
        meMap.put(18, "New Valley Governorate");
        meMap.put(16, "Menia Governorate");
        meMap.put(5, "Beni Suef Governorate");
        meMap.put(27, "Suez Governorate");
        meMap.put(26, "South Sinai Governorate");
        meMap.put(6, "Cairo Governorate");
        meMap.put(9, "Faiyum Governorate");
        meMap.put(11, "Giza Governorate");
        meMap.put(12, "Ismailia Governorate");
        meMap.put(24, "Ash Sharqia Governorate");
        meMap.put(21, "Al Qalyubia Governorate");
        meMap.put(17, "Menofia Governorate");
        meMap.put(10, "Gharbia Governorate");
        meMap.put(19, "North Sinai Governorate");
        meMap.put(20, "Port Said Governorate");
        meMap.put(8, "Damietta Governorate");
        meMap.put(7, "Dakahlia Governorate");
        meMap.put(13, "Kafr El Sheikh Governorate");
        meMap.put(1, "Alexandria Governorate ");
        meMap.put(15, "Matrouh Governorate");






        meMap.put(4, "البحيرة");
        meMap.put(2, "أسوان");
        meMap.put(14, "الأقصر");
        meMap.put(22, "قنا");
        meMap.put(25, "سوهاج");
        meMap.put(23, "البحر الأحمر");
        meMap.put(3, " أسيوط");
        meMap.put(18, "الوادي الجديد");
        meMap.put(16, "المنيا");
        meMap.put(5, "بني سويف");
        meMap.put(27, "السويس");
        meMap.put(26, "جنوب سيناء");
        meMap.put(6, "القاهرة");
        meMap.put(9, "الفيوم");
        meMap.put(11, "الجيزة");
        meMap.put(12, "الإسماعيلية");
        meMap.put(24, "الشرقية");
        meMap.put(21, "القليوبية");
        meMap.put(17, "المنوفية");
        meMap.put(10, "الغربية");
        meMap.put(19, "شمال سيناء");
        meMap.put(20, "بورسعيد");
        meMap.put(8, "دمياط");
        meMap.put(7, "الدقهلية");
        meMap.put(13, "كفر الشيخ");
        meMap.put(1, "الإسكندرية");
        meMap.put(15, "مطروح");


        return meMap;
    }
}
