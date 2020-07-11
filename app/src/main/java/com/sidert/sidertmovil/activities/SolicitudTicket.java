package com.sidert.sidertmovil.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MSpiner;
import com.sidert.sidertmovil.models.MTickets;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;
import com.sidert.sidertmovil.utils.Validator;

import java.util.ArrayList;
import java.util.HashMap;

import static com.sidert.sidertmovil.utils.Constants.ID;
import static com.sidert.sidertmovil.utils.Constants.ESTATUS;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TICKETS;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.warning;

public class SolicitudTicket extends AppCompatActivity {

    private Context ctx;

    private Toolbar tbMain;

    private Spinner spCategoria;
    private Spinner spFichas;

    private LinearLayout llNombre;
    private LinearLayout llClienteGrupoId;
    private LinearLayout llNumSolicitud;
    private LinearLayout llFolio;
    private LinearLayout llComentario;

    private TextView tvCliGpoID;
    private TextView tvNumSolicitud;

    private EditText etFolio;
    private EditText etComentario;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private ArrayList<MTickets> _categorias = new ArrayList<>();
    private ArrayList<MSpiner> _fichas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_ticket);

        ctx = this;

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        tbMain  = findViewById(R.id.tbMain);

        spCategoria = findViewById(R.id.spCategoria);
        spFichas    = findViewById(R.id.spFichas);

        tvCliGpoID = findViewById(R.id.tvCliGpoID);
        tvNumSolicitud = findViewById(R.id.tvNumSolicitud);

        etFolio     = findViewById(R.id.etFolio);
        etComentario    = findViewById(R.id.etComentario);

        llNombre            = findViewById(R.id.llNombre);
        llClienteGrupoId    = findViewById(R.id.llClienteGrupoId);
        llNumSolicitud      = findViewById(R.id.llNumSolicitud);
        llFolio             = findViewById(R.id.llFolio);
        llComentario        = findViewById(R.id.llComentario);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Solicitud de Soporte");

        String sql = "SELECT * FROM " + TICKETS + " WHERE estatus = ? OR estatus = ?";
        Cursor row = db.rawQuery(sql, new String[]{"true","1"});

        Log.e("Row", String.valueOf(row.getCount()));
        MTickets cat = new MTickets();
        cat.setNombre("SELECCIONE UNA OPCIÓN");
        cat.setId(0);
        _categorias.add(cat);
        if (row.getCount() > 0){
            row.moveToFirst();
            for (int i = 0; i < row.getCount(); i++){
                Log.e("0", row.getString(0));
                Log.e("1", row.getString(1));
                Log.e("2", row.getString(2));
                Log.e("3", row.getString(3));
                cat = new MTickets();
                cat.setNombre(row.getString(2));
                cat.setId(row.getInt(1));
                _categorias.add(cat);
                row.moveToNext();
            }
        }
        row.close();
        ArrayAdapter<MTickets> adapter = new ArrayAdapter<>(ctx, android.R.layout.simple_list_item_1, _categorias);
        spCategoria.setAdapter(adapter);

        sql = "SELECT * FROM (SELECT id_cartera, nombre, 1 AS tipo FROM "+TBL_CARTERA_IND_T+" WHERE estatus = ? UNION SELECT id_cartera, nombre, 2 AS tipo FROM " +TBL_CARTERA_GPO_T+" WHERE estatus = ?) AS fichas ORDER BY nombre ASC";
        row = db.rawQuery(sql, new String[]{"1","1"});
        Log.e("Row", String.valueOf(row.getCount()));
        MSpiner ficha = new MSpiner();
        ficha.setNombre("SELECCIONE UNA OPCIÓN");
        ficha.setId(0);
        _fichas.add(ficha);
        if (row.getCount() > 0){
            row.moveToFirst();
            for (int i = 0; i < row.getCount(); i++){
                ficha = new MSpiner();
                ficha.setTipo(row.getInt(2));
                ficha.setNombre(row.getString(1));
                ficha.setId(row.getInt(0));
                _fichas.add(ficha);
                row.moveToNext();
            }
        }
        row.close();
        ArrayAdapter<MSpiner> adapter_fichas = new ArrayAdapter<>(ctx, android.R.layout.simple_list_item_1, _fichas);
        spFichas.setAdapter(adapter_fichas);

        spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                llNombre.setVisibility(View.GONE);
                llClienteGrupoId.setVisibility(View.GONE);
                llNumSolicitud.setVisibility(View.GONE);
                llFolio.setVisibility(View.GONE);
                llComentario.setVisibility(View.GONE);

                switch (((MTickets)spCategoria.getSelectedItem()).getId()){
                    case 1: //impresion
                        llNombre.setVisibility(View.VISIBLE);
                        llClienteGrupoId.setVisibility(View.VISIBLE);
                        llNumSolicitud.setVisibility(View.VISIBLE);
                        llFolio.setVisibility(View.VISIBLE);
                        llComentario.setVisibility(View.VISIBLE);
                        break;
                    case 2: //fichas pendientes
                    case 3: //cierre de aplicacion
                    case 4: //detalle con dispositivo
                        llComentario.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spFichas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0){
                    String id = String.valueOf(((MSpiner)spFichas.getSelectedItem()).getId());
                    int tipo = ((MSpiner)spFichas.getSelectedItem()).getTipo();
                    if (tipo == 1){
                        String sqlCartera = "SELECT p.id_cliente, p.num_solicitud FROM " + TBL_CARTERA_IND_T + " AS c INNER JOIN " + TBL_PRESTAMOS_IND_T + " AS p ON c.id_cartera = p.id_cliente WHERE c.id_cartera = ?";
                        Cursor rowCartera = db.rawQuery(sqlCartera, new String[]{id});
                        if (rowCartera.getCount() > 0){
                            rowCartera.moveToFirst();
                            tvCliGpoID.setText(rowCartera.getString(0));
                            tvNumSolicitud.setText(rowCartera.getString(1));
                        }
                        else {
                            tvCliGpoID.setText("");
                            tvNumSolicitud.setText("");
                        }
                        rowCartera.close();
                    }
                    else{
                        String sqlCartera = "SELECT p.id_grupo, p.num_solicitud FROM " + TBL_CARTERA_GPO_T + " AS c INNER JOIN " + TBL_PRESTAMOS_GPO_T + " AS p ON c.id_cartera = p.id_grupo WHERE c.id_cartera = ?";
                        Cursor rowCartera = db.rawQuery(sqlCartera, new String[]{id});
                        if (rowCartera.getCount() > 0){
                            rowCartera.moveToFirst();
                            tvCliGpoID.setText(rowCartera.getString(0));
                            tvNumSolicitud.setText(rowCartera.getString(1));
                        }
                        else {
                            tvCliGpoID.setText("");
                            tvNumSolicitud.setText("");
                        }

                        rowCartera.close();
                    }

                }
                else{
                    tvCliGpoID.setText("");
                    tvNumSolicitud.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void EnviarDatos() {
        Validator validator = new Validator();
        if (((MTickets)spCategoria.getSelectedItem()).getId() > 0){
            switch (((MTickets)spCategoria.getSelectedItem()).getId()){
                case 1:
                    if (((MSpiner)spFichas.getSelectedItem()).getId() > 0){
                        if (!validator.validate(etFolio, new String[]{validator.REQUIRED}) &&
                        !validator.validate(etComentario, new String[]{validator.REQUIRED})){
                            HashMap<Integer, String> params = new HashMap<>();
                            params.put(0, String.valueOf(((MTickets)spCategoria.getSelectedItem()).getId()));
                            params.put(1, String.valueOf(((MSpiner)spFichas.getSelectedItem()).getTipo()));
                            params.put(2, ((MSpiner)spFichas.getSelectedItem()).getNombre());
                            if (((MSpiner)spFichas.getSelectedItem()).getTipo() == 2) {
                                params.put(3, tvCliGpoID.getText().toString().trim());
                                params.put(4, "0");
                            }
                            else {
                                params.put(3, "0");
                                params.put(4, tvCliGpoID.getText().toString().trim());
                            }
                            params.put(5, tvNumSolicitud.getText().toString().trim());
                            params.put(6, etFolio.getText().toString().trim());
                            params.put(7, etComentario.getText().toString().toUpperCase().trim());
                            params.put(8, "");
                            params.put(9, "0");
                            params.put(10, "");
                            params.put(11, Miscellaneous.ObtenerFecha(TIMESTAMP));
                            params.put(12, "");
                            params.put(13, "PENDIENTE");
                            params.put(14, "0");

                            Long id = dBhelper.saveSoporte(db, params);

                            Servicios_Sincronizado ss = new Servicios_Sincronizado();
                            ss.SendSoporte(ctx, false);

                            Intent i_res = new Intent();
                            i_res.putExtra(ESTATUS, 1);
                            i_res.putExtra(ID, id);
                            setResult(Activity.RESULT_OK, i_res);
                            finish();
                        }
                    }
                    else
                        showMensajes("Seleccione el cliente o grupo afectado");
                    break;
                case 2:
                case 3:
                case 4:
                    if (!validator.validate(etComentario, new String[]{validator.REQUIRED})){
                        HashMap<Integer, String> params = new HashMap<>();
                        params.put(0, String.valueOf(((MTickets)spCategoria.getSelectedItem()).getId()));
                        params.put(1, "0");
                        params.put(2, "");
                        params.put(3, "0");
                        params.put(4, "0");
                        params.put(5, "0");
                        params.put(6, "0");
                        params.put(7, etComentario.getText().toString().toUpperCase().trim());
                        params.put(8, "");
                        params.put(9, "0");
                        params.put(10, "");
                        params.put(11, Miscellaneous.ObtenerFecha(TIMESTAMP));
                        params.put(12, "");
                        params.put(13, "PENDIENTE");
                        params.put(14, "0");

                        Long id = dBhelper.saveSoporte(db, params);

                        Servicios_Sincronizado ss = new Servicios_Sincronizado();
                        ss.SendSoporte(ctx, false);

                        Intent i_res = new Intent();
                        i_res.putExtra(ESTATUS, 1);
                        i_res.putExtra(ID, id);
                        setResult(Activity.RESULT_OK, i_res);
                        finish();
                    }
                    break;
            }


        }
        else
            showMensajes("Seleccione una categoria para definir el tipo de soporte");
    }

    private void showMensajes(String mess){
        AlertDialog success = Popups.showDialogMessage(ctx, warning,
                mess, R.string.accept, new Popups.DialogMessage() {
                    @Override
                    public void OnClickListener(AlertDialog dialog) {
                        dialog.dismiss();
                    }
                });
        success.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        success.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        success.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_enviar_datos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.enviar:
                EnviarDatos();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
