package com.example.capstone.furniturestore.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capstone.furniturestore.R;

/**
 * Created by tejalpatel on 2018-03-13.
 */

public class SearchListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public ImageView Category_Image;
    public TextView Category_Name;

    private ItemClickListener mListener;

    public interface ItemClickListener {
        void onClickItem(int pos);
    }



    public SearchListViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        Category_Name = (TextView) itemView.findViewById(R.id.txtCategoryName);
    }

    public void setClickListener(ItemClickListener listener) {
        this.mListener = listener;
    }




    @Override
    public void onClick(View v) {
        mListener.onClickItem(getLayoutPosition());
    }
}
