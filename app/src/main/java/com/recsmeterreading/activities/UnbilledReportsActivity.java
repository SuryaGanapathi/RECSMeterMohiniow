package com.recsmeterreading.activities;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.recsmeterreading.R;
import com.recsmeterreading.Utils.DialogAction;
import com.recsmeterreading.Utils.Globals;
import com.recsmeterreading.Utils.SharedPreferenceUtil;
import com.recsmeterreading.adapters.UnbilledReportAdapter;
import com.recsmeterreading.api.ApiUtils;
import com.recsmeterreading.api.MeterApi;
import com.recsmeterreading.bluetoothPrinter.BluetoothPrinterMain;
import com.recsmeterreading.model.CategoryDetails;
import com.recsmeterreading.model.ServiceNo;
import com.recsmeterreading.model.ServiceNumber;
import com.recsmeterreading.model.UnbilledServices;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnbilledReportsActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "UnbilledReportsActivity";

    private List<ServiceNumber> unBilledCat = new ArrayList<>();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private Button search,print,download;
    private RecyclerView unBilledRV;

    private AutoCompleteTextView areaCodeEditText;
    private DialogAction dialogAction;
    private UnbilledServices unbilledServices;
    private RecyclerView unRv;
    static String[] areaCode ={"10115",
            "10116",
            "10105",
            "20201",
            "20234",
            "20229",
            "20230",
            "20218",
            "20226",
            "20204",
            "20206",
            "20235",
            "20219",
            "20208",
            "20202",
            "20211",
            "20216",
            "80896",
            "20210",
            "20203",
            "20222",
            "20212",
            "20209",
            "20228",
            "20213",
            "20225",
            "20214",
            "20207",
            "20217",
            "20231",
            "20237",
            "20205",
            "20241",
            "20233",
            "20215",
            "20221",
            "20227",
            "20232",
            "20223",
            "20224",
            "20220",
            "40056",
            "30341",
            "40057",
            "40052",
            "30348",
            "30362",
            "30301",
            "30319",
            "30322",
            "30352",
            "40064",
            "40053",
            "30363",
            "30327",
            "30338",
            "30385",
            "30367",
            "40061",
            "30353",
            "30381",
            "30358",
            "30323",
            "40058",
            "40034",
            "40060",
            "30368",
            "30307",
            "30335",
            "30365",
            "30364",
            "30332",
            "30359",
            "30302",
            "30334",
            "30377",
            "30303",
            "30318",
            "30308",
            "30328",
            "30379",
            "30357",
            "40054",
            "40059",
            "40033",
            "30342",
            "30329",
            "40032",
            "40062",
            "40055",
            "30326",
            "30361",
            "40007",
            "40030",
            "40012",
            "40005",
            "40016",
            "40011",
            "40020",
            "40013",
            "40065",
            "40001",
            "40022",
            "40018",
            "40019",
            "40029",
            "40003",
            "40024",
            "40031",
            "40026",
            "40009",
            "40027",
            "40028",
            "40017",
            "50508",
            "50516",
            "50510",
            "50509",
            "50517",
            "50540",
            "50524",
            "50527",
            "50526",
            "50532",
            "50502",
            "50538",
            "50504",
            "50503",
            "50536",
            "50513",
            "50515",
            "50525",
            "50534",
            "50521",
            "50505",
            "50533",
            "50520",
            "50522",
            "50539",
            "50530",
            "50518",
            "50529",
            "50512",
            "50528",
            "50519",
            "50514",
            "50541",
            "50506",
            "50507",
            "50535",
            "50531",
            "50501",
            "50511",
            "50523",
            "50537",
            "60622",
            "60649",
            "60634",
            "60617",
            "60635",
            "60637",
            "60612",
            "60613",
            "60633",
            "60626",
            "60625",
            "60631",
            "60618",
            "60620",
            "60623",
            "60632",
            "60621",
            "60615",
            "60641",
            "60624",
            "60640",
            "60627",
            "60628",
            "60630",
            "60647",
            "60629",
            "60648",
            "60639",
            "60619",
            "60636",
            "70706",
            "70786",
            "70711",
            "70754",
            "70701",
            "70709",
            "70747",
            "70760",
            "70772",
            "70774",
            "70707",
            "70771",
            "70704",
            "70705",
            "70703",
            "70773",
            "70702",
            "80828",
            "80821",
            "80823",
            "80861",
            "80817",
            "80841",
            "80834",
            "80807",
            "80888",
            "80854",
            "80856",
            "80831",
            "80855",
            "80802",
            "80836",
            "80891",
            "80852",
            "80897",
            "80837",
            "80840",
            "80860",
            "80827",
            "80815",
            "80829",
            "80898",
            "80824",
            "80839",
            "80835",
            "80820",
            "80846",
            "80862",
            "80869",
            "80808",
            "80826",
            "80853",
            "80830",
            "80818",
            "80822",
            "80850",
            "80895",
            "80849",
            "80812",
            "80870",
            "80857",
            "80833",
            "80859",
            "80803",
            "80819",
            "80847",
            "80804",
            "80845",
            "80843",
            "80814",
            "80811",
            "80851",
            "80844",
            "80801",
            "80825",
            "80813",
            "80842",
            "80809",
            "80810",
            "80805",
            "80816",
            "80838",
            "80848",
            "80858",
            "80899",
            "80806",
            "80832",
            "80872",
            "90973",
            "90201",
            "90838",
            "90111",
            "90533",
            "90336",
            "90350",
            "90241",
            "90630",
            "90848",
            "90356",
            "90504",
            "90031",
            "90636",
            "90057",
            "90001",
            "90018",
            "90840",
            "90811",
            "90380",
            "90532",
            "90215",
            "90003",
            "90230",
            "90706",
            "90707",
            "90747",
            "90754",
            "90703",
            "90318",
            "90218",
            "90621",
            "90228",
            "90527",
            "90827",
            "90846",
            "90506",
            "90332",
            "90116",
            "90016",
            "90202",
            "90059",
            "90103",
            "90511",
            "90849",
            "90011",
            "90860",
            "90205",
            "90760",
            "90304",
            "90812",
            "90850",
            "90634",
            "90101",
            "90612",
            "90020",
            "90786",
            "90216",
            "90340",
            "90053",
            "90346",
            "90009",
            "90823",
            "90841",
            "90106",
            "90105",
            "90212",
            "90702",
            "90112",
            "90343",
            "90813",
            "90821",
            "90013",
            "90503",
            "90520",
            "90019",
            "90114",
            "90530",
            "90505",
            "90618",
            "90641",
            "90826",
            "90056",
            "90359",
            "90012",
            "90312",
            "90774",
            "90632",
            "90844",
            "90805",
            "90032",
            "90711",
            "90903",
            "90364",
            "90802",
            "90524",
            "90302",
            "90353",
            "90635",
            "90629",
            "90365",
            "90317",
            "90771",
            "90709",
            "90117",
            "90344",
            "90329",
            "90816",
            "90515",
            "90204",
            "90501",
            "90526",
            "90235",
            "90342",
            "90303",
            "90110",
            "90007",
            "90541",
            "90537",
            "90804",
            "90620",
            "90348",
            "90624",
            "90622",
            "90301",
            "90005",
            "90815",
            "90525",
            "90352",
            "90528",
            "90102",
            "90208",
            "90869",
            "90845",
            "90203",
            "90701",
            "90034",
            "90355",
            "90327",
            "90362",
            "90508",
            "90209",
            "90895",
            "90321",
            "90801",
            "90623",
            "90322",
            "90837",
            "90851",
            "90613",
            "90058",
            "90631",
            "90507",
            "90839",
            "90637",
            "90107",
            "90349",
            "90773",
            "90055",
            "90062",
            "90619",
            "90843",
            "10107",
            "10111",
            "10110",
            "10108",
            "10106",
            "10114",
            "10117",
            "10113",
            "30343",
            "30383",
            "30378",
            "30350",
            "30355",
            "30317",
            "30305",
            "30337",
            "30324",
            "30351",
            "30321",
            "30304",
            "30333",
            "30336",
            "30384",
            "30339",
            "30315",
            "30344",
            "30380",
            "30311",
            "30320",
            "30306",
            "30376",
            "30346",
            "30349",
            "30314",
            "30331",
            "30309",
            "30340",
            "30345",
            "30356",
            "30312",
            "30313",
            "90309",
            "10104",
            "10102",
            "10101",
            "10112",
            "10103"
    };

    private List<ServiceNumber> servicesList = new ArrayList<>();
    UnbilledReportAdapter adapterN;

    private SharedPreferenceUtil sharedPreferenceUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_billed_report_d);

        dialogAction = new DialogAction(this);
        unRv = findViewById(R.id.recyclerViewU);
        sharedPreferenceUtil = new SharedPreferenceUtil();



        Toolbar myToolbar = findViewById(R.id.unbilled_report_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionbar = getSupportActionBar();
        if (getSupportActionBar() != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setTitle(" Unbilled Reports ");

        }

        areaCodeEditText = findViewById(R.id.autoCompleteTextViewU);
        // Initilise View

        String areaC = SharedPreferenceUtil.getAreaCode(this);
        areaCodeEditText.setText(areaC);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, areaCode);
        areaCodeEditText.setThreshold(1);
        areaCodeEditText.setAdapter(adapter);

        search = findViewById(R.id.searchU);
        search.setOnClickListener(this);
        print = findViewById(R.id.printU);
        print.setOnClickListener(this);
        download = findViewById(R.id.downloadU);
        download.setOnClickListener(this);
        unBilledRV = findViewById(R.id.recyclerViewU);



    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.searchU:
                if(!areaCodeEditText.getText().toString().trim().isEmpty()){
                    getUnBilledReport(areaCodeEditText.getText().toString().trim());
//                    unBilledCat.add(new ServiceNumber("101",3525));
                }else {
                    Globals.showToast(this,"Area Code Not Valid");
                }


                break;
            case R.id.downloadU:
                if(unBilledCat.size()>0)
                    createandDisplayPdf("UnBilledCategory",unBilledCat);
                else
                    Toast.makeText(this, "No Record Found", Toast.LENGTH_SHORT).show();
                break;

            case R.id.printU:

                if(unBilledCat.size()>0){
                    Intent intent = new Intent(this, BluetoothPrinterMain.class);

                    Bundle args = new Bundle();
                    args.putSerializable("ARRAYLIST",(Serializable)unBilledCat);
                    intent.putExtra("data",args);
//                    intent.putExtra("areaCode",areaCode);
//                    intent.putExtra("category",category);
//                    intent.putExtra("typeService",title);
//                    intent.putExtras(args,"data");
                    intent.putExtra("report",11);
                    startActivityForResult(intent,9);

                }


