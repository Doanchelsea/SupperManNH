package com.fpoly.suppermannh.ui.account.password;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.api.Server;
import com.fpoly.suppermannh.base.BaseActivity;
import com.fpoly.suppermannh.model.local.AppPreferencesHelper;
import com.fpoly.suppermannh.model.local.DataManager;
import com.fpoly.suppermannh.ui.account.detail.AccountDetailActivity;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class PasswordActivity extends BaseActivity implements Connectable, Bindable, Disconnectable {
    private SharedPreferences mPrefs;
    private AppPreferencesHelper appPreferencesHelper;
    private DataManager dataManager;

    @BindView(R.id.activity_update_info_iv_avatar_password)
    CircleImageView imgAvatar;
    @BindView(R.id.activity_ed_password)
    EditText edPassword;
    @BindView(R.id.activity_ed_change_password)
    EditText edChange;
    @BindView(R.id.activity_update_ed_password)
    EditText edUpdate;
    @Override
    protected void onResume() {
        super.onResume();
        registerBindable(this);
        registerConnectable(this);
        registerDisconnectable(this);
    }
    public static void startActivity(Activity context){
        context.startActivity(new Intent(context, PasswordActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_password;
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
        mPrefs = context.getSharedPreferences("", Context.MODE_PRIVATE);
        appPreferencesHelper = new AppPreferencesHelper(mPrefs,this);
        dataManager = new DataManager(appPreferencesHelper);
    }

    @Override
    protected void initToolbar() {
        loadAvatar(Server.duongdananh+dataManager.getImage(),imgAvatar);
    }

    @Override
    protected void addEvents() {
        
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
}
