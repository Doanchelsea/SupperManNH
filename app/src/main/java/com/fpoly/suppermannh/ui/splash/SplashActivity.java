package com.fpoly.suppermannh.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.base.BaseActivity;
import com.fpoly.suppermannh.model.local.AppPreferencesHelper;
import com.fpoly.suppermannh.model.local.DataManager;
import com.fpoly.suppermannh.ui.ads.AdsActivity;
import com.fpoly.suppermannh.ui.ads.model.PrefManager;
import com.fpoly.suppermannh.ui.login.LoginActivity;
import com.fpoly.suppermannh.ui.main.MainActivity;
import com.fpoly.suppermannh.untils.StringUtils;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;

import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity implements Connectable, Disconnectable, Bindable,SplashContract {
    private DataManager dataManager;
    private AppPreferencesHelper appPreferencesHelper;
    private SharedPreferences mPrefs;
    private PrefManager prefManager;
    private SplashPresenter splashPresenter;
    @Override
    protected void onResume() {
        super.onResume();
        registerBindable(this);
        registerConnectable(this);
        registerConnectable(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected Merlin initMerlin() {
        return new Merlin.Builder()
                .withConnectableCallbacks()
                .withDisconnectableCallbacks()
                .withBindableCallbacks()
                .build(this);
    }

    @Override
    protected void initData() {
        splashPresenter = new SplashPresenter(this,this);
        mPrefs = context.getSharedPreferences("", Context.MODE_PRIVATE);
        appPreferencesHelper = new AppPreferencesHelper(mPrefs,this);
        dataManager = new DataManager(appPreferencesHelper);
        prefManager = new PrefManager(this);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void addEvents() {
        splashPresenter.getData(dataManager.getID());
        if (!prefManager.isFirstTimeLaunch()){
            if (StringUtils.isEmpty(dataManager.getID())){
                addDisposable(Observable.just(0).delay(1000, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(aVoid -> {
                            LoginActivity.startActivity(this);
                        }));
            }
            return;
        }

        addDisposable(Observable.just(0).delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    AdsActivity.startActivity(this);
                }));
    }

    @Override
    public void onBind(NetworkStatus networkStatus) {

    }

    @Override
    public void onConnect() {

    }

    @Override
    public void onDisconnect() {

    }

    @Override
    public void showSucces(String token) {
        if (!token.equals(dataManager.getToken())){
            LoginActivity.startActivity(this);
            dataManager.clearAllUserInfo();
            Toasty.error(this,R.string.error_android).show();
            return;
        }
        addDisposable(Observable.just(0).delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    MainActivity.startActivity(this);
                }));
    }

    @Override
    public void showError(int error) {
        Toasty.error(this,error).show();
    }
}
