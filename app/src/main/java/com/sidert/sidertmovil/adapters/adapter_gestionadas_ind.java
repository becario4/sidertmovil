package com.sidert.sidertmovil.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.MGestionada;

import java.util.List;

public class adapter_gestionadas_ind extends RecyclerView.Adapter<adapter_gestionadas_ind.ViewHolder> {

    private Context ctx;
    private List<MGestionada> data;
    private Event evento;

    public interface Event {
        void GestionadaClick(MGestionada item);
        void SendClickLong(MGestionada item);
    }


    public adapter_gestionadas_ind(Context ctx, List<MGestionada> data, Event evento) {
        this.ctx = ctx;
        this.data = data;
        this.evento = evento;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_gestionada_ind, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        MGestionada item = data.get(i);
        holder.tvFechaGestion.setText(item.getFechaGestion());
        holder.tvContacto.setText(item.getContacto());
        holder.tvMonto.setText(item.getMonto());
        holder.tvComBan.setText(item.getComentarioBanco());
        holder.tvResultado.setText(item.getResultado());
        holder.tvEstatus.setText(item.getEstatusCancel());
        holder.tvcomentario.setText(item.getComentario());

        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFechaGestion;
        private TextView tvContacto;
        private TextView tvComBan;
        private TextView tvResultado;
        private TextView tvMonto;
        private TextView tvEstatus;
        private TextView tvcomentario;

        public ViewHolder(@NonNull View v) {
            super(v);
            tvFechaGestion = v.findViewById(R.id.tvFechagestion);
            tvContacto = v.findViewById(R.id.tvContacto);
            tvComBan = v.findViewById(R.id.tvComBan);
            tvResultado = v.findViewById(R.id.tvResultado);
            tvMonto = v.findViewById(R.id.tvMonto);
            tvEstatus   = v.findViewById(R.id.tvEstatus);
            tvcomentario = v.findViewById(R.id.tvComentario);
        }

        public void bind (final MGestionada item){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    evento.GestionadaClick(item);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    evento.SendClickLong(item);
                    return true;
                }
            });
        }
    }
}
