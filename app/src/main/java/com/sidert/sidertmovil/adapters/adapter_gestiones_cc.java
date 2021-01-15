package com.sidert.sidertmovil.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.utils.Miscellaneous;

import java.util.HashMap;
import java.util.List;

public class adapter_gestiones_cc extends RecyclerView.Adapter<adapter_gestiones_cc.ViewHolder> {

    private Context ctx;
    private List<HashMap<Integer, String>> data;
    private Event evento;

    public interface Event{
        void FichaOnClick(HashMap<Integer, String> item);
    }

    public adapter_gestiones_cc(Context ctx, List<HashMap<Integer, String>> data, Event evento) {
        this.ctx = ctx;
        this.data = data;
        this.evento = evento;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_gestion_cc, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HashMap<Integer, String> item = data.get(position);

        holder.tvNombreUno.setText(item.get(2));
        if (Integer.parseInt(item.get(1)) == 2) {
            holder.tvNombreDos.setText(item.get(4));
            holder.tvNombreDos.setVisibility(View.VISIBLE);
        }
        else
            holder.tvNombreDos.setVisibility(View.GONE);

        if (item.get(5).equals("GESTIONADAS")){
            holder.llComplemento.setVisibility(View.VISIBLE);
            holder.tvMonto.setText((Html.fromHtml("<b>Monto:</b> "+Miscellaneous.moneyFormat(item.get(6)))));
            holder.tvFolio.setText((Html.fromHtml("<b>Folio:</b> "+item.get(8))));
            holder.tvMedioPago.setText((Html.fromHtml("<b>Medio Pago:</b> "+item.get(7))));
            holder.tvFechaTermino.setText((Html.fromHtml("<b>Fecha Término:</b> "+item.get(9))));
            holder.tvFechaEnvio.setText((Html.fromHtml("<b>Fecha Envío:</b> "+item.get(10))));

            Log.e("Folio", item.get(8));
            if (!Miscellaneous.validStr(item.get(8)).isEmpty() && !item.get(8).equals("0"))
                holder.tvFolio.setVisibility(View.VISIBLE);
            else
                holder.tvFolio.setVisibility(View.GONE);
        }

        //if (item.get(5).equals("PARCIAL"))
            holder.bind(item);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivTipo;
        private TextView tvNombreUno;
        private TextView tvNombreDos;
        private LinearLayout llComplemento;
        private TextView tvMonto;
        private TextView tvMedioPago;
        private TextView tvFolio;
        private TextView tvFechaTermino;
        private TextView tvFechaEnvio;

        public ViewHolder(@NonNull View v) {
            super(v);
            tvNombreUno = v.findViewById(R.id.tvNombreUno);
            tvNombreDos = v.findViewById(R.id.tvNombreDos);
            llComplemento = v.findViewById(R.id.llComplemento);
            tvMonto = v.findViewById(R.id.tvMonto);
            tvMedioPago = v.findViewById(R.id.tvMedioPago);
            tvFolio = v.findViewById(R.id.tvFolio);
            tvFechaTermino = v.findViewById(R.id.tvFechaTermino);
            tvFechaEnvio = v.findViewById(R.id.tvFechaEnvio);
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
}
