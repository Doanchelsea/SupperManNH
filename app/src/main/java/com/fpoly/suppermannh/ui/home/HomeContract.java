package com.fpoly.suppermannh.ui.home;

import androidx.core.view.ViewCompat;

public interface HomeContract {
    void showError(int error);
    void showSuccess();
    void dismisButton();
    void showLoading(boolean show);
}
