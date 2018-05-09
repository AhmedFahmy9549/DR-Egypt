package com.example.gmsproduction.dregypt.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.gmsproduction.dregypt.Models.JobsModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.activities.DetailsJobs;
import com.example.gmsproduction.dregypt.utils.ProductsModel;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class JobAdsAdapter extends RecyclerView.Adapter<JobAdsAdapter.MyViewHolder> {
    final String basicImgUrl = "http://gms-sms.com:89";
    private Context mContext;
    private ArrayList<JobsModel> mArrayList;
    int LastPosition = -1;

    //RecyclerViewClickListener ClickListener ;
    public JobAdsAdapter() {
    }

    public JobAdsAdapter(Context mContext, ArrayList<JobsModel> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
    }

    /*  public void setClickListener(RecyclerViewClickListener clickListener){
         this.ClickListener= clickListener;
     }*/
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_job_ads, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final JobsModel currentItem = mArrayList.get(position);
        final String id = currentItem.getId();
        final String title = currentItem.getTitle();
        final String description = currentItem.getDescription();
        final String image = currentItem.getImage();
        final String address = currentItem.getAddress();
        final String created_at = currentItem.getCreated_at();
        final String phone_1 = currentItem.getPhone_1();
        final String phone_2 = currentItem.getPhone_2();

        holder.TxTTitle.setText(title);
        holder.TxTAdress.setText(address);
        holder.TxTPhone.setText(phone_1);
        holder.TxTDate.setText(created_at);

        //togglebutton
        holder.toggleButton.setChecked(false);
        holder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_rate_white_18dp));
        holder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    holder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_star_rate_black_18dp));
                else
                    holder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_rate_white_18dp));
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, DetailsJobs.class);
                intent.putExtra("JDTitle",currentItem.getTitle());
                intent.putExtra("JDdescription",currentItem.getDescription());
                intent.putExtra("JDsalary",currentItem.getSalary());
                intent.putExtra("JDimage",currentItem.getImage());
                intent.putExtra("JDaddress",currentItem.getAddress());
                intent.putExtra("JDcreated_at",currentItem.getCreated_at());
                intent.putExtra("JDphone_1",currentItem.getPhone_1());
                intent.putExtra("JDphone_2",currentItem.getPhone_2());
                intent.putExtra("JDexperience",currentItem.getExperience());
                intent.putExtra("JDeducation_level",currentItem.getEducation_level());
                intent.putExtra("JDemployment_type",currentItem.getEmployment_type());
                mContext.startActivity(intent);

            }
        });

        //setAnimation(holder.cardView,position);
    }


    @Override
    public int getItemCount() {
        if (mArrayList == null || mArrayList.size() == 0)
            return 0;
        return mArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener */ {

        TextView TxTTitle;
        TextView TxTAdress;
        TextView TxTPhone;
        TextView TxTDate;
        CardView cardView;
        ToggleButton toggleButton;


        public MyViewHolder(View itemView) {
            super(itemView);
            // itemView.setOnClickListener(this);

            TxTTitle = itemView.findViewById(R.id.Jobs_Title);
            TxTAdress = itemView.findViewById(R.id.Jobs_adress);
            TxTPhone = itemView.findViewById(R.id.Jobs_phone);
            TxTDate = itemView.findViewById(R.id.Jobs_Date);
            cardView = itemView.findViewById(R.id.Jobs_Card);
            toggleButton = itemView.findViewById(R.id.Jobs_ToggleButton);

        }

       /* @Override
        public void onClick(View view) {
            if(ClickListener!=null)
                ClickListener.ItemClicked(view ,getAdapterPosition());
        }

        public void clearAnimation()
        {
            cardView.clearAnimation();
        }
    }

    public interface RecyclerViewClickListener
    {

        public void ItemClicked(View v, int position);
    }

    private void setAnimation(View viewToAnimate, int position)
    {

        if (position > LastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            LastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }*/

    }

}

