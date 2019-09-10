package com.example.capstone.furniturestore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capstone.furniturestore.Models.Product;
import com.example.capstone.furniturestore.ProductDetailActivity;
import com.example.capstone.furniturestore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by tejalpatel on 2018-04-17.
 */

public class ProductFilterAdapter extends RecyclerView.Adapter<ProductFilterAdapter.ProductFilterViewHolder> {

    ArrayList<Product> products = new ArrayList<Product>();
    Context ctx;


    public ProductFilterAdapter(ArrayList<Product> products, Context ctx) {

        this.products = (ArrayList<Product>) products;
        this.ctx = ctx;

    }

    @Override
    public ProductFilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = parent;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false);

        ProductFilterViewHolder favouriteViewHolder = new ProductFilterViewHolder(view,ctx,products);
        return favouriteViewHolder;
    }

    @Override
    public void onBindViewHolder(ProductFilterViewHolder holder, int position) {
        Product model = products.get(position);

        Picasso.with(ctx).load(model.getProductImage()).into(holder.product_Image);
        holder.product_Name.setText(model.getProductName());
        holder.product_Manufacturer.setText(model.getProductManufacturer());
        holder.product_Sale_Price.setText("$"+String.valueOf( model.getProductSalePrice()));
        holder.product_Price.setText("$"+String.valueOf(model.getProductPrice()));
        holder.product_Price.setPaintFlags(holder.product_Price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        if( model.getProductSaleLimit() > 0) {
            holder.product_saleLimit.setVisibility(View.VISIBLE);
            holder.product_saleLimit.setText(" " + model.getProductSaleLimit() + " off");
        }

        Double price = model.getProductSalePrice();
        if (price>=75.0){
            holder.product_Shipping.setText("Free Shipping");
        }
        else {
            holder.product_Shipping.setText(" ");
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductFilterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView product_Image;
        public TextView product_Name;
        public TextView product_Manufacturer;
        public TextView product_Sale_Price;
        public TextView product_Price;
        public TextView product_Shipping;
        public TextView product_saleLimit;

        public CheckBox chk_editFavourite;

        ArrayList<Product> products = new ArrayList<Product>();
        Context ctx;

        public ProductFilterViewHolder(View itemView,Context ctx,ArrayList<Product> products) {
            super(itemView);
            this.products = products;
            this.ctx = ctx;
            itemView.setOnClickListener(this);
            product_Image = (ImageView) itemView.findViewById(R.id.image_product);
            product_Name = (TextView) itemView.findViewById(R.id.txt_productName);
            product_Manufacturer = (TextView) itemView.findViewById(R.id.txt_productManufacturer);
            product_Sale_Price = (TextView) itemView.findViewById(R.id.txt_productSalePrice);
            product_Price = (TextView) itemView.findViewById(R.id.txt_productPrice);
            product_Shipping = (TextView) itemView.findViewById(R.id.txt_productshipping);
            product_saleLimit = (TextView) itemView.findViewById(R.id.txtSaleTag);



        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Product product = this.products.get(position);

            Intent intent = new Intent(ctx, ProductDetailActivity.class);
            intent.putExtra("ProductID", product.getProductID());
            ctx.startActivity(intent);
        }
    }
}
