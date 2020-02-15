package com.sidert.sidertmovil.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sidert.sidertmovil.R;

import java.util.ArrayList;
import java.util.HashMap;

public class adapter_originacion extends RecyclerView.Adapter<adapter_originacion.ViewHolder> {

    Context ctx;
    ArrayList<HashMap<Integer,String>> data;
    private Event evento;

    public interface Event {
        void FichaOnClick(HashMap<Integer,String> item);
    }

    public adapter_originacion(Context ctx, ArrayList<HashMap<Integer, String>> data, Event evento) {
        this.ctx = ctx;
        this.data = data;
        this.evento = evento;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_originacion, parent, false);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
        }

        public void bind (final HashMap<Integer, String> item){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    evento.FichaOnClick(item);
                }
            });
        }
    }

    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public HashMap<Integer, String> getItem(int position) {
        return data.get(position);
    }
}
