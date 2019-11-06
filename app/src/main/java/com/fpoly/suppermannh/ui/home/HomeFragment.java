package com.fpoly.suppermannh.ui.home;

import android.os.Bundle;

import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.base.BaseFragment;
import com.fpoly.suppermannh.ui.account.AccountFragment;

public class HomeFragment extends BaseFragment {

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
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
        return R.layout.home_fragment;
    }
}
