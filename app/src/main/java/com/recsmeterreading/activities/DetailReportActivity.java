package com.recsmeterreading.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.recsmeterreading.R;
import com.recsmeterreading.Utils.DialogAction;
import com.recsmeterreading.Utils.Globals;
import com.recsmeterreading.api.ApiUtils;
import com.recsmeterreading.api.MeterApi;
import com.recsmeterreading.bluetoothPrinter.BluetoothPrinterMain;
import com.recsmeterreading.model.CategoryDetails;
import com.recsmeterreading.model.CategoryValue;
import com.recsmeterreading.model.ReportDetails;
import com.recsmeterreading.model.ServiceDetails;
import com.recsmeterreading.model.ServiceNo;
import com.recsmeterreading.model.ServiceNumber;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailReportActivity extends AppCompatActivity implements View.OnClickListener {


    private Button download, print;
    String title;

    DialogAction dialogAction;
    CategoryDetails categoryDetails;
    ServiceDetails serviceDetails;
    List<ServiceNo> serviceNoList = new ArrayList<>();

    String areaCode = null;
    String category = null;
    private TextView cat, noOfService;

    String valueAmountUnit = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_report);

        dialogAction = new DialogAction(this);

        Intent intent = getIntent();
        title = intent.getStringExtra("typeService");


        areaCode = intent.getStringExtra("areaCode");
        category = intent.getStringExtra("category");



        Toolbar myToolbar = findViewById(R.id.details_report_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionbar = getSupportActionBar();
        if (getSupportActionBar() != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setTitle(title);

        }

        init();
        if(title.equals("TotalAmount") || title.equals("TotalUnits")){
            String value = intent.getStringExtra("value");
            valueAmountUnit = value;
            if(value != null){
                if(title.equals("TotalAmount")){
                    UpdateUi("Total Amount: ".concat(value));
                }else {
                    UpdateUi("Total Unit: ".concat(value));
                }
            }else {
                Toast.makeText(this, "Record Not Found", Toast.LENGTH_SHORT).show();
            }

        }else {
            MeterApi meterApi = ApiUtils.getMeterApi();
            dialogAction.showDialog("Reports","Retrieving Reports");
            meterApi.getTotalService(title,areaCode,category).enqueue(new Callback<ServiceDetails>() {
                @Override
                public void onResponse(Call<ServiceDetails> call, Response<ServiceDetails> response) {
                    dialogAction.hideDialog();
                    if(response.isSuccessful()){
                        serviceDetails = response.body();
                        if(serviceDetails != null){
                            if(serviceDetails.getService_numbers() != null){
                                serviceNoList = serviceDetails.getService_numbers();
                                Log.e("ss",""+serviceDetails.getCATEGORY()+serviceDetails.getService_numbers().get(0).getSCNO());
                                if(serviceDetails.getValue() != null){
                                    UpdateUi("No Of Services: ".concat(serviceDetails.getValue()));
                                }else {
                                    UpdateUi("No Of Services: No Record Found");
                                }

                            }else {
                                UpdateUi("No Of Services: No Record Found");
                            }

                        }

                    }

                }

                @Override
                public void onFailure(Call<ServiceDetails> call, Throwable t) {
                    dialogAction.hideDialog();
                }
            });
        }

//        meterApi.getTotalService(title,areaCode,category).enqueue(new Callback<CategoryDetails>() {
//            @Override
//            public void onResponse(Call<CategoryDetails> call, Response<CategoryDetails> response) {
//
//
//
//                categoryDetails = response.body();
////                Log.e("retrofit",""+categoryDetails.getTotal_records());
//                if (categoryDetails != null) {
//                    updateUi(categoryDetails.getCategoryValues());
//                }
//                dialogAction.hideDialog();
//            }
//
//            @Override
//            public void onFailure(Call<CategoryDetails> call, Throwable t) {
//                Log.e("retrofit Error",""+t.toString());
//                dialogAction.hideDialog();
//                Globals.showToast(DetailReportActivity.this,"Something Went Wrong");
//            }
//        });

    }

    private void UpdateUi(String value) {

        cat.setText("Category: ".concat(category));
        if(value != null){
            noOfService.setText(value);
        }else {

        }


    }



    private void init() {

        cat = findViewById(R.id.cat_no);
        cat.setText("Category: ".concat(category));

        noOfService = findViewById(R.id.no_of_services);

        download = findViewById(R.id.download_report);
        download.setOnClickListener(this);

        print = findViewById(R.id.print_report);
        print.setOnClickListener(this);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.download_report:
//                new PdfGenerationTask().execute();

//                serviceNoList.add(new ServiceNo("10101 01212"));
//                serviceNoList.add(new ServiceNo("10101 01215"));
//                serviceNoList.add(new ServiceNo("10101 01219"));
                if(title.equals("TotalAmount") || title.equals("TotalUnits")){
                    createandDisplayPdf(title,serviceNoList);
                }else {
                    if(serviceNoList.size()>0)
                        createandDisplayPdf(title,serviceNoList);
                    else
                        Toast.makeText(this, "No Record Found", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.print_report:
                if(title.equals("TotalAmount") || title.equals("TotalUnits")){
                    Intent intent = new Intent(this, BluetoothPrinterMain.class);

                    Bundle args = new Bundle();
                    args.putSerializable("ARRAYLIST",(Serializable)serviceNoList);
                    intent.putExtra("areaCode",areaCode);
                    intent.putExtra("category",category);
                    intent.putExtra("typeService",title);
                    if(valueAmountUnit != null){
                        intent.putExtra("value",valueAmountUnit);
                        intent.putExtra("report",5);
                        startActivityForResult(intent,2);

                    }else
                        Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();

                }else{
                    if(serviceNoList.size()>0){
                        Intent intent = new Intent(this, BluetoothPrinterMain.class);

                        Bundle args = new Bundle();
                        args.putSerializable("ARRAYLIST",(Serializable)serviceNoList);
                        intent.putExtra("areaCode",areaCode);
                        intent.putExtra("category",category);
                        intent.putExtra("typeService",title);
                        if(title.equals("TotalAmount") || title.equals("TotalUnits")){
                            intent.putExtra("value",valueAmountUnit);
                        }
                        intent.putExtra("report",5);
                        startActivityForResult(intent,2);

                    }else
                        Toast.makeText(this, "No Record Found", Toast.LENGTH_SHORT).show();
                }



//                    createandDisplayPdf(title,serviceNoList);

                break;

        }
    }

    private void createandDisplayPdf(String unbilledServises, List<ServiceNo> services) {
        Document doc = new Document();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
        String pdfName = unbilledServises
                + sdf.format(Calendar.getInstance().getTime()) + ".pdf";

        try {
//            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dir";
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/Meter";

            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();



            File file = new File(dir, pdfName);
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();
            if(unbilledServises.equals("TotalAmount") || unbilledServises.equals("TotalUnits")) {
//                for(int i=0; i<services.size(); i++){
                if (valueAmountUnit != null) {
                    Paragraph p1 = new Paragraph("Category " + " : " + category + " & Value: " + valueAmountUnit);
                    Font paraFont = new Font(Font.FontFamily.COURIER);
                    p1.setAlignment(Paragraph.ALIGN_LEFT);
                    p1.setFont(paraFont);

                    //add paragraph to document
                    doc.add(p1);
                }
//                }
            }else {
                for(int i=0; i<services.size(); i++){
                    Paragraph p1 = new Paragraph((i+1)+". Service Number "+" : "+services.get(i).getSCNO());
                    Font paraFont= new Font(Font.FontFamily.COURIER);
                    p1.setAlignment(Paragraph.ALIGN_LEFT);
                    p1.setFont(paraFont);

                    //add paragraph to document
                    doc.add(p1);
                }
            }




            Toast.makeText(this,""+path,Toast.LENGTH_LONG).show();

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally {
            doc.close();
        }

        viewPdf(pdfName, "Meter");
    }

    // Method for opening a pdf file
    private void viewPdf(String file, String directory) {

        File pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + directory + "/" + file);
        Uri path = Uri.fromFile(pdfFile);

        // Setting the intent for pdf reader
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Can't read pdf file", Toast.LENGTH_SHORT).show();
        }
    }

    // Method for creating a pdf file from text, saving it then opening it for display
