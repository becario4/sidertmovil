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

import java.util.HashMap;
import java.util.List;

public class adapter_impresiones_cc extends RecyclerView.Adapter<adapter_impresiones_cc.ViewHolder> {

    private Context ctx;
    private List<HashMap<Integer, String>> data;

    public adapter_impresiones_cc(Context ctx, List<HashMap<Integer, String>> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_impresion_cc, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HashMap<Integer, String> item = data.get(position);
        holder.tvClienteGpo.setText((Html.fromHtml("<b>"+item.get(1)+"</b> ")));
        if (item.get(0).equals("Grupal")) {
            holder.tvAvalRepre.setText((Html.fromHtml("<b>" + item.get(2) + "</b> ")));
            holder.tvAvalRepre.setVisibility(View.VISIBLE);
        }
        else
            holder.tvAvalRepre.setVisibility(View.GONE);
        holder.tvRecibo.setText(Html.fromHtml("<b>"+item.get(0)+"</b> "));
        holder.tvImpresion.setText(Html.fromHtml("<b>Impresión:</b> "+item.get(4)));
        holder.tvFolio.setText(Html.fromHtml("<b>Folio:</b> "+item.get(5)));
        holder.tvFechaImpreso.setText(Html.fromHtml("<b>Fecha Impreso:</b> "+item.get(6)));
        holder.tvFechaEnvio.setText(Html.fromHtml("<b>Fecha Envio:</b> "+item.get(7)));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvClienteGpo;
        private TextView tvAvalRepre;
        private TextView tvRecibo;
        private TextView tvImpresion;
        private TextView tvFolio;
        private TextView tvFechaImpreso;
        private TextView tvFechaEnvio;
        public ViewHolder(@NonNull View v) {
            super(v);
            tvClienteGpo        = v.findViewById(R.id.tvClienteGpo);
            tvAvalRepre        = v.findViewById(R.id.tvAvalRepre);
            tvRecibo        = v.findViewById(R.id.tvRecibo);
            tvImpresion     = v.findViewById(R.id.tvImpresion);
            tvFolio         = v.findViewById(R.id.tvFolio);
            tvFechaImpreso  = v.findViewById(R.id.tvFechaImpreso);
            tvFechaEnvio    = v.findViewById(R.id.tvFechaEnvio);
        }
    }
}
