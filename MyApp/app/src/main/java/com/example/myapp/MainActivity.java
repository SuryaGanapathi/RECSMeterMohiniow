package com.example.myapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
        LinearLayout l;
        ImageView img;
        TextView txt,txt2;
        private Object InstanceState;
        private Bundle savedInstanceState;

        public < InstanceState > void oncreate( Bundle savedInstanceState ){
            super.onCreate ( savedInstanceState );
            setContentView ( R.layout.activity_main );

            l=new LinearLayout ( this );
            l.setOrientation ( LinearLayout.VERTICAL );

            txt = new TextView ( this );
            txt.setText ( "sorry for the inconvenience" );

            txt2 = new TextView ( this );
            txt2.setText ( "Please Try after sometime" );

            setContentView ( l );

    }
}