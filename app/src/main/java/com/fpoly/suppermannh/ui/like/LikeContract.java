package com.fpoly.suppermannh.ui.like;

public interface LikeContract {
    void showSuccess();
    void showNull();
    void showError(int error);
    void showLoading(boolean show);
}
