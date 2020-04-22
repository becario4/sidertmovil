package com.sidert.sidertmovil.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.MIntegrante;
import com.sidert.sidertmovil.utils.Miscellaneous;

import java.util.List;

public class adapter_integrantes extends RecyclerView.Adapter<adapter_integrantes.ViewHolder> {

    private Context ctx;
    private List<MIntegrante> data;

    public adapter_integrantes(Context ctx, List<MIntegrante> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_intengrante, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MIntegrante item = data.get(position);
        holder.tvTipoIntegrante.setText(item.getTipo());
        holder.tvNombre.setText(item.getNombre());
        holder.tvDireccion.setText(item.getDireccion());
        holder.tvMontoPrestamo.setText(Html.fromHtml("<b>MONTO DEL PRESTAMO:</b> "+ Miscellaneous.moneyFormat(item.getMontoPrestamo())));
        holder.tvTelCasa.setText(Html.fromHtml("<b>TEL. CASA:</b> "+ item.getTelCasa()));
        holder.tvTelCel.setText(Html.fromHtml("<b>TEL. MÓVIL:</b> "+ item.getTelCelular()));
        switch (item.getTipo()){
            case "PRESIDENTE":
                Glide.with(ctx).load(R.drawable.ic_corbata).into(holder.ivTipoIntegrante);
                break;
            case "TESORERO":
                Glide.with(ctx).load(R.drawable.ic_tesoro).into(holder.ivTipoIntegrante);
                break;
            case "SECRETARIO":
                Glide.with(ctx).load(R.drawable.ic_lapiz).into(holder.ivTipoIntegrante);
                break;
            case "INTEGRANTE":
                Glide.with(ctx).load(R.drawable.ic_integrante).into(holder.ivTipoIntegrante);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTipoIntegrante;
        private TextView tvNombre;
        private TextView tvDireccion;
        private TextView tvMontoPrestamo;
        private TextView tvTelCasa;
        private TextView tvTelCel;
        private ImageView ivTipoIntegrante;
        public ViewHolder(@NonNull View v) {
            super(v);
            tvTipoIntegrante = v.findViewById(R.id.tvTipoIntegrante);
            tvNombre = v.findViewById(R.id.tvNombre);
            tvDireccion = v.findViewById(R.id.tvDireccion);
            tvMontoPrestamo = v.findViewById(R.id.tvMontoPrestamo);
            tvTelCasa = v.findViewById(R.id.tvTelCasa);
            tvTelCel = v.findViewById(R.id.tvTelCel);
            ivTipoIntegrante = v.findViewById(R.id.ivTipoIntegrante);
        }
    }


}
