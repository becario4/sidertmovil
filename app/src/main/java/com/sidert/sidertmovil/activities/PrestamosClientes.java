package com.sidert.sidertmovil.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.database.CursorWindowCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_prestamos;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MPrestamo;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prestamos_clientes);

        ctx = this;

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();

        rvPrestamos = findViewById(R.id.rvPrestamos);

        rvPrestamos.setLayoutManager(new LinearLayoutManager(ctx));
        rvPrestamos.setHasFixedSize(false);

        Log.e("ID_cliente", getIntent().getStringExtra(ID_CARTERA));

        switch (getIntent().getStringExtra(TIPO)){
            case "INDIVIDUAL":
                GetPrestamosInd(getIntent().getStringExtra(ID_CARTERA));
                break;
            case "GRUPAL":
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

                mPrestamos.add(item);
                row.moveToNext();
            }
        }

        adatper = new adapter_prestamos(ctx, mPrestamos, new adapter_prestamos.Event() {
            @Override
            public void PrestamoClick(MPrestamo item) {
                Intent intent_order = new Intent(ctx, RecuperacionIndividual.class);
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
    protected void onResume() {
        super.onResume();
        Servicios_Sincronizado ss = new Servicios_Sincronizado();
        ss.SaveRespuestaGestion(ctx, false);
    }
}
