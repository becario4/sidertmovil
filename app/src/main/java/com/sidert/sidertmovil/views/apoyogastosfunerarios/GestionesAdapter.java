package com.sidert.sidertmovil.views.apoyogastosfunerarios;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.apoyogastosfunerarios.Gestion;
import com.sidert.sidertmovil.utils.Miscellaneous;

import java.util.HashMap;
import java.util.List;

public class GestionesAdapter extends RecyclerView.Adapter<GestionesAdapter.ViewHolder> {

    private Context ctx;
    private List<Gestion> data;

    public GestionesAdapter(Context ctx, List<Gestion> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_cc_agf, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Gestion gestion = data.get(position);

        holder.tvNombre.setText(gestion.getNombre());
        holder.tvtipo.setText((Html.fromHtml("<b>Tipo:</b> "+gestion.getTipo())));
        holder.tvMonto.setText((Html.fromHtml("<b>Monto:</b> "+Miscellaneous.moneyFormat(gestion.getMonto()))));
        holder.tvMedioPago.setText((Html.fromHtml("<b>Medio Pago:</b> "+gestion.getMedioPago())));

        if(gestion.getMedioPago().equals("EFECTIVO") && gestion.getImprimirRecibo().equals("NO"))
        {
            holder.tvFolio.setText((Html.fromHtml("<b>Folio</b> "+gestion.getFolioManual())));
        }
        else{
            holder.tvFolio.setText((Html.fromHtml("<b>Folio</b> "+gestion.getFolioImpresion())));
        }

        holder.tvFechaTermino.setText((Html.fromHtml("<b>Fecha Término: </b> "+gestion.getFechaTermino())));
        holder.tvFechaEnvio.setText((Html.fromHtml("<b>Fecha Envío</b> "+gestion.getFechaEnvio())));

        if (!Miscellaneous.validStr(holder.tvFolio.getText()).isEmpty())
            holder.tvFolio.setVisibility(View.VISIBLE);
        else
            holder.tvFolio.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombre;
        private TextView tvtipo;
        private TextView tvMonto;
        private TextView tvMedioPago;
        private TextView tvFolio;
        private TextView tvFechaTermino;
        private TextView tvFechaEnvio;

        public ViewHolder(@NonNull View iv) {
            super(iv);
            tvNombre        = iv.findViewById(R.id.tvNombre);
            tvtipo          = iv.findViewById(R.id.tvTipo);
            tvMonto         = iv.findViewById(R.id.tvMonto);
            tvMedioPago     = iv.findViewById(R.id.tvMedioPago);
            tvFolio         = iv.findViewById(R.id.tvFolio);
            tvFechaTermino  = iv.findViewById(R.id.tvFechaTermino);
            tvFechaEnvio    = iv.findViewById(R.id.tvFechaEnvio);
        }
    }
}
