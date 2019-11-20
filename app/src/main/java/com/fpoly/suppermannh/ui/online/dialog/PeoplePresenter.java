package com.fpoly.suppermannh.ui.online.dialog;

import com.fpoly.suppermannh.model.People;

import java.util.List;

public class PeoplePresenter {

    PeopleContract contract;

    public PeoplePresenter(PeopleContract contract) {
        this.contract = contract;
    }

    public void getPeople(List<People> people){
        for (int i=0; i<100 ; i++){
            people.add(new People(1+i));
        }
        contract.show();
    }
}
