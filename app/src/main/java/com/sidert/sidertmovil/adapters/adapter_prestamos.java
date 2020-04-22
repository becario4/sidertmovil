package com.sidert.sidertmovil.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.MPrestamo;
import com.sidert.sidertmovil.utils.Miscellaneous;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class adapter_prestamos extends RecyclerView.Adapter<adapter_prestamos.ViewHolder> {

    private Context ctx;
    private List<MPrestamo> data;
    private Event evento;

    public interface Event {
        void PrestamoClick(MPrestamo item);
        void GestionadasClick(MPrestamo item);
    }

    public adapter_prestamos(Context ctx, List<MPrestamo> data, Event evento) {
        this.ctx = ctx;
        this.data = data;
        this.evento = evento;
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
        holder.tvMontoPrestamo.setText(Miscellaneous.moneyFormat(item.getMontoPrestamo()));
        holder.tvIdPrestamo.setText(item.getIdPrestamo());
        if (item.getEstatus().equals("1")) {
            holder.tvEstatus.setText("PAGADO");
            holder.tvEstatus.setTextColor(ctx.getResources().getColor(R.color.green));
        }
        else{
            holder.tvEstatus.setText(Miscellaneous.moneyFormat(item.getMontoRestante()));
            holder.tvEstatus.setTextColor(ctx.getResources().getColor(R.color.shadowdrawer));
        }



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
        private TextView tvEstatus;
        public ViewHolder(@NonNull View v) {
            super(v);
            tvNombre = v.findViewById(R.id.tvNombre);
            tvDesembolso = v.findViewById(R.id.tvDesembolso);
            tvMontoPrestamo = v.findViewById(R.id.tvMontoPrestamo);
            tvIdPrestamo = v.findViewById(R.id.tvIdPrestamo);
            tvEstatus = v.findViewById(R.id.tvEstatus);
        }

        public void bind (final MPrestamo item_prestamo){

                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View view) {
                            PopupMenu popup = new PopupMenu(view.getContext(), view);
                            try {
                                // Reflection apis to enforce show icon
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
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            popup.getMenuInflater().inflate(R.menu.menu_prestamos, popup.getMenu());
                            if (item_prestamo.getEstatus().equals("1"))
                                popup.getMenu().getItem(0).setEnabled(false);
                            popup.getMenu().getItem(2).setEnabled(false);
                            popup.setGravity(Gravity.RIGHT);
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    switch (item.getItemId()) {
                                        case R.id.nvRecuperacion:
                                            evento.PrestamoClick(item_prestamo);
                                            return true;
                                        case R.id.nvGestionadas:
                                            evento.GestionadasClick(item_prestamo);
                                            return true;
                                        case R.id.nvGeolocalizacion:
                                            Toast.makeText(view.getContext(), "Estamos trabajando....", Toast.LENGTH_LONG).show();
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
