package com.fpoly.suppermannh.model.local;

public class DataManager implements PreferencesHelper {

    private PreferencesHelper preferencesHelper;

    public DataManager(PreferencesHelper preferencesHelper) {
        this.preferencesHelper = preferencesHelper;
    }

    @Override
    public void setToken(String token) {
        preferencesHelper.setToken(token);
    }

    @Override
    public String getToken() {
        return preferencesHelper.getToken();
    }

    @Override
    public void setLoggedIn(boolean isLoggedIn) {
        preferencesHelper.setLoggedIn(isLoggedIn);
    }

    @Override
    public boolean IsLoggedIn() {
        return preferencesHelper.IsLoggedIn();
    }

    @Override
    public void setID(String id) {
        preferencesHelper.setID(id);
    }

    @Override
    public String getID() {
        return preferencesHelper.getID();
    }

    @Override
    public void clearID() {
        preferencesHelper.clearID();
    }

    @Override
    public void setName(String name) {
        preferencesHelper.setName(name);
    }

    @Override
    public String getName() {
        return preferencesHelper.getName();
    }

    @Override
    public void clearName() {
        preferencesHelper.clearName();
    }

    @Override
    public void setImage(String image) {
        preferencesHelper.setImage(image);
    }

    @Override
    public String getImage() {
        return preferencesHelper.getImage();
    }

    @Override
    public void clearImage() {
        preferencesHelper.clearImage();
    }

    @Override
    public void setusername(String username) {
        preferencesHelper.setusername(username);
    }

    @Override
    public String getusername() {
        return preferencesHelper.getusername();
    }

    @Override
    public void clearusername() {
        preferencesHelper.clearusername();
    }

    @Override
    public void setpassword(String password) {
        preferencesHelper.setpassword(password);
    }

    @Override
    public String getpassword() {
        return preferencesHelper.getpassword();
    }

    @Override
    public void clearpassword() {
        preferencesHelper.clearpassword();
    }

    @Override
    public void setphone(String phone) {
        preferencesHelper.setphone(phone);
    }

    @Override
    public String getphone() {
        return preferencesHelper.getphone();
    }

    @Override
    public void clearphone() {
        preferencesHelper.clearphone();
    }

    public void updateUserInfoSharedPreference(String id,String name,String image,String username,String password,
                                               String phone, boolean isLoggedIn) {
        setID(id);
        setImage(image);
        setName(name);
        setusername(username);
        setpassword(password);
        setphone(phone);
        setLoggedIn(isLoggedIn);
    }
    public void clearAllUserInfo() {
        clearID();
        clearImage();
        clearName();
        clearusername();
        clearpassword();
        clearphone();
    }
}
