package com.fpoly.suppermannh.dialog;

import android.os.Bundle;
import android.view.Window;

import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.base.BaseDiglog;
import com.fpoly.suppermannh.ui.online.dialog.PeopleDiglog;

public class DialogDisconnect extends BaseDiglog {

    public static DialogDisconnect newInstance() {
        Bundle args = new Bundle();
        DialogDisconnect fragment = new DialogDisconnect();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_disconnect;
    }

    @Override
    protected void initDialog() {
        Window window = getDialog().getWindow();
        setTransparentDialog(window);
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
}
