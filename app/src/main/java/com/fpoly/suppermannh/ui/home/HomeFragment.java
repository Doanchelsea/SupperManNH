package com.fpoly.suppermannh.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.base.BaseFragment;
import com.fpoly.suppermannh.lisenner.HomeLisenner;
import com.fpoly.suppermannh.model.Houst;
import com.fpoly.suppermannh.model.LoadingDialog;
import com.fpoly.suppermannh.ui.account.AccountFragment;
import com.fpoly.suppermannh.ui.ads.AdsActivity;
import com.fpoly.suppermannh.ui.home.adapter.HoustAdapter;
import com.fpoly.suppermannh.ui.home.homedetail.HomeDetailActivity;
import com.fpoly.suppermannh.ui.home.menudetail.MenuDetailActivity;
import com.fpoly.suppermannh.ui.home.viewpager.SlideViewPager;
import com.fpoly.suppermannh.untils.MyTimerTask;
import com.jakewharton.rxbinding3.view.RxView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends BaseFragment implements HomeContract, HomeLisenner {
    int page;
    int count;
    @BindView(R.id.view_pager_home)
    ViewPager viewPager;
    @BindView(R.id.liner_layout_home)
    LinearLayout linearLayout;
    @BindView(R.id.constraintLayout_hotpot_home)
    ConstraintLayout constraintLayout_hotpot_home;
    @BindView(R.id.constraintLayout_hotpot_dish)
    ConstraintLayout constraintLayout_hotpot_dish;
    @BindView(R.id.constraintLayout_hotpot_drinks)
    ConstraintLayout constraintLayout_hotpot_drinks;
    @BindView(R.id.view_houst_home)
    View view_houst_home;
    @BindView(R.id.recycler_view_houst_home)
    RecyclerView recycler_view_houst_home;
    @BindView(R.id.btn_load_more_home)
    Button btn_load_more_home;
    @BindView(R.id.nestedScrollView_home_fragment)
    NestedScrollView nestedScrollView_home_fragment;
    @BindView(R.id.shimmer_view_containeroff)
    ShimmerFrameLayout shimmerFrameLayout;
    @BindView(R.id.constraintLayout_home_fragment)
    ConstraintLayout constraintLayout;
    List<Houst> housts = new ArrayList<>();
    HoustAdapter houstAdapter;
    LinearLayoutManager manager = new GridLayoutManager(activity,2);
    private int dotscount;
    private RoundedImageView[] dots;
    private MyTimerTask myTimerTask;
    private HomePresenter presenter;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        count = 8;
        presenter.getDataHoust(housts,page,count);
        visible(btn_load_more_home);
        addDisposable(RxView.clicks(btn_load_more_home)
                .throttleFirst(2, TimeUnit.SECONDS)
                .compose(bindToLifecycle()).subscribe(unit -> {
                     page++;
                     presenter.getDataHoust(housts,page,count);
                 }));

    }

    @Override
    public void onStart() {
        shimmerFrameLayout.startShimmer();
        super.onStart();
    }

    @Override
    public void onStop() {
        shimmerFrameLayout.stopShimmer();
        showLoading(false);
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        housts.clear();
    }

    @Override
    protected void addEvents() {
        nestedScrollView_home_fragment.getParent().requestChildFocus(nestedScrollView_home_fragment, nestedScrollView_home_fragment);
        presenter.layoutParams(dots,dotscount,linearLayout);
        presenter.setPosition(viewPager,dots,dotscount);

        addDisposable(RxView.clicks(constraintLayout_hotpot_home)
                .throttleFirst(1,TimeUnit.SECONDS)
                .subscribe(unit -> {
                    MenuDetailActivity.startActivity(activity,1);
                }));
        addDisposable(RxView.clicks(constraintLayout_hotpot_dish)
                .throttleFirst(1,TimeUnit.SECONDS)
                .subscribe(unit -> {
                    MenuDetailActivity.startActivity(activity,2);
                }));
        addDisposable(RxView.clicks(constraintLayout_hotpot_drinks)
                .throttleFirst(1,TimeUnit.SECONDS)
                .subscribe(unit -> {
                    MenuDetailActivity.startActivity(activity,3);
                }));

    }

    @Override
    protected void configViews() {

    }

    @Override
    protected void initDatas() {
        houstAdapter = new HoustAdapter(activity,housts,this);

        recycler_view_houst_home.setHasFixedSize(true);
        recycler_view_houst_home.setLayoutManager(manager);
        recycler_view_houst_home.setAdapter(houstAdapter);

        SlideViewPager slideViewPager = new SlideViewPager(this,housts,activity);
        viewPager.setAdapter(slideViewPager);
        dotscount = slideViewPager.getCount();
        dots = new RoundedImageView[dotscount];
        myTimerTask = new MyTimerTask(activity,viewPager);
        presenter = new HomePresenter(activity,myTimerTask,this);

    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_fragment;
    }

    @Override
    public void showError(int error) {
        shimmerFrameLayout.stopShimmer();
        gone(shimmerFrameLayout);
        Toasty.error(activity,error).show();
    }

    @Override
    public void showSuccess() {
        visible(constraintLayout);
        houstAdapter.notifyDataSetChanged();
        shimmerFrameLayout.stopShimmer();
        gone(shimmerFrameLayout);
    }

    @Override
    public void dismisButton() {
        alerter(R.string.error_houst);
        gone(btn_load_more_home);
    }

    @Override
    public void showLoading(boolean show) {

        if (show){
            LoadingDialog.getInstance().showLoading(activity);
        }else {
            LoadingDialog.getInstance().hideLoading();
        }
    }

    @Override
    public void onClick(Houst houst) {
        showLoading(true);
        HomeDetailActivity.startActivity(activity,houst);
    }
}
