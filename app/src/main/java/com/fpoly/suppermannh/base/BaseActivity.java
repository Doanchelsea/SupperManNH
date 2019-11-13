package com.fpoly.suppermannh.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.untils.StringUtils;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.tapadoo.alerter.Alerter;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import java.io.ByteArrayOutputStream;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity extends RxAppCompatActivity {

    protected CompositeDisposable compositeDisposable = new CompositeDisposable();
    protected Context context;
    protected Merlin merlin;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        context = this;
        initData();
        initToolbar();
        addEvents();
        merlin = initMerlin();
    }

    protected abstract int getLayoutId();

    @Override
    protected void onStart() {
        super.onStart();
        merlin.bind();
    }

    @Override
    protected void onStop() {
        super.onStop();
        merlin.unbind();
    }

    @Override
    protected void onDestroy() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    protected abstract Merlin initMerlin();
    protected abstract void initData();
    protected abstract void initToolbar();
    protected abstract void addEvents();

    protected void registerConnectable(Connectable connectable) {
        merlin.registerConnectable(connectable);
    }

    protected void registerDisconnectable(Disconnectable disconnectable) {
        merlin.registerDisconnectable(disconnectable);
    }

    protected void registerBindable(Bindable bindable) {
        merlin.registerBindable(bindable);
    }
    protected void loadAvatar(String url, ImageView ivAvatar) {
        if (url == null || ivAvatar == null) {
            return;
        }
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.img_hinhcho)
                .error(R.drawable.img_hinhcho)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontTransform()
                .dontAnimate()
                .into(ivAvatar);
    }

    protected void gone(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }
    protected void invisible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    protected void visible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }

    }
    protected void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public void showToastDisconnect() {
        Toasty.warning(this, getString(R.string.error_missing_network), 200).show();
    }

    public void showImage(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,1);
    }
    public String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        byte[]  imageBytes = outputStream.toByteArray();
        String edcodeImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return  edcodeImage;
    }

    protected void loadFullName(String fullName, TextView tvFullName) {
        if (StringUtils.isEmpty(fullName) || tvFullName == null) {
            return;
        }
        tvFullName.setText(fullName);
    }

    public void alerter(int showAlerter){
        Alerter.create(this)
                .setTitle(R.string.app_name)
                .setText(showAlerter)
                .setDuration(1500)
                .setBackgroundColorRes(R.color.bg_color_alert_dialog)
                .show();

    }

    public void alerter(int showAlerter,String showTitle){
        Alerter.create(this)
                .setTitle(showTitle)
                .setText(showAlerter)
                .setDuration(1500)
                .setBackgroundColorRes(R.color.bg_color_alert_dialog)
                .show();

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void tint(ImageView imageView, int color){
        imageView.setImageTintList(getResources().getColorStateList(color));
    }

}
