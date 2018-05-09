package com.example.gmsproduction.dregypt.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gmsproduction.dregypt.R;
import com.squareup.picasso.Picasso;

public class DetailsJobs extends AppCompatActivity {

    String id, userId, title, description, salary, image, address, created_at, phone_1, phone_2, experience, education_level, employment_type;
    TextView TXTtitle, TXTdescription, TXTsalary, TXTaddress, TXTcreated_at, TXTphone_1, TXTexperience, TXTeducation_level, TXTemployment_type;
    ImageView TXTimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_jobs);
        Extras();
        Initializing();
        Deploy();

    }

    public void Extras() {
        Intent extra = getIntent();
        title = extra.getStringExtra("JDTitle");
        description = extra.getStringExtra("JDdescription");
        salary = extra.getStringExtra("JDsalary");
        image = extra.getStringExtra("JDimage");
        address = extra.getStringExtra("JDaddress");
        created_at = extra.getStringExtra("JDcreated_at");
        phone_1 = extra.getStringExtra("JDphone_1");
        phone_2 = extra.getStringExtra("JDphone_2");
        experience = extra.getStringExtra("JDexperience");
        education_level = extra.getStringExtra("JDeducation_level");
        employment_type = extra.getStringExtra("JDemployment_type");



    }

    public void Deploy() {
        TXTdescription.setText(description);
        TXTsalary.setText(salary);
        Picasso.with(DetailsJobs.this).load(image).fit().centerCrop().into(TXTimage);
        TXTaddress.setText(address);
        TXTcreated_at.setText(created_at);
        TXTphone_1.setText(phone_1);
        TXTphone_1.append(" , " + phone_2);
        TXTexperience.setText(experience);
        TXTeducation_level.setText(education_level);
        TXTemployment_type.setText(employment_type);
        setTitle(title);
    }

    public void Initializing() {

        TXTdescription = findViewById(R.id.JobDetails_description);
        TXTsalary = findViewById(R.id.JobDetails_salary);
        TXTimage = findViewById(R.id.JobDetails_IMG);
        TXTaddress = findViewById(R.id.JobDetails_address);
        TXTcreated_at = findViewById(R.id.JobDetails_created_at);
        TXTphone_1 = findViewById(R.id.JobDetails_phone);
        TXTexperience = findViewById(R.id.JobDetails_explvl);
        TXTeducation_level = findViewById(R.id.JobDetails_education_level);
        TXTemployment_type = findViewById(R.id.JobDetails_employment_type);
    }
}
