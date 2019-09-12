package com.recsmeterreading.connections;

/**
 * Created by Rama on 17-09-2016.
 *
 * http://www.roommatesportal.com/api/Supplier/Login
 */

public class Constants {


  //  public static final String base_url = "https://dev.anyemi.com/webservices/ledger/";
    public static final String base_url = "https://api.anyemi.com/ledger/";
  //public static final String base_url = "https://api.anyemi.com/ledger/ledger_data.php?area_code=10107&start=20"
    public static final String VERSION_ID = "0.2";

   //public static final String GET_READING_DATA =base_url+"ledger.php?area_code=";

    public static final String GET_READING_DATA = base_url+"ledger_data.php?area=";
//    public static final String POST_READING_DATA =base_url+"curl_insert_bill_data.php";
    public static final String POST_READING_DATA =base_url+"lapi.php?rquest=billdata";
}
