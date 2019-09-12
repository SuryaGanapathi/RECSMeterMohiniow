package com.recsmeterreading.font;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Anuprog on 10/13/2016.
 */

@SuppressLint ("AppCompatCustomView")
public class MyEditTextArial extends EditText {

    public MyEditTextArial(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyEditTextArial(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyEditTextArial(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/Quicksand-Regular.ttf");
        setTypeface(tf);
    }

//
//    private void init() {
//        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/Dosis-Light.ttf");
//        setTypeface(tf);
//    }

}
