package com.sidert.sidertmovil.adapters;

import android.content.Context;
//import androidx.annotation.NonNull;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.ModeloCatalogoGral;

import java.util.List;

public class adapter_catalogos extends RecyclerView.Adapter<adapter_catalogos.ViewHolder> {

    private Context ctx;
    private List<ModeloCatalogoGral> data;
    private Event evento;

    public interface Event {
        void FichaOnClick(ModeloCatalogoGral item);
    }

    public adapter_catalogos(Context ctx, List<ModeloCatalogoGral> data, Event evento) {
        this.ctx = ctx;
        this.data = data;
        this.evento = evento;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_catalogo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ModeloCatalogoGral item = data.get(position);
        holder.tvNombre.setText(item.getNombre());
        holder.tvNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evento.FichaOnClick(item);
            }
        });

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
    }
}
