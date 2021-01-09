package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.view_pager.recuperacion_ind_fragment;
import com.sidert.sidertmovil.fragments.view_pager.ri_detalle_fragment;
import com.sidert.sidertmovil.fragments.view_pager.ri_pagos_fragment;
import com.sidert.sidertmovil.utils.BottomNavigationViewHelper;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.sidert.sidertmovil.utils.Constants.ID_PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.MONTO_AMORTIZACION;
import static com.sidert.sidertmovil.utils.Constants.RECUPERACION_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_AMORTIZACIONES;
import static com.sidert.sidertmovil.utils.Constants.TBL_AMORTIZACIONES_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_AVAL;
import static com.sidert.sidertmovil.utils.Constants.TBL_AVAL_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_TELEFONOS_CLIENTE;
import static com.sidert.sidertmovil.utils.NameFragments.DETALLE_IND;
import static com.sidert.sidertmovil.utils.NameFragments.REPORTE_PAGOS_IND;

/**Clase para visualiar detalle del prestamo grupal, Realizar gestiones o ver tabla de pagos*/
public class RecuperacionIndividual extends AppCompatActivity {

    private Context ctx;

    private Toolbar TBmain;

    private DBhelper dBhelper;
    private SQLiteDatabase db;
    /**Variables globales para ser consultadas en vistas como detalle del prestamo, recuperacion, o reporte de pagos*/
    public String id_prestamo = "";
    public String id_respuesta = "";
    public String monto_amortiz = "";
    public String nombre = "";
    public String monto_prestamo = "";
    public String monto_requerido = "";
    public String num_prestamo = "";
    public String num_cliente = "";
    public String clave_cliente = "";
    public double saldo_corte = 0;
    public String fecha_establecida = "";
    public String tipo_cartera = "";
    public String id_cartera = "";
    public String num_amortizacion = "";
    public String telCliente = "";
    public String telCelular = "";

    public String latitud = "";
    public String longitud = "";

    private BottomNavigationView nvMenu;

