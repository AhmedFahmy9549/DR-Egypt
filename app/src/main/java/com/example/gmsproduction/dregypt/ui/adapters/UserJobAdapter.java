package com.example.gmsproduction.dregypt.ui.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.JobAdsRequests.DeleteUserJobRequest;
import com.example.gmsproduction.dregypt.Data.remoteDataSource.NetworkRequests.ProductAdsRequests.DeleteUserProductRequest;
import com.example.gmsproduction.dregypt.Models.JobsModel;
import com.example.gmsproduction.dregypt.Models.PhoneModel;
import com.example.gmsproduction.dregypt.Models.ProductsModel;
import com.example.gmsproduction.dregypt.R;
import com.example.gmsproduction.dregypt.ui.activities.AddItemActivity;
import com.example.gmsproduction.dregypt.ui.activities.DetailsJobs;
import com.example.gmsproduction.dregypt.ui.fragments.AddItems.AddJobFragment;
import com.example.gmsproduction.dregypt.ui.fragments.AddItems.UserJobsListFragment;
import com.example.gmsproduction.dregypt.utils.Constants;
import com.example.gmsproduction.dregypt.utils.Utils;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class UserJobAdapter extends RecyclerView.Adapter<UserJobAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<JobsModel> mArrayList;
    private ArrayList<String>mArrayList1,mArrayList2;
    private ArrayList<PhoneModel>phoneArrayList;

    private int userid;

    public UserJobAdapter(Context mContext, ArrayList<JobsModel> mArrayList, ArrayList<String> mArrayList1, ArrayList<String> mArrayList2, ArrayList<PhoneModel> phoneArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
        this.mArrayList1 = mArrayList1;
        this.mArrayList2 = mArrayList2;
        this.phoneArrayList = phoneArrayList;
    }

    public UserJobAdapter(Context mContext, ArrayList<JobsModel> mArrayList, ArrayList<String> mArrayList1, ArrayList<String> mArrayList2) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
        this.mArrayList1 = mArrayList1;
        this.mArrayList2 = mArrayList2;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_details, parent, false);
        SharedPreferences prefs = mContext.getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
        userid = prefs.getInt("User_id", 0);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final JobsModel currentItem = mArrayList.get(position);
        PhoneModel currentPhone = phoneArrayList.get(position);

        final int id = Integer.valueOf(currentItem.getId());
        final String title = currentItem.getTitle();
        final String description = currentItem.getDescription();
        final String Salary = currentItem.getSalary();
        final String address = currentItem.getAddress();

        //phone
        final String phone_id1 = currentPhone.getId01();
        final String phone1 = currentPhone.getNum01();
        final String phone_id2 = currentPhone.getId02();
        final String phone2 = currentPhone.getNum02();

        String favcounts = mArrayList1.get(position);
        String viewcounts = mArrayList2.get(position);



        holder.ProductTitle.setText(title);
        holder.ProductFav.setText(favcounts);
        holder.ProductNum.setText(String.valueOf(position + 1));
        holder.ProductViews.setText(viewcounts);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailsJobs.class);
                intent.putExtra("JDTitle",currentItem.getTitle());
                intent.putExtra("JDID",currentItem.getId());
                intent.putExtra("JDdescription",currentItem.getDescription());
                intent.putExtra("JDsalary",currentItem.getSalary());
                intent.putExtra("JDimage",currentItem.getImage());
                intent.putExtra("JDaddress",currentItem.getAddress());
                intent.putExtra("JDcreated_at",currentItem.getCreated_at());
                intent.putExtra("JDphone_1",phone1);
                intent.putExtra("JDphone_2",phone2);
                intent.putExtra("JDexperience",currentItem.getExperience());
                intent.putExtra("JDeducation_level",currentItem.getEducation_level());
                intent.putExtra("JDemployment_type",currentItem.getEmployment_type());
                mContext.startActivity(intent);

            }
        });


        //edit
        holder.ProductEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobFragment(title,Salary,description,phone_id1,phone1,phone_id2,phone2,address,id);

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
                                DeleteUserJobRequest delete = new DeleteUserJobRequest(mContext, userid, id, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.e("deletePro",response);
                                        MyJobsFragment();
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

    class MyViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener */ {

        TextView ProductTitle, ProductNum, ProductViews, ProductFav;
        ImageView ProductEdit, ProductDelete;
        LinearLayout cardView,RowLiner;


        public MyViewHolder(View itemView) {
            super(itemView);
            // itemView.setOnClickListener(this);

            ProductTitle = itemView.findViewById(R.id.List_title);
            ProductNum = itemView.findViewById(R.id.List_Num);
            ProductViews = itemView.findViewById(R.id.List_views);
            ProductFav = itemView.findViewById(R.id.List_Fav);
            cardView = itemView.findViewById(R.id.List_Click);
            ProductEdit = itemView.findViewById(R.id.List_edit);
            ProductDelete = itemView.findViewById(R.id.List_delete);
            RowLiner = itemView.findViewById(R.id.List_linear);
        }

    }

    public void JobFragment(String title,String Salary,String Desc,String phID1,String phone1,String phID2,String phone2 , String Addres,int id) {
        AddJobFragment fragment = new AddJobFragment();
        Bundle arguments = new Bundle();
        arguments.putInt("Edit", 55);
        arguments.putInt("JobID", id);
        arguments.putString("title",title);
        arguments.putString("salary",Salary);
        arguments.putString("Desc",Desc);
        arguments.putString("phone1",phone1);
        arguments.putString("phone2",phone2);
        arguments.putString("phoneID1",phID1);
        arguments.putString("phoneID2",phID2);
        arguments.putString("Addres",Addres);
        arguments.putString("Img","null");
        fragment.setArguments(arguments);
        final android.support.v4.app.FragmentTransaction ft = ((AddItemActivity)mContext).getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.additem_Include, fragment, Utils.addJobs);
        ft.commit();
    }
    public void MyJobsFragment() {
        UserJobsListFragment fragment = new UserJobsListFragment();
        Bundle arguments = new Bundle();
        arguments.putInt("duck", 55);
        fragment.setArguments(arguments);
        final android.support.v4.app.FragmentTransaction ft = ((AddItemActivity)mContext).getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.additem_Include, fragment, Utils.UserJobs);
        ft.addToBackStack(null);
        ft.commit();
    }
}

