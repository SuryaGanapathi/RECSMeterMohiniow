package com.recsmeterreading.activities;

        import android.content.Intent;
        import android.database.Cursor;
        import android.database.DatabaseUtils;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.v7.app.ActionBar;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.Button;
        import android.widget.LinearLayout;
        import android.widget.ListView;
        import android.widget.Toast;


        import com.google.gson.Gson;
        import com.recsmeterreading.R;
        import com.recsmeterreading.Utils.Globals;
        import com.recsmeterreading.Utils.Utils;
        import com.recsmeterreading.adapters.PendingBillAdapter;
        import com.recsmeterreading.bgtask.BackgroundTask;
        import com.recsmeterreading.bgtask.BackgroundThread;
        import com.recsmeterreading.connections.ServiceCalls;
        import com.recsmeterreading.model.GetBillDetailsModel;
        import com.recsmeterreading.model.LocalBillModel;

        import java.util.ArrayList;

        import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;
        import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class PendingBillActivity extends AppCompatActivity {
    public ArrayList<LocalBillModel> getBillDetailsModel = new ArrayList<>();
    LocalBillModel GetBillDetailsModelForPrint, refModel;
    String  Current_service_number="";
    int Current_index;
    GetBillDetailsModel billdetailModel;

    public static PendingBillAdapter pendingBillAdapter;
    ListView listView;
    Button button;
    LinearLayout ll_loading;
    public int successCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_bill2);


        Toolbar myToolbar = findViewById(R.id.pending_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionbar = getSupportActionBar();
        if (getSupportActionBar() != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setTitle("పెండింగ్ బిల్లు");

        }
        listView = findViewById(R.id.pendingBillLV);
        getBillDetailsModel.clear();

        refModel = new LocalBillModel();

        button=findViewById(R.id.btn_submit);
        ll_loading=findViewById(R.id.ll_loading);

        ll_loading.setVisibility(View.GONE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_loading.setVisibility(View.VISIBLE);
                Current_index=0;
                Log.e("Request:",new Gson().toJson(getBillDetailsModel));
                Current_service_number=getBillDetailsModel.get(Current_index).getSERVICENO();

                getBillDetailsModel.get(Current_index).setBILLNO(getBillDetailsModel.get(Current_index).getSTATUS().substring(2));
                getBillDetailsModel.get(Current_index).setSTATUS("1");
//                getBillDetailsModel.get(Current_index).setBILLNO((getBillDetailsModel.get(Current_index).getBILLNO().replace("-",""))+(getBillDetailsModel.get(Current_index).getSERVICENO().replace(" ","")));

                String[] abc = getBillDetailsModel.get(Current_index).getNKT().split(" ");
                getBillDetailsModel.get(Current_index).setNKT(abc[0]);

                if(getBillDetailsModel.get(Current_index).getOPSTATUS().length() == 1){
                    getBillDetailsModel.get(Current_index).setOPSTATUS("0".concat(getBillDetailsModel.get(Current_index).getOPSTATUS()));
                }

                getBillDetailsModel.get(Current_index).setTOTALAMT(getBillDetailsModel.get(Current_index).getBILLAMT());
                double totalAmount = 0;
                if(getBillDetailsModel.get(Current_index).getCASTE().equals("SC") || getBillDetailsModel.get(Current_index).getCASTE().equals("ST")){

                    totalAmount= Double.parseDouble(getBillDetailsModel.get(Current_index).getBILLAMT())
                            - Double.parseDouble(getBillDetailsModel.get(Current_index).getNEWARREARS())
                            + Double.parseDouble(getBillDetailsModel.get(Current_index).getADJAMT());


                }else {
                    totalAmount = Double.parseDouble(getBillDetailsModel.get(Current_index).getBILLAMT())
                            - Double.parseDouble(getBillDetailsModel.get(Current_index).getNEWARREARS());
                }

                getBillDetailsModel.get(Current_index).setBILLAMT(String.valueOf(totalAmount));

                Log.e("Request FINAL:",new Gson().toJson(getBillDetailsModel));
                postInputReadingDataToServer(new Gson().toJson(getBillDetailsModel.get(Current_index)));
            }
        });

        new getDataFromDbAsync().execute();



    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    private String prepareRequest() {
        Gson gson= new Gson();
        String s= gson.toJson(getBillDetailsModel);
        Log.e("Request ", " " + s);
        return s;
    }

    private void postInputReadingDataToServer(final String localBillModel) {

        new BackgroundTask(PendingBillActivity.this, new BackgroundThread() {
            @Override
            public Object runTask() {
                Log.e("parameters :",localBillModel);

                return ServiceCalls.postBills(PendingBillActivity.this,localBillModel);
            }

            @Override
            public void taskCompleted(Object data) {
                String statusO = data.toString();

                if (data != null && !data.equals("") && data.toString().equals("{\"status\":\"success\",\"msg\":\"Successfully Inserted\"}") ) {
                    successCount++;
                    Log.e("Response 1 :", " " + data.toString());
//                    Globals.showToast(PendingBillActivity.this,""+successCount+" records updated");

                    final SQLiteDatabase sqLiteDatabase;
                    sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(Utils.getSharedInstance().databasePath + "RecsBillingData", null);
                    String strSQL = "UPDATE Billsgen SET BILL_STATUS = "+"1 " + "WHERE SERVICENO='"+ Current_service_number+"'";
                    Log.e("Query",strSQL);
                    sqLiteDatabase.execSQL(strSQL);



                    if(Current_index < getBillDetailsModel.size()-1){
                        Current_index=Current_index+1;
                        Current_service_number=getBillDetailsModel.get(Current_index).getSERVICENO();

                        getBillDetailsModel.get(Current_index).setBILLNO(getBillDetailsModel.get(Current_index).getSTATUS().substring(2));
                        getBillDetailsModel.get(Current_index).setSTATUS("1");

                        String[] abc = getBillDetailsModel.get(Current_index).getNKT().split(" ");
                        getBillDetailsModel.get(Current_index).setNKT(abc[0]);

                        if(getBillDetailsModel.get(Current_index).getOPSTATUS().length() == 1){
                            getBillDetailsModel.get(Current_index).setOPSTATUS("0".concat(getBillDetailsModel.get(Current_index).getOPSTATUS()));
                        }

                        getBillDetailsModel.get(Current_index).setTOTALAMT(getBillDetailsModel.get(Current_index).getBILLAMT());
                        double totalAmount = 0;
                        if(getBillDetailsModel.get(Current_index).getCASTE().equals("SC") || getBillDetailsModel.get(Current_index).getCASTE().equals("ST")){

                            totalAmount= Double.parseDouble(getBillDetailsModel.get(Current_index).getBILLAMT())
                                    - Double.parseDouble(getBillDetailsModel.get(Current_index).getNEWARREARS())
                                    + Double.parseDouble(getBillDetailsModel.get(Current_index).getADJAMT());


                        }else {
                            totalAmount = Double.parseDouble(getBillDetailsModel.get(Current_index).getBILLAMT())
                                    - Double.parseDouble(getBillDetailsModel.get(Current_index).getNEWARREARS());
                        }

                        getBillDetailsModel.get(Current_index).setBILLAMT(String.valueOf(totalAmount));

                        Log.e("Request FINAL2:",new Gson().toJson(getBillDetailsModel));

                        postInputReadingDataToServer(new Gson().toJson(getBillDetailsModel.get(Current_index)));

                    }else {
                        ll_loading.setVisibility(View.GONE);
                        getBillDetailsModel.clear();
                        new getDataFromDbAsync().execute();
                        sqLiteDatabase.close();
                    }

                } else {
                    ll_loading.setVisibility(View.GONE);
                    Globals.showToast(getApplicationContext(), ""+data.toString());
                }

            }
        }).execute();

    }


    private void getDataFromDb() {

        final SQLiteDatabase sqLiteDatabase;
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(Utils.getSharedInstance().databasePath + "RecsBillingData", null);

        String sqlQuery = "SELECT * FROM Billsgen ";
        Cursor c = sqLiteDatabase.rawQuery(sqlQuery, null);


        if (c.moveToFirst()) {
            do {
                Log.e("dorami", "onCreate: " + DatabaseUtils.dumpCursorToString(c));
                Log.e("dorami", "onCreate: " + c.getColumnIndex("count(SERVICENO)"));
                // Log.d("dorami", "onCreate: " + c.getColumnIndex("catg"));
                Log.e("bill status",""+c.getString(16));

                if(c.getString(16).equals("0")) {

                    GetBillDetailsModelForPrint = new LocalBillModel();
                    GetBillDetailsModelForPrint.setSERVICENO(c.getString(0));
                    GetBillDetailsModelForPrint.setAREACODE(c.getString(1));
                    GetBillDetailsModelForPrint.setOLDRDG_KWH(c.getString(2));
                    GetBillDetailsModelForPrint.setPRESRDG_KWH(c.getString(3));
                    GetBillDetailsModelForPrint.setUNITS(c.getString(4));
                    GetBillDetailsModelForPrint.setENGCHG(c.getString(5));
                    GetBillDetailsModelForPrint.setCUSCHG(c.getString(6));
                    GetBillDetailsModelForPrint.setEDCHG(c.getString(7));
                    GetBillDetailsModelForPrint.setBILLAMT(c.getString(8));
                    GetBillDetailsModelForPrint.setADJAMT(c.getString(9));
                    GetBillDetailsModelForPrint.setTOTALAMT(c.getString(10));
                    GetBillDetailsModelForPrint.setCATEGORY(c.getString(11));
                    GetBillDetailsModelForPrint.setCONSUMER_NAME(c.getString(12));
                    GetBillDetailsModelForPrint.setBILL_DATE(c.getString(13));
                    GetBillDetailsModelForPrint.setSTATUS(c.getString(14));
                    GetBillDetailsModelForPrint.setLORG(c.getString(15));

                    GetBillDetailsModelForPrint.setLASTCONSUMPTION(c.getString(17));
                    GetBillDetailsModelForPrint.setNKT(c.getString(18));
                    GetBillDetailsModelForPrint.setFIXEDCHARGE(c.getString(19));
                    GetBillDetailsModelForPrint.setMETERBRUNTVALUE(c.getString(20));
                    GetBillDetailsModelForPrint.setAADHARNO(c.getString(21));
                    GetBillDetailsModelForPrint.setPHONENO(c.getString(22));
                    GetBillDetailsModelForPrint.setNEWMETERNO(c.getString(23));
                    GetBillDetailsModelForPrint.setBILLNO(c.getString(24));

                    GetBillDetailsModelForPrint.setOPSTATUS(c.getString(25));
                    GetBillDetailsModelForPrint.setBILLDATE(c.getString(26));
                    GetBillDetailsModelForPrint.setLPAMT(c.getString(27));
                    GetBillDetailsModelForPrint.setDUEDATE(c.getString(28));
                    GetBillDetailsModelForPrint.setDISCONDATE(c.getString(29));
                    GetBillDetailsModelForPrint.setLOAD(c.getString(30));

                    GetBillDetailsModelForPrint.setCASTE(c.getString(31));
                    GetBillDetailsModelForPrint.setNEWARREARS(c.getString(32));




                    getBillDetailsModel.add(GetBillDetailsModelForPrint);
                }




// do what ever you want here
            } while (c.moveToNext());
        }
        c.close();
        sqLiteDatabase.close();






    }



    private class BackgroundTaskRetriveSingleBill extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
                           // billdetailModel.setClose_bal(c.getString(50));
                            billdetailModel.setCSM_AADHAAR_NO(c.getString(51));
                            billdetailModel.setCSM_CASTE(c.getString(52));
                            billdetailModel.setCSM_PHONE_NO(c.getString(53));
                            billdetailModel.setCSM_SHARE(c.getString(54));
                            billdetailModel.setCSM_METER_NO(c.getString(55));
                            billdetailModel.setMD(c.getString(56));
                            billdetailModel.setERO(c.getString(57));




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
            if(s.equals("ok")){
//                Toast.makeText(PendingBillActivity.this,""+s,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(PendingBillActivity.this, BillInfoActivity.class);
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
                billdetailModel.setNkt(refModel.getNKT());

//                billdetailModel.setSTATUS(refModel.getOPSTATUS());
                billdetailModel.setBILLDATE(refModel.getBILLDATE());
                billdetailModel.setLPAMT(refModel.getLPAMT());
                billdetailModel.setDUEDATE(refModel.getDUEDATE());
                billdetailModel.setDISCDATE(refModel.getDISCONDATE());
                billdetailModel.setCSM_CONNECTED_LOAD(refModel.getLOAD());


                if(refModel.getMETERBRUNTVALUE().length()>0){
                    //for meter charges
                    i.putExtra("charge",Integer.valueOf(refModel.getMETERBRUNTVALUE()));
                    billdetailModel.setBurntValue(refModel.getMETERBRUNTVALUE());
                }

                i.putExtra("model", new Gson().toJson(billdetailModel));
              //  i.putExtra("lastcon",Integer.valueOf(billdetailModel.getLASTMCONSUPTION()));

//            i.putExtra("lastcon",Integer.parseInt(refModel.getSTATUS()));
                Log.e("model",""+new Gson().toJson(billdetailModel));
                Log.e("model l",""+billdetailModel.getLASTMCONSUPTION());
//            i.putExtra("lastcon",Integer.parseInt(billdetailModel.getLASTMCONSUPTION()));

                startActivity(i);
            }else {
                Toast.makeText(PendingBillActivity.this,"Area Code should be same as you want to print the bill",Toast.LENGTH_SHORT).show();
            }


