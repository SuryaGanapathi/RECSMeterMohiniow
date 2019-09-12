package com.recsmeterreading.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.recsmeterreading.R;

public class UnBilledReportD extends AppCompatActivity implements View.OnClickListener{

    private AutoCompleteTextView autoT;
    private Button search,print,download;
    private RecyclerView unBilledRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_billed_report_d);

        init();
    }

    private void init() {
        autoT = findViewById(R.id.autoCompleteTextViewU);


    }

    @Override
    public void onClick(View view) {

    }
}
