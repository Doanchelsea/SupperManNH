package com.fpoly.suppermannh.ui.home.homedetail.viewpager;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.base.BaseFragment;
import com.fpoly.suppermannh.model.Houst;
import com.fpoly.suppermannh.model.Menu;
import com.fpoly.suppermannh.model.MenuAdapter;
import com.fpoly.suppermannh.ui.home.homedetail.viewpager.contract.AccountContract;
import com.fpoly.suppermannh.ui.home.homedetail.viewpager.presenter.AccoutDetailPresenter;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class AccountDetailFragment extends BaseFragment implements AccountContract {
    boolean hotpot ;
    boolean dish ;
    boolean drinks;
    @BindView(R.id.re_hotpot_home_detail)
    RelativeLayout re_hotpot_home_detail;
    @BindView(R.id.re_dish_home_detail)
    RelativeLayout re_dish_home_detail;
    @BindView(R.id.re_drinks_home_detail)
    RelativeLayout re_drinks_home_detail;

    @BindView(R.id.recycler_view_hotpot_home_detail)
    RecyclerView recycler_view_hotpot_home_detail;
    @BindView(R.id.recycler_view_dish_home_detail)
    RecyclerView recycler_view_dish_home_detail;
    @BindView(R.id.recycler_view_drinks_home_detail)
    RecyclerView recycler_view_drinks_home_detail;

    @BindView(R.id.tv_hotpot_home_detail)
    TextView tv_hotpot_home_detail;
    @BindView(R.id.tv_dirh_home_detail)
    TextView tv_dirh_home_detail;
    @BindView(R.id.tv_drinks_home_detail)
    TextView tv_drinks_home_detail;

    @BindView(R.id.img_hotpot_home_detail)
    ImageView img_hotpot_home_detail;
    @BindView(R.id.img_dish_home_detail)
    ImageView img_dish_home_detail;
    @BindView(R.id.img_drinks_home_detail)
    ImageView img_drinks_home_detail;

    private AccoutDetailPresenter presenter;
    private List<Menu> menus = new ArrayList<>();
    private List<Menu> menusdish = new ArrayList<>();
    private List<Menu> menusdrinks = new ArrayList<>();
    private LinearLayoutManager manager,managerdish,managerdrinks;
    private MenuAdapter menuAdapter,menuAdapterdish,menuAdapterdrinks;

    private Houst houst;


    public static final String HOUST = "HOUST";

    public static AccountDetailFragment newInstance(Houst houst) {
        Bundle args = new Bundle();
        AccountDetailFragment fragment = new AccountDetailFragment();
        args.putParcelable(HOUST,houst);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
         hotpot = true;
         dish = true;
         drinks = true;
    }

    @Override
    protected void addEvents() {
        houst = getArguments().getParcelable(HOUST);
        re_hotpot_home_detail.setOnClickListener(view -> {
            if (hotpot){
                hotpot = false;
                gone(recycler_view_hotpot_home_detail);
                image(img_hotpot_home_detail,R.drawable.ic_expand_more_black_24dp);
            }else{
                hotpot = true;
                visible(recycler_view_hotpot_home_detail);
                image(img_hotpot_home_detail,R.drawable.ic_expand_less_black_24dp);
            }
        });

        re_dish_home_detail.setOnClickListener(view -> {
            if (dish){
                gone(recycler_view_dish_home_detail);
                image(img_dish_home_detail,R.drawable.ic_expand_more_black_24dp);
                dish = false;
            }else{
                visible(recycler_view_dish_home_detail);
                image(img_dish_home_detail,R.drawable.ic_expand_less_black_24dp);
                dish = true;
            }
        });

        re_drinks_home_detail.setOnClickListener(view -> {
            if (drinks){
                gone(recycler_view_drinks_home_detail);
                image(img_drinks_home_detail,R.drawable.ic_expand_more_black_24dp);
                drinks = false;
            }else{
                visible(recycler_view_drinks_home_detail);
                image(img_drinks_home_detail,R.drawable.ic_expand_less_black_24dp);
                drinks = true;
            }
        });

        if (houst.getId() != 0 ){
            presenter.getData(menus,1,houst.getId());
            presenter.getDataDish(menusdish,2,houst.getId());
            presenter.getDataDrinks(menusdrinks,3,houst.getId());
        }else  {
            presenter.getData(menus,1,houst.getIdnhahang());
            presenter.getDataDish(menusdish,2,houst.getIdnhahang());
            presenter.getDataDrinks(menusdrinks,3,houst.getIdnhahang());
        }
    }

    @Override
    protected void configViews() {

    }

    @Override
    protected void initDatas() {
        presenter = new AccoutDetailPresenter(this,activity);
        initData();
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.accout_detail_fragment;
    }

    @Override
    public void showSuccess(int count) {
        menuAdapter.notifyDataSetChanged();
        if (count == 0){
            tv_hotpot_home_detail.setText("Lẩu nướng ("+0+")");
            return;
        }
        tv_hotpot_home_detail.setText("Lẩu nướng ("+count+")");
    }

    @Override
    public void showError(int error) {
        Toasty.error(activity,error).show();
    }

    @Override
    public void showSuccessdish(int dish) {
        menuAdapterdish.notifyDataSetChanged();
        if (dish == 0){
            tv_dirh_home_detail.setText("Món ăn ("+0+")");
            return;
        }
        tv_dirh_home_detail.setText("Món ăn ("+dish+")");
    }

    @Override
    public void showSuccessdrinks(int drinks) {
        menuAdapterdrinks.notifyDataSetChanged();
        if (drinks == 0){
            tv_drinks_home_detail.setText("Nước uống ("+drinks+")");
            return;
        }
        tv_drinks_home_detail.setText("Nước uống ("+drinks+")");
    }

    private void initData(){
        manager = new LinearLayoutManager(context);
        menuAdapter = new MenuAdapter(context,menus);
        recycler_view_hotpot_home_detail.setHasFixedSize(true);
        recycler_view_hotpot_home_detail.setLayoutManager(manager);
        recycler_view_hotpot_home_detail.setAdapter(menuAdapter);

        managerdish = new LinearLayoutManager(context);
        menuAdapterdish = new MenuAdapter(context,menusdish);
        recycler_view_dish_home_detail.setHasFixedSize(true);
        recycler_view_dish_home_detail.setLayoutManager(managerdish);
        recycler_view_dish_home_detail.setAdapter(menuAdapterdish);


        managerdrinks = new LinearLayoutManager(context);
        menuAdapterdrinks = new MenuAdapter(context,menusdrinks);
        recycler_view_drinks_home_detail.setHasFixedSize(true);
        recycler_view_drinks_home_detail.setLayoutManager(managerdrinks);
        recycler_view_drinks_home_detail.setAdapter(menuAdapterdrinks);

    }
}
