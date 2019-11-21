package com.fpoly.suppermannh.ui.account.detail;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.api.Server;
import com.fpoly.suppermannh.base.BaseActivity;
import com.fpoly.suppermannh.model.LoadingDialog;
import com.fpoly.suppermannh.model.local.AppPreferencesHelper;
import com.fpoly.suppermannh.model.local.DataManager;
import com.fpoly.suppermannh.ui.main.MainActivity;
import com.fpoly.suppermannh.untils.StringUtils;
import com.fpoly.suppermannh.untils.ValidateUtils;
import com.jakewharton.rxbinding3.view.RxView;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;
import com.tapadoo.alerter.Alerter;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AccountDetailActivity extends BaseActivity implements Connectable, Bindable, Disconnectable,AccountContract {

    Bitmap bitmap;
    @BindView(R.id.activity_update_info_iv_back)
    ImageView imgBack;
    @BindView(R.id.activity_update_info_tv_save)
    TextView tvSave;
    @BindView(R.id.activity_update_info_edt_full_name)
    EditText edName;
    @BindView(R.id.activity_update_info_iv_avatar_driver)
    CircleImageView imgAvatar;
    @BindView(R.id.activity_update_info_edt_phone_number)
    EditText edPhoneNumber;
    @BindView(R.id.activity_update_info_edt_username)
    EditText edUserName;
    private AccuontPresenter presenter;

    private SharedPreferences mPrefs;
    private AppPreferencesHelper appPreferencesHelper;
    private DataManager dataManager;

    public static void startActivity(Activity context){
        context.startActivity(new Intent(context,AccountDetailActivity.class));
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
        return R.layout.activity_account_detail;
    }

    @Override
    protected Merlin initMerlin() {
        return new Merlin.Builder()
                .withConnectableCallbacks()
                .withDisconnectableCallbacks()
                .withBindableCallbacks()
                .build(this);
    }

    @Override
    protected void initData() {
        mPrefs = context.getSharedPreferences("", Context.MODE_PRIVATE);
        appPreferencesHelper = new AppPreferencesHelper(mPrefs,this);
        dataManager = new DataManager(appPreferencesHelper);
        presenter = new AccuontPresenter(this,this);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void addEvents() {
        edUserName.setText(dataManager.getusername());
        edName.setText(dataManager.getName());
        edPhoneNumber.setText(dataManager.getphone());
        loadAvatar(Server.duongdananh+dataManager.getImage(),imgAvatar);

        addDisposable(RxView.clicks(imgAvatar).throttleFirst(2, TimeUnit.SECONDS)
        .compose(bindToLifecycle())
        .subscribe(unit -> {
            showImage();
        }));

        addDisposable(RxView.clicks(tvSave).throttleFirst(2,TimeUnit.SECONDS)
                .compose(bindToLifecycle())
        .subscribe(unit -> {
            String name = edName.getText().toString().trim();
            if (StringUtils.isEmpty(name)){
                Toasty.warning(this,R.string.error_null).show();
            }else if (!ValidateUtils.isVaidFullName(name)){
                Toasty.warning(this,R.string.error_ky_tu).show();
            }else {
                ShowLoading(true);
                if (bitmap != null){
                    presenter.UpdateUser(name,imageToString(bitmap));
                }else {
                    presenter.UpdateUser(name,"");
                }
            }
        }));
        addDisposable(RxView.clicks(imgBack).throttleFirst(2,TimeUnit.SECONDS)
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imgAvatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void ShowSuccuer(String name,String images) {
        ShowLoading(false);
        dataManager.updateUserInfoSharedPreference(MainActivity.ID,name,images,
                MainActivity.USERNAME,MainActivity.PASSWORD,MainActivity.PHONE,true);
        alerter(R.string.succer_account);
        addDisposable(Observable.just(0).delay(1600, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    finish();
                }));
    }

    @Override
    public void ShowError(int error) {
        Toasty.error(context,error).show();
        ShowLoading(false);
    }

    @Override
    public void ShowLoading(boolean show) {
        if (show){
            LoadingDialog.getInstance().showLoading(this);
        }else {
            LoadingDialog.getInstance().hideLoading();
        }
    }
}
