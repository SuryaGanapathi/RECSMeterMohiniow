package com.recsmeterreading.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.recsmeterreading.R;
import com.recsmeterreading.Utils.Converter;
import com.recsmeterreading.Utils.Globals;
import com.recsmeterreading.Utils.PrintLog;
import com.recsmeterreading.Utils.SharedPreferenceUtil;
import com.recsmeterreading.Utils.StringConstants;
import com.recsmeterreading.Utils.Utils;
import com.recsmeterreading.activities.BillInfoActivity;
import com.recsmeterreading.bgtask.BackgroundTask;
import com.recsmeterreading.bgtask.BackgroundThread;
import com.recsmeterreading.connections.ServiceCalls;
import com.recsmeterreading.model.GetBillDetailsModel;
import com.recsmeterreading.model.LocalTarrifModel;
import com.recsmeterreading.model.ServiceResponseInputModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;
import static com.recsmeterreading.Utils.SharedPreferenceUtil.getBillNo;

/**
 * Created by Surya Teja Challa.
 **/

public class BillDetailsFragment extends Fragment implements View.OnClickListener {


//    final String DATABASE_NAME = "CustomerServices";
//    sqLiteDatabase =
//    sqLiteDatabase = openOrCreateDatabase(Utils.getSharedInstance().databasePath + "RecsBillingData", null);

    private static final String LOG_TAG = "Bill Details Fragment";
    LocalTarrifModel mLocalTarrifModel;  // Tarrif rates for different categories and Consumptions

    ServiceResponseInputModel mServiceResponseInputJsondata;  // previous month reading data

    TextView serviceNoTv, nameTv, addressTv, catgoryTv, subCategoryTv, meterNoTv, phaseTv,
            tv_dial_value,
            lastMonthConsumptionTv,tv_arrers,shareCapital,caste;
    //TextView for getting meter reading of 10 different meters
    private EditText meterOneEt, meterTwoEt, meterThreeEt, meterFourEt, meterFiveEt, meterSixEt, meterSevenEt, meterEightEt, meterNineEt, meterTenEt;
    private EditText twoMetersFirstEt, twoMetersSecondEt;
    //praful
    private EditText meters,newMeter;
    private Button plusMeterButton;
    private TextView textMeters;
    private String showReading = "";
    private int meterReading = 0;
    private int count = 0;
    private Double peakLoad = 0.0;
    private static boolean isPeakLoadCount = false;
    private String peakLoadDetails ="";

    String resultString;

    View rootView;
    private RelativeLayout billDetailsLyt;

    LinearLayout ll_loading;
    Button searchBtn, proceedBtn;
    private EditText serviceNoEt, curReadTv,curReadTv1, statusEt, previousEndReadTV, currentStartReadTV,
            currentEndReadTV, oldMeterReadEt, lastMonthConsumptionEt, totalConsumptionEt,am610,pm610,ampm610;
    private TextView lastMeterAndConsumptionT;
    private GetBillDetailsModel billdetailModel, billdetailModelSCST;
    private Spinner burntMeterSpinner, statusSpinner;
    String burntMeterValue = "";
    LinearLayout ll_arrers,statusLyt, tenMetersLayout, twoMetersLayout, currentReadingLyt, dropDownLayout,
            ll_dail, lastMonthConsumptionLayout,multimMter,peakLoadLyt;

    private EditText aadhar, phone;
    RelativeLayout aadharLayout, phoneLayout;
    private static int DAIL_VALUE = 9999, CURRENT_READING = 0;
    String lastMonthConsumption = "value", status = "";



    ArrayList<String> mServiceNumbers = new ArrayList<>();
    String request;
    int page_id = 20;
    boolean isRecoresAviable = true;

    String JSONDATA = "";
    private SharedPreferenceUtil sharedPreferenceUtil;

    public BillDetailsFragment() {
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_get_bill_details, container, false);


        sharedPreferenceUtil = new SharedPreferenceUtil();

        // Initilise View

        request = SharedPreferenceUtil.getAreaCode(getActivity()) + "&start=" + page_id + "";

        initView();

        initActions();

        // Initilise dataBase

        Utils.getSharedInstance().createFoldersAndDb();

//       Get Data from Local Database

//       getDataFromLocalDataBase(serviceNoEt.getText().toString());

        ll_loading.setVisibility(View.GONE);

        cheackIfDataAvaiableInDataBase();

        //getInputReadingDataFromLocalJson();

        getLocalTarrifData();

