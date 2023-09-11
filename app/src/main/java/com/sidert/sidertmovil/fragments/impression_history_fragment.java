package com.sidert.sidertmovil.fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
/*import androidx.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;*/
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.ResumenImpresiones;
import com.sidert.sidertmovil.adapters.ImpressionsAdapter;
import com.sidert.sidertmovil.adapters.adapter_reimpresiones;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.ImpressionsFields;
import com.sidert.sidertmovil.models.Reimpresion;

import java.util.ArrayList;
import java.util.HashMap;

import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VENCIDA_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_IMPRESIONES_VIGENTE_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_REIMPRESION_VIGENTE_T;
import static com.sidert.sidertmovil.utils.Constants.TIPO;


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

    private boolean isVisible = false;

    private AutoCompleteTextView aetFolio;
    private CheckBox cbInd;
    private CheckBox cbGpo;
    private CheckBox cbEnv;
    private CheckBox cbPen;

    private int tipoImpresion = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_impression_history, container, false);

        ctx = getContext();
        boostrap = (Home) getActivity();

        boostrap.setTitle("Historial de impresiones");
        dbHelper = DBhelper.getInstance(ctx);
        db = dbHelper.getReadableDatabase();
        rbVigente = view.findViewById(R.id.rbVigente);
        rbVencida = view.findViewById(R.id.rbVencida);
        rbReimpresion = view.findViewById(R.id.rbReimpresion);

        rvHistory = view.findViewById(R.id.rvLogImp);

        rvHistory.setLayoutManager(new LinearLayoutManager(ctx));
        rvHistory.setHasFixedSize(false);
        boostrap.invalidateOptionsMenu();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rbVigente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoImpresion = 1;
                isVisible = true;
                setHasOptionsMenu(true);
                FillAdapterVigente("");
            }
        });

        rbVencida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoImpresion = 2;
                isVisible = true;
                setHasOptionsMenu(true);
                rvHistory.setAdapter(null);
                FillAdapterVencida("");
            }
        });

        rbReimpresion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoImpresion = 3;
                isVisible = true;
                setHasOptionsMenu(true);
                FillAdapterReimpresion("");
            }
        });

    }

    private void FillAdapterVigente(String where) {
        rvHistory.setAdapter(null);
        Cursor row;
        String sql = "";
        if (cbInd != null && cbGpo != null && cbInd.isChecked() && !cbGpo.isChecked()) {
            sql = "SELECT * FROM (SELECT ivi1.*,COALESCE(ci.nombre,'GUARDADO Y SINCRONIZADO') AS x, CASE (SELECT COUNT(*) FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS i2 WHERE i2.folio = ivi1.folio) WHEN 1 THEN 'O' ELSE 'O|C' END AS folios FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS ivi1 LEFT JOIN " +
                    TBL_PRESTAMOS_IND_T + " AS pi ON ivi1.num_prestamo = pi.num_prestamo LEFT JOIN " +
                    TBL_CARTERA_IND_T + " AS ci ON pi.id_cliente = ci.id_cartera WHERE ivi1.num_prestamo LIKE '%-L%' AND ivi1.tipo_impresion = (SELECT i3.tipo_impresion FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS i3 WHERE i3.folio = ivi1.folio ORDER BY i3.tipo_impresion DESC LIMIT 1)" +
                    ") AS impresiones " + where + " ORDER BY folio DESC";
        } else if (cbInd != null && cbGpo != null && !cbInd.isChecked() && cbGpo.isChecked()) {
            sql = "SELECT * FROM (SELECT ivi2.*, COALESCE(cg.nombre,'GUARDADO Y SINCRONIZADO') AS x, CASE(SELECT COUNT(*) FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS i2 WHERE i2.folio = ivi2.folio) WHEN 1 THEN 'O' ELSE 'O|C' END AS folios FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS ivi2 LEFT JOIN " +
                    TBL_PRESTAMOS_GPO_T + " AS pg ON ivi2.num_prestamo = pg.num_prestamo LEFT JOIN " +
                    TBL_CARTERA_GPO_T + " AS cg ON pg.id_grupo = cg.id_cartera WHERE ivi2.num_prestamo NOT LIKE '%-L%' AND ivi2.tipo_impresion = (SELECT i2.tipo_impresion FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS i2 WHERE i2.folio = ivi2.folio ORDER BY i2.tipo_impresion DESC LIMIT 1)) AS impresiones " + where + " ORDER BY folio DESC";
        } else {
            sql = "SELECT * FROM (SELECT ivi1.*,COALESCE(ci.nombre,'GUARDADO Y SINCRONIZADO') AS x, CASE (SELECT COUNT(*) FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS i2 WHERE i2.folio = ivi1.folio) WHEN 1 THEN 'O' ELSE 'O|C' END AS folios FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS ivi1 LEFT JOIN " +
                    TBL_PRESTAMOS_IND_T + " AS pi ON ivi1.num_prestamo = pi.num_prestamo LEFT JOIN " +
                    TBL_CARTERA_IND_T + " AS ci ON pi.id_cliente = ci.id_cartera WHERE ivi1.num_prestamo LIKE '%-L%' AND ivi1.tipo_impresion = (SELECT i3.tipo_impresion FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS i3 WHERE i3.folio = ivi1.folio ORDER BY i3.tipo_impresion DESC LIMIT 1)" +
                    " UNION SELECT ivi2.*, COALESCE(cg.nombre,'GUARDADO Y SINCRONIZADO') AS x, CASE(SELECT COUNT(*) FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS i2 WHERE i2.folio = ivi2.folio) WHEN 1 THEN 'O' ELSE 'O|C' END AS folios FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS ivi2 LEFT JOIN " +
                    TBL_PRESTAMOS_GPO_T + " AS pg ON ivi2.num_prestamo = pg.num_prestamo LEFT JOIN " +
                    TBL_CARTERA_GPO_T + " AS cg ON pg.id_grupo = cg.id_cartera WHERE ivi2.num_prestamo NOT LIKE '%-L%' AND ivi2.tipo_impresion = (SELECT i2.tipo_impresion FROM " + TBL_IMPRESIONES_VIGENTE_T + " AS i2 WHERE i2.folio = ivi2.folio ORDER BY i2.tipo_impresion DESC LIMIT 1)) AS impresiones " + where + " ORDER BY folio DESC";
        }

        row = db.rawQuery(sql, null);

        Log.e("CountImr", row.getCount() + " XD");
        if (row.getCount() > 0) {

            ArrayList<ImpressionsFields> obj = new ArrayList<>();
            row.moveToFirst();
            for (int i = 0; i < row.getCount(); i++) {
                ImpressionsFields item = new ImpressionsFields();
                item.setNombre(row.getString(12));
                item.setNumPrestamo(row.getString(10));
                item.setClave(row.getString(6));
                item.setAsesor(row.getString(2));
                item.setTipoImpresion(row.getString(13));
                item.setFolio(row.getString(3));
                item.setMonto(row.getString(5));
                item.setImpreso(row.getString(7));
                item.setEnviado(row.getString(8));

                obj.add(item);
                row.moveToNext();
            }
            if (obj.size() == 0) {
                Toast.makeText(ctx, "Sin contenido", Toast.LENGTH_SHORT).show();
            }

            adapter = new ImpressionsAdapter(ctx, obj);
            rvHistory.setAdapter(adapter);
        } else {
            Toast.makeText(ctx, "Sin contenido", Toast.LENGTH_SHORT).show();
        }
    }

    private void FillAdapterVencida(String where) {
        rvHistory.setAdapter(null);
        Cursor row;
        String sql = "";

        if (cbInd != null && cbGpo != null && cbInd.isChecked() && !cbGpo.isChecked())
            sql = "SELECT * FROM (SELECT ivi1._id, ivi1.num_prestamo_id_gestion, ivi1.asesor_id, ivi1.folio, ivi1.tipo_impresion, ivi1.monto, ivi1.clave_cliente, ivi1.create_at, ivi1.sent_at, ivi1.estatus, ivi1.num_prestamo, COALESCE(ci.nombre,'GUARDADO Y SINCRONIZADO') AS x, CASE (SELECT COUNT(*) FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS i2 WHERE i2.folio = ivi1.folio) WHEN 1 THEN 'O' ELSE 'O|C' END AS folios FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS ivi1 LEFT JOIN " +
                    TBL_PRESTAMOS_IND_T + " AS pi ON ivi1.num_prestamo = pi.num_prestamo LEFT JOIN " +
                    TBL_CARTERA_IND_T + " AS ci ON pi.id_cliente = ci.id_cartera WHERE ivi1.num_prestamo LIKE '%-L%' AND ivi1.tipo_impresion = (SELECT i3.tipo_impresion FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS i3 WHERE i3.folio = ivi1.folio ORDER BY i3.tipo_impresion DESC LIMIT 1)" +
                    ") AS impresiones " + where + " ORDER BY folio DESC";
        else if (cbInd != null && cbGpo != null && !cbInd.isChecked() && cbGpo.isChecked())
            sql = "SELECT * FROM (SELECT ivi2._id, ivi2.num_prestamo_id_gestion, ivi2.asesor_id, ivi2.folio, ivi2.tipo_impresion, ivi2.monto, ivi2.clave_cliente, ivi2.create_at, ivi2.sent_at, ivi2.estatus, ivi2.num_prestamo, COALESCE(cg.nombre,'GUARDADO Y SINCRONIZADO') AS x, CASE(SELECT COUNT(*) FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS i2 WHERE i2.folio = ivi2.folio) WHEN 1 THEN 'O' ELSE 'O|C' END AS folios FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS ivi2 LEFT JOIN " +
                    TBL_PRESTAMOS_GPO_T + " AS pg ON ivi2.num_prestamo = pg.num_prestamo LEFT JOIN " +
                    TBL_CARTERA_GPO_T + " AS cg ON pg.id_grupo = cg.id_cartera WHERE ivi2.num_prestamo NOT LIKE '%-L%' AND ivi2.tipo_impresion = (SELECT i2.tipo_impresion FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS i2 WHERE i2.folio = ivi2.folio ORDER BY i2.tipo_impresion DESC LIMIT 1)) AS impresiones " + where + " ORDER BY folio DESC";
        else
            sql = "SELECT * FROM (SELECT ivi1._id, ivi1.num_prestamo_id_gestion, ivi1.asesor_id, ivi1.folio, ivi1.tipo_impresion, ivi1.monto, ivi1.clave_cliente, ivi1.create_at, ivi1.sent_at, ivi1.estatus, ivi1.num_prestamo, COALESCE(ci.nombre,'GUARDADO Y SINCRONIZADO') AS x, CASE (SELECT COUNT(*) FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS i2 WHERE i2.folio = ivi1.folio) WHEN 1 THEN 'O' ELSE 'O|C' END AS folios FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS ivi1 LEFT JOIN " +
                    TBL_PRESTAMOS_IND_T + " AS pi ON ivi1.num_prestamo = pi.num_prestamo LEFT JOIN " +
                    TBL_CARTERA_IND_T + " AS ci ON pi.id_cliente = ci.id_cartera WHERE ivi1.num_prestamo LIKE '%-L%' AND ivi1.tipo_impresion = (SELECT i3.tipo_impresion FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS i3 WHERE i3.folio = ivi1.folio ORDER BY i3.tipo_impresion DESC LIMIT 1)" +
                    " UNION SELECT ivi2._id, ivi2.num_prestamo_id_gestion, ivi2.asesor_id, ivi2.folio, ivi2.tipo_impresion, ivi2.monto, ivi2.clave_cliente, ivi2.create_at, ivi2.sent_at, ivi2.estatus, ivi2.num_prestamo, COALESCE(cg.nombre,'GUARDADO Y SINCRONIZADO') AS x, CASE(SELECT COUNT(*) FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS i2 WHERE i2.folio = ivi2.folio) WHEN 1 THEN 'O' ELSE 'O|C' END AS folios FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS ivi2 LEFT JOIN " +
                    TBL_PRESTAMOS_GPO_T + " AS pg ON ivi2.num_prestamo = pg.num_prestamo LEFT JOIN " +
                    TBL_CARTERA_GPO_T + " AS cg ON pg.id_grupo = cg.id_cartera WHERE ivi2.num_prestamo NOT LIKE '%-L%' AND ivi2.tipo_impresion = (SELECT i2.tipo_impresion FROM " + TBL_IMPRESIONES_VENCIDA_T + " AS i2 WHERE i2.folio = ivi2.folio ORDER BY i2.tipo_impresion DESC LIMIT 1)) AS impresiones " + where + " ORDER BY folio DESC";

        Log.e("SQLVENCIDA", sql);
        row = db.rawQuery(sql, null);

        if (row.getCount() > 0) {

            ArrayList<ImpressionsFields> obj = new ArrayList<>();
            row.moveToFirst();
            for (int i = 0; i < row.getCount(); i++) {
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
                Toast.makeText(ctx, "Sin contenido", Toast.LENGTH_SHORT).show();
            }

            adapter = new ImpressionsAdapter(ctx, obj);
            rvHistory.setAdapter(adapter);
        } else {
            Toast.makeText(ctx, "Sin contenido", Toast.LENGTH_SHORT).show();
        }
    }

    private void FillAdapterReimpresion(String where) {
        rvHistory.setAdapter(null);
        Cursor row;
        String sql = "";
        if (cbInd != null && cbGpo != null && cbInd.isChecked() && !cbGpo.isChecked())
            sql = "SELECT * FROM (SELECT rim1._id, rim1.num_prestamo_id_gestion, rim1.tipo_reimpresion, rim1.folio, rim1.monto, rim1.clv_cliente, rim1.asesor_id, rim1.serie_id, rim1.create_at, rim1.sent_at, rim1.estatus, rim1.num_prestamo, ci.nombre, CASE (SELECT COUNT(*) FROM " + TBL_REIMPRESION_VIGENTE_T + " AS rim WHERE rim.folio = rim1.folio) WHEN 1 THEN 'RO' ELSE 'RO|RC' END AS folios FROM " + TBL_REIMPRESION_VIGENTE_T + " AS rim1 LEFT JOIN " +
                    TBL_PRESTAMOS_IND_T + " AS pi ON rim1.num_prestamo = pi.num_prestamo LEFT JOIN " +
                    TBL_CARTERA_IND_T + " AS ci ON pi.id_cliente = ci.id_cartera WHERE rim1.num_prestamo LIKE '%-L%' AND rim1.tipo_reimpresion = (SELECT i3.tipo_reimpresion FROM " + TBL_REIMPRESION_VIGENTE_T + " AS i3 WHERE i3.folio = rim1.folio ORDER BY i3.tipo_reimpresion DESC LIMIT 1)" +
                    ") AS impresiones " + where + " ORDER BY folio DESC";
        else if (cbInd != null && cbGpo != null && !cbInd.isChecked() && cbGpo.isChecked())
            sql = "SELECT * FROM (SELECT rim2._id, rim2.num_prestamo_id_gestion, rim2.tipo_reimpresion, rim2.folio, rim2.monto, rim2.clv_cliente, rim2.asesor_id, rim2.serie_id, rim2.create_at, rim2.sent_at, rim2.estatus, rim2.num_prestamo, cg.nombre, CASE(SELECT COUNT(*) FROM " + TBL_REIMPRESION_VIGENTE_T + " AS rim WHERE rim.folio = rim2.folio) WHEN 1 THEN 'RO' ELSE 'RO|RC' END AS folios FROM " + TBL_REIMPRESION_VIGENTE_T + " AS rim2 LEFT JOIN " +
                    TBL_PRESTAMOS_GPO_T + " AS pg ON rim2.num_prestamo = pg.num_prestamo LEFT JOIN " +
                    TBL_CARTERA_GPO_T + " AS cg ON pg.id_grupo = cg.id_cartera WHERE rim2.num_prestamo NOT LIKE '%-L%' AND rim2.tipo_reimpresion = (SELECT i2.tipo_reimpresion FROM " + TBL_REIMPRESION_VIGENTE_T + " AS i2 WHERE i2.folio = rim2.folio ORDER BY i2.tipo_reimpresion DESC LIMIT 1)) AS impresiones " + where + " ORDER BY folio DESC";
        else
            sql = "SELECT * FROM (SELECT rim1._id, rim1.num_prestamo_id_gestion, rim1.tipo_reimpresion, rim1.folio, rim1.monto, rim1.clv_cliente, rim1.asesor_id, rim1.serie_id, rim1.create_at, rim1.sent_at, rim1.estatus, rim1.num_prestamo, ci.nombre, CASE (SELECT COUNT(*) FROM " + TBL_REIMPRESION_VIGENTE_T + " AS rim WHERE rim.folio = rim1.folio) WHEN 1 THEN 'RO' ELSE 'RO|RC' END AS folios FROM " + TBL_REIMPRESION_VIGENTE_T + " AS rim1 LEFT JOIN " +
                    TBL_PRESTAMOS_IND_T + " AS pi ON rim1.num_prestamo = pi.num_prestamo LEFT JOIN " +
                    TBL_CARTERA_IND_T + " AS ci ON pi.id_cliente = ci.id_cartera WHERE rim1.num_prestamo LIKE '%-L%' AND rim1.tipo_reimpresion = (SELECT i3.tipo_reimpresion FROM " + TBL_REIMPRESION_VIGENTE_T + " AS i3 WHERE i3.folio = rim1.folio ORDER BY i3.tipo_reimpresion DESC LIMIT 1)" +
                    " UNION SELECT rim2._id, rim2.num_prestamo_id_gestion, rim2.tipo_reimpresion, rim2.folio, rim2.monto, rim2.clv_cliente, rim2.asesor_id, rim2.serie_id, rim2.create_at, rim2.sent_at, rim2.estatus, rim2.num_prestamo, cg.nombre, CASE(SELECT COUNT(*) FROM " + TBL_REIMPRESION_VIGENTE_T + " AS rim WHERE rim.folio = rim2.folio) WHEN 1 THEN 'RO' ELSE 'RO|RC' END AS folios FROM " + TBL_REIMPRESION_VIGENTE_T + " AS rim2 LEFT JOIN " +
                    TBL_PRESTAMOS_GPO_T + " AS pg ON rim2.num_prestamo = pg.num_prestamo LEFT JOIN " +
                    TBL_CARTERA_GPO_T + " AS cg ON pg.id_grupo = cg.id_cartera WHERE rim2.num_prestamo NOT LIKE '%-L%' AND rim2.tipo_reimpresion = (SELECT i2.tipo_reimpresion FROM " + TBL_REIMPRESION_VIGENTE_T + " AS i2 WHERE i2.folio = rim2.folio ORDER BY i2.tipo_reimpresion DESC LIMIT 1)) AS impresiones " + where + " ORDER BY folio DESC";

        row = db.rawQuery(sql, null);

        if (row.getCount() > 0) {

            ArrayList<Reimpresion> obj = new ArrayList<>();
            row.moveToFirst();
            for (int i = 0; i < row.getCount(); i++) {
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

            adapterReim = new adapter_reimpresiones(ctx, obj);
            rvHistory.setAdapter(null);
            rvHistory.setAdapter(adapterReim);
        } else {
            Toast.makeText(ctx, "Sin contenido", Toast.LENGTH_SHORT).show();
        }
    }

    private void Filtros() {
        int sizeH = 900;
        Activity activity = this.getActivity();
        if (activity != null) {
            View decorateView = activity.getWindow().getDecorView();
            sizeH = (int) (decorateView.getHeight() / 2.0);
        }

        DialogPlus filtros_dg = DialogPlus.newDialog(boostrap)
                .setContentHolder(new ViewHolder(R.layout.sheet_dialog_filtros_impresiones))
                .setGravity(Gravity.TOP)
                .setPadding(20, 10, 20, 10)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        String where = "";

                        HashMap<String, String> filtros = new HashMap<>();
                        InputMethodManager imm = (InputMethodManager) boostrap.getSystemService(Context.INPUT_METHOD_SERVICE);
                        int id = view.getId();
                        if (id == R.id.btnFiltrar) {
                            if (!aetFolio.getText().toString().trim().isEmpty()) {
                                where = " AND folio = '" + aetFolio.getText().toString().trim() + "'";
                            }

                            if (cbEnv.isChecked() && cbPen.isChecked()) {
                                where += " AND estatus IN ('1','0')";
                            } else if (cbEnv.isChecked()) {
                                where += " AND estatus = '1'";
                            } else if (cbPen.isChecked()) {
                                where += " AND estatus = '0'";
                            }


                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                            switch (tipoImpresion) {
                                case 1:
                                    if (where.length() > 4)
                                        FillAdapterVigente("WHERE" + where.substring(4));
                                    else
                                        FillAdapterVigente("");
                                    break;
                                case 2:
                                    if (where.length() > 4)
                                        FillAdapterVencida("WHERE" + where.substring(4));
                                    else
                                        FillAdapterVencida("");
                                    break;
                                case 3:
                                    if (where.length() > 4)
                                        FillAdapterReimpresion("WHERE" + where.substring(4));
                                    else
                                        FillAdapterReimpresion("");
                                    break;
                            }

                            dialog.dismiss();
                        } else if (id == R.id.btnBorrarFiltros) {
                            cbInd.setChecked(false);
                            cbGpo.setChecked(false);
                            cbEnv.setChecked(false);
                            cbPen.setChecked(false);
                            aetFolio.setText("");


                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            switch (tipoImpresion) {
                                case 1:
                                    FillAdapterVigente("");
                                    break;
                                case 2:
                                    FillAdapterVencida("");
                                    break;
                                case 3:
                                    FillAdapterReimpresion("");
                                    break;
                            }

                            //dialog.dismiss();
                        }

                    }
                })
                .setExpanded(true, sizeH)
                .create();
        aetFolio = filtros_dg.getHolderView().findViewById(R.id.aetFolio);
        cbInd = filtros_dg.getHolderView().findViewById(R.id.cbInd);
        cbGpo = filtros_dg.getHolderView().findViewById(R.id.cbGpo);
        cbEnv = filtros_dg.getHolderView().findViewById(R.id.cbEnv);
        cbPen = filtros_dg.getHolderView().findViewById(R.id.cbPen);

        filtros_dg.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        onResume();
        inflater.inflate(R.menu.menu_filtro, menu);

        final MenuItem menuItem = menu.findItem(R.id.nvFiltro);
        View actionView = MenuItemCompat.getActionView(menuItem);
        TextView tvContFiltros = actionView.findViewById(R.id.filtro_bagde);
        tvContFiltros.setVisibility(View.GONE);
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        for (int i = 0; i < menu.size(); i++)
            menu.getItem(i).setVisible(isVisible);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nvFiltro) {
            Filtros();
            return true;
        } else if (itemId == R.id.nvInfo) {
            Intent i_resumen_imp = new Intent(ctx, ResumenImpresiones.class);
            i_resumen_imp.putExtra(TIPO, tipoImpresion);
            startActivity(i_resumen_imp);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
