package com.sidert.sidertmovil.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_originacion;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_renovacion_gpo;
import com.sidert.sidertmovil.fragments.dialogs.dialog_solicitud_gpo;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static com.sidert.sidertmovil.utils.Constants.TBL_CREDITO_GPO_AUTO;
import static com.sidert.sidertmovil.utils.Constants.TBL_INTEGRANTES_GPO_AUTO;
import static com.sidert.sidertmovil.utils.Constants.TBL_OTROS_DATOS_INTEGRANTE_AUTO;
import static com.sidert.sidertmovil.utils.Constants.TBL_SOLICITUDES_AUTO;
import static com.sidert.sidertmovil.utils.Constants.warning;

public class SolicitudGpo extends AppCompatActivity {

    private Context ctx;

    private Toolbar TBmain;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private RecyclerView rvIntegrantes;

    private adapter_originacion adapter;

    private String nombre_gpo;
    private String plazo;
    private String periodicidad;
    private String fecha_desembolso;
    private String dia_desembolso;
    private String hora_visita;
    private boolean is_edit = false;

    private TextView tvInfoCredito;

    private long id_solicitud = 0;
    private long id_credito = 0;
    private boolean is_new = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_gpo);

        ctx = this;

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        TBmain = findViewById(R.id.TBmain);
        setSupportActionBar(TBmain);

        tvInfoCredito = findViewById(R.id.tvInfoCredito);
        rvIntegrantes = findViewById(R.id.rvIntegrantes);

        rvIntegrantes.setLayoutManager(new LinearLayoutManager(ctx));
        rvIntegrantes.setHasFixedSize(false);


        tvInfoCredito.setOnClickListener(tvInfoCredito_OnClick);


            id_solicitud = Long.parseLong(getIntent().getStringExtra("id_solicitud"));

            Cursor row = dBhelper.getRecords(TBL_CREDITO_GPO_AUTO, " WHERE id_solicitud = ?", "", new String[]{String.valueOf(id_solicitud)});
            row.moveToFirst();
            id_credito = row.getLong(0);
            initComponents(row.getString(1));
    }

    private View.OnClickListener tvInfoCredito_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openInfoOriginacion();
        }
    };

    private void openInfoOriginacion() {
        dialog_solicitud_gpo solicitud_gpo = new dialog_solicitud_gpo();
        solicitud_gpo.setCancelable(false);
        Bundle b = new Bundle();
        b.putBoolean("is_edit", false);
        b.putString("nombre", nombre_gpo);
        b.putString("plazo", plazo);
        b.putString("periodicidad", periodicidad);
        b.putString("fecha_desembolso", fecha_desembolso);
        b.putString("dia_desembolso", dia_desembolso);
        b.putString("hora_visita", hora_visita);

        solicitud_gpo.setArguments(b);
        solicitud_gpo.setArguments(b);
        solicitud_gpo.show(getSupportFragmentManager(), NameFragments.DIALOGORIGINACIONGPO);
    }

    private void initComponents(String idSolicitud){
        String sql =
                "SELECT c.*, s.estatus FROM " + TBL_CREDITO_GPO_AUTO + " AS c " +
                "INNER JOIN " + TBL_SOLICITUDES_AUTO + " AS s ON c.id_solicitud = s.id_solicitud " +
                "WHERE c.id_solicitud = ?";
        Cursor row = db.rawQuery(sql, new String[]{idSolicitud});
        //Cursor row = dBhelper.getRecords(TBL_CREDITO_GPO, " WHERE id_solicitud = ?", "",new String[]{idSolicitud});
        row .moveToFirst();
        id_credito = row.getInt(0);
        nombre_gpo = row.getString(2);
        plazo = row.getString(3);
        periodicidad = row.getString(4);
        fecha_desembolso = row.getString(5);
        dia_desembolso = row.getString(6);
        hora_visita = row.getString(7);

        is_edit = row.getInt(12) == 0;
        if (!is_edit)
            invalidateOptionsMenu();


        row.close();

        sql = "SELECT i.*, o.estatus_completado FROM " + TBL_INTEGRANTES_GPO_AUTO + " AS i " +
                "INNER JOIN " + TBL_OTROS_DATOS_INTEGRANTE_AUTO + " AS o ON o.id_integrante = i.id "+
                "WHERE i.id_credito = ? ORDER BY nombre ASC";
        Cursor row_integrantes = db.rawQuery(sql, new String[]{String.valueOf(id_credito)});
        //Cursor row_integrantes = dBhelper.getRecords(TBL_INTEGRANTES_GPO_AUTO, " WHERE id_credito = ?", " ORDER BY nombre ASC", new String[]{String.valueOf(id_credito)});
        if (row_integrantes.getCount() > 0){
            row_integrantes.moveToFirst();
            ArrayList<HashMap<Integer,String>> data = new ArrayList<>();
            for(int i = 0; i < row_integrantes.getCount(); i++){
                HashMap<Integer, String> item = new HashMap();
                item.put(0,row_integrantes.getString(0));
                String nombre = row_integrantes.getString(3) + " " +row_integrantes.getString(4) + " " + row_integrantes.getString(5);
                item.put(1, nombre.trim().toUpperCase());
                item.put(2, "2");
                item.put(3, row_integrantes.getString(23));
                item.put(4, "");
                item.put(5, "");
                item.put(6, row_integrantes.getString(1));
                item.put(7, row_integrantes.getString(20));
                data.add(item);
                row_integrantes.moveToNext();
            }

            adapter = new adapter_originacion(ctx, data, new adapter_originacion.Event() {
                @Override
                public void FichaOnClick(HashMap<Integer, String> item) {
                    Intent i_integrante = null;

                    if (i_integrante == null) {
                        i_integrante = new Intent(ctx, SolicitudIntegrante.class);
                        i_integrante.putExtra("is_new", false);
                        i_integrante.putExtra("id_integrante", item.get(0));
                        i_integrante.putExtra("id_credito", item.get(6));
                        i_integrante.putExtra("periodicidad", Miscellaneous.GetPeriodicidad(periodicidad));
                        i_integrante.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i_integrante);
                    }
                }
            });

            rvIntegrantes.setAdapter(adapter);

        }
        row_integrantes.close();

        if (!is_edit)
            invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_enviar_datos, menu);
        if (!is_edit)
        {
            for (int i = 0; i < menu.size(); i++)
                menu.getItem(i).setVisible(is_edit);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                break;
            case R.id.enviar:

                String sql = "SELECT " +
                        "SUM (CASE WHEN o.estatus_completado = 1 THEN 1 " +
                        "ELSE 0 END) AS completadas, " +
                        "SUM (CASE " +
                        "WHEN o.estatus_completado = 0 THEN 1 " +
                        "ELSE 0 END) AS pendientes FROM " + TBL_OTROS_DATOS_INTEGRANTE_AUTO + " AS o " +
                        "INNER JOIN " + TBL_INTEGRANTES_GPO_AUTO + " AS i ON i.id = o.id_integrante " +
                        "WHERE i.id_credito = ? GROUP BY id_credito";
                Cursor row_total = db.rawQuery(sql, new String[]{String.valueOf(id_credito)});

                //Cursor row_total = dBhelper.customSelect(TBL_OTROS_DATOS_INTEGRANTE_AUTO , "SUM (CASE WHEN estatus_completado = 1 THEN 1 ELSE 0 END) AS completadas, SUM (CASE WHEN estatus_completado = 0 THEN 1 ELSE 0 END) AS pendientes", " WHERE id_credito = ?"," GROUP BY id_credito", new String[]{String.valueOf(id_credito)});
                row_total.moveToFirst();

                if (row_total.getInt(1) == 0){
                    final AlertDialog fachada_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                            R.string.enviar_informacion, R.string.enviar, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    ContentValues cv = new ContentValues();
                                    cv.put("estatus_completado", 1);

                                    db.update(TBL_CREDITO_GPO_AUTO, cv, "id = ?", new String[]{String.valueOf(id_credito)});

                                    ContentValues cv_solicitud = new ContentValues();
                                    cv_solicitud.put("estatus", 1);
                                   // cv_solicitud.put("fecha_termino", Miscellaneous.ObtenerFecha("timestamp"));

                                    db.update(TBL_SOLICITUDES_AUTO, cv_solicitud, "id_solicitud = ?" , new String[]{String.valueOf(id_solicitud)});

                                    Servicios_Sincronizado ss = new Servicios_Sincronizado();
                                    ss.MontoAutorizado(ctx, false);

                                    dialog.dismiss();
                                    finish();

                                }
                            }, R.string.cancel, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    dialog.dismiss();
                                }
                            });
                    Objects.requireNonNull(fachada_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                    fachada_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    fachada_dlg.show();
                }
                else
                    Mensaje("Existen integrantes que no se ha especificado el monto autorizado.");

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Mensaje(String mess){
        final AlertDialog solicitud;
        solicitud = Popups.showDialogMessage(ctx, warning,
                mess, R.string.accept, new Popups.DialogMessage() {
                    @Override
                    public void OnClickListener(AlertDialog dialog) {
                        dialog.dismiss();
                    }
                });
        solicitud.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        solicitud.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        solicitud.show();
    }

    /*@Override
    public void onComplete(long id_solicitud, long id_credito, String nombre, String plazo, String periodicidad, String fecha, String dia, String hora) {
        if (id_solicitud > 0 && id_credito > 0) {
            is_new = false;
            this.id_solicitud = id_solicitud;
            this.id_credito = id_credito;
            nombre_gpo = nombre;
            this.plazo = plazo;
            this.periodicidad = periodicidad;
            this.fecha_desembolso = fecha;
            this.dia_desembolso = dia;
            this.hora_visita = hora;
            is_edit = false;
        }
        else if (nombre == null )
            finish();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        if (!is_new)
            initComponents(String.valueOf(id_solicitud));
    }
}
