package com.fpoly.suppermannh.ui.registration;

public interface RegistrationContract {
    void error(int error);
    void success();
    void showLoading(boolean show);
}
