package com.sidert.sidertmovil.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.MPrestamo;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class adapter_prestamos extends RecyclerView.Adapter<adapter_prestamos.ViewHolder> {

    private Context ctx;
    private List<MPrestamo> data;
    private Event evento;
    private SessionManager session;
    public interface Event {
        void ExpedientesClick(MPrestamo item);
        void PrestamoClick(MPrestamo item);
        void GestionadasClick(MPrestamo item);
        void CodigoOxxoClick(MPrestamo item);
    }

    public adapter_prestamos(Context ctx, List<MPrestamo> data, Event evento) {
        this.ctx = ctx;
        this.data = data;
        this.evento = evento;
        this.session = new SessionManager(ctx);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_prestamo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MPrestamo item = data.get(position);

        holder.tvNombre.setText(item.getNombre());
        holder.tvDesembolso.setText("Desembolso: "+item.getDesembolso());
        holder.tvMontoPrestamo.setText("Otorgado: "+Miscellaneous.moneyFormat(item.getMontoPrestamo()));
        holder.tvIdPrestamo.setText(item.getIdPrestamo());
        if (item.getEstatus().equals("1"))
            holder.tvPagado.setVisibility(View.VISIBLE);
        else
            holder.tvPagado.setVisibility(View.INVISIBLE);

        holder.tvSaldoMovil.setText("S Móvil: " + Miscellaneous.moneyFormat(item.getSaldoCorte()));
        holder.tvSaldoMovil.setTextColor(ctx.getResources().getColor(R.color.shadowdrawer));
        holder.tvSaldoOmega.setText("S Omega: " + Miscellaneous.moneyFormat(item.getSaldoOmega()));

        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNombre;
        private TextView tvDesembolso;
        private TextView tvMontoPrestamo;
        private TextView tvIdPrestamo;
        private TextView tvSaldoMovil;
        private TextView tvSaldoOmega;
        private TextView tvPagado;
        public ViewHolder(@NonNull View v) {
            super(v);
            tvNombre        = v.findViewById(R.id.tvNombre);
            tvDesembolso    = v.findViewById(R.id.tvDesembolso);
            tvMontoPrestamo = v.findViewById(R.id.tvMontoPrestamo);
            tvIdPrestamo    = v.findViewById(R.id.tvIdPrestamo);
            tvSaldoMovil    = v.findViewById(R.id.tvSaldoMovil);
            tvSaldoOmega    = v.findViewById(R.id.tvSaldoOmega);
            tvPagado        = v.findViewById(R.id.tvPagada);
        }

        public void bind (final MPrestamo item_prestamo){

                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View view) {
                            PopupMenu popup = new PopupMenu(view.getContext(), view);

                            try {
                                Field[] fields = popup.getClass().getDeclaredFields();
                                for (Field field : fields) {
                                    if (field.getName().equals("mPopup")) {
                                        field.setAccessible(true);
                                        Object menuPopupHelper = field.get(popup);
                                        Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                                        Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                                        setForceIcons.invoke(menuPopupHelper, true);
                                        break;
                                    }
                                }
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                            popup.getMenuInflater().inflate(R.menu.menu_prestamos, popup.getMenu());

                            int iIndexExpedientes = 0;
                            int iIndexRecuperacion = 1;
                            int iIndexGestionadas = 2;
                            int iIndexCodigosOxxo = 3;

                            if (item_prestamo.getEstatus().equals("1"))
                                popup.getMenu().getItem(iIndexRecuperacion).setEnabled(false);

                            Log.e("ROLE", session.getUser().get(5));
                            if (session.getUser().get(5).contains("ROLE_SUPER") ||
                                session.getUser().get(5).contains("ROLE_ANALISTA") ||
                                session.getUser().get(5).contains("ROLE_DIRECCION") ||
                                session.getUser().get(5).contains("ROLE_ASESOR") ||
                                session.getUser().get(5).contains("ROLE_GERENTESUCURSAL") ||
                                session.getUser().get(5).contains("ROLE_GERENTEREGIONAL")) {
                                if (session.getUser().get(5).contains("ROLE_GERENTESUCURSAL") ||
                                    session.getUser().get(5).contains("ROLE_GERENTEREGIONAL")){
                                    JSONArray modulos = null;
                                    try {
                                        modulos = new JSONArray(session.getUser().get(8));
                                        for (int i = 0; i < modulos.length(); i++){

                                            JSONObject item = modulos.getJSONObject(i);
                                            if (item.getString("nombre").trim().toLowerCase().equals("cartera")){
                                                JSONArray permisos = item.getJSONArray("permisos");
                                                for(int j = 0; j < permisos.length(); j++){
                                                    JSONObject item_permiso = permisos.getJSONObject(j);
                                                    if (item_permiso.getString("nombre").toLowerCase().equals("editar")){
                                                        popup.getMenu().getItem(iIndexCodigosOxxo).setVisible(true);
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }else
                                    popup.getMenu().getItem(iIndexCodigosOxxo).setVisible(true);

                                if (
                                        session.getUser().get(5).contains("ROLE_SUPER") ||
                                        session.getUser().get(5).contains("ROLE_ANALISTA") ||
                                        session.getUser().get(5).contains("ROLE_DIRECCION") ||
                                        session.getUser().get(5).contains("ROLE_GESTOR") ||
                                        session.getUser().get(5).contains("ROLE_GERENTECARTERAVENCIDA")
                                ) {
                                    popup.getMenu().getItem(iIndexExpedientes).setVisible(true);
                                }
                                else
                                {
                                    popup.getMenu().getItem(iIndexExpedientes).setVisible(false);
                                }

                            }

                            popup.setGravity(Gravity.RIGHT);
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    switch (item.getItemId()) {
                                        case R.id.nvExpedientes:
                                            evento.ExpedientesClick(item_prestamo);
                                            return true;
                                        case R.id.nvRecuperacion:
                                            evento.PrestamoClick(item_prestamo);
                                            return true;
                                        case R.id.nvGestionadas:
                                            evento.GestionadasClick(item_prestamo);
                                            return true;
                                        case R.id.nvCodigoOxxo:
                                            evento.CodigoOxxoClick(item_prestamo);
                                            return true;
                                        default:
                                            return false;
                                    }
                                }
                            });
                            popup.show();
                        }
                    });

        }
    }
}
