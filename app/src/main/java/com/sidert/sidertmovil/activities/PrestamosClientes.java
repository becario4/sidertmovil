package com.sidert.sidertmovil.activities;

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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.adapter_prestamos;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.MPrestamo;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.NetworkStatus;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Servicios_Sincronizado;
import com.sidert.sidertmovil.utils.SessionManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.sidert.sidertmovil.utils.Constants.CLAVE;
import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;
import static com.sidert.sidertmovil.utils.Constants.FECHA;
import static com.sidert.sidertmovil.utils.Constants.FECHA_AMORTIZACION;
import static com.sidert.sidertmovil.utils.Constants.ID_CARTERA;
import static com.sidert.sidertmovil.utils.Constants.ID_PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.MONTO_AMORTIZACION;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE;
import static com.sidert.sidertmovil.utils.Constants.NUMERO_DE_PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.TBL_AMORTIZACIONES;
import static com.sidert.sidertmovil.utils.Constants.TBL_AMORTIZACIONES_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_NAME;
import static com.sidert.sidertmovil.utils.Constants.TBL_PAGOS_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_V_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_INTEGRANTE_T;
import static com.sidert.sidertmovil.utils.Constants.TIPO;
import static com.sidert.sidertmovil.utils.Constants.TIPO_GESTION;
import static com.sidert.sidertmovil.utils.Constants.TIPO_PRESTAMO;

public class PrestamosClientes extends AppCompatActivity {

    private Context ctx;
    private Toolbar tbMain;
    private RecyclerView rvPrestamos;
    private adapter_prestamos adatper;

    private DBhelper dBhelper;
    private SQLiteDatabase db;

