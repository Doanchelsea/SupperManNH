package com.fpoly.suppermannh.ui.home.homedetail;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.api.Server;
import com.fpoly.suppermannh.base.BaseActivity;
import com.fpoly.suppermannh.model.Houst;
import com.fpoly.suppermannh.ui.bandat.BandatFragment;
import com.fpoly.suppermannh.ui.home.HomeFragment;
import com.fpoly.suppermannh.ui.home.homedetail.viewpager.AccountDetailFragment;
import com.fpoly.suppermannh.ui.home.homedetail.viewpager.MarkerDetailFragment;
import com.fpoly.suppermannh.ui.home.homedetail.viewpager.ViewPage;
import com.fpoly.suppermannh.ui.home.homedetail.viewpager.presenter.AccoutDetailPresenter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.jakewharton.rxbinding3.view.RxView;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class HomeDetailActivity extends BaseActivity implements Connectable, Disconnectable, Bindable,HomeDetailContract, View.OnClickListener {

    @BindView(R.id.app_bar_layout_home_detail)
    AppBarLayout appBarLayout;
    @BindView(R.id.tv_name_nh_home_detail)
    TextView tv_name_nh_home_detail;
    @BindView(R.id.img_home_detail)
    ImageView img_home_detail;
    @BindView(R.id.tv_address_home_detail)
    TextView tv_address_home_detail;
    @BindView(R.id.tv_ratting_history_detail)
    TextView tv_ratting_history_detail;
    @BindView(R.id.tv_count_ratting_home_detail)
    TextView tv_count_ratting_home_detail;
    @BindView(R.id.rattingBar_home_detail)
    RatingBar rattingBar_home_detail;
//    @BindView(R.id.tab_layout_home_detail)
//    TabLayout tabLayout;
//    @BindView(R.id.view_pager_home_detail)
//    ViewPager viewPager;
    @BindView(R.id.activity_register_iv_back_home_detail)
    ImageView activity_register_iv_back_home_detail;
    @BindView(R.id.activity_register_iv_love_home_detail)
    ImageView activity_register_iv_love_home_detail;
    @BindView(R.id.activity_register_iv_love_red_home_detail)
    ImageView activity_register_iv_love_red_home_detail;
    @BindView(R.id.tv_title_home_detail)
    TextView tv_title_home_detail;


    @BindView(R.id.linearLayout_one_tab)
    LinearLayout linearLayout_one_tab;
    @BindView(R.id.linearLayout_two_tab)
    LinearLayout linearLayout_two_tab;
    @BindView(R.id.tv_tab_one_home_detail)
    TextView tv_tab_one_home_detail;
    @BindView(R.id.tv_tab_two_home_detail)
    TextView tv_tab_two_home_detail;
    @BindView(R.id.view_tab_one_home_detail)
    View view_tab_one_home_detail;
    @BindView(R.id.view_tab_two_home_detail)
    View view_tab_two_home_detail;

    Houst houst;

     private Fragment activeFragment;
    private AccountDetailFragment accountDetailFragment;
    private MarkerDetailFragment markerDetailFragment;

    private HomeDetailPresenter presenter;
    public static final String HOUST = "HOUST";

    public static void startActivity(Activity context, Houst houst){
        context.startActivity(new Intent(context,HomeDetailActivity.class)
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
        return R.layout.activity_home_detail;
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

    }

    @Override
    protected void initToolbar() {
        houst = getIntent().getParcelableExtra(HOUST);
        accountDetailFragment = AccountDetailFragment.newInstance(houst);
        markerDetailFragment = MarkerDetailFragment.newInstance(houst);
        activeFragment = accountDetailFragment;
        loadAllFragment();
        presenter = new HomeDetailPresenter(this,this,houst);
    }

    @Override
    protected void addEvents() {


        loadAvatar(Server.duongdananh + houst.getImages(),img_home_detail);
        loadFullName(houst.getNames(),tv_name_nh_home_detail);
        loadFullName(houst.getAddress(),tv_address_home_detail);
        loadFullName(houst.getNames(),tv_title_home_detail);
        loadFullName("Đánh giá : ",tv_ratting_history_detail);
        appBarLayout();

        presenter.checklove();
        presenter.ratting();

        addDisposable(RxView.clicks(activity_register_iv_love_home_detail)
                .throttleFirst(1, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
                    presenter.love();
                }));

        addDisposable(RxView.clicks(activity_register_iv_love_red_home_detail)
                .throttleFirst(1, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
                    presenter.delete();
                }));
        addDisposable(RxView.clicks(activity_register_iv_back_home_detail)
                .throttleFirst(1,TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
                    onBackPressed();
                    finish();
                }));

        invisible(view_tab_two_home_detail);

        linearLayout_one_tab.setOnClickListener(this);
        linearLayout_two_tab.setOnClickListener(this);
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
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                int scollRange = appBarLayout.getTotalScrollRange();
                if (scollRange + i <= 80){
                    visible(tv_title_home_detail);
                    tint(activity_register_iv_back_home_detail,R.color.black);
                    tint(activity_register_iv_love_home_detail,R.color.black);
                    gone(tv_name_nh_home_detail,
                            tv_address_home_detail,
                            tv_ratting_history_detail,
                            tv_count_ratting_home_detail,
                            rattingBar_home_detail);

                }else{
                    gone(tv_title_home_detail);
                    tint(activity_register_iv_back_home_detail,R.color.white);
                    tint(activity_register_iv_love_home_detail,R.color.white);

                    visible(tv_name_nh_home_detail,
                            tv_address_home_detail,
                            tv_ratting_history_detail,
                            tv_count_ratting_home_detail,
                            rattingBar_home_detail);
                }
            }
        });
    }

    @Override
    public void showSuccess() {
        visible(activity_register_iv_love_red_home_detail);
        gone(activity_register_iv_love_home_detail);
    }

    @Override
    public void showDelete() {
        visible(activity_register_iv_love_home_detail);
        gone(activity_register_iv_love_red_home_detail);
    }

    @Override
    public void showError(int error) {
        Toasty.error(this,error).show();
    }

    @Override
    public void showCountRatting(double count,int i) {
        rattingBar_home_detail.setRating((float) count);
        loadFullName(" "+count+" ("+i+")",tv_count_ratting_home_detail);
    }


    private void loadFragment(Fragment activeFragment, Fragment showFragment) {
        getSupportFragmentManager().beginTransaction().hide(activeFragment).show(showFragment).commit();
    }

    private void loadAllFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container_home_detail, markerDetailFragment, "2").hide(markerDetailFragment).commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container_home_detail, accountDetailFragment, "1").commit();
    }


    @Override
    public void onClick(View view) {
        LinearLayout linearLayout = (LinearLayout) view;
        switch (linearLayout.getId()){
            case R.id.linearLayout_one_tab:
                invisible(view_tab_two_home_detail);
                visible(view_tab_one_home_detail);
                loadFragment(activeFragment, accountDetailFragment);
                activeFragment = accountDetailFragment;
                break;
            case R.id.linearLayout_two_tab:
                invisible(view_tab_one_home_detail);
                visible(view_tab_two_home_detail);
                loadFragment(activeFragment,markerDetailFragment );
                activeFragment = markerDetailFragment;
                break;

                default:
                    break;
        }
    }
}
