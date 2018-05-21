package com.example.gmsproduction.dregypt.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.fragments.AddItems.AddProductFragment;
import com.example.gmsproduction.dregypt.ui.fragments.NoInternt_Fragment;
import com.example.gmsproduction.dregypt.utils.Utils;

public class AddItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);

        AddProductFragment fragment = new AddProductFragment();
        Bundle arguments = new Bundle();
        arguments.putInt( "duck" , 55);
        fragment.setArguments(arguments);
        final android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.additem_Include, fragment , Utils.addproduct);
        ft.commit();

    }
}
