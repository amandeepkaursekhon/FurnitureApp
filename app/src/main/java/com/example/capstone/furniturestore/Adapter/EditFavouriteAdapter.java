package com.example.capstone.furniturestore.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.furniturestore.Models.Favourite;
import com.example.capstone.furniturestore.Models.Product;
import com.example.capstone.furniturestore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by tejalpatel on 2018-04-07.
 */

public class EditFavouriteAdapter extends RecyclerView.Adapter<EditFavouriteAdapter.EditFavouriteViewHolder> {

    ArrayList<Product> products = new ArrayList<Product>();
    Context ctx;
    List<String> SelectedProductID = new ArrayList<>();
    ArrayList<Favourite> FavProductID = new ArrayList<Favourite>();

    public EditFavouriteAdapter(ArrayList<Product> products, Context ctx, List<String> selectedProductID, ArrayList<Favourite> favProductID) {

        this.products = (ArrayList<Product>) products;
        notifyDataSetChanged();
        this.ctx = ctx;
        this.SelectedProductID = selectedProductID;
        this.FavProductID = favProductID;
    }



    @Override
    public EditFavouriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = parent;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_favourite_layout, parent, false);


        EditFavouriteAdapter.EditFavouriteViewHolder favouriteViewHolder = new EditFavouriteAdapter.EditFavouriteViewHolder(view,ctx,products,SelectedProductID, FavProductID);
        return favouriteViewHolder;
    }

    @Override
    public void onBindViewHolder(EditFavouriteViewHolder holder, int position) {
        Product model = holder.products.get(position);
        Favourite fav_model =  holder.favourites.get(position);

        Picasso.with(ctx).load(model.getProductImage()).into(holder.product_Image);
        holder.product_Name.setText(model.getProductName());
        holder.product_Manufacturer.setText(model.getProductManufacturer());
        holder.product_Sale_Price.setText("$"+String.valueOf( model.getProductSalePrice()));
        holder.product_Price.setText("$"+String.valueOf(model.getProductPrice()));
        holder.product_Price.setPaintFlags(holder.product_Price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.favourite_title.setText(fav_model.getFavorite_title());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class EditFavouriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView product_Image;
        public TextView product_Name;
        public TextView product_Manufacturer;
        public TextView product_Sale_Price;
        public TextView product_Price;
        public TextView product_Shipping;
        public TextView product_saleLimit;
        public TextView favourite_title;

        List<String> SelectedProductID = new ArrayList<>();

        public CheckBox chk_editFavourite;
        public Button btn_Delete;

        ArrayList<Product> products = new ArrayList<Product>();
        ArrayList<Favourite> favourites = new ArrayList<Favourite>();

        Context ctx;

        int i = 0;

        public EditFavouriteViewHolder(View itemView,Context ctx,ArrayList<Product> products,List<String> selectedProductID, ArrayList<Favourite> favourites) {
            super(itemView);
            this.products = products;


            this.favourites = favourites;
            this.ctx = ctx;
            this.SelectedProductID = selectedProductID;
            itemView.setOnClickListener(this);
            product_Image = (ImageView) itemView.findViewById(R.id.image_product);
            product_Name = (TextView) itemView.findViewById(R.id.txt_productName);
            product_Manufacturer = (TextView) itemView.findViewById(R.id.txt_productManufacturer);
            product_Sale_Price = (TextView) itemView.findViewById(R.id.txt_productSalePrice);
            product_Price = (TextView) itemView.findViewById(R.id.txt_productPrice);
            product_Shipping = (TextView) itemView.findViewById(R.id.txt_productshipping);
            product_saleLimit = (TextView) itemView.findViewById(R.id.txtSaleTag);
            favourite_title = (TextView) itemView.findViewById(R.id.txt_favTitle);
            chk_editFavourite = (CheckBox) itemView.findViewById(R.id.check_editFavourite);


        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Favourite favourite = this.favourites.get(position);

            if(chk_editFavourite.isChecked() == true)
            {chk_editFavourite.setChecked(false);
            int index = SelectedProductID.indexOf(favourite.getFavorite_ID());
                SelectedProductID.remove(index);

            }
            else {
            chk_editFavourite.setChecked(true);

            SelectedProductID.add(favourite.getFavorite_ID());
           // i++;
            }
        }
    }
}

