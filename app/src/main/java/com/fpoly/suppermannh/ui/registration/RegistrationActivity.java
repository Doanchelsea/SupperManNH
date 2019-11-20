package com.fpoly.suppermannh.ui.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.base.BaseActivity;
import com.fpoly.suppermannh.model.LoadingDialog;
import com.fpoly.suppermannh.untils.SoUtils;
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

public class RegistrationActivity extends BaseActivity implements Connectable, Bindable, Disconnectable,RegistrationContract {

    @BindView(R.id.activity_ed_registration)
    EditText activity_ed_registration;
    @BindView(R.id.activity_ed_phone_registration)
    EditText activity_ed_phone_registration;
    @BindView(R.id.activity_ed_password_registration)
    EditText activity_ed_password_registration;
    @BindView(R.id.activity_ed_info_password_registration)
    EditText activity_ed_info_password_registration;
    @BindView(R.id.btn_confirm_registration)
    Button btn_confirm_registration;

    private RegistrationPresenter presenter;



    public static void startActivity(Activity context){
        context.startActivity(new Intent(context,RegistrationActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
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
        return R.layout.activity_registration;
    }

    @Override
    protected Merlin initMerlin() {
        return new Merlin.Builder()
                .withConnectableCallbacks()
                .withBindableCallbacks()
                .withDisconnectableCallbacks()
                .build(this);
    }

    @Override
    protected void initData() {
        presenter = new RegistrationPresenter(this,this);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void addEvents() {
        addDisposable(RxView.clicks(btn_confirm_registration)
                .throttleFirst(1, TimeUnit.SECONDS)
                .compose(bindToLifecycle()).subscribe(unit -> {
                    String user = activity_ed_registration.getText().toString().trim();
                    String phone = activity_ed_phone_registration.getText().toString().trim();
                    String pass = activity_ed_password_registration.getText().toString().trim();
                    String infopass = activity_ed_info_password_registration.getText().toString().trim();

                    if (StringUtils.isEmpty(user) || StringUtils.isEmpty(pass) || StringUtils.isEmpty(phone) || StringUtils.isEmpty(infopass)){
                        Toasty.success(this,R.string.error_null).show();
                    }else if (!ValidateUtils.isVaidFullName(user)){
                        Toasty.success(this,R.string.error_ky_tu).show();
                    }else if (!SoUtils.isVaidSo(phone)){
                        Toasty.success(this,R.string.error_phone).show();
                    }else if (!pass.equals(infopass)){
                        Toasty.error(this,R.string.error_no_coincide).show();
                    } else {
                        showLoading(true);
                        presenter.checkuser(user,phone,pass);
                    }

                }));
    }

    @Override
    public void onBind(NetworkStatus networkStatus) {
        if (!networkStatus.isAvailable()){
            onDisconnect();
        }

    }

    @Override
    public void onConnect() {

    }

    @Override
    public void onDisconnect() {
        showToastDisconnect();
    }

    @Override
    public void error(int error) {
        showLoading(false);
        Toasty.error(this,error).show();
    }

    @Override
    public void success() {
        showLoading(false);
        Toasty.success(this,R.string.success_registraton).show();
        finish();
    }

    @Override
    public void showLoading(boolean show) {
        if (show){
            LoadingDialog.getInstance().showLoading(this);
        }else {
            LoadingDialog.getInstance().hideLoading();
        }
    }
}
