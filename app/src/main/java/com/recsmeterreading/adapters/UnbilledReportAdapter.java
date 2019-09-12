package com.recsmeterreading.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.recsmeterreading.R;
import com.recsmeterreading.model.ServiceNumber;

import java.util.List;

public class UnbilledReportAdapter extends RecyclerView.Adapter<UnbilledReportAdapter.UnReportViewHolder> {

    private Context context;
    private List<ServiceNumber> services;

    public UnbilledReportAdapter(Context context, List<ServiceNumber> services) {
        this.context = context;
        this.services = services;
    }

    @NonNull
    @Override
    public UnReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.unbilled_cat, parent, false);
        return new UnReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnReportViewHolder holder, int position) {

        ServiceNumber serviceNumber = services.get(position);

        holder.category.setText("Category: ".concat(serviceNumber.getCategory()));
        holder.value.setText("No Of Services: ".concat(String.valueOf(serviceNumber.getValue())));
        Log.e("aaa",serviceNumber.getCategory());

    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class UnReportViewHolder extends RecyclerView.ViewHolder{
        private TextView category,value;
        public UnReportViewHolder(View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.category_un);
            value = itemView.findViewById(R.id.value_un);
        }
    }
}
