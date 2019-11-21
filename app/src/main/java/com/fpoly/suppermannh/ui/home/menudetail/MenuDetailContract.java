package com.fpoly.suppermannh.ui.home.menudetail;

import com.fpoly.suppermannh.model.Houst;
import com.fpoly.suppermannh.model.Menu;

import java.util.List;

public interface MenuDetailContract {
    void showlist(List<Menu> menus, boolean show);
    void showError(int error);
    void showSuccess(List<Houst> housts,Menu menu);
}