//                    createandDisplayPdf(title,serviceNoList);
                else
                    Toast.makeText(this, "No Record Found", Toast.LENGTH_SHORT).show();

                break;
        }

    }

    private void getUnBilledReport(String areaCode) {

        MeterApi meterApi = ApiUtils.getMeterApi();
        dialogAction.showDialog("Reports","Retrieving Reports");
        meterApi.getUnbilledServices("UnbilledReports",areaCode).enqueue(new Callback<UnbilledServices>() {
            @Override
            public void onResponse(Call<UnbilledServices> call, Response<UnbilledServices> response) {
                if(response.isSuccessful()){
                    unbilledServices = response.body();
                    Log.e("retrofit qq2",unbilledServices.getStatus()+" : "+new Gson().toJson(unbilledServices));
                    unBilledCat = unbilledServices.getServices();
                    setUnbilledReportRv(unbilledServices.getServices());

                    //                Log.e("retrofit",""+unbilledServices.getTotal_records()+unbilledServices.getServices().get(0).getServiceNo());
//                    if (unbilledServices != null && unbilledServices.getServices()!= null)
//                        createandDisplayPdf("UnbilledServises", unbilledServices.getServices());

                }
                dialogAction.hideDialog();

            }

            @Override
            public void onFailure(Call<UnbilledServices> call, Throwable t) {
                Log.e("retrofit",""+t.toString());
                
                dialogAction.hideDialog();
            }
        });

    }

    private void setUnbilledReportRv(List<ServiceNumber> services) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(UnbilledReportsActivity.this);
        unRv.setLayoutManager(layoutManager);
        unRv.setHasFixedSize(true);
        adapterN = new UnbilledReportAdapter(UnbilledReportsActivity.this,services);
        unRv.setAdapter(adapterN);
