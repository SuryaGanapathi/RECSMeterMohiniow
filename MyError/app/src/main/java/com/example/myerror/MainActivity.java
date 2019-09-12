package com.example.myerror;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//import java.time.Instant;


public class MainActivity extends AppCompatActivity {
    LinearLayout l;
    ImageView img;
    TextView txt,txt2,txt3,txt4;
    private Object InstanceState;
    private Bundle savedInstanceState;

    @SuppressLint ( "SetTextI18n" )
    public < InstanceState > void oncreate( Bundle savedInstanceState ){
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        l=new LinearLayout ( this );
        l.setOrientation ( LinearLayout.VERTICAL );

        txt = new TextView ( this );
        txt.setText ( "we_re_sorry" );

        txt2 = new TextView ( this );
        txt2.setText ( "An error occured when we tried to process your request" );

        txt3 = new TextView ( this );
        txt3.setText ( "we_re_working_on_the_problem_and_expected_to_resolve_it_shortly_please_try_again_later" );

        txt4 = new TextView ( this );
        txt4.setText ( "we_apologise_for_the_inconvenience" );

        setContentView ( l );
    }
}