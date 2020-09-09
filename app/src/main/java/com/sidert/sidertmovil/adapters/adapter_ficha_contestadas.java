package com.sidert.sidertmovil.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.MFichaContestada;

import java.util.List;

public class adapter_ficha_contestadas extends RecyclerView.Adapter<adapter_ficha_contestadas.ViewHolder> {


    private Context ctx;
    private List<MFichaContestada> data;

    public adapter_ficha_contestadas(Context ctx, List<MFichaContestada> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_ficha_contestada, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MFichaContestada item = data.get(position);
        holder.tvNombre.setText(item.getNombre());
        holder.tvEstatus.setText(item.getEstatusGestion());
        holder.tvDiaPago.setText(item.getDiaPago());
        holder.tvTimestamp.setText(item.getTimestamp());
        holder.tvInicio.setText(item.getFechaInicio());


        if (item.getTipoFicha().equals("INDIVIDUAL"))
            Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_person_blue)).into(holder.ivTipoFicha);
        else
            Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_group_blue)).into(holder.ivTipoFicha);

        switch (item.getEstatusFicha()){
            case "0":
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_send_black)).into(holder.ivEstatus);
                break;
            case "1":
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_pendiente)).into(holder.ivEstatus);
                break;
            case "2":
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_enviado_yellow)).into(holder.ivEstatus);
                break;
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombre;
        private TextView tvEstatus;
        private TextView tvDiaPago;
        private TextView tvTimestamp;
        private ImageView ivTipoFicha;
        private ImageView ivEstatus;
        private TextView tvInicio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvEstatus = itemView.findViewById(R.id.tvEstatusGestion);
            tvDiaPago = itemView.findViewById(R.id.tvDiaPago);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            ivTipoFicha = itemView.findViewById(R.id.ivTipoFicha);
            ivEstatus  = itemView.findViewById(R.id.ivEstatus);
            tvInicio  = itemView.findViewById(R.id.tvInicio);
        }
    }

    public void UpdateData(List<MFichaContestada> _data){
        if (data != null)
            data.clear();
        data = _data;
        notifyDataSetChanged();
    }
}
