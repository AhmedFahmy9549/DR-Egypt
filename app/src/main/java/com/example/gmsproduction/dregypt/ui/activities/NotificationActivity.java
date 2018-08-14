package com.example.gmsproduction.dregypt.ui.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.gmsproduction.dregypt.Models.NotificationModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.adapters.AdapterNotification;

import java.util.ArrayList;

public class NotificationActivity extends BaseActivity {

    RecyclerView mRecyclerView;
    AdapterNotification mAdapter;
    ArrayList<NotificationModel> mArraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mRecyclerView = findViewById(R.id.recycler_notification);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mArraylist = new ArrayList<>();

        setActivityTitle("اشعارات","Notifications");

        SharedPreferences prefs = getSharedPreferences("notification", MODE_PRIVATE);
        String title = prefs.getString("title", "null");
        String body = prefs.getString("body", "null");
        String title1 = prefs.getString("title1", "null");
        String body1 = prefs.getString("body1", "null");

        if (!body.equals("null")){
            mArraylist.add(new NotificationModel(title,body));
        }
        if (!body1.equals("null")){
            mArraylist.add(new NotificationModel(title1,body1));
        }
        mAdapter = new AdapterNotification(this,mArraylist);
        mRecyclerView.setAdapter(mAdapter);


    }
}
