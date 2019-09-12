package com.recsmeterreading.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl){
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(200, TimeUnit.SECONDS);
        client.readTimeout(200, TimeUnit.SECONDS);
        client.writeTimeout(200, TimeUnit.SECONDS);
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client.build())
                    .build();
        }
        return retrofit;
    }
}
