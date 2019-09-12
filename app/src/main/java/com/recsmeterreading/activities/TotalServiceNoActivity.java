package com.recsmeterreading.activities;

import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.recsmeterreading.R;
import com.recsmeterreading.Utils.Utils;
import com.recsmeterreading.adapters.TotalServiceListAdapter;

public class TotalServiceNoActivity extends AppCompatActivity {

    ListView totalCategoryWiseLv;
    TotalServiceListAdapter adapter;
    ProgressDialog progressDialog;
    SQLiteDatabase sqLiteDatabases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_service_no);
        sqLiteDatabases=openOrCreateDatabase(Utils.getSharedInstance().databasePath+Utils.getSharedInstance().databaseName, MODE_PRIVATE,null);
        totalCategoryWiseLv = findViewById(R.id.listView);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        adapter = new TotalServiceListAdapter(this);

        totalCategoryWiseLv.setAdapter(adapter);

//       Cursor c = sqLiteDatabases.rawQuery("SELECT count(Service_No),catg FROM CustomerServices GROUP BY (catg)", null);
//
//        if (c.moveToFirst()){
//            do{
//                Log.d("dorami", "onCreate: "+ DatabaseUtils.dumpCursorToString(c));
//                 Log.d("dorami", "onCreate: "+ c.getColumnIndex("count(Service_No)"));
//                Log.d("dorami", "onCreate: "+ c.getColumnIndex("catg"));
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
}
