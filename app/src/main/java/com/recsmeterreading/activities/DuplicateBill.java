package com.recsmeterreading.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.recsmeterreading.R;
import com.recsmeterreading.Utils.DialogAction;
import com.recsmeterreading.Utils.Globals;
import com.recsmeterreading.Utils.Utils;
import com.recsmeterreading.adapters.PendingBillAdapter;
import com.recsmeterreading.model.GetBillDetailsModel;
import com.recsmeterreading.model.LocalBillModel;

import java.util.ArrayList;

public class DuplicateBill extends AppCompatActivity {

    public ArrayList<LocalBillModel> getBillDetailsModel = new ArrayList<>();
    private EditText serviceNo;
    private Button proceedPrint;
    GetBillDetailsModel billdetailModel;
    LocalBillModel refModel;
    private String scNo;
    private DialogAction dialogAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duplicate_bill);

        Toolbar myToolbar = findViewById(R.id.duplicate_bill_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionbar = getSupportActionBar();
        if (getSupportActionBar() != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setTitle("డుప్లికేట్ బిల్లు ");

        }
        serviceNo = findViewById(R.id.service_no_print);
        proceedPrint = findViewById(R.id.proceed_to_print);
        getBillDetailsModel.clear();
//        refModel = new LocalBillModel();
        dialogAction = new DialogAction(this);

        proceedPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(serviceNo.getText().toString().trim().length() == 0){
                    Globals.showToast(DuplicateBill.this, "Enter Service Number");
                }else if(serviceNo.getText().toString().trim().length() < 10){
                    Globals.showToast(DuplicateBill.this, "Service Number must be 10 digits");
                }else {
                    scNo = serviceNo.getText().toString().trim().substring(0,5)+
                            " "+
                            serviceNo.getText().toString().trim().substring(5);
                    new getDataFromDbAsync().execute(scNo);
                }

            }
        });
    }

    private class BackgroundTaskRetriveDuplicateBill extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogAction.showDialog("Duplicate Bill","Retrieving Duplicate Bills for print");
