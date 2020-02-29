package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionViewTarget;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_originacion;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_originacion_gpo;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.NameFragments;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class SolicitudCreditoGpo extends AppCompatActivity implements dialog_originacion_gpo.OnCompleteListener {

    private Context ctx;

    private Toolbar TBmain;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private RecyclerView rvIntegrantes;
    private FloatingActionButton fabAgregar;

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
        setContentView(R.layout.activity_solicitud_credito_gpo);

        ctx = this;

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        TBmain = findViewById(R.id.TBmain);
        setSupportActionBar(TBmain);

        tvInfoCredito = findViewById(R.id.tvInfoCredito);
        rvIntegrantes = findViewById(R.id.rvIntegrantes);
        fabAgregar = findViewById(R.id.fabAgregar);

        rvIntegrantes.setLayoutManager(new LinearLayoutManager(ctx));
        rvIntegrantes.setHasFixedSize(false);


        tvInfoCredito.setOnClickListener(tvInfoCredito_OnClick);
        fabAgregar.setOnClickListener(fabAgregar_OnClick);

        if (getIntent().getBooleanExtra("is_new",true)) {
            is_edit = true;
            is_new = true;
            openInfoOriginacion();
        }
        else{
            is_new = false;
            id_solicitud = Long.parseLong(getIntent().getStringExtra("id_solicitud"));

            Cursor row = dBhelper.getRecords(Constants.DATOS_CREDITO_GPO_T, " WHERE id_solicitud = " + id_solicitud, "", null);
            row.moveToFirst();
            id_credito = Long.parseLong(row.getString(0));
            initComponents(row.getString(0));
        }
    }

    private View.OnClickListener tvInfoCredito_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openInfoOriginacion();
        }
    };

    private View.OnClickListener fabAgregar_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Cursor row = dBhelper.customSelect(Constants.DATOS_INTEGRANTES_GPO_T, "COUNT (cargo)", " WHERE id_credito = ?", "", new String[]{String.valueOf(id_credito)});
            row.moveToFirst();
            if (row.getInt(0) < 40) {
                Intent i_agregar_integrante = new Intent(ctx, AgregarIntegrante.class);
                Log.e("id_credito", "credito"+id_credito);
                i_agregar_integrante.putExtra("id_credito", String.valueOf(id_credito));
                startActivityForResult(i_agregar_integrante, Constants.REQUEST_CODE_ADD_INTEGRANTE);
            }
            else
                Toast.makeText(ctx, "Ha superado el límite de integrantes por grupo", Toast.LENGTH_SHORT).show();
        }
    };

    private void openInfoOriginacion() {
        dialog_originacion_gpo originacion_gpo = new dialog_originacion_gpo();
        originacion_gpo.setCancelable(false);
        Bundle b = new Bundle();
        Log.e("is_edit", String.valueOf(is_edit));
        if (!is_edit) {
            b.putBoolean("is_edit", false);
            b.putString("nombre", nombre_gpo);
            b.putString("plazo", plazo);
            b.putString("periodicidad", periodicidad);
            b.putString("fecha_desembolso", fecha_desembolso);
            b.putString("dia_desembolso", dia_desembolso);
            b.putString("hora_visita", hora_visita);
            originacion_gpo.setArguments(b);
        }
        else
            b.putBoolean("is_edit", true);
        originacion_gpo.setArguments(b);
        originacion_gpo.show(getSupportFragmentManager(), NameFragments.DIALOGORIGINACIONGPO);
    }

    private void initComponents(String idSolicitud){
        Cursor row = dBhelper.getOriginacionCreditoGpo(idSolicitud);
        Log.e("count", "xxxxx"+row.getCount());
        row .moveToFirst();
        id_credito = row.getInt(12);
        nombre_gpo = row.getString(13);
        plazo = row.getString(14);
        periodicidad = row.getString(15);
        fecha_desembolso = row.getString(16);
        dia_desembolso = row.getString(17);
        hora_visita = row.getString(18);
        is_edit = false;

        row.close();

        Cursor row_integrantes = dBhelper.getRecords(Constants.DATOS_INTEGRANTES_GPO_T, " WHERE id_credito = ?", " ORDER BY nombre ASC", new String[]{String.valueOf(id_credito)});
        if (row_integrantes.getCount() > 0){
            row_integrantes.moveToFirst();
            ArrayList<HashMap<Integer,String>> data = new ArrayList<>();
            for(int i = 0; i < row_integrantes.getCount(); i++){
                HashMap<Integer, String> item = new HashMap();
                item.put(0,row_integrantes.getString(0));
                String nombre = row_integrantes.getString(3) + " " +row_integrantes.getString(4) + " " + row_integrantes.getString(5);
                item.put(1, nombre.trim().toUpperCase());
                item.put(2, row_integrantes.getString(1));
                data.add(item);
                row_integrantes.moveToNext();
            }

            adapter = new adapter_originacion(ctx, data, new adapter_originacion.Event() {
                @Override
                public void FichaOnClick(HashMap<Integer, String> item) {
                    Intent i_integrante = new Intent(ctx, AgregarIntegrante.class);
                    i_integrante.putExtra("is_new", false);
                    i_integrante.putExtra("id_integrante", item.get(0));
                    i_integrante.putExtra("id_credito", item.get(2));
                    startActivity(i_integrante);
                }
            });

            rvIntegrantes.setAdapter(adapter);
            //initSwipe();

        }
        row_integrantes.close();
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

                break;
            case R.id.enviar:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
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



    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!is_new)
            initComponents(String.valueOf(id_solicitud));
    }
}
