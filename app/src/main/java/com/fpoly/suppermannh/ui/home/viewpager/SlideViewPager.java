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
import com.makeramen.roundedimageview.RoundedImageView;

public class SlideViewPager extends PagerAdapter {
    private Context context;
    private Integer[] images = {
            R.drawable.anhnh1,
            R.drawable.nhahang1,
            R.drawable.nhahang2,
            R.drawable.nhahang3,
            R.drawable.nhahang4,
            R.drawable.nhahang5,
            R.drawable.nhahang6};

    public SlideViewPager(Context context) {
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
            if(position == 0){
                Toast.makeText(context, "Slide 1 Clicked", Toast.LENGTH_SHORT).show();
            } else if(position == 1){
                Toast.makeText(context, "Slide 2 Clicked", Toast.LENGTH_SHORT).show();
            } else if (position ==2){
                Toast.makeText(context, "Slide 3 Clicked", Toast.LENGTH_SHORT).show();
            }else if (position == 3){
                Toast.makeText(context, "Slide 4 Clicked", Toast.LENGTH_SHORT).show();
            }else if (position == 4){
                Toast.makeText(context, "Slide 5 Clicked", Toast.LENGTH_SHORT).show();
            }else if (position == 5){
                Toast.makeText(context, "Slide 6 Clicked", Toast.LENGTH_SHORT).show();
            }else if (position == 6){
                Toast.makeText(context, "Slide 7 Clicked", Toast.LENGTH_SHORT).show();
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
