package com.fpoly.suppermannh.ui.bandat.cancel;

public interface CancelContract {
    void error(int error);
    void success();
    void showLoading(boolean show);
}
