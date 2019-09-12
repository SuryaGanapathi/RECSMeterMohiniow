package com.recsmeterreading.connections;

import android.content.Context;

public class ServiceCalls {

    public static Object getReadingData(Context aContext, String id) {
        try {
            return Connection.callHttpGetRequestsV2(aContext, Constants.GET_READING_DATA + id, null);
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        }
    }

    public static Object postBills(Context aContext, String request) {
        try {
            return Connection.callHttpPostRequestsV2Jobj(aContext, Constants.POST_READING_DATA, request);
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        }
    }
}