//            Toast.makeText(PendingBillActivity.this,"back",Toast.LENGTH_SHORT).show();

        }

        @Override
        protected String doInBackground(String... param) {

            String status = "false";

            Log.v("exce"," do in back"+param[0]);
            final SQLiteDatabase sqLiteDatabase;
            sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(Utils.getSharedInstance().databasePath + "RecsBillingData", null);


            String sqlQuery = "SELECT * FROM CustomerServices " + "WHERE SERVICENO='" + param[0] + "'";
            Cursor c = sqLiteDatabase.rawQuery(sqlQuery, null);

            try{
                if(c.getCount()>0){
                    status = "ok";
                    if (c.moveToFirst()) {
                        do {
                            Log.v("dorami", "onCreate: " + DatabaseUtils.dumpCursorToString(c));
                            Log.v("dorami", "onCreate: " + c.getColumnIndex("count(SERVICENO)"));
                            // Log.d("dorami", "onCreate: " + c.getColumnIndex("catg"));

                            billdetailModel = new GetBillDetailsModel();
                            billdetailModel.setId(c.getString(0));
                            billdetailModel.setAREACODE(c.getString(1));
                            billdetailModel.setMonth(c.getString(2));
                            billdetailModel.setSCNO(c.getString(3));
                            billdetailModel.setCategory(c.getString(4));
                            billdetailModel.setSubcategory(c.getString(5));
                            billdetailModel.setSlbamoth(c.getString(6));
                            billdetailModel.setGroupid(c.getString(7));
                            billdetailModel.setOpen_bal(c.getString(8));
                            billdetailModel.setClose_bal(c.getString(9));
                            billdetailModel.setDj(c.getString(10));
                            billdetailModel.setAdj_amount(c.getString(11));
                            billdetailModel.setPHASE(c.getString(12));
                            billdetailModel.setOpmonth(c.getString(13));
                            billdetailModel.setOpstatus(c.getString(14));
                            billdetailModel.setOPRDNG(c.getString(15));
                            billdetailModel.setSTATUS(c.getString(16));
                            billdetailModel.setUNITS(c.getString(17));
                            billdetailModel.setDATEDIS(c.getString(18));
                            billdetailModel.setMETERCY(c.getString(19));
                            billdetailModel.setEDINT(c.getString(20));
                            billdetailModel.setCLEDUTY(c.getString(21));
                            billdetailModel.setCELEDINT(c.getString(22));
                            billdetailModel.setOPEDINT(c.getString(23));
                            billdetailModel.setCAPCHRGS(c.getString(24));
                            billdetailModel.setSURCHRGS(c.getString(25));
                            billdetailModel.setCSSURCHG(c.getString(26));
                            billdetailModel.setOSSURCHG(c.getString(27));
                            billdetailModel.setOPFSA(c.getString(28));
                            billdetailModel.setCLFSA(c.getString(29));
                            billdetailModel.setCL2(c.getString(30));
                            billdetailModel.setCL3(c.getString(31));
                            billdetailModel.setCL4(c.getString(32));
                            billdetailModel.setCL5(c.getString(33));
                            billdetailModel.setCL6(c.getString(34));
                            billdetailModel.setLRPNO(c.getString(35));
                            billdetailModel.setLPDATE(c.getString(36));
                            billdetailModel.setLPAMT(c.getString(37));
                            billdetailModel.setPOINTS(c.getString(38));
                            billdetailModel.setCAPFLAG(c.getString(39));
                            billdetailModel.setCAPSURCH(c.getString(40));
                            billdetailModel.setSBSFLAG(c.getString(41));
                            billdetailModel.setOPDEMAN(c.getString(42));
                            billdetailModel.setCLDEMAN(c.getString(43));
                            billdetailModel.setCLRDNG(c.getString(44));
                            billdetailModel.setCLSTATUS(c.getString(45));
                            billdetailModel.setCSM_CONSUMER_NAME(c.getString(46));
                            billdetailModel.setCSM_ADDRESS3(c.getString(47));
                            billdetailModel.setCSM_CONNECTED_LOAD(c.getString(48));
                            billdetailModel.setPREAVG(c.getString(49));
                            billdetailModel.setNEWARREARS(c.getString(50));
                          //  billdetailModel.setClose_bal(c.getString(50));
                            billdetailModel.setCSM_AADHAAR_NO(c.getString(51));
                            billdetailModel.setCSM_CASTE(c.getString(52));
                            billdetailModel.setCSM_PHONE_NO(c.getString(53));
                            billdetailModel.setCSM_SHARE(c.getString(54));
                            billdetailModel.setCSM_METER_NO(c.getString(55));




                        } while (c.moveToNext());
                    }
                }

                c.close();
                sqLiteDatabase.close();
            }catch (Exception e){
                Log.v("exce","+e"+e);
            }


            return status;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialogAction.hideDialog();
            if(s.equals("ok")){
//                Toast.makeText(PendingBillActivity.this,""+s,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(DuplicateBill.this, BillInfoActivity.class);
//            billdetailModel.setCUSCHG(billdetailModel.getCSSURCHG());
                billdetailModel.setOPRDNG(refModel.getOLDRDG_KWH());
                billdetailModel.setCLRDNG(refModel.getPRESRDG_KWH());
                billdetailModel.setUNITS(refModel.getUNITS());
                billdetailModel.setENGCHG(refModel.getENGCHG());
                billdetailModel.setCUSCHG(refModel.getCUSCHG());
                billdetailModel.setEDCHG(refModel.getEDCHG());
                billdetailModel.setBILLAMT(refModel.getBILLAMT());
                billdetailModel.setAdj_amount(refModel.getADJAMT());
                billdetailModel.setLASTMCONSUPTION(refModel.getLASTCONSUMPTION());
//            String[] abc = billdetailModel.getLASTMCONSUPTION().split(" ");
                billdetailModel.setNkt(refModel.getNKT());
                billdetailModel.setFIXEDCHG(refModel.getFIXEDCHARGE());
                billdetailModel.setCSM_AADHAAR_NO(refModel.getAADHARNO());
                billdetailModel.setCSM_PHONE_NO(refModel.getPHONENO());
                billdetailModel.setCSM_METER_NO(refModel.getNEWMETERNO());
                billdetailModel.setBILLDATE(refModel.getBILLNO());
                billdetailModel.setSTATUS(refModel.getSTATUS());
                billdetailModel.setSURCHRGS(refModel.getLORG());
                if(refModel.getMETERBRUNTVALUE().length()>0){
                    //for meter charges
                    i.putExtra("charge",Integer.valueOf(refModel.getMETERBRUNTVALUE()));
                    billdetailModel.setBurntValue(refModel.getMETERBRUNTVALUE());
                }
                billdetailModel.setDUEDATE(refModel.getDUEDATE());
                billdetailModel.setDISCDATE(refModel.getDISCONDATE());
                i.putExtra("model", new Gson().toJson(billdetailModel));
               // i.putExtra("lastcon",Integer.valueOf(billdetailModel.getLASTMCONSUPTION()));

//            i.putExtra("lastcon",Integer.parseInt(refModel.getSTATUS()));
                Log.e("model",""+new Gson().toJson(billdetailModel));
                Log.e("model l",""+billdetailModel.getLASTMCONSUPTION());
//            i.putExtra("lastcon",Integer.parseInt(billdetailModel.getLASTMCONSUPTION()));

                startActivity(i);
            }else {

                Toast.makeText(DuplicateBill.this,"Area Code should be same as you want to print the bill",Toast.LENGTH_SHORT).show();
            }


        }
    }

    private class getDataFromDbAsync extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogAction.showDialog("Duplicate Bill","Retrieving Duplicate Bills");
            Toast.makeText(DuplicateBill.this,"Loading...",Toast.LENGTH_SHORT).show();
