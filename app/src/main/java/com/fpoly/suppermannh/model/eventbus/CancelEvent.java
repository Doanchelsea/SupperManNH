package com.fpoly.suppermannh.model.eventbus;

public class CancelEvent {
    String id;

    public CancelEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
