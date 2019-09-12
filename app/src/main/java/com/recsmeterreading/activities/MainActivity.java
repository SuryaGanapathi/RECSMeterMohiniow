package com.recsmeterreading.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.recsmeterreading.Utils.Converter;
import com.recsmeterreading.Utils.Globals;
import com.recsmeterreading.Utils.PrintLog;
import com.recsmeterreading.Utils.SharedPreferenceUtil;
import com.recsmeterreading.Utils.StringConstants;
import com.recsmeterreading.Utils.Utils;

import com.recsmeterreading.R;
import com.recsmeterreading.Utils.Utils;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

/*
 * Created by Manikanta Sarma
 */


public class MainActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private Button generateBillBtn, pendingBillBtn, reportsBillBtn, duplicateBill, unbilledReport;
    private TextView countTv;
    Button print;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        print = findViewById(R.id.print);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionbar = getSupportActionBar();
        if (getSupportActionBar() != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setTitle("హోమ్");

        }

        initView();

print.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent it = new Intent(MainActivity.this,SunmiPrint.class);
        startActivity(it);
    }
});
    }



    private void initView() {
        generateBillBtn = findViewById(R.id.generateBillBtn);
        pendingBillBtn = findViewById(R.id.pendingBillBtn);
        reportsBillBtn = findViewById(R.id.reportsBillBtn);
        duplicateBill = findViewById(R.id.duplicateBill);
        countTv = findViewById(R.id.countTv);
        unbilledReport = findViewById(R.id.unbilled_report_main);

        unbilledReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this,UnbilledReportsActivity.class);
//                intent.putExtra("areaCode",areaCode);
//                intent.putExtra("category",category);
                startActivity(intent1);
            }
        });


        generateBillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GetBillDetailsActivity.class);
                startActivity(i);

            }
        });

        pendingBillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isTableExist()){
                    Intent i = new Intent(getApplicationContext(), PendingBillActivity.class);
                    startActivity(i);
                }else {
                    Toast.makeText(MainActivity.this, "Generate Bill First then Print", Toast.LENGTH_SHORT).show();
                }

            }
        });


        reportsBillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MasterReportActivity.class);
                startActivity(i);
            }
        });

        duplicateBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DuplicateBill.class);
                startActivity(i);
            }
        });
    }

    private boolean isTableExist() {
//        Utils.getSharedInstance().createFoldersAndDb();
        try {
            final SQLiteDatabase sqLiteDatabase;

            sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(Utils.getSharedInstance().databasePath + "RecsBillingData", null);

            String sqlQuery = "SELECT * FROM CustomerServices ";
            Cursor c = sqLiteDatabase.rawQuery(sqlQuery, null);
            int count = c.getCount();
            Log.e("Request ; ",""+count);
            return c.getCount() != 0;
        }catch (SQLException e){
            return false;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Utils.getSharedInstance().getBillDetailsModel.size() != 0) {
            countTv.setVisibility(View.VISIBLE);
            countTv.setText(Utils.getSharedInstance().getBillDetailsModel.size() + "");
        } else {
            countTv.setVisibility(View.GONE);
            countTv.setText("");
        }
    }
}
