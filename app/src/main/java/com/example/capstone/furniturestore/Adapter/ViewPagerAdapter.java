package com.example.capstone.furniturestore.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.capstone.furniturestore.R;

/**
 * Created by tejalpatel on 2018-03-06.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private Context contex;
    private LayoutInflater layoutiflater;
    private Integer [] image = {R.drawable.homeimage1,R.drawable.homeimage2,R.drawable.homeimage3,R.drawable.homeimage4};

    public ViewPagerAdapter(Context contex) {
        this.contex = contex;
    }

    @Override
    public int getCount() {
        return image.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutiflater = (LayoutInflater) contex.getSystemService(contex.LAYOUT_INFLATER_SERVICE);
        View view = layoutiflater.inflate(R.layout.costumpager, null);
        ImageView img_home = (ImageView) view.findViewById(R.id.Homepage_Items);
        img_home.setImageResource(image[position]);
        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
