package com.recsmeterreading.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryDetails {

    @SerializedName("Services")
    private List<CategoryValue> categoryValues;

    @SerializedName("total_records")
    private String total_records;

    public List<CategoryValue> getCategoryValues() {
        return categoryValues;
    }

    public String getTotal_records() {
        return total_records;
    }
}
