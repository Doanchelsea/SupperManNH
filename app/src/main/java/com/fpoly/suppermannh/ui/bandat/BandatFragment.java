package com.fpoly.suppermannh.ui.bandat;

import android.os.Bundle;

import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.base.BaseFragment;
import com.fpoly.suppermannh.ui.account.AccountFragment;

public class BandatFragment extends BaseFragment {

    public static BandatFragment newInstance() {
        Bundle args = new Bundle();
        BandatFragment fragment = new BandatFragment();
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
        return R.layout.bandat_fragment;
    }
}
