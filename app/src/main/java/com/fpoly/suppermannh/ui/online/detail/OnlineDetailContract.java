package com.fpoly.suppermannh.ui.online.detail;

public interface OnlineDetailContract {
    void showban(int soban);
    void showError(int error);
    void showErrorOnline(int error);
    void showSuccess();
    void showLoading(boolean show);
    void showMesing();
}
