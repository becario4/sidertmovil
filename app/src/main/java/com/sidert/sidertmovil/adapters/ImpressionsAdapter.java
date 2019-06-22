package com.sidert.sidertmovil.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.ImpressionsFields;

import java.util.List;

public class ImpressionsAdapter extends RecyclerView.Adapter<ImpressionsAdapter.ViewHolder>{

    private Context ctx;
    private List<ImpressionsFields> data;

    public ImpressionsAdapter(Context ctx, List<ImpressionsFields> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_impression, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImpressionsFields item = data.get(position);

        holder.tvExternalID.setText(Html.fromHtml("<b>ID:</b> "+item.getExternalID()));
        holder.tvAsessor.setText(Html.fromHtml("<b>Asesor:</b> "+item.getAsessorID()));
        holder.tvType.setText(Html.fromHtml("<b>Tipo:</b> "+item.getType()));
        holder.tvFolio.setText(Html.fromHtml("<b>Folio:</b> "+item.getFolio()));
        holder.tvAmount.setText(Html.fromHtml("<b>Monto:</b> "+"$"+item.getAmount()));
        holder.tvStatus.setText(Html.fromHtml("<b>Estatus:</b> "+((item.getStatus().equals("0"))?"Enviada":"No Enviada")));
        holder.tvGeneratedAt.setText(Html.fromHtml("<b>Impreso:</b> "+item.getGeneratedAt()));
        holder.tvSendedAt.setText(Html.fromHtml("<b>Enviado:</b> "+item.getSendedAt()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvExternalID;
        private TextView tvAsessor;
        private TextView tvType;
        private TextView tvFolio;
        private TextView tvAmount;
        private TextView tvStatus;
        private TextView tvGeneratedAt;
        private TextView tvSendedAt;

        public ViewHolder(View itemView) {
            super(itemView);
            tvExternalID    = itemView.findViewById(R.id.tvExternalId);
            tvAsessor       = itemView.findViewById(R.id.tvAsessor);
            tvType          = itemView.findViewById(R.id.tvType);
            tvFolio         = itemView.findViewById(R.id.tvFolio);
            tvAmount        = itemView.findViewById(R.id.tvAmount);
            tvStatus        = itemView.findViewById(R.id.tvStatus);
            tvGeneratedAt   = itemView.findViewById(R.id.tvGeneratedAt);
            tvSendedAt      = itemView.findViewById(R.id.tvSendedAt);
        }
    }
}
