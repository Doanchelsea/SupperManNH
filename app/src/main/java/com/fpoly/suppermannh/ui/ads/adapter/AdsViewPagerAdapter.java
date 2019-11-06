package com.fpoly.suppermannh.ui.ads.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.fpoly.suppermannh.R;

public class AdsViewPagerAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private Integer [] images = {R.layout.layout1,R.layout.layout2,R.layout.layout3,R.layout.layout4};

    public AdsViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(images[position], container, false);
        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
