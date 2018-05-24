package com.example.gmsproduction.dregypt.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.fragments.AddItems.AddJobFragment;
import com.example.gmsproduction.dregypt.ui.fragments.AddItems.AddProductFragment;
import com.example.gmsproduction.dregypt.ui.fragments.NoInternt_Fragment;
import com.example.gmsproduction.dregypt.utils.Utils;

public class AddItemActivity extends AppCompatActivity {
    int vald;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent extra = getIntent();
        vald = extra.getIntExtra("Add",0);

        if (vald==1001){
            AddProductFragment fragment = new AddProductFragment();
            Bundle arguments = new Bundle();
            arguments.putInt( "duck" , 55);
            fragment.setArguments(arguments);
            final android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.additem_Include, fragment , Utils.addproduct);
            ft.commit();
        }else if (vald==2002){
            AddJobFragment fragment = new AddJobFragment();
            Bundle arguments = new Bundle();
            arguments.putInt( "duck" , 55);
            fragment.setArguments(arguments);
            final android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.additem_Include, fragment , Utils.addproduct);
            ft.commit();
        }


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
