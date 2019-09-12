package com.recsmeterreading.Database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.recsmeterreading.Utils.Utils;


public class SqliteDb {

    private SQLiteDatabase sqLiteDatabase;  //contains the sql lite database object
    private String db_Name = "RecsBillingData";
    private String TAG = "SqliteDb";


    /*creating databse in rci folder location */
    public SQLiteDatabase createDatabase() {

        try {
            sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(Utils.getSharedInstance().databasePath + db_Name, null, null);
        } catch (Exception e) {
            Log.e(TAG, "createDatabase exception : " + e.toString());
        }
        createTable();
        return sqLiteDatabase;
    }


    public SQLiteDatabase deleteTable() {

        try {
            sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(Utils.getSharedInstance().databasePath + db_Name, null, null);
        } catch (Exception e) {
            Log.e(TAG, "createDatabase exception : " + e.toString());
        }
        deleteDbTable();
        return sqLiteDatabase;
    }

    private void deleteDbTable() {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS CustomerServices");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Billsgen");

    }


    // This method will Create database table. if it already exist then retrieve data from it.
    private void createTable() {

        try {
            /*Creating table for CustomerCharges */

            String customerServicesQuery = "CREATE TABLE IF NOT EXISTS CustomerServices" +

                    "(" +
                    "id TEXT,"+
                    "AREACODE TEXT,"+
                    "month TEXT,"+
                    "SERVICENO TEXT PRIMARY KEY," +
                    "Category TEXT,"+
                    "Subcategory TEXT,"+
                    "Slbamoth TEXT,"+
                    "groupid TEXT," +
                    "open_bal TEXT,"+
                    "close_bal TEXT," +
                    "dj TEXT," +
                    "adj_amount TEXT," +
                    "phese TEXT,"+
                    "opmonth TEXT,"+
                    "opstatus TEXT,"+
                    "OPRDNG TEXT,"+
                    "STATUS TEXT,"+
                    "UNITS TEXT,"+
                    "DATEDIS TEXT,"+
                    "METERCY TEXT,"+  //Previous Dail Values  9999  10000 01
                    "EDINT TEXT,"+
                    "CLEDUTY TEXT,"+
                    "CELEDINT TEXT,"+
                    "OPEDINT TEXT,"+
                    "CAPCHRGS TEXT,"+
                    "SURCHRGS TEXT,"+
                    "CSSURCHG TEXT,"+
                    "OSSURCHG TEXT,"+
                    "OPFSA TEXT,"+
                    "CLFSA TEXT,"+
                    "CL2 TEXT,"+
                    "CL3 TEXT,"+
                    "CL4 TEXT,"+
                    "CL5 TEXT,"+
                    "CL6 TEXT,"+
                    "LRPNO TEXT,"+
                    "LPDATE TEXT,"+
                    "LPAMT TEXT,"+
                    "POINTS TEXT,"+
                    "CAPFLAG TEXT,"+
                    "CAPSURCH TEXT,"+
                    "SBSFLAG TEXT,"+
                    "OPDEMAN TEXT,"+
                    "CLDEMAN TEXT,"+
                    "CLRDNG TEXT,"+
                    "CLSTATUS TEXT,"+
                    "CSM_CONSUMER_NAME TEXT,"+
                    "CSM_ADDRESS3 TEXT,"+
                    "CSM_CONNECTED_LOAD TEXT,"+
                    "PREAVG TEXT,"+
                    "NEWARREARS TEXT," +
                    "CSM_AADHAAR_NO TEXT," +
                    "CSM_CASTE TEXT," +
                    "CSM_PHONE_NO TEXT," +
                    "CSM_SHARE TEXT," +
                    "CSM_METER_NO TEXT," +
                    "MD TEXT," +
                    "ERO TEXT" +
                    ")";


            String BillDetailsQuery = "CREATE TABLE IF NOT EXISTS Billsgen" +



                    "(" +
                    "SERVICENO TEXT PRIMARY KEY,"+
                    "AREACODE TEXT,"+
                    "OLDRDG_KWH TEXT,"+
                    "PRESRDG_KWH TEXT," +
                    "UNITS TEXT,"+
                    "ENGCHG TEXT,"+
                    "CUSCHG TEXT,"+
                    "EDCHG TEXT," +  // Electrical duty
                    "BILLAMT TEXT,"+
                    "ADJAMT TEXT," +
                    "TOTALAMT TEXT," +
                    "CATEGORY TEXT,"+
                    "CONSUMER_NAME TEXT,"+
                    "PRESDT TEXT,"+   //bill date
                    "STATUS TEXT,"+
                    "LORG TEXT,"+
                    "BILL_STATUS TEXT,"+
                    "LASTCONSUMPTIONUNIT TEXT,"+  //PRAFUL
                    "NKT TEXT,"+
                    "FIXEDCHARGE TEXT,"+
                    "METERBRUNTVALUE TEXT,"+
                    "AADHARNO TEXT,"+
                    "PHONENO TEXT,"+
                    "NEWMETERNO TEXT,"+
                    "BILLNO TEXT,"+
                    "OPSTATUS TEXT," + //praul 6th march 2019
                    "BILLDATE TEXT," +
                    "LPAMT TEXT," +
                    "DUEDATE TEXT," +
                    "DISCONDATE TEXT," +
                    "LOAD TEXT," +
                    "CASTE TEXT," +
                    "NEWARREARS TEXT" +
                    ")";


            String customerChargesQuery = "CREATE TABLE IF NOT EXISTS CustomerCharges" +
                            "(" +
                            "consumer_charges_id            TEXT PRIMARY KEY," +
                            "CONSMUER_CATEGORY_ID          TEXT," +
                            "SUB_CATEGORY_ID          TEXT," +
                            "CONSMUER_CATEGORY          TEXT," +
                            "CONSUMER_CHARGES        TEXT" +
                            ")";


            /*Creating table for MonthlyMinimumCharges*/

            String monthlyMinimumChargesQuery = "CREATE TABLE IF NOT EXISTS MonthlyMinimumCharges" +
                    "(" +
                    "CONSMUER_CATEGORY_ID            INTEGER ," +
                    "SUB_CATEGORY_ID          TEXT," +
                    "CONTRACTED_LOAD          TEXT," +
                    "SUPPLY_PHASE          TEXT," +
                    "MONTHLY_CHARGES          TEXT" +
                    " );";

            /*Creating table for SpotBillingStatusList*/
            String spotBillingStatusListQuery = "CREATE TABLE IF NOT EXISTS SpotBillingStatusList" +
                    "" +
                    "(" +
                    "status          INTEGER," +
                    "reason            TEXT" +
                    ")";


            /*Creating table for TariffRate*/
            String tariffRateQuery = "CREATE TABLE IF NOT EXISTS TariffRate" +
                    "(" +
                    "CONSMUER_CATEGORY_ID            TEXT," +
                    "SUB_CATEGORY_ID          TEXT," +
                    "CONSMUER_CONSUMPTION          INTEGER," +
                    "ENERGY_UNIT          TEXT," +
                    "FIXED_CHARGE          TEXT," +
                    "ENERGY_CHARGE          TEXT," +
                    "CATEGORY_DESCRIPTION          TEXT" +
                    ")";

            // Creating database table if it not exist.
            sqLiteDatabase.execSQL(customerChargesQuery);
            sqLiteDatabase.execSQL(customerServicesQuery);
            sqLiteDatabase.execSQL(monthlyMinimumChargesQuery);
            sqLiteDatabase.execSQL(spotBillingStatusListQuery);
            sqLiteDatabase.execSQL(tariffRateQuery);
            sqLiteDatabase.execSQL(BillDetailsQuery);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
