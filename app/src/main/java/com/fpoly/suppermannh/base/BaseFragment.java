package com.fpoly.suppermannh.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.untils.StringUtils;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.tapadoo.alerter.Alerter;
import com.trello.rxlifecycle3.components.support.RxFragment;

import java.io.ByteArrayOutputStream;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseFragment extends RxFragment {
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();
    protected Context context;
    protected AppCompatActivity appCompatActivity;
    protected FragmentActivity activity;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(getLayoutId(), container, false);
        activity = getSupportActivity();
        context = activity;
        appCompatActivity = (AppCompatActivity) activity;
        return parentView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initToolbar();
        initDatas();
        configViews();
        addEvents();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.activity = (FragmentActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.activity = null;
    }
    @Override
    public void onDestroyView() {
        unbinder.unbind();
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        super.onDestroyView();
    }
    protected void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);

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

    protected void loadFullName(String fullName, TextView tvFullName) {
        if (StringUtils.isEmpty(fullName) || tvFullName == null) {
            return;
        }
        tvFullName.setText(fullName);
    }

    protected abstract void addEvents();

    protected abstract void configViews();

    protected abstract void initDatas() ;

    protected  abstract  void initToolbar();

    protected  abstract int getLayoutId();

    private FragmentActivity getSupportActivity() {
        return super.getActivity();
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        byte[]  imageBytes = outputStream.toByteArray();
        String edcodeImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return  edcodeImage;
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
    public void alerter(int showAlerter){
        Alerter.create(activity)
                .setTitle(R.string.app_name)
                .setText(showAlerter)
                .setDuration(1500)
                .setBackgroundColorRes(R.color.bg_color_alert_dialog)
                .show();

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

    protected void image(ImageView imageView,int drawable){
        if (drawable ==0 || imageView == null) {
            return;
        }
        imageView.setImageResource(drawable);
    }
    public BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        int wight = 40, height = 70;
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, wight, height);
        Bitmap bitmap = Bitmap.createBitmap(wight, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