//    public void createandDisplayPdf(String title, List<CategoryValue> categoryValues) {
//
//        Document doc = new Document();
//        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
//        String pdfName = title
//                + sdf.format(Calendar.getInstance().getTime()) + ".pdf";
//
//        try {
////            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dir";
//            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/Meter";
//
//            File dir = new File(path);
//            if(!dir.exists())
//                dir.mkdirs();
//
//
//
//            File file = new File(dir, pdfName);
//            FileOutputStream fOut = new FileOutputStream(file);
//
//            PdfWriter.getInstance(doc, fOut);
//
//            //open the document
//            doc.open();
//
//            for(int i=0; i<categoryValues.size(); i++){
//                Paragraph p1 = new Paragraph("Category "+(i+1)+" : "+categoryValues.get(i).getValue());
//                Font paraFont= new Font(Font.FontFamily.COURIER);
//                p1.setAlignment(Paragraph.ALIGN_LEFT);
//                p1.setFont(paraFont);
//
//                //add paragraph to document
//                doc.add(p1);
//            }
//
//
//            Toast.makeText(this,""+path,Toast.LENGTH_LONG).show();
//
//        } catch (DocumentException de) {
//            Log.e("PDFCreator", "DocumentException:" + de);
//        } catch (IOException e) {
//            Log.e("PDFCreator", "ioException:" + e);
//        }
//        finally {
//            doc.close();
//        }
//
//        viewPdf(pdfName, "Meter");
//    }
//
//    // Method for opening a pdf file
//    private void viewPdf(String file, String directory) {
//
//        File pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + directory + "/" + file);
//        Uri path = Uri.fromFile(pdfFile);
//
//        // Setting the intent for pdf reader
//        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
//        pdfIntent.setDataAndType(path, "application/pdf");
//        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        try {
//            startActivity(pdfIntent);
//        } catch (ActivityNotFoundException e) {
//            Toast.makeText(this, "Can't read pdf file", Toast.LENGTH_SHORT).show();
//        }
//    }


}
