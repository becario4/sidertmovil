package com.sidert.sidertmovil.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.database.CursorWindowCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_prestamos;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MPrestamo;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;
import static com.sidert.sidertmovil.utils.Constants.EXTERNAL_ID;
import static com.sidert.sidertmovil.utils.Constants.FECHA_CREDITO_OTORGADO;
import static com.sidert.sidertmovil.utils.Constants.ID_CARTERA;
import static com.sidert.sidertmovil.utils.Constants.ID_PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.MONTO_AMORTIZACION;
import static com.sidert.sidertmovil.utils.Constants.MONTO_PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.SALDO_ACTUAL;
import static com.sidert.sidertmovil.utils.Constants.TBL_AMORTIZACIONES;
import static com.sidert.sidertmovil.utils.Constants.TBL_AMORTIZACIONES_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_NAME;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TIPO;
import static com.sidert.sidertmovil.utils.Constants.camara;

public class PrestamosClientes extends AppCompatActivity {

    private Context ctx;
    private Toolbar tbMain;
    private RecyclerView rvPrestamos;
    private adapter_prestamos adatper;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private int id_carteta;
    private int tipo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prestamos_clientes);

        ctx = this;

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        tbMain = findViewById(R.id.tbMain);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        rvPrestamos = findViewById(R.id.rvPrestamos);

        rvPrestamos.setLayoutManager(new LinearLayoutManager(ctx));
        rvPrestamos.setHasFixedSize(false);

        id_carteta = Integer.parseInt(getIntent().getStringExtra(ID_CARTERA));

        switch (getIntent().getStringExtra(TIPO)){
            case "INDIVIDUAL":
                tipo = 1;
                GetPrestamosInd(getIntent().getStringExtra(ID_CARTERA));
                break;
            case "GRUPAL":
                tipo = 2;
                GetPrestamosGpo(getIntent().getStringExtra(ID_CARTERA));
                break;
        }

    }

    private void GetPrestamosInd (String id_cliente){

        String nombre = "";
        Cursor row;
        if (ENVIROMENT)
            row = dBhelper.customSelect(TBL_PRESTAMOS_IND + " AS p", "c.nombre, p.*", " INNER JOIN "+TBL_CARTERA_IND+" AS c ON p.id_cliente = c.id_cartera WHERE p.id_cliente = ?", "", new String[]{id_cliente});
        else
            row = dBhelper.customSelect(TBL_PRESTAMOS_IND_T + " AS p", "c.nombre, p.*", " INNER JOIN "+TBL_CARTERA_IND_T+" AS c ON p.id_cliente = c.id_cartera WHERE p.id_cliente = ?", "", new String[]{id_cliente});
        row.moveToFirst();
        ArrayList<MPrestamo> mPrestamos = new ArrayList<>();
        if (row.getCount() > 0) {
            row.moveToFirst();

            Log.e("_idPrestamo",row.getString(1));

            for (int i = 0; i < row.getCount(); i++) {
                MPrestamo item = new MPrestamo();
                item.setId(row.getString(2));
                item.setNombre(row.getString(0));
                item.setDesembolso(row.getString(6));
                item.setMontoPrestamo(row.getString(7));
                item.setMontoRestante(row.getString(8));
                item.setMontoAmortiz(row.getString(9));
                item.setIdPrestamo(row.getString(4));
                item.setEstatus(row.getString(14));
                item.setTipo(1);

                Cursor rowSaldoCorte;
                if (ENVIROMENT)
                    rowSaldoCorte = dBhelper.customSelect(TBL_AMORTIZACIONES + " AS a", " SUM(total - total_pagado) AS saldo_corte", " WHERE id_prestamo = ?", "", new String[]{row.getString(2)});
                else
                    rowSaldoCorte = dBhelper.customSelect(TBL_AMORTIZACIONES_T + " AS a", " SUM(total - total_pagado) AS saldo_corte", " WHERE id_prestamo = ?", "", new String[]{row.getString(2)});

                if (rowSaldoCorte.getCount() > 0){
                    rowSaldoCorte.moveToFirst();
                    item.setSaldoCorte(rowSaldoCorte.getString(0));
                }
                rowSaldoCorte.close();

                mPrestamos.add(item);
                row.moveToNext();
            }
        }

        adatper = new adapter_prestamos(ctx, mPrestamos, new adapter_prestamos.Event() {
            @Override
            public void PrestamoClick(MPrestamo item) {
                Intent intent_order;
                intent_order = new Intent(ctx, RecuperacionIndividual.class);
                intent_order.putExtra(ID_PRESTAMO, item.getId());
                intent_order.putExtra(MONTO_AMORTIZACION, item.getMontoAmortiz());
                startActivity(intent_order);
            }

            @Override
            public void GestionadasClick(MPrestamo item) {
                Intent intent_order = new Intent(ctx, Gestionadas.class);
                intent_order.putExtra(ID_PRESTAMO, item.getId());
                if (ENVIROMENT)
                    intent_order.putExtra(TBL_NAME, TBL_RESPUESTAS_IND);
                else
                    intent_order.putExtra(TBL_NAME, TBL_RESPUESTAS_IND_T);
                startActivity(intent_order);
            }
        });
        rvPrestamos.setAdapter(adatper);
    }

    private void GetPrestamosGpo (String id_grupo){

        String nombre = "";
        Cursor row;
        if (ENVIROMENT)
            row = dBhelper.customSelect(TBL_PRESTAMOS_GPO + " AS p", "c.nombre, p.*", " INNER JOIN "+TBL_CARTERA_GPO+" AS c ON p.id_grupo = c.id_cartera WHERE p.id_grupo = ?", "", new String[]{id_grupo});
        else
            row = dBhelper.customSelect(TBL_PRESTAMOS_GPO_T + " AS p", "c.nombre, p.*", " INNER JOIN "+TBL_CARTERA_GPO_T+" AS c ON p.id_grupo = c.id_cartera WHERE p.id_grupo = ?", "", new String[]{id_grupo});
        row.moveToFirst();
        ArrayList<MPrestamo> mPrestamos = new ArrayList<>();
        if (row.getCount() > 0) {
            row.moveToFirst();

            for (int i = 0; i < row.getCount(); i++) {
                MPrestamo item = new MPrestamo();
                item.setId(row.getString(2));
                item.setNombre(row.getString(0));
                item.setDesembolso(row.getString(6));
                item.setMontoPrestamo(row.getString(7));
                item.setMontoRestante(row.getString(8));
                item.setMontoAmortiz(row.getString(9));
                item.setIdPrestamo(row.getString(4));
                item.setEstatus(row.getString(14));

                Cursor rowSaldoCorte;
                if (ENVIROMENT)
                    rowSaldoCorte = dBhelper.customSelect(TBL_AMORTIZACIONES + " AS a", " SUM(total - total_pagado) AS saldo_corte", " WHERE id_prestamo = ?", "", new String[]{row.getString(2)});
                else
                    rowSaldoCorte = dBhelper.customSelect(TBL_AMORTIZACIONES_T + " AS a", " SUM(total - total_pagado) AS saldo_corte", " WHERE id_prestamo = ?", "", new String[]{row.getString(2)});

                if (rowSaldoCorte.getCount() > 0){
                    rowSaldoCorte.moveToFirst();
                    item.setSaldoCorte(rowSaldoCorte.getString(0));
                }
                rowSaldoCorte.close();
                item.setTipo(2);

                mPrestamos.add(item);
                row.moveToNext();
            }
        }

        adatper = new adapter_prestamos(ctx, mPrestamos, new adapter_prestamos.Event() {
            @Override
            public void PrestamoClick(MPrestamo item) {
                Intent intent_order = new Intent(ctx, RecuperacionGrupal.class);
                intent_order.putExtra(ID_PRESTAMO, item.getId());
                intent_order.putExtra(MONTO_AMORTIZACION, item.getMontoAmortiz());
                startActivity(intent_order);
            }

            @Override
            public void GestionadasClick(MPrestamo item) {
                Intent intent_order = new Intent(ctx, Gestionadas.class);
                intent_order.putExtra(ID_PRESTAMO, item.getId());
                if (ENVIROMENT)
                    intent_order.putExtra(TBL_NAME, TBL_RESPUESTAS_GPO);
                else
                    intent_order.putExtra(TBL_NAME, TBL_RESPUESTAS_GPO_T);
                startActivity(intent_order);
            }
        });
        rvPrestamos.setAdapter(adatper);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_actualizar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.actualizar:
                Servicios_Sincronizado ss = new Servicios_Sincronizado();
                if (NetworkStatus.haveNetworkConnection(ctx))
                    ss.GetPrestamo(ctx, id_carteta, tipo);
                else{
                    final AlertDialog error_network = Popups.showDialogMessage(ctx, Constants.not_network,
                            R.string.not_network, R.string.accept, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    dialog.dismiss();
                                }
                            });
                    error_network.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    error_network.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    error_network.show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Servicios_Sincronizado ss = new Servicios_Sincronizado();
        ss.SaveRespuestaGestion(ctx, false);
    }
}
