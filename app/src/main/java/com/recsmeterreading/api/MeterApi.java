package com.recsmeterreading.api;

import com.recsmeterreading.model.CategoryDetails;
import com.recsmeterreading.model.ReportDetails;
import com.recsmeterreading.model.ServiceDetails;
import com.recsmeterreading.model.UnbilledServices;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MeterApi {
//    https://newsapi.org/v2/top-headlines?country=in&apiKey=c19366b11c0440848041a33b1745e3d1
    @GET("lapi.php")
    Call<ReportDetails> getResponse(@Query("rquest") String typeReport,
                                    @Query("areacode") String areaCode,
                                    @Query("category") String category);

    @GET("lapi.php")
    Call<ServiceDetails> getTotalService(@Query("rquest") String typeReport,
                                         @Query("areacode") String areaCode,
                                         @Query("category") String category);

    @GET("lapi.php")
    Call<UnbilledServices> getUnbilledServices(@Query("rquest") String typeReport,
                                               @Query("areacode") String areaCode);






}
