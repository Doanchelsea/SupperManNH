package com.fpoly.suppermannh.ui.bandat;

import com.fpoly.suppermannh.model.Manage;

import java.util.List;

public interface BandatContract {
    void errornull();
    void error(int error);
    void success(List<Manage> manages);
    void houst(String name,String lat,String lng);
}
