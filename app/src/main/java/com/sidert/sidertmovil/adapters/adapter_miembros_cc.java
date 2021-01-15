package com.sidert.sidertmovil.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sidert.sidertmovil.R;

import java.util.HashMap;
import java.util.List;

public class adapter_miembros_cc extends RecyclerView.Adapter<adapter_miembros_cc.ViewHolder> {

    private Context ctx;
    private List<HashMap<Integer, String>> data;
    private Event evento;

    public interface Event {
        void ccOnClick(HashMap<Integer, String> item);
    }

    public adapter_miembros_cc(Context ctx, List<HashMap<Integer, String>> data, Event evento) {
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

        HashMap<Integer, String> item = data.get(position);

        holder.tvNombre.setText(item.get(1));
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNombre;
        public ViewHolder(@NonNull View iv) {
            super(iv);

            tvNombre = iv.findViewById(R.id.tvNombre);

        }

        public void bind (final HashMap<Integer, String> item){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    evento.ccOnClick(item);
                }
            });
        }
    }
}
