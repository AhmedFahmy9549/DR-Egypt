package com.example.gmsproduction.dregypt.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.gmsproduction.dregypt.R;

import java.util.HashMap;

public class MedicalGuideActivity extends AppCompatActivity implements View.OnClickListener , BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private SliderLayout mDemoSlider;
    ImageView hospitalBtn,ClinicBtn,PharmacyBtn;
    TextView hospitalText,ClinicText,PharmacyText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_guide);

        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        hospitalBtn=(ImageView) findViewById(R.id.next_name1);
        ClinicBtn=(ImageView) findViewById(R.id.next_name2);
        PharmacyBtn=(ImageView) findViewById(R.id.next_name3);

        hospitalText=(TextView) findViewById(R.id.text_name1);
        ClinicText=(TextView) findViewById(R.id.text_name2);
        PharmacyText=(TextView) findViewById(R.id.text_name3);


        hospitalBtn.setOnClickListener(this);
        ClinicBtn.setOnClickListener(this);
        PharmacyBtn.setOnClickListener(this);
        hospitalText.setOnClickListener(this);
        ClinicText.setOnClickListener(this);
        PharmacyText.setOnClickListener(this);


        HashMap<String, Integer> url_maps = new HashMap<String, Integer>();
        url_maps.put("Big Bang Theory",  R.drawable.photo1);
        url_maps.put("House of Cards",  R.drawable.photo2);
        url_maps.put("Game of Thrones", R.drawable.photo3);

        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit.CenterCrop)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator_ducks));
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(5000);
        mDemoSlider.addOnPageChangeListener(this);
        /*ListView l = (ListView) findViewById(R.id.transformers);
        l.setAdapter(new TransformerAdapter(this));
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDemoSlider.setPresetTransformer(((TextView) view).getText().toString());
                Toast.makeText(MedicalGuideActivity.this, ((TextView) view).getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
*/

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        int id=view.getId();

        switch (id) {
            case R.id.next_name1:
                intent = new Intent(this, HospitalsActivity.class);
                startActivity(intent);
                break;


            case R.id.next_name2:
                intent = new Intent(this, ClinicsActivity.class);
                startActivity(intent);
                break;

            case R.id.next_name3:
                intent = new Intent(this, PharmacyActivity.class);
                startActivity(intent);
                break;

            case R.id.text_name1:
                intent = new Intent(this, HospitalsActivity.class);
                startActivity(intent);
                break;
            case R.id.text_name2:
                intent = new Intent(this, ClinicsActivity.class);
                startActivity(intent);
                break;
            case R.id.text_name3:
                intent = new Intent(this, PharmacyActivity.class);
                startActivity(intent);
                break;
        }

        }
}

