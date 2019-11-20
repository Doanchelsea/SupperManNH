package com.fpoly.suppermannh.model.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.fpoly.suppermannh.model.Contract;

public class AppPreferencesHelper implements PreferencesHelper {

    private static final String ID = Contract.ID;
    private static final String NAME = Contract.NAME;
    private static final String IMAGE = Contract.IMAGE;
    private static final String LOGGED = Contract.LOGGED;
    private static final String USERNAME = Contract.USERNAME;
    private static final String PASSWORD = Contract.PASSWORD;
    private static final String PHONE = Contract.PHONE;
    private static final String TOKEN = Contract.TOKEN;

    private SharedPreferences mPrefs;
    Context context;

    public AppPreferencesHelper(SharedPreferences mPrefs, Context context) {
        this.mPrefs = mPrefs;
        this.context = context;
    }

    @Override
    public void setToken(String token) {
        mPrefs.edit().putString(TOKEN,token).apply();
    }

    @Override
    public String getToken() {
        return mPrefs.getString(TOKEN,"");
    }

    @Override
    public void setLoggedIn(boolean isLoggedIn) {
        mPrefs.edit().putBoolean(LOGGED, isLoggedIn).apply();
    }

    @Override
    public boolean IsLoggedIn() {
        return mPrefs.getBoolean(LOGGED, false);
    }

    @Override
    public void setID(String id) {
        mPrefs.edit().putString(ID, id).apply();
    }

    @Override
    public String getID() {
        return mPrefs.getString(ID, "");
    }

    @Override
    public void clearID() {
        mPrefs.edit().remove(ID).apply();
    }

    @Override
    public void setName(String name) {
        mPrefs.edit().putString(NAME, name).apply();
    }

    @Override
    public String getName() {
        return mPrefs.getString(NAME, "");
    }

    @Override
    public void clearName() {
        mPrefs.edit().remove(NAME).apply();
    }

    @Override
    public void setImage(String image) {
        mPrefs.edit().putString(IMAGE, image).apply();
    }

    @Override
    public String getImage() {
        return mPrefs.getString(IMAGE, "");
    }

    @Override
    public void clearImage() {
        mPrefs.edit().remove(IMAGE).apply();
    }

    @Override
    public void setusername(String username) {
        mPrefs.edit().putString(USERNAME,username).apply();
    }

    @Override
    public String getusername() {
        return mPrefs.getString(USERNAME,"");
    }

    @Override
    public void clearusername() {
        mPrefs.edit().remove(USERNAME).apply();
    }

    @Override
    public void setpassword(String password) {
        mPrefs.edit().putString(PASSWORD,password).apply();
    }

    @Override
    public String getpassword() {
        return mPrefs.getString(PASSWORD,"");
    }

    @Override
    public void clearpassword() {
        mPrefs.edit().remove(PASSWORD).apply();
    }

    @Override
    public void setphone(String phone) {
        mPrefs.edit().putString(PHONE,phone).apply();
    }

    @Override
    public String getphone() {
        return mPrefs.getString(PHONE,"");
    }

    @Override
    public void clearphone() {
        mPrefs.edit().remove(PHONE).apply();
    }
}
