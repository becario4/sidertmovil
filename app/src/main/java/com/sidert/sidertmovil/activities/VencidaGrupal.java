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
import com.sidert.sidertmovil.fragments.view_pager.cvg_detalle_fragment;
import com.sidert.sidertmovil.fragments.view_pager.cvg_pagos_fragment;
import com.sidert.sidertmovil.fragments.view_pager.vencida_gpo_fragment;
import com.sidert.sidertmovil.utils.BottomNavigationViewHelper;
import com.sidert.sidertmovil.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.sidert.sidertmovil.utils.Constants.ID_PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.MONTO_AMORTIZACION;
import static com.sidert.sidertmovil.utils.Constants.TBL_AMORTIZACIONES;
import static com.sidert.sidertmovil.utils.Constants.TBL_AMORTIZACIONES_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_CARTERA_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_PRESTAMOS_GPO_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_RESPUESTAS_GPO_T;
import static com.sidert.sidertmovil.utils.NameFragments.DETALLE_GPO;
import static com.sidert.sidertmovil.utils.NameFragments.REPORTE_PAGOS_GPO;
import static com.sidert.sidertmovil.utils.NameFragments.VENCIDA_GPO;

public class VencidaGrupal extends AppCompatActivity {

    private Context ctx;

    private Toolbar TBmain;

    private DBhelper dBhelper;

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

    private BottomNavigationView nvMenu;

    private Menu menu;

    private SessionManager session;

    private boolean bExpedientes = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vencida_grupal);

        ctx             = this;

        session = SessionManager.getInstance(ctx);

        dBhelper        = DBhelper.getInstance(ctx);
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
        id_prestamo = data.getString(ID_PRESTAMO);
        monto_amortiz = data.getString(MONTO_AMORTIZACION);
        if(data.containsKey("expedientes")) bExpedientes = true;

        Cursor row;
        row = dBhelper.getRecords(TBL_RESPUESTAS_GPO_T, " WHERE id_prestamo = ?", " ORDER BY _id ASC", new String[]{id_prestamo});

        row.moveToLast();
        if (row.getCount() > 0){
            if (row.getInt(25) == 0){
                id_respuesta = row.getString(0);
                latitud = row.getString(2);
                longitud = row.getString(3);
            }
        }

        row = dBhelper.customSelect(TBL_PRESTAMOS_GPO_T + " AS p", "p.*, c.nombre, c.tesorera, c.clave", " INNER JOIN "+TBL_CARTERA_GPO_T  + " AS c ON p.id_grupo = c.id_cartera WHERE p.id_prestamo = ?", "", new String[]{id_prestamo});

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

        row = dBhelper.customSelect(TBL_AMORTIZACIONES_T + " AS a", " SUM(total - total_pagado) AS saldo_corte", " WHERE id_prestamo = ?", "", new String[]{id_prestamo});

        if (row.getCount() > 0){
            row.moveToFirst();
            saldo_corte = row.getDouble(0);
        }

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

            if (is_recuperacion)
                nvMenu.setSelectedItemId(R.id.nvGestion);
            else{
                menu.getItem(1).setEnabled(false);
                nvMenu.setSelectedItemId(R.id.nvDatos);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //nvMenu.setSelectedItemId(R.id.nvGestion);

        if(bExpedientes)
        {
            setTitle(getApplicationContext().getString(R.string.expedientes));
            nvMenu.findViewById(R.id.nvGestion).setVisibility(View.GONE);
            menu.getItem(1).setVisible(false);
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
                setFragment(DETALLE_GPO, null);
            } else if (itemId == R.id.nvGestion) {
                setFragment(VENCIDA_GPO, null);
            } else if (itemId == R.id.nvReporte) {
                setFragment(REPORTE_PAGOS_GPO, null);
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
            case DETALLE_GPO:
                if (!(current instanceof cvg_detalle_fragment)){
                    cvg_detalle_fragment detalle = new cvg_detalle_fragment();
                    detalle.setArguments(extras);
                    transaction.replace(R.id.flMain, detalle, DETALLE_GPO);
                    tokenFragment = DETALLE_GPO;
                } else
                    return;
                break;
            case VENCIDA_GPO:
                if (!(current instanceof vencida_gpo_fragment)){
                    vencida_gpo_fragment vencida = new vencida_gpo_fragment();
                    vencida.setArguments(extras);
                    transaction.replace(R.id.flMain, vencida, VENCIDA_GPO);
                    tokenFragment = VENCIDA_GPO;
                } else
                    return;

                break;
            case REPORTE_PAGOS_GPO:
                if (!(current instanceof cvg_pagos_fragment)){
                    cvg_pagos_fragment reporte = new cvg_pagos_fragment();
                    reporte.setArguments(extras);
                    transaction.replace(R.id.flMain, reporte, REPORTE_PAGOS_GPO);
                    tokenFragment = REPORTE_PAGOS_GPO;
                } else
                    return;
                break;
        }

        if(!tokenFragment.equals(VENCIDA_GPO) && !tokenFragment.equals(DETALLE_GPO) && !tokenFragment.equals(REPORTE_PAGOS_GPO)) {
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
