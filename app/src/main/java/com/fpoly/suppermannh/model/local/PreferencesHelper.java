package com.fpoly.suppermannh.model.local;

public interface PreferencesHelper {

    void setToken(String token);

    String getToken();

    void setLoggedIn(boolean isLoggedIn);

    boolean IsLoggedIn();

    void setID(String id);

    String getID();

    void clearID();

    void setName(String name);

    String getName();

    void clearName();

    void setImage(String image);

    String getImage();

    void clearImage();

    void setusername(String username);

    String getusername();

    void clearusername();

    void setpassword(String password);

    String getpassword();

    void clearpassword();

    void setphone(String phone);

    String getphone();

    void clearphone();
}
