package com.recsmeterreading.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.recsmeterreading.R;
import com.recsmeterreading.Utils.Utils;
import com.recsmeterreading.adapters.TotalServiceListAdapter;

import java.util.Calendar;

public class BilledDetailsActivity extends AppCompatActivity {
    ImageView FromImage,ToImage;
    TextView FromDisplay,ToDisplay;
    DatePickerDialog StartTime;
    Calendar current_date;
    int cur_date;
    int check=0;
    ListView totalCategoryWiseLv;
    TotalServiceListAdapter adapter;
    ProgressDialog progressDialog;
    SQLiteDatabase sqLiteDatabases;

    public BilledDetailsActivity() {
        StartTime = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billed_details);
        sqLiteDatabases=openOrCreateDatabase(Utils.getSharedInstance().databasePath+Utils.getSharedInstance().databaseName, MODE_PRIVATE,null);

        FromImage               =   findViewById(R.id.from_image);
        ToImage                 =   findViewById(R.id.to_image);
        FromDisplay             =   findViewById(R.id.from_display);
        ToDisplay               =   findViewById(R.id.to_display);
        totalCategoryWiseLv     =   findViewById(R.id.listView);
        progressDialog          =   new ProgressDialog(this);

        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        adapter = new TotalServiceListAdapter(this);

        totalCategoryWiseLv.setAdapter(adapter);
        current_date = Calendar.getInstance();
        int current_year=current_date.get(Calendar.YEAR);

        current_date.set(Calendar.DAY_OF_MONTH,1);
        current_date.set(Calendar.YEAR,current_year);
        FromImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check=1;
                Datepicker(FromDisplay);

                StartTime.getDatePicker().setMinDate(current_date.getTimeInMillis());

                StartTime.getDatePicker().setMaxDate(System.currentTimeMillis());
                StartTime.show();
            }

        });
        ToImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check==1) {
                    Datepicker(ToDisplay);
                    StartTime.getDatePicker().setMinDate(current_date.getTimeInMillis());

                    StartTime.getDatePicker().setMaxDate(System.currentTimeMillis());


                    StartTime.show();
                    check=0;
                }else
                {
                    Toast.makeText(BilledDetailsActivity.this, "To date should be greater than from date.", Toast.LENGTH_SHORT).show();
                }
            }

        });


//        Cursor c = sqLiteDatabases.rawQuery("SELECT count(Service_No) FROM CustomerServices GROUP BY (catg)", null);
//
//        if (c.moveToFirst()){
//            do{
//                Log.d("dorami", "onCreate: "+ DatabaseUtils.dumpCursorToString(c));
//                // do what ever you want here
//            }while(c.moveToNext());
//        }
//        c.close();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        },1000);

    }

    //function for datePicker Dialog
    public void Datepicker( final TextView display)
    {
        boolean check=true;
        StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                int displaydate=0;

                if (display == FromDisplay) {
                    newDate.set(year, monthOfYear, dayOfMonth);
                    cur_date=newDate.get(Calendar.DATE);
                    Log.d("Datahack", "onDateSet: "+cur_date);
                    showDate(year, monthOfYear + 1, dayOfMonth, display);
                }
                else {
                    newDate.set(year, monthOfYear, dayOfMonth);
                    if (dayOfMonth >=cur_date) {
                        showDate(year, monthOfYear + 1, dayOfMonth, display);
                        Log.d("dorami", "onDateSet: " + cur_date);
                    } else {
                        Toast.makeText(BilledDetailsActivity.this, "message", Toast.LENGTH_SHORT).show();
                    }
                }
                Log.d("checkkar", "current selected date : "+cur_date);
            }

        }, current_date.get(Calendar.YEAR), current_date.get(Calendar.MONTH), current_date.get(Calendar.DAY_OF_MONTH));
    }

    private void showDate(int year, int month, int day,TextView id) {
        id.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }
}