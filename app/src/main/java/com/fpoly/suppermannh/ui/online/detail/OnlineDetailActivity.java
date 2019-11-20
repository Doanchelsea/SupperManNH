package com.fpoly.suppermannh.ui.online.detail;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.base.BaseActivity;
import com.fpoly.suppermannh.lisenner.OnlineDetailLisenner;
import com.fpoly.suppermannh.lisenner.PeopleLisenner;
import com.fpoly.suppermannh.model.Houst;
import com.fpoly.suppermannh.model.LoadingDialog;
import com.fpoly.suppermannh.model.Menu;
import com.fpoly.suppermannh.model.eventbus.CancelEvent;
import com.fpoly.suppermannh.ui.main.MainActivity;
import com.fpoly.suppermannh.ui.online.adapter.PeopleAdapter;
import com.fpoly.suppermannh.ui.online.dialog.PeopleDiglog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jakewharton.rxbinding3.view.RxView;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class OnlineDetailActivity extends BaseActivity implements Connectable, Disconnectable, Bindable,
        OnMapReadyCallback, OnlineDetailLisenner,OnlineDetailContract {

    private static final String DATE = "DATE";
    private static final String TIME = "TIME";
    private static final String MENU = "MENU";
    private static final String HOUST = "HOUST";
    private GoogleMap driverMap;
    private Houst houst;
    private Menu menu;
    private OnlineDetailPresenter presenter;
    @BindView(R.id.tv_name_nh_online_detail)
    TextView tv_name_nh_online_detail;
    @BindView(R.id.tv_name_mon_an_online_detail)
    TextView tv_name_mon_an_online_detail;
    @BindView(R.id.tv_user_online_detail)
    TextView tv_user_online_detail;
    @BindView(R.id.tv_so_luong)
    TextView tv_so_luong;
    @BindView(R.id.tv_Sua)
    TextView tv_Sua;
    @BindView(R.id.tv_dat_ban)
    TextView tv_dat_ban;
    @BindView(R.id.tv_date_dat_ban)
    TextView tv_date_dat_ban;
    int people1 = 1;
    @BindView(R.id.activity_register_iv_back_dat_ban)
    ImageView activity_register_iv_back_dat_ban;
    PeopleDiglog dialog;


    public static void staryActivity(Activity context, String date, String time, Menu menu, Houst houst){
        context.startActivity(new Intent(context,OnlineDetailActivity.class)
        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        .putExtra(DATE,date)
        .putExtra(TIME,time)
        .putExtra(MENU,menu)
        .putExtra(HOUST,houst));

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
        return R.layout.activity_online_detail;
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
        houst = getIntent().getParcelableExtra(HOUST);
        menu = getIntent().getParcelableExtra(MENU);
        initMap();
        presenter = new OnlineDetailPresenter(this,this,menu);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void addEvents() {
        loadFullName(houst.getNames(),tv_name_nh_online_detail);
        loadFullName(menu.getNames(),tv_name_mon_an_online_detail);
        loadFullName(MainActivity.NAME+" - "+MainActivity.PHONE,tv_user_online_detail);
        loadFullName(getIntent().getStringExtra(TIME)+" - "+
                       getIntent().getStringExtra(DATE),tv_date_dat_ban);
        loadFullName(getString(R.string.amount_of_people)+people1+" người",tv_so_luong);

        addDisposable(RxView.clicks(tv_Sua).throttleFirst(1, TimeUnit.SECONDS)
        .compose(bindToLifecycle())
        .subscribe(unit -> {
            dialog = PeopleDiglog.newInstance();
            dialog.show(getSupportFragmentManager(), dialog.getTag());
        }));
        addDisposable(RxView.clicks(tv_dat_ban)
                .throttleFirst(1,TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
                    showLoading(true);
                    presenter.checkuser();
                }));
        addDisposable(RxView.clicks(activity_register_iv_back_dat_ban)
                .throttleFirst(1,TimeUnit.SECONDS)
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
        showLoading(false);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        driverMap = googleMap;
        LatLng currentLatLng = new LatLng(houst.getLat(), houst.getLng());
        driverMap.addMarker(new MarkerOptions().position(currentLatLng)
                .title(houst.getNames()));
//                .icon(bitmapDescriptorFromVector(this,R.drawable.logoapp)));
        driverMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 16));
    }
    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.driver_map_dat_ban);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void people(int people) {
        people1 = people;
        loadFullName(getString(R.string.amount_of_people)+people1+" người",tv_so_luong);
        dialog.dismiss();
    }

    @Override
    public void showban(int soban) {
        presenter.getcheck(getIntent().getStringExtra(TIME)
                ,getIntent().getStringExtra(DATE),soban,people1);
    }

    @Override
    public void showError(int error) {
        Toasty.error(this,error).show();
        showLoading(false);
    }

    @Override
    public void showErrorOnline(int error) {
        Toasty.error(this,error).show();
        showLoading(false);
    }

    @Override
    public void showSuccess() {
        presenter.getToken();
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
    public void showMesing() {
        Toasty.success(this,R.string.messing_success).show();
        showLoading(false);
        finish();
    }
}
