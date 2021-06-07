package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/*import android.support.design.widget.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;*/
import android.os.Bundle;
/*import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;*/
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_gestiones_cc;
import com.sidert.sidertmovil.adapters.adapter_impresiones_cc;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.circulocredito.GestionCirculoCredito;
import com.sidert.sidertmovil.models.circulocredito.GestionCirculoCreditoDao;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.views.circulocredito.CirculoCreditoActivity;

import java.util.ArrayList;
import java.util.HashMap;

import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS_CC;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECUPERACION_RECIBOS_CC;

/**Vista ver los cobros realizados de circulo de credito/impresiones de CC y gestiones de CC*/
public class CobrosCC extends AppCompatActivity {

    private Context ctx;

    private RecyclerView rvClientesCC;
    private FloatingActionButton fbAgregarCC;

    private RadioButton rbCobros;
    private RadioButton rbRecibos;
    private RadioButton rbGestiones;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private SessionManager session;

    private Toolbar tbMain;

    private int tipoSeccion = 1;

    private adapter_gestiones_cc adapter_cc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobros_c_c);

        ctx = this;

        tbMain  = findViewById(R.id.TBmain);

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();
        session = new SessionManager(ctx);

        rbCobros        = findViewById(R.id.rbCobrados);
        rbRecibos       = findViewById(R.id.rbRecibos);
        rbGestiones     = findViewById(R.id.rbGestiones);

        rvClientesCC = findViewById(R.id.rvClienteCC);
        fbAgregarCC  = findViewById(R.id.fbAgregar);

        rvClientesCC.setLayoutManager(new LinearLayoutManager(ctx));
        rvClientesCC.setHasFixedSize(false);

        fbAgregarCC.setOnClickListener(fbAgregarCC_OnClick);

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Solicitud de Soporte");

        invalidateOptionsMenu();

        /**Coloca por defecto en la vista de cobros parciales que no han sido guardados*/
        rbCobros.setChecked(true);

        /**Evento de click para mostrar cobros en parcial */
        rbCobros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoSeccion = 1;

                getCobros(" WHERE estatus <= '1'");

            }
        });

        /**Evento de click para ver las impresiones realizadas de CC*/
        rbRecibos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoSeccion = 2;
                getRecibos("");
            }
        });

        /**Evento de click para ver las gestiones realizadas de CC*/
        rbGestiones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipoSeccion = 3;

                getGestionados("");
            }
        });
    }

    /**Evento de click para abrir la vista de gestion de CC*/
    private View.OnClickListener fbAgregarCC_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i_cc = new Intent(getApplicationContext(), CirculoCreditoActivity.class);
            i_cc.putExtra("circulo_credito", new GestionCirculoCredito());
            startActivity(i_cc);
        }
    };

    /**Funcion para obtener las gestiones de recuperacion de CC en estado parcial*/
    private void getCobros( String where){
        rvClientesCC.setAdapter(null);
        String sql = "SELECT * FROM " + TBL_RECUPERACION_RECIBOS_CC + where;

        final Cursor row = db.rawQuery(sql, null);

        if (row.getCount() > 0){
            row.moveToFirst();
            ArrayList<HashMap<Integer, String>> gestiones = new ArrayList<>();
            for (int i = 0; i < row.getCount(); i++){
                HashMap<Integer, String> items = new HashMap<>();
                items.put(0, row.getString(0)); //_id
                items.put(1, row.getString(1)); //tipo_credito
                items.put(2, row.getString(2)); //nombre_uno
                items.put(3, row.getString(3)); //curp
                items.put(4, row.getString(4)); //nombre_dos
                items.put(5, "PARCIAL");       //tipo


                gestiones.add(items);
                row.moveToNext();
            }

            adapter_cc = new adapter_gestiones_cc(ctx, gestiones, new adapter_gestiones_cc.Event() {
                @Override
                public void FichaOnClick(HashMap<Integer, String> item) {
                    GestionCirculoCreditoDao gestionCirculoCreditoDao = new GestionCirculoCreditoDao(ctx);
                    GestionCirculoCredito gestionCC = gestionCirculoCreditoDao.findById(Integer.parseInt(item.get(0)));
                    if(gestionCC == null) gestionCC = new GestionCirculoCredito();

                    Intent i_cc = new Intent(getApplicationContext(), CirculoCreditoActivity.class);
                    i_cc.putExtra("terminado", false);
                    i_cc.putExtra("tipo_credito", item.get(1));
                    i_cc.putExtra("cliente_grupo", item.get(2));
                    i_cc.putExtra("aval_representante", item.get(4));
                    i_cc.putExtra("curp", item.get(3));
                    i_cc.putExtra("id_respuesta", Long.parseLong(item.get(0)));
                    i_cc.putExtra("circulo_credito", gestionCC);
                    startActivity(i_cc);
                }
            });
            rvClientesCC.setAdapter(adapter_cc);
        }
        row.close();

    }

    /**Funcion para obtener las impresiones realizadas de las gestiones de CC*/
    private void getRecibos( String where){
        rvClientesCC.setAdapter(null);
        //String sql = "SELECT r.nombre_uno, r.folio, r.fecha_impresion, r.fecha_envio, CASE(SELECT COUNT(*) FROM "+TBL_RECIBOS_CC+" AS r2 WHERE r2.folio = r.folio) WHEN 1 THEN 'O' ELSE 'O|C' END AS impresiones FROM "+TBL_RECUPERACION_RECIBOS_CC+" rr INNER JOIN "+TBL_RECIBOS_CC+" r ON rr.curp = r.curp AND rr.tipo_credito = r.tipo_credito WHERE r.tipo_impresion = (SELECT r3.tipo_impresion FROM "+TBL_RECIBOS_CC+" AS r3 WHERE r3.folio = r.folio ORDER BY r3.tipo_impresion DESC LIMIT 1) "+where+" ORDER BY r.fecha_impresion DESC";
        String sql = "SELECT * FROM " + TBL_RECIBOS_CC + " AS r ORDER BY fecha_impresion DESC";

        Cursor row = db.rawQuery(sql, null);

        Log.e("Recibos", row.getCount()+" Total");

        if (row.getCount() > 0){
            row.moveToFirst();

            ArrayList<HashMap<Integer,String>> data = new ArrayList<>();
            for(int i = 0; i < row.getCount(); i++){
                HashMap<Integer,String> ticketCC = new HashMap<>();

                ticketCC.put(0,(row.getInt(1) == 1)?"Individual":"Grupal"); //Tipo Credito
                ticketCC.put(1,row.getString(2)); //Nombre Cliente_Grupo
                ticketCC.put(2,row.getString(4)); //Nombre Aval_Repre
                ticketCC.put(3,row.getString(6)); //Monto
                ticketCC.put(4,row.getString(7)); //Tipo Impresion
                ticketCC.put(5,row.getString(8)); //Folio
                ticketCC.put(6,row.getString(9)); //Fecha Impreso
                ticketCC.put(7,row.getString(10)); //Fecha Envio

                data.add(ticketCC);
                row.moveToNext();
            }

            adapter_impresiones_cc adapter = new adapter_impresiones_cc(ctx, data);
            rvClientesCC.setAdapter(adapter);
        }
        row.close();
    }

    /**Funcion para obtener las gestiones ya completadas de CC*/
    private void getGestionados( String where){
        rvClientesCC.setAdapter(null);
        String sql = "SELECT rr.*, COALESCE(r1.folio, '') FROM " + TBL_RECUPERACION_RECIBOS_CC + " AS rr LEFT JOIN "+TBL_RECIBOS_CC+" AS r1 on r1.tipo_credito = rr.tipo_credito AND r1.curp = rr.curp AND r1.nombre_dos = rr.nombre_dos AND r1.tipo_impresion = 'O' WHERE rr.estatus in (1,2) "+where+" ORDER BY rr.fecha_termino DESC, rr.fecha_envio DESC";

        final Cursor row = db.rawQuery(sql, null);

        if (row.getCount() > 0){
            row.moveToFirst();
            ArrayList<HashMap<Integer, String>> prestamos = new ArrayList<>();
            for (int i = 0; i < row.getCount(); i++){

                HashMap<Integer, String> items = new HashMap<>();
                items.put(0, row.getString(0)); //_id
                items.put(1, row.getString(1)); //tipo_credito
                items.put(2, row.getString(2));  //nombre_uno
                items.put(3, row.getString(3));  //curp
                items.put(4, row.getString(4));  //nombre_dos
                items.put(5, "GESTIONADAS");       //tipo
                items.put(6, row.getString(6));  //monto
                items.put(7, row.getString(7)); //medio_pago
                items.put(8, row.getString(9)); //folio
                items.put(9, row.getString(12)); //fecha termino
                items.put(10, row.getString(13)); //fecha envio

                prestamos.add(items);
                row.moveToNext();
            }

            adapter_gestiones_cc adapter = new adapter_gestiones_cc(ctx, prestamos, new adapter_gestiones_cc.Event() {
                @Override
                public void FichaOnClick(HashMap<Integer, String> item) {
                    GestionCirculoCreditoDao gestionCirculoCreditoDao = new GestionCirculoCreditoDao(ctx);
                    GestionCirculoCredito gestionCC = gestionCirculoCreditoDao.findByCurp(item.get(3));
                    if(gestionCC == null) gestionCC = new GestionCirculoCredito();

                    Intent i_cc = new Intent(getApplicationContext(), CirculoCreditoActivity.class);
                    i_cc.putExtra("terminado", true);
                    i_cc.putExtra("tipo_credito", item.get(1));
                    i_cc.putExtra("cliente_grupo", item.get(2));
                    i_cc.putExtra("aval_representante", item.get(4));
                    i_cc.putExtra("curp", item.get(3));
                    i_cc.putExtra("id_respuesta", Long.parseLong(item.get(0)));
                    i_cc.putExtra("circulo_credito", gestionCC);
                   startActivityForResult(i_cc,RESULT_OK);

                }
            });
            rvClientesCC.setAdapter(adapter);
        }
        row.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:/**Menu de retroceso en el toolbar <- */
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**Funcion que se ejecuta cada vez que se entra a la vista y dependiendo
     * a check seleccionado es la lista que va a cargar*/
    @Override
    protected void onResume() {
        super.onResume();
        switch (tipoSeccion){
            case 1:/**Gestiones parciales*/
                getCobros(" WHERE estatus <= '1'");
                break;
            case 2:/**Impresiones*/
                getRecibos("");
                break;
            case 3:/**Gestionadas Terminadas*/
                getGestionados("");
                break;
        }


    }
}