    private Menu menu;

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperacion_individual);
        ctx             = this;

        session = new SessionManager(ctx);

        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();
        TBmain          = findViewById(R.id.TBmain);

        nvMenu          = findViewById(R.id.nvMenu);
        BottomNavigationViewHelper.disableShiftMode(nvMenu);
        nvMenu.setOnNavigationItemSelectedListener(nvMenu_onClick);

        menu = nvMenu.getMenu();

        setSupportActionBar(TBmain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getApplicationContext().getString(R.string.order));

        /**Se obtiene los datos que se enviaron entre clases*/
        Bundle data = getIntent().getExtras();
        id_prestamo = data.getString(ID_PRESTAMO);
        monto_amortiz = data.getString(MONTO_AMORTIZACION);

        /**Se busca si hay gestiones en estado parcial*/
        Cursor row = dBhelper.getRecords(TBL_RESPUESTAS_IND_T, " WHERE id_prestamo = ?", " ORDER BY _id ASC", new String[]{id_prestamo});

        row.moveToLast();
        if (row.getCount() > 0){
            if (row.getInt(25) == 0){ /**row.getInt(25) es la columna de estatus de gestion*/
                id_respuesta = row.getString(0);/**Se obtiene el id de respuesta gestion para tenerlo como variable global*/
                latitud = row.getString(2);
                longitud = row.getString(3);
            }
        }
        row.close();

        /**Consulta para obtener los datos del detalle del prestamo*/
        row = dBhelper.customSelect(TBL_PRESTAMOS_IND_T + " AS p", "p.*, a.*, c.nombre, c.clave", " LEFT JOIN "+TBL_AVAL_T+" AS a ON p.id_prestamo = a.id_prestamo INNER JOIN "+TBL_CARTERA_IND_T + " AS c ON p.id_cliente = c.id_cartera WHERE p.id_prestamo = ?", "", new String[]{id_prestamo});

        if (row.getCount() > 0) {
            row.moveToFirst();
            id_cartera = row.getString(2);
            num_prestamo = row.getString(3);
            num_cliente = row.getString(26);
            nombre = row.getString(25);
            monto_amortiz = row.getString(8);
            monto_prestamo = row.getString(6);
            monto_requerido = row.getString(9);
            clave_cliente = row.getString(26);
            fecha_establecida = row.getString(11);
            tipo_cartera = row.getString(12);
            num_amortizacion = row.getString(10);
            //tipo_cartera = "VENCIDA";
        }
        row.close();

        /**Consulta para obtener el saldo al corte del prestamo y se guarda en variable global*/
        row = dBhelper.customSelect(TBL_AMORTIZACIONES_T + " AS a", " SUM(total - total_pagado) AS saldo_corte", " WHERE id_prestamo = ?", "", new String[]{id_prestamo});

        if (row.getCount() > 0){
            row.moveToFirst();
            saldo_corte = row.getDouble(0);
        }
        row.close();

        /**Consulta para obtener el telefono del cliente y se guarda en variable global*/
        row = dBhelper.getRecords(TBL_TELEFONOS_CLIENTE, " WHERE id_prestamo = ?", "", new String[]{id_prestamo});
        if (row.getCount() > 0){
            row.moveToFirst();
            telCliente = row.getString(2);
            telCelular = row.getString(3);
        }
        row.close();

        boolean is_recuperacion = false;

        /**Se valida los permisos que tiene el usuario para validar
         * si podrá realizar gestiones o se bloquera el menu de Recuperacion
         * para que no pueda entrar*/
        try {
            JSONArray modulos = new JSONArray(session.getUser().get(8));
            for (int i = 0; i < modulos.length(); i++){

                JSONObject item = modulos.getJSONObject(i);
                if (item.getString("nombre").trim().toLowerCase().equals("cartera")){
                    JSONArray permisos = item.getJSONArray("permisos");
                    for(int j = 0; j < permisos.length(); j++){
                        JSONObject item_permiso = permisos.getJSONObject(j);
                        if (item_permiso.getString("nombre").toLowerCase().equals("editar")){
                            is_recuperacion = true;
                        }
                    }
                }
            }

            /**Si tiene el permiso de editar_cartera se coloca la vista de recuperacion*/
            if (is_recuperacion) {
                nvMenu.setSelectedItemId(R.id.nvGestion);
            }
            else{
                /**En caso de no tener el permiso se bloquea el menu de recuperacion y se manda
                 * a la vista del detalle del prestamo*/
                menu.getItem(1).setEnabled(false);
                nvMenu.setSelectedItemId(R.id.nvDatos);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //nvMenu.setSelectedItemId(R.id.nvGestion);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:/**Selecciono el menu de retroceso del toolbar <- */
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**Escuchador del menu para cambiar de vista*/
    private BottomNavigationView.OnNavigationItemSelectedListener nvMenu_onClick = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nvDatos:
                    setFragment(DETALLE_IND, null);
                    break;
                case R.id.nvGestion:
                    setFragment(RECUPERACION_IND, null);
                    break;
                case R.id.nvReporte:
                    setFragment(REPORTE_PAGOS_IND, null);
                    break;

            }
            return true;
        }
    };

    /**Funcion para realizar las transiciones de las vistas*/
    public void setFragment(String fragment, Bundle extras) {
        Fragment current = getSupportFragmentManager().findFragmentById(R.id.flMain);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        String tokenFragment = "";

        switch (fragment) {
            case DETALLE_IND:/**Para cambiar a la vista del Detalle del Prestamo*/
                if (!(current instanceof ri_detalle_fragment)){
                    ri_detalle_fragment detalle = new ri_detalle_fragment();
                    detalle.setArguments(extras);
                    transaction.replace(R.id.flMain, detalle, DETALLE_IND);
                    tokenFragment = DETALLE_IND;
                } else
                    return;
                break;
            case RECUPERACION_IND:/**PAra cambiar a la vista de Recuperacion*/
                if (!(current instanceof recuperacion_ind_fragment)){
                    recuperacion_ind_fragment recuperacion = new recuperacion_ind_fragment();
                    recuperacion.setArguments(extras);
                    transaction.replace(R.id.flMain, recuperacion, RECUPERACION_IND);
                    tokenFragment = RECUPERACION_IND;
                } else
                    return;

                break;
            case REPORTE_PAGOS_IND:/**PAra cambiar a la vista de reporte de pagos*/
                if (!(current instanceof ri_pagos_fragment)){
                    ri_pagos_fragment reporte = new ri_pagos_fragment();
                    reporte.setArguments(extras);
                    transaction.replace(R.id.flMain, reporte, REPORTE_PAGOS_IND);
                    tokenFragment = REPORTE_PAGOS_IND;
                } else
                    return;
                break;

        }

        if(!tokenFragment.equals(RECUPERACION_IND) && !tokenFragment.equals(DETALLE_IND) && !tokenFragment.equals(REPORTE_PAGOS_IND)) {
            int count = manager.getBackStackEntryCount();
            if(count > 0) {
                int index = count - 1;
                String tag = manager.getBackStackEntryAt(index).getName();
                if(!tag.equals(tokenFragment)) {
                    transaction.addToBackStack(tokenFragment);
                }
            } else {
                transaction.addToBackStack(tokenFragment);
            }
        } else {
            cleanFragments();
        }
        transaction.commit();
    }

    public void cleanFragments() {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }


}
