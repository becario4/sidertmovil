package com.sidert.sidertmovil.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.GeoIntegrante;
import com.sidert.sidertmovil.activities.GeolocalizacionGpo;
import com.sidert.sidertmovil.adapters.adapter_geo_miembros;
import com.sidert.sidertmovil.database.DBhelper;

import java.util.ArrayList;
import java.util.HashMap;

import static com.sidert.sidertmovil.utils.Constants.ID_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.NUM_SOLICITUD;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE;
import static com.sidert.sidertmovil.utils.Constants.REQUEST_CODE_MIEMBRO_GEO;
import static com.sidert.sidertmovil.utils.Constants.TBL_GEO_RESPUESTAS_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;

public class geo_miembros_fragment extends Fragment {

    private Context ctx;
    private GeolocalizacionGpo boostrap;

    private RecyclerView rvMiembros;

    private DBhelper dbHelper;
    private SQLiteDatabase db;

    private adapter_geo_miembros adapter;

    private String numSolicitud = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_geo_miembros, container, false);

        ctx = getContext();

        dbHelper = new DBhelper(ctx);
        db = dbHelper.getWritableDatabase();

        boostrap = (GeolocalizacionGpo) getActivity();

        rvMiembros = v.findViewById(R.id.rvMiembros);
        rvMiembros.setLayoutManager(new LinearLayoutManager(ctx));
        rvMiembros.setHasFixedSize(false);
        setHasOptionsMenu(true);

        numSolicitud = getArguments().getString(NUM_SOLICITUD);
        GetMiembros(numSolicitud);
        return v;
    }

    private void GetMiembros (String numSolicitud){
        Cursor row;

        String sql = "SELECT p.id_grupo, m.id_integrante, COALESCE(m.nombre, ''), COALESCE(m.direccion, ''), p.num_solicitud, COALESCE(gr.estatus, -1) FROM " + TBL_MIEMBROS_GPO_T + " AS m LEFT JOIN " + TBL_PRESTAMOS_GPO_T + " AS p ON p.id_prestamo = m.id_prestamo LEFT JOIN "+TBL_GEO_RESPUESTAS_T+" AS gr ON m.id_integrante = gr.id_integrante WHERE m.tipo_integrante = 'INTEGRANTE' AND p.num_solicitud = ?";

        row = db.rawQuery(sql, new String[]{numSolicitud});

        if (row.getCount() > 0){
            row.moveToFirst();
            ArrayList<HashMap<String, String >> _miembros = new ArrayList<>();
            for(int i = 0; i < row.getCount(); i++){
                HashMap<String, String> item = new HashMap<>();
                item.put("id_grupo", row.getString(0));
                item.put("id_integrante", row.getString(1));
                item.put("nombre", row.getString(2));
                item.put("direccion", row.getString(3));
                item.put("num_solicitud", row.getString(4));
                item.put("estatus", row.getString(5));

                _miembros.add(item);

                row.moveToNext();
            }

            adapter = new adapter_geo_miembros(ctx, _miembros, new adapter_geo_miembros.Event() {
                @Override
                public void ItemOnClick(HashMap<String, String> item) {
                    Intent i_miembro = new Intent(boostrap, GeoIntegrante.class);
                    i_miembro.putExtra(NUM_SOLICITUD, item.get("num_solicitud"));
                    i_miembro.putExtra(ID_INTEGRANTE, item.get("id_integrante"));
                    startActivityForResult(i_miembro, REQUEST_CODE_MIEMBRO_GEO);
                }
            });

            rvMiembros.setAdapter(adapter);

        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_MIEMBRO_GEO:
                GetMiembros(numSolicitud);
                break;
        }
    }
}
