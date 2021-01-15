package com.sidert.sidertmovil.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.MPago;
import com.sidert.sidertmovil.utils.Miscellaneous;

import java.util.List;

public class adapter_pagos_gpo extends RecyclerView.Adapter<adapter_pagos_gpo.ViewHolder> {

    private Context ctx;
    private List<MPago> data;

    public adapter_pagos_gpo(Context ctx, List<MPago> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_tabla_pagos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MPago item = data.get(position);
        Log.v("Tabla", item.getBanco());

        holder.tvNumero.setText((position<9)?"0"+(position +1):String.valueOf(position +1));
        holder.tvFecha.setText(": " + item.getFecha());
        holder.tvPago.setText(": " + Miscellaneous.moneyFormat(String.valueOf(item.getMonto())));
        holder.tvBanco.setText(": " + item.getBanco());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNumero;
        private TextView tvFecha;
        private TextView tvPago;
        private TextView tvBanco;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumero = itemView.findViewById(R.id.tvNumero);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvPago = itemView.findViewById(R.id.tvPago);
            tvBanco = itemView.findViewById(R.id.tvBanco);
        }
    }
}