//        adapterN = new UnbilledReportAdapter(this,services);
//        unRv.setAdapter(adapterN);
//        adapterN.notifyDataSetChanged();
//        adapter.notify();

    }

    private void createandDisplayPdf(String unbilledServises, List<ServiceNumber> services) {
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

            for(int i=0; i<services.size(); i++){
                Paragraph p1 = new Paragraph((i+1)+". Category: "+" : "+services.get(i).getCategory()+" & Count: "+ services.get(i).getValue());
                Font paraFont= new Font(Font.FontFamily.COURIER);
                p1.setAlignment(Paragraph.ALIGN_LEFT);
                p1.setFont(paraFont);

                //add paragraph to document
                doc.add(p1);
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
//    private void createandDisplayPdf(String unbilledServises, List<ServiceNumber> services) {
//
//        Document doc = new Document();
//        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
//        String pdfName = unbilledServises
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
//            for(int i=0; i<services.size(); i++){
//                Paragraph p1 = new Paragraph((i+1)+". Category "+" : "+services.get(i).getValue());
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
//        if(isReadStoragePermissionGranted()){
//            File pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + directory + "/" + file);
////            Uri path = Uri.fromFile(pdfFile);
//            Uri path;
//            path = Uri.fromFile(pdfFile);
//
//            Log.e("file",pdfFile.toString());
////        if (Build.VERSION.SDK_INT >= 24) {
////            path = FileProvider.getUriForFile(getApplicationContext(), "com.recsmeterreading.activities", pdfFile);
////        } else {
////            path = Uri.fromFile(pdfFile);
////        }
//
//            // Setting the intent for pdf reader
//            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
////            pdfIntent.setDataAndTypeAndNormalize(path,"application/pdf");
//            pdfIntent.setDataAndType(path, "application/pdf");
//
//            pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//
//            try {
////                startActivity(Intent.createChooser(pdfIntent, "Your title"));
//            startActivity(pdfIntent);
//            } catch (ActivityNotFoundException e) {
//                Toast.makeText(this, "Can't read pdf file", Toast.LENGTH_SHORT).show();
//                Log.e(TAG,""+e.toString());
//            }
//        }else {
//            Log.e("file","not Permmited");
//        }
//
//
//    }

    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted1");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked1");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted1");
            return true;
        }
    }

    public  boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted2");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted2");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                Log.d(TAG, "External storage2");
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                    //resume tasks needing this permission
//                    downloadPdfFile();
                }else{
//                    progress.dismiss();
                }
                break;

            case 3:
                Log.d(TAG, "External storage1");
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                    //resume tasks needing this permission
//                    SharePdfFile();
                }else{
//                    progress.dismiss();
                }
                break;
        }
    }
}
