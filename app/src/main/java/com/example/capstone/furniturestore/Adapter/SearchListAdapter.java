package com.example.capstone.furniturestore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.capstone.furniturestore.Models.Category;
import com.example.capstone.furniturestore.Models.Product;
import com.example.capstone.furniturestore.Models.SearchItems;
import com.example.capstone.furniturestore.ProductDetailActivity;
import com.example.capstone.furniturestore.ProductListActivity;
import com.example.capstone.furniturestore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tejalpatel on 2018-03-13.
 */

public class SearchListAdapter extends ArrayAdapter<Category> {
    public SearchListAdapter(Context context, ArrayList<Category> categories) {
        super(context, 0, categories);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Category category = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_list, parent, false);
        }
        // Lookup view for data population
        TextView catName = (TextView) convertView.findViewById(R.id.txtCategoryName);
        // Populate the data into the template view using the data object
        catName.setText(category.getCategoryName());




        // Return the completed view to render on screen
        return convertView;
    }
}



/*
*
    ArrayList<Category> categories = new ArrayList<Category>();
    Context ctx;

    public SearchListAdapter(ArrayList<Category> categories, Context ctx) {

        this.categories = (ArrayList<Category>) categories;
        this.ctx = ctx;
    }

    @Override
    public SearchListAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list, parent, false);
        SearchListAdapter.SearchViewHolder searchViewHolder = new SearchListAdapter.SearchViewHolder(view,ctx,categories);
        return searchViewHolder;
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        Category model = categories.get(position);
        holder.category_Name.setText(model.getCategoryName());
    }



    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

       public TextView category_Name;

        ArrayList<Category> categories = new ArrayList<Category>();
        Context ctx;
        public SearchViewHolder(View itemView,Context ctx,ArrayList<Category> categories) {
            super(itemView);
            this.categories = categories;
            this.ctx = ctx;
            itemView.setOnClickListener(this);
            category_Name = (TextView) itemView.findViewById(R.id.txtCategoryName);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Category category = this.categories.get(position);
            Intent intent = new Intent(ctx, ProductListActivity.class);
            intent.putExtra("CategoryID", category.getCategoryID());
            intent.putExtra("CategoryName", category.getCategoryName());
            ctx.startActivity(intent);
        }
    }
*/