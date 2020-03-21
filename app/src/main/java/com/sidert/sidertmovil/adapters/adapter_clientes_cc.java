package com.sidert.sidertmovil.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.MTicketCC;

import java.util.List;

public class adapter_clientes_cc extends RecyclerView.Adapter<adapter_clientes_cc.ViewHolder> {

    private Context ctx;
    private List<MTicketCC> data;
    private Event evento;

    public interface Event {
        void ClienteOnClick(MTicketCC item);
    }

    public adapter_clientes_cc(Context ctx, List<MTicketCC> data, Event evento) {
        this.ctx = ctx;
        this.data = data;
        this.evento = evento;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_nombre, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MTicketCC item = data.get(position);
        holder.tvNombre.setText(item.getNombre_cliente());

        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombre;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
        }

        public void bind (final MTicketCC item){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    evento.ClienteOnClick(item);
                }
            });
        }
    }
}
