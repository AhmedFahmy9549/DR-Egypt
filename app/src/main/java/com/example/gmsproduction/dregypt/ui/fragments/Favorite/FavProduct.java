package com.example.gmsproduction.dregypt.ui.fragments.Favorite;

/**
 * Created by Hima on 5/20/2018.
 */
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gmsproduction.dregypt.R;

public class FavProduct extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite_products, container, false);

        return rootView;
    }
}
