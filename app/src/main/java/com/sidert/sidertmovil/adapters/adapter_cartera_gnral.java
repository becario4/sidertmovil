package com.sidert.sidertmovil.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.MCarteraGnral;

import java.util.ArrayList;


public class adapter_cartera_gnral extends RecyclerView.Adapter<adapter_cartera_gnral.ViewHolder> {

    private Context ctx;
    private ArrayList<MCarteraGnral> data;
    private Event evento;

    public interface Event {
        void CarteraClick(MCarteraGnral item);
    }

    public adapter_cartera_gnral(Context ctx, ArrayList<MCarteraGnral> data, Event evento) {
        this.ctx = ctx;
        this.data = data;
        this.evento = evento;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_cartera, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        MCarteraGnral item = data.get(i);

        switch (item.getTipo()){
            case 1:
                holder.ivTipo.setImageDrawable(ctx.getResources().getDrawable(R.drawable.ic_default_user));
                break;
            case 2:
                holder.ivTipo.setImageDrawable(ctx.getResources().getDrawable(R.drawable.ic_users));
                break;
        }

        holder.cbRuta.setEnabled(!item.isIs_obligatorio());
        holder.cbRuta.setChecked(item.isIs_ruta());
        holder.tvNombre.setText(item.getNombre());
        holder.tvTesorera.setText(item.getTesorera());
        holder.tvDiaSemana.setText(item.getDiaSemana());
        holder.tvDireccion.setText(item.getDireccion());

        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivTipo;
        private CheckBox cbRuta;
        private TextView tvNombre;
        private TextView tvTesorera;
        private TextView tvDiaSemana;
        private TextView tvDireccion;

        public ViewHolder(@NonNull View v) {
            super(v);
            ivTipo      = v.findViewById(R.id.ivTipo);
            cbRuta      = v.findViewById(R.id.cbRuta);
            tvNombre    = v.findViewById(R.id.tvNombre);
            tvTesorera  = v.findViewById(R.id.tvTesorera);
            tvDiaSemana = v.findViewById(R.id.tvDiaSemana);
            tvDireccion = v.findViewById(R.id.tvDireccion);
        }

        public void bind (final MCarteraGnral item){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    evento.CarteraClick(item);
                }
            });
        }
    }
}
