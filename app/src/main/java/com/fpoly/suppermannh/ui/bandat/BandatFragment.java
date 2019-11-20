package com.fpoly.suppermannh.ui.bandat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.base.BaseFragment;
import com.fpoly.suppermannh.model.Manage;
import com.fpoly.suppermannh.model.eventbus.CancelEvent;
import com.fpoly.suppermannh.model.local.AppPreferencesHelper;
import com.fpoly.suppermannh.model.local.DataManager;
import com.fpoly.suppermannh.ui.account.AccountFragment;
import com.fpoly.suppermannh.ui.bandat.cancel.CancelActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jakewharton.rxbinding3.view.RxView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class BandatFragment extends BaseFragment implements OnMapReadyCallback,BandatContract {


    private SharedPreferences mPrefs;
    private AppPreferencesHelper appPreferencesHelper;
    private DataManager dataManager;

    private GoogleMap driver_map_ban_dat_fragment;
    @BindView(R.id.linearLayout_one_ban_dat_fragment)
    LinearLayout linearLayout_one_ban_dat_fragment;
    @BindView(R.id.constraintLayout_one_ban_dat_fragment)
    ConstraintLayout constraintLayout_one_ban_dat_fragment;
    @BindView(R.id.tv_name_nh_ban_dat_fragment)
    TextView tv_name_nh_ban_dat_fragment;
    @BindView(R.id.tv_name_mon_an_ban_dat_fragment)
    TextView tv_name_mon_an_ban_dat_fragment;
    @BindView(R.id.tv_tim_dates_ban_dat_fragment)
    TextView tv_tim_dates_ban_dat_fragment;
    @BindView(R.id.tv_name_user_ban_dat_fragment)
    TextView tv_name_user_ban_dat_fragment;
    @BindView(R.id.tv_huy_ban_dat)
    TextView tv_huy_ban_dat;
    @BindView(R.id.img_my_location)
    ImageView img_my_location;


    private List<Manage> manages = new ArrayList<>();
    private BandatPresenter presenter;

    @Override
    public void onStart() {
        super.onStart();
        presenter.getData(manages,dataManager.getID());
    }

    @Override
    public void onStop() {
        super.onStop();
        manages.clear();
    }

    public static BandatFragment newInstance() {
        Bundle args = new Bundle();
        BandatFragment fragment = new BandatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CancelEvent cancelEvent){
        if (manages.size() == 0){
            return;
        }
        if (cancelEvent.getId().equals(manages.get(0).getId()))
        presenter.getData(manages,dataManager.getID());
    }

    @Override
    protected void addEvents() {
    }

    @Override
    protected void configViews() {

    }

    @Override
    protected void initDatas() {
        mPrefs = context.getSharedPreferences("", Context.MODE_PRIVATE);
        appPreferencesHelper = new AppPreferencesHelper(mPrefs,activity);
        dataManager = new DataManager(appPreferencesHelper);
        presenter = new BandatPresenter(activity,this);
        initMap();
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.bandat_fragment;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        driver_map_ban_dat_fragment = googleMap;
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.driver_map_ban_dat_fragment);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void errornull() {
        gone(constraintLayout_one_ban_dat_fragment);
        visible(linearLayout_one_ban_dat_fragment);
    }

    @Override
    public void error(int error) {
        Toasty.error(activity,error).show();
        gone(constraintLayout_one_ban_dat_fragment);
        visible(linearLayout_one_ban_dat_fragment);
    }

    @Override
    public void success(List<Manage> manages) {
        visible(constraintLayout_one_ban_dat_fragment);
        gone(linearLayout_one_ban_dat_fragment);
        presenter.getNH(manages.get(0).getIdnhahang());
        loadFullName(manages.get(0).getName(),tv_name_mon_an_ban_dat_fragment);
        loadFullName("Thời gian : "+manages.get(0).getTime()+" - "+manages.get(0).getDate(),tv_tim_dates_ban_dat_fragment);
        loadFullName(dataManager.getName()+" ("+dataManager.getphone()+")",tv_name_user_ban_dat_fragment);


        addDisposable(RxView.clicks(tv_huy_ban_dat)
                .throttleFirst(1, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
                    CancelActivity.startActivity(activity,manages.get(0));
                }));
    }

    @Override
    public void houst(String name, String lat, String lng) {
        LatLng currentLatLng = new LatLng(Double.valueOf(lat), Double.valueOf(lng));
        driver_map_ban_dat_fragment.addMarker(new MarkerOptions().position(currentLatLng)
                .title(name));
//                .icon(bitmapDescriptorFromVector(activity,R.drawable.logoapp)));
        driver_map_ban_dat_fragment.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 16));

        addDisposable(RxView.clicks(img_my_location).throttleFirst(2,TimeUnit.SECONDS)
                .compose(bindToLifecycle()).subscribe(unit -> {
                    driver_map_ban_dat_fragment.addMarker(new MarkerOptions().position(currentLatLng)
                            .title(name));
//                .icon(bitmapDescriptorFromVector(activity,R.drawable.logoapp)));
                    driver_map_ban_dat_fragment.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 16));
                }));
        loadFullName(name,tv_name_nh_ban_dat_fragment);

    }
}
