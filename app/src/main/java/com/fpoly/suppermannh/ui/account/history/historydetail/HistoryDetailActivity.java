package com.fpoly.suppermannh.ui.account.history.historydetail;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.api.Server;
import com.fpoly.suppermannh.base.BaseActivity;
import com.fpoly.suppermannh.lisenner.RattingLisenner;
import com.fpoly.suppermannh.model.History;
import com.fpoly.suppermannh.model.Pay;
import com.fpoly.suppermannh.ui.account.history.adapter.PayAdapter;
import com.fpoly.suppermannh.ui.account.history.historydetail.bottomsheet.RattingBottomSheet;
import com.fpoly.suppermannh.ui.main.MainActivity;
import com.fpoly.suppermannh.untils.FormatUtils;
import com.google.android.material.appbar.AppBarLayout;
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

public class HistoryDetailActivity extends BaseActivity implements Connectable, Bindable, Disconnectable, HistoryDetailContract, RattingLisenner {

    @BindView(R.id.img_history_detail)
    ImageView img_history_detail;
    @BindView(R.id.activity_register_iv_back_history_detail)
    ImageView activity_register_iv_back_history_detail;
    @BindView(R.id.tv_title_history_detail)
    TextView tv_title_history_detail;
    @BindView(R.id.app_bar_layout)
    AppBarLayout app_bar_layout;
    @BindView(R.id.tv_name_nh_histoty_detail)
    TextView tv_name_nh_histoty_detail;
    @BindView(R.id.tv_time_history_detail)
    TextView tv_time_history_detail;
    @BindView(R.id.tv_dis_history_detail)
    TextView tv_dis_history_detail;
    @BindView(R.id.tv_price_history_detail)
    TextView tv_price_history_detail;
    @BindView(R.id.re_history_detail)
    RecyclerView recyclerView;
    @BindView(R.id.btn_ratting_history_detail)
    Button btn_ratting_history_detail;
    @BindView(R.id.relative_layout_history)
    RelativeLayout relativeLayout;
    History history;

    private HistoryDetailPresenter presenter;
    private List<Pay> list = new ArrayList<>();
    private PayAdapter payAdapter;
    private LinearLayoutManager manager = new LinearLayoutManager(this);


    private static final String HISTORY = "HISTORY";
    public static void startActivity(Activity context, History history){
        context.startActivity(new Intent(context,HistoryDetailActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .putExtra(HISTORY,history));
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
        return R.layout.activity_history_detail;
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
        payAdapter = new PayAdapter(this,list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(payAdapter);
        presenter = new HistoryDetailPresenter(this,this);
    }


    @Override
    protected void initToolbar() {

    }

    @Override
    protected void addEvents() {

        history = getIntent().getParcelableExtra(HISTORY);
        presenter.getButton(history.getId());
        appBarLayout();
        loadAvatar(Server.duongdananh+history.getImages(),img_history_detail);
        loadFullName(history.getNamenh(),tv_title_history_detail);
        loadFullName(history.getNamenh(),tv_name_nh_histoty_detail);
        loadFullName("Thời gian đến : "+history.getDate(),tv_time_history_detail);
        loadFullName("Ưu đãi : giảm giá 15%",tv_dis_history_detail);
        loadFullName("Hóa đơn : "+ FormatUtils.convertEstimatedPrice(Double.valueOf(history.getPrice()))+" VNĐ"
                ,tv_price_history_detail);
        presenter.getPay(list,history.getIdtrangthai());

        addDisposable(RxView.clicks(activity_register_iv_back_history_detail)
        .throttleFirst(2, TimeUnit.SECONDS)
        .compose(bindToLifecycle())
        .subscribe(unit -> {
            onBackPressed();
            finish();
        }));

        addDisposable(RxView.clicks(btn_ratting_history_detail)
        .throttleFirst(2,TimeUnit.SECONDS)
        .compose(bindToLifecycle())
        .subscribe(
                unit -> {

                    RattingBottomSheet rattingBottomSheet = RattingBottomSheet.getInstance();
                    rattingBottomSheet.show(getSupportFragmentManager(),rattingBottomSheet.getTag());

                }));

    }

    @Override
    public void onBind(NetworkStatus networkStatus) {
        if (!networkStatus.isAvailable()){
            onDisconnect();
        }else {
            onConnect();
        }
    }

    @Override
    public void onConnect() {
        hideDialog();
    }
    @Override
    public void onDisconnect() {
        showdialog();
    }

    private void appBarLayout(){
        app_bar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                int scollRange = appBarLayout.getTotalScrollRange();
                if (scollRange + i <= 80){
                    visible(tv_title_history_detail);
                    tint(activity_register_iv_back_history_detail,R.color.black);
                }else{
                    gone(tv_title_history_detail);
                    tint(activity_register_iv_back_history_detail,R.color.white);
                }
            }
        });
    }

    @Override
    public void showSuccess() {
        payAdapter.notifyDataSetChanged();
        visible(relativeLayout);
    }

    @Override
    public void showErorr(int error) {
        Toasty.error(this,error).show();
    }

    @Override
    public void showRatting() {
        alerter(R.string.text_ratting,MainActivity.NAME);
        gone(btn_ratting_history_detail);
    }

    @Override
    public void showButton() {
        visible(btn_ratting_history_detail);
    }

    @Override
    public void comment(int ratting, String comment) {
        presenter.getRatting(ratting,comment,history.getIdnhahang(),history.getId());
    }
}