    private int id_carteta;
    private int tipo;
    private SessionManager session;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private Calendar fi;
    private Calendar ff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prestamos_clientes);

        ctx = this;

        session = new SessionManager(ctx);

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

        Calendar c = Calendar.getInstance();

        c.set(Integer.parseInt(Miscellaneous.ObtenerFecha(FECHA.toLowerCase()).substring(0,4)),
                Integer.parseInt(Miscellaneous.ObtenerFecha(FECHA.toLowerCase()).substring(5,7))-1,
                 Integer.parseInt(Miscellaneous.ObtenerFecha(FECHA.toLowerCase()).substring(8,10)));
        int nD=(c.get(Calendar.DAY_OF_WEEK) == 1)?8:c.get(Calendar.DAY_OF_WEEK);

        fi = Calendar.getInstance();
        ff = Calendar.getInstance();

        fi.add(Calendar.DAY_OF_YEAR, -(nD -2));
        ff.add(Calendar.DAY_OF_YEAR, ((7-nD)+1));

        Log.e("FechaIni", sdf.format(fi.getTime()));
        Log.e("FechaFin", sdf.format(ff.getTime()));

    }

    private void GetPrestamosInd (String id_cliente){
        //String sql = "SELECT c.nombre, p.* FROM " + TBL_PRESTAMOS_IND_T + " AS p INNER JOIN " + TBL_CARTERA_IND_T + " AS c ON p.id_cliente = c.id_cartera WHERE p.id_cliente = ? AND pagada = 0 AND substr(p.fecha_actualizado,1,4)||substr(p.fecha_actualizado,6,2)||substr(p.fecha_actualizado,9,2) BETWEEN '"+sdf.format(fi.getTime()).replace("-","")+"' AND '"+sdf.format(ff.getTime()).replace("-","")+"'";
        String sql = "SELECT c.nombre, p.* FROM " + TBL_PRESTAMOS_IND_T + " AS p INNER JOIN " + TBL_CARTERA_IND_T + " AS c ON p.id_cliente = c.id_cartera WHERE p.id_cliente = ?";

        //Cursor row = dBhelper.customSelect(TBL_PRESTAMOS_IND_T + " AS p", "c.nombre, p.*", " INNER JOIN "+TBL_CARTERA_IND_T+" AS c ON p.id_cliente = c.id_cartera WHERE p.id_cliente = ?", "", new String[]{id_cliente});
        Cursor row = db.rawQuery(sql, new String[]{id_cliente});
        row.moveToFirst();
        ArrayList<MPrestamo> mPrestamos = new ArrayList<>();
        if (row.getCount() > 0) {
            row.moveToFirst();

            for (int i = 0; i < row.getCount(); i++) {
                MPrestamo item = new MPrestamo();
                item.setNombre(row.getString(0));               //NOMBRE
                item.setId(row.getString(2));                   //ID_PRESTAMO
                item.setDesembolso(row.getString(6));           //FECHA DESEMBOLSO
                item.setMontoPrestamo(row.getString(7));        //MONTO OTORGADO
                item.setMontoRestante(row.getString(8));        //MONTO TOTAL
                item.setMontoAmortiz(row.getString(9));         //MONTO AMORTIZACION
                item.setIdPrestamo(row.getString(4));           //NUM PRESTAMO
                item.setEstatus(row.getString(14));             //PAGADA
                item.setTipo(1);                                  //TIPO PRESTAMO
                item.setTipoPrestamo(row.getString(13));        //TIPO CARTERA
                item.setNumAmortiz(row.getString(11));          //NUM AMORTIZACION

                Cursor rowSaldoCorte = dBhelper.customSelect(TBL_AMORTIZACIONES_T + " AS a", " SUM(total - total_pagado) AS saldo_corte", " WHERE id_prestamo = ?", "", new String[]{row.getString(2)});

                if (rowSaldoCorte.getCount() > 0){
                    rowSaldoCorte.moveToFirst();
                    item.setSaldoCorte(rowSaldoCorte.getString(0));
                }
                rowSaldoCorte.close();

                Cursor rowSaldoOmega = dBhelper.customSelect(TBL_PAGOS_T + " AS p", " SUM(monto) AS totalOmega", " WHERE id_prestamo = ?", "",new String[]{row.getString(2)} );
                if (rowSaldoOmega.getCount() > 0){
                    rowSaldoOmega.moveToFirst();
                    double saldoOme = row.getDouble(8) - rowSaldoOmega.getDouble(0);
                    item.setSaldoOmega(String.valueOf(saldoOme));
                }
                mPrestamos.add(item);
                row.moveToNext();
            }
        }

        adatper = new adapter_prestamos(ctx, mPrestamos, new adapter_prestamos.Event() {
            @Override
            public void PrestamoClick(MPrestamo item) {
                Intent intent_order;
                if (item.getTipoPrestamo().equals("VENCIDA"))
                    intent_order = new Intent(ctx, VencidaIndividual.class);
                else
                    intent_order = new Intent(ctx, RecuperacionIndividual.class);
                intent_order.putExtra(ID_PRESTAMO, item.getId());
                intent_order.putExtra(MONTO_AMORTIZACION, item.getMontoAmortiz());
                startActivity(intent_order);
            }

            @Override
            public void GestionadasClick(MPrestamo item) {
                Intent intent_order = new Intent(ctx, Gestionadas.class);
                intent_order.putExtra(ID_PRESTAMO, item.getId());
                if (item.getTipoPrestamo().equals("VENCIDA")) {
                    intent_order.putExtra(TBL_NAME, TBL_RESPUESTAS_IND_V_T);
                    intent_order.putExtra(TIPO_PRESTAMO, "VENCIDA");
                    intent_order.putExtra(TIPO_GESTION, 1);
                }
                else {
                    intent_order.putExtra(TBL_NAME, TBL_RESPUESTAS_IND_T);
                    intent_order.putExtra(TIPO_PRESTAMO, "VIGENTE");
                    intent_order.putExtra(TIPO_GESTION, 1);
                }
                startActivity(intent_order);
            }

            @Override
            public void CodigoOxxoClick(MPrestamo item) {
                String sql = "SELECT p.num_prestamo, a.fecha, a.total,c.nombre, c.clave FROM "+ TBL_AMORTIZACIONES_T +" AS a INNER JOIN "+TBL_PRESTAMOS_IND_T+" AS p ON p.id_prestamo = a.id_prestamo INNER JOIN "+TBL_CARTERA_IND_T+" AS c ON p.id_cliente = c.id_cartera WHERE a.id_prestamo = ? AND a.numero = ?";
                Cursor row = db.rawQuery(sql, new String[]{item.getId(), item.getNumAmortiz()});
                if (row.getCount() > 0){
                    row.moveToFirst();

                    Intent i_codigos = new Intent(ctx, CodigosOxxo.class);
                    i_codigos.putExtra(ID_PRESTAMO, Long.parseLong(item.getId()));
                    i_codigos.putExtra(TIPO, item.getTipo());
                    i_codigos.putExtra(NUMERO_DE_PRESTAMO, row.getString(0));
                    i_codigos.putExtra(FECHA_AMORTIZACION, row.getString(1));
                    i_codigos.putExtra(MONTO_AMORTIZACION, row.getString(2));
                    i_codigos.putExtra(NOMBRE, row.getString(3));
                    i_codigos.putExtra(CLAVE, row.getString(4));
                    startActivity(i_codigos);
                }
            }
        });
        rvPrestamos.setAdapter(adatper);
    }

    private void GetPrestamosGpo (final String id_grupo){
        //String sql = "SELECT c.nombre, p.* FROM " + TBL_PRESTAMOS_GPO_T + " AS p INNER JOIN " + TBL_CARTERA_GPO_T + " AS c ON p.id_grupo = c.id_cartera WHERE p.id_grupo = ? AND pagada = 0 AND substr(p.fecha_actualizado,1,4)||substr(p.fecha_actualizado,6,2)||substr(p.fecha_actualizado,9,2) BETWEEN '"+sdf.format(fi.getTime()).replace("-","")+"' AND '"+sdf.format(ff.getTime()).replace("-","")+"'";
        String sql = "SELECT c.nombre, p.* FROM " + TBL_PRESTAMOS_GPO_T + " AS p INNER JOIN " + TBL_CARTERA_GPO_T + " AS c ON p.id_grupo = c.id_cartera WHERE p.id_grupo = ?";
        Cursor row = db.rawQuery(sql, new String[]{id_grupo});
        //Cursor row = dBhelper.customSelect(TBL_PRESTAMOS_GPO_T + " AS p", "c.nombre, p.*", " INNER JOIN "+TBL_CARTERA_GPO_T+" AS c ON p.id_grupo = c.id_cartera WHERE p.id_grupo = ?", "", new String[]{id_grupo});
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
                item.setTipoPrestamo(row.getString(13));
                item.setTipo(2);
                item.setNumAmortiz(row.getString(11));

                Cursor rowSaldoCorte = dBhelper.customSelect(TBL_AMORTIZACIONES_T + " AS a", " SUM(total - total_pagado) AS saldo_corte", " WHERE id_prestamo = ?", "", new String[]{row.getString(2)});

                if (rowSaldoCorte.getCount() > 0){
                    rowSaldoCorte.moveToFirst();
                    item.setSaldoCorte(rowSaldoCorte.getString(0));
                }
                rowSaldoCorte.close();
                item.setTipo(2);

                Cursor rowSaldoOmega = dBhelper.customSelect(TBL_PAGOS_T + " AS p", " SUM(monto) AS totalOmega", " WHERE id_prestamo = ?", "",new String[]{row.getString(2)} );
                if (rowSaldoOmega.getCount() > 0){
                    rowSaldoOmega.moveToFirst();
                    double saldoOme = row.getDouble(8) - rowSaldoOmega.getDouble(0);
                    item.setSaldoOmega(String.valueOf(saldoOme));
                }

                mPrestamos.add(item);
                row.moveToNext();
            }
        }

        adatper = new adapter_prestamos(ctx, mPrestamos, new adapter_prestamos.Event() {
            @Override
            public void PrestamoClick(MPrestamo item) {
                Intent intent_order;

                if (item.getTipoPrestamo().equals("VENCIDA"))
                    intent_order = new Intent(ctx, VencidaGrupal.class);
                else
                    intent_order = new Intent(ctx, RecuperacionGrupal.class);
                intent_order.putExtra(ID_PRESTAMO, item.getId());
                intent_order.putExtra(MONTO_AMORTIZACION, item.getMontoAmortiz());
                startActivity(intent_order);
            }

            @Override
            public void GestionadasClick(MPrestamo item) {
                Intent intent_order = new Intent(ctx, Gestionadas.class);
                intent_order.putExtra(ID_PRESTAMO, item.getId());

                if (item.getTipoPrestamo().equals("VENCIDA")){
                    intent_order.putExtra(TBL_NAME, TBL_RESPUESTAS_INTEGRANTE_T);
                    intent_order.putExtra(TIPO_PRESTAMO, "VENCIDA");
                    intent_order.putExtra(TIPO_GESTION, 2);
                }
                else {
                    intent_order.putExtra(TBL_NAME, TBL_RESPUESTAS_GPO_T);
                    intent_order.putExtra(TIPO_PRESTAMO, "VIGENTE");
                    intent_order.putExtra(TIPO_GESTION, 2);
                }
                startActivity(intent_order);
            }

            @Override
            public void CodigoOxxoClick(MPrestamo item) {

                String sql = "SELECT p.num_prestamo, a.fecha, a.total,m.nombre, m.clave FROM "+ TBL_AMORTIZACIONES_T +" AS a INNER JOIN "+TBL_PRESTAMOS_GPO_T+" AS p ON p.id_prestamo = a.id_prestamo INNER JOIN "+TBL_MIEMBROS_GPO_T+" AS m ON p.id_prestamo = m.id_prestamo WHERE a.id_prestamo = ? AND m.tipo_integrante = 'TESORERO' AND a.numero = ?";

                Cursor row = db.rawQuery(sql, new String[]{item.getId(), item.getNumAmortiz()});
                Log.e("ROWOXXO", row.getCount()+" as");
                if (row.getCount() > 0){
                    row.moveToFirst();

                    Intent i_codigos = new Intent(ctx, CodigosOxxo.class);
                    i_codigos.putExtra(ID_PRESTAMO, Long.parseLong(item.getId()));
                    i_codigos.putExtra(TIPO, item.getTipo());
                    i_codigos.putExtra(NUMERO_DE_PRESTAMO, row.getString(0));
                    i_codigos.putExtra(FECHA_AMORTIZACION, row.getString(1));
                    i_codigos.putExtra(MONTO_AMORTIZACION, row.getString(2));
                    i_codigos.putExtra(NOMBRE, row.getString(3));
                    i_codigos.putExtra(CLAVE, row.getString(4));
                    startActivity(i_codigos);
                }

                //GenerarCodigo(item, fechaAmortiz, montoAmortiz, clave, nombreTesorera);
            }
        });
        rvPrestamos.setAdapter(adatper);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_actualizar, menu);
        menu.getItem(0).setVisible(false);
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
}
