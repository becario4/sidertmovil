package com.sidert.sidertmovil.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.MAutorizarCC;

import java.util.List;

public class autorizaciones_adapter extends RecyclerView.Adapter<autorizaciones_adapter.ViewHolder> {

    private Context ctx;
    private List<MAutorizarCC> data;
    private Event evento;

    public interface Event {
        void infoClick(MAutorizarCC item);
    }

    public autorizaciones_adapter(Context ctx, List<MAutorizarCC> data, Event evento) {
        this.ctx = ctx;
        this.data = data;
        this.evento = evento;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_autorizar_cc, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MAutorizarCC item = data.get(position);

        if (item.getAutorizacion().equals("ERROR")) {
            holder.tvProspecto.setTextColor(ContextCompat.getColor(ctx, R.color.red));
            holder.tvRfc.setTextColor(ContextCompat.getColor(ctx, R.color.red_push));
            holder.tvFecha.setTextColor(ContextCompat.getColor(ctx, R.color.red));
            holder.tvHora.setTextColor(ContextCompat.getColor(ctx, R.color.red));
            holder.tvUsuario.setTextColor(ContextCompat.getColor(ctx, R.color.red_push));
            holder.tvPreAutorizacion.setTextColor(ContextCompat.getColor(ctx, R.color.red));
        }
        else {
            holder.tvProspecto.setTextColor(ContextCompat.getColor(ctx, R.color.black));
            holder.tvRfc.setTextColor(ContextCompat.getColor(ctx, R.color.gray));
            holder.tvFecha.setTextColor(ContextCompat.getColor(ctx, R.color.black));
            holder.tvHora.setTextColor(ContextCompat.getColor(ctx, R.color.black));
            holder.tvUsuario.setTextColor(ContextCompat.getColor(ctx, R.color.gray));
            holder.tvPreAutorizacion.setTextColor(ContextCompat.getColor(ctx, R.color.black));

        }

        holder.tvAutorizacion.setTextColor(ContextCompat.getColor(ctx, R.color.blue_sidert));

        holder.tvProspecto.setText(item.getNombreConsulta());
        holder.tvUsuario.setText(item.getUsuario());
        holder.tvRfc.setText(item.getRfc());
        holder.tvFecha.setText(item.getCreatedAt().split(" ")[0]);
        holder.tvHora.setText(item.getCreatedAt().split(" ")[1]);
        holder.tvPreAutorizacion.setText(item.getPreautorizacion());
        holder.tvAutorizacion.setText(item.getAutorizacion());
        if (item.getProducto().equals("CRÉDITO GRUPAL"))
            Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_group_blue)).into(holder.ivTipo);
        else
            Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_person_blue)).into(holder.ivTipo);

        if (item.getOrigen().equals("WEB"))
            Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_laptop)).into(holder.ivOrigen);
        else
            Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_mobile)).into(holder.ivOrigen);

        holder.ivInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    evento.infoClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivTipo;
        private ImageView ivOrigen;
        private ImageView ivInfo;
        private TextView tvProspecto;
        private TextView tvRfc;
        private TextView tvFecha;
        private TextView tvHora;
        private TextView tvUsuario;
        private TextView tvPreAutorizacion;
        private TextView tvAutorizacion;

        public ViewHolder(@NonNull View iv) {
            super(iv);
            ivTipo              = iv.findViewById(R.id.ivTipo);
            ivOrigen            = iv.findViewById(R.id.ivOrigen);
            ivInfo              = iv.findViewById(R.id.ivInfo);
            tvProspecto         = iv.findViewById(R.id.tvProspecto);
            tvRfc               = iv.findViewById(R.id.tvRfc);
            tvFecha             = iv.findViewById(R.id.tvFecha);
            tvHora              = iv.findViewById(R.id.tvHora);
            tvUsuario           = iv.findViewById(R.id.tvUsuario);
            tvPreAutorizacion   = iv.findViewById(R.id.tvPreAutorizacion);
            tvAutorizacion      = iv.findViewById(R.id.tvAutorizacion);
        }
    }
}
