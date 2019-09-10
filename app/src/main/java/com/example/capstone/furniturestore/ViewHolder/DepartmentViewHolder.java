package com.example.capstone.furniturestore.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capstone.furniturestore.CategoryActivity;
import com.example.capstone.furniturestore.Models.Department;
import com.example.capstone.furniturestore.R;

import java.util.ArrayList;

/**
 * Created by tejalpatel on 2018-03-18.
 */

public class DepartmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView department_Image;
    public TextView department_Name;

    private ItemClickListener mListener;

    public interface ItemClickListener {
        void onClickItem(int pos);
    }


    public DepartmentViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        department_Image = (ImageView) itemView.findViewById(R.id.image_department);
        department_Name = (TextView) itemView.findViewById(R.id.txt_departmentName);
    }

    public void setClickListener(ItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {

        mListener.onClickItem(getLayoutPosition());
    }
}
