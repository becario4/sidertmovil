package com.sidert.sidertmovil.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.models.ModelGeolocalizacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class adapter_geo_pendientes extends RecyclerView.Adapter<adapter_geo_pendientes.ViewHolder> implements SectionIndexer {

    private Context ctx;
    private List<ModelGeolocalizacion> data;
    private ArrayList<Integer> mSectionPositions;
    private Event evento;


    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>(26);
        mSectionPositions = new ArrayList<>(26);
        for (int i = 0, size = data.size(); i < size; i++) {
            String section = String.valueOf(data.get(i).getNombre().charAt(0)).toUpperCase();
            if (!sections.contains(section)) {
                sections.add(section);
                mSectionPositions.add(i);
            }
        }
        return sections.toArray(new String[0]);
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return mSectionPositions.get(sectionIndex);
    }

    @Override
    public int getSectionForPosition(int position) {
        return mSectionPositions.get(position);
    }

    public interface Event {
        void GeoOnClick(ModelGeolocalizacion item, int modulo);
    }

    public adapter_geo_pendientes(Context ctx, List<ModelGeolocalizacion> data, Event evento) {
        this.ctx = ctx;
        this.data = data;
        this.evento = evento;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_geo_pendiente, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ModelGeolocalizacion item = data.get(position);

        holder.tvNombre.setText(item.getNombre());
        holder.tvDireccion.setText(item.getDireccion());

        if (item.getTipo_form() == 1){
            Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_person_blue)).into(holder.ivTipo);

            holder.tvTituloUno.setText(ctx.getResources().getString(R.string.cliente));
            holder.tvTituloDos.setText(ctx.getResources().getString(R.string.negocio));
            holder.tvTituloTres.setText(ctx.getResources().getString(R.string.aval));

            Drawable icon_uno = (item.getRes_uno().equals(""))?ctx.getResources().getDrawable(R.drawable.ic_home_red):ctx.getResources().getDrawable(R.drawable.ic_home_green);
            Glide.with(ctx).load(icon_uno).into(holder.ivResUno);

            Drawable icon_dos = (item.getRes_dos().equals(""))?ctx.getResources().getDrawable(R.drawable.ic_store_red):ctx.getResources().getDrawable(R.drawable.ic_store_green);
            Glide.with(ctx).load(icon_dos).into(holder.ivResDos);

            Drawable icon_tres = (item.getRes_tres().equals(""))?ctx.getResources().getDrawable(R.drawable.ic_person_red):ctx.getResources().getDrawable(R.drawable.ic_person_green);
            Glide.with(ctx).load(icon_tres).into(holder.ivResTres);

        }
        else {
            Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_group_blue)).into(holder.ivTipo);

            holder.tvTituloUno.setText(ctx.getResources().getString(R.string.presidente));
            holder.tvTituloDos.setText(ctx.getResources().getString(R.string.tesorera));
            holder.tvTituloTres.setText(ctx.getResources().getString(R.string.secretaria));

            Drawable icon_uno = (item.getRes_uno().equals(""))?ctx.getResources().getDrawable(R.drawable.ic_person_red):ctx.getResources().getDrawable(R.drawable.ic_person_green);
            Glide.with(ctx).load(icon_uno).into(holder.ivResUno);

            Drawable icon_dos = (item.getRes_dos().equals(""))?ctx.getResources().getDrawable(R.drawable.ic_person_red):ctx.getResources().getDrawable(R.drawable.ic_person_green);
            Glide.with(ctx).load(icon_dos).into(holder.ivResDos);

            Drawable icon_tres = (item.getRes_tres().equals(""))?ctx.getResources().getDrawable(R.drawable.ic_person_red):ctx.getResources().getDrawable(R.drawable.ic_person_green);
            Glide.with(ctx).load(icon_tres).into(holder.ivResTres);
        }
        holder.llResUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evento.GeoOnClick(item, 1);
            }
        });

        holder.llResDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evento.GeoOnClick(item, 2);
            }
        });

        holder.llResTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evento.GeoOnClick(item, 3);
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
        private TextView tvDireccion;
        private LinearLayout llResUno;
        private LinearLayout llResDos;
        private LinearLayout llResTres;
        private ImageView ivResUno;
        private ImageView ivResDos;
        private ImageView ivResTres;
        private ImageView ivTipo;
        private TextView tvTituloUno;
        private TextView tvTituloDos;
        private TextView tvTituloTres;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre     = itemView.findViewById(R.id.tvNombre);
            tvDireccion  = itemView.findViewById(R.id.tvDireccion);
            llResUno     = itemView.findViewById(R.id.llResUno);
            llResDos     = itemView.findViewById(R.id.llResDos);
            llResTres    = itemView.findViewById(R.id.llResTres);
            ivResUno     = itemView.findViewById(R.id.ivResUno);
            ivResDos     = itemView.findViewById(R.id.ivResDos);
            ivResTres    = itemView.findViewById(R.id.ivResTres);
            ivTipo       = itemView.findViewById(R.id.ivTipo);
            tvTituloUno  = itemView.findViewById(R.id.tvTituloUno);
            tvTituloDos  = itemView.findViewById(R.id.tvTituloDos);
            tvTituloTres = itemView.findViewById(R.id.tvTituloTres);
        }

        public void bind (final ModelGeolocalizacion item){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    evento.GeoOnClick(item, 1);
                }
            });
        }
    }

    public void UpdateData(List<ModelGeolocalizacion> _data){
        if (data != null)
            data.clear();
        data = _data;
        notifyDataSetChanged();
    }
}
