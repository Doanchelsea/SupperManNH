package com.fpoly.suppermannh.ui.home.viewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.lisenner.HomeLisenner;
import com.fpoly.suppermannh.model.Houst;
import com.fpoly.suppermannh.untils.StringUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class SlideViewPager extends PagerAdapter {
    private HomeLisenner homeLisenner;
    private List<Houst> houst;
    private Context context;

    private Integer[] images = {
            R.drawable.anhnh1,
            R.drawable.nhahang1,
            R.drawable.nhahang2,
            R.drawable.nhahang3,};

    public SlideViewPager(HomeLisenner homeLisenner, List<Houst> houst, Context context) {
        this.homeLisenner = homeLisenner;
        this.houst = houst;
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.cutom_layout,container,false);
        ImageView imageView = view.findViewById(R.id.image_cutom_layout_home);
        imageView.setImageResource(images[position]);
        view.setOnClickListener(view1 -> {
            if (houst.size() == 0){
                return;
            }else {
                if(position == 0){
                    homeLisenner.onClick(houst.get(0));
                } else if(position == 1){
                    homeLisenner.onClick(houst.get(1));
                } else if (position ==2){
                    homeLisenner.onClick(houst.get(2));
                }else if (position == 3){
                    homeLisenner.onClick(houst.get(3));
                }
            }

        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