//            ll_loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
//            getDataFromDb(params[0]);
            return getDataFromDb(params[0]);
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            dialogAction.hideDialog();
            if(aVoid.equals("ok")){
                //success

                new BackgroundTaskRetriveDuplicateBill().execute(scNo);
            }else {
                //failed

                Toast.makeText(DuplicateBill.this,"No Service No Found...",Toast.LENGTH_SHORT).show();
            }


        }
    }

    private String getDataFromDb(String param) {
        String status ="false";
        final SQLiteDatabase sqLiteDatabase;
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(Utils.getSharedInstance().databasePath + "RecsBillingData", null);

        String sqlQuery = "SELECT * FROM Billsgen "+ "WHERE SERVICENO='" + param + "'";
        Cursor c = sqLiteDatabase.rawQuery(sqlQuery, null);


        if (c.moveToFirst()) {
            do {
                Log.e("dorami", "onCreate: " + DatabaseUtils.dumpCursorToString(c));
                Log.e("dorami", "onCreate: " + c.getColumnIndex("count(SERVICENO)"));
                // Log.d("dorami", "onCreate: " + c.getColumnIndex("catg"));

//                if(c.getString(16).equals("0")) {
                status ="ok";

                    refModel = new LocalBillModel();
                    refModel.setSERVICENO(c.getString(0));
                    refModel.setAREACODE(c.getString(1));
                    refModel.setOLDRDG_KWH(c.getString(2));
                    refModel.setPRESRDG_KWH(c.getString(3));
                    refModel.setUNITS(c.getString(4));
                    refModel.setENGCHG(c.getString(5));
                    refModel.setCUSCHG(c.getString(6));
                    refModel.setEDCHG(c.getString(7));
                    refModel.setBILLAMT(c.getString(8));
                    refModel.setADJAMT(c.getString(9));
                    refModel.setTOTALAMT(c.getString(10));
                    refModel.setCATEGORY(c.getString(11));
                    refModel.setCONSUMER_NAME(c.getString(12));
                    refModel.setBILL_DATE(c.getString(13));
                    refModel.setSTATUS(c.getString(14));
                    refModel.setLORG(c.getString(15));

                    refModel.setLASTCONSUMPTION(c.getString(17));
                    refModel.setNKT(c.getString(18));
                    refModel.setFIXEDCHARGE(c.getString(19));
                    refModel.setMETERBRUNTVALUE(c.getString(20));
                    refModel.setAADHARNO(c.getString(21));
                    refModel.setPHONENO(c.getString(22));
                    refModel.setNEWMETERNO(c.getString(23));
                    refModel.setBILLNO(c.getString(24));
                    refModel.setDUEDATE(c.getString(28));
                    refModel.setDISCONDATE(c.getString(29));

//                "OPSTATUS TEXT," + //praul 6th march 2019 25
//                        "BILLDATE TEXT," +26
//                        "LPAMT TEXT," +27
//                        "DUEDATE TEXT," +28
//                        "DISCONDATE TEXT," 29+
//                        "LOAD TEXT," +
//                        "CASTE TEXT," +
//                        "NEWARREARS TEXT"


                    getBillDetailsModel.add(refModel);
//                }




// do what ever you want here
            } while (c.moveToNext());
        }
        c.close();
        sqLiteDatabase.close();


        return status;
    }
}
