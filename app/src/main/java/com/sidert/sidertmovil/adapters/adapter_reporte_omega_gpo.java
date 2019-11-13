package com.sidert.sidertmovil.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.ModeloGrupal;

import java.util.List;

public class adapter_reporte_omega_gpo extends RecyclerView.Adapter<adapter_reporte_omega_gpo.ViewHolder> {

    private Context ctx;
    private List<ModeloGrupal.ReporteAnaliticoOmega> data;

    public adapter_reporte_omega_gpo(Context ctx, List<ModeloGrupal.ReporteAnaliticoOmega> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_reporte_omega, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModeloGrupal.ReporteAnaliticoOmega item = data.get(position);

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