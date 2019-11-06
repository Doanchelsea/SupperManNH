package com.fpoly.suppermannh.ui.ads;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.base.BaseActivity;
import com.fpoly.suppermannh.ui.ads.adapter.AdsViewPagerAdapter;
import com.fpoly.suppermannh.ui.ads.model.PrefManager;
import com.fpoly.suppermannh.ui.login.LoginActivity;
import com.fpoly.suppermannh.ui.main.MainActivity;
import com.jakewharton.rxbinding3.view.RxView;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class AdsActivity extends BaseActivity implements Connectable, Disconnectable, Bindable {
    @BindView(R.id.view_pager_ads)
    ViewPager viewPager;
    @BindView(R.id.layoutDots)
    LinearLayout layoutDots;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.imgClear)
    ImageView imageClear;
    private AdsViewPagerAdapter viewPagerAdapter;
    private TextView[] dots;
    private int dous;
    private PrefManager prefManager;


    public static void startActivity(Activity context){
        context.startActivity(new Intent(context, AdsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        context.finish();
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
        return R.layout.activity_ads;
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
        prefManager = new PrefManager(this);
        viewPagerAdapter = new AdsViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        dous = viewPagerAdapter.getCount();
        addBottomDots(0);


    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void addEvents() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
                if (position == 0){
                    btnBack.setVisibility(View.GONE);
                } else if (position == dous - 1) {
                    // last page. make button text to GOT IT
                    btnNext.setText("GOT IT");
                    btnBack.setVisibility(View.VISIBLE);
                } else {
                    // still pages are left
                    btnNext.setText("NEXT");
                    btnBack.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        addDisposable(RxView.clicks(imageClear).throttleFirst(1, TimeUnit.SECONDS)
        .compose(bindToLifecycle())
        .subscribe(unit -> {
            onLogin();
        }));

        addDisposable(RxView.clicks(btnBack).subscribe(unit -> {
                    int current = getItem(-1);
                    viewPager.setCurrentItem(current);
                }));
        addDisposable(RxView.clicks(btnNext).subscribe(unit -> {
                    int current = getItem(+1);
                    if (current < dous) {
                        // move to next screen
                        viewPager.setCurrentItem(current);
                    } else {
                        onLogin();
                    }
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

    private void addBottomDots(int currentPage) {
        dots = new TextView[dous];
        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);
        layoutDots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            layoutDots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void onLogin(){
        prefManager.setFirstTimeLaunch(false);
        LoginActivity.startActivity(this);
    }
}
