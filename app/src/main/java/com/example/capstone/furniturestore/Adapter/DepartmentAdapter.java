package com.example.capstone.furniturestore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capstone.furniturestore.CategoryActivity;
import com.example.capstone.furniturestore.Models.Department;
import com.example.capstone.furniturestore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.crypto.spec.DESedeKeySpec;

/**
 * Created by tejalpatel on 2018-03-18.
 */

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.DepartmentViewHolder> {

    ArrayList<Department> departments = new ArrayList<Department>();
    Context ctx;

    public DepartmentAdapter(ArrayList<Department> departments, Context ctx) {

        this.departments = departments;
        this.ctx = ctx;
    }

    @Override
    public DepartmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.department_layout, parent, false);
        DepartmentViewHolder departmentViewHolder = new DepartmentViewHolder(view,ctx,departments);

        return departmentViewHolder;
    }

    @Override
    public void onBindViewHolder(DepartmentViewHolder holder, int position) {
        Department department = departments.get(position);
      //  holder.department_Image.setImageResource(department.getDepartmentImage());
        Picasso.with(ctx).load(department.getDepartmentImage()).into(holder.department_Image);

        holder.department_Name.setText(department.getDepartmentName());
    }

    @Override
    public int getItemCount() {
        return departments.size();
    }

    public static class DepartmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView department_Image;
        TextView department_Name;
        ArrayList<Department> departments = new ArrayList<Department>();
        Context ctx;
        public DepartmentViewHolder(View itemView,Context ctx,ArrayList<Department> departments) {
            super(itemView);
            this.departments = departments;
            this.ctx = ctx;
            itemView.setOnClickListener(this);
            department_Image = (ImageView) itemView.findViewById(R.id.image_department);
            department_Name = (TextView) itemView.findViewById(R.id.txt_departmentName);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            Department department = this.departments.get(position);
            Intent intent = new Intent(ctx, CategoryActivity.class);
            intent.putExtra("DeptID", department.getDepartmentID());
            ctx.startActivity(intent);
        }
    }
}
