package com.sidert.sidertmovil.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sidert.sidertmovil.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public class adapter_prestamos_agf extends RecyclerView.Adapter<adapter_prestamos_agf.ViewHolder> {

    private Context ctx;
    private List<HashMap<Integer, String>> data;
    private Event evento;

    public interface Event {
        void AgfOnClick(HashMap<Integer, String> item);
        //void CcOnClick(HashMap<Integer, String> item);
    }

    public adapter_prestamos_agf(Context ctx, List<HashMap<Integer, String>> data, Event evento) {
        this.ctx = ctx;
        this.data = data;
        this.evento = evento;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_prestamo_agf_cc, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HashMap<Integer, String> item = data.get(position);

        holder.ivTipo.setVisibility(View.VISIBLE);

        if (Long.parseLong(item.get(1)) == 1) {
            holder.tvNombre.setText(item.get(8).substring(1));

                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_person_blue)).into(holder.ivTipo);
        }
        else {
            holder.tvNombre.setText(item.get(0));

                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_group_blue)).into(holder.ivTipo);
        }

        holder.bind(item);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNombre;
        private ImageView ivTipo;

        public ViewHolder(@NonNull View iv) {
            super(iv);

            tvNombre = iv.findViewById(R.id.tvNombre);
            ivTipo  = iv.findViewById(R.id.ivTipo);
        }

        public void bind (final HashMap<Integer, String> pres){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    evento.AgfOnClick(pres);
                    /*PopupMenu popup = new PopupMenu(view.getContext(), view);
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    popup.getMenuInflater().inflate(R.menu.menu_recibos, popup.getMenu());
                    popup.setGravity(Gravity.RIGHT);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.nvAGF:
                                    evento.AgfOnClick(pres);
                                    return true;
                                case R.id.nvCC:
                                    evento.CcOnClick(pres);
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    popup.show();*/
                }
            });
        }
    }
}
