package com.example.capstone.furniturestore.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capstone.furniturestore.R;

/**
 * Created by tejalpatel on 2018-03-19.
 */

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView product_Image;
    public TextView product_Name;
    public TextView product_Manufacturer;
    public TextView product_Sale_Price;
    public TextView product_Price;
    public TextView product_Shipping;
    public TextView product_saleLimit;
    public TextView product_saleEndDate;

    private ItemClickListener mListener;

    public interface ItemClickListener {
        void onClickItem(View view, int pos, boolean b);

        void onClick(View view, int adapterPosition, boolean b);
    }


    public ProductViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        product_Image = (ImageView) itemView.findViewById(R.id.image_product);
        product_Name = (TextView) itemView.findViewById(R.id.txt_productName);
        product_Manufacturer = (TextView) itemView.findViewById(R.id.txt_productManufacturer);
        product_Sale_Price = (TextView) itemView.findViewById(R.id.txt_productSalePrice);
        product_Price = (TextView) itemView.findViewById(R.id.txt_productPrice);
        product_Shipping = (TextView) itemView.findViewById(R.id.txt_productshipping);
        product_saleLimit = (TextView) itemView.findViewById(R.id.txtSaleTag);
        product_saleEndDate = (TextView) itemView.findViewById(R.id.txt_saleEndDate);
    }

    public void setClickListener(ItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View view) {

        mListener.onClickItem(view, getLayoutPosition(), false);
    }
}
