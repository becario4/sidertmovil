package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/*import androidx.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;*/
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.fragments.view_pager.cvi_detalle_fragment;
import com.sidert.sidertmovil.fragments.view_pager.cvi_pagos_fragment;
import com.sidert.sidertmovil.fragments.view_pager.vencida_ind_fragment;
import com.sidert.sidertmovil.utils.BottomNavigationViewHelper;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.views.expedientes.DocumentosFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.sidert.sidertmovil.utils.Constants.ID_PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.MONTO_AMORTIZACION;
import static com.sidert.sidertmovil.utils.Constants.TBL_AMORTIZACIONES;
import static com.sidert.sidertmovil.utils.Constants.TBL_AMORTIZACIONES_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_AVAL;
import static com.sidert.sidertmovil.utils.Constants.TBL_AVAL_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_IND_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_IND_V_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_TELEFONOS_CLIENTE;
import static com.sidert.sidertmovil.utils.NameFragments.DETALLE_IND;
import static com.sidert.sidertmovil.utils.NameFragments.DOCUMENTOS_IND;
import static com.sidert.sidertmovil.utils.NameFragments.REPORTE_PAGOS_IND;
import static com.sidert.sidertmovil.utils.NameFragments.VENCIDA_IND;

public class VencidaIndividual extends AppCompatActivity {

    private Context ctx;

    private Toolbar TBmain;

    private DBhelper dBhelper;
    private SQLiteDatabase db;
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

    public String latitud = "";
    public String longitud = "";

    public String telCliente = "";
    public String telCelular = "";

    private BottomNavigationView nvMenu;

    private Menu menu;

    private SessionManager session;

    private boolean bExpedientes = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vencida_individual);

        ctx             = this;

        session = SessionManager.getInstance(ctx);

        dBhelper = DBhelper.getInstance(ctx);
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

        Bundle data = getIntent().getExtras();

        if(data.containsKey("expedientes")) bExpedientes = true;

        id_prestamo = data.getString(ID_PRESTAMO);
        monto_amortiz = data.getString(MONTO_AMORTIZACION);

        Cursor row;

        row = dBhelper.getRecords(TBL_RESPUESTAS_IND_V_T, " WHERE id_prestamo = ?", " ORDER BY _id ASC", new String[]{id_prestamo});

        row.moveToLast();
        if (row.getCount() > 0){
            if (row.getInt(26) == 0){
                id_respuesta = row.getString(0);
                latitud = row.getString(2);
                longitud = row.getString(3);
            }
        }

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
            //tipo_cartera = "VENCIDA";
        }
        row.close();

        row = dBhelper.customSelect(TBL_AMORTIZACIONES_T + " AS a", " SUM(total - total_pagado) AS saldo_corte", " WHERE id_prestamo = ?", "", new String[]{id_prestamo});

        if (row.getCount() > 0){
            row.moveToFirst();
            saldo_corte = row.getDouble(0);
        }


        /**Consulta para obtener el telefono del cliente y se guarda en variable global*/
        row = dBhelper.getRecords(TBL_TELEFONOS_CLIENTE, " WHERE id_prestamo = ?", "", new String[]{id_prestamo});
        if (row.getCount() > 0){
            row.moveToFirst();
            telCliente = row.getString(2);
            telCelular = row.getString(3);
        }
        row.close();


        boolean is_recuperacion = false;

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

            if (is_recuperacion) {
                nvMenu.setSelectedItemId(R.id.nvGestion);
            }
            else{
                menu.getItem(1).setEnabled(false);
                nvMenu.setSelectedItemId(R.id.nvDatos);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(bExpedientes)
        {
            setTitle(getApplicationContext().getString(R.string.expedientes));
            nvMenu.findViewById(R.id.nvGestion).setVisibility(View.GONE);
            nvMenu.findViewById(R.id.nvDocumentos).setVisibility(View.VISIBLE);
            menu.getItem(1).setVisible(false);
            menu.getItem(3).setVisible(true);
            nvMenu.setSelectedItemId(R.id.nvDatos);
        }
        else
        {
            nvMenu.setSelectedItemId(R.id.nvGestion);
            nvMenu.setVisibility(View.GONE);
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

    private BottomNavigationView.OnNavigationItemSelectedListener nvMenu_onClick = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == R.id.nvDatos) {
                setFragment(DETALLE_IND, null);
            } else if (itemId == R.id.nvGestion) {
                setFragment(VENCIDA_IND, null);
            } else if (itemId == R.id.nvReporte) {
                setFragment(REPORTE_PAGOS_IND, null);
            } else if (itemId == R.id.nvDocumentos) {
                Bundle data = new Bundle();
                data.putString(ID_PRESTAMO, id_prestamo);
                setFragment(DOCUMENTOS_IND, data);
            }
            return true;
        }
    };

    public void setFragment(String fragment, Bundle extras) {
        Fragment current = getSupportFragmentManager().findFragmentById(R.id.flMain);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        String tokenFragment = "";

        switch (fragment) {
            case DETALLE_IND:
                if (!(current instanceof cvi_detalle_fragment)){
                    cvi_detalle_fragment detalle = new cvi_detalle_fragment();
                    detalle.setArguments(extras);
                    transaction.replace(R.id.flMain, detalle, DETALLE_IND);
                    tokenFragment = DETALLE_IND;
                } else
                    return;
                break;
            case VENCIDA_IND:
                if (!(current instanceof vencida_ind_fragment)){
                    vencida_ind_fragment vencida = new vencida_ind_fragment();
                    vencida.setArguments(extras);
                    transaction.replace(R.id.flMain, vencida, VENCIDA_IND);
                    tokenFragment = VENCIDA_IND;
                } else
                    return;

                break;
            case NameFragments.REPORTE_PAGOS_IND:
                if (!(current instanceof cvi_pagos_fragment)){
                    cvi_pagos_fragment reporte = new cvi_pagos_fragment();
                    reporte.setArguments(extras);
                    transaction.replace(R.id.flMain, reporte, NameFragments.REPORTE_PAGOS_IND);
                    tokenFragment = NameFragments.REPORTE_PAGOS_IND;
                } else
                    return;
                break;
            case DOCUMENTOS_IND:
                if(!(current instanceof DocumentosFragment)){
                    DocumentosFragment documentos = new DocumentosFragment();
                    documentos.setArguments(extras);
                    transaction.replace(R.id.flMain, documentos, DOCUMENTOS_IND);
                    tokenFragment = DOCUMENTOS_IND;
                }
                else
                    return;
                break;
        }

        if(!tokenFragment.equals(VENCIDA_IND) && !tokenFragment.equals(DETALLE_IND) && !tokenFragment.equals(REPORTE_PAGOS_IND)) {
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
