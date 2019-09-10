package com.example.capstone.furniturestore.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capstone.furniturestore.Models.Address;
import com.example.capstone.furniturestore.Models.Department;
import com.example.capstone.furniturestore.R;

import java.util.ArrayList;

/**
 * Created by mankirankaur on 2018-03-31.
 */

public class AddressViewHolder extends RecyclerView.ViewHolder {

    public TextView tvFullName;
    public TextView tvAddress;
    public TextView tvCity;
    public TextView tvState;
    public TextView tvPostalcode;
   // public TextView tvEditaddress;
    public ImageView tvImageEditaddress;

    //ArrayList<Address> arrayListaddress = new ArrayList<Address>();
    //Context ctx;

    public AddressViewHolder(View itemView){//, Context ctx, ArrayList<Address> arrayListaddress) {
        super(itemView);

        ///this.arrayListaddress = arrayListaddress;
        ///this.ctx = ctx;
        tvFullName =(TextView)itemView.findViewById(R.id.tvFullName);
        tvAddress =(TextView)itemView.findViewById(R.id.tvAddress);
        tvCity = (TextView)itemView.findViewById(R.id.tvCity);
        tvState = (TextView)itemView.findViewById(R.id.tvState);
        tvPostalcode = (TextView)itemView.findViewById(R.id.tvPostalcode);
        //tvEditaddress = (TextView)itemView.findViewById(R.id.text_Editaddress);
        tvImageEditaddress = (ImageView)itemView.findViewById(R.id.image_Editaddress);

    }
    /*
    public AddressViewHolder(View itemView, Context ctx, ArrayList<Address> arrayListaddress) {
        super(itemView);

        this.arrayListaddress = arrayListaddress;
        this.ctx = ctx;
        tvFullName =(TextView)itemView.findViewById(R.id.tvFullName);
        tvAddress =(TextView)itemView.findViewById(R.id.tvAddress);

    }
    */

}
