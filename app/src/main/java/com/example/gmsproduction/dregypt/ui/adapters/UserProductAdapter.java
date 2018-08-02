package com.example.gmsproduction.dregypt.ui.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.ProductAdsRequests.DeleteUserProductRequest;
import com.example.gmsproduction.dregypt.Models.PhoneModel;
import com.example.gmsproduction.dregypt.Models.ProductsModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.activities.AddItemActivity;
import com.example.gmsproduction.dregypt.ui.activities.DetailsProducts;
import com.example.gmsproduction.dregypt.ui.fragments.AddItems.Products.EditProductTry;
import com.example.gmsproduction.dregypt.ui.fragments.AddItems.Products.UserProductsListFragment;
import com.example.gmsproduction.dregypt.utils.Constants;
import com.example.gmsproduction.dregypt.utils.Utils;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class UserProductAdapter extends RecyclerView.Adapter<UserProductAdapter.MyViewHolder> {
    final String basicImgUrl = "http://gms-sms.com:89";
    private Context mContext;
    private ArrayList<ProductsModel> mArrayList;
    private ArrayList<String> mArrayList1;
    private ArrayList<String> mArrayList2;
    private ArrayList<PhoneModel>phoneArrayList;
    int LastPosition = -1, userid;
    RecyclerViewClickListener ClickListener;
    String CheckStatus;
    String status;


    public UserProductAdapter() {
    }

    public UserProductAdapter(Context mContext, ArrayList<ProductsModel> mArrayList, ArrayList<String> mArrayList1, ArrayList<String> mArrayList2, ArrayList<PhoneModel> phoneArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
        this.mArrayList1 = mArrayList1;
        this.mArrayList2 = mArrayList2;
        this.phoneArrayList = phoneArrayList;
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
        PhoneModel currentPhone = phoneArrayList.get(position);
        final String title = currentItem.getTitlez();
        final int ProductID = Integer.valueOf(currentItem.getIdz());
        String favcounts = mArrayList1.get(position);
        String viewcounts = mArrayList2.get(position);

        final String price = currentItem.getPrice();
        final String Desc = currentItem.getDescription();

        final String phone_id1 = currentPhone.getId01();
        final String phone1 = currentPhone.getNum01();
        final String phone_id2 = currentPhone.getId02();
        final String phone2 = currentPhone.getNum02();

        final String Addres = currentItem.getAddress();

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailsProducts.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("arrz", mArrayList);
                intent.putExtras(bundle);
                intent.putExtra("position", position);

                mContext.startActivity(intent);
            }
        });

        holder.ProductTitle.setText(title);
        holder.ProductFav.setText(favcounts);
        holder.ProductNum.setText(String.valueOf(position + 1));
        holder.ProductViews.setText(viewcounts);

        //edit
        holder.ProductEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductFragment(title,price,Desc,phone_id1,phone1,phone_id2,phone2,Addres,ProductID);

            }
        });

        //delete
        holder.ProductDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(mContext)
                        .setTitle("Delete Product")
                        .setMessage("Do you really want to delete this?")
                        .setIcon(android.R.drawable.ic_delete)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                DeleteUserProductRequest delete = new DeleteUserProductRequest(mContext, userid, ProductID, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.e("deletePro",response);
                                        MyProductFragment();
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                });
                                delete.start();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        if (mArrayList == null || mArrayList.size() == 0)
            return 0;
        return mArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView ProductTitle, ProductNum, ProductViews, ProductFav;
        ImageView ProductEdit, ProductDelete;
        LinearLayout cardView,RowLiner;

        public MyViewHolder(View itemView) {
            super(itemView);
            ProductTitle = itemView.findViewById(R.id.List_title);
            ProductNum = itemView.findViewById(R.id.List_Num);
            ProductViews = itemView.findViewById(R.id.List_views);
            ProductFav = itemView.findViewById(R.id.List_Fav);
            cardView = itemView.findViewById(R.id.List_Click);
            ProductEdit = itemView.findViewById(R.id.List_edit);
            ProductDelete = itemView.findViewById(R.id.List_delete);
            RowLiner = itemView.findViewById(R.id.List_linear);
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

    public void MyProductFragment() {
        UserProductsListFragment fragment = new UserProductsListFragment();
        Bundle arguments = new Bundle();
        arguments.putInt("duck", 55);
        fragment.setArguments(arguments);
        final android.support.v4.app.FragmentTransaction ft = ((AddItemActivity)mContext).getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.additem_Include, fragment, Utils.UserProducts);
        ft.commit();
    }
    public void ProductFragment(String title,String price,String Desc,String phID1,String phone1,String phID2,String phone2 , String Addres ,int ProductID) {
        EditProductTry fragment = new EditProductTry();
        Bundle arguments = new Bundle();
        arguments.putInt("Edit", 55);
        arguments.putInt("ProductID", ProductID);
        arguments.putString("title",title);
        arguments.putString("price",price);
        arguments.putString("Desc",Desc);
        arguments.putString("phone1",phone1);
        arguments.putString("phone2",phone2);
        arguments.putString("phoneID1",phID1);
        arguments.putString("phoneID2",phID2);
        arguments.putString("Addres",Addres);
        arguments.putString("Img","null");


        fragment.setArguments(arguments);
        final android.support.v4.app.FragmentTransaction ft = ((AddItemActivity)mContext).getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.additem_Include, fragment, Utils.addproduct);
        ft.addToBackStack(null);
        ft.commit();
    }
}

