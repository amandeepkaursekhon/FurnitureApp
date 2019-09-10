package com.example.capstone.furniturestore.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.capstone.furniturestore.R;

/**
 * Created by amandeepsekhon on 2018-04-10.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtOrderId,txtOrderDate, txtOrderStatus , txtOrderName ,txtOrderPhone , txtOrderAddress ;

    private ProductViewHolder.ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView) {

        super(itemView);
        txtOrderId = (TextView) itemView.findViewById(R.id.order_id);
        txtOrderDate = (TextView) itemView.findViewById(R.id.order_date);
        txtOrderStatus = (TextView) itemView.findViewById(R.id.order_status);
        txtOrderName = (TextView) itemView.findViewById(R.id.order_name);
        txtOrderPhone = (TextView) itemView.findViewById(R.id.order_phone);
        txtOrderAddress = (TextView) itemView.findViewById(R.id.order_address);
        itemView.setOnClickListener(this);

    }

    public void setItemClickListener(ProductViewHolder.ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
      itemClickListener.onClickItem(view,getAdapterPosition(),false);

    }
}
