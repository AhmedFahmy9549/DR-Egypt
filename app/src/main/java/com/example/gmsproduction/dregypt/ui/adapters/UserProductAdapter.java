package com.example.gmsproduction.dregypt.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.gmsproduction.dregypt.Models.ProductsModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.activities.DetailsProducts;
import com.example.gmsproduction.dregypt.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class UserProductAdapter extends RecyclerView.Adapter<UserProductAdapter.MyViewHolder> {
    final String basicImgUrl = "http://gms-sms.com:89";
    private Context mContext;
    private ArrayList<ProductsModel> mArrayList;
    private ArrayList<String> mArrayList1;
    private ArrayList<String> mArrayList2;
    int LastPosition = -1,userid;
    RecyclerViewClickListener ClickListener;
    String CheckStatus;
    String status;


    public UserProductAdapter() {
    }

    public UserProductAdapter(Context mContext, ArrayList<ProductsModel> mArrayList, ArrayList<String> mArrayList1, ArrayList<String> mArrayList2) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
        this.mArrayList1 = mArrayList1;
        this.mArrayList2 = mArrayList2;
    }

    public void setClickListener(RecyclerViewClickListener clickListener) {
        this.ClickListener = clickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_details, parent, false);
        SharedPreferences prefs = mContext.getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
        userid = prefs.getInt("User_id", 0);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ProductsModel currentItem = mArrayList.get(position);
        final String title= currentItem.getTitlez();
        String favcounts = mArrayList1.get(position);
        String viewcounts = mArrayList2.get(position);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailsProducts.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("arrz",mArrayList);
                intent.putExtras(bundle);
                intent.putExtra("position",position);

                mContext.startActivity(intent);
            }
        });

        holder.ProductTitle.setText(title);
        holder.ProductFav.setText(favcounts);
        holder.ProductNum.setText(String.valueOf(position+1));
        holder.ProductViews.setText(viewcounts);

    }


    @Override
    public int getItemCount() {
        if (mArrayList == null || mArrayList.size() == 0)
            return 0;
        return mArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView ProductTitle, ProductNum,ProductViews,ProductFav;
        ImageView ProductEdit,ProductDelete;
        LinearLayout cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ProductTitle = itemView.findViewById(R.id.List_title);
            ProductNum = itemView.findViewById(R.id.List_Num);
            ProductViews = itemView.findViewById(R.id.List_views);
            ProductFav = itemView.findViewById(R.id.List_Fav);
            cardView = itemView.findViewById(R.id.List_Click);
            ProductEdit = itemView.findViewById(R.id.List_edit);
            ProductDelete = itemView.findViewById(R.id.List_delete);



            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (ClickListener != null)
                ClickListener.ItemClicked(view, getAdapterPosition());
        }

        public void clearAnimation() {
            cardView.clearAnimation();
        }
    }

    public interface RecyclerViewClickListener {

        public void ItemClicked(View v, int position);
    }

    private void setAnimation(View viewToAnimate, int position) {

        if (position > LastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            LastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }


}

