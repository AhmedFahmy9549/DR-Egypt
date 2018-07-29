package com.example.gmsproduction.dregypt.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.utils.ContactUsUtilities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Hima on 4/18/2018.
 */

public class ContactUsAdapter extends RecyclerView.Adapter<ContactUsAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mArrayList;
    private ArrayList<Integer> mArrayList2;
    ContactUsUtilities utilities;

    public ContactUsAdapter(Context mContext, ArrayList<String> mArrayList, ArrayList<Integer> mArrayList2) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
        this.mArrayList2 = mArrayList2;

        utilities= new ContactUsUtilities(mContext);
    }

    @Override
    public ContactUsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.row_contactus, parent, false);
        return new ContactUsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ContactUsAdapter.ViewHolder holder, final int position) {
        final String text = mArrayList.get(position);
        final int images = mArrayList2.get(position);

        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position){
                    case 0:
                        utilities.openMapLocation();
                        break;
                    case 1:
                        utilities.phoneCall(utilities.phoneNumber);
                        break;
                    case 2:
                        mContext.startActivity(utilities.getOpenFacebookIntent(mContext,utilities.facebookPageID));
                        break;
                    case 3:
                        utilities.openEmail(utilities.emailStr);
                        break;
                    case 4:
                        utilities.openWebsite(utilities.websiteStr);
                        break;
                    /*case 5:
                        utilities.openYoutube(utilities.youtubeChannel);
                        break;*/

                }

            }
        });

        holder.data_name.setText(text);
        Picasso.with(mContext).load(images).into(holder.imgs);



    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        ImageView imgs;
        TextView data_name;
        LinearLayout linear;

        public ViewHolder(View itemView) {
            super(itemView);
            imgs = itemView.findViewById(R.id.ContactUs_IMG);
            data_name = itemView.findViewById(R.id.ContactUS_TXT);
            linear = itemView.findViewById(R.id.ContactUs_linear);
        }
    }
}