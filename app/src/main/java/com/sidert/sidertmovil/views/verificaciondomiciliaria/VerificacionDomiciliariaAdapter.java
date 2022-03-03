package com.sidert.sidertmovil.views.verificaciondomiciliaria;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.verificacionesdomiciliarias.GestionVerificacionDomiciliaria;
import com.sidert.sidertmovil.models.verificacionesdomiciliarias.GestionVerificacionDomiciliariaDao;
import com.sidert.sidertmovil.models.verificacionesdomiciliarias.VerificacionDomiciliaria;
import com.sidert.sidertmovil.views.apoyogastosfunerarios.PrestamosAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VerificacionDomiciliariaAdapter extends RecyclerView.Adapter<VerificacionDomiciliariaAdapter.ViewHolder> {
    private Context ctx;
    private List<VerificacionDomiciliaria> data;
    private Event evento;

    public interface Event {
        void previewOnClick(VerificacionDomiciliaria verDom);
    }

    public VerificacionDomiciliariaAdapter(Context ctx, List<VerificacionDomiciliaria> data, Event evento) {
        this.ctx = ctx;
        this.data = data;
        this.evento = evento;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.preview_ver_dom, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VerificacionDomiciliaria verificacion = data.get(position);

        if(verificacion.getGrupoId() == 1)
        {
            holder.tvNombre.setText(verificacion.getClienteNombre());
            holder.tvtipo.setText((Html.fromHtml("<b>Tipo:</b> Individual")));
        }
        else
        {
            holder.tvNombre.setText(verificacion.getGrupoNombre());
            holder.tvtipo.setText((Html.fromHtml("<b>Tipo:</b> Grupal")));
        }

        //holder.tvMonto.setText((Html.fromHtml("<b>Monto:</b> "+ Miscellaneous.moneyFormat(verificacion.getMontoSolicitado()))));

        GestionVerificacionDomiciliariaDao gestionDao = new GestionVerificacionDomiciliariaDao(ctx);
        GestionVerificacionDomiciliaria gestion = gestionDao.findByVerificacionDomiciliariaId(verificacion.getVerificacionDomiciliariaId());

        if(gestion != null)
        {
            holder.tvFechaTermino.setVisibility(View.VISIBLE);
            holder.tvFechaEnvio.setVisibility(View.VISIBLE);

            holder.tvFechaTermino.setText((Html.fromHtml("<b>Fecha Término: </b> " + gestion.getFechaFin())));
            holder.tvFechaEnvio.setText((Html.fromHtml("<b>Fecha Envío</b> " + gestion.getFechaEnvio())));
        }

        holder.bind(verificacion);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombre;
        private TextView tvtipo;
        private TextView tvMonto;
        private TextView tvFechaTermino;
        private TextView tvFechaEnvio;

        public ViewHolder(@NonNull View iv) {
            super(iv);
            tvNombre        = iv.findViewById(R.id.tvNombre);
            tvtipo          = iv.findViewById(R.id.tvTipo);
            tvMonto         = iv.findViewById(R.id.tvMonto);
            tvFechaTermino  = iv.findViewById(R.id.tvFechaTermino);
            tvFechaEnvio    = iv.findViewById(R.id.tvFechaEnvio);
        }

        public void bind (final VerificacionDomiciliaria verDom){
            itemView.setOnClickListener(view -> evento.previewOnClick(verDom));
        }

    }
}
