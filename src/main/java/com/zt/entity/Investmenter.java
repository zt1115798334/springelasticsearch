package com.zt.entity;

import java.util.List;

public class Investmenter {
    private List<String> invList;
    private String invType;

    public Investmenter() {
    }

    public Investmenter(List<String> invList, String invType) {
        this.invList = invList;
        this.invType = invType;
    }

    public List<String> getInvList() {
        return invList;
    }

    public void setInvList(List<String> invList) {
        this.invList = invList;
    }

    public String getInvType() {
        return invType;
    }

    public void setInvType(String invType) {
        this.invType = invType;
    }
}
