package com.recsmeterreading.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServiceNo implements Serializable {

    @SerializedName("SCNO")
    private String SCNO;

    public ServiceNo(String SCNO) {
        this.SCNO = SCNO;
    }

    public String getSCNO() {
        return SCNO;
    }

    public void setSCNO(String SCNO) {
        this.SCNO = SCNO;
    }
}
