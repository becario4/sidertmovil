package com.sidert.sidertmovil.fragments.view_pager;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_ficha_contestadas;
import com.sidert.sidertmovil.adapters.adapter_fichas_pendientes;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_details_order;
import com.sidert.sidertmovil.fragments.orders_fragment;
import com.sidert.sidertmovil.models.MFichaContestada;
import com.sidert.sidertmovil.models.ModelFichaContestada;
import com.sidert.sidertmovil.models.ModeloFichaGeneral;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.NameFragments;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_T;

public class answers_fragment extends Fragment{

    private Context ctx;
    private Home boostrap;
    private RecyclerView rvFichas;
    private adapter_ficha_contestadas adapter;
    private List<MFichaContestada> _m_fichaContestada;

    private ModelFichaContestada mFichaContestada;
    private SQLiteDatabase db;
    private DBhelper dBhelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_answers, container, false);
        ctx = getContext();
        boostrap = (Home) getActivity();

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        rvFichas = view.findViewById(R.id.rvFichas);

        rvFichas.setLayoutManager(new LinearLayoutManager(ctx));
        rvFichas.setHasFixedSize(false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Cursor row;

        String query;

        if (ENVIROMENT)
            query = "SELECT * FROM (SELECT r._id AS id, p.id_prestamo, r.contacto AS estatus_gestion, r.fecha_fin AS timestamp, r.estatus AS estatus_ficha, p.fecha_establecida AS dia_pago, c.nombre, 'INDIVIDUAL' AS tipo_ficha, p.monto_requerido, r.pago_realizado, r.resultado_gestion FROM " + TBL_RESPUESTAS_IND + " AS r INNER JOIN "+ TBL_PRESTAMOS_IND + " AS p ON r.id_prestamo = p.id_prestamo INNER JOIN " + TBL_CARTERA_IND + " AS c ON p.id_cliente = c.id_cartera WHERE r.estatus > 0 UNION SELECT rg._id AS id, pg.id_prestamo, rg.contacto AS estatus_gestion, rg.fecha_fin AS timestamp, rg.estatus AS estatus_ficha, pg.fecha_establecida AS dia_pago, cg.nombre, 'GRUPAL' AS tipo_ficha, pg.monto_requerido, rg.pago_realizado,rg.resultado_gestion FROM "+ TBL_RESPUESTAS_GPO + " AS rg INNER JOIN " + TBL_PRESTAMOS_GPO + " AS pg ON rg.id_prestamo = pg.id_prestamo INNER JOIN " + TBL_CARTERA_GPO + " AS cg ON pg.id_grupo = cg.id_cartera WHERE rg.estatus > 0 ) AS contestadas ORDER BY timestamp DESC";
        else
            query = "SELECT * FROM (SELECT r._id AS id, p.id_prestamo, r.contacto AS estatus_gestion, r.fecha_fin AS timestamp, r.estatus AS estatus_ficha, p.fecha_establecida AS dia_pago, c.nombre, 'INDIVIDUAL' AS tipo_ficha, p.monto_requerido, r.pago_realizado, r.resultado_gestion FROM " + TBL_RESPUESTAS_IND_T + " AS r INNER JOIN "+ TBL_PRESTAMOS_IND_T + " AS p ON r.id_prestamo = p.id_prestamo INNER JOIN " + TBL_CARTERA_IND_T + " AS c ON p.id_cliente = c.id_cartera WHERE r.estatus > 0 UNION SELECT rg._id AS id, pg.id_prestamo, rg.contacto AS estatus_gestion, rg.fecha_fin AS timestamp, rg.estatus AS estatus_ficha, pg.fecha_establecida AS dia_pago, cg.nombre, 'GRUPAL' AS tipo_ficha, pg.monto_requerido, rg.pago_realizado,rg.resultado_gestion FROM "+ TBL_RESPUESTAS_GPO_T + " AS rg INNER JOIN " + TBL_PRESTAMOS_GPO_T + " AS pg ON rg.id_prestamo = pg.id_prestamo INNER JOIN " + TBL_CARTERA_GPO_T + " AS cg ON pg.id_grupo = cg.id_cartera WHERE rg.estatus > 0) AS contestadas ORDER BY timestamp DESC";

        row = db.rawQuery(query, null);

        if (row.getCount() > 0){
            _m_fichaContestada = new ArrayList<>();
            row.moveToFirst();
            for (int i = 0; i < row.getCount(); i++){
                MFichaContestada item = new MFichaContestada();
                item.setIdGestion(row.getString(0));
                item.setIdPrestamo(row.getString(1));
                item.setNombre(row.getString(6));
                item.setTipoFicha(row.getString(7));
                item.setEstatusFicha(row.getString(4));
                switch (row.getString(2)){
                    case "SI":
                        if (row.getString(10).equals("PAGO")){
                            if (row.getDouble(9) >= row.getDouble(8)){
                                item.setEstatusGestion("CERRADA");
                            }
                            else{
                                item.setEstatusGestion("ABIERTA");
                            }
                        }
                        else if (row.getString(10).equals("NO PAGO")){
                            item.setEstatusGestion("ABIERTA");
                        }
                        break;
                    case "NO":
                        item.setEstatusGestion("ABIERTA");
                        break;
                    case "ACLARACION":
                        item.setEstatusGestion(row.getString(2));
                        break;
                }

                item.setDiaPago(row.getString(5));
                item.setTimestamp(row.getString(3));
                _m_fichaContestada.add(item);
                row.moveToNext();
            }

            adapter = new adapter_ficha_contestadas(ctx, _m_fichaContestada);
            rvFichas.setAdapter(adapter);
        }
    }

}
