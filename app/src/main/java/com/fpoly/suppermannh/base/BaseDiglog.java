package com.fpoly.suppermannh.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.trello.rxlifecycle3.components.support.RxDialogFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseDiglog extends RxDialogFragment {
    public static final int DISTANCE_1000_METTER = 1000;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private LayoutInflater inflater;
    protected FragmentActivity activity;

    private Unbinder unbinder;

    @Override
    public void onStart() {
        super.onStart();
        initDialog();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        this.inflater = inflater;
        activity = getSupportActivity();
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupCancelableDialog();
        initDatas();
        configViews();
        addEvents();
    }


    @Override
    public void onAttach(Context context) {
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
            compositeDisposable.clear();
        }
        super.onDestroyView();
    }

    protected void setTransparentDialog(Window window) {
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    protected abstract int getLayoutId();

    protected abstract void initDialog();

    protected abstract void addEvents();

    protected abstract void configViews();

    protected abstract void initDatas();

    private FragmentActivity getSupportActivity() {
        return  super.getActivity();
    }
    private void setupCancelableDialog() {
        setCancelable(true);
        getDialog().setCanceledOnTouchOutside(true);
    }

    protected void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }
    protected void hideDialog() {
        if (getDialog() != null && getDialog().isShowing()) {
            getDialog().dismiss();
        }
    }
}
