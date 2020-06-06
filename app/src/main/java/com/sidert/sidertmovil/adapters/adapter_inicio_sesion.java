package com.sidert.sidertmovil.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.MLogLogin;
import com.sidert.sidertmovil.utils.Miscellaneous;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class adapter_inicio_sesion extends RecyclerView.Adapter<adapter_inicio_sesion.ViewHolder> {

    private Context ctx;
    private List<MLogLogin> data;

    public adapter_inicio_sesion(Context ctx, List<MLogLogin> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_inicio_sesion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MLogLogin item = data.get(position);

        if (Miscellaneous.validStr(item.getHorariologin()).isEmpty())
            holder.vEstatus.setBackgroundColor(ctx.getResources().getColor(R.color.red_push));
        else
            holder.vEstatus.setBackgroundColor(ctx.getResources().getColor(R.color.env_verde));

        holder.tvFechaLogin.setText(Miscellaneous.validStr(item.getHorariologin()).replace(" ", "\n"));

        if (!Miscellaneous.validStr(item.getNivelbateria()).isEmpty())
            holder.ivBateria.setVisibility(VISIBLE);
        else
            holder.ivBateria.setVisibility(GONE);

        if (!Miscellaneous.validStr(item.getVersionapp()).isEmpty())
            holder.ivVersion.setVisibility(VISIBLE);
        else
            holder.ivVersion.setVisibility(GONE);

        holder.tvNivelBateria.setText((!Miscellaneous.validStr(item.getNivelbateria()).isEmpty())?Miscellaneous.validStr(item.getNivelbateria())+"%":"");
        holder.tvVersionApp.setText(Miscellaneous.validStr(item.getVersionapp()));
        holder.tvNombre.setText(item.getNombreAsesor());
        holder.tvSucursal.setText(item.getSucursal());

        holder.tvPrimeraGestion.setText(Html.fromHtml((!Miscellaneous.validStr(item.getPrimeragestion()).isEmpty())?"<b>Primera Gestión: </b>"+item.getPrimeragestion():""));
        holder.tvUltimaGestion.setText(Html.fromHtml((!Miscellaneous.validStr(item.getUltimagestion()).isEmpty())?"<b>Última Gestión: </b>"+item.getUltimagestion():""));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View vEstatus;
        private TextView tvFechaLogin;
        private TextView tvNivelBateria;
        private TextView tvVersionApp;
        private TextView tvNombre;
        private TextView tvSucursal;
        private TextView tvPrimeraGestion;
        private TextView tvUltimaGestion;
        private ImageView ivBateria;
        private ImageView ivVersion;
        public ViewHolder(@NonNull View v) {
            super(v);
            vEstatus            = v.findViewById(R.id.vEstatus);
            tvFechaLogin        = v.findViewById(R.id.tvFechaLogin);
            tvNivelBateria      = v.findViewById(R.id.tvNivelBateria);
            tvVersionApp        = v.findViewById(R.id.tvVersionApp);
            tvNombre            = v.findViewById(R.id.tvNombre);
            tvSucursal          = v.findViewById(R.id.tvSucursal);
            tvPrimeraGestion    = v.findViewById(R.id.tvPrimeraGestion);
            tvUltimaGestion     = v.findViewById(R.id.tvUltimaGestion);
            ivBateria           = v.findViewById(R.id.ivBateria);
            ivVersion           = v.findViewById(R.id.ivVersion);
        }
    }
}
