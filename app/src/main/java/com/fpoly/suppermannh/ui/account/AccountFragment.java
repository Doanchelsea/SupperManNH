package com.fpoly.suppermannh.ui.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.api.Server;
import com.fpoly.suppermannh.base.BaseFragment;
import com.fpoly.suppermannh.model.Contract;
import com.fpoly.suppermannh.model.local.AppPreferencesHelper;
import com.fpoly.suppermannh.model.local.DataManager;
import com.fpoly.suppermannh.ui.account.detail.AccountDetailActivity;
import com.fpoly.suppermannh.ui.account.password.PasswordActivity;
import com.fpoly.suppermannh.ui.login.LoginActivity;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends BaseFragment {

    @Override
    public void onResume() {
        super.onResume();
        loadAvatar(Server.duongdananh+dataManager.getImage(),ivAvatar);
        loadFullName(dataManager.getName(),tvFullName);
    }

    private SharedPreferences mPrefs;
    private AppPreferencesHelper appPreferencesHelper;
    private DataManager dataManager;

    @BindView(R.id.fragment_account_iv_avatar_driver)
    CircleImageView ivAvatar;
    @BindView(R.id.fragment_account_tv_full_name_driver)
    TextView tvFullName;
    @BindView(R.id.account_container_bill)
    ConstraintLayout account_history;
    @BindView(R.id.fragment_account_container_profile)
    ConstraintLayout container_thongtin;
    @BindView(R.id.fragment_account_container_language)
    ConstraintLayout container_logout;
    @BindView(R.id.account_container_contact)
    ConstraintLayout container_lienhe;

    public static AccountFragment newInstance() {
        Bundle args = new Bundle();
        AccountFragment fragment = new AccountFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void addEvents() {
        addDisposable(RxView.clicks(account_history)
                .throttleFirst(2, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
//                    HistoryActivity.startActivity(activity);
                }));

        addDisposable(RxView.clicks(container_lienhe)
                .throttleFirst(2, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
                    PasswordActivity.startActivity(activity);
                }));
        addDisposable(RxView.clicks(container_thongtin)
                .throttleFirst(2, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
                    AccountDetailActivity.startActivity(activity);
                }));
        addDisposable(RxView.clicks(container_logout)
                .throttleFirst(2, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
                    dataManager.clearAllUserInfo();
                    LoginActivity.startActivity(activity);
                }));
    }

    @Override
    protected void configViews() {

    }

    @Override
    protected void initDatas() {
        mPrefs = context.getSharedPreferences("", Context.MODE_PRIVATE);
        appPreferencesHelper = new AppPreferencesHelper(mPrefs,activity);
        dataManager = new DataManager(appPreferencesHelper);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.accout_fragment;
    }
}
