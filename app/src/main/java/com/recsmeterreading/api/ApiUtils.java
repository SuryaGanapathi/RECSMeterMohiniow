package com.recsmeterreading.api;

public class ApiUtils {
    public static final String METER_URL="https://api.anyemi.com/ledger/";

    public static MeterApi getMeterApi(){
        return RetrofitClient.getClient(METER_URL).create(MeterApi.class);
    }

}
