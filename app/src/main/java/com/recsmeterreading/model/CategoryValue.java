package com.recsmeterreading.model;

import com.google.gson.annotations.SerializedName;

public class CategoryValue {
    @SerializedName("CATEGORY")
    private String category;

    @SerializedName("VALUE")
    private String value;

    public String getCategory() {
        return category;
    }

    public String getValue() {
        return value;
    }
}
