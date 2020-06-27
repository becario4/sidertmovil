package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_integrantes;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MIntegrante;

import java.util.ArrayList;
import java.util.Objects;

import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;
import static com.sidert.sidertmovil.utils.Constants.ID_PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE_GRUPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_GPO_T;

public class Integrantes extends AppCompatActivity {

    private Context ctx;
    private Toolbar tbMain;

    private TextView tvNombreGpo;

    private adapter_integrantes adapter;
    private RecyclerView rvIntegrantes;

    private DBhelper dBhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integrantes);

        ctx = this;
        dBhelper = new DBhelper(ctx);
        tbMain = findViewById(R.id.tbMain);

        tvNombreGpo = findViewById(R.id.tvnombreGpo);

        rvIntegrantes = findViewById(R.id.rvIntegrantes);
        rvIntegrantes.setLayoutManager(new LinearLayoutManager(ctx));
        rvIntegrantes.setHasFixedSize(false);

        setSupportActionBar(tbMain);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvNombreGpo.setText(getIntent().getStringExtra(NOMBRE_GRUPO));
        GetIntegrantes(getIntent().getStringExtra(ID_PRESTAMO));
    }

    private void GetIntegrantes(String id_prestamo){
        Cursor row;

        if (ENVIROMENT)
            row = dBhelper.getRecords(TBL_MIEMBROS_GPO, " WHERE id_prestamo = ?", " ORDER BY tipo_integrante DESC", new String[]{id_prestamo});
        else
            row = dBhelper.getRecords(TBL_MIEMBROS_GPO_T, " WHERE id_prestamo = ?", " ORDER BY tipo_integrante DESC", new String[]{id_prestamo});

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
                item.setPrestamoId(row.getInt(15));
                data.add(item);
                row.moveToNext();
            }

            adapter = new adapter_integrantes(ctx, data, new adapter_integrantes.Event() {
                @Override
                public void IntegranteOnClick(MIntegrante item) {

                }
            });
            rvIntegrantes.setAdapter(adapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
