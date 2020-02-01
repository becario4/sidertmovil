package com.sidert.sidertmovil.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.ModelFichaContestada;
import com.sidert.sidertmovil.models.ModeloFichaGeneral;

import java.util.List;

public class adapter_ficha_contestadas extends RecyclerView.Adapter<adapter_ficha_contestadas.ViewHolder> {


    private Context ctx;
    private List<ModelFichaContestada> data;
    private Event evento;

    public interface Event {
        void FichaOnClick(ModelFichaContestada item);
    }

    public adapter_ficha_contestadas(Context ctx, List<ModelFichaContestada> data, Event evento) {
        this.ctx = ctx;
        this.data = data;
        this.evento = evento;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_ficha_contestada, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ModelFichaContestada item = data.get(position);
        holder.tvNombre.setText(item.getNombreClienteGpo());
        holder.tvFechaIni.setText(item.getFechaIni());
        holder.tvFechaTer.setText(item.getFechaTer());
        holder.tvFechaEnv.setText(item.getFechaEnv());
        holder.tvFechaFin.setText(item.getFechaFin());
        holder.tvPosicion.setText(String.valueOf(item.getPosicion()));

        holder.bind(item);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombre;
        private TextView tvFechaIni;
        private TextView tvFechaTer;
        private TextView tvFechaEnv;
        private TextView tvFechaFin;
        private TextView tvPosicion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvFechaIni = itemView.findViewById(R.id.tvFechaIni);
            tvFechaTer = itemView.findViewById(R.id.tvFechaTer);
            tvFechaEnv = itemView.findViewById(R.id.tvFechaEnv);
            tvFechaFin = itemView.findViewById(R.id.tvFechaFin);
            tvPosicion  = itemView.findViewById(R.id.tvPosicion);
        }

        public void bind (final ModelFichaContestada item){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    evento.FichaOnClick(item);
                }
            });
        }
    }
}
