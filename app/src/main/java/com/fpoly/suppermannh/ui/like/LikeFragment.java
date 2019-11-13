package com.fpoly.suppermannh.ui.like;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.base.BaseFragment;
import com.fpoly.suppermannh.lisenner.HomeLisenner;
import com.fpoly.suppermannh.model.Houst;
import com.fpoly.suppermannh.model.local.AppPreferencesHelper;
import com.fpoly.suppermannh.model.local.DataManager;
import com.fpoly.suppermannh.ui.home.homedetail.HomeDetailActivity;
import com.fpoly.suppermannh.ui.like.adapter.LikeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class LikeFragment extends BaseFragment implements LikeContract, HomeLisenner {

    private SharedPreferences mPrefs;
    private AppPreferencesHelper appPreferencesHelper;
    private DataManager dataManager;

    List<Houst> likes = new ArrayList<>();
    LinearLayoutManager manager;
    LikeAdapter likeAdapter;
    private LikePresenter presenter;
    @BindView(R.id.recycler_view_like_fragment)
    RecyclerView recyclerView;

    @Override
    public void onResume() {
        super.onResume();
        presenter.getData(likes, dataManager.getID());
    }

    public static LikeFragment newInstance() {
        Bundle args = new Bundle();
        LikeFragment fragment = new LikeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void addEvents() {

    }

    @Override
    protected void configViews() {

    }

    @Override
    protected void initDatas() {
        mPrefs = context.getSharedPreferences("", Context.MODE_PRIVATE);
        appPreferencesHelper = new AppPreferencesHelper(mPrefs,activity);
        dataManager = new DataManager(appPreferencesHelper);

        presenter = new LikePresenter(activity,this);
        manager = new LinearLayoutManager(activity);
        likeAdapter = new LikeAdapter(activity,likes,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(likeAdapter);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.like_fragment;
    }

    @Override
    public void showSuccess() {
        visible(recyclerView);
        likeAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNull() {
        gone(recyclerView);
    }

    @Override
    public void showError(int error) {
        Toasty.error(activity,error).show();
    }


    @Override
    public void onClick(Houst houst) {
        HomeDetailActivity.startActivity(activity,houst);
    }
}
