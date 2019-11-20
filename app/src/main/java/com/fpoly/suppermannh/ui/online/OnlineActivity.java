package com.fpoly.suppermannh.ui.online;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.api.Server;
import com.fpoly.suppermannh.base.BaseActivity;
import com.fpoly.suppermannh.lisenner.OnlineLisenner;
import com.fpoly.suppermannh.model.Houst;
import com.fpoly.suppermannh.model.Menu;
import com.fpoly.suppermannh.model.Time;
import com.fpoly.suppermannh.ui.online.adapter.OnlineAdapter;
import com.fpoly.suppermannh.ui.online.detail.OnlineDetailActivity;
import com.fpoly.suppermannh.untils.FormatUtils;
import com.jakewharton.rxbinding3.view.RxView;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class OnlineActivity extends BaseActivity implements Connectable, Disconnectable, Bindable,
        DatePickerDialog.OnDateSetListener,OnlineContract, OnlineLisenner {

    private static final String MENU = "MENU";
    private static final String HOUST = "HOUST";

    public static void startActivity(Activity context, Menu menu, Houst houst){
        context.startActivity(new Intent(context,OnlineActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .putExtra(MENU,menu)
                .putExtra(HOUST,houst));
    }

    @BindView(R.id.img_online_nh)
    ImageView img_online_nh;
    @BindView(R.id.img_back_online)
    ImageView img_back_online;
    @BindView(R.id.img_online_mon_an)
    ImageView img_online_mon_an;
    @BindView(R.id.tv_date_time)
    TextView tv_date_time;
    @BindView(R.id.tv_name_mon_an_online)
    TextView tv_name_mon_an_online;
    @BindView(R.id.tv_time_online)
    TextView tv_time_online;
    @BindView(R.id.tv_name_nh_online)
    TextView tv_name_nh_online;
    @BindView(R.id.tv_address_online)
    TextView tv_address_online;
    @BindView(R.id.recycler_view_online)
    RecyclerView recycler_view_online;
    Calendar calendar;
    Menu menu;
    Houst houst;
    List<Time> times = new ArrayList<>();
    OnlineAdapter onlineAdapter;
    LinearLayoutManager manager;
    OnlinePresenter presenter;

    @Override
    protected void onResume() {
        super.onResume();
        registerBindable(this);
        registerConnectable(this);
        registerDisconnectable(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_online;
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
        presenter = new OnlinePresenter(this,this);
        menu = getIntent().getParcelableExtra(MENU);
        calendar = Calendar.getInstance();
        manager = new LinearLayoutManager(this,LinearLayout.HORIZONTAL,false);
        onlineAdapter = new OnlineAdapter(this,times,menu,calendar,this);
        recycler_view_online.setHasFixedSize(true);
        recycler_view_online.setLayoutManager(manager);
        recycler_view_online.setAdapter(onlineAdapter);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void addEvents() {
        houst = getIntent().getParcelableExtra(HOUST);
        menu = getIntent().getParcelableExtra(MENU);
        loadAvatar(Server.duongdananh+houst.getImages(),img_online_nh);
        loadAvatar(Server.duongdananh+menu.getImages(),img_online_mon_an);
        loadFullName(houst.getNames(),tv_name_nh_online);
        loadFullName(houst.getAddress(),tv_address_online);
        loadFullName(menu.getNames(),tv_name_mon_an_online);

        if (menu.getDates().equals("day")){
            loadFullName("Cả ngày - ",tv_date_time);
        }else if (menu.getDates().equals("lunch")){
            loadFullName("Bữa trưa - ",tv_date_time);
        }else if (menu.getDates().equals("dinner")){
            loadFullName("Bữa tối - ",tv_date_time);
        }

        loadFullName(FormatUtils.convertEstimatedDate4(calendar.getTime()),tv_time_online);

        addDisposable(RxView.clicks(tv_time_online)
                .throttleFirst(1, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
                    datepiker(this,calendar);
                }));
        addDisposable(RxView.clicks(img_back_online).throttleFirst(1,TimeUnit.SECONDS)
        .compose(bindToLifecycle()).subscribe(unit -> {
            onBackPressed();
            finish();
                }));
        presenter.getTime(times);
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
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year,monthOfYear,dayOfMonth);
        loadFullName(FormatUtils.convertEstimatedDate4(calendar.getTime()),tv_time_online);
        Calendar calendar1 = Calendar.getInstance();

        if (dayOfMonth != calendar1.get(Calendar.DAY_OF_MONTH)){
            onlineAdapter.setOnLoad(false);
            onlineAdapter.notifyDataSetChanged();
        }else {
            onlineAdapter.setOnLoad(true);
            onlineAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void time() {
        onlineAdapter.notifyDataSetChanged();
    }

    @Override
    public void error(int error) {
        Toasty.error(this,error).show();
    }

    @Override
    public void onclick(int time) {
        String times = time+":00";
        OnlineDetailActivity.staryActivity(this,
                FormatUtils.convertEstimatedDate1(calendar.getTime()),
                times,menu,houst);
    }
}
