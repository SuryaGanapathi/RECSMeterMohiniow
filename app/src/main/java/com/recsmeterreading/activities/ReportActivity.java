package com.recsmeterreading.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.recsmeterreading.R;
import com.recsmeterreading.Utils.DialogAction;
import com.recsmeterreading.Utils.Globals;
import com.recsmeterreading.api.ApiUtils;
import com.recsmeterreading.api.MeterApi;
import com.recsmeterreading.model.ReportDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView totalServices, billedServices, unbilledServices,totalAmount, totalUnits;
    private String tS,bS,uS, tA,tU = null;
    private ImageView totalServicesImage, billedServicesImage, unbilledServicesImage,totalAmountImage,
            totalUnitsImage, unbilledReportsImage;
    private static ReportDetails reportDetails;
    private static boolean isReportCalled = false;
    DialogAction dialogAction;
    private static String areaCode = null;
    private static String category = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Intent intent = getIntent();

        String areaCo = intent.getStringExtra("areaCode");
        String catego = intent.getStringExtra("category");
        Log.e("area code",""+areaCo);
        if(areaCo != null){
            areaCode = areaCo;
            category = catego;
        }


//        isReportCalled = true;



        Toolbar myToolbar = findViewById(R.id.report_info_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionbar = getSupportActionBar();
        if (getSupportActionBar() != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setTitle(" Reports ");

        }
        init();





    }

    private void updateUi(ReportDetails reportDetails) {
        tS = reportDetails.getServices();
        totalServices.setText(tS);
        Log.e("ts",""+tS);

        bS = reportDetails.getBilled_services();
        billedServices.setText(bS);
        Log.e("ts",""+bS);

        uS = reportDetails.getUnbilled();
        unbilledServices.setText(uS);
        Log.e("ts",""+uS);

        tA = reportDetails.getAmount();
        totalAmount.setText(tA);
        Log.e("ts",""+tA);

        tU = reportDetails.getUnits();
        totalUnits.setText(tU);
        Log.e("ts",""+tU);
    }

    private void init() {
        dialogAction = new DialogAction(this);
        totalServices = findViewById(R.id.total_services_value);
        billedServices= findViewById(R.id.billed_services_value);
        unbilledServices = findViewById(R.id.unbilled_services_value);
        totalAmount = findViewById(R.id.total_amount_value);
        totalUnits = findViewById(R.id.total_units_value);

        totalServicesImage = findViewById(R.id.total_services_go);
        totalServicesImage.setOnClickListener(this);

        billedServicesImage = findViewById(R.id.billed_services_go);
        billedServicesImage.setOnClickListener(this);

        unbilledServicesImage = findViewById(R.id.unbilled_services_go);
        unbilledServicesImage.setOnClickListener(this);

        totalAmountImage = findViewById(R.id.total_amount_go);
        totalAmountImage.setOnClickListener(this);

        totalUnitsImage = findViewById(R.id.total_units_go);
        totalUnitsImage.setOnClickListener(this);

        unbilledReportsImage = findViewById(R.id.unbilled_reports_go);
        unbilledReportsImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, DetailReportActivity.class);
        switch (view.getId()){
            case R.id.total_services_go:

                intent.putExtra("typeService","TotalServices");
                intent.putExtra("areaCode",areaCode);
                intent.putExtra("category",category);
                if(tS != null){
                    startActivity(intent);
                }else {
                    Toast.makeText(this, "No Record Found for "+areaCode+" and "+category, Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.billed_services_go:

                intent.putExtra("typeService","BilledServices");
                intent.putExtra("areaCode",areaCode);
                intent.putExtra("category",category);
                if(bS != null){
                    startActivity(intent);
                }else {
                    Toast.makeText(this, "No Record Found for "+areaCode+" and "+category, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.unbilled_services_go:
                intent.putExtra("typeService","UnbilledServices");
                intent.putExtra("areaCode",areaCode);
                intent.putExtra("category",category);
                if(uS != null){
                    startActivity(intent);
                }else {
                    Toast.makeText(this, "No Record Found for "+areaCode+" and "+category, Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.total_amount_go:
                intent.putExtra("typeService","TotalAmount");
                intent.putExtra("areaCode",areaCode);
                intent.putExtra("category",category);
                intent.putExtra("value",tA);
                if(tA != null){
                    startActivity(intent);
                }else {
                    Toast.makeText(this, "No Record Found for "+areaCode+" and "+category, Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.total_units_go:
                intent.putExtra("typeService","TotalUnits");
                intent.putExtra("areaCode",areaCode);
                intent.putExtra("category",category);
                intent.putExtra("value",tU);
                if(tU != null){

                    startActivity(intent);
                }else {
                    Toast.makeText(this, "No Record Found for "+areaCode+" and "+category, Toast.LENGTH_SHORT).show();
                }


                break;

            case R.id.unbilled_reports_go:
//                Intent intent1 = new Intent(this,UnbilledReportsActivity.class);
//                intent.putExtra("areaCode",areaCode);
//                intent.putExtra("category",category);
//                startActivity(intent1);
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
//        if(!isReportCalled){
            MeterApi meterApi = ApiUtils.getMeterApi();
            dialogAction.showDialog("Reports","Retrieving Reports");
            meterApi.getResponse("Reports",areaCode,category).enqueue(new Callback<ReportDetails>() {
                @Override
                public void onResponse(Call<ReportDetails> call, Response<ReportDetails> response) {
                    if(response.isSuccessful()){

                        reportDetails = response.body();
                        if (reportDetails != null){
                            Log.e("retrofit ",""+response.body()+reportDetails.getBilled_services()+reportDetails.getServices());
                            updateUi(reportDetails);
                        }

                        isReportCalled = true;
                    }

                    dialogAction.hideDialog();
                }

                @Override
                public void onFailure(Call<ReportDetails> call, Throwable t) {
                    Log.e("retrofit Error",""+t.toString());
                    Globals.showToast(ReportActivity.this,"Something went wrong");
                    dialogAction.hideDialog();
                }
            });
//        }else {
//            updateUi(reportDetails);
//        }

    }
}
