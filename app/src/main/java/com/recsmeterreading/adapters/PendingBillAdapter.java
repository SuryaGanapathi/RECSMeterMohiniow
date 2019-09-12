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
import com.recsmeterreading.model.GetBillDetailsModel;
import com.recsmeterreading.model.LocalBillModel;

import java.util.ArrayList;


public class PendingBillAdapter extends BaseAdapter {

    Activity context;
    TextView AmountText,Name,ServiceNumber,BillAmount;
    ArrayList<LocalBillModel> BillDetailsModel;
    public PendingBillAdapter(Activity context, ArrayList<LocalBillModel> getBillDetailsModel) {
        this.context = context;
        BillDetailsModel=getBillDetailsModel;
    }

    @Override
    public int getCount() {
//        return Utils.getSharedInstance().getBillDetailsModel.size();

        return BillDetailsModel.size();
    }

    @Override
    public Object getItem(int i) {
       // return Utils.getSharedInstance().getBillDetailsModel.get(i);
        return BillDetailsModel.get(i);
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
        grid = inflater.inflate(R.layout.pending_list_item, null);
        AmountText=grid.findViewById(R.id.amountText);
        Name=grid.findViewById(R.id.name);
        ServiceNumber=grid.findViewById(R.id.service_number);

        AmountText.setText(BillDetailsModel.get(position).getBILLAMT());
        Name.setText(BillDetailsModel.get(position).getCONSUMER_NAME());
        ServiceNumber.setText(BillDetailsModel.get(position).getSERVICENO());
        return grid;
    }
}
