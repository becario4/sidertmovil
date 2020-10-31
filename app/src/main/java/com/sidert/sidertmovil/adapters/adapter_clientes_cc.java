package com.sidert.sidertmovil.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.MTicketCC;

import java.util.HashMap;
import java.util.List;

public class adapter_clientes_cc extends RecyclerView.Adapter<adapter_clientes_cc.ViewHolder> {

    private Context ctx;
    private List<HashMap<Integer, String>> data;
    /*private Event evento;

    public interface Event {
        void ClienteOnClick(MTicketCC item);
    }*/

    public adapter_clientes_cc(Context ctx, List<HashMap<Integer, String>> data) {
        this.ctx = ctx;
        this.data = data;
        //this.evento = evento;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_recibo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HashMap<Integer, String> item = data.get(position);
        holder.tvNombre.setText((Html.fromHtml("<b>"+item.get(0)+"</b> ")));
        holder.tvRecibo.setText(Html.fromHtml("<b>Recibo:</b> "+item.get(4)));
        holder.tvImpresion.setText(Html.fromHtml("<b>Impresión:</b> "+item.get(5)));
        holder.tvFolio.setText(Html.fromHtml("<b>Folio:</b> "+item.get(1)));
        holder.tvFechaImpreso.setText(Html.fromHtml("<b>Fecha Impreso:</b> "+item.get(2)));
        holder.tvFechaEnvio.setText(Html.fromHtml("<b>Fecha Envio:</b> "+item.get(3)));

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

        /*public void bind (final MTicketCC item){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    evento.ClienteOnClick(item);
                }
            });
        }*/
    }
}
