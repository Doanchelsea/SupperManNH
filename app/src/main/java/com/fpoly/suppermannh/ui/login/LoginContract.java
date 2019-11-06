package com.fpoly.suppermannh.ui.login;

public interface LoginContract {
    void ShowSuccer();
    void ShowError(int error);
    void showLoading(boolean show);
}
