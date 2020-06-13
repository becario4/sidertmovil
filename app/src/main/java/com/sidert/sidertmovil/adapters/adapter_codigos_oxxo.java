package com.sidert.sidertmovil.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.MCodigoOxxo;
import com.sidert.sidertmovil.utils.Miscellaneous;

import java.util.List;

public class adapter_codigos_oxxo extends RecyclerView.Adapter<adapter_codigos_oxxo.ViewHolder> {

    private Context ctx;
    private List<MCodigoOxxo> data;
    private Event evento;

    public interface Event {
        void CompartirClick(String link);
    }

    public adapter_codigos_oxxo(Context ctx, List<MCodigoOxxo> data, Event evento) {
        this.ctx = ctx;
        this.data = data;
        this.evento = evento;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_codigo_oxxo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MCodigoOxxo item = data.get(position);

        holder.tvFecha.setText(item.getFechaAmortizacion());
        holder.tvMonto.setText(Miscellaneous.moneyFormat(String.valueOf(item.getMontoAmortizacion())));

        holder.ivCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evento.CompartirClick(item.getNombrePdf());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvFecha;
        private TextView tvMonto;
        private ImageView ivCompartir;

        public ViewHolder(@NonNull View v) {
            super(v);
            tvFecha = v.findViewById(R.id.tvFecha);
            tvMonto = v.findViewById(R.id.tvMonto);
            ivCompartir   = v.findViewById(R.id.ivCompartir);
        }
    }
}
