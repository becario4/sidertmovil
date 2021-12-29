package com.sidert.sidertmovil.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.utils.Miscellaneous;

import org.w3c.dom.Text;

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

            Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_person_blue)).into(holder.ivTipo);

            //if (!item.get(7).trim().isEmpty()) {
            Log.e("AQUI SOLICITUD", item.get(7).trim().toUpperCase());
            Log.e("AQUI SOLICITUD", String.valueOf((item.get(7).trim().toUpperCase().equals("AUTORIZADO"))));

            String comentario = Miscellaneous.ucFirst(item.get(7));

            if(item.get(7).trim().toUpperCase().equals("VALIDADO"))
            {
                holder.tvComentario.setTextColor(ctx.getResources().getColor(R.color.green));
            }
            else if(item.get(7).trim().toUpperCase().equals("EN REVISIÓN"))
            {
                holder.tvComentario.setTextColor(ctx.getResources().getColor(R.color.yellow));
            }
            else if(item.get(7).trim().toUpperCase().contains("NO AUTORIZADO"))
            {
                holder.tvComentario.setTextColor(ctx.getResources().getColor(R.color.red));
            }
            else if(!item.get(7).trim().toUpperCase().isEmpty())
            {
                holder.tvComentario.setTextColor(ctx.getResources().getColor(R.color.orange));
                comentario = "Editar";
            }

            if (item.containsKey(2)){
                if (item.get(2).equals("2") && comentario.equals("Editar")) comentario = "";
            }

            if(!comentario.trim().isEmpty())
            {
                holder.tvComentario.setVisibility(View.VISIBLE);
                holder.tvComentario.setText(comentario);
            }

            /*}
            else {
                holder.tvComentario.setVisibility(View.GONE);
                holder.tvComentario.setText("");
            }*/
        }
        else
        {
            Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_group_blue)).into(holder.ivTipo);
            holder.tvComentario.setVisibility(View.GONE);
            holder.tvComentario.setText("");
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

        if(item.containsKey(8))
        {
            holder.tvCargo.setVisibility(View.VISIBLE);

            switch (item.get(8)){
                case "1":
                    holder.tvCargo.setText(ctx.getResources().getString(R.string.presidente).toUpperCase());
                    break;
                case "3":
                    holder.tvCargo.setText(ctx.getResources().getString(R.string.tesorera).toUpperCase());
                    break;
                case "2":
                    holder.tvCargo.setText(ctx.getResources().getString(R.string.secretaria).toUpperCase());
                    break;
                case "4":
                    holder.tvCargo.setText(ctx.getResources().getString(R.string.integrante).toUpperCase());
                    break;
                default:
                    holder.tvCargo.setText("PENDIENTE DE ASIGNAR CARGO");
                    break;
            }
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
        private TextView tvCargo;
        private TextView tvNombre;
        private TextView tvComentario;
        private TextView tvFechaTermino;
        private TextView tvFechaEnvio;
        private ImageView ivInfo;
        private ImageView ivTipo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCargo         = itemView.findViewById(R.id.tvCargo);
            tvNombre        = itemView.findViewById(R.id.tvNombre);
            tvComentario    = itemView.findViewById(R.id.tvComentario);
            tvFechaTermino  = itemView.findViewById(R.id.tvFechaTermino);
            tvFechaEnvio    = itemView.findViewById(R.id.tvFechaEnvio);
            ivInfo          = itemView.findViewById(R.id.ivInfo);
            ivTipo          = itemView.findViewById(R.id.ivTipo);
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
