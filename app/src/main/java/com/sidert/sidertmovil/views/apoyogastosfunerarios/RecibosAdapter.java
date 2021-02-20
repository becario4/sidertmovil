package com.sidert.sidertmovil.views.apoyogastosfunerarios;

import android.content.Context;
//import androidx.annotation.NonNull;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.apoyogastosfunerarios.Recibo;

import java.util.HashMap;
import java.util.List;

public class RecibosAdapter extends RecyclerView.Adapter<RecibosAdapter.ViewHolder> {

    private Context ctx;
    private List<Recibo> data;

    public RecibosAdapter(Context ctx, List<Recibo> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_recibo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recibo recibo = data.get(position);
        holder.tvNombre.setText((Html.fromHtml("<b>"+recibo.getNombre()+"</b> ")));
        holder.tvRecibo.setText(Html.fromHtml("<b>Recibo:</b> "+recibo.getTipoRecibo()));
        holder.tvImpresion.setText(Html.fromHtml("<b>Impresión:</b> "+recibo.getImpresiones()));
        holder.tvFolio.setText(Html.fromHtml("<b>Folio:</b> "+recibo.getFolio()));
        holder.tvFechaImpreso.setText(Html.fromHtml("<b>Fecha Impreso:</b> "+recibo.getFechaImpresion()));
        holder.tvFechaEnvio.setText(Html.fromHtml("<b>Fecha Envio:</b> "+recibo.getFechaEnvio()));

        //holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombre;
        private TextView tvRecibo;
        private TextView tvImpresion;
        private TextView tvFolio;
        private TextView tvFechaImpreso;
        private TextView tvFechaEnvio;

        public ViewHolder(@NonNull View v) {
            super(v);
            tvNombre        = v.findViewById(R.id.tvNombre);
            tvRecibo        = v.findViewById(R.id.tvRecibo);
            tvImpresion     = v.findViewById(R.id.tvImpresion);
            tvFolio         = v.findViewById(R.id.tvFolio);
            tvFechaImpreso  = v.findViewById(R.id.tvFechaImpreso);
            tvFechaEnvio    = v.findViewById(R.id.tvFechaEnvio);
        }
    }
}
