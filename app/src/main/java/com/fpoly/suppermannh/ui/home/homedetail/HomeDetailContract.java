package com.fpoly.suppermannh.ui.home.homedetail;

public interface HomeDetailContract {
    void showSuccess();
    void showDelete();
    void showError(int error);
    void showCountRatting(double count, int i);
}
