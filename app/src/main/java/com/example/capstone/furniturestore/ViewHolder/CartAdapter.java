package com.example.capstone.furniturestore.ViewHolder;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.capstone.furniturestore.Cart;
import com.example.capstone.furniturestore.Database.Database;
import com.example.capstone.furniturestore.Models.Product;
import com.example.capstone.furniturestore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by amandeepsekhon on 2018-03-28.
 */




public class CartAdapter extends RecyclerView.Adapter<CartViewHolder>{


    private List<Product> listData = new ArrayList<>();
    private Cart cart;

    public CartAdapter(List<Product> listData, Cart cart) {
        this.listData = listData;
        this.cart = cart;
        checkList(listData);
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(cart);
        View itemView = inflater.inflate(R.layout.cart_layout,parent,false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder,final int position) {
        Picasso.with(cart)
                .load(listData.get(position).getProductImage())
                .resize(70,70)
                .centerCrop()
                .into(holder.cart_image);


holder.btn_quantity.setNumber(listData.get(position).getProductQunt());
      holder.btn_quantity.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
          @Override
          public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
              Product order = listData.get(position);
              order.setProductQunt(String.valueOf(newValue));
              new Database(cart).updateCart(order);

              float total = 0;
                      //Do something after 100ms
                      List<Product> orders = new Database(cart).getCarts();
                      for(Product item :listData)
                          total +=(Float.parseFloat(item.getProductPricenew()))*(Integer.parseInt(item.getProductQunt()));
                      cart.txtTotalPrice.setText("$"+total+"");
          }

      });
        holder.txt_price.setText(listData.get(position).getProductPricenew());


        holder.txt_cart_name.setText(listData.get(position).getProductName());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

public Product getItem(int position)
{
    return listData.get(position);
}


    public void checkList(List<Product> listDa){
        if(listDa.size()==0){
            Toast.makeText(cart,"Cart is Empty",Toast.LENGTH_SHORT).show();
        }
    }

    public void removeItem(int position)
    {
        listData.remove(position);
        notifyItemRemoved(position);
    }
    public void restoreItem(Product item, int position)
    {
        listData.add(position,item);
       notifyItemInserted(position);
    }


}
