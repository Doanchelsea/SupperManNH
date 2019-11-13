package com.fpoly.suppermannh.ui.account.password;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.api.Server;
import com.fpoly.suppermannh.base.BaseActivity;
import com.fpoly.suppermannh.model.LoadingDialog;
import com.fpoly.suppermannh.model.local.AppPreferencesHelper;
import com.fpoly.suppermannh.model.local.DataManager;
import com.fpoly.suppermannh.ui.account.detail.AccountDetailActivity;
import com.fpoly.suppermannh.untils.StringUtils;
import com.jakewharton.rxbinding3.view.RxView;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;
import com.tapadoo.alerter.Alerter;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PasswordActivity extends BaseActivity implements Connectable, Bindable, Disconnectable,PasswordContract {
    private SharedPreferences mPrefs;
    private AppPreferencesHelper appPreferencesHelper;
    private DataManager dataManager;
    String update = "";

    @BindView(R.id.activity_update_info_iv_avatar_password)
    CircleImageView imgAvatar;
    @BindView(R.id.activity_ed_password)
    EditText edPassword;
    @BindView(R.id.activity_ed_change_password)
    EditText edChange;
    @BindView(R.id.activity_update_ed_password)
    EditText edUpdate;
    @BindView(R.id.btn_confirm_password)
    Button buttonConfirm;
    @BindView(R.id.activity_update_info_iv_back)
    ImageView imgBack;
    private PasswordPresenter passwordPresenter;
    @Override
    protected void onResume() {
        super.onResume();
        registerBindable(this);
        registerConnectable(this);
        registerDisconnectable(this);
    }
    public static void startActivity(Activity context){
        context.startActivity(new Intent(context, PasswordActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_password;
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
        mPrefs = context.getSharedPreferences("", Context.MODE_PRIVATE);
        appPreferencesHelper = new AppPreferencesHelper(mPrefs,this);
        dataManager = new DataManager(appPreferencesHelper);
        passwordPresenter = new PasswordPresenter(this,this);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void addEvents() {
        loadAvatar(Server.duongdananh+dataManager.getImage(),imgAvatar);
        addDisposable(RxView.clicks(buttonConfirm).throttleFirst(2, TimeUnit.SECONDS)
        .compose(bindToLifecycle())
        .subscribe(unit -> {
            String password = edPassword.getText().toString().trim();
            String change = edChange.getText().toString().trim();
             update = edUpdate.getText().toString().trim();
            if (StringUtils.isEmpty(password) || StringUtils.isEmpty(change) || StringUtils.isEmpty(update)){
                Toasty.warning(this,R.string.error_null).show();
            }else if (!password.equals(dataManager.getpassword())){
                Toasty.warning(this,R.string.wrong_password).show();
            }else if (!change.equals(update)){
                Toasty.warning(this,R.string.password_not).show();
            }else {
                ShowLoading(true);
                passwordPresenter.ChangePassword(update);
            }
        }));
        addDisposable(RxView.clicks(imgBack).throttleFirst(2,TimeUnit.SECONDS)
        .compose(bindToLifecycle())
        .subscribe(unit -> {
            onBackPressed();
            finish();
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
    public void ShowSuccer() {
        dataManager.setpassword(update);
        ShowLoading(false);
        alerter(R.string.password_succer);
        addDisposable(Observable.just(0).delay(1600, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    finish();
                }));
    }

    @Override
    public void ShowError(int error) {
        Toasty.warning(this,error).show();
        ShowLoading(false);
    }

    @Override
    public void ShowLoading(boolean show) {
        if (show){
            LoadingDialog.getInstance().showLoading(this);
        }else {
            LoadingDialog.getInstance().hideLoading();
        }
    }
}
