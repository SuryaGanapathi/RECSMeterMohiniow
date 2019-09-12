package com.recsmeterreading.activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.recsmeterreading.R;
import com.recsmeterreading.fragments.BillDetailsFragment;

/*
 * Created by Manikanta Sarma
 */

public class GetBillDetailsActivity extends AppCompatActivity {
    Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar myToolbar = findViewById(R.id.bill_details_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionbar = getSupportActionBar();
        if (getSupportActionBar() != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setTitle("బిల్లును పొందుట కొరకు");

        }

//        // Get a support ActionBar corresponding to this toolbar
//        ActionBar ab = getSupportActionBar();
//
//        // Enable the Up button
//        ab.setDisplayHomeAsUpEnabled(true);

        fragment = new BillDetailsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();


    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
