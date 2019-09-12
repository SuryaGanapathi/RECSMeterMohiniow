package com.recsmeterreading.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class UnbilledServices {

    @SerializedName("status")
    private String status;

    @SerializedName("Services")
    private List<ServiceNumber> Services;

    @SerializedName("total_records")
    private String total_records;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UnbilledServices(String status, List<ServiceNumber> services, String total_records) {
        this.status = status;
        Services = services;
        this.total_records = total_records;
    }

    public List<ServiceNumber> getServices() {
        return Services;
    }

    public void setServices(List<ServiceNumber> services) {
        Services = services;
    }

    public String getTotal_records() {
        return total_records;
    }

    public void setTotal_records(String total_records) {
        this.total_records = total_records;
    }
}
