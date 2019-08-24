package com.sidert.sidertmovil.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.ModeloFichaGeneral;
import com.sidert.sidertmovil.models.ModeloIndividual;

import java.util.List;

public class adapter_fichas_pendientes extends RecyclerView.Adapter<adapter_fichas_pendientes.ViewHolder> {

    private Context ctx;
    private List<ModeloFichaGeneral> data;
    private Event evento;

    public interface Event {
        void FichaOnClick(ModeloFichaGeneral item);
    }

    public adapter_fichas_pendientes(Context ctx, List<ModeloFichaGeneral> data, Event evento) {
        this.ctx = ctx;
        this.data = data;
        this.evento = evento;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_ficha, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ModeloFichaGeneral item = data.get(position);
        holder.tvNombre.setText(item.getNombreClienteGpo());
        holder.tvDireccion.setText(item.getDireccion());
        holder.tvFechaPago.setText(item.getFechaPago());
        holder.tvDiaSemana.setText(item.getDiaSemana());

        holder.cbRuta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setChecked(isChecked);
            }
        });
        holder.cbRuta.setChecked(item.isChecked());
        holder.bind(item);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombre;
        private TextView tvDireccion;
        private TextView tvFechaPago;
        private TextView tvDiaSemana;
        private CheckBox cbRuta;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre    = itemView.findViewById(R.id.tvNombre);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvFechaPago = itemView.findViewById(R.id.tvFechaPago);
            tvDiaSemana = itemView.findViewById(R.id.tvDiaSemana);
            cbRuta      = itemView.findViewById(R.id.cbRuta);
        }

        public void bind (final ModeloFichaGeneral item){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    evento.FichaOnClick(item);
                }
            });
        }


    }
}
