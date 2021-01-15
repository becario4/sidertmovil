package com.sidert.sidertmovil.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.utils.Miscellaneous;

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
        if (item.containsKey(3)){
            if (item.get(3).equals("0"))
                holder.ivInfo.setVisibility(View.VISIBLE);
            else
                holder.ivInfo.setVisibility(View.GONE);
        }

        if (item.containsKey(7)){
            if (!item.get(7).trim().isEmpty()) {
                holder.tvComentario.setVisibility(View.VISIBLE);
                holder.tvComentario.setText(Miscellaneous.ucFirst(item.get(7)));
            }
            else {
                holder.tvComentario.setVisibility(View.GONE);
                holder.tvComentario.setText("");
            }
        }

        if (!item.get(4).trim().isEmpty()){
            holder.tvFechaTermino.setText("Terminó: "+item.get(4));
            holder.tvFechaTermino.setVisibility(View.VISIBLE);
        }
        else{
            holder.tvFechaTermino.setVisibility(View.GONE);
        }
        if (!item.get(5).trim().isEmpty()){
            holder.tvFechaEnvio.setText("Envío: "+item.get(5));
            holder.tvFechaEnvio.setVisibility(View.VISIBLE);
        }
        else {
            holder.tvFechaEnvio.setVisibility(View.GONE);
        }

        holder.ivInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx, "Formulario incompleto", Toast.LENGTH_SHORT).show();
            }
        });
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombre;
        private TextView tvComentario;
        private TextView tvFechaTermino;
        private TextView tvFechaEnvio;
        private ImageView ivInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre        = itemView.findViewById(R.id.tvNombre);
            tvComentario    = itemView.findViewById(R.id.tvComentario);
            tvFechaTermino  = itemView.findViewById(R.id.tvFechaTermino);
            tvFechaEnvio    = itemView.findViewById(R.id.tvFechaEnvio);
            ivInfo          = itemView.findViewById(R.id.ivInfo);
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
