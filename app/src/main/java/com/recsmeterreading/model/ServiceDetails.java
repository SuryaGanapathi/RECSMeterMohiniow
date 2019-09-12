package com.recsmeterreading.model;

import java.util.List;

public class ServiceDetails {

    private List<ServiceNo> Service_numbers;
    private String CATEGORY;
    private String value;

    public ServiceDetails(List<ServiceNo> service_numbers, String CATEGORY, String value) {
        Service_numbers = service_numbers;
        this.CATEGORY = CATEGORY;
        this.value = value;
    }

    public List<ServiceNo> getService_numbers() {
        return Service_numbers;
    }

    public void setService_numbers(List<ServiceNo> service_numbers) {
        Service_numbers = service_numbers;
    }

    public String getCATEGORY() {
        return CATEGORY;
    }

    public void setCATEGORY(String CATEGORY) {
        this.CATEGORY = CATEGORY;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
