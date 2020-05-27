package com.sidert.sidertmovil.fragments;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.ImpressionsAdapter;
import com.sidert.sidertmovil.adapters.adapter_reimpresiones;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.ImpressionsFields;
import com.sidert.sidertmovil.models.MReimpresion;
import com.sidert.sidertmovil.models.Reimpresion;
import com.sidert.sidertmovil.utils.Constants;

import java.util.ArrayList;

import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VENCIDA;
import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VENCIDA_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VIGENTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VIGENTE_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_REIMPRESION;
import static com.sidert.sidertmovil.utils.Constants.TBL_REIMPRESION_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_REIMPRESION_VIGENTE;
import static com.sidert.sidertmovil.utils.Constants.TBL_REIMPRESION_VIGENTE_T;


public class impression_history_fragment extends Fragment {

    private Context ctx;
    private Home boostrap;

    private RadioButton rbVigente;
    private RadioButton rbVencida;
    private RadioButton rbReimpresion;

    private RecyclerView rvHistory;

    private ImpressionsAdapter adapter;
    private adapter_reimpresiones adapterReim;

    private DBhelper dbHelper;
    private SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_impression_history, container, false);

        ctx     = getContext();
        boostrap    = (Home) getActivity();

        boostrap.setTitle("Historial de impresiones");
        dbHelper = new DBhelper(ctx);
        db = dbHelper.getReadableDatabase();
        rbVigente     = view.findViewById(R.id.rbVigente);
        rbVencida    = view.findViewById(R.id.rbVencida);
        rbReimpresion    = view.findViewById(R.id.rbReimpresion);

        rvHistory   = view.findViewById(R.id.rvLogImp);

        rvHistory.setLayoutManager(new LinearLayoutManager(ctx));
        rvHistory.setHasFixedSize(false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rbVigente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FillAdapterVigente();
            }
        });

        rbVencida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvHistory.setAdapter(null);
                FillAdapterVencida();
            }
        });

        rbReimpresion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FillAdapterReimpresion();
            }
        });

    }

    private void FillAdapterVigente (){
        rvHistory.setAdapter(null);
        Cursor row;

        String sql;
        if(ENVIROMENT)
            sql = "SELECT * FROM (SELECT ivi1.*,COALESCE(ci.nombre,'GUARDADO Y SINCRONIZADO') AS x FROM " + TBL_IMPRESIONES_VIGENTE + " AS ivi1 LEFT JOIN " +
                    TBL_PRESTAMOS_IND + " AS pi ON ivi1.num_prestamo = pi.num_prestamo LEFT JOIN "+
                    TBL_CARTERA_IND + " AS ci ON pi.id_cliente = ci.id_cartera WHERE ivi1.num_prestamo LIKE '%-L%'" +
                    " UNION SELECT ivi2.*, COALESCE(cg.nombre,'GUARDADO Y SINCRONIZADO') AS x FROM " + TBL_IMPRESIONES_VIGENTE + " AS ivi2 LEFT JOIN " +
                    TBL_PRESTAMOS_GPO + " AS pg ON ivi2.num_prestamo = pg.num_prestamo LEFT JOIN " +
                    TBL_CARTERA_GPO + " AS cg ON pg.id_grupo = cg.id_cartera WHERE ivi2.num_prestamo NOT LIKE '%-L%') AS impresiones ORDER BY folio DESC";
        else
            sql = "SELECT * FROM (SELECT ivi1.*,COALESCE(ci.nombre,'GUARDADO Y SINCRONIZADO') AS x, CASE (SELECT COUNT(*) FROM "+ TBL_IMPRESIONES_VIGENTE_T + " AS i2 WHERE i2.folio = ivi1.folio) WHEN 1 THEN 'O' ELSE 'O|C' END AS folios FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS ivi1 LEFT JOIN " +
                    TBL_PRESTAMOS_IND_T + " AS pi ON ivi1.num_prestamo = pi.num_prestamo LEFT JOIN "+
                    TBL_CARTERA_IND_T + " AS ci ON pi.id_cliente = ci.id_cartera WHERE ivi1.num_prestamo LIKE '%-L%' AND ivi1.tipo_impresion = (SELECT i3.tipo_impresion FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS i3 WHERE i3.folio = ivi1.folio ORDER BY i3.tipo_impresion DESC LIMIT 1)" +
                    " UNION SELECT ivi2.*, COALESCE(cg.nombre,'GUARDADO Y SINCRONIZADO') AS x, CASE(SELECT COUNT(*) FROM "+ TBL_IMPRESIONES_VIGENTE_T + " AS i2 WHERE i2.folio = ivi2.folio) WHEN 1 THEN 'O' ELSE 'O|C' END AS folios FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS ivi2 LEFT JOIN " +
                    TBL_PRESTAMOS_GPO_T + " AS pg ON ivi2.num_prestamo = pg.num_prestamo LEFT JOIN " +
                    TBL_CARTERA_GPO_T + " AS cg ON pg.id_grupo = cg.id_cartera WHERE ivi2.num_prestamo NOT LIKE '%-L%' AND ivi2.tipo_impresion = (SELECT i2.tipo_impresion FROM "+TBL_IMPRESIONES_VIGENTE_T+" AS i2 WHERE i2.folio = ivi2.folio ORDER BY i2.tipo_impresion DESC LIMIT 1)) AS impresiones ORDER BY folio DESC";

        //sql = "SELECT *,'QVV' AS nombre, 'OC' AS folios from "+ TBL_IMPRESIONES_VIGENTE_T + " ORDER BY folio DESC";
        Log.e("SQLImpresiones", sql);
        row = db.rawQuery(sql, null);

       // row = dbHelper.getRecords(table,""," ORDER BY folio DESC",null);

        if (row.getCount() > 0){

            Log.e("xx", "xx"+row.getCount());
            ArrayList<ImpressionsFields> obj = new ArrayList<>();
            row.moveToFirst();
            for (int i = 0; i < row.getCount(); i++){
                ImpressionsFields item = new ImpressionsFields();
                item.setNombre(row.getString(11));
                item.setNumPrestamo(row.getString(10));
                item.setClave(row.getString(6));
                item.setAsesor(row.getString(2));
                item.setTipoImpresion(row.getString(12));
                item.setFolio(row.getString(3));
                item.setMonto(row.getString(5));
                item.setImpreso(row.getString(7));
                item.setEnviado(row.getString(8));

                obj.add(item);
                row.moveToNext();
            }
            if (obj.size() == 0) {
                Toast.makeText(ctx,"Sin contenido", Toast.LENGTH_SHORT).show();
            }

            adapter =  new ImpressionsAdapter(ctx,obj);
            rvHistory.setAdapter(adapter);
        }
        else {
            Toast.makeText(ctx,"Sin contenido", Toast.LENGTH_SHORT).show();
        }
    }

    private void FillAdapterVencida (){
        rvHistory.setAdapter(null);
        Cursor row;

        String sql;
        if(ENVIROMENT)
            sql = "SELECT * FROM (SELECT ivi1.*,COALESCE(ci.nombre,'GUARDADO Y SINCRONIZADO') AS x FROM " + TBL_IMPRESIONES_VIGENTE + " AS ivi1 LEFT JOIN " +
                    TBL_PRESTAMOS_IND + " AS pi ON ivi1.num_prestamo = pi.num_prestamo LEFT JOIN "+
                    TBL_CARTERA_IND + " AS ci ON pi.id_cliente = ci.id_cartera WHERE ivi1.num_prestamo LIKE '%-L%'" +
                    " UNION SELECT ivi2.*, COALESCE(cg.nombre,'GUARDADO Y SINCRONIZADO') AS x FROM " + TBL_IMPRESIONES_VIGENTE + " AS ivi2 LEFT JOIN " +
                    TBL_PRESTAMOS_GPO + " AS pg ON ivi2.num_prestamo = pg.num_prestamo LEFT JOIN " +
                    TBL_CARTERA_GPO + " AS cg ON pg.id_grupo = cg.id_cartera WHERE ivi2.num_prestamo NOT LIKE '%-L%') AS impresiones ORDER BY folio DESC";
        else
            sql = "SELECT * FROM (SELECT ivi1.*,COALESCE(ci.nombre,'GUARDADO Y SINCRONIZADO') AS x, CASE (SELECT COUNT(*) FROM "+ TBL_IMPRESIONES_VENCIDA_T + " AS i2 WHERE i2.folio = ivi1.folio) WHEN 1 THEN 'O' ELSE 'O|C' END AS folios FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS ivi1 LEFT JOIN " +
                    TBL_PRESTAMOS_IND_T + " AS pi ON ivi1.num_prestamo = pi.num_prestamo LEFT JOIN "+
                    TBL_CARTERA_IND_T + " AS ci ON pi.id_cliente = ci.id_cartera WHERE ivi1.num_prestamo LIKE '%-L%' AND ivi1.tipo_impresion = (SELECT i3.tipo_impresion FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS i3 WHERE i3.folio = ivi1.folio ORDER BY i3.tipo_impresion DESC LIMIT 1)" +
                    " UNION SELECT ivi2.*, COALESCE(cg.nombre,'GUARDADO Y SINCRONIZADO') AS x, CASE(SELECT COUNT(*) FROM "+ TBL_IMPRESIONES_VENCIDA_T + " AS i2 WHERE i2.folio = ivi2.folio) WHEN 1 THEN 'O' ELSE 'O|C' END AS folios FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS ivi2 LEFT JOIN " +
                    TBL_PRESTAMOS_GPO_T + " AS pg ON ivi2.num_prestamo = pg.num_prestamo LEFT JOIN " +
                    TBL_CARTERA_GPO_T + " AS cg ON pg.id_grupo = cg.id_cartera WHERE ivi2.num_prestamo NOT LIKE '%-L%' AND ivi2.tipo_impresion = (SELECT i2.tipo_impresion FROM "+TBL_IMPRESIONES_VENCIDA_T+" AS i2 WHERE i2.folio = ivi2.folio ORDER BY i2.tipo_impresion DESC LIMIT 1)) AS impresiones ORDER BY folio DESC";

        //sql = "SELECT *,'QVV' AS nombre, 'OC' AS folios from "+ TBL_IMPRESIONES_VIGENTE_T + " ORDER BY folio DESC";
        Log.e("SQLImpresiones", sql);
        row = db.rawQuery(sql, null);

        // row = dbHelper.getRecords(table,""," ORDER BY folio DESC",null);

        if (row.getCount() > 0){

            Log.e("xx", "xx"+row.getCount());
            ArrayList<ImpressionsFields> obj = new ArrayList<>();
            row.moveToFirst();
            for (int i = 0; i < row.getCount(); i++){
                ImpressionsFields item = new ImpressionsFields();
                item.setNombre(row.getString(11));
                item.setNumPrestamo(row.getString(10));
                item.setClave(row.getString(6));
                item.setAsesor(row.getString(2));
                item.setTipoImpresion(row.getString(12));
                item.setFolio(row.getString(3));
                item.setMonto(row.getString(5));
                item.setImpreso(row.getString(7));
                item.setEnviado(row.getString(8));

                obj.add(item);
                row.moveToNext();
            }
            if (obj.size() == 0) {
                Toast.makeText(ctx,"Sin contenido", Toast.LENGTH_SHORT).show();
            }

            adapter =  new ImpressionsAdapter(ctx,obj);
            rvHistory.setAdapter(adapter);
        }
        else {
            Toast.makeText(ctx,"Sin contenido", Toast.LENGTH_SHORT).show();
        }
    }

    private void FillAdapterReimpresion () {
        rvHistory.setAdapter(null);
        Cursor row;

        String sql;
        if(ENVIROMENT)
            sql = "SELECT * FROM (SELECT ivi1.*,COALESCE(ci.nombre,'GUARDADO Y SINCRONIZADO') AS x FROM " + TBL_IMPRESIONES_VIGENTE + " AS ivi1 LEFT JOIN " +
                    TBL_PRESTAMOS_IND + " AS pi ON ivi1.num_prestamo = pi.num_prestamo LEFT JOIN "+
                    TBL_CARTERA_IND + " AS ci ON pi.id_cliente = ci.id_cartera WHERE ivi1.num_prestamo LIKE '%-L%'" +
                    " UNION SELECT ivi2.*, COALESCE(cg.nombre,'GUARDADO Y SINCRONIZADO') AS x FROM " + TBL_IMPRESIONES_VIGENTE + " AS ivi2 LEFT JOIN " +
                    TBL_PRESTAMOS_GPO + " AS pg ON ivi2.num_prestamo = pg.num_prestamo LEFT JOIN " +
                    TBL_CARTERA_GPO + " AS cg ON pg.id_grupo = cg.id_cartera WHERE ivi2.num_prestamo NOT LIKE '%-L%') AS impresiones ORDER BY folio DESC";
        else
            sql = "SELECT * FROM (SELECT rim1.*,ci.nombre, CASE (SELECT COUNT(*) FROM "+ TBL_REIMPRESION_VIGENTE + " AS rim WHERE rim.folio = rim1.folio) WHEN 1 THEN 'RO' ELSE 'RO|RC' END AS folios FROM " + TBL_REIMPRESION_VIGENTE_T + " AS rim1 LEFT JOIN " +
                    TBL_PRESTAMOS_IND_T + " AS pi ON rim1.num_prestamo = pi.num_prestamo LEFT JOIN "+
                    TBL_CARTERA_IND_T + " AS ci ON pi.id_cliente = ci.id_cartera WHERE rim1.num_prestamo LIKE '%-L%' AND rim1.tipo_reimpresion = (SELECT i3.tipo_reimpresion FROM " + TBL_REIMPRESION_VIGENTE_T + " AS i3 WHERE i3.folio = rim1.folio ORDER BY i3.tipo_reimpresion DESC LIMIT 1)" +
                    " UNION SELECT rim2.*, cg.nombre, CASE(SELECT COUNT(*) FROM "+ TBL_REIMPRESION_VIGENTE_T + " AS rim WHERE rim.folio = rim2.folio) WHEN 1 THEN 'RO' ELSE 'RO|RC' END AS folios FROM " + TBL_REIMPRESION_VIGENTE_T + " AS rim2 LEFT JOIN " +
                    TBL_PRESTAMOS_GPO_T + " AS pg ON rim2.num_prestamo = pg.num_prestamo LEFT JOIN " +
                    TBL_CARTERA_GPO_T + " AS cg ON pg.id_grupo = cg.id_cartera WHERE rim2.num_prestamo NOT LIKE '%-L%' AND rim2.tipo_reimpresion = (SELECT i2.tipo_reimpresion FROM "+TBL_REIMPRESION_VIGENTE_T+" AS i2 WHERE i2.folio = rim2.folio ORDER BY i2.tipo_reimpresion DESC LIMIT 1)) AS impresiones ORDER BY folio DESC";

        //sql = "SELECT *,'nombre' as nombre, 'x' as folios from "+ TBL_REIMPRESION_VIGENTE_T;
        Log.e("SQLImpresiones", sql);
        row = db.rawQuery(sql, null);

        if (row.getCount() > 0){

            Log.e("xx", "xx"+row.getCount());
            ArrayList<Reimpresion> obj = new ArrayList<>();
            row.moveToFirst();
            for (int i = 0; i < row.getCount(); i++){
                Log.e("aaaa","aaa"+1);
                Reimpresion item = new Reimpresion();
                item.setNombre(row.getString(12));
                item.setNumPrestamo(row.getString(11));
                item.setClave(row.getString(5));
                item.setAsesor(row.getString(6));
                item.setTipoImpresion(row.getString(13));
                item.setFolio(row.getString(3));
                item.setMonto(row.getString(4));
                item.setImpreso(row.getString(8));
                item.setEnviado(row.getString(9));

                obj.add(item);
                row.moveToNext();
            }
            if (obj.size() == 0) {
                Toast.makeText(ctx, "Sin contenido", Toast.LENGTH_SHORT).show();
            }

            adapterReim =  new adapter_reimpresiones(ctx,obj);
            rvHistory.setAdapter(null);
            rvHistory.setAdapter(adapterReim);
        }
        else {
            Toast.makeText(ctx,"Sin contenido", Toast.LENGTH_SHORT).show();
        }
    }
}
