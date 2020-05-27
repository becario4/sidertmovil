package com.sidert.sidertmovil.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sidert.sidertmovil.R;

import java.util.ArrayList;
import java.util.HashMap;

public class adapter_geo_miembros extends RecyclerView.Adapter<adapter_geo_miembros.ViewHolder> {

    private ArrayList<HashMap<String, String>> data;
    private Context ctx;
    private Event evento;

    public interface Event {
        void ItemOnClick(HashMap<String, String> item);
    }

    public adapter_geo_miembros(Context ctx, ArrayList<HashMap<String, String>> data, Event evento) {
        this.data = data;
        this.ctx = ctx;
        this.evento = evento;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_geo_miembro, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HashMap<String, String> item = data.get(position);

        holder.tvNombre.setText(item.get("nombre").trim().toUpperCase());
        holder.tvDireccion.setText(item.get("direccion").trim().toUpperCase());
        if (item.get("estatus").trim().isEmpty() || item.get("estatus").equals("-1"))
            holder.ivGeo.setVisibility(View.INVISIBLE);
        else
            holder.ivGeo.setVisibility(View.VISIBLE);

        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return (data != null)? data.size():0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivGeo;
        private TextView tvNombre;
        private TextView tvDireccion;

        public ViewHolder(@NonNull View v) {
            super(v);
            ivGeo = v.findViewById(R.id.ivGeo);
            tvNombre = v.findViewById(R.id.tvNombre);
            tvDireccion = v.findViewById(R.id.tvDireccion);
        }

        public void bind (final HashMap<String, String> item){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    evento.ItemOnClick(item);
                }
            });
        }
    }
}
