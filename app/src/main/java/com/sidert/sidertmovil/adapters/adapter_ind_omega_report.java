package com.sidert.sidertmovil.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sidert.sidertmovil.R;

import java.util.List;
import java.util.Random;

public class adapter_ind_omega_report extends RecyclerView.Adapter<adapter_ind_omega_report.ViewHolder>{

    private Context ctx;

    public adapter_ind_omega_report(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_ind_omega_report, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int i) {
        viewHolder.tvNumber.setText(String.valueOf(i+1));
        viewHolder.tvDays.setText(String.valueOf((new Random()).nextInt((10 - (-5) ) + 1) + (-5) ));
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNumber;
        private TextView tvDays;
        public ViewHolder(View itemView) {
            super(itemView);
            tvNumber    = itemView.findViewById(R.id.tvNumber);
            tvDays      = itemView.findViewById(R.id.tvDays);
        }
    }
}
