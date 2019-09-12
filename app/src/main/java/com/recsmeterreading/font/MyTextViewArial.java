package com.recsmeterreading.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class MyTextViewArial extends android.support.v7.widget.AppCompatTextView {

    public MyTextViewArial(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyTextViewArial(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextViewArial(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/akshar.ttf");
        setTypeface(tf);
    }

//
//    private void init() {
//        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/Dosis-Light.ttf");
//        setTypeface(tf);
//    }

}
