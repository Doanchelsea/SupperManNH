package com.fpoly.suppermannh.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.base.BaseActivity;
import com.fpoly.suppermannh.model.LoadingDialog;
import com.fpoly.suppermannh.model.local.AppPreferencesHelper;
import com.fpoly.suppermannh.model.local.DataManager;
import com.fpoly.suppermannh.ui.main.MainActivity;
import com.fpoly.suppermannh.ui.registration.RegistrationActivity;
import com.fpoly.suppermannh.untils.StringUtils;
import com.fpoly.suppermannh.untils.ValidateUtils;
import com.jakewharton.rxbinding3.view.RxView;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class LoginActivity extends BaseActivity implements Connectable, Disconnectable, Bindable,LoginContract {

    @BindView(R.id.edt_phone)
    EditText edtTaikhoan;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnRegistration)
    Button btnRegistration;
    private LoginPresenter presenter;
    private DataManager dataManager;
    private AppPreferencesHelper appPreferencesHelper;
    private SharedPreferences mPrefs;

    public static void startActivity(Activity context){
        context.startActivity(new Intent(context, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        context.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerBindable(this);
        registerConnectable(this);
        registerDisconnectable(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected Merlin initMerlin() {
        return new  Merlin.Builder().withConnectableCallbacks()
                .withBindableCallbacks()
                .withDisconnectableCallbacks()
                .build(this);
    }

    @Override
    protected void initData() {
        mPrefs = context.getSharedPreferences("", Context.MODE_PRIVATE);
        appPreferencesHelper = new AppPreferencesHelper(mPrefs,this);
        dataManager = new DataManager(appPreferencesHelper);
        presenter = new LoginPresenter(this,this,dataManager);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void addEvents() {
        addDisposable(RxView.clicks(btnLogin).throttleFirst(2, TimeUnit.SECONDS)
        .compose(bindToLifecycle()).subscribe(unit -> {
            String user = edtTaikhoan.getText().toString().trim();
            String pass = edtPassword.getText().toString().trim();
            if (StringUtils.isEmpty(user) || StringUtils.isEmpty(pass)){
                Toasty.warning(this,R.string.error_null).show();
            }else if (!ValidateUtils.isVaidFullName(user)){
                Toasty.warning(this,R.string.error_ky_tu).show();
            }else {
                showLoading(true);
                presenter.login(user,pass);
            }
        }));
        addDisposable(RxView.clicks(btnRegistration)
                .throttleFirst(2,TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
                    RegistrationActivity.startActivity(this);
                }));
    }

    @Override
    public void onBind(NetworkStatus networkStatus) {
        if (!networkStatus.isAvailable()) {
            onDisconnect();
        }else {
            onConnect();
        }
    }

    @Override
    public void onConnect() {
        
    }

    @Override
    public void onDisconnect() {

    }


    @Override
    public void ShowSuccer() {
        showLoading(false);
        MainActivity.startActivity(this);
    }

    @Override
    public void ShowError(int error) {
        showLoading(false);
        Toasty.error(this,error).show();
    }


    @Override
    public void showLoading(boolean show) {
        if (show){
            LoadingDialog.getInstance().showLoading(this);
        }else {
            LoadingDialog.getInstance().hideLoading();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
