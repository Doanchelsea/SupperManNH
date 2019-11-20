package com.fpoly.suppermannh.ui.bandat.cancel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.base.BaseActivity;
import com.fpoly.suppermannh.model.LoadingDialog;
import com.fpoly.suppermannh.model.Manage;
import com.fpoly.suppermannh.model.eventbus.CancelEvent;
import com.fpoly.suppermannh.untils.StringUtils;
import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxRadioGroup;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class CancelActivity extends BaseActivity implements Connectable, Bindable, Disconnectable,CancelContract {

    private static final String MANAGE = "MANAGE";
    public static void startActivity(Activity context, Manage manage){
        context.startActivity(new Intent(context,CancelActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .putExtra(MANAGE,manage));
    }

    private CancelPresenter presenter;

    @BindView(R.id.fakeStatusBar)
    View fakeStatusBar;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.txt_titlemenu)
    TextView txtTitlemenu;
    @BindView(R.id.rbReason1)
    RadioButton rbReasonOne;
    @BindView(R.id.rbReason2)
    RadioButton rbReasonTwo;
    @BindView(R.id.rbReason3)
    RadioButton rbReasonThree;
    @BindView(R.id.rbOtherReason)
    RadioButton rbOtherReason;
    @BindView(R.id.rgReason)
    RadioGroup rgReason;
    @BindView(R.id.edtOtherReason)
    EditText edtOtherReason;
    @BindView(R.id.btnConfirm)
    Button btnConfirm;
    @BindView(R.id.mainView)
    LinearLayout mainView;

    @Override
    protected void onResume() {
        super.onResume();
        registerBindable(this);
        registerConnectable(this);
        registerDisconnectable(this);
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CancelEvent cancelEvent){
        Manage manage = getIntent().getParcelableExtra(MANAGE);
        if (manage == null){
            return;
        }
        if (cancelEvent.getId().equals(String.valueOf(manage.getId()))){
            onBackPressed();
            finish();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cancel;
    }

    @Override
    protected Merlin initMerlin() {
        return new Merlin.Builder().withConnectableCallbacks()
                .withDisconnectableCallbacks()
                .withBindableCallbacks()
                .build(this);
    }

    @Override
    protected void initData() {
        presenter = new CancelPresenter(this,this,getIntent().getParcelableExtra(MANAGE));
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void addEvents() {
        addDisposable(RxRadioGroup.checkedChanges(rgReason)
                .compose(bindToLifecycle())
                .subscribe(id -> {
                    switch (id) {
                        case R.id.rbReason1: {
                            edtOtherReason.setEnabled(false);
                            break;
                        }
                        case R.id.rbReason2: {

                            edtOtherReason.setEnabled(false);
                            break;
                        }
                        case R.id.rbReason3: {
                            edtOtherReason.setEnabled(false);
                            break;
                        }
                        case R.id.rbOtherReason: {
                            edtOtherReason.setEnabled(true);
                            break;
                        }
                    }
                }));
        addDisposable(RxView.clicks(btnConfirm).throttleFirst(2, TimeUnit.SECONDS)
        .compose(bindToLifecycle()).subscribe(unit -> {
            String edtOtherReasons = edtOtherReason.getText().toString().trim();

            if (StringUtils.isEmpty(edtOtherReasons) && rbOtherReason.isChecked()){
                Toasty.warning(this,R.string.error_null).show();
                return;
            }else {
                showLoading(true);
                presenter.delete();
            }
        }));
        addDisposable(RxView.clicks(imgBack).throttleFirst(1,TimeUnit.SECONDS)
        .compose(bindToLifecycle())
        .subscribe(unit -> {
            onBackPressed();
            finish();
        }));

    }

    @Override
    public void onBind(NetworkStatus networkStatus) {
        if (!networkStatus.isAvailable()){
            onDisconnect();
        }
    }

    @Override
    public void onConnect() {

    }

    @Override
    public void onDisconnect() {
        showToastDisconnect();
    }

    @Override
    public void error(int error) {
        Toasty.error(this,error).show();
        showLoading(false);
    }

    @Override
    public void success() {
        showLoading(false);
        onBackPressed();
        finish();
    }

    @Override
    public void showLoading(boolean show) {
        if (show){
            LoadingDialog.getInstance().showLoading(this);
        }else {
            LoadingDialog.getInstance().hideLoading();
        }
    }
}
