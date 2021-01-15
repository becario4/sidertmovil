package com.sidert.sidertmovil.adapters;

import android.content.Context;
/*import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;*/
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.Reimpresion;

import java.util.List;

public class adapter_reimpresiones extends RecyclerView.Adapter<adapter_reimpresiones.ViewHolder> {

    private Context ctx;
    private List<Reimpresion> data;

    public adapter_reimpresiones(Context ctx, List<Reimpresion> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_impression, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reimpresion item = data.get(position);

        holder.tvNombre.setText(Html.fromHtml("<b>"+item.getNombre()+"</b> "));
        holder.tvNumPrestamo.setText(Html.fromHtml("<b>NPrestamo:</b> "+item.getNumPrestamo()));
        holder.tvClave.setText(Html.fromHtml("<b>Clave:</b> "+item.getClave()));
        holder.tvAsesor.setText(Html.fromHtml("<b>Asesor:</b> "+item.getAsesor()));
        holder.tvTipoImpresion.setText(Html.fromHtml("<b>Tipo:</b> "+item.getTipoImpresion()));
        holder.tvFolio.setText(Html.fromHtml("<b>Folio:</b> "+item.getFolio()));
        holder.tvMonto.setText(Html.fromHtml("<b>Monto:</b> "+"$"+item.getMonto()));
        holder.tvImpreso.setText(Html.fromHtml("<b>Impreso:</b> "+item.getImpreso()));
        holder.tvEnviado.setText(Html.fromHtml("<b>Enviado:</b> "+item.getEnviado()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombre;
        private TextView tvNumPrestamo;
        private TextView tvClave;
        private TextView tvAsesor;
        private TextView tvTipoImpresion;
        private TextView tvFolio;
        private TextView tvMonto;
        private TextView tvImpreso;
        private TextView tvEnviado;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNombre    = itemView.findViewById(R.id.tvNombre);
            tvNumPrestamo       = itemView.findViewById(R.id.tvNumPrestamo);
            tvClave          = itemView.findViewById(R.id.tvClave);
            tvAsesor         = itemView.findViewById(R.id.tvAsesor);
            tvTipoImpresion        = itemView.findViewById(R.id.tvTipoImpresion);
            tvFolio        = itemView.findViewById(R.id.tvFolio);
            tvMonto   = itemView.findViewById(R.id.tvMonto);
            tvImpreso      = itemView.findViewById(R.id.tvImpreso);
            tvEnviado      = itemView.findViewById(R.id.tvEnviado);

        }
    }
}
