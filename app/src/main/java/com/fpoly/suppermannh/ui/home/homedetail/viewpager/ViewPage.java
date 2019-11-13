package com.fpoly.suppermannh.ui.home.homedetail.viewpager;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.fpoly.suppermannh.model.Houst;


public class ViewPage extends FragmentPagerAdapter {
    Houst houst;

    public ViewPage(FragmentManager fm, Houst housts) {
        super(fm);
        this.houst = housts;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i){

            case 0:
                AccountDetailFragment accountDetailFragment = AccountDetailFragment.newInstance(houst);
                return accountDetailFragment;

            case 1:
                MarkerDetailFragment markerDetailFragment = MarkerDetailFragment.newInstance(houst);
                return markerDetailFragment;

                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
    private void loadAllFragment() {

    }
}
