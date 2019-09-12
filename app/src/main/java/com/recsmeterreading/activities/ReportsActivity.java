package com.recsmeterreading.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.recsmeterreading.R;

public class ReportsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        findViewById(R.id.totalServicesLyt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),TotalServiceNoActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.totalServicesBilledLyt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),BilledDetailsActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.totalServicesUnbilledLyt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),UnBilledDetailsActivity.class);
                startActivity(i);
            }
        });
    }
}
