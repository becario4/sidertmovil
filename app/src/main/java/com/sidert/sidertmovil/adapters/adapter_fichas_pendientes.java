package com.sidert.sidertmovil.adapters;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.MCartera;
import com.sidert.sidertmovil.models.MCarteraGnral;
import com.sidert.sidertmovil.models.ModeloFichaGeneral;
import com.sidert.sidertmovil.models.ModeloIndividual;
import com.sidert.sidertmovil.utils.Constants;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static com.sidert.sidertmovil.utils.Constants.RECUPERACION_IND;

public class adapter_fichas_pendientes extends RecyclerView.Adapter<adapter_fichas_pendientes.ViewHolder> {

    private Context ctx;
    private List<MCarteraGnral> data;
    private Event evento;

    public interface Event {
        void FichaOnClick(MCarteraGnral item);
        void IsRutaOnClick(MCarteraGnral item, boolean is_ruta);
    }

    public adapter_fichas_pendientes(Context ctx, List<MCarteraGnral> data, Event evento) {
        this.ctx = ctx;
        this.data = data;
        this.evento = evento;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_ficha, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MCarteraGnral item = data.get(position);
        if (item.getTipo().equals("INDIVIDUAL")) {
            Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_person_blue)).into(holder.ivTipoFicha);
            holder.tvNombreTesorera.setText("");
        }
        else {
            Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_group_blue)).into(holder.ivTipoFicha);
            holder.tvNombreTesorera.setText(item.getTesorera());
        }

        holder.tvDiaSemana.setText(item.getDiaSemana());
        holder.tvNombre.setText(item.getNombre());
        holder.tvDireccion.setText(item.getDireccion());

        holder.cbRuta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setIs_ruta(isChecked);
                evento.IsRutaOnClick(item, isChecked);

            }
        });
        holder.cbRuta.setChecked(item.isIs_ruta());

        Log.e("Enable", ""+item.isIs_obligatorio());
        holder.cbRuta.setEnabled(!item.isIs_obligatorio());

        holder.bind(item);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //private TextView tvNombre;
        //private TextView tvDireccion;
        //private TextView tvFechaPago;
        //private TextView tvDiaSemana;
        //private CheckBox cbRuta;
        //private ImageView ivTipoFicha;
        //private TextView tvStatus;
        private TextView tvNombre;
        private TextView tvNombreTesorera;
        private TextView tvDiaSemana;
        private TextView tvDireccion;

        private ImageView ivTipoFicha;
        private CheckBox cbRuta;


        public ViewHolder(@NonNull View v) {
            super(v);
            tvNombre    = v.findViewById(R.id.tvNombre);
            tvDireccion = v.findViewById(R.id.tvDireccion);
            tvDiaSemana = v.findViewById(R.id.tvDiaSemana);
            //tvDiaSemana = itemView.findViewById(R.id.tvDiaSemana);
            cbRuta      = v.findViewById(R.id.cbRuta);
            ivTipoFicha = v.findViewById(R.id.ivTipoFicha);
            tvNombreTesorera = v.findViewById(R.id.tvNombreTesorera);
            //tvStatus    = itemView.findViewById(R.id.tvStatus);
        }

        public void bind (final MCarteraGnral item){
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
                    popup.getMenuInflater().inflate(R.menu.menu_nav_geo_gpo, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.nvPresidente:
                                    Toast.makeText(view.getContext(), "Presidente", Toast.LENGTH_LONG).show();
                                    return true;
                                case R.id.nvTesorera:
                                    Toast.makeText(view.getContext(), "Tesorero ", Toast.LENGTH_LONG).show();
                                    return true;
                                case R.id.nvSecretaria:
                                    Toast.makeText(view.getContext(), "Secretario", Toast.LENGTH_LONG).show();
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    //popup.show();
                    evento.FichaOnClick(item);
                }
            });
        }
    }

    public void UpdateData(List<MCarteraGnral> _data){
        if (data != null)
            data.clear();
        data = _data;
        notifyDataSetChanged();
    }

}
