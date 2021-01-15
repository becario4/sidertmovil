package com.sidert.sidertmovil.fragments.view_pager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.VencidaGrupal;
import com.sidert.sidertmovil.activities.VencidaIntegrante;
import com.sidert.sidertmovil.adapters.adapter_integrantes;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MIntegrante;

import java.util.ArrayList;

import static com.sidert.sidertmovil.utils.Constants.ID_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.ID_PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE_GRUPO;
import static com.sidert.sidertmovil.utils.Constants.NUMERO_DE_PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_GPO_T;


public class vencida_gpo_fragment extends Fragment {

    Context ctx;

    private adapter_integrantes adapter;
    private RecyclerView rvIntegrantes;

    private VencidaGrupal parent;

    private DBhelper dBhelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_vencida_gpo, container, false);

        ctx = getContext();
        dBhelper = new DBhelper(ctx);

        parent                = (VencidaGrupal) getActivity();
        assert parent != null;
        parent.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        rvIntegrantes = v.findViewById(R.id.rvIntegrantes);
        rvIntegrantes.setLayoutManager(new LinearLayoutManager(ctx));
        rvIntegrantes.setHasFixedSize(false);

        GetIntegrantes(parent.id_prestamo);

        return v;
    }

    private void GetIntegrantes(String id_prestamo){
        Cursor row = dBhelper.getRecords(TBL_MIEMBROS_GPO_T, " WHERE id_prestamo = ?", " ORDER BY tipo_integrante DESC", new String[]{id_prestamo});

        if (row.getCount() > 0){
            row.moveToFirst();
            ArrayList<MIntegrante> data = new ArrayList<>();
            for (int i = 0; i < row.getCount(); i++){
                MIntegrante item = new MIntegrante();
                item.setId(row.getInt(2));
                item.setNumSolicitud(row.getInt(3));
                item.setGrupoId(row.getInt(4));
                item.setNombre(row.getString(5));
                item.setDireccion(row.getString(6));
                item.setTelCasa(row.getString(7));
                item.setTelCelular(row.getString(8));
                item.setTipo(row.getString(9));
                item.setMontoPrestamo(row.getString(10));
                item.setMontoRequerido(row.getString(11));
                item.setPrestamoId(row.getInt(1));
                data.add(item);
                row.moveToNext();
            }

            adapter = new adapter_integrantes(ctx, data, new adapter_integrantes.Event() {
                @Override
                public void IntegranteOnClick(MIntegrante item) {
                    Intent view = new Intent(parent, VencidaIntegrante.class);
                    view.putExtra(ID_PRESTAMO, String.valueOf(item.getPrestamoId()));
                    view.putExtra(ID_INTEGRANTE, String.valueOf(item.getId()));
                    view.putExtra(NOMBRE, item.getNombre());
                    view.putExtra(NUMERO_DE_PRESTAMO, parent.num_prestamo);
                    view.putExtra(NOMBRE_GRUPO, parent.nombre);
                    startActivity(view);
                }
            });
            rvIntegrantes.setAdapter(adapter);
        }
    }
}