        return rootView;
    }

    private void cheackIfDataAvaiableInDataBase() {

        final SQLiteDatabase sqLiteDatabase;
        sqLiteDatabase = openOrCreateDatabase(Utils.getSharedInstance().databasePath + "RecsBillingData", null);

        String sqlQuery = "SELECT * FROM CustomerServices ";
        Cursor c = sqLiteDatabase.rawQuery(sqlQuery, null);
        int count = c.getCount();
        Log.e("Request ; ",""+count);

        ll_loading.setVisibility(View.GONE);
        if (c.getCount() == 0) {

            if (Utils.isNetworkAvailable(getActivity())) {
                // appending start= to the url

                if (!request.equals("")) {
                    ll_loading.setVisibility(View.VISIBLE);
                    request = SharedPreferenceUtil.getAreaCode(getActivity()) + "&start=" + page_id + "";
                    Log.e("Request ; ",""+request);

                    getInputReadingDataFromServer(request);
                } else {
                    Globals.showToast(getActivity(), "Area Code Empty");
                }

            } else {
                Globals.showToast(getActivity(), "Please Connect to Internet to get Data from server");
            }
        } else {

        }

        c.close();
        sqLiteDatabase.close();

    }

    private void getDataFromLocalDataBase(String s) {

        final SQLiteDatabase sqLiteDatabase;
        sqLiteDatabase = openOrCreateDatabase(Utils.getSharedInstance().databasePath + "RecsBillingData", null);

        String sqlQuery = "SELECT * FROM CustomerServices " + "WHERE SERVICENO='" + s + "'";
        Cursor c = sqLiteDatabase.rawQuery(sqlQuery, null);

        int count = c.getCount();
        if (c.moveToFirst()) {
            do {
                Log.d("dorami", "onCreate: " + DatabaseUtils.dumpCursorToString(c));
                Log.d("dorami", "onCreate: " + c.getColumnIndex("count(SERVICENO)"));
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
               c.close();
               sqLiteDatabase.close();
        }

        if (billdetailModel != null && count>0 ) {
            //    Globals.showToast(getActivity(),"that value"+ billdetailModel.getPreviousMeterEndRead());
            updateTextViews();
            Log.e("before proceed search",""+new Gson().toJson(billdetailModel));
            if((billdetailModel.getCategory().equals("3") && billdetailModel.getSubcategory().equals("2"))
                    || ((billdetailModel.getCategory().equals("2")) && billdetailModel.getSubcategory().equals("4"))){
                isPeakLoadCount = true;
                peakLoadLyt.setVisibility(View.VISIBLE);
                currentReadingLyt.setVisibility(View.GONE);
            }else {
                peakLoadLyt.setVisibility(View.GONE);
                isPeakLoadCount = false;
            }

        } else {
            Globals.showToast(getActivity(), "Service Number not found");
        }

    }

    private void initActions() {
        serviceNoEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (serviceNoEt.getText().length() != 0 && billDetailsLyt.getVisibility() == View.VISIBLE) {
                    billDetailsLyt.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

//        statusEt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (statusEt.getText().length() != 0) {
//
//                    ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.burnt_meter_values,android.R.layout.simple_spinner_item);
//                    staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    burntMeterSpinner.setAdapter(staticAdapter);
//                    burntMeterSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parent, View view,
//                                                   int position, long id) {
//                            Log.v("item", (String) parent.getItemAtPosition(position));
//                            burntMeterValue = (String) parent.getItemAtPosition(position);
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parent) {
//                            //    TODO Auto-generated method stub
//                        }
//                    });
//
//                        if (statusEt.getText().toString().equals("01")) {
//                             Globals.showToast(getActivity(),"Normal");
//                             currentReadingLyt.setVisibility(View.VISIBLE);
//                        } else if (statusEt.getText().toString().equals("02")) {
//                            Globals.showToast(getActivity(), "Meter Struck(ave rage)");
//                            /*curReadTv.setText(Integer.parseInt(billdetailModel.getUNITS())+
//                                    Integer.parseInt(billdetailModel.getPREAVG()));*/
//                            curReadTv.setText("0");
//                        } else if (statusEt.getText().toString().equals("03")) {
//                            Globals.showToast(getActivity(), "Under disconnection");
//                            curReadTv.setEnabled(false);
//                            //curReadTv.setVisibility(View.GONE);
//                        } else if (statusEt.getText().toString().equals("04")) {
//                            Globals.showToast(getActivity(), "Meter change");
//                            lastMonthConsumption = billdetailModel.getCLRDNG();
//                            lastMonthConsumptionEt.setText(lastMonthConsumption);
//                            curReadTv.setEnabled(true);
//                        } else if (statusEt.getText().toString().equals("05")) {
//                            Globals.showToast(getActivity(), "Door lock");
//                            lastMonthConsumption = billdetailModel.getUNITS();
//                            lastMonthConsumptionTv.setText(lastMonthConsumption);
//                        } else if (statusEt.getText().toString().equals("06")) {
//                            Globals.showToast(getActivity(), " Meter not existing");
//                            curReadTv.setEnabled(false);
//                        } else if (statusEt.getText().toString().equals("07")) {
//                            Globals.showToast(getActivity(), "Dial complete");
//                            curReadTv.setEnabled(true);
//                        } else if (statusEt.getText().toString().equals("08")) {
//                            Globals.showToast(getActivity(), "Reading not finished");
//                            curReadTv.setEnabled(false);
//                        } else if (statusEt.getText().toString().equals("09")) {
//                            Globals.showToast(getActivity(), "Nill consumption");
//                            curReadTv.setEnabled(false);
//                        } else if (statusEt.getText().toString().equals("10")) {
//                            Globals.showToast(getActivity(), "Multiple meters");
//                            curReadTv.setEnabled(true);
//                        }else if (statusEt.getText().toString().equals("11")) {
//                            Globals.showToast(getActivity(), "Meter burnt");
//                            curReadTv.setEnabled(false);
//                        }else if (statusEt.getText().toString().equals("12")) {
//                            Globals.showToast(getActivity(), "Consumption less than Usage");
//                            curReadTv.setEnabled(true);
//                        }else if (statusEt.getText().toString().equals("13")) {
//                            Globals.showToast(getActivity(), "Bill stopped");
//                            curReadTv.setEnabled(true);
//                        }else if (statusEt.getText().toString().equals("14")) {
//                            Globals.showToast(getActivity(), "Dismantled service");
//                            curReadTv.setEnabled(true);
//                        }else if (statusEt.getText().toString().equals("15")) {
//                            Globals.showToast(getActivity(), "Abnormal low consumption");
//                            curReadTv.setEnabled(true);
//                        }else if (statusEt.getText().toString().equals("16")) {
//                            Globals.showToast(getActivity(), "Abnormal high consumption");
//                            curReadTv.setEnabled(true);
//                        }else if (statusEt.getText().toString().equals("17")) {
//                            Globals.showToast(getActivity(), "Progressive reading in udc");
//                            curReadTv.setEnabled(true);
//                        }else if (statusEt.getText().toString().equals("18")) {
//                            Globals.showToast(getActivity(), "Negative balance");
//                            curReadTv.setEnabled(false);
//                        }else if (statusEt.getText().toString().equals("19")) {
//                            Globals.showToast(getActivity(), "No master data case");
//                            curReadTv.setEnabled(true);
//                        }else if (statusEt.getText().toString().equals("20")) {
//                            Globals.showToast(getActivity(), "Regressive reading");
//                            curReadTv.setEnabled(true);
//                        }else if (statusEt.getText().toString().equals("21")) {
//                            Globals.showToast(getActivity(), "More than one reading");
//                            curReadTv.setEnabled(true);
//                        }else if (statusEt.getText().toString().equals("22")) {
//                            Globals.showToast(getActivity(), "Service more than once");
//                            curReadTv.setEnabled(true);
//                        }
//                        if (statusEt.getText().toString().equals("02")) {
//                        currentReadingLyt.setVisibility(View.GONE);
//                        } else {
//                        currentReadingLyt.setVisibility(View.VISIBLE);
//                        }
//
//                       if (statusEt.getText().toString().equals("04")) {
//                           statusLyt.setVisibility(View.VISIBLE);
//                           currentReadingLyt.setVisibility(View.GONE);
//                        }
//                      else {
//                            statusLyt.setVisibility(View.GONE);
//                            currentReadingLyt.setVisibility(View.VISIBLE);
//                        }
//                        if(statusEt.getText().toString().equals("10")){
//                            tenMetersLayout.setVisibility(View.VISIBLE);
//                            currentReadingLyt.setVisibility(View.GONE);
//                        }else {
//                            tenMetersLayout.setVisibility(View.GONE);
//                            currentReadingLyt.setVisibility(View.VISIBLE);
//
//                        }
//                    if(statusEt.getText().toString().equals("11")){
//                        dropDownLayout.setVisibility(View.VISIBLE);
//                        currentReadingLyt.setVisibility(View.GONE);
//                    }else {
//                        dropDownLayout.setVisibility(View.GONE);
//                        currentReadingLyt.setVisibility(View.VISIBLE);
//                       }
//                        if(statusEt.getText().toString().equals("21")){
//                            twoMetersLayout.setVisibility(View.VISIBLE);
//                            currentReadingLyt.setVisibility(View.GONE);
//                        }
//                        else{
//                            twoMetersLayout.setVisibility(View.GONE);
//                            currentReadingLyt.setVisibility(View.VISIBLE);
//                        }
//                        if(statusEt.getText().toString().equals("05")){
//                          lastMonthConsumptionLayout.setVisibility(View.VISIBLE);
//                          currentReadingLyt.setVisibility(View.GONE);
//                        }else{
//                            lastMonthConsumptionLayout.setVisibility(View.GONE);
//                            currentReadingLyt.setVisibility(View.VISIBLE);
//                        }
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

    }


    private void getLocalTarrifData() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    String myJson = Utils.getSharedInstance().readFile("json/tarrifData.json", getActivity());
                    parseLocalTarrifJsonData(myJson);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                return null;
            }
        }.execute();

    }

    private void getInputReadingDataFromLocalJson() {

        //        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                try {
//                    String myJson = Utils.getSharedInstance().readFile("json/serviceDetails.json", getActivity());
//                    parseInputServiceJsonData(myJson);
//                } catch (Exception exception) {
//                    exception.printStackTrace();
//                }
//
//                return null;
//            }
//        }.execute();
    }

    private void initView() {

        billDetailsLyt = rootView.findViewById(R.id.billDetailsLyt);
        ll_loading = rootView.findViewById(R.id.ll_loading);
        searchBtn = rootView.findViewById(R.id.search);
        proceedBtn = rootView.findViewById(R.id.proceedBtn);
        serviceNoEt = rootView.findViewById(R.id.serviceNoEt);
        serviceNoEt.setText(SharedPreferenceUtil.getAreaCode(getActivity()));
        serviceNoEt.setSelection(serviceNoEt.getText().length());

        aadhar = rootView.findViewById(R.id.aadhar_no);
        phone = rootView.findViewById(R.id.phone_no);
        shareCapital = rootView.findViewById(R.id.share_capital);
        caste = rootView.findViewById(R.id.caste);
        curReadTv = rootView.findViewById(R.id.curReadTv);
        curReadTv1 = rootView.findViewById(R.id.curReadTv1);

        am610 = rootView.findViewById(R.id.am610);
        pm610 = rootView.findViewById(R.id.pm610);
        ampm610 = rootView.findViewById(R.id.ampm610);
        // statusEt = rootView.findViewById(R.id.statusEt);

        serviceNoTv = rootView.findViewById(R.id.serviceNoTv);
        nameTv = rootView.findViewById(R.id.nameTv);
        addressTv = rootView.findViewById(R.id.addressTv);
        catgoryTv = rootView.findViewById(R.id.catgoryTv);
        subCategoryTv = rootView.findViewById(R.id.subCategoryTv);
        meterNoTv = rootView.findViewById(R.id.meterNoTv);
        phaseTv = rootView.findViewById(R.id.phaseTv);
        tv_dial_value = rootView.findViewById(R.id.tv_dial_value);

        lastMonthConsumptionTv = rootView.findViewById(R.id.lastMonthConsumtionTv);
        tv_arrers = rootView.findViewById(R.id.tv_arrers);

        statusLyt = rootView.findViewById(R.id.statusLyt);
        tenMetersLayout = rootView.findViewById(R.id.tenMetersLayout);
        peakLoadLyt = rootView.findViewById(R.id.peakLoadLyt);
        //praful
        multimMter = rootView.findViewById(R.id.multipleMeterLayout);

        twoMetersLayout = rootView.findViewById(R.id.twoMetersLayout);
        currentReadingLyt = rootView.findViewById(R.id.currentReadingLyt);
        ll_arrers = rootView.findViewById(R.id.ll_arrers);

        //Spinner
        burntMeterSpinner = rootView.findViewById(R.id.burntMeterSpinner);
        statusSpinner = rootView.findViewById(R.id.statusSpinner);
        //Layout for status 11
        dropDownLayout = rootView.findViewById(R.id.dropDownLayout);
        ll_dail = rootView.findViewById(R.id.ll_dail);
        lastMonthConsumptionLayout = rootView.findViewById(R.id.lastMonthConsumptionLayout);

        //for status code 10
        meterOneEt = rootView.findViewById(R.id.firstMeterReading);
        meterTwoEt = rootView.findViewById(R.id.secondMeterReading);
        meterThreeEt = rootView.findViewById(R.id.thirdMeterReading);
        meterFourEt = rootView.findViewById(R.id.fourthMeterReading);
        meterFiveEt = rootView.findViewById(R.id.fifthMeterReading);
        meterSixEt = rootView.findViewById(R.id.sixthMeterReading);
        meterSevenEt = rootView.findViewById(R.id.seventhMeterReading);
        meterEightEt = rootView.findViewById(R.id.eighthMeterReading);
        meterNineEt = rootView.findViewById(R.id.ninthMeterReading);
        meterTenEt = rootView.findViewById(R.id.tenthMeterReading);
        //praful
        meters = rootView.findViewById(R.id.multipleMeterReading);
        newMeter = rootView.findViewById(R.id.new_meter_no);
        plusMeterButton = rootView.findViewById(R.id.addMeterReading);
        textMeters = rootView.findViewById(R.id.textMultimeter);
        plusMeterButton.setOnClickListener(this);
        //
        // for meter code 21
        twoMetersFirstEt = rootView.findViewById(R.id.firstTwotMeterReading);
        twoMetersSecondEt = rootView.findViewById(R.id.secondTwotMeterReading);

        //        previousStartReadTV = rootView.findViewById(R.id.previousStartReadTV);
        // previousEndReadTV = rootView.findViewById(R.id.previousEndReadTV);
        currentStartReadTV = rootView.findViewById(R.id.currentStartReadTV);
        // currentEndReadTV = rootView.findViewById(R.id.currentEndReadTV);
        oldMeterReadEt = rootView.findViewById(R.id.oldMeterReadTV);
        lastMeterAndConsumptionT = rootView.findViewById(R.id.lastMeterAndConsumption);
        lastMonthConsumptionEt = rootView.findViewById(R.id.lastConsumptionEt);

        //hiding the previous bill details data
        billDetailsLyt.setVisibility(View.GONE);

        //click listeners
        searchBtn.setOnClickListener(this);
        proceedBtn.setOnClickListener(this);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.status_values, android.R.layout.simple_spinner_item);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(staticAdapter);
        statusSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                status = (String) parent.getItemAtPosition(position);

                //
                ll_dail.setVisibility(View.GONE);
                lastMonthConsumptionLayout.setVisibility(View.GONE);
                currentReadingLyt.setVisibility(View.VISIBLE);
                ll_arrers.setVisibility(View.VISIBLE);
                curReadTv.setText("");


                if (status.length() != 0) {

                    ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.burnt_meter_values, android.R.layout.simple_spinner_item);
                    staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    burntMeterSpinner.setAdapter(staticAdapter);
                    burntMeterSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {
                            Log.v("item", (String) parent.getItemAtPosition(position));
                            burntMeterValue = (String) parent.getItemAtPosition(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub
                        }
                    });

                    if (status.equals("01")) {
                        Globals.showToast(getActivity(), "Normal");
                        currentReadingLyt.setVisibility(View.VISIBLE);
                        curReadTv.setEnabled(true);
                    } else if (status.equals("02")) {
                        Globals.showToast(getActivity(), "Meter Struck(ave rage)");
                       // currentReadingLyt.setVisibility(View.VISIBLE);
                        curReadTv.setEnabled(false);
                        currentReadingLyt.setVisibility(View.GONE);
                            /*curReadTv.setText(Integer.parseInt(billdetailModel.getUNITS())+
                                    Integer.parseInt(billdetailModel.getPREAVG()));*/

                    } else if (status.equals("03")) {
                        Globals.showToast(getActivity(), "Under disconnection");
                        curReadTv.setEnabled(false);
                        //curReadTv.setVisibility(View.GONE);
                    } else if (status.equals("04")) {
                        Globals.showToast(getActivity(), "Meter change");
                        lastMonthConsumptionLayout.setVisibility(View.VISIBLE);
                        currentReadingLyt.setVisibility(View.GONE);
                        lastMonthConsumptionEt.setVisibility(View.GONE);
                        lastMonthConsumption = billdetailModel.getCLRDNG();
                        lastMeterAndConsumptionT.setText("Old Meter Start Reading");
                        lastMonthConsumptionEt.setText(billdetailModel.getCLRDNG());
                        curReadTv.setEnabled(true);
                    } else if (status.equals("05")) {
                        Globals.showToast(getActivity(), "Door lock");
//                        lastMonthConsumptionLayout.setVisibility(View.VISIBLE);
//                        currentReadingLyt.setVisibility(View.GONE);
                        currentReadingLyt.setVisibility(View.VISIBLE);
                        curReadTv.setEnabled(false);
                        lastMeterAndConsumptionT.setText("Last Consumption");
                        lastMonthConsumption = billdetailModel.getUNITS();
                        lastMonthConsumptionTv.setText(lastMonthConsumption);
                    } else if (status.equals("06")) {
                        Globals.showToast(getActivity(), " Meter not existing");
                        curReadTv.setEnabled(false);
                        currentReadingLyt.setVisibility(View.GONE);
                    } else if (status.equals("07")) {
                        Globals.showToast(getActivity(), "Dial complete");
                        ll_dail.setVisibility(View.VISIBLE);
                        currentReadingLyt.setVisibility(View.VISIBLE);
                        curReadTv.setEnabled(true);
                        lastMeterAndConsumptionT.setText("Old Meter End Reding");
                       lastMonthConsumptionTv.setText(billdetailModel.getCLRDNG());
                    } else if (status.equals("08")) {
                        Globals.showToast(getActivity(), "Reading not finished");
                        currentReadingLyt.setVisibility(View.GONE);
                        curReadTv.setEnabled(false);
                    } else if (status.equals("09")) {
                        Globals.showToast(getActivity(), "Nill consumption");
                        curReadTv.setEnabled(false);
                    } else if (status.equals("10")) {
                        Globals.showToast(getActivity(), "Multiple meters");
                        curReadTv.setEnabled(false);
                    } else if (status.equals("11")) {
                        Globals.showToast(getActivity(), "Meter burnt");
                        curReadTv.setEnabled(false);
                    } else if (status.equals("12")) {
                        Globals.showToast(getActivity(), "Consumption less than Usage");
                        curReadTv.setEnabled(true);
                    } else if (status.equals("13")) {
                        Globals.showToast(getActivity(), "Bill stopped");
                        curReadTv.setEnabled(true);
                    } else if (status.equals("14")) {
                        Globals.showToast(getActivity(), "Dismantled service");
                        curReadTv.setEnabled(true);
                    } else if (status.equals("15")) {
                        Globals.showToast(getActivity(), "Abnormal low consumption");
                        curReadTv.setEnabled(true);
                    } else if (status.equals("16")) {
                        Globals.showToast(getActivity(), "Abnormal high consumption");
                        curReadTv.setEnabled(true);
                    } else if (status.equals("17")) {
                        Globals.showToast(getActivity(), "Progressive reading in udc");
                        curReadTv.setEnabled(true);
                    } else if (status.equals("18")) {
                        Globals.showToast(getActivity(), "Negative balance");
//                        ll_arrers.setVisibility(View.VISIBLE);
                        tv_arrers.setText(billdetailModel.getClose_bal());
                        curReadTv.setEnabled(true);
                    } else if (status.equals("19")) {
                        Globals.showToast(getActivity(), "No master data case");
                        curReadTv.setEnabled(true);
                    } else if (status.equals("20")) {
                        Globals.showToast(getActivity(), "Regressive reading");
                        curReadTv.setEnabled(true);
                    } else if (status.equals("21")) {
                        Globals.showToast(getActivity(), "More than one reading");
                        curReadTv.setEnabled(false);
                    } else if (status.equals("22")) {
                        Globals.showToast(getActivity(), "Service more than once");
                        curReadTv.setEnabled(true);
                    }else
                        lastMeterAndConsumptionT.setText("Last Consumption");

                   /* if (status.equals("02")) {
                        currentReadingLyt.setVisibility(View.GONE);
                    } else {
                        currentReadingLyt.setVisibility(View.VISIBLE);
                    }*/

                    if (status.equals("04")) {
                        statusLyt.setVisibility(View.VISIBLE);

//                        Log.e(LOG_TAG, " 11 avg/cur reading:  " + curRead);
                        Log.e(LOG_TAG, " 11 units :  " +billdetailModel.getUNITS());
                        Log.e(LOG_TAG, " 11 :start reading  " +billdetailModel.getOPRDNG());
                        Log.e(LOG_TAG, " 11 end reading :  " +billdetailModel.getCLRDNG());
                        Log.e(LOG_TAG, " 11 :bill amount  " +billdetailModel.getBILLAMT());

                    } else {
                        statusLyt.setVisibility(View.GONE);
                        currentReadingLyt.setVisibility(View.VISIBLE);
                    }
                    if (status.equals("10")) {
//                        tenMetersLayout.setVisibility(View.VISIBLE);
                        multimMter.setVisibility(View.VISIBLE);
                        currentReadingLyt.setVisibility(View.GONE);
                    } else {

//                        tenMetersLayout.setVisibility(View.GONE);
                        multimMter.setVisibility(View.GONE);
                        currentReadingLyt.setVisibility(View.VISIBLE);

                    }
                    if (status.equals("11")) {
                        dropDownLayout.setVisibility(View.VISIBLE);
                        currentReadingLyt.setVisibility(View.GONE);
                    } else {
                        dropDownLayout.setVisibility(View.GONE);
                        currentReadingLyt.setVisibility(View.VISIBLE);
                    }
                    if (status.equals("21")) {
//                        twoMetersLayout.setVisibility(View.VISIBLE);
                        multimMter.setVisibility(View.VISIBLE);
                        currentReadingLyt.setVisibility(View.GONE);
                    } else {
                        twoMetersLayout.setVisibility(View.GONE);
                        currentReadingLyt.setVisibility(View.VISIBLE);
                    }

                    if (status.equals("05") || status.equals("10") || status.equals("21") || status.equals("04") || status.equals("02") || status.equals("06") || status.equals("08")) {

                        currentReadingLyt.setVisibility(View.GONE);
                    } else {

                        currentReadingLyt.setVisibility(View.VISIBLE);
                    }

                    if (status.equals("07")  || status.equals("05")) { //status 5 added by praful
                        lastMonthConsumptionLayout.setVisibility(View.VISIBLE);
                    } else {
                        lastMonthConsumptionLayout.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    private void getInputReadingDataFromServer(final String id) {

        new BackgroundTask(getActivity(), new BackgroundThread() {
            @Override
            public Object runTask() {
                return ServiceCalls.getReadingData(getActivity(), id);
            }

            @Override
            public void taskCompleted(Object data) {
                if (data != null && !data.equals("")) {


                    Log.e("Response data ", " " + data.toString());
                    //parseInputServiceJsonData(data.toString());


                    JSONDATA = data.toString();

                    new AddTask().execute();
                    //  new InsertIntoDB().execute();

                    if (isRecoresAviable) {
                        //  getInputReadingDataFromServer(request);
                    } else {
                        ll_loading.setVisibility(View.GONE);
                    }

                } else {
                    ll_loading.setVisibility(View.GONE);
                    Globals.showToast(getActivity(), "Invaild Data");
                }

            }
        }, getResources().getString(R.string.loading_txt)).execute();
        //}).execute();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private void parseData(final Object data) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Gson gson = new Gson();
                    mServiceResponseInputJsondata = gson.fromJson(data.toString(), ServiceResponseInputModel.class);
                    PrintLog.print("Response data json ", mServiceResponseInputJsondata.getDetails().get(0).getSCNO());
                } catch (Exception e) {
                    e.printStackTrace();
//                    Toast.makeText(getActivity(), "Exception at parse data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search:

//                if (performValidations()) {
//                    // getServiceNumberDetails();
//                }
                curReadTv.setText("");
                aadhar.setText("");
                phone.setText("");

                if (serviceNoEt.getText().toString().equals("")) {
                    Globals.showToast(getActivity(), "Please enter service number");
                } else if (serviceNoEt.getText().toString().length() < 10) {
                    Globals.showToast(getActivity(), "Invalid service number");
                } else {

                    String substr1 = serviceNoEt.getText().toString().substring(0, 5);
                    String substr2 = serviceNoEt.getText().toString().substring(5);


                    resultString = substr1 + " " + substr2;
                    checkWeatherBillUpdatedOrNot(resultString);

                }

                break;

            case R.id.proceedBtn:
                String status_code = status;

                Log.e("before proceed",""+new Gson().toJson(billdetailModel));
                if (performValidations(status_code)) {
                    if(performValidations()){
                        this.getActivity().finish();
                    }
                }



                // status code 01
                if (!status_code.equals("") ) {

                    billdetailModel.setBILLDATE(getBillNoDateTime());
                    if(billdetailModel.getERO().equals("009") || billdetailModel.getERO().equals("9")){
                        billdetailModel.setDUEDATE(Converter.getEndDateForERONine());
                        billdetailModel.setDISCDATE(Converter.addDays(Converter.getEndDateForERONineDis(),7));
                    }else {
                        billdetailModel.setDUEDATE(Converter.getEndDate());
                        billdetailModel.setDISCDATE(Converter.addDays(Converter.getEndDateOfTheMonth(),15));
                    }

                    int i1 = SharedPreferenceUtil.setBillNumber(getActivity(), (getBillNo(getActivity()) + 1));
                    billdetailModel.setSTATUS(status_code+(billdetailModel.getBILLDATE().replace("-",""))+
                            billdetailModel.getAREACODE().substring(2)+billdetailModel.getSCNO().substring(6)
                            +String.valueOf(getBillNo(getActivity())).substring(1));
                    Log.e("Bill No:",""+billdetailModel.getSTATUS());
                    if(isPeakLoadCount){

                        if(am610.getText().toString().trim().length()>0){
                            peakLoad = Double.parseDouble(am610.getText().toString().trim())*1.00;
                            billdetailModel.setCL2(String.valueOf(peakLoad));
                        }else {
                            billdetailModel.setCL2("0");
                        }
                        if(pm610.getText().toString().trim().length()>0){
                            peakLoad = peakLoad+Double.parseDouble(pm610.getText().toString().trim())*1.00;
                            Double p=Double.parseDouble(pm610.getText().toString().trim())*1.00;
                            billdetailModel.setCL2(billdetailModel.getCL2()+" "+ p);

                        }else
                            billdetailModel.setCL2(billdetailModel.getCL2()+" "+"0");
                        if(ampm610.getText().toString().trim().length()>0){
                            peakLoad = peakLoad+Double.parseDouble(ampm610.getText().toString().trim())*(-1.00);
                            Double p=Double.parseDouble(ampm610.getText().toString().trim())*(-1.00);
                            billdetailModel.setCL2(billdetailModel.getCL2()+" "+ p);
                        }else
                            billdetailModel.setCL2(billdetailModel.getCL2()+" "+"0");

                    }
                    if (status_code.equals("01")) {
                        if (performValidations(status_code)) {

                            String currentReading1 = billdetailModel.getUNITS();
//                            Toast.makeText(getActivity(),""+billdetailModel.getNEWARREARS()+": "
//                                    +billdetailModel.getCSM_AADHAAR_NO()+" : "
//                                    +billdetailModel.getCSM_PHONE_NO(),Toast.LENGTH_SHORT).show();
                            String currentReading = curReadTv.getText().toString();
                            billdetailModelSCST = new GetBillDetailsModel(billdetailModel);
//                            billdetailModelSCST = billdetailModel;
                            Log.e("end reading","sc"+billdetailModelSCST.getCLRDNG()+" n "+billdetailModel.getCLRDNG());
                            try {
                                billdetailModel = StringConstants.CALICULATE_BILL(billdetailModel, currentReading, mLocalTarrifModel, Integer.parseInt(status_code),peakLoad);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Log.e("end reading after","sc"+billdetailModelSCST.getCLRDNG()+" n "+billdetailModel.getCLRDNG());
                            try {
                                calculateIfSCST(currentReading,status_code);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Log.e("after calculation",""+new Gson().toJson(billdetailModel));

//                            billdetailModel.setLASTMCONSUPTION(currentReading1);
//                            billdetailModel.setSTATUS(currentReading1+" "+billdetailModel.getNkt()+" "+billdetailModel.getFIXEDCHG());
//                            billdetailModel.setSTATUS(billdetailModel.getSTATUS()+" "+b);
                            billdetailModel.setLASTMCONSUPTION(currentReading1);
                            insertBillDataIntoDB();


//                            new updateBillDataForNextPrint().execute(billdetailModel.getSCNO());
                            Intent i = new Intent(getActivity(), BillInfoActivity.class);

                            i.putExtra("model", new Gson().toJson(billdetailModel));
                            i.putExtra("currentReading", currentReading);
//                            i.putExtra("lastcon",Integer.parseInt(currentReading1));
                            Log.e("model1",""+new Gson().toJson(billdetailModel));
                            startActivity(i);


                        }
                    }
                    else if (status_code.equals("02")) {
                        //  average unit reading to be taken
                        if (performValidations(status_code)) {
                            String currentReading1 = billdetailModel.getUNITS();
                            // curReadTv.setText("Average Units will be considered");
                            String currentReading = billdetailModel.getPREAVG();
                            billdetailModelSCST = new GetBillDetailsModel(billdetailModel);
                            try {
                                billdetailModel = StringConstants.CALICULATE_BILL(billdetailModel, currentReading, mLocalTarrifModel, Integer.parseInt(status), peakLoad);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            try {
                                calculateIfSCST(currentReading,status_code);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
//                            billdetailModel.setSTATUS(currentReading1+" "+billdetailModel.getNkt()+" "+billdetailModel.getFIXEDCHG());
                            billdetailModel.setLASTMCONSUPTION(currentReading1);
                            insertBillDataIntoDB();
                            Intent i = new Intent(getActivity(), BillInfoActivity.class);
                            i.putExtra("model", new Gson().toJson(billdetailModel));
                       //     i.putExtra("lastcon",Integer.parseInt(currentReading1));
                            startActivity(i);


                    //    status code 02
                  /*  if (status_code.equals("02")) {
                        if (performValidations(status_code)) {
                            //changed 5th march 2019

                            String currentReading1 = billdetailModel.getUNITS();
//                            Toast.makeText(getActivity(),""+billdetailModel.getNEWARREARS()+": "
//                                    +billdetailModel.getCSM_AADHAAR_NO()+" : "
//                                    +billdetailModel.getCSM_PHONE_NO(),Toast.LENGTH_SHORT).show();
                            String currentReading = billdetailModel.getPREAVG();
                            billdetailModelSCST = new GetBillDetailsModel(billdetailModel);
//                            billdetailModelSCST = billdetailModel;
                            Log.e("end reading","sc"+billdetailModelSCST.getCLRDNG()+" n "+billdetailModel.getCLRDNG());
                            try {
                                billdetailModel = StringConstants.CALICULATE_BILL(billdetailModel, currentReading, mLocalTarrifModel, Integer.parseInt(status_code),peakLoad);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Log.e("end reading after","sc"+billdetailModelSCST.getCLRDNG()+" n "+billdetailModel.getCLRDNG());
                            try {
                                calculateIfSCST(currentReading,status_code);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Log.e("after calculation",""+new Gson().toJson(billdetailModel));

//                            billdetailModel.setLASTMCONSUPTION(currentReading1);
//                            billdetailModel.setSTATUS(currentReading1+" "+billdetailModel.getNkt()+" "+billdetailModel.getFIXEDCHG());
//                            billdetailModel.setSTATUS(billdetailModel.getSTATUS()+" "+b);
                            billdetailModel.setLASTMCONSUPTION(currentReading1);
                            insertBillDataIntoDB();


//                            new updateBillDataForNextPrint().execute(billdetailModel.getSCNO());
                            Intent i = new Intent(getActivity(), BillInfoActivity.class);

                            i.putExtra("model", new Gson().toJson(billdetailModel));
                          //  i.putExtra("currentReading", currentReading);
//                            i.putExtra("lastcon",Integer.parseInt(currentReading1));
                            Log.e("model1",""+new Gson().toJson(billdetailModel));
                            startActivity(i);


*/
                            //praful
//                            String currentReading = billdetailModel.getUNITS();
                            //  average unit reading to be taken
//                            String currentUnits = billdetailModel.getPREAVG();
                            //   int oldMeterReading = Integer.parseInt(billdetailModel.getOPRDNG());
                            //    int averageUnits = Integer.parseInt(currentUnits);
                            //    int reading = oldMeterReading + averageUnits;
                            //    String currentReading = String.valueOf(reading);
                            //  Globals.showToast(getActivity(),"This is the currenT"+currentReading);
//                            billdetailModelSCST = new GetBillDetailsModel(billdetailModel);
//                            billdetailModel = StringConstants.CALICULATE_BILL(billdetailModel, currentUnits, mLocalTarrifModel, Integer.parseInt(status.toString()), peakLoad);
//                            calculateIfSCST(currentUnits,status_code);
//                            billdetailModel.setSTATUS(currentReading+" "+billdetailModel.getNkt()+" "+billdetailModel.getFIXEDCHG());
//                            billdetailModel.setLASTMCONSUPTION(currentReading);
//                            insertBillDataIntoDB();
//                            Intent i = new Intent(getActivity(), BillInfoActivity.class);
//                            i.putExtra("model", new Gson().toJson(billdetailModel));
//                            praful
//                            i.putExtra("lastcon",Integer.parseInt(currentReading));
                            //   i.putExtra("currentReading", currentUnits);
//                            startActivity(i);
                        }
                    }


                    // For status code 03, minimum monthly charges to be taken
                    else if (status_code.equals("03")) {
                        if (performValidations(status_code)) {

                            //praful
                            String currentReading1 = billdetailModel.getUNITS();

                            // Minimum meter reading to be provided by agent
                            //curReadTv.setText("Minimum Monthly Charges");
                            String currentReading = "0";
//                                String minimumReading = "";
//                                String contractedLoad = billdetailModel.getCSM_CONNECTED_LOAD();
//                                String phase = billdetailModel.getPHASE();
//                                String category = billdetailModel.getCategory();
//                                String subCategory = billdetailModel.getSubcategory();
//                                if (phase.equals("1")) {
//                                    if (category.equals("1") && (subCategory.equals("0") || subCategory.equals("1") || subCategory.equals("2"))) {
//                                        if (Integer.parseInt(contractedLoad) <= 500) {
//                                            minimumReading = "25";
//                                        } else {
//                                            minimumReading = "50";
//                                        }
//
//                                    } else if (category.equals("2") && (subCategory.equals("0") || subCategory.equals("1"))) {
//                                        minimumReading = "65";
//
//                                    } else if (category.equals("7") && subCategory.equals("0")) {
//                                        minimumReading = "50";
//                                    } else if (category.equals("2") && subCategory.equals("2")) {
//                                        minimumReading = "300";
//                                    }
//                                } else if (phase.equals("3") ) {
//                                    if (category.equals("1") &&(subCategory.equals("0") || subCategory.equals("1") || subCategory.equals("2"))) {
//                                        minimumReading = "150";
//                                    } else if (category.equals("2") && (subCategory.equals("0") || subCategory.equals("1"))) {
//                                        minimumReading = "200";
//                                    }
//                                    else if (category.equals("2") && subCategory.equals("2")) {
//                                        minimumReading = "300";
//                                    }else if (category.equals("7") && subCategory.equals("0")) {
//                                        minimumReading = "150";
//                                    }
//                                }else {
//                                    Globals.showToast(getActivity(),"No phase for current user");
//                                }
                            billdetailModelSCST = new GetBillDetailsModel(billdetailModel);
                            try {
                                billdetailModel = StringConstants.CALICULATE_BILL(billdetailModel, currentReading, mLocalTarrifModel, Integer.parseInt(status), peakLoad);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            try {
                                calculateIfSCST(currentReading,status_code);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
//                            billdetailModel.setSTATUS(currentReading1+" "+billdetailModel.getNkt()+" "+billdetailModel.getFIXEDCHG());
                            billdetailModel.setLASTMCONSUPTION(currentReading1);
                            insertBillDataIntoDB();
                            Intent i = new Intent(getActivity(), BillInfoActivity.class);
                            i.putExtra("model", new Gson().toJson(billdetailModel));
                            //praful
//                            i.putExtra("lastcon",Integer.parseInt(currentReading1));
                            startActivity(i);
                        }
                    }

                    // status code 04
//                        else if (status_code.equals("04")) {
//                            if (performValidations(status_code)) {
//                                billdetailModel.setPreviousMeterEndRead(previousEndReadTV.getText().toString());
//                                billdetailModel.setCurrentMeterStartRead(currentStartReadTV.getText().toString());
//                                billdetailModel.setCurrentMeterEndRead(currentEndReadTV.getText().toString());
//                                int lastMonthConsumption = Integer.parseInt(billdetailModel.getCLRDNG());
//                                if (lastMonthConsumption > 0) {
//                                    String currReading = String.valueOf((Integer.parseInt(currentEndReadTV.getText().toString())) - (Integer.parseInt(currentStartReadTV.getText().toString())) + ((Integer.parseInt(previousEndReadTV.getText().toString())) - lastMonthConsumption));
//                                    billdetailModel = StringConstants.CALICULATE_BILL(billdetailModel, currReading, mLocalTarrifModel, Integer.parseInt(statusEt.getText().toString()));
//                                    insertBillDataIntoDB();
//
//                                    Intent i = new Intent(getActivity(), BillInfoActivity.class);
//                                    i.putExtra("model", new Gson().toJson(billdetailModel));
//                                    startActivity(i);
//                                } else {
//                                    Globals.showToast(getActivity(), "lastMonthConsumption value is not valid");
//                                }
//                            }
//                        }
                    else if (status_code.equals("04")) {
                        if (performValidations(status_code)) {

                            // current Meter Reading
                            int presentReading = Integer.parseInt(curReadTv1.getText().toString());
                            //Current Meter Start Reading
                            int presentStartReading = Integer.parseInt(currentStartReadTV.getText().toString());
                            // old meter reading
                            int oldMeterReading = Integer.parseInt(oldMeterReadEt.getText().toString());
                            currentReadingLyt.setVisibility(View.GONE);
                            currentReadingLyt.setVisibility(View.GONE);
                            billdetailModel.setCSM_METER_NO(newMeter.getText().toString());
                            // last month consumption
                            lastMonthConsumption = billdetailModel.getCLRDNG();
                            String lastConsumptionUnits = billdetailModel.getUNITS();
                            String lastMeterReading = billdetailModel.getCLRDNG();
                            if (lastMeterReading != null) {
                                String currReading;

                                int lastConsumption = Integer.parseInt(lastMeterReading);
                                Log.e("last meter reading : ",""+lastMonthConsumption);
                                int totalReading = (presentReading - presentStartReading) + (oldMeterReading - lastConsumption);
                                currReading = Integer.toString(totalReading);
                                Globals.showToast(getActivity(), "Total Consumption is " + currReading);
                                //
                                //praful
                                billdetailModel.setCLRDNG(String.valueOf(presentReading));
                                billdetailModelSCST = new GetBillDetailsModel(billdetailModel);

                                try {
                                    billdetailModel = StringConstants.CALICULATE_BILL(billdetailModel, currReading, mLocalTarrifModel, Integer.parseInt(status), peakLoad);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    calculateIfSCST(currReading,status_code);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

//                                billdetailModel.setSTATUS(lastConsumptionUnits+" "+billdetailModel.getNkt()+" "+billdetailModel.getFIXEDCHG());
                                billdetailModel.setLASTMCONSUPTION(lastConsumptionUnits);
                                insertBillDataIntoDB();
                            } else {
                                Globals.showToast(getActivity(), "The last month consumption is null");

                            }

                            Intent i = new Intent(getActivity(), BillInfoActivity.class);
                            i.putExtra("model", new Gson().toJson(billdetailModel));
//                            i.putExtra("lastcon",Integer.parseInt(lastConsumptionUnits));

                            startActivity(i);
                        }
                    }

                     /*else if (!statusEt.getText().toString().equals("") &&
                            Integer.parseInt(statusEt.getText().toString()) >= 1 &&
                            Integer.parseInt(statusEt.getText().toString()) > 0 &&
                            Integer.parseInt(statusEt.getText().toString()) <= 30) {
                        billdetailModel = StringConstants.CALICULATE_BILL(billdetailModel, curReadTv.getText().toString(), mLocalTarrifModel, Integer.parseInt(statusEt.getText().toString()));
                        insertBillDataIntoDB();
                        Intent i = new Intent(getActivity(), BillInfoActivity.class);
                        i.putExtra("model", new Gson().toJson(billdetailModel));
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(getActivity(), R.string.valid_status, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.valid_reading, Toast.LENGTH_SHORT).show();
                }*/

                    // For status code 02, present reading to be entered as 0 and automatically average must be taken


                    // last month value to be taken for 05
                    else if (status_code.equals("05")) {
                        if (performValidations(status_code)) {
                            String currentReading = billdetailModel.getUNITS();
                            billdetailModelSCST = new GetBillDetailsModel(billdetailModel);
                            try {
                                billdetailModel = StringConstants.CALICULATE_BILL(billdetailModel, currentReading, mLocalTarrifModel, Integer.parseInt(status), peakLoad);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            try {
                                calculateIfSCST(currentReading,status_code);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
//                            billdetailModel.setSTATUS(currentReading+" "+billdetailModel.getNkt()+" "+billdetailModel.getFIXEDCHG());
                            billdetailModel.setLASTMCONSUPTION(currentReading);
                            insertBillDataIntoDB();
                            Intent i = new Intent(getActivity(), BillInfoActivity.class);
                            i.putExtra("model", new Gson().toJson(billdetailModel));
                            i.putExtra("lastcon",Integer.parseInt(currentReading));
                            startActivity(i);
                        }
                    }

                    // For ststus code 06, average units to be taken
                    else if (status_code.equals("06")) {
                        //  average unit reading to be taken
                        if (performValidations(status_code)) {
                            String currentReading1 = billdetailModel.getUNITS();
                            // curReadTv.setText("Average Units will be considered");
                            String currentReading = billdetailModel.getPREAVG();
                            billdetailModelSCST = new GetBillDetailsModel(billdetailModel);
                            try {
                                billdetailModel = StringConstants.CALICULATE_BILL(billdetailModel, currentReading, mLocalTarrifModel, Integer.parseInt(status), peakLoad);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            try {
                                calculateIfSCST(currentReading,status_code);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
//                            billdetailModel.setSTATUS(currentReading1+" "+billdetailModel.getNkt()+" "+billdetailModel.getFIXEDCHG());
                            billdetailModel.setLASTMCONSUPTION(currentReading1);
                            insertBillDataIntoDB();
                            Intent i = new Intent(getActivity(), BillInfoActivity.class);
                            i.putExtra("model", new Gson().toJson(billdetailModel));
//                            i.putExtra("lastcon",Integer.parseInt(currentReading1));
                            startActivity(i);
                        }
                    } else if (status_code.equals("07")) {
                        if (performValidations(status_code)) {
                            int lastMonthReading = Integer.parseInt(billdetailModel.getCLRDNG());

                            //praful 18/11
                            String lastConsumptionUnits = billdetailModel.getUNITS();

                            if (Double.parseDouble(billdetailModel.getCLRDNG()) < 999) {
                                DAIL_VALUE = 999;
                            } else if (Double.parseDouble(billdetailModel.getCLRDNG()) < 9999) {
                                DAIL_VALUE = 9999;
                            } else if (Double.parseDouble(billdetailModel.getCLRDNG()) < 99999) {// 5 Digit
                                DAIL_VALUE = 99999;
                            } else if (Double.parseDouble(billdetailModel.getCLRDNG()) < 999999) { // 6 Digit
                                DAIL_VALUE = 999999;
                            } else if (Double.parseDouble(billdetailModel.getCLRDNG()) < 9999999) { // 6 Digit
                                DAIL_VALUE = 9999999;
                            } else if (Double.parseDouble(billdetailModel.getCLRDNG()) < 99999999) { // 6 Digit
                                DAIL_VALUE = 99999999;
                            } else if (Double.parseDouble(billdetailModel.getCLRDNG()) < 999999999) { // 6 Digit
                                DAIL_VALUE = 999999999;
                            }

                            int lastMeterReading = DAIL_VALUE - lastMonthReading;
                            int currReading = Integer.parseInt(curReadTv.getText().toString()) + lastMeterReading;
                            String currentReading = Integer.toString(currReading);
                            String presentReading = curReadTv.getText().toString();// s
                            billdetailModelSCST = new GetBillDetailsModel(billdetailModel);
                            try {
                                billdetailModel = StringConstants.CALICULATE_BILL(billdetailModel, currentReading, mLocalTarrifModel, Integer.parseInt(status), peakLoad);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            try {
                                calculateIfSCST(currentReading,status_code);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
//                            billdetailModel.setSTATUS(lastConsumptionUnits+" "+billdetailModel.getNkt()+" "+billdetailModel.getFIXEDCHG());
                           // lastMonthConsumptionEt.setText(billdetailModel.getOPRDNG());
                            billdetailModel.setCLRDNG(presentReading);
                            billdetailModel.setUNITS(String.valueOf(currReading));
                            billdetailModel.setLASTMCONSUPTION(lastConsumptionUnits);
                            insertBillDataIntoDB();
                            Intent i = new Intent(getActivity(), BillInfoActivity.class);
                            i.putExtra("model", new Gson().toJson(billdetailModel));
                            //praful
//                            i.putExtra("lastcon",Integer.parseInt(lastConsumptionUnits));
                            i.putExtra("dial",1);

                            startActivity(i);
                        }
                    }/*else if (status_code.equals("07")) {
                        if (performValidations(status_code)) {
                            int lastMonthReading = Integer.parseInt(billdetailModel.getCLRDNG());

                            //praful 18/11
                            String lastConsumptionUnits = billdetailModel.getUNITS();
                            if (Double.parseDouble(billdetailModel.getCLRDNG()) < 999) {
                                DAIL_VALUE = 999;
                            } else if (Double.parseDouble(billdetailModel.getCLRDNG()) < 9999) {
                                DAIL_VALUE = 9999;
                            } else if (Double.parseDouble(billdetailModel.getCLRDNG()) < 99999) {// 5 Digit
                                DAIL_VALUE = 99999;
                            } else if (Double.parseDouble(billdetailModel.getCLRDNG()) < 999999) { // 6 Digit
                                DAIL_VALUE = 999999;
                            } else if (Double.parseDouble(billdetailModel.getCLRDNG()) < 9999999) { // 6 Digit
                                DAIL_VALUE = 9999999;
                            } else if (Double.parseDouble(billdetailModel.getCLRDNG()) < 99999999) { // 6 Digit
                                DAIL_VALUE = 99999999;
                            } else if (Double.parseDouble(billdetailModel.getCLRDNG()) < 999999999) { // 6 Digit
                                DAIL_VALUE = 999999999;
                            }
                            int lastMeterReading = DAIL_VALUE - lastMonthReading;
                            int currReading = Integer.parseInt(curReadTv.getText().toString()) + lastMeterReading;
                           String currentReading = Integer.toString(currReading);
                            String presentReading = curReadTv.getText().toString();// s
                            //billdetailModel.setUNITS(Integer.toString(currReading));
                            billdetailModelSCST = new GetBillDetailsModel(billdetailModel);
                            try {
                                billdetailModel = StringConstants.CALICULATE_BILL(billdetailModel, currentReading, mLocalTarrifModel, Integer.parseInt(status), peakLoad);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            try {
                                calculateIfSCST(currentReading,status_code);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
//                            billdetailModel.setSTATUS(lastConsumptionUnits+" "+billdetailModel.getNkt()+" "+billdetailModel.getFIXEDCHG());
                            billdetailModel.setLASTMCONSUPTION(lastConsumptionUnits);
                          //  billdetailModel.setCLRDNG(billdetailModel.getCLRDNG()); //s
                            insertBillDataIntoDB();
                            Intent i = new Intent(getActivity(), BillInfoActivity.class);
                            i.putExtra("model", new Gson().toJson(billdetailModel));
                            //praful
                            i.putExtra("lastcon",Integer.parseInt(lastConsumptionUnits));
                            i.putExtra("dial",1);

                            startActivity(i);
                        }
                    }*/
                    // For status code 08, average units to be taken
                    else if (status_code.equals("08")) {
                        curReadTv.setText("Average Units will be considered");
                        if (performValidations(status_code)) {
                            String currentReading1 = billdetailModel.getUNITS();
                            String currentReading = billdetailModel.getPREAVG();
                            billdetailModelSCST = new GetBillDetailsModel(billdetailModel);
                            try {
                                billdetailModel = StringConstants.CALICULATE_BILL(billdetailModel, currentReading, mLocalTarrifModel, Integer.parseInt(status), peakLoad);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            try {
                                calculateIfSCST(currentReading,status_code);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
//                            billdetailModel.setSTATUS(currentReading1+" "+billdetailModel.getNkt()+" "+billdetailModel.getFIXEDCHG());
                            billdetailModel.setLASTMCONSUPTION(currentReading1);
                            insertBillDataIntoDB();
                            Intent i = new Intent(getActivity(), BillInfoActivity.class);
                            i.putExtra("model", new Gson().toJson(billdetailModel));
//                            i.putExtra("lastcon",Integer.parseInt(currentReading1));
                            startActivity(i);
                        }
                    }

                    // For status code 09, monthly min charges
                    else if (status_code.equals("09")) {
                        if (performValidations(status_code)) {
                            String currentReading1 = billdetailModel.getUNITS();
                            //  curReadTv.setText("Monthly minimum charges will be considered");
                            String currentReading = "0";
                            billdetailModelSCST = new GetBillDetailsModel(billdetailModel);
                            try {
                                billdetailModel = StringConstants.CALICULATE_BILL(billdetailModel, currentReading, mLocalTarrifModel, Integer.parseInt(status), peakLoad);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            try {
                                calculateIfSCST(currentReading,status_code);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
//                            billdetailModel.setSTATUS(currentReading1+" "+billdetailModel.getNkt()+" "+billdetailModel.getFIXEDCHG());
                            billdetailModel.setLASTMCONSUPTION(currentReading1);
                            insertBillDataIntoDB();
                            Intent i = new Intent(getActivity(), BillInfoActivity.class);
                            i.putExtra("model", new Gson().toJson(billdetailModel));
//                            i.putExtra("lastcon",Integer.parseInt(currentReading1));
                            startActivity(i);
                        }
                    }

                    // For status code 10, ten meter readings to be taken
                    else if (status_code.equals("10")) {
                        if (performValidations(status_code)) {

                            String currentReading1 = billdetailModel.getUNITS();

//                            int meterOne = Integer.parseInt(meterOneEt.getText().toString());
//                            int meterTwo = Integer.parseInt(meterTwoEt.getText().toString());
//                            int meterThree = Integer.parseInt(meterThreeEt.getText().toString());
//                            int meterFour = Integer.parseInt(meterFourEt.getText().toString());
//                            int meterFive = Integer.parseInt(meterFiveEt.getText().toString());
//                            int meterSix = Integer.parseInt(meterSixEt.getText().toString());
//                            int meterSeven = Integer.parseInt(meterSevenEt.getText().toString());
//                            int meterEight = Integer.parseInt(meterEightEt.getText().toString());
//                            int meterNine = Integer.parseInt(meterNineEt.getText().toString());
//                            int meterTen = Integer.parseInt(meterTenEt.getText().toString());
//                            curReadTv.setEnabled(false);
                            int totalReading = meterReading;//meterOne + meterTwo + meterThree + meterFour + meterFive + meterSix + meterSeven + meterEight + meterNine + meterTen;

                            String currentReading = Integer.toString(totalReading);
                            billdetailModelSCST = new GetBillDetailsModel(billdetailModel);
                            try {
                                billdetailModel = StringConstants.CALICULATE_BILL(billdetailModel, currentReading, mLocalTarrifModel, Integer.parseInt(status), peakLoad);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            try {
                                calculateIfSCST(currentReading,status_code);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
//                            billdetailModel.setSTATUS(currentReading1+" "+billdetailModel.getNkt()+" "+billdetailModel.getFIXEDCHG());
                            billdetailModel.setLASTMCONSUPTION(currentReading1);
                            insertBillDataIntoDB();
                            Intent i = new Intent(getActivity(), BillInfoActivity.class);
                            i.putExtra("model", new Gson().toJson(billdetailModel));
//                            i.putExtra("lastcon",Integer.parseInt(currentReading1));
                            startActivity(i);

                            //praful
                            meterReading = 0;
                        }
                    } else if (status_code.equals("11")) {
                        if (performValidations(status_code)) {
                            String meterCharges = "";
                            String averageUnits = billdetailModel.getPREAVG();

                            String currentReading1 = billdetailModel.getUNITS();

                            if (burntMeterValue.equals("S-Phase")) {
                                meterCharges = "960";
                            } else if (burntMeterValue.equals("3-Phase")) {
                                meterCharges = "2370";
                            } else if (burntMeterValue.equals("LT-trivector")) {
                                meterCharges = "7320";
                            } else if (burntMeterValue.equals("HT-trivector")) {
                                meterCharges = "12620";
                            } else if (burntMeterValue.equals("Select Meter")) {
                                Globals.showToast(getActivity(), "Select Meter Type");

                            } else {
                                Globals.showToast(getActivity(), "Select Meter Type");
                            }

                            billdetailModel.setBurntValue(meterCharges);
                            // To do : add meter charges based on the meter category
                            billdetailModelSCST = new GetBillDetailsModel(billdetailModel);
                            try {
                                billdetailModel = StringConstants.CALICULATE_BILL(billdetailModel, averageUnits, mLocalTarrifModel, Integer.parseInt(status), peakLoad);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            try {
                                calculateIfSCST(averageUnits,status_code);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
//                            calculateIfSCST(averageUnits,status_code);
//                            billdetailModel.setSTATUS(currentReading1+" "+billdetailModel.getNkt()+" "+billdetailModel.getFIXEDCHG()+" "+billdetailModel.getBurntValue());
                            billdetailModel.setLASTMCONSUPTION(currentReading1);
                            insertBillDataIntoDB();
                            Globals.showToast(getActivity(), "this is the value of meter" + billdetailModel.getBurntValue());
                            Intent i = new Intent(getActivity(), BillInfoActivity.class);
                            i.putExtra("model", new Gson().toJson(billdetailModel));
//                            i.putExtra("lastcon",Integer.parseInt(currentReading1));
                            i.putExtra("charge",Integer.parseInt(billdetailModel.getBurntValue()));
                            startActivity(i);
                        }
                    }
                    // Must stop the bill for that particular service
                    else if (status_code.equals("13")) {
                        if (performValidations(status_code)) {

                            String currentReading1 = billdetailModel.getUNITS();

                            String currentReading = curReadTv.getText().toString();
                            billdetailModel.setSTATUS("13");
                            billdetailModelSCST = new GetBillDetailsModel(billdetailModel);
                            try {
                                billdetailModel = StringConstants.CALICULATE_BILL(billdetailModel, currentReading, mLocalTarrifModel, Integer.parseInt(status), peakLoad);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            try {
                                calculateIfSCST(currentReading,status_code);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
//                            billdetailModel.setSTATUS(currentReading1+" "+billdetailModel.getNkt()+" "+billdetailModel.getFIXEDCHG());
                            billdetailModel.setLASTMCONSUPTION(currentReading1);
                            insertBillDataIntoDB();
                            Intent i = new Intent(getActivity(), BillInfoActivity.class);
                            i.putExtra("model", new Gson().toJson(billdetailModel));
//                            i.putExtra("lastcon",Integer.parseInt(currentReading1));
                            startActivity(i);
                        }
                    }
//                        // must stop meter
                    else if (status_code.equals("14")) {
                        if (performValidations(status_code)) {

                            String currentReading1 = billdetailModel.getUNITS();

                            String currentReading = curReadTv.getText().toString();
                            billdetailModel.setSTATUS("14");
                            billdetailModelSCST = new GetBillDetailsModel(billdetailModel);
                            try {
                                billdetailModel = StringConstants.CALICULATE_BILL(billdetailModel, currentReading, mLocalTarrifModel, Integer.parseInt(status), peakLoad);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            try {
                                calculateIfSCST(currentReading,status_code);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
//                            billdetailModel.setSTATUS(currentReading1+" "+billdetailModel.getNkt()+" "+billdetailModel.getFIXEDCHG());
                            billdetailModel.setLASTMCONSUPTION(currentReading1);
                            insertBillDataIntoDB();
                            Intent i = new Intent(getActivity(), BillInfoActivity.class);
                            i.putExtra("model", new Gson().toJson(billdetailModel));
//                            i.putExtra("lastcon",Integer.parseInt(currentReading1));
                            startActivity(i);
                        }
                    }
                    if (status_code.equals("17")) {
                        if (performValidations(status_code)) {

                            String currentReading1 = billdetailModel.getUNITS();

                            String currentReading = curReadTv.getText().toString();
                            billdetailModelSCST = new GetBillDetailsModel(billdetailModel);
                            try {
                                billdetailModel = StringConstants.CALICULATE_BILL(billdetailModel, currentReading, mLocalTarrifModel, Integer.parseInt(status), peakLoad);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            try {
                                calculateIfSCST(currentReading,status_code);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
//                            billdetailModel.setSTATUS(currentReading1+" "+billdetailModel.getNkt()+" "+billdetailModel.getFIXEDCHG());
                            billdetailModel.setLASTMCONSUPTION(currentReading1);
                            insertBillDataIntoDB();
                            Intent i = new Intent(getActivity(), BillInfoActivity.class);
                            i.putExtra("model", new Gson().toJson(billdetailModel));
//                            i.putExtra("lastcon",Integer.parseInt(currentReading1));
                            startActivity(i);
                        }
                    } else if (status_code.equals("18")) {
                        if (performValidations(status_code)) {

                            String currentReading1 = billdetailModel.getUNITS();

                            String currentReading = curReadTv.getText().toString();
//                            String billAmount = billdetailModel.getAdj_amount();
//                            billdetailModel.setAdj_amount(billAmount);
                            billdetailModelSCST = new GetBillDetailsModel(billdetailModel);
                            try {
                                billdetailModel = StringConstants.CALICULATE_BILL(billdetailModel, currentReading, mLocalTarrifModel, Integer.parseInt(status), peakLoad);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            try {
                                calculateIfSCST(currentReading,status_code);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
//                            billdetailModel.setSTATUS(currentReading1+" "+billdetailModel.getNkt()+" "+billdetailModel.getFIXEDCHG());
                            billdetailModel.setLASTMCONSUPTION(currentReading1);
                            insertBillDataIntoDB();
                            Intent i = new Intent(getActivity(), BillInfoActivity.class);
                            i.putExtra("model", new Gson().toJson(billdetailModel));
//                            i.putExtra("lastcon",Integer.parseInt(currentReading1));
                            startActivity(i);
                        }
                    }

                    // for status code 21
                    else if (status_code.equals("21")) {
                        if (performValidations(status_code)) {

                            String currentReading1 = billdetailModel.getUNITS();

//                            int meterOne = Integer.parseInt(twoMetersFirstEt.getText().toString());
//                            int meterTwo = Integer.parseInt(twoMetersSecondEt.getText().toString());

                            int totalReading = meterReading;

                            String currentReading = Integer.toString(totalReading);
                            billdetailModelSCST = new GetBillDetailsModel(billdetailModel);
                            try {
                                billdetailModel = StringConstants.CALICULATE_BILL(billdetailModel, currentReading, mLocalTarrifModel, Integer.parseInt(status), peakLoad);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            try {
                                calculateIfSCST(currentReading,status_code);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
//                            billdetailModel.setSTATUS(currentReading1+" "+billdetailModel.getNkt()+" "+billdetailModel.getFIXEDCHG());
                            billdetailModel.setLASTMCONSUPTION(currentReading1);
                            insertBillDataIntoDB();
                            Intent i = new Intent(getActivity(), BillInfoActivity.class);
                            i.putExtra("model", new Gson().toJson(billdetailModel));
//                            i.putExtra("lastcon",Integer.parseInt(currentReading1));

                            startActivity(i);

                            meterReading = 0;

                        }
                    }
                } else {
                    Globals.showToast(getActivity(), "Status cannot be empty ");
                }

                break;
            case R.id.addMeterReading:

                String mr = "";

                if(!meters.getText().toString().equals("")){
                    mr = meters.getText().toString();
                    count++;
                    showReading = showReading+"No: "+count+" Reading :"+mr+"\n";
                    meterReading = meterReading + Integer.parseInt(mr);
                    textMeters.setText(showReading);
                    meters.setText("");
//                    meters.setFocusable(true);
                }
//                private TextView textMeters;
//                private String showReading;
//                private int meterReading = 0;


                break;
        }

    }

    private void calculateIfSCST(String currentReading, String status_code) throws ParseException {
        int units = Integer.parseInt(billdetailModel.getUNITS());
        if((billdetailModel.getCSM_CASTE().toUpperCase().equals("SC") || billdetailModel.getCSM_CASTE().toUpperCase().equals("ST"))
                && units<=125){

            String curUnits = billdetailModel.getUNITS();

            if(units>100 && units<=125){

                //calculate 1st 100
                String curRead100 = String.valueOf(Integer.parseInt(currentReading) - (units-100));
                //calculate for free energy charges with duplicate billdetailModel
                billdetailModelSCST = StringConstants.CALICULATE_BILL(billdetailModelSCST, curRead100, mLocalTarrifModel, Integer.parseInt(status_code),peakLoad);
                String freeEnergyCharge = billdetailModelSCST.getENGCHG();

                float eCharge = Float.parseFloat(billdetailModel.getENGCHG());
                float chargable = (eCharge/units) *(units-100);
                float freeC = Float.parseFloat(freeEnergyCharge);
//                float difference = eCharge-freeC;

                billdetailModel.setAdj_amount(freeEnergyCharge);

                float amount = Float.parseFloat(billdetailModel.getBILLAMT()) - freeC;
                billdetailModel.setBILLAMT(String.valueOf(Math.round(amount)));


            }else if(units<= 100){
                //calculate 100 or less units to show adjustment amount

                billdetailModel.setAdj_amount(billdetailModel.getENGCHG());
                float eCharge = Float.parseFloat(billdetailModel.getENGCHG());
                float amount = Float.parseFloat(billdetailModel.getBILLAMT())-eCharge;
                billdetailModel.setBILLAMT(String.valueOf((Math.round(amount))));

            }


        }else{

        }

    }

    private void insertBillDataIntoDB() {

        String sql;
        SQLiteStatement insertStmt;
        final SQLiteDatabase sqLiteDatabase;
        final String DATABASE_NAME = "CustomerServices";
        sqLiteDatabase = openOrCreateDatabase(Utils.getSharedInstance().databasePath + "RecsBillingData", null);


        sql = "INSERT INTO Billsgen(" +
                "SERVICENO," +
                "AREACODE," +
                "OLDRDG_KWH," +
                "PRESRDG_KWH," +
                "UNITS," +
                "ENGCHG," +
                "CUSCHG," +
                "EDCHG," +
                "BILLAMT," +
                "ADJAMT," +
                "TOTALAMT," +
                "CATEGORY," +
                "CONSUMER_NAME," +
                "STATUS," +
                "LORG," +
                "PRESDT," +
                "BILL_STATUS," +
                "LASTCONSUMPTIONUNIT," +
                "NKT," +
                "FIXEDCHARGE," +
                "METERBRUNTVALUE," +
                "AADHARNO," +
                "PHONENO," +
                "NEWMETERNO," +
                "BILLNO," +
                "OPSTATUS," +
                "BILLDATE," +
                "LPAMT," +
                "DUEDATE," +
                "DISCONDATE," +
                "LOAD," +
                "CASTE," +
                "NEWARREARS" +
                ")VALUES (?,?,?,?,?," +
                "?,?,?,?,?," +
                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";



        insertStmt = sqLiteDatabase.compileStatement(sql);


        insertStmt.clearBindings();

        if (billdetailModel.getSCNO() != null) {
            insertStmt.bindString(1, billdetailModel.getSCNO());
        } else {
            insertStmt.bindString(1, "");
        }

        if (billdetailModel.getAREACODE() != null) {
            insertStmt.bindString(2, billdetailModel.getAREACODE());
        } else {
            insertStmt.bindString(2, "");
        }

        if (billdetailModel.getOPRDNG() != null) {
            insertStmt.bindString(3, billdetailModel.getOPRDNG());
        } else {
            insertStmt.bindString(3, "");
        }

        if (billdetailModel.getCLRDNG() != null) {
            insertStmt.bindString(4, billdetailModel.getCLRDNG());
        } else {
            insertStmt.bindString(4, "");
        }

        if (billdetailModel.getUNITS() != null) {
            insertStmt.bindString(5, billdetailModel.getUNITS());
        } else {
            insertStmt.bindString(5, "");
        }
        if (billdetailModel.getENGCHG() != null) {
            insertStmt.bindString(6, billdetailModel.getENGCHG());
        } else {
            insertStmt.bindString(6, "");
        }
        if (billdetailModel.getCUSCHG() != null) {
            insertStmt.bindString(7, billdetailModel.getCUSCHG());
        } else {
            insertStmt.bindString(7, "");
        }
        if (billdetailModel.getEDCHG() != null) {
            insertStmt.bindString(8, billdetailModel.getEDCHG());
        } else {
            insertStmt.bindString(8, "");
        }

        if (billdetailModel.getBILLAMT() != null) {
            insertStmt.bindString(9, billdetailModel.getBILLAMT());
        } else {
            insertStmt.bindString(9, "");
        }
        if (billdetailModel.getAdj_amount() != null) {
            insertStmt.bindString(10, billdetailModel.getAdj_amount());
        } else {
            insertStmt.bindString(10, "");
        }
        if (billdetailModel.getBILLAMT() != null) {
            insertStmt.bindString(11, billdetailModel.getBILLAMT());
        } else {
            insertStmt.bindString(11, "");
        }
        if (billdetailModel.getCategory() != null) {
            insertStmt.bindString(12, billdetailModel.getCategory() + billdetailModel.getSubcategory());
        } else {
            insertStmt.bindString(12, "");
        }
        if (billdetailModel.getCSM_CONSUMER_NAME() != null) {
            insertStmt.bindString(13, billdetailModel.getCSM_CONSUMER_NAME());
        } else {
            insertStmt.bindString(13, "");
        }


        if (billdetailModel.getSTATUS() != null) {
            insertStmt.bindString(14, billdetailModel.getSTATUS());
        } else {
            insertStmt.bindString(14, "");
        }

        if (billdetailModel.getLORG() != null) {
            insertStmt.bindString(15, billdetailModel.getSURCHRGS());
        } else {
            insertStmt.bindString(15, "");
        }
        if (billdetailModel.getCL2() != null) {
            insertStmt.bindString(16, billdetailModel.getCL2());
        } else {
            insertStmt.bindString(16, "");
        }
//        insertStmt.bindString(16, getDateTime());
        insertStmt.bindString(17, "0");

        if (billdetailModel.getLASTMCONSUPTION() != null) {
            insertStmt.bindString(18, billdetailModel.getLASTMCONSUPTION());
        } else {
            insertStmt.bindString(18, "");
        }

        if (billdetailModel.getNkt() != null) {
            billdetailModel.setNkt(billdetailModel.getNkt()+" "+billdetailModel.getCL2());
            insertStmt.bindString(19, billdetailModel.getNkt());
        } else {
            insertStmt.bindString(19, "");
        }

        if (billdetailModel.getFIXEDCHG() != null) {
            insertStmt.bindString(20, billdetailModel.getFIXEDCHG());
        } else {
            insertStmt.bindString(20, "");
        }

        if (billdetailModel.getBurntValue() != null) {
            insertStmt.bindString(21, billdetailModel.getBurntValue());
        } else {
            insertStmt.bindString(21, "");
        }

        if (billdetailModel.getCSM_AADHAAR_NO() != null) {
            insertStmt.bindString(22, billdetailModel.getCSM_AADHAAR_NO());
        } else {
            insertStmt.bindString(22, "");
        }

        if (billdetailModel.getCSM_PHONE_NO() != null) {
            insertStmt.bindString(23, billdetailModel.getCSM_PHONE_NO());
        } else {
            insertStmt.bindString(23, "");
        }

        if (billdetailModel.getCSM_METER_NO() != null) {
            insertStmt.bindString(24, billdetailModel.getCSM_METER_NO());
        } else {
            insertStmt.bindString(24, "");
        }

        if (billdetailModel.getSCNO() != null && billdetailModel.getAREACODE() != null && billdetailModel.getBILLDATE()!= null) {
            insertStmt.bindString(25, (billdetailModel.getBILLDATE()));
        } else {
            insertStmt.bindString(25, "");
        }

        if (billdetailModel.getOpstatus() != null) {
            insertStmt.bindString(26, billdetailModel.getOpstatus());
        } else {
            insertStmt.bindString(26, "");
        }


        if (billdetailModel.getBILLDATE() != null) {
            insertStmt.bindString(27, billdetailModel.getBILLDATE());
        } else {
            insertStmt.bindString(27, "");
        }

        if (billdetailModel.getLPAMT() != null) {
            insertStmt.bindString(28, billdetailModel.getLPAMT());
        } else {
            insertStmt.bindString(28, "");
        }

        if (billdetailModel.getDUEDATE() != null) {
            insertStmt.bindString(29, billdetailModel.getDUEDATE());
        } else {
            insertStmt.bindString(29, "");
        }

        if (billdetailModel.getDISCDATE() != null) {
            insertStmt.bindString(30, billdetailModel.getDISCDATE());
        } else {
            insertStmt.bindString(30, "");
        }

        if (billdetailModel.getCSM_CONNECTED_LOAD() != null) {
            insertStmt.bindString(31, billdetailModel.getCSM_CONNECTED_LOAD());
        } else {
            insertStmt.bindString(31, "");
        }

        if (billdetailModel.getCSM_CASTE() != null) {
            insertStmt.bindString(32, billdetailModel.getCSM_CASTE());
        } else {
            insertStmt.bindString(32, "");
        }
        if (billdetailModel.getClose_bal() != null) {
            insertStmt.bindString(33, billdetailModel.getClose_bal());
        } else {
            insertStmt.bindString(33, "");
        }



        //        private String OPSTATUS;
//        private String BILLDATE;
//        private String LPAMT;
//        private String DUEDATE;
//        private String DISCONDATE;
//        private String LOAD;


        insertStmt.execute();
    }

    private String getDateTime() {

        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }
    private String getBillNoDateTime() {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }


    private void checkWeatherBillUpdatedOrNot(String resultString) {

        final SQLiteDatabase sqLiteDatabase;
        sqLiteDatabase = openOrCreateDatabase(Utils.getSharedInstance().databasePath + "RecsBillingData", null);


        String sqlQuery = "SELECT * FROM Billsgen " + "WHERE SERVICENO='" + resultString + "'";
        Cursor c = sqLiteDatabase.rawQuery(sqlQuery, null);

        if (c.getCount() == 0) {
            Log.i("inserted",""+resultString);
            getDataFromLocalDataBase(resultString);
        } else {
            int count = c.getCount();
            Log.i("else",""+count);
            Globals.showToast(getActivity(), "Bill Already Generated");
        }

        c.close();
        sqLiteDatabase.close();
    }

    private boolean performValidations() {
        boolean isValid = false;

        if (serviceNoEt.getText().toString().equals("")) {
            Globals.showToast(getActivity(), "Please enter service number");
        } else if (serviceNoEt.getText().toString().length() < 10) {
            Globals.showToast(getActivity(), "Invalid service number");

        } else if (mServiceResponseInputJsondata == null) {
            if (!request.equals("")) {
//                getInputReadingDataFromServer(request);
            } else {
                Globals.showToast(getActivity(), "Area Code Empty");
            }
        } else if (mServiceResponseInputJsondata.getDetails() == null) {
            if (!request.equals("")) {
//                getInputReadingDataFromServer(request);
            } else {
                Globals.showToast(getActivity(), "Area Code Empty");
            }
        } else {
            isValid = true;
        }


        return isValid;
    }

    private boolean performValidations(String status) {
        boolean isValid = false;
//        if(aadhar.getText().toString().length() == 12){

//            if(billdetailModel.getCSM_AADHAAR_NO().length() ==12){
                if(aadhar.isEnabled())
                    if(!aadhar.getText().toString().trim().isEmpty())
                        if(aadhar.getText().toString().trim().length() == 12)
                            billdetailModel.setCSM_AADHAAR_NO(aadhar.getText().toString());
//            }
            if(billdetailModel.getCSM_PHONE_NO().length() != 10){
                billdetailModel.setCSM_PHONE_NO(phone.getText().toString());
            }
            if (status.equals("01")) {
                if (curReadTv.getText().toString().equals("")) {
                    Globals.showToast(getActivity(), "Please enter Current Reading");
                } else if (Integer.parseInt(billdetailModel.getCLRDNG()) > Integer.parseInt(curReadTv.getText().toString())) {

                    Globals.showToast(getActivity(), "Current Reading cannot be less than Previous Reading");
                } else {
                    isValid = true;
                }
            } else if (status.equals("02")) {
                isValid = true;

                //added on 5th march 2019
                /*if (curReadTv.getText().toString().equals("")) {
                    Globals.showToast(getActivity(), "Please enter Current Reading");
                } else if (Integer.parseInt(billdetailModel.getCLRDNG()) > Integer.parseInt(curReadTv.getText().toString())) {

                    Globals.showToast(getActivity(), "Current Reading cannot be less than Previous Reading");
                } else {
                    isValid = true;
                }*/
//            if(curReadTv.getText().toString().equals("")) {
//                Globals.showToast(getActivity(), "Please enter current Reading");
//            }else if(!(curReadTv.getText().toString().equals("0"))){
//                Globals.showToast(getActivity(),"Please enter 0");
//            } else if (Integer.parseInt(billdetailModel.getCLRDNG()) > Integer.parseInt(curReadTv.getText().toString())) {
//
//                Globals.showToast(getActivity(), "Current Reading cannot be less than Previous Reading");
//            }
//            else {
//                isValid = true;
//            }

            }
            else if (status.equals("03")) {
                isValid = true;

            } else if (status.equals("04")) {
                if (curReadTv1.getText().toString().equals("")) {
                    Globals.showToast(getActivity(), "Please enter current Reading");
                } else if (oldMeterReadEt.getText().toString().equals("")) {
                    Globals.showToast(getActivity(), "Please enter old Meter Reading");
                } else if (currentStartReadTV.getText().toString().equals("")) {
                    Globals.showToast(getActivity(), "Please enter current Start Reading");
                }
                // commented by praful
//            else if (Integer.parseInt(billdetailModel.getCLRDNG()) > Integer.parseInt(curReadTv.getText().toString())) {
//                Globals.showToast(getActivity(), "Current Reading cannot be less than last month consumption");
//            }

                else if (Integer.parseInt(curReadTv1.getText().toString()) < Integer.parseInt(currentStartReadTV.getText().toString())) {
                    Globals.showToast(getActivity(), "Current Reading cannot be less than the meter start reading");
                }

                else if (Integer.parseInt(oldMeterReadEt.getText().toString()) < Integer.parseInt(billdetailModel.getCLRDNG())) {
                    Globals.showToast(getActivity(), "Old meter start reading cannot be less than old meter end reading");
                }else if(newMeter.getText().toString().equals("")){
                    Globals.showToast(getActivity(), "Enter New Meter Number");
                }


                else {
                    isValid = true;
                }

            } else if (status.equals("05")) {
                isValid = true;

            } else if (status.equals("06")) {
                isValid = true;

            } else if (status.equals("07")) {
                if (curReadTv.getText().toString().equals("")) {
                    Globals.showToast(getActivity(), "Please enter current Reading");
                }

//            else if (Integer.parseInt(billdetailModel.getCLRDNG()) > Integer.parseInt(curReadTv.getText().toString())) {
//                Globals.showToast(getActivity(), "Current Reading cannot be less than Previous Reading");
//            }


                else {
                    isValid = true;
                }
            } else if (status.equals("08")) {
                isValid = true;
            } else if (status.equals("09")) {
                isValid = true;


            } else if (status.equals("10")) {
//            if (meterOneEt.getText().toString().equals("")) {
//                Globals.showToast(getActivity(), "Please enter first meter Reading");
//            } else if (meterTwoEt.getText().toString().equals("")) {
//                Globals.showToast(getActivity(), "Please enter second meter Reading");
//            } else if (meterThreeEt.getText().toString().equals("")) {
//                Globals.showToast(getActivity(), "Please enter third meter Reading");
//            } else if (meterFourEt.getText().toString().equals("")) {
//                Globals.showToast(getActivity(), "Please enter fourth meter Reading");
//            } else if (meterFiveEt.getText().toString().equals("")) {
//                Globals.showToast(getActivity(), "Please enter fifth meter Reading");
//            } else if (meterSixEt.getText().toString().equals("")) {
//                Globals.showToast(getActivity(), "Please enter sixth meter Reading");
//            } else if (meterSevenEt.getText().toString().equals("")) {
//                Globals.showToast(getActivity(), "Please enter seventh meter Reading");
//            } else if (meterEightEt.getText().toString().equals("")) {
//                Globals.showToast(getActivity(), "Please enter eighth meter Reading");
//            } else if (meterNineEt.getText().toString().equals("")) {
//                Globals.showToast(getActivity(), "Please enter ninth meter Reading");
//            } else if (meterTenEt.getText().toString().equals("")) {
//                Globals.showToast(getActivity(), "Please enter tenth meter Reading");
//            } else {
//                isValid = true;
//
//            }
                if(meterReading == 0){
                    Globals.showToast(getActivity(), "Reading Should not be zero");
                }else if (Integer.parseInt(billdetailModel.getCLRDNG()) > meterReading) {

                    Globals.showToast(getActivity(), "Current Reading cannot be less than Previous Reading");
                }
                else {
                    isValid = true;

                }
            } else if (status.equals("11")) {
                isValid = true;
            } else if (status.equals("13")) {
                if (curReadTv.getText().toString().equals("")) {
                    Globals.showToast(getActivity(), "Please enter current Reading");
                } else if (Integer.parseInt(billdetailModel.getCLRDNG()) > Integer.parseInt(curReadTv.getText().toString())) {
                    Globals.showToast(getActivity(), "Current Reading cannot be less than Previous Reading");
                } else {
                    isValid = true;
                }
            } else if (status.equals("14")) {
                if (curReadTv.getText().toString().equals("")) {
                    Globals.showToast(getActivity(), "Please enter current Reading");
                } else if (Integer.parseInt(billdetailModel.getCLRDNG()) > Integer.parseInt(curReadTv.getText().toString())) {
                    Globals.showToast(getActivity(), "Current Reading cannot be less than Previous Reading");
                } else {
                    isValid = true;
                }
            } else if (status.equals("17")) {
                if (curReadTv.getText().toString().equals("")) {
                    Globals.showToast(getActivity(), "Please enter current Reading");
                } else if (Integer.parseInt(billdetailModel.getCLRDNG()) > Integer.parseInt(curReadTv.getText().toString())) {
                    Globals.showToast(getActivity(), "Current Reading cannot be less than Previous Reading");
                } else {
                    isValid = true;
                }
            } else if (status.equals("18")) {
                if (curReadTv.getText().toString().equals("")) {
                    Globals.showToast(getActivity(), "Please enter current Reading");
                } else if (Integer.parseInt(billdetailModel.getCLRDNG()) > Integer.parseInt(curReadTv.getText().toString())) {
                    Globals.showToast(getActivity(), "Current Reading cannot be less than Previous Reading");
                } else {
                    isValid = true;
                }
            } else if (status.equals("21")) {
                if(meterReading == 0){
                    Globals.showToast(getActivity(), "Reading Should not be zero");
                }else if (Integer.parseInt(billdetailModel.getCLRDNG()) > meterReading) {

                    Globals.showToast(getActivity(), "Current Reading cannot be less than Previous Reading");
                } else {
                    isValid = true;
                }
            } else {
                isValid = false;
            }

//        }else if(aadhar.getText().toString().length() == 0){
//            Globals.showToast(getActivity(), "Please Update Aadhar Number");
//        }else if(aadhar.getText().toString().length()<12){
//            Globals.showToast(getActivity(), "Aadhar Number must be 12 Digit");
//        }else
//            Globals.showToast(getActivity(), "Please Update Aadhar Number");

        return isValid;
    }


    public void alertDialog() {
        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getActivity());
        }
        builder.setMessage(R.string.meter_reading)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        dialog.dismiss();
                    }
                }).show();
    }

    private void parseLocalTarrifJsonData(final String data) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Gson gson = new Gson();
                    mLocalTarrifModel = gson.fromJson(data, LocalTarrifModel.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    //class AddTask extends AsyncTask<String, String, String> {
    class AddTask extends AsyncTask<Void, String, Void> {

        protected void onPreExecute() {
//create and display your alert here

            ll_loading.setVisibility(View.VISIBLE);
        }

        protected Void doInBackground(Void... unused) {

            // here is the thread's work ( what is on your method run()


            final SQLiteDatabase sqLiteDatabase;
            final String DATABASE_NAME = "CustomerServices";
            sqLiteDatabase = openOrCreateDatabase(Utils.getSharedInstance().databasePath + "RecsBillingData", null);


            Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM CustomerServices", null);


            mServiceNumbers.clear();
            if (c.moveToFirst()) {
                do {
                    Log.d("dorami", "onCreate: " + DatabaseUtils.dumpCursorToString(c));
                    mServiceNumbers.add(c.getString(c.getColumnIndex("SERVICENO")));
                } while (c.moveToNext());
            }
            c.close();


            try {


                Gson gson = new Gson();
                mServiceResponseInputJsondata = gson.fromJson(JSONDATA, ServiceResponseInputModel.class);
                Log.e("data",mServiceResponseInputJsondata.toString());

                // pagination 101<100
                //page_id : number of records to be fetched in service call and start index.
                if (Integer.parseInt(mServiceResponseInputJsondata.getTotal_records()) < (page_id - 20)) {
                    isRecoresAviable = false;
                } else {
                    page_id = page_id + 20;
                    request = SharedPreferenceUtil.getAreaCode(getActivity()) + "&start=" + page_id + "";
                }

                String sql;
                SQLiteStatement insertStmt;
                //sql = "INSERT INTO CustomerServices(id, BILLNO, MACHINEID ,
                // Service_No , CSM_CONSUMER_CODE , Name , Ero_Code , Area_Code ,
                // Address1 , Address2 , Address3 ,Category  , Sub_Category ,
                // OLD_RDG_KWH , OLD_RDG_KVAH , OLDDT , PRESRDG_KVAH  , PRESRDG_KWH ,
                // PRESRDG_LT , PRESRDG_KVA , PRESDT , PRESSTS , PRESSTS_LT , INITRDG_KWH ,
                // FINALRDG_KWH , INITRDG_KVAH , FINALRDG_KVAH , units , ltunits , ENGCHG ,
                // FIXEDCHG , CUSCHG , EDCHG , LPF_CAP_CHG , BILLAMT , ADJAMT , btotal1 ,
                // AVGUNITS ,OLDARREARS , NEWARREARS , ASURCHG , AEDINT , PAMOUNT , PEDCHG , ACD , FSA , others1 , others2 , duedate , disdate , subsidy , pf , lorg , md , kvah , EROCODE , catg , billmonth , pay_status , phone, load, phase) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                sql = "INSERT INTO CustomerServices(" +
                        "id," +
                        "AREACODE," +
                        "month," +
                        "SERVICENO ," +
                        "Category ," +
                        "Subcategory ," +
                        "Slbamoth ," +
                        "groupid ," +
                        "open_bal ," +
                        "close_bal ," +
                        "dj ," +
                        "adj_amount ," +
                        "phese ," +
                        "opmonth ," +
                        "opstatus ," +
                        "OPRDNG ," +
                        "STATUS ," +
                        "UNITS ," +
                        "DATEDIS ," +
                        "METERCY ," +
                        "EDINT ," +
                        "CLEDUTY ," +
                        "CELEDINT ," +
                        "OPEDINT ," +
                        "CAPCHRGS ," +
                        "SURCHRGS ," +
                        "CSSURCHG ," +
                        "OSSURCHG ," +
                        "OPFSA ," +
                        "CLFSA ," +
                        "CL2 ," +
                        "CL3 ," +
                        "CL4 ," +
                        "CL5 ," +
                        "CL6 ," +
                        "LRPNO ," +
                        "LPDATE ," +
                        "LPAMT ," +
                        "POINTS ," +
                        "CAPFLAG ," +
                        "CAPSURCH ," +
                        "SBSFLAG ," +
                        "OPDEMAN ," +
                        "CLDEMAN ," +
                        "CLRDNG ," +
                        "CLSTATUS ," +
                        "CSM_CONSUMER_NAME ," +
                        "CSM_ADDRESS3 ," +
                        "CSM_CONNECTED_LOAD ," +
                        "PREAVG," +
                        "NEWARREARS," +
                        "CSM_AADHAAR_NO," +
                        "CSM_CASTE," +
                        "CSM_PHONE_NO," +
                        "CSM_SHARE," +
                        "CSM_METER_NO," +
                        "MD," +
                        "ERO )VALUES (?,?,?,?,?," +
                        "?,?,?,?,?," +
                        "?,?,?,?,?,?," +
                        "?,?,?,?,?,?," +
                        "?,?,?,?,?,?," +
                        "?,?,?,?,?,?," +
                        "?,?,?,?,?,?," +
                        "?,?,?,?,?,?," +
                        "?,?,?,?,?,"+
                        "?,?,?,?,?,?,?)";

                insertStmt = sqLiteDatabase.compileStatement(sql);


                for (int i = 0; i < mServiceResponseInputJsondata.getDetails().size(); i++) {
                    if ((mServiceNumbers.contains(mServiceResponseInputJsondata.getDetails().get(i).getSCNO()))) {
//Globals.showToast(getActivity(),mServiceResponseInputJsondata.getDetails().get(i).getSCNO());
                    } else {
                        mServiceNumbers.add(mServiceResponseInputJsondata.getDetails().get(i).getSCNO());

                        insertStmt.clearBindings();


//                            if (mServiceResponseInputJsondata.getDetails().get(i).getI() != null) {
//                                insertStmt.bindString(1, mServiceResponseInputJsondata.getDetails().get(i).getId());
//                            } else {
//                                insertStmt.bindString(1, "");
//                            }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getAREACODE() != null) {
                            insertStmt.bindString(2, mServiceResponseInputJsondata.getDetails().get(i).getAREACODE());
                        } else {

                            insertStmt.bindString(2, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getMONTH() != null) {
                            insertStmt.bindString(3, mServiceResponseInputJsondata.getDetails().get(i).getMONTH());
                        } else {
                            insertStmt.bindString(3, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getSCNO() != null) {
                            Log.e("Error 1",""+mServiceResponseInputJsondata.getDetails().get(i).getSCNO());
                            insertStmt.bindString(4, mServiceResponseInputJsondata.getDetails().get(i).getSCNO());
                        } else {
                            insertStmt.bindString(4, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getCATEGORY() != null) {
                            insertStmt.bindString(5, mServiceResponseInputJsondata.getDetails().get(i).getCATEGORY());
                        } else {
                            insertStmt.bindString(5, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getSUBCATEGORY() != null) {
                            insertStmt.bindString(6, mServiceResponseInputJsondata.getDetails().get(i).getSUBCATEGORY());
                        } else {
                            insertStmt.bindString(6, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getSLABAMT() != null) {
                            insertStmt.bindString(7, mServiceResponseInputJsondata.getDetails().get(i).getSLABAMT());
                        } else {
                            insertStmt.bindString(7, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getGROUPID() != null) {
                            insertStmt.bindString(8, mServiceResponseInputJsondata.getDetails().get(i).getGROUPID());
                        } else {
                            insertStmt.bindString(8, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getOPEN_BAL() != null) {
                            insertStmt.bindString(9, mServiceResponseInputJsondata.getDetails().get(i).getOPEN_BAL());
                        } else {
                            insertStmt.bindString(9, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getCLBAL() != null) {
                            insertStmt.bindString(10, mServiceResponseInputJsondata.getDetails().get(i).getCLBAL());
                        } else {
                            insertStmt.bindString(10, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getDL() != null) {
                            insertStmt.bindString(11, mServiceResponseInputJsondata.getDetails().get(i).getDL());
                        } else {
                            insertStmt.bindString(11, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getADJAMT() != null) {
                            insertStmt.bindString(12, mServiceResponseInputJsondata.getDetails().get(i).getADJAMT());
                        } else {
                            insertStmt.bindString(12, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getPHASE() != null) {
                            insertStmt.bindString(13, mServiceResponseInputJsondata.getDetails().get(i).getPHASE());
                        } else {
                            insertStmt.bindString(13, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getOPMONTH() != null) {
                            insertStmt.bindString(14, mServiceResponseInputJsondata.getDetails().get(i).getOPMONTH());
                        } else {
                            insertStmt.bindString(14, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getOPSTATUS() != null) {
                            insertStmt.bindString(15, mServiceResponseInputJsondata.getDetails().get(i).getOPSTATUS());
                        } else {
                            insertStmt.bindString(15, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getOPRDNG() != null) {
                            insertStmt.bindString(16, mServiceResponseInputJsondata.getDetails().get(i).getOPRDNG());
                        } else {
                            insertStmt.bindString(16, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getSTATUS() != null) {
                            insertStmt.bindString(17, mServiceResponseInputJsondata.getDetails().get(i).getSTATUS());
                        } else {
                            insertStmt.bindString(17, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getCURRUNITS() != null) {
                            insertStmt.bindString(18, mServiceResponseInputJsondata.getDetails().get(i).getCURRUNITS());
                        } else {
                            insertStmt.bindString(18, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getDATEDIS() != null) {
                            insertStmt.bindString(19, mServiceResponseInputJsondata.getDetails().get(i).getDATEDIS());
                        } else {
                            insertStmt.bindString(19, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getMETERCYCLE() != null) {
                            insertStmt.bindString(20, mServiceResponseInputJsondata.getDetails().get(i).getMETERCYCLE());
                        } else {
                            insertStmt.bindString(20, "");
                        }
//                            if (mServiceResponseInputJsondata.getDetails().get(i).getId() != null) {
//                                insertStmt.bindString(21, "");
//                            } else {
//                                insertStmt.bindString(21, "");
//                            }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getCLEDUTY() != null) {
                            insertStmt.bindString(22, mServiceResponseInputJsondata.getDetails().get(i).getCLEDUTY());
                        } else {
                            insertStmt.bindString(22, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getCLEDINT() != null) {

                            insertStmt.bindString(23, mServiceResponseInputJsondata.getDetails().get(i).getCLEDINT());
                        } else {
                            insertStmt.bindString(23, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getOPEDINT() != null) {
                            insertStmt.bindString(24, mServiceResponseInputJsondata.getDetails().get(i).getOPEDINT());
                        } else {
                            insertStmt.bindString(24, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getCAPCHRGS() != null) {
                            insertStmt.bindString(25, mServiceResponseInputJsondata.getDetails().get(i).getCAPCHRGS());
                        } else {
                            insertStmt.bindString(25, "");
                        }
//                            if (mServiceResponseInputJsondata.getDetails().get(i).getId() != null) {
//                                insertStmt.bindString(26, "");
//                            } else {
//                                insertStmt.bindString(26, "");
//                            }
//                            if (mServiceResponseInputJsondata.getDetails().get(i).getId() != null) {
//                                insertStmt.bindString(27, "");
//                            } else {
//                                insertStmt.bindString(27, "");
//                            }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getOSSURCHG() != null) {
                            insertStmt.bindString(28, mServiceResponseInputJsondata.getDetails().get(i).getOSSURCHG());
                        } else {
                            insertStmt.bindString(28, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getOPFSA() != null) {
                            insertStmt.bindString(29, mServiceResponseInputJsondata.getDetails().get(i).getOPFSA());
                        } else {
                            insertStmt.bindString(29, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getCLFSA() != null) {
                            insertStmt.bindString(30, mServiceResponseInputJsondata.getDetails().get(i).getCLFSA());
                        } else {
                            insertStmt.bindString(30, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getCL2() != null) {
                            insertStmt.bindString(31, mServiceResponseInputJsondata.getDetails().get(i).getCL2());
                        } else {
                            insertStmt.bindString(31, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getCL3() != null) {
                            insertStmt.bindString(32, mServiceResponseInputJsondata.getDetails().get(i).getCL3());
                        } else {
                            insertStmt.bindString(32, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getCL5() != null) {
                            insertStmt.bindString(33, mServiceResponseInputJsondata.getDetails().get(i).getCL5());
                        } else {
                            insertStmt.bindString(33, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getCL5() != null) {
                            insertStmt.bindString(34, mServiceResponseInputJsondata.getDetails().get(i).getCL5());
                        } else {
                            insertStmt.bindString(34, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getCL6() != null) {
                            insertStmt.bindString(35, mServiceResponseInputJsondata.getDetails().get(i).getCL6());
                        } else {
                            insertStmt.bindString(35, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getLPRNO() != null) {
                            insertStmt.bindString(36, mServiceResponseInputJsondata.getDetails().get(i).getLPRNO());
                        } else {
                            insertStmt.bindString(36, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getLPDATE() != null) {
                            insertStmt.bindString(37, mServiceResponseInputJsondata.getDetails().get(i).getLPDATE());
                        } else {
                            insertStmt.bindString(37, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getLPAMT() != null) {
                            insertStmt.bindString(38, mServiceResponseInputJsondata.getDetails().get(i).getLPAMT());
                        } else {
                            insertStmt.bindString(38, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getPOINTS() != null) {

                            insertStmt.bindString(39, mServiceResponseInputJsondata.getDetails().get(i).getPOINTS());
                        } else {
                            insertStmt.bindString(39, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getCAPFLAG() != null) {
                            insertStmt.bindString(40, mServiceResponseInputJsondata.getDetails().get(i).getCAPFLAG());
                        } else {
                            insertStmt.bindString(40, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getCAPSURCHG() != null) {
                            insertStmt.bindString(41, mServiceResponseInputJsondata.getDetails().get(i).getCAPSURCHG());
                        } else {
                            insertStmt.bindString(41, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getSBSFLAG() != null) {
                            insertStmt.bindString(42, mServiceResponseInputJsondata.getDetails().get(i).getSBSFLAG());
                        } else {
                            insertStmt.bindString(42, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getOPDEMAND() != null) {
                            insertStmt.bindString(43, mServiceResponseInputJsondata.getDetails().get(i).getOPDEMAND());
                        } else {
                            insertStmt.bindString(43, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getCLDEMAND() != null) {
                            insertStmt.bindString(44, mServiceResponseInputJsondata.getDetails().get(i).getCLDEMAND());
                        } else {
                            insertStmt.bindString(44, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getCLRDNG() != null) {
                            insertStmt.bindString(45, mServiceResponseInputJsondata.getDetails().get(i).getCLRDNG());
                        } else {
                            insertStmt.bindString(45, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getCLSTATUS() != null) {
                            insertStmt.bindString(46, mServiceResponseInputJsondata.getDetails().get(i).getCLSTATUS());
                        } else {
                            insertStmt.bindString(46, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getCSM_CONSUMER_NAME() != null) {
                            insertStmt.bindString(47, mServiceResponseInputJsondata.getDetails().get(i).getCSM_CONSUMER_NAME());
                        } else {
                            insertStmt.bindString(47, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getCSM_ADDRESS3() != null) {
                            insertStmt.bindString(48, mServiceResponseInputJsondata.getDetails().get(i).getCSM_ADDRESS3());
                        } else {
                            insertStmt.bindString(48, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getCSM_CONNECTED_LOAD() != null) {
                            insertStmt.bindString(49, mServiceResponseInputJsondata.getDetails().get(i).getCSM_CONNECTED_LOAD());
                        } else {
                            insertStmt.bindString(49, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getPREAVG() != null) {
                            insertStmt.bindString(50, mServiceResponseInputJsondata.getDetails().get(i).getPREAVG());
                        } else {
                            insertStmt.bindString(50, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getNEWARREARS() != null) {
                            insertStmt.bindString(51, mServiceResponseInputJsondata.getDetails().get(i).getNEWARREARS());
                        } else {
                            insertStmt.bindString(51, "");
                        }

                        if (mServiceResponseInputJsondata.getDetails().get(i).getCSM_AADHAAR_NO() != null) {
                            insertStmt.bindString(52, mServiceResponseInputJsondata.getDetails().get(i).getCSM_AADHAAR_NO());
                        } else {
                            insertStmt.bindString(52, "");
                        }

                        if (mServiceResponseInputJsondata.getDetails().get(i).getCSM_CASTE() != null) {
                            insertStmt.bindString(53, mServiceResponseInputJsondata.getDetails().get(i).getCSM_CASTE());
                        } else {
                            insertStmt.bindString(53, "");
                        }

                        if (mServiceResponseInputJsondata.getDetails().get(i).getCSM_PHONE_NO() != null) {
                            insertStmt.bindString(54, mServiceResponseInputJsondata.getDetails().get(i).getCSM_PHONE_NO());
                        } else {
                            insertStmt.bindString(54, "");
                        }

                        if (mServiceResponseInputJsondata.getDetails().get(i).getCSM_SHARE() != null) {
                            insertStmt.bindString(55, mServiceResponseInputJsondata.getDetails().get(i).getCSM_SHARE());
                        } else {
                            insertStmt.bindString(55, "");
                        }

                        if (mServiceResponseInputJsondata.getDetails().get(i).getCSM_METER_NO() != null) {
                            insertStmt.bindString(56, mServiceResponseInputJsondata.getDetails().get(i).getCSM_METER_NO());
                        } else {
                            insertStmt.bindString(56, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getMD() != null) {
                            insertStmt.bindString(57, mServiceResponseInputJsondata.getDetails().get(i).getMD());
                        } else {
                            insertStmt.bindString(57, "");
                        }
                        if (mServiceResponseInputJsondata.getDetails().get(i).getERO() != null) {
                            insertStmt.bindString(58, mServiceResponseInputJsondata.getDetails().get(i).getERO());
                        } else {
                            insertStmt.bindString(58, "");
                        }
                        insertStmt.execute();
                    }
                }
                //  onPostExecute();
                //  ll_loading.setVisibility(View.GONE);

//                        for (Item it : items) {
//                            publishProgress(it);
//                        }

            } catch (Exception e) {
                Log.e("Error",""+e.toString());
                e.printStackTrace();
            } finally {
                // ll_loading.setVisibility(View.GONE);
                sqLiteDatabase.close();

            }


            return (null);
        }

        //      protected void onProgressUpdate(Item... item) {
        //adapter.add(item[0]);
//            TextView tv;
//            if(tv==null)
//            TextView tv=getActivity().findViewById(R.id.tv_status);
//            tv.setText("");
        //      }

        protected void onPostExecute(Void unused) {
            //dismiss the alert here where the thread has finished his work
            ll_loading.setVisibility(View.GONE);
        }
    }


//    class InsertIntoDB extends AsyncTask<Void, String, Void> {
//
//        protected void onPreExecute() {
//            pDialog = ProgressDialog.show(MyActivity.this,"Please wait...", "Retrieving data ...", true);
//        }
//
//        protected Void doInBackground(Void... unused) {
//            items = parser.getItems();
//
//            for (Item it : items) {
//                publishProgress(it);
//            }
//            return(null);
//        }
//
//        protected void onProgressUpdate(Void... item) {
//            //adapter.add(item[0]);
//        }
//
//        protected void onPostExecute(Void unused) {
//           // pDialog.dismiss();
//        }
//    }


    private void parseInputServiceJsonData(final String data) {
        final SQLiteDatabase sqLiteDatabase;
        final String DATABASE_NAME = "CustomerServices";
        sqLiteDatabase = openOrCreateDatabase(Utils.getSharedInstance().databasePath + "RecsBillingData", null);


        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM CustomerServices", null);

        mServiceNumbers.clear();
        if (c.moveToFirst()) {
            do {
                Log.d("dorami", "onCreate: " + DatabaseUtils.dumpCursorToString(c));
                mServiceNumbers.add(c.getString(c.getColumnIndex("SERVICENO")));
            } while (c.moveToNext());
        }
        c.close();

        ll_loading.setVisibility(View.VISIBLE);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {


                    Gson gson = new Gson();
                    mServiceResponseInputJsondata = gson.fromJson(data, ServiceResponseInputModel.class);

                    // pagination 101<100
                    //page_id : number of records to be fetched in service call and start index.
                    if (Integer.parseInt(mServiceResponseInputJsondata.getTotal_records()) < (page_id - 20)) {
                        isRecoresAviable = false;
                    } else {
                        page_id = page_id + 20;
                        request = SharedPreferenceUtil.getAreaCode(getActivity()) + "&start=" + page_id + "";
                    }

                    String sql;
                    SQLiteStatement insertStmt;
                    //sql = "INSERT INTO CustomerServices(id, BILLNO, MACHINEID ,
                    // Service_No , CSM_CONSUMER_CODE , Name , Ero_Code , Area_Code ,
                    // Address1 , Address2 , Address3 ,Category  , Sub_Category ,
                    // OLD_RDG_KWH , OLD_RDG_KVAH , OLDDT , PRESRDG_KVAH  , PRESRDG_KWH ,
                    // PRESRDG_LT , PRESRDG_KVA , PRESDT , PRESSTS , PRESSTS_LT , INITRDG_KWH ,
                    // FINALRDG_KWH , INITRDG_KVAH , FINALRDG_KVAH , units , ltunits , ENGCHG ,
                    // FIXEDCHG , CUSCHG , EDCHG , LPF_CAP_CHG , BILLAMT , ADJAMT , btotal1 ,
                    // AVGUNITS ,OLDARREARS , NEWARREARS , ASURCHG , AEDINT , PAMOUNT , PEDCHG , ACD , FSA , others1 , others2 , duedate , disdate , subsidy , pf , lorg , md , kvah , EROCODE , catg , billmonth , pay_status , phone, load, phase) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                    sql = "INSERT INTO CustomerServices(" +
                            "id," +
                            "AREACODE," +
                            "month," +
                            "SERVICENO ," +
                            "Category ," +
                            "Subcategory ," +
                            "Slbamoth ," +
                            "groupid ," +
                            "open_bal ," +
                            "close_bal ," +
                            "dj ," +
                            "adj_amount ," +
                            "phese ," +
                            "opmonth ," +
                            "opstatus ," +
                            "OPRDNG ," +
                            "STATUS ," +
                            "UNITS ," +
                            "DATEDIS ," +
                            "METERCY ," +
                            "EDINT ," +
                            "CLEDUTY ," +
                            "CELEDINT ," +
                            "OPEDINT ," +
                            "CAPCHRGS ," +
                            "SURCHRGS ," +
                            "CSSURCHG ," +
                            "OSSURCHG ," +
                            "OPFSA ," +
                            "CLFSA ," +
                            "CL2 ," +
                            "CL3 ," +
                            "CL4 ," +
                            "CL5 ," +
                            "CL6 ," +
                            "LRPNO ," +
                            "LPDATE ," +
                            "LPAMT ," +
                            "POINTS ," +
                            "CAPFLAG ," +
                            "CAPSURCH ," +
                            "SBSFLAG ," +
                            "OPDEMAN ," +
                            "CLDEMAN ," +
                            "CLRDNG ," +
                            "CLSTATUS ," +
                            "CSM_CONSUMER_NAME ," +
                            "CSM_ADDRESS3 ," +
                            "CSM_CONNECTED_LOAD ," +
                            "PREAVG," +
                            "CSM_METER_NO )VALUES (?,?,?,?,?," +
                            "?,?,?,?,?," +
                            "?,?,?,?,?,?," +
                            "?,?,?,?,?,?," +
                            "?,?,?,?,?,?," +
                            "?,?,?,?,?,?," +
                            "?,?,?,?,?,?," +
                            "?,?,?,?,?,?," +
                            "?,?,?,?,?)";

                    insertStmt = sqLiteDatabase.compileStatement(sql);


                    for (int i = 0; i < mServiceResponseInputJsondata.getDetails().size(); i++) {
                        if ((mServiceNumbers.contains(mServiceResponseInputJsondata.getDetails().get(i).getSCNO()))) {
//Globals.showToast(getActivity(),mServiceResponseInputJsondata.getDetails().get(i).getSCNO());
                        } else {
                            mServiceNumbers.add(mServiceResponseInputJsondata.getDetails().get(i).getSCNO());

                            insertStmt.clearBindings();


//                            if (mServiceResponseInputJsondata.getDetails().get(i).getI() != null) {
//                                insertStmt.bindString(1, mServiceResponseInputJsondata.getDetails().get(i).getId());
//                            } else {
//                                insertStmt.bindString(1, "");
//                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getAREACODE() != null) {
                                insertStmt.bindString(2, mServiceResponseInputJsondata.getDetails().get(i).getAREACODE());
                            } else {

                                insertStmt.bindString(2, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getMONTH() != null) {
                                insertStmt.bindString(3, mServiceResponseInputJsondata.getDetails().get(i).getMONTH());
                            } else {
                                insertStmt.bindString(3, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getSCNO() != null) {
                                insertStmt.bindString(4, mServiceResponseInputJsondata.getDetails().get(i).getSCNO());
                            } else {
                                insertStmt.bindString(4, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getCATEGORY() != null) {
                                insertStmt.bindString(5, mServiceResponseInputJsondata.getDetails().get(i).getCATEGORY());
                            } else {
                                insertStmt.bindString(5, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getSUBCATEGORY() != null) {
                                insertStmt.bindString(6, mServiceResponseInputJsondata.getDetails().get(i).getSUBCATEGORY());
                            } else {
                                insertStmt.bindString(6, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getSLABAMT() != null) {
                                insertStmt.bindString(7, mServiceResponseInputJsondata.getDetails().get(i).getSLABAMT());
                            } else {
                                insertStmt.bindString(7, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getGROUPID() != null) {
                                insertStmt.bindString(8, mServiceResponseInputJsondata.getDetails().get(i).getGROUPID());
                            } else {
                                insertStmt.bindString(8, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getOPEN_BAL() != null) {
                                insertStmt.bindString(9, mServiceResponseInputJsondata.getDetails().get(i).getOPEN_BAL());
                            } else {
                                insertStmt.bindString(9, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getCLBAL() != null) {
                                insertStmt.bindString(10, mServiceResponseInputJsondata.getDetails().get(i).getCLBAL());
                            } else {
                                insertStmt.bindString(10, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getDL() != null) {
                                insertStmt.bindString(11, mServiceResponseInputJsondata.getDetails().get(i).getDL());
                            } else {
                                insertStmt.bindString(11, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getADJAMT() != null) {
                                insertStmt.bindString(12, mServiceResponseInputJsondata.getDetails().get(i).getADJAMT());
                            } else {
                                insertStmt.bindString(12, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getPHASE() != null) {
                                insertStmt.bindString(13, mServiceResponseInputJsondata.getDetails().get(i).getPHASE());
                            } else {
                                insertStmt.bindString(13, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getOPMONTH() != null) {
                                insertStmt.bindString(14, mServiceResponseInputJsondata.getDetails().get(i).getOPMONTH());
                            } else {
                                insertStmt.bindString(14, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getOPSTATUS() != null) {
                                insertStmt.bindString(15, mServiceResponseInputJsondata.getDetails().get(i).getOPSTATUS());
                            } else {
                                insertStmt.bindString(15, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getOPRDNG() != null) {
                                insertStmt.bindString(16, mServiceResponseInputJsondata.getDetails().get(i).getOPRDNG());
                            } else {
                                insertStmt.bindString(16, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getSTATUS() != null) {
                                insertStmt.bindString(17, mServiceResponseInputJsondata.getDetails().get(i).getSTATUS());
                            } else {
                                insertStmt.bindString(17, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getCURRUNITS() != null) {
                                insertStmt.bindString(18, mServiceResponseInputJsondata.getDetails().get(i).getCURRUNITS());
                            } else {
                                insertStmt.bindString(18, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getDATEDIS() != null) {
                                insertStmt.bindString(19, mServiceResponseInputJsondata.getDetails().get(i).getDATEDIS());
                            } else {
                                insertStmt.bindString(19, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getMETERCYCLE() != null) {
                                insertStmt.bindString(20, mServiceResponseInputJsondata.getDetails().get(i).getMETERCYCLE());
                            } else {
                                insertStmt.bindString(20, "");
                            }
//                            if (mServiceResponseInputJsondata.getDetails().get(i).getId() != null) {
//                                insertStmt.bindString(21, "");
//                            } else {
//                                insertStmt.bindString(21, "");
//                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getCLEDUTY() != null) {
                                insertStmt.bindString(22, mServiceResponseInputJsondata.getDetails().get(i).getCLEDUTY());
                            } else {
                                insertStmt.bindString(22, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getCLEDINT() != null) {

                                insertStmt.bindString(23, mServiceResponseInputJsondata.getDetails().get(i).getCLEDINT());
                            } else {
                                insertStmt.bindString(23, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getOPEDINT() != null) {
                                insertStmt.bindString(24, mServiceResponseInputJsondata.getDetails().get(i).getOPEDINT());
                            } else {
                                insertStmt.bindString(24, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getCAPCHRGS() != null) {
                                insertStmt.bindString(25, mServiceResponseInputJsondata.getDetails().get(i).getCAPCHRGS());
                            } else {
                                insertStmt.bindString(25, "");
                            }
//                            if (mServiceResponseInputJsondata.getDetails().get(i).getId() != null) {
//                                insertStmt.bindString(26, "");
//                            } else {
//                                insertStmt.bindString(26, "");
//                            }
//                            if (mServiceResponseInputJsondata.getDetails().get(i).getId() != null) {
//                                insertStmt.bindString(27, "");
//                            } else {
//                                insertStmt.bindString(27, "");
//                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getOSSURCHG() != null) {
                                insertStmt.bindString(28, mServiceResponseInputJsondata.getDetails().get(i).getOSSURCHG());
                            } else {
                                insertStmt.bindString(28, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getOPFSA() != null) {
                                insertStmt.bindString(29, mServiceResponseInputJsondata.getDetails().get(i).getOPFSA());
                            } else {
                                insertStmt.bindString(29, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getCLFSA() != null) {
                                insertStmt.bindString(30, mServiceResponseInputJsondata.getDetails().get(i).getCLFSA());
                            } else {
                                insertStmt.bindString(30, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getCL2() != null) {
                                insertStmt.bindString(31, mServiceResponseInputJsondata.getDetails().get(i).getCL2());
                            } else {
                                insertStmt.bindString(31, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getCL3() != null) {
                                insertStmt.bindString(32, mServiceResponseInputJsondata.getDetails().get(i).getCL3());
                            } else {
                                insertStmt.bindString(32, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getCL5() != null) {
                                insertStmt.bindString(33, mServiceResponseInputJsondata.getDetails().get(i).getCL5());
                            } else {
                                insertStmt.bindString(33, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getCL5() != null) {
                                insertStmt.bindString(34, mServiceResponseInputJsondata.getDetails().get(i).getCL5());
                            } else {
                                insertStmt.bindString(34, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getCL6() != null) {
                                insertStmt.bindString(35, mServiceResponseInputJsondata.getDetails().get(i).getCL6());
                            } else {
                                insertStmt.bindString(35, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getLPRNO() != null) {
                                insertStmt.bindString(36, mServiceResponseInputJsondata.getDetails().get(i).getLPRNO());
                            } else {
                                insertStmt.bindString(36, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getLPDATE() != null) {
                                insertStmt.bindString(37, mServiceResponseInputJsondata.getDetails().get(i).getLPDATE());
                            } else {
                                insertStmt.bindString(37, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getLPAMT() != null) {
                                insertStmt.bindString(38, mServiceResponseInputJsondata.getDetails().get(i).getLPAMT());
                            } else {
                                insertStmt.bindString(38, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getPOINTS() != null) {

                                insertStmt.bindString(39, mServiceResponseInputJsondata.getDetails().get(i).getPOINTS());
                            } else {
                                insertStmt.bindString(39, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getCAPFLAG() != null) {
                                insertStmt.bindString(40, mServiceResponseInputJsondata.getDetails().get(i).getCAPFLAG());
                            } else {
                                insertStmt.bindString(40, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getCAPSURCHG() != null) {
                                insertStmt.bindString(41, mServiceResponseInputJsondata.getDetails().get(i).getCAPSURCHG());
                            } else {
                                insertStmt.bindString(41, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getSBSFLAG() != null) {
                                insertStmt.bindString(42, mServiceResponseInputJsondata.getDetails().get(i).getSBSFLAG());
                            } else {
                                insertStmt.bindString(42, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getOPDEMAND() != null) {
                                insertStmt.bindString(43, mServiceResponseInputJsondata.getDetails().get(i).getOPDEMAND());
                            } else {
                                insertStmt.bindString(43, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getCLDEMAND() != null) {
                                insertStmt.bindString(44, mServiceResponseInputJsondata.getDetails().get(i).getCLDEMAND());
                            } else {
                                insertStmt.bindString(44, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getCLRDNG() != null) {
                                insertStmt.bindString(45, mServiceResponseInputJsondata.getDetails().get(i).getCLRDNG());
                            } else {
                                insertStmt.bindString(45, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getCLSTATUS() != null) {
                                insertStmt.bindString(46, mServiceResponseInputJsondata.getDetails().get(i).getCLSTATUS());
                            } else {
                                insertStmt.bindString(46, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getCSM_CONSUMER_NAME() != null) {
                                insertStmt.bindString(47, mServiceResponseInputJsondata.getDetails().get(i).getCSM_CONSUMER_NAME());
                            } else {
                                insertStmt.bindString(47, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getCSM_ADDRESS3() != null) {
                                insertStmt.bindString(48, mServiceResponseInputJsondata.getDetails().get(i).getCSM_ADDRESS3());
                            } else {
                                insertStmt.bindString(48, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getCSM_CONNECTED_LOAD() != null) {
                                insertStmt.bindString(49, mServiceResponseInputJsondata.getDetails().get(i).getCSM_CONNECTED_LOAD());
                            } else {
                                insertStmt.bindString(49, "");
                            }
                            if (mServiceResponseInputJsondata.getDetails().get(i).getPREAVG() != null) {
                                insertStmt.bindString(50, mServiceResponseInputJsondata.getDetails().get(i).getPREAVG());
                            } else {
                                insertStmt.bindString(50, "");
                            }

                            if (mServiceResponseInputJsondata.getDetails().get(i).getCSM_METER_NO() != null) {
                                insertStmt.bindString(51, mServiceResponseInputJsondata.getDetails().get(i).getCSM_METER_NO());
                            } else {
                                insertStmt.bindString(51, "");
                            }
                            insertStmt.execute();
                        }
                    }

                    ll_loading.setVisibility(View.GONE);


                } catch (Exception e) {
                    ll_loading.setVisibility(View.GONE);
                    e.printStackTrace();
                } finally {
                    ll_loading.setVisibility(View.GONE);
                    sqLiteDatabase.close();

                }

            }
        });
    }

    public void updateTextViews() {
        getActivity().runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                billDetailsLyt.setVisibility(View.VISIBLE);
                meterNoTv.setText(billdetailModel.getCSM_METER_NO());
                serviceNoTv.setText(billdetailModel.getSCNO());

                if(billdetailModel.getCSM_AADHAAR_NO().length() >= 12){
                    aadhar.setText("XXXXXXXXX"+billdetailModel.getCSM_AADHAAR_NO().substring(9));
                    aadhar.setEnabled(false);
                }else {
                    aadhar.setEnabled(true);
                }

                phone.setText(billdetailModel.getCSM_PHONE_NO());

                if(billdetailModel.getCSM_PHONE_NO().length()==10){
                    phone.setEnabled(false);
                }else {
                    phone.setEnabled(true);
                }


                nameTv.setText(billdetailModel.getCSM_CONSUMER_NAME());
                addressTv.setText(billdetailModel.getCSM_ADDRESS3() + " .");
                catgoryTv.setText(billdetailModel.getCategory());
                subCategoryTv.setText("0" + billdetailModel.getSubcategory());
                phaseTv.setText(billdetailModel.getPHASE());
                lastMonthConsumptionTv.setText(billdetailModel.getUNITS());
                tv_dial_value.setText(DAIL_VALUE + "");
                tv_arrers.setText(billdetailModel.getClose_bal());
//                tv_arrers.setText(billdetailModel.getCLDEMAN());
                shareCapital.setText(billdetailModel.getCSM_SHARE());
                caste.setText(billdetailModel.getCSM_CASTE());


               /* if(! phaseTv.getText ().toString ().equals ( "" )){
                    ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.burnt_meter_values1, android.R.layout.simple_spinner_item);
                    staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    burntMeterSpinner.setAdapter(staticAdapter);
                    burntMeterSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {
                            Log.v("item", (String) parent.getItemAtPosition(position));
                            burntMeterValue = (String) parent.getItemAtPosition(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub
                        }
                    });
                }
                else*/ if(phaseTv.getText ().toString ().equals ( "1" ) || phaseTv.getText ().toString ().equals ( "3" ) ){
                    ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.burnt_meter_values2, android.R.layout.simple_spinner_item);
                    staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    burntMeterSpinner.setAdapter(staticAdapter);
                    burntMeterSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {
                            Log.v("item", (String) parent.getItemAtPosition(position));
                            burntMeterValue = (String) parent.getItemAtPosition(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub
                        }
                    });
                }
                else
                {
                    ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.burnt_meter_values1, android.R.layout.simple_spinner_item);
                    staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    burntMeterSpinner.setAdapter(staticAdapter);
                    burntMeterSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {
                            Log.v("item", (String) parent.getItemAtPosition(position));
                            burntMeterValue = (String) parent.getItemAtPosition(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub
                        }
                    });
                }

            }
        });
    }

    private class updateBillDataForNextPrint extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... param) {

            final SQLiteDatabase sqLiteDatabase;
            final String DATABASE_NAME = "CustomerServices";
            sqLiteDatabase = openOrCreateDatabase(Utils.getSharedInstance().databasePath + "RecsBillingData", null);

            String sql;
            SQLiteStatement insertStmt;
            //sql = "INSERT INTO CustomerServices(id, BILLNO, MACHINEID ,
            // Service_No , CSM_CONSUMER_CODE , Name , Ero_Code , Area_Code ,
            // Address1 , Address2 , Address3 ,Category  , Sub_Category ,
            // OLD_RDG_KWH , OLD_RDG_KVAH , OLDDT , PRESRDG_KVAH  , PRESRDG_KWH ,
            // PRESRDG_LT , PRESRDG_KVA , PRESDT , PRESSTS , PRESSTS_LT , INITRDG_KWH ,
            // FINALRDG_KWH , INITRDG_KVAH , FINALRDG_KVAH , units , ltunits , ENGCHG ,
            // FIXEDCHG , CUSCHG , EDCHG , LPF_CAP_CHG , BILLAMT , ADJAMT , btotal1 ,
            // AVGUNITS ,OLDARREARS , NEWARREARS , ASURCHG , AEDINT , PAMOUNT , PEDCHG , ACD , FSA , others1 , others2 , duedate , disdate , subsidy , pf , lorg , md , kvah , EROCODE , catg , billmonth , pay_status , phone, load, phase) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            sql = "UPDATE CustomerServices SET " +
//                    "id = "+billdetailModel.getId()+", " +
//                    "AREACODE," +
//                    "month = "+billdetailModel.getMonth()+", " +
//                    "SERVICENO ," +
//                    "Category ," +
//                    "Subcategory ," +
//                    "Slbamoth ," +
//                    "groupid ," +
//                    "open_bal ," +
//                    "close_bal ," +
//                    "dj ," +
//                    "adj_amount ," +
//                    "phese ," +
//                    "opmonth ," +
//                    "opstatus ," +
                    "OPRDNG = "+billdetailModel.getOPRDNG()+", " +
                    "CLRDNG = "+billdetailModel.getCLRDNG()+", " +
                    "UNITS ="+billdetailModel.getUNITS()+", " +
                    "CSSURCHG ="+billdetailModel.getCUSCHG()+", " +
                    "PREAVG = "+billdetailModel.getLASTMCONSUPTION()+ " "+
//                    "STATUS ," +
//                    "UNITS ," +
//                    "DATEDIS ," +
//                    "METERCY ," +
//                    "EDINT ," +
//                    "CLEDUTY ," +
//                    "CELEDINT ," +
//                    "OPEDINT ," +
//                    "CAPCHRGS ="+billdetailModel.getCAPCHRGS()+", " +
//                    "SURCHRGS ="+billdetailModel.getSURCHRGS()+", " +
//                      cussss
//                    "OSSURCHG ="+billdetailModel.getOSSURCHG()+" "+
                    "WHERE SERVICENO='" + param[0] + "'";
//                    "OPFSA ," +
//                    "CLFSA ," +
//                    "CL2 ," +
//                    "CL3 ," +
//                    "CL4 ," +
//                    "CL5 ," +
//                    "CL6 ," +
//                    "LRPNO ," +
//                    "LPDATE ," +
//                    "LPAMT ," +
//                    "POINTS ," +
//                    "CAPFLAG ," +
//                    "CAPSURCH ," +
//                    "SBSFLAG ," +
//                    "OPDEMAN ," +
//                    "CLDEMAN ," +
//                    "CLRDNG ," +
//                    "CLSTATUS ," +
//                    "CSM_CONSUMER_NAME ," +
//                    "CSM_ADDRESS3 ," +
//                    "CSM_CONNECTED_LOAD ," +
//                    "PREAVG," +
//                    "CSM_METER_NO )

//            sqLiteDatabase.compileStatement(sql);
            sqLiteDatabase.execSQL(sql);
            Log.e("log sql", ""+sql);
            sqLiteDatabase.close();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}