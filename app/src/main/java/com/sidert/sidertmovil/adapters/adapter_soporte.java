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
import com.sidert.sidertmovil.models.MSoporte;

import java.util.List;

public class adapter_soporte extends RecyclerView.Adapter<adapter_soporte.ViewHolder> {

    private List<MSoporte> data;
    private Context ctx;

    private Event evento;

    public interface Event {
        void Soporte(MSoporte mensaje);
    }

    public adapter_soporte(Context ctx, List<MSoporte> data, Event evento) {
        this.data = data;
        this.ctx = ctx;
        this.evento = evento;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_soporte, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MSoporte item = data.get(position);

        holder.tvFolio.setText(Html.fromHtml("<b>Folio:</b> "+item.getFolioSolicitud()));
        holder.tvEstatus.setText(Html.fromHtml("<b>Estatus:</b> "+item.getEstatusSolicitud()));
        holder.tvCategoria.setText(Html.fromHtml("<b>Categoria:</b> "+item.getCategoria()));
        holder.tvComentario.setText(item.getComentario());
        holder.tvFechaSolicitud.setText(Html.fromHtml("<b>Fecha Solicitud:</b> "+item.getFechaSolicitud()));
        holder.tvFechaEnvio.setText((Html.fromHtml("<b>Fecha Envio:</b> "+((item.getFechaEnvio().trim().isEmpty())?"PENDIENTE DE ENVIO":item.getFechaEnvio()))));

        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFolio;
        private TextView tvEstatus;
        private TextView tvCategoria;
        private TextView tvComentario;
        private TextView tvFechaSolicitud;
        private TextView tvFechaEnvio;
        public ViewHolder(@NonNull View v) {
            super(v);

            tvFolio = v.findViewById(R.id.tvFolio);
            tvEstatus   = v.findViewById(R.id.tvEstatus);
            tvCategoria = v.findViewById(R.id.tvCategoria);
            tvComentario = v.findViewById(R.id.tvComentario);
            tvFechaSolicitud = v.findViewById(R.id.tvFechaSolicitud);
            tvFechaEnvio    = v.findViewById(R.id.tvFechaEnvio);
        }

        public void bind (final MSoporte item){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    evento.Soporte(item);
                }
            });
        }
    }

    public void UpdateData(List<MSoporte> _data){
        if (data != null)
            data.clear();
        data = _data;
        notifyDataSetChanged();
    }
}
