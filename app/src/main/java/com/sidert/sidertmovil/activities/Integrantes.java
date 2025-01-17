package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
//import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
/*import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;*/
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_integrantes;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.dialogs.dialog_imprimir_recibos;
import com.sidert.sidertmovil.models.MIntegrante;
import com.sidert.sidertmovil.views.expedientes.DocumentosIntegranteActivity;

import java.util.ArrayList;
import java.util.Objects;


import static com.sidert.sidertmovil.utils.Constants.ID_INTEGRANTE;
import static com.sidert.sidertmovil.utils.Constants.ID_PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE_GRUPO;
import static com.sidert.sidertmovil.utils.Constants.NUMERO_DE_PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_GPO_T;

/**Clase donde se puede visualizar los integrantes de un grupo*/
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
        dBhelper = DBhelper.getInstance(ctx);
        tbMain = findViewById(R.id.tbMain);

        tvNombreGpo = findViewById(R.id.tvnombreGpo);

        rvIntegrantes = findViewById(R.id.rvIntegrantes);
        rvIntegrantes.setLayoutManager(new LinearLayoutManager(ctx));
        rvIntegrantes.setHasFixedSize(false);

        setSupportActionBar(tbMain);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvNombreGpo.setText(getIntent().getStringExtra(NOMBRE_GRUPO));
        /**Obtiene los integrantes de acorde al id del prestamo*/
        GetIntegrantes(getIntent().getStringExtra(ID_PRESTAMO));
    }

    /**Funcion para obtener un listado de integrantes de un prestamo*/
    private void GetIntegrantes(String id_prestamo){

        /**getRecords es una funcion que recibe el
         * nombre de la tabla,
         * un condicional pede ser vacio,
         * un ordenamiento puede ser vacio,
         * y los valores a buscar*/
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
                item.setIdPrestamoIntegrante(row.getInt(15));
                data.add(item);
                row.moveToNext();
            }

            /**Se pasa el listado de integrantes al adaptador */
            adapter = new adapter_integrantes(ctx, data, item -> {
                Intent view = new Intent(this, DocumentosIntegranteActivity.class);
                view.putExtra(ID_PRESTAMO, String.valueOf(item.getIdPrestamoIntegrante()));
                view.putExtra(ID_INTEGRANTE, String.valueOf(item.getId()));
                view.putExtra(NOMBRE, item.getNombre());
                view.putExtra(NUMERO_DE_PRESTAMO, String.valueOf(item.getNumSolicitud()));
                view.putExtra(NOMBRE_GRUPO, tvNombreGpo.getText());
                startActivity(view);

                /*dialog_imprimir_recibos dialogRoot = new dialog_imprimir_recibos();
                Bundle b = new Bundle();
                b.putString(ID_INTEGRANTE, String.valueOf(item.getId()));
                b.putString(ID_PRESTAMO, String.valueOf(item.getPrestamoId()));
                dialogRoot.setArguments(b);
                dialogRoot.show(getSupportFragmentManager(), DIALOGIMPRIMIRRECIBOS);*/
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
