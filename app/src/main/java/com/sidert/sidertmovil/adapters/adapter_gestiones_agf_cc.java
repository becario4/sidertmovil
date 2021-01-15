package com.sidert.sidertmovil.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.utils.Miscellaneous;

import java.util.HashMap;
import java.util.List;

public class adapter_gestiones_agf_cc extends RecyclerView.Adapter<adapter_gestiones_agf_cc.ViewHolder> {

    private Context ctx;
    private List<HashMap<Integer, String>> data;

    public adapter_gestiones_agf_cc(Context ctx, List<HashMap<Integer, String>> data) {
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

        HashMap<Integer, String> item = data.get(position);

        holder.tvNombre.setText(item.get(0));
        holder.tvtipo.setText((Html.fromHtml("<b>Tipo:</b> "+item.get(1))));
        holder.tvMonto.setText((Html.fromHtml("<b>Monto:</b> "+Miscellaneous.moneyFormat(item.get(2)))));
        holder.tvMedioPago.setText((Html.fromHtml("<b>Medio Pago:</b> "+item.get(3))));
        holder.tvFolio.setText((Html.fromHtml("<b>Folio</b> "+item.get(4))));
        holder.tvFechaTermino.setText((Html.fromHtml("<b>Fecha Término: </b> "+item.get(5))));
        holder.tvFechaEnvio.setText((Html.fromHtml("<b>Fecha Envío</b> "+item.get(6))));

        if (!Miscellaneous.validStr(item.get(4)).isEmpty())
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
