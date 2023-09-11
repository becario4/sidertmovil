package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.database.Cursor;
/*import androidx.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;*/
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.view_pager.recuperacion_gpo_fragment;
import com.sidert.sidertmovil.fragments.view_pager.rg_detalle_fragment;
import com.sidert.sidertmovil.fragments.view_pager.rg_pagos_fragment;
import com.sidert.sidertmovil.utils.BottomNavigationViewHelper;
import com.sidert.sidertmovil.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;

import static com.sidert.sidertmovil.utils.Constants.ID_PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.MONTO_AMORTIZACION;
import static com.sidert.sidertmovil.utils.Constants.TBL_AMORTIZACIONES_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO_T;
import static com.sidert.sidertmovil.utils.NameFragments.DETALLE_GPO;
import static com.sidert.sidertmovil.utils.NameFragments.RECUPERACION_GPO;
import static com.sidert.sidertmovil.utils.NameFragments.RECUPERACION_GPO_INT;
import static com.sidert.sidertmovil.utils.NameFragments.REPORTE_PAGOS_GPO;

/**
 * Clase para visualiar detalle del prestamo grupal, Realizar gestiones o ver tabla de pagos
 */
public class RecuperacionGrupal extends AppCompatActivity {

    /**
     * Variables globales para ser consultadas en vistas como detalle del prestamo, recuperacion, o reporte de pagos
     */
    public String id_prestamo = "";
    public String id_respuesta = "";
    public String monto_amortiz = "";
    public String nombre = "";
    public String tesorera = "";
    public String monto_prestamo = "";
    public double monto_requerido = 0;
    public String num_prestamo = "";
    public String grupo_id = "";
    public String clave_grupo = "";
    public double saldo_corte = 0;
    public String fecha_establecida = "";
    public String tipo_cartera = "";
    public String latitud = "";
    public String longitud = "";
    public String telTesorero = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperacion_grupal);
        Context ctx = this;

        SessionManager session = SessionManager.getInstance(ctx);

        DBhelper dBhelper = DBhelper.getInstance(ctx);
        Toolbar TBmain = findViewById(R.id.TBmain);
        BottomNavigationView nvMenu = findViewById(R.id.nvMenu);
        BottomNavigationViewHelper.disableShiftMode(nvMenu);
        nvMenu.setOnNavigationItemSelectedListener(nvMenu_onClick);

        Menu menu = nvMenu.getMenu();
        setSupportActionBar(TBmain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getApplicationContext().getString(R.string.order));

        /**Se obtiene los datos que se enviaron entre clases*/
        Bundle data = getIntent().getExtras();
        id_prestamo = data.getString(ID_PRESTAMO);
        monto_amortiz = data.getString(MONTO_AMORTIZACION);

        /**Se busca si hay gestiones en estado parcial*/
        Cursor row = dBhelper.getRecords(TBL_RESPUESTAS_GPO_T, " WHERE id_prestamo = ?", " ORDER BY _id ASC", new String[]{id_prestamo});

        row.moveToLast();
        if (row.getCount() > 0) {
            if (row.getInt(25) == 0) {/**row.getInt(25) es la columna de estatus de gestion*/
                id_respuesta = row.getString(0); /**Se obtiene el id de respuesta gestion para tenerlo como variable global*/
                latitud = row.getString(2);
                longitud = row.getString(3);
            }
        }
        row.close();

        /**Consulta para obtener los datos del detalle del prestamo*/
        row = dBhelper.customSelect(TBL_PRESTAMOS_GPO_T + " AS p", "p.*, c.nombre, c.tesorera, c.clave", " INNER JOIN " + TBL_CARTERA_GPO_T + " AS c ON p.id_grupo = c.id_cartera WHERE p.id_prestamo = ?", "", new String[]{id_prestamo});

        if (row.getCount() > 0) {
            row.moveToFirst();
            grupo_id = row.getString(2);
            num_prestamo = row.getString(3);
            monto_amortiz = row.getString(8);
            monto_prestamo = row.getString(6);
            monto_requerido = row.getDouble(9);
            nombre = row.getString(16);
            fecha_establecida = row.getString(11);
            tesorera = row.getString(17);
            clave_grupo = row.getString(18);
            tipo_cartera = row.getString(12);
            //tipo_cartera = "VENCIDA";
        }
        row.close();

        /**Consulta para obtener el saldo al corte del prestamo y se guarda en variable global*/
        row = dBhelper.customSelect(TBL_AMORTIZACIONES_T + " AS a", " SUM(total - total_pagado) AS saldo_corte", " WHERE id_prestamo = ?", "", new String[]{id_prestamo});

        if (row.getCount() > 0) {
            row.moveToFirst();
            saldo_corte = row.getDouble(0);
        }
        row.close();

        /**Consulta para obtener el telefono de la tesorera buscando en la tabla de miembros
         * y se guarda en variable global*/
        row = dBhelper.customSelect(TBL_MIEMBROS_GPO_T, "*", " WHERE tipo_integrante = 'TESORERO' AND id_prestamo = ?", "", new String[]{id_prestamo});
        if (row.getCount() > 0) {
            row.moveToFirst();
            telTesorero = row.getString(8);
        }
        row.close();
        boolean is_recuperacion = false;

        /**Se valida los permisos que tiene el usuario para validar
         * si podrá realizar gestiones o se bloquera el menu de Recuperacion
         * para que no pueda entrar*/
        try {
            ArrayList<String> userdata = session.getUser();
            if (userdata != null) {
                String permissions = userdata.get(0);
                if (permissions != null) {
                    JSONArray modulos = new JSONArray(permissions);
                    for (int i = 0; i < modulos.length(); i++) {
                        JSONObject item = modulos.getJSONObject(i);
                        if (item.getString("nombre").trim().equalsIgnoreCase("cartera")) {
                            JSONArray permisos = item.getJSONArray("permisos");
                            for (int j = 0; j < permisos.length(); j++) {
                                JSONObject item_permiso = permisos.getJSONObject(j);
                                if (item_permiso.getString("nombre").equalsIgnoreCase("editar")) {
                                    is_recuperacion = true;
                                }
                            }
                        }
                    }
                }
            }

            /**Si tiene el permiso de editar_cartera se coloca la vista de recuperacion*/
            if (is_recuperacion)
                nvMenu.setSelectedItemId(R.id.nvGestion);
            else {
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
        if (item.getItemId() == android.R.id.home)/**Selecciono el menu de retroceso del toolbar <- */
            finish();

        return super.onOptionsItemSelected(item);
    }

    /**
     * Escuchador del menu para cambiar de vista
     */
    private BottomNavigationView.OnNavigationItemSelectedListener nvMenu_onClick = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == R.id.nvDatos) {
                setFragment(DETALLE_GPO, null);
            } else if (itemId == R.id.nvIntegrantes) {
                setFragment(RECUPERACION_GPO_INT, null);
            } else if (itemId == R.id.nvGestion) {
                setFragment(RECUPERACION_GPO, null);
            } else if (itemId == R.id.nvReporte) {
                setFragment(REPORTE_PAGOS_GPO, null);
            }
            return true;
        }
    };

    /**
     * Funcion para realizar las transiciones de las vistas
     */
    public void setFragment(String fragment, Bundle extras) {
        Fragment current = getSupportFragmentManager().findFragmentById(R.id.flMain);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        String tokenFragment = "";

        switch (fragment) {
            case DETALLE_GPO:/**Para cambiar a la vista del Detalle del Prestamo*/
                if (!(current instanceof rg_detalle_fragment)) {
                    rg_detalle_fragment detalle = new rg_detalle_fragment();
                    detalle.setArguments(extras);
                    transaction.replace(R.id.flMain, detalle, DETALLE_GPO);
                    tokenFragment = DETALLE_GPO;
                } else
                    return;
                break;
            case RECUPERACION_GPO:/**PAra cambiar a la vista de Recuperacion*/
                if (!(current instanceof recuperacion_gpo_fragment)) {
                    recuperacion_gpo_fragment recuperacion = new recuperacion_gpo_fragment();
                    recuperacion.setArguments(extras);
                    transaction.replace(R.id.flMain, recuperacion, RECUPERACION_GPO);
                    tokenFragment = RECUPERACION_GPO;
                } else
                    return;

                break;
            case REPORTE_PAGOS_GPO: /**PAra cambiar a la vista de reporte de pagos*/
                if (!(current instanceof rg_pagos_fragment)) {
                    rg_pagos_fragment reporte = new rg_pagos_fragment();
                    reporte.setArguments(extras);
                    transaction.replace(R.id.flMain, reporte, REPORTE_PAGOS_GPO);
                    tokenFragment = REPORTE_PAGOS_GPO;
                } else
                    return;
                break;
        }

        if (!tokenFragment.equals(RECUPERACION_GPO) && !tokenFragment.equals(DETALLE_GPO) && !tokenFragment.equals(REPORTE_PAGOS_GPO)) {
            int count = manager.getBackStackEntryCount();
            if (count > 0) {
                int index = count - 1;
                String tag = manager.getBackStackEntryAt(index).getName();
                if (!tag.equals(tokenFragment)) {
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
