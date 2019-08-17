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
import com.sidert.sidertmovil.models.ModeloIndividual;

import java.util.List;
import java.util.Random;

public class adapter_ind_omega_report extends RecyclerView.Adapter<adapter_ind_omega_report.ViewHolder>{

    private Context ctx;
    private List<ModeloIndividual.ReporteAnaliticoOmega> data;

    public adapter_ind_omega_report(Context ctx, List<ModeloIndividual.ReporteAnaliticoOmega> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_ind_omega_report, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        ModeloIndividual.ReporteAnaliticoOmega item = data.get(position);

        holder.tvNumber.setText(String.valueOf(item.getNo()));
        holder.tvFechaAmortizacion.setText(item.getFechaAmortizacion());
        holder.tvFechaPago.setText(item.getFechaPago());
        holder.tvEstatus.setText(item.getEstatus());
        holder.tvDays.setText(String.valueOf(item.getDias()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFechaAmortizacion;
        private TextView tvFechaPago;
        private TextView tvEstatus;
        private TextView tvNumber;
        private TextView tvDays;
        public ViewHolder(View itemView) {
            super(itemView);
            tvFechaAmortizacion = itemView.findViewById(R.id.tvFechaAmortizacion);
            tvFechaPago         = itemView.findViewById(R.id.tvFechaPago);
            tvEstatus           = itemView.findViewById(R.id.tvEstatus);
            tvNumber            = itemView.findViewById(R.id.tvNumber);
            tvDays              = itemView.findViewById(R.id.tvDays);
        }
    }
}
