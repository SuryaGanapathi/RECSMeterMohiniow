package com.recsmeterreading.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.recsmeterreading.R;
import com.recsmeterreading.Utils.Utils;

import java.util.ArrayList;


public class TotalServiceListAdapter extends BaseAdapter {

    Activity context;
    TextView serviceNoCountTv,categoryTv;
    String[] category = new String[]{"100","101","102","200","201","202","203","204","205","300","301","302","303","304",
    "305","306","307","308","400","401","501","502","503","504","505","506","507","508","509","601","602","603","604","605","700","701","702","800"};

    String[] totalServiceNos = new String[]{"1000","800","1200","600","770","450","130","50","200","300","200","300","200","300",
    "200","300","200","300","200","300","200","300","200","300","200","300","100","100","100","200","100","200","100","100","200","100","100","100"};
    public TotalServiceListAdapter(Activity context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return category.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if (view == null) {
            grid = new View(context);
        } else {
            grid = view;
        }
        grid = inflater.inflate(R.layout.list_item, null);
        categoryTv=grid.findViewById(R.id.categoryTv);
        serviceNoCountTv=grid.findViewById(R.id.serviceNoCountTv);

        categoryTv.setText(category[position]);
        serviceNoCountTv.setText(totalServiceNos[position]);

        return grid;
    }
}
