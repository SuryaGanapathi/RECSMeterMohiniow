package com.recsmeterreading.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.recsmeterreading.R;
import com.recsmeterreading.Utils.Globals;
import com.recsmeterreading.Utils.Utils;
import com.recsmeterreading.bluetoothPrinter.BluetoothPrinterMain;
import com.recsmeterreading.model.GetBillDetailsModel;
import com.recsmeterreading.model.GprsSpotBillModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/*
 * Created by Manikanta Sarma
 */
@SuppressLint("SimpleDateFormat")
public class BillInfoActivity extends AppCompatActivity {

    GetBillDetailsModel billDetailsModel = new GetBillDetailsModel();

    TextView serviceNoTv,meterNo,nameTv,addressTv,billDateTv,catgoryTv,subCategoryTv, previousStartReadTv,
            previousEndReadTv, loadTv, phaseTv,
            endReadDateTv,custmChargesTv,lateFeeChargesTv,fixedChargesTv,
            tv_dial_value,tv_last_month_consumption,edChargesTv,unitsTv,nktTv,amountTv,
            lastDatePymtTv,poweDisconTv,engChgTv,mCharge,arrears,aadhar,shareC,billNo,statusCode,grandTotal, startReadingDate,
            adjEnergyCharges,peakAll, peakall2, peakall3, lastPaidDate, lastPaidAmount;
    Calendar calendar;
    Button printBtn;
    String currentReading, status, endReading;
    final private static int DAIL_VALUE = 9999;
    int lastMonthConsumption = 0;
    LinearLayout dialLayout, meterChargesLayout,lastMonthConLayout, adjLayout,peakLayout, peakLayout2, peakLayout3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_info);

        Toolbar myToolbar = findViewById(R.id.bill_info_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionbar = getSupportActionBar();
        if (getSupportActionBar() != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setTitle(" విద్యుత్ వినియోగపు నెలవారి బిల్లు ");

        }

        dialLayout = findViewById(R.id.dial_layout);
        meterChargesLayout = findViewById(R.id.meterLayout);
        lastMonthConLayout = findViewById(R.id.ll_last_month_consumption);

        adjLayout = findViewById(R.id.adj_layout);
        peakLayout = findViewById(R.id.peakDisplayLayout);
        peakLayout2 = findViewById(R.id.peakDisplayLayout2);
        peakLayout3 = findViewById(R.id.peakDisplayLayout3);
//        meterChargesLayout.setVisibility(View.GONE);

        serviceNoTv         = findViewById(R.id.serviceNoTv);
        nameTv              = findViewById(R.id.nameTv);
        addressTv           = findViewById(R.id.addressTv);
        billDateTv          = findViewById(R.id.billDateTv);
        catgoryTv           = findViewById(R.id.catgoryTv);
        subCategoryTv       = findViewById(R.id.subCategoryTv);
        startReadingDate    = findViewById(R.id.start_reading_date);
        previousStartReadTv = findViewById(R.id.previousStartReadTv);
        //meter charges praful 13/11
        mCharge = findViewById(R.id.meterCharge);
        arrears = findViewById(R.id.arrears);
        meterNo = findViewById(R.id.meterNo);
        aadhar = findViewById(R.id.aadhar_number);
        shareC = findViewById(R.id.share);
        billNo = findViewById(R.id.bill_number);
        statusCode = findViewById(R.id.status_spinner);
//        startReadDateTv     = findViewById(R.id.startReadDateTv);
        previousEndReadTv   = findViewById(R.id.previousEndReadTv);
        endReadDateTv       = findViewById(R.id.endReadDateTv);
        custmChargesTv      = findViewById(R.id.custmChargesTv);
        lateFeeChargesTv    = findViewById(R.id.lateFeeChargesTv);
        edChargesTv         = findViewById(R.id.powerChargesTv);
        unitsTv             = findViewById(R.id.unitsTv);
        nktTv               = findViewById(R.id.nktTv);
        amountTv            = findViewById(R.id.amountTv);
        grandTotal          = findViewById(R.id.grand_total);
        lastPaidDate        = findViewById(R.id.last_paid_date);
        lastPaidAmount      = findViewById(R.id.last_paid_amount);
        adjEnergyCharges    = findViewById(R.id.adj_energy_charge);
        peakAll             = findViewById(R.id.peakLoadAll);
        peakall2            = findViewById(R.id.peakLoadAll2);
        peakall3            = findViewById(R.id.peakLoadAll3);
        lastDatePymtTv      = findViewById(R.id.lastDatePymtTv);
        poweDisconTv        = findViewById(R.id.poweDisconTv);
        engChgTv            = findViewById(R.id.engChgTv);
        printBtn            = findViewById(R.id.printBtn);
        loadTv              = findViewById(R.id.loadTv);
        phaseTv             = findViewById(R.id.phaseTv);
        fixedChargesTv      = findViewById(R.id.fixedChargesTv);
        tv_dial_value = findViewById(R.id.tv_dial_value);
        tv_last_month_consumption = findViewById(R.id.tv_last_month_consumption);
        Intent ii = getIntent();
        String intent = ii.getExtras().getString("model");

        lastMonthConsumption = ii.getIntExtra("lastcon",-1);
        int dialVisible = ii.getIntExtra("dial",-1);
        int metCharge = ii.getIntExtra("charge",-1);
       /* if(dialVisible == -1){
            dialLayout.setVisibility(View.GONE);
        }else
            dialLayout.setVisibility(View.VISIBLE);
*/
        if(metCharge == -1){
            meterChargesLayout.setVisibility(View.GONE);
        }else
            meterChargesLayout.setVisibility(View.VISIBLE);

        if(lastMonthConsumption == -1){
            lastMonthConLayout.setVisibility(View.GONE);
        }else
            lastMonthConLayout.setVisibility(View.VISIBLE);



//        currentReading = getIntent().getExtras().getString("currentReading");
//        status = getIntent().getExtras().getString("status");
//        endReading = getIntent().getExtras().getString("endReadingUnits");
//        Globals.showToast(getApplicationContext(), "This is the value of status"+status);
//        Globals.showToast(getApplicationContext(), "This is the value of endReading"+endReading);
        Log.e("last con: ",""+lastMonthConsumption);

        try {
            Gson gson = new Gson();
            billDetailsModel = gson.fromJson(intent, GetBillDetailsModel.class);
            if(billDetailsModel.getCSM_CASTE().equals("SC") || billDetailsModel.getCSM_CASTE().equals("ST")){
                adjLayout.setVisibility(View.VISIBLE);
            }else
                adjLayout.setVisibility(View.GONE);

            if((billDetailsModel.getCategory().equals("3") && billDetailsModel.getSubcategory().equals("6"))
                    || ((billDetailsModel.getCategory().equals("2")) && billDetailsModel.getSubcategory().equals("4"))){
                peakLayout.setVisibility(View.VISIBLE);
                peakLayout2.setVisibility(View.VISIBLE);
                peakLayout3.setVisibility(View.VISIBLE);
            }else{
                peakLayout.setVisibility(View.GONE);
                peakLayout2.setVisibility(View.GONE);
                peakLayout3.setVisibility(View.GONE);
            }



            updateTextviews();
        } catch (Exception e) {
            Log.e("Exeption ", e.toString());
        }

        printBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(getApplicationContext(), BluetoothPrinterMain.class);
                i.putExtra("data", billDetailsModel);
                startActivityForResult(i,1);
            }
        });
    }
    private String getBillNoDateTime() {

        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }
    @SuppressLint("SetTextI18n")
    public void updateTextviews(){
//        billNo.setText((billDetailsModel.getBILLDATE().replace("-",""))+
//                (billDetailsModel.getSCNO().replace(" ","")));
        billNo.setText(billDetailsModel.getSTATUS().substring(2));
        serviceNoTv.setText(billDetailsModel.getSCNO());
        meterNo.setText(billDetailsModel.getCSM_METER_NO());
        nameTv.setText(billDetailsModel.getCSM_CONSUMER_NAME());
        addressTv.setText(billDetailsModel.getCSM_ADDRESS3()+".");
        if(billDetailsModel.getCSM_AADHAAR_NO().length()>=12){
            aadhar.setText("XXXXXXXXX"+billDetailsModel.getCSM_AADHAAR_NO().substring(9));
        }

        statusCode.setText(billDetailsModel.getSTATUS().substring(0,2));
        billDetailsModel.setBILLDATE(billDetailsModel.getBILLDATE());
        billDateTv.setText(billDetailsModel.getBILLDATE());
        catgoryTv.setText(billDetailsModel.getCategory());
        subCategoryTv.setText("0"+billDetailsModel.getSubcategory());
//        if((billDetailsModel.getAdj_amount() != null) && (!billDetailsModel.getAdj_amount().equals(""))){

//        }

        int category = Integer.parseInt(billDetailsModel.getCategory() + "0" + billDetailsModel.getSubcategory());
        //kw
        if(billDetailsModel.getCategory().equals("1")
                || billDetailsModel.getCategory().equals("2")
                || category == 601
                || category == 602
                || billDetailsModel.getCategory().equals("7")
                || billDetailsModel.getCategory().equals("8")){

            loadTv.setText(billDetailsModel.getCSM_CONNECTED_LOAD()+" kw");
        }else {
            loadTv.setText(billDetailsModel.getCSM_CONNECTED_LOAD()+" hp");
        }

        phaseTv.setText(billDetailsModel.getPHASE());
        previousStartReadTv.setText(billDetailsModel.getOPRDNG());
        Log.e("meter chareg",""+billDetailsModel.getBurntValue());
        //set meter charges praful 13/11

            mCharge.setText(billDetailsModel.getBurntValue());
//        startReadDateTv.setText(billDetailsModel.get);
        startReadingDate.setText(billDetailsModel.getOpmonth());
         previousEndReadTv.setText(billDetailsModel.getCLRDNG());


        endReadDateTv.setText(billDetailsModel.getBILLDATE());
        custmChargesTv.setText("\u20B9"+billDetailsModel.getCUSCHG());
        lateFeeChargesTv.setText("\u20B9"+billDetailsModel.getSURCHRGS());
        edChargesTv.setText("\u20B9"+billDetailsModel.getEDCHG());
        unitsTv.setText(billDetailsModel.getUNITS());
        String[] abc = billDetailsModel.getNkt().split(" ");

        nktTv.setText("\u20B9"+abc[0]);
        double totalAmount = 0;
        if(billDetailsModel.getCSM_CASTE().equals("SC") || billDetailsModel.getCSM_CASTE().equals("ST")){

             totalAmount= Double.parseDouble(billDetailsModel.getBILLAMT())- Double.parseDouble(billDetailsModel.getClose_bal())+ Double.parseDouble(billDetailsModel.getAdj_amount());


        }else {
            totalAmount = Double.parseDouble(billDetailsModel.getBILLAMT())- Double.parseDouble(billDetailsModel.getClose_bal());
        }



//        int totalAmount = Integer.parseInt(billDetailsModel.getBILLAMT())- Integer.parseInt(billDetailsModel.getCLDEMAN());
        // changed 5th march
//        amountTv.setText("\u20B9"+String.valueOf(totalAmount));
        amountTv.setText("\u20B9"+ totalAmount);
        grandTotal.setText("\u20B9"+billDetailsModel.getBILLAMT());
//        amountTv.setText("\u20B9"+String.valueOf(totalAmount));
//        grandTotal.setText("\u20B9"+billDetailsModel.getBILLAMT());
//        amountTv.setText("\u20B9"+billDetailsModel.getBILLAMT());
//        grandTotal.setText("\u20B9"+String.valueOf(totalAmount));
        lastPaidDate.setText(billDetailsModel.getLPDATE());
       lastPaidAmount.setText("\u20B9"+billDetailsModel.getLPAMT());
        fixedChargesTv.setText("\u20B9"+billDetailsModel.getFIXEDCHG());
        engChgTv.setText("\u20B9"+billDetailsModel.getENGCHG());

        adjEnergyCharges.setText("\u20B9"+billDetailsModel.getAdj_amount());
        if(abc.length>2){

            peakAll.setText(abc[1]);
            peakall2.setText(abc[2]);
            peakall3.setText(abc[3]);
        }

//        if(billDetailsModel.getERO().equals("009") || billDetailsModel.getERO().equals("9")){
//            billDetailsModel.setDUEDATE(Converter.getEndDateForERONine());
//            billDetailsModel.setDISCDATE(Converter.addDays(Converter.getEndDateForERONineDis(),7));
//        }else {
//            billDetailsModel.setDUEDATE(Converter.getEndDate());
//            billDetailsModel.setDISCDATE(Converter.addDays(Converter.getEndDateOfTheMonth(),15));
//        }

//        billDetailsModel.setDUEDATE(getEndDate());
        lastDatePymtTv.setText(billDetailsModel.getDUEDATE());
//        lastDatePymtTv.setText(billDetailsModel.getDUEDATE());
//        billDetailsModel.setDISCDATE(addDays(calendar.getTime(),15));
        poweDisconTv.setText(billDetailsModel.getDISCDATE());
        //praful


        arrears.setText(billDetailsModel.getClose_bal());
//        arrears.setText(billDetailsModel.getCLDEMAN());
        shareC.setText(billDetailsModel.getCSM_SHARE());
        //praful
        if(lastMonthConsumption != -1){
            billDetailsModel.setLASTMCONSUPTION(String.valueOf(lastMonthConsumption));
            tv_last_month_consumption.setText(billDetailsModel.getLASTMCONSUPTION());
        }else {
            billDetailsModel.setLASTMCONSUPTION(billDetailsModel.getCLRDNG() + "");
            tv_last_month_consumption.setText(billDetailsModel.getLASTMCONSUPTION());
        }

        tv_dial_value.setText(DAIL_VALUE+"");

        Utils.getSharedInstance().getBillDetailsModel.add(0,billDetailsModel);
        Log.e("Bill info json data",""+updateBill());
        Utils.getSharedInstance().whenInternetisEnabled(this,Utils.getSharedInstance().
                isConnectedToInternet(this));
    }



    private String updateBill() {
        GprsSpotBillModel gprsSpotBillModel = new GprsSpotBillModel();
//        gprsSpotBillModel.setBILLNO(billDetailsModel.getBILLNO());
//        gprsSpotBillModel.setMACHINEID(billDetailsModel.getMACHINEID());
        gprsSpotBillModel.setSERVICENO(billDetailsModel.getSCNO());
        gprsSpotBillModel.setAREACODE(billDetailsModel.getAREACODE());
//        gprsSpotBillModel.setOLDRDG_KWH(billDetailsModel.getOLDRDG_KWH());
        gprsSpotBillModel.setOLDRDG_KVAH(billDetailsModel.getOPRDNG());
//        gprsSpotBillModel.setOLDDT(billDetailsModel.getOLDDT());
//        gprsSpotBillModel.setPRESRDG_KVAH(billDetailsModel.getPRESRDG_KVAH());
        gprsSpotBillModel.setPRESRDG_KWH(billDetailsModel.getCLRDNG());
//        gprsSpotBillModel.setPRESRDG_LT(billDetailsModel.getPRESRDG_LT());
//        gprsSpotBillModel.setPRESRDG_KVA(billDetailsModel.getPRESRDG_KVA());
//        gprsSpotBillModel.setPRESDT(billDetailsModel.getPRESDT());
//        gprsSpotBillModel.setPRESSTS(billDetailsModel.getPRESSTS());
//        gprsSpotBillModel.setPRESSTS_LT(billDetailsModel.getPRESSTS_LT());
//        gprsSpotBillModel.setINITRDG_KWH(billDetailsModel.getINITRDG_KWH());
//        gprsSpotBillModel.setFINALRDG_KWH(billDetailsModel.getFINALRDG_KWH());
//        gprsSpotBillModel.setINITRDG_KVAH(billDetailsModel.getINITRDG_KVAH());
//        gprsSpotBillModel.setFINALRDG_KVAH(billDetailsModel.getFINALRDG_KVAH());
        gprsSpotBillModel.setUNITS(billDetailsModel.getUNITS());
//        gprsSpotBillModel.setUNITS_LT(billDetailsModel.getUn());
        gprsSpotBillModel.setENGCHG(billDetailsModel.getENGCHG());
        gprsSpotBillModel.setFIXEDCHG(billDetailsModel.getFIXEDCHG());
        gprsSpotBillModel.setCUSCHG(billDetailsModel.getCUSCHG());
        gprsSpotBillModel.setEDCHG(billDetailsModel.getEDCHG());
//        gprsSpotBillModel.setLPF_CAP_CHG(billDetailsModel.getLPF_CAP_CHG());
        gprsSpotBillModel.setBILLAMT(billDetailsModel.getBILLAMT());
        gprsSpotBillModel.setADJAMT(billDetailsModel.getAdj_amount());
        gprsSpotBillModel.setTOTALAMT(billDetailsModel.getBILLAMT());
//        gprsSpotBillModel.setAVGUNITS(billDetailsModel.getAVGUNITS());
//        gprsSpotBillModel.setOLDARREARS(billDetailsModel.getOLDARREARS());
//        gprsSpotBillModel.setNEWARREARS(billDetailsModel.getNEWARREARS());
//        gprsSpotBillModel.setASURCHG(billDetailsModel.getASURCHG());
//        gprsSpotBillModel.setAEDINT(billDetailsModel.getAEDINT());
//        gprsSpotBillModel.setPAMOUNT(billDetailsModel.getPAMOUNT());
//        gprsSpotBillModel.setPEDCHG(billDetailsModel.getPEDCHG());
//        gprsSpotBillModel.setACD(billDetailsModel.getACD());
//        gprsSpotBillModel.setFSA(billDetailsModel.getFSA());
//        gprsSpotBillModel.setOTHERS1(billDetailsModel.getOthers1());
//        gprsSpotBillModel.setOTHERS2(billDetailsModel.getOthers2());
//        gprsSpotBillModel.setDUEDATE(billDetailsModel.getDuedate());
//        gprsSpotBillModel.setDISCDATE(billDetailsModel.getDisdate());
//        gprsSpotBillModel.setSUBSIDIAMT(billDetailsModel.getSubsidy());
//        gprsSpotBillModel.setPF(billDetailsModel.getPf());
//        gprsSpotBillModel.setLORG(billDetailsModel.getLorg());
//        gprsSpotBillModel.setMD(billDetailsModel.getMd());
//        gprsSpotBillModel.setKVAH(billDetailsModel.getKvah());
//        gprsSpotBillModel.setEROCODE(billDetailsModel.getEROCODE());
        gprsSpotBillModel.setCATEGORY(billDetailsModel.getCategory()+"0"+billDetailsModel.getSubcategory());
//        gprsSpotBillModel.setBILLMONTH(billDetailsModel.getBillmonth());

        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("BILLNO", gprsSpotBillModel.getBILLNO());
            requestObject.put("MACHINEID", gprsSpotBillModel.getMACHINEID());
            requestObject.put("SERVICENO", gprsSpotBillModel.getSERVICENO());
            requestObject.put("AREACODE", gprsSpotBillModel.getAREACODE());
            requestObject.put("OLDRDG_KWH", gprsSpotBillModel.getOLDRDG_KWH());
            requestObject.put("OLDRDG_KVAH", gprsSpotBillModel.getOLDRDG_KVAH());
            requestObject.put("OLDDT", gprsSpotBillModel.getOLDDT());
            requestObject.put("PRESRDG_KVAH", gprsSpotBillModel.getPRESRDG_KVAH());
            requestObject.put("PRESRDG_KWH", gprsSpotBillModel.getPRESRDG_KWH());
            requestObject.put("PRESRDG_LT", gprsSpotBillModel.getPRESRDG_LT());
            requestObject.put("PRESRDG_KVA", gprsSpotBillModel.getPRESRDG_KVA());
            requestObject.put("PRESDT", gprsSpotBillModel.getPRESDT());
            requestObject.put("PRESSTS", gprsSpotBillModel.getPRESSTS());
            requestObject.put("PRESSTS_LT", gprsSpotBillModel.getPRESSTS_LT());
            requestObject.put("INITRDG_KWH", gprsSpotBillModel.getINITRDG_KWH());
            requestObject.put("FINALRDG_KWH", gprsSpotBillModel.getFINALRDG_KWH());
            requestObject.put("INITRDG_KVAH", gprsSpotBillModel.getINITRDG_KVAH());
            requestObject.put("FINALRDG_KVAH", gprsSpotBillModel.getFINALRDG_KVAH());
            requestObject.put("UNITS", gprsSpotBillModel.getUNITS());
            requestObject.put("UNITS_LT", gprsSpotBillModel.getUNITS_LT());
            requestObject.put("ENGCHG", gprsSpotBillModel.getENGCHG());
            requestObject.put("FIXEDCHG", gprsSpotBillModel.getFIXEDCHG());
            requestObject.put("CUSCHG", gprsSpotBillModel.getCUSCHG());
            requestObject.put("EDCHG", gprsSpotBillModel.getEDCHG());
            requestObject.put("LPF_CAP_CHG", gprsSpotBillModel.getLPF_CAP_CHG());
            requestObject.put("BILLAMT", gprsSpotBillModel.getBILLAMT());
            requestObject.put("ADJAMT", gprsSpotBillModel.getADJAMT());
            requestObject.put("TOTALAMT", gprsSpotBillModel.getTOTALAMT());
            requestObject.put("AVGUNITS", gprsSpotBillModel.getAVGUNITS());
            requestObject.put("OLDARREARS", gprsSpotBillModel.getOLDARREARS());
            requestObject.put("NEWARREARS", gprsSpotBillModel.getNEWARREARS());
            requestObject.put("ASURCHG", gprsSpotBillModel.getASURCHG());
            requestObject.put("AEDINT", gprsSpotBillModel.getAEDINT());
            requestObject.put("PAMOUNT", gprsSpotBillModel.getPAMOUNT());
            requestObject.put("PEDCHG", gprsSpotBillModel.getPEDCHG());
            requestObject.put("ACD", gprsSpotBillModel.getACD());
            requestObject.put("FSA", gprsSpotBillModel.getFSA());
            requestObject.put("OTHERS1", gprsSpotBillModel.getOTHERS1());
            requestObject.put("OTHERS2", gprsSpotBillModel.getOTHERS2());
            requestObject.put("DUEDATE", gprsSpotBillModel.getDUEDATE());
            requestObject.put("DISCDATE", gprsSpotBillModel.getDISCDATE());
            requestObject.put("SUBSIDIAMT", gprsSpotBillModel.getSUBSIDIAMT());
            requestObject.put("PF", gprsSpotBillModel.getPF());
            requestObject.put("LORG", gprsSpotBillModel.getLORG());
            requestObject.put("MD", gprsSpotBillModel.getMD());
            requestObject.put("KVAH", gprsSpotBillModel.getKVAH());
            requestObject.put("EROCODE", gprsSpotBillModel.getEROCODE());
            requestObject.put("CATEGORY", gprsSpotBillModel.getCATEGORY());
            requestObject.put("BILLMONTH", gprsSpotBillModel.getBILLMONTH());
            System.out.println(requestObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return requestObject.toString();
    }

    private String getDateTime() {

        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = new Date();

        return dateFormat.format(date);
    }

    private String getEndDate() {
        calendar = Calendar.getInstance();
        int lastDate = calendar.getActualMaximum(Calendar.DATE);

        calendar.set(Calendar.DATE, lastDate);
        int lastDay = calendar.get(Calendar.DAY_OF_WEEK);

        System.out.println("Last Date: " + calendar.getTime());

        System.out.println("Last Day : " + lastDay);
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

        return dateFormat.format(calendar.getTime());
    }


    public static String addDays(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);

        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        return dateFormat.format(cal.getTime());
    }

//
    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Globals.showToast(this,"Press Top Back Button");
//        finish();
//        Intent setIntent = new Intent(getApplicationContext(),GetBillDetailsActivity.class);
//        startActivity(setIntent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                onSupportNavigateUp();
//                finish();
                break;

            default:


        }
    }
}
