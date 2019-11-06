package com.fpoly.suppermannh.ui.like;

import android.os.Bundle;

import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.base.BaseFragment;
import com.fpoly.suppermannh.ui.home.HomeFragment;

public class LikeFragment extends BaseFragment {


    public static LikeFragment newInstance() {
        Bundle args = new Bundle();
        LikeFragment fragment = new LikeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void addEvents() {

    }

    @Override
    protected void configViews() {

    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.like_fragment;
    }
}
