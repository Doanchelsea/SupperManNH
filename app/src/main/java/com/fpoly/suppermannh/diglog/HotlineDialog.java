package com.fpoly.suppermannh.diglog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.base.BaseDiglog;
import com.fpoly.suppermannh.untils.StringUtils;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

/**
 * doannd ==> ok
 * <p>
 * hotline dialog
 */
public class HotlineDialog extends BaseDiglog {

    private static final int DURATION_DELAY_CLICK_BUTTON = 2;
    private static final String EXTRA_PHONE_NUMBER = "EXTRA_PHONE_NUMBER";

    @BindView(R.id.dialog_hotline_btn_call)
    Button btnCall;
    @BindView(R.id.dialog_hotline_btn_cancel)
    Button btnCancel;
    @BindView(R.id.dialog_hotline_tv_phone_number)
    TextView tvPhoneNumber;

    private String phoneNumber;

    public static HotlineDialog newInstance(String phoneNumber) {
        Bundle args = new Bundle();
        HotlineDialog fragment = new HotlineDialog();
        args.putString(EXTRA_PHONE_NUMBER, phoneNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_hotline;
    }

    @Override
    protected void initDatas() {
        if (getArguments() == null) {
            return;
        }
        phoneNumber = getArguments().getString(EXTRA_PHONE_NUMBER);
    }

    @Override
    protected void configViews() {
        if (StringUtils.isEmpty(phoneNumber)) {
            return;
        }
        tvPhoneNumber.setText(phoneNumber);
    }

    @Override
    protected void initDialog() {
        Window window = getDialog().getWindow();
        if (window == null) return;
        setTransparentDialog(window);
    }

    @Override
    protected void addEvents() {
        addDisposable(RxView.clicks(btnCancel)
                .throttleFirst(DURATION_DELAY_CLICK_BUTTON, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(aVoid -> hideDialog()));

        addDisposable(RxView.clicks(btnCall)
                .throttleFirst(DURATION_DELAY_CLICK_BUTTON, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(aVoid -> {
                    Uri link = Uri.parse("tel: " + phoneNumber);
                    activity.startActivity(new Intent(Intent.ACTION_DIAL, link));
                    hideDialog();
                }));
    }
}
