package com.fpoly.suppermannh.ui.account.history;

import com.fpoly.suppermannh.model.History;

import java.util.List;

public interface HistoryContract {
    void showList(List<History> histories, boolean show);
    void showError(int error);
}
