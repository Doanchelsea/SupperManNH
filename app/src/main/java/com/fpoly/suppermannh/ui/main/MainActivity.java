package com.fpoly.suppermannh.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.base.BaseActivity;
import com.fpoly.suppermannh.model.local.AppPreferencesHelper;
import com.fpoly.suppermannh.model.local.DataManager;
import com.fpoly.suppermannh.ui.account.AccountFragment;
import com.fpoly.suppermannh.ui.bandat.BandatFragment;
import com.fpoly.suppermannh.ui.home.HomeFragment;
import com.fpoly.suppermannh.ui.like.LikeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements Connectable, Disconnectable, Bindable {
    public static String ID;
    public static String USERNAME;
    public static String PASSWORD;
    public static String NAME;
    public static String PHONE;
    public static String IMAGES;


    private SharedPreferences mPrefs;
    private AppPreferencesHelper appPreferencesHelper;
    private DataManager dataManager;

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;

    private Fragment activeFragment;
    private HomeFragment homeFragment = HomeFragment.newInstance();
    private BandatFragment bandatFragment = BandatFragment.newInstance();
    private LikeFragment likeFragment = LikeFragment.newInstance();
    private AccountFragment accountFragment = AccountFragment.newInstance();

    public static void startActivity(Activity context){
        context.startActivity(new Intent(context,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        context.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerBindable(this);
        registerConnectable(this);
        registerDisconnectable(this);
        if (dataManager != null){
            ID = dataManager.getID();
            USERNAME = dataManager.getusername();
            PASSWORD = dataManager.getpassword();
            NAME = dataManager.getName();
            PHONE = dataManager.getphone();
            IMAGES = dataManager.getImage();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
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
        mPrefs = context.getSharedPreferences("", Context.MODE_PRIVATE);
        appPreferencesHelper = new AppPreferencesHelper(mPrefs,this);
        dataManager = new DataManager(appPreferencesHelper);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void addEvents() {
        activeFragment = homeFragment;
        loadAllFragment();
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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

    private BottomNavigationView.OnNavigationItemSelectedListener
            mOnNavigationItemSelectedListener = menuItem -> {
        switch (menuItem.getItemId()) {
            case R.id.menu_navigation_menu:
                loadFragment(activeFragment, homeFragment);
                activeFragment = homeFragment;
                return true;
            case R.id.menu_navigation_ratting:
                loadFragment(activeFragment, bandatFragment);
                activeFragment = bandatFragment;
                return true;
            case R.id.menu_navigation_like:
                loadFragment(activeFragment, likeFragment);
                activeFragment = likeFragment;
                return true;
            case R.id.menu_navigation_thong_tin:
                loadFragment(activeFragment, accountFragment);
                activeFragment = accountFragment;
                return true;
        }
        return false;
    };
    private void loadFragment(Fragment activeFragment, Fragment showFragment) {
        getSupportFragmentManager().beginTransaction().hide(activeFragment).show(showFragment).commit();
    }
    private void loadAllFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, accountFragment, "4").hide(accountFragment).commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, likeFragment, "3").hide(likeFragment).commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, bandatFragment, "2").hide(bandatFragment).commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, homeFragment, "1").commit();
    }
}
