package com.fpoly.suppermannh.untils;

import android.app.Activity;
import android.content.Context;

import androidx.viewpager.widget.ViewPager;

import java.util.TimerTask;

public class MyTimerTask extends TimerTask {

    private Activity context;
    private ViewPager viewPager;

    public MyTimerTask(Activity context, ViewPager viewPager) {
        this.context = context;
        this.viewPager = viewPager;
    }

    @Override
    public void run() {
        context.runOnUiThread(() -> {
            if(viewPager.getCurrentItem() == 0){
                viewPager.setCurrentItem(1);
            } else if(viewPager.getCurrentItem() == 1){
                viewPager.setCurrentItem(2);
            } else if (viewPager.getCurrentItem() == 2){
                viewPager.setCurrentItem(3);
            }else if (viewPager.getCurrentItem() == 3){
                viewPager.setCurrentItem(0);
            }
        });


    }
}
