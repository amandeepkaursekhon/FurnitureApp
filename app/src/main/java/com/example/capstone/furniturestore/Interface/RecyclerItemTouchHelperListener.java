package com.example.capstone.furniturestore.Interface;

import android.support.v7.widget.RecyclerView;

/**
 * Created by amandeepsekhon on 2018-04-07.
 */

public interface RecyclerItemTouchHelperListener {
    void onSwiped(RecyclerView.ViewHolder viewHolder,int direction, int position );
}
