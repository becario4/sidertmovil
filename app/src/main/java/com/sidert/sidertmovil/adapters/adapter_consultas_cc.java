package com.sidert.sidertmovil.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.utils.Miscellaneous;

import java.util.HashMap;
import java.util.List;

public class adapter_consultas_cc extends RecyclerView.Adapter<adapter_consultas_cc.ViewHolder> {

    private Context ctx;
    private List<HashMap<String, String>> data;
    private Event evento;

    public interface Event {
        void OnClick(HashMap<String,String> item);
    }

    public adapter_consultas_cc(Context ctx, List<HashMap<String, String>> data, Event evento) {
        this.ctx = ctx;
        this.data = data;
        this.evento = evento;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_consulta_cc, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final HashMap<String, String> item = data.get(position);
        holder.tvNombre.setText(item.get("nombre"));
        holder.tvDireccion.setText(item.get("direccion"));
        holder.tvProducto.setText(item.get("producto"));

        switch (item.get("estatus")){
            case "0":
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_pendiente)).into(holder.ivEstatus);
                break;
            case "1":
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_enviado_yellow)).into(holder.ivEstatus);
                break;
        }

        holder.ivInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evento.OnClick(item);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivEstatus;
        private ImageView ivInfo;
        private TextView tvNombre;
        private TextView tvDireccion;
        private TextView tvProducto;

        public ViewHolder(@NonNull View v) {
            super(v);
            ivEstatus = v.findViewById(R.id.ivEstatus);
            tvNombre    = v.findViewById(R.id.tvNombre);
            tvDireccion = v.findViewById(R.id.tvDireccion);
            tvProducto  = v.findViewById(R.id.tvProducto);
            ivInfo      = v.findViewById(R.id.ivInfo);
        }
    }
}
