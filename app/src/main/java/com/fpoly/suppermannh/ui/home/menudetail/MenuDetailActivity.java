package com.fpoly.suppermannh.ui.home.menudetail;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.base.BaseActivity;
import com.fpoly.suppermannh.model.Menu;
import com.fpoly.suppermannh.model.loadmore.EndlessRecyclerViewScrollListener;
import com.fpoly.suppermannh.ui.home.adapter.MenuDetailAdapter;
import com.jakewharton.rxbinding3.view.RxView;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class MenuDetailActivity extends BaseActivity implements Connectable, Disconnectable, Bindable,MenuDetailContract {
    public static void startActivity(Activity context, int idmon){
        context.startActivity(new Intent(context, MenuDetailActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .putExtra("IDMON", idmon));
    }
    private List<Menu> menus = new ArrayList<>();
    private LinearLayoutManager manager = new LinearLayoutManager(this);
    private MenuDetailAdapter xemthemAdapter;
    @BindView(R.id.img_xem_them_back)
    ImageView imgBack;
    @BindView(R.id.tv_name_xem_them)
    TextView tvName;
    @BindView(R.id.re_xem_them)
    RecyclerView recyclerView;
    private MenuDetailPresenter xemthemPresenter;
    int page = 1;

    @Override
    protected void onResume() {
        registerBindable(this);
        registerConnectable(this);
        registerDisconnectable(this);
        super.onResume();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_menu_detail;
    }

    @Override
    protected Merlin initMerlin() {
        return new Merlin.Builder().withConnectableCallbacks()
                .withBindableCallbacks()
                .withDisconnectableCallbacks()
                .build(this);
    }

    @Override
    protected void initData() {
        xemthemPresenter = new MenuDetailPresenter(this,this);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void addEvents() {
        int idmon = getIntent().getIntExtra("IDMON",0);
        if (idmon == 1){
            tvName.setText("Lẩu nướng");
        }else if (idmon == 2){
            tvName.setText("Đồ ăn");
        }else if (idmon == 3){
            tvName.setText("Nước uống");
        }
        addDisposable(RxView.clicks(imgBack).throttleFirst(2, TimeUnit.SECONDS)
        .compose(bindToLifecycle())
        .subscribe(unit -> {
            onBackPressed();
            finish();
        }));

        xemthemAdapter = new MenuDetailAdapter(this,menus);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(xemthemAdapter);

        xemthemPresenter.getData(menus,String.valueOf(idmon),page);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                xemthemPresenter.getData(menus,String.valueOf(idmon),page+1);
            }
        });
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
    public void showlist(List<Menu> menus, boolean show) {
        xemthemAdapter.notifyDataSetChanged();
        if (show == false){
            xemthemAdapter.setOnLoadMore(show);
        }
    }

    @Override
    public void showError(int error) {
        Toasty.error(this,error).show();
    }

}
