package com.sidert.sidertmovil.views.apoyogastosfunerarios;

import android.content.Context;
import androidx.annotation.NonNull;
//import android.support.v7.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.apoyogastosfunerarios.Prestamo;

import java.util.HashMap;
import java.util.List;

public class PrestamosAdapter extends RecyclerView.Adapter<PrestamosAdapter.ViewHolder> {

    private Context ctx;
    private List<Prestamo> data;
    private Event evento;

    public interface Event {
        void AgfOnClick(Prestamo prestamo);
    }

    public PrestamosAdapter(Context ctx, List<Prestamo> data, Event evento) {
        this.ctx = ctx;
        this.data = data;
        this.evento = evento;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_prestamo_agf_cc, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Prestamo prestamo = data.get(position);

        holder.ivTipo.setVisibility(View.VISIBLE);

        if (prestamo.getGrupoId() == 1) {
            holder.tvNombre.setText(prestamo.getNombreCliente().substring(1));
            if (prestamo.getEstatusRecibo() == -1)
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_person_blue)).into(holder.ivTipo);
            else
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_user_yellow)).into(holder.ivTipo);
        }
        else {
            holder.tvNombre.setText(prestamo.getNombreGrupo());
            if (prestamo.getEstatusRecibo() == -1)
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_group_blue)).into(holder.ivTipo);
            else
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_cliente)).into(holder.ivTipo);
        }

        holder.bind(prestamo);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvNombre;
        private ImageView ivTipo;

        public ViewHolder(@NonNull View iv)
        {
            super(iv);

            tvNombre = iv.findViewById(R.id.tvNombre);
            ivTipo  = iv.findViewById(R.id.ivTipo);
        }

        public void bind (final Prestamo p){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    evento.AgfOnClick(p);
                }
            });
        }
    }
}
