package com.recsmeterreading.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.recsmeterreading.Database.SqliteDb;
import com.recsmeterreading.R;
import com.recsmeterreading.Utils.Globals;
import com.recsmeterreading.Utils.SharedPreferenceUtil;
import com.recsmeterreading.Utils.Utils;
import com.recsmeterreading.connections.Connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/*
 * Created by Manikanta Sarma
 */


public class SigninActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText pwdET;
    private AutoCompleteTextView emailET;
    Button loginBtn;
    private boolean permissionDenied = false; // creating a variable for checking the permissions from 6.0(OS)
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        initView();

        initSqlDb();
    }

    private void initSqlDb() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(SigninActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            //creating the folders
            Utils.getSharedInstance().createFoldersAndDb();
            try {
                //creating database
                SqliteDb sqliteDb = new SqliteDb();
                sqliteDb.createDatabase();
            } catch (Exception exception) {
                Log.e("Sqlite ", "exception " + exception);
            }
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //creating the folders
            Utils.getSharedInstance().createFoldersAndDb();
            try {
                //creating database
                SqliteDb sqliteDb = new SqliteDb();
                sqliteDb.createDatabase();
            } catch (Exception exception) {
                Log.e("Sqlite ", "exception " + exception);
            }
        }
    }

    private void initView() {
        emailET = findViewById(R.id.emailET);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, areaCode);
        emailET.setThreshold(1);
        emailET.setAdapter(adapter);
        pwdET = findViewById(R.id.pwdET);
        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (emailET.getText().toString().equals("")) {
                    Globals.showToast(getApplicationContext(), "Enter Area Code");
                } else if (emailET.getText().toString().length() < 5) {
                    Globals.showToast(getApplicationContext(), "Invalid Area Code");
                }else if(!Arrays.asList(areaCode).contains(emailET.getText().toString())){
                    Globals.showToast(getApplicationContext(), "Area Code does not exist");
                } else {

                    String s=emailET.getText().toString();

                    if(!s.equals(SharedPreferenceUtil.getAreaCode(getApplicationContext()))){
                        SqliteDb sqliteDb = new SqliteDb();
                        sqliteDb.deleteTable();
                    }

                    SharedPreferenceUtil.setAreaCode(getApplicationContext(),s );
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);


                }

            }
            });



    }


    @Override
    protected void onResume() {
        super.onResume();
        // checking storage permission.
        checkStoragePermission();

/*
        // Clearing list variables used in Attendance Screens in background
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Utilities.getSharedInstance().selectedEmployeesAttendance.clear();
                return null;
            }
        }.execute();*/

    }

    // checking storage permission
    private void checkStoragePermission() {
        /*requesting for external storage permission */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !permissionDenied &&
                ActivityCompat.checkSelfPermission(SigninActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SigninActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        // if user checked never ask again permission and displaying dialog.
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
                ActivityCompat.checkSelfPermission(SigninActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            // Showing Dialog that navigates to Settings.
            showMessageOKCancel(
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Intent to phone settings to enable storage permissions for app
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    });
        } else {
            // creating project folders for db.
            Utils.getSharedInstance().createFoldersAndDb();
/*
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    // Api call checking server update date and local syncing date and sync data.
                    if (Utilities.getSharedInstance().isConnectedToInternet(HomeScreenActivity.this)) {
                        // Api call checking server update date and local syncing date and sync data.

                        if (!Utilities.getSharedInstance().fromLogin) {
                            apiCallForSyncUpdate();
                        } else {
                            if (Utilities.circularSpinnerDialog != null) {
                                if (!Utilities.circularSpinnerDialog.isShowing()) {
                                    Utilities.getSharedInstance().showProgressDialog(currentActivity, "Fetching Data...");
                                }
                            } else {
                                Utilities.getSharedInstance().showProgressDialog(currentActivity, "Fetching Data...");
                            }
                            Utilities.getSharedInstance().fromLogin = false;
                            getEmployeesListData();
                        }
                        sync_count.setText(String.valueOf(count()));
                    } else {
                        if (Utilities.getSharedInstance().homeScreenDataNotFetched) {
                            Utilities.getSharedInstance().homeScreenDataNotFetched = false;
                            fetchingDataFromDB();

                        } else {
                            Utilities.getSharedInstance().dismissProgressDialog();
                        }
                        sync_count.setText(String.valueOf(count()));
                    }
                }

            }, 0);
*/
        }

    }

    // for handling the request response for android 6.0 for storage.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requestCode == 1 && shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //TO check whether user granted permission or not

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {

                    permissionDenied = false;
                    ActivityCompat.requestPermissions(SigninActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //create db
                    Utils.getSharedInstance().createFoldersAndDb();
                }
            } else {
                permissionDenied = true;
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //create db
                    Utils.getSharedInstance().createFoldersAndDb();
                }
            }
        }
    }

    // displaying alert dialog if permission is not granted.
    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(SigninActivity.this);
        // dialog to check if it is already showing.
        AlertDialog permissionAlertDialog;
        builder.setTitle("Storage Permission!");
        builder.setMessage("Allow RECS to access storage permission to continue further...");
        builder.setPositiveButton("Open Settings", okListener);
        builder.setCancelable(false);
        permissionAlertDialog = builder.create();
        if (!permissionAlertDialog.isShowing()) {
            WindowManager.LayoutParams wmlp = permissionAlertDialog.getWindow().getAttributes();

            // setting the alert to the bottom.
            wmlp.gravity = Gravity.BOTTOM;

            permissionAlertDialog.show();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                if (emailET.getText().length() == 0) {
                    Toast.makeText(this, "Username can't be empty", Toast.LENGTH_SHORT).show();
                } else if (pwdET.getText().length() == 0) {
                    Toast.makeText(this, "Password can't be empty", Toast.LENGTH_SHORT).show();
                } else {
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            try {
                                Object rdata = Connection.callHttpPostRequestsV2Jobj(SigninActivity.this, "http://192.168.0.96/restfulapi/curl_login.php", login());
                                parseData(rdata.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                    }.execute();
                }
                break;
        }
    }


    private String login() {
        String uname, password;

        uname = emailET.getText().toString();
        password = pwdET.getText().toString();


        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("name", uname);
            requestObject.put("password", password);
            System.out.println(requestObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObject.toString();
    }


    private void parseData(Object data) {

        Gson gson = new Gson();

/*        mResponsedata = gson.fromJson(data.toString(), GetBillDetailsModelForPrint[].class);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                serviceNoTv.setText(mResponsedata[0].getService_No());
                nameTv.setText(mResponsedata[0].getName());
                addressTv.setText(mResponsedata[0].getAddress1()+","+mResponsedata[0].getAddress2()+","+mResponsedata[0].getAddress3()+".");
                billDateTv.setText(mResponsedata[0].getPRESDT());
                catgoryTv.setText(mResponsedata[0].getCategory());
                subCategoryTv.setText(mResponsedata[0].getSub_Category());
                previousStartReadTv.setText(mResponsedata[0].getOLDRDG_KWH());
                previousEndReadTv.setText(mResponsedata[0].getPRESRDG_KWH());
                unitsTv.setText(mResponsedata[0].getUnits());
                amountTv.setText(mResponsedata[0].getBILLAMT());

                if(mResponsedata[0].getPay_status().equals("0")){
                    statusTv.setText("Paid");
                    statusTv.setTextColor(Color.GREEN);
                }else {
                    statusTv.setText("Not Paid");
                    statusTv.setTextColor(Color.RED);
                }
            }
        });*/
    }

}
