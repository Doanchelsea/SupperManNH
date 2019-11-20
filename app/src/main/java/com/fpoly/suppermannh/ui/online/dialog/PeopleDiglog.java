package com.fpoly.suppermannh.ui.online.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.base.BaseDiglog;
import com.fpoly.suppermannh.lisenner.OnlineDetailLisenner;
import com.fpoly.suppermannh.lisenner.OnlineLisenner;
import com.fpoly.suppermannh.lisenner.PeopleLisenner;
import com.fpoly.suppermannh.model.People;
import com.fpoly.suppermannh.ui.online.adapter.PeopleAdapter;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class PeopleDiglog extends BaseDiglog implements PeopleContract, PeopleLisenner {

    private OnlineDetailLisenner onlineLisenner;
    public static PeopleDiglog newInstance() {
        Bundle args = new Bundle();
        PeopleDiglog fragment = new PeopleDiglog();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
            onlineLisenner = (OnlineDetailLisenner) activity;
    }
    @Override
    public void onDestroyView() {
        onlineLisenner = null;
        super.onDestroyView();
    }

    @BindView(R.id.recyclerView_dialog_people)
    RecyclerView recyclerView_dialog_people;
    @BindView(R.id.tv_cancel_dialog_people)
    TextView tv_cancel_dialog_people;
    private List<People> people = new ArrayList<>();
    private PeopleAdapter peopleAdapter;
    private LinearLayoutManager manager = new LinearLayoutManager(activity);
    private PeoplePresenter peoplePresenter;
    @Override
    protected int getLayoutId() {
        return R.layout.people_dialog;
    }

    @Override
    protected void initDialog() {
        Window window = getDialog().getWindow();
        setTransparentDialog(window);

    }

    @Override
    protected void addEvents() {
        peoplePresenter.getPeople(people);
        addDisposable(RxView.clicks(tv_cancel_dialog_people)
                .throttleFirst(1, TimeUnit.SECONDS)
        .compose(bindToLifecycle())
        .subscribe(unit -> {
            hideDialog();
        }));
    }

    @Override
    protected void configViews() {

    }

    @Override
    protected void initDatas() {
        peoplePresenter = new PeoplePresenter(this);
        peopleAdapter = new PeopleAdapter(activity,people,this);
        recyclerView_dialog_people.setHasFixedSize(true);
        recyclerView_dialog_people.setLayoutManager(manager);
        recyclerView_dialog_people.setAdapter(peopleAdapter);
    }

    @Override
    public void show() {
        peopleAdapter.notifyDataSetChanged();
    }

    @Override
    public void people(int people) {
        onlineLisenner.people(people);
    }
}
