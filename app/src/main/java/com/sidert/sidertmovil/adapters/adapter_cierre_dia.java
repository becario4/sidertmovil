package com.sidert.sidertmovil.adapters;

import android.content.Context;
//import androidx.annotation.NonNull;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.MCierreDia;

import java.util.List;

public class adapter_cierre_dia extends RecyclerView.Adapter<adapter_cierre_dia.ViewHolder> {

    private Context ctx;
    private List<MCierreDia> data;
    private Event evento;

    public interface Event {
        void CierreOnClick(MCierreDia item);
    }

    public adapter_cierre_dia(Context ctx, List<MCierreDia> data, Event evento) {
        this.ctx = ctx;
        this.data = data;
        this.evento = evento;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_cierre_dia, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MCierreDia item = data.get(position);

        holder.tvNombre.setText(item.getNombre());
        holder.tvFecha.setText(item.getFecha());
        holder.tvPago.setText("$ "+item.getPago());

        if (item.getEstatus() == 0) {
            Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_coins)).into(holder.ivEstatus);
            holder.ivEnviado.setVisibility(View.INVISIBLE);
        }
        else if (item.getEstatus() == 1) {
            Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_depositado)).into(holder.ivEstatus);
            Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_pendiente)).into(holder.ivEnviado);
            holder.ivEnviado.setVisibility(View.VISIBLE);
        }
        else {
            Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_depositado)).into(holder.ivEstatus);
            Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_enviado_yellow)).into(holder.ivEnviado);
            holder.ivEnviado.setVisibility(View.VISIBLE);
        }

        holder.bind(item);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNombre;
        private TextView tvFecha;
        private TextView tvPago;
        private ImageView ivEstatus;
        private ImageView ivEnviado;

        public ViewHolder(@NonNull View v) {
            super(v);

            tvNombre    = v.findViewById(R.id.tvNombre);
            tvFecha     = v.findViewById(R.id.tvFecha);
            tvPago      = v.findViewById(R.id.tvPago);
            ivEstatus   = v.findViewById(R.id.ivEstatus);
            ivEnviado   = v.findViewById(R.id.ivEnviado);
        }

        public void bind (final MCierreDia item){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    evento.CierreOnClick(item);
                }
            });
        }
    }
}