//            GetBillDetailsModelForPrint = new LocalBillModel();
//            GetBillDetailsModelForPrint.setSERVICENO(c.getString(0));
//            GetBillDetailsModelForPrint.setAREACODE(c.getString(1));
//            GetBillDetailsModelForPrint.setOLDRDG_KWH(c.getString(2));
//            GetBillDetailsModelForPrint.setPRESRDG_KWH(c.getString(3));
//            GetBillDetailsModelForPrint.setUNITS(c.getString(4));
//            GetBillDetailsModelForPrint.setENGCHG(c.getString(5));
//            GetBillDetailsModelForPrint.setCUSCHG(c.getString(6));
//            GetBillDetailsModelForPrint.setEDCHG(c.getString(7));
//            GetBillDetailsModelForPrint.setBILLAMT(c.getString(8));
//            GetBillDetailsModelForPrint.setADJAMT(c.getString(9));
//            GetBillDetailsModelForPrint.setTOTALAMT(c.getString(10));
//            GetBillDetailsModelForPrint.setCATEGORY(c.getString(11));
//            GetBillDetailsModelForPrint.setCONSUMER_NAME(c.getString(12));
//            GetBillDetailsModelForPrint.setBILL_DATE(c.getString(13));
//            GetBillDetailsModelForPrint.setSTATUS(c.getString(14));
//            GetBillDetailsModelForPrint.setLORG(c.getString(15));
        }
    }

    private class getDataFromDbAsync extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(PendingBillActivity.this,"Loading...",Toast.LENGTH_SHORT).show();
            ll_loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            getDataFromDb();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(getBillDetailsModel.size()==0){
                button.setVisibility(View.GONE);
                if(successCount > 0)
                    Globals.showToast(getApplicationContext(),""+successCount+" Bills Updated");
                else
                    Globals.showToast(getApplicationContext(),"No Bills to be Updated");
                successCount = 0;

            }
//        else if((getBillDetailsModel.size() > 0) && successCount > 0){
//            Globals.showToast(getApplicationContext(),""+successCount+" Bills Updated");
//            successCount = 0;
//        }
            else {
                button.setVisibility(View.VISIBLE);
            }

            pendingBillAdapter = new PendingBillAdapter(PendingBillActivity.this, getBillDetailsModel);
            listView.setAdapter(pendingBillAdapter);
            pendingBillAdapter.notifyDataSetChanged();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                    Intent i = new Intent(PendingBillActivity.this, BillInfoActivity.class);
                    GetBillDetailsModel billDetailsModel11 = new GetBillDetailsModel();
                    refModel = (LocalBillModel)listView.getItemAtPosition(position);
                    Toast.makeText(PendingBillActivity.this,""+refModel.getSERVICENO(),Toast.LENGTH_SHORT).show();
                    Log.e("list"," d: "+refModel.toString());
                    new BackgroundTaskRetriveSingleBill().execute(refModel.getSERVICENO());
//                billDetailsModel1 = listView.

                }
            });

            ll_loading.setVisibility(View.GONE);

        }
    }
}
