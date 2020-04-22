package com.sidert.sidertmovil.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.TabsRecentsAdapter;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.database.SidertTables;
import com.sidert.sidertmovil.fragments.view_pager.recuperacion_ind_fragment;
import com.sidert.sidertmovil.fragments.view_pager.ri_gestion_fragment;
import com.sidert.sidertmovil.fragments.view_pager.ri_detalle_fragment;
import com.sidert.sidertmovil.fragments.view_pager.ri_pagos_fragment;
import com.sidert.sidertmovil.models.ModeloIndividual;
import com.sidert.sidertmovil.utils.BottomNavigationViewHelper;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.CustomViewPager;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Validator;
import com.sidert.sidertmovil.utils.ValidatorTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;
import static com.sidert.sidertmovil.utils.Constants.FICHAS;
import static com.sidert.sidertmovil.utils.Constants.FICHAS_T;
import static com.sidert.sidertmovil.utils.Constants.ID_PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.LATITUD;
import static com.sidert.sidertmovil.utils.Constants.LONGITUD;
import static com.sidert.sidertmovil.utils.Constants.MONTO_AMORTIZACION;
import static com.sidert.sidertmovil.utils.Constants.RESPUESTA_GESTION;
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

public class RecuperacionIndividual extends AppCompatActivity {

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

    private BottomNavigationView nvMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperacion_individual);
        ctx             = this;
        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();
        TBmain          = findViewById(R.id.TBmain);

        nvMenu          = findViewById(R.id.nvMenu);
        BottomNavigationViewHelper.disableShiftMode(nvMenu);
        nvMenu.setOnNavigationItemSelectedListener(nvMenu_onClick);

        setSupportActionBar(TBmain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getApplicationContext().getString(R.string.order));

        Bundle data = getIntent().getExtras();
        id_prestamo = data.getString(ID_PRESTAMO);
        monto_amortiz = data.getString(MONTO_AMORTIZACION);


        Cursor row;
        if (ENVIROMENT)
            row = dBhelper.getRecords(TBL_RESPUESTAS_IND, " WHERE id_prestamo = ?", " ORDER BY _id ASC", new String[]{id_prestamo});
        else
            row = dBhelper.getRecords(TBL_RESPUESTAS_IND_T, " WHERE id_prestamo = ?", " ORDER BY _id ASC", new String[]{id_prestamo});

        row.moveToLast();
        if (row.getCount() > 0){
            if (row.getInt(25) == 0){
                id_respuesta = row.getString(0);
            }
        }

        if (ENVIROMENT)
            row = dBhelper.customSelect(TBL_PRESTAMOS_IND + " AS p", "p.*, a.*, c.nombre, c.clave", " INNER JOIN "+TBL_AVAL+" AS a ON p.id_prestamo = a.id_prestamo INNER JOIN "+TBL_CARTERA_IND + " AS c ON p.id_cliente = c.id_cartera WHERE p.id_prestamo = ?", "", new String[]{id_prestamo});
        else
            row = dBhelper.customSelect(TBL_PRESTAMOS_IND_T + " AS p", "p.*, a.*, c.nombre, c.clave", " INNER JOIN "+TBL_AVAL_T+" AS a ON p.id_prestamo = a.id_prestamo INNER JOIN "+TBL_CARTERA_IND_T + " AS c ON p.id_cliente = c.id_cartera WHERE p.id_prestamo = ?", "", new String[]{id_prestamo});

        if (row.getCount() > 0) {
            row.moveToFirst();
            num_prestamo = row.getString(3);
            num_cliente = row.getString(2);
            nombre = row.getString(25);
            monto_amortiz = row.getString(8);
            monto_prestamo = row.getString(6);
            monto_requerido = row.getString(9);
            clave_cliente = row.getString(26);
            fecha_establecida = row.getString(11);
        }
        row.close();

        if (ENVIROMENT)
            row = dBhelper.customSelect(TBL_AMORTIZACIONES + " AS a", " SUM(total - total_pagado) AS saldo_corte", " WHERE id_prestamo = ?", "", new String[]{id_prestamo});
        else
            row = dBhelper.customSelect(TBL_AMORTIZACIONES_T + " AS a", " SUM(total - total_pagado) AS saldo_corte", " WHERE id_prestamo = ?", "", new String[]{id_prestamo});

        if (row.getCount() > 0){
            row.moveToFirst();
            saldo_corte = row.getDouble(0);
        }
        nvMenu.setSelectedItemId(R.id.nvGestion);
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
            switch (item.getItemId()) {
                case R.id.nvDatos:
                    setFragment(NameFragments.DETALLE_IND, null);
                    break;
                case R.id.nvGestion:
                    setFragment(NameFragments.RECUPERACION_IND, null);
                    break;
                case R.id.nvReporte:
                    setFragment(NameFragments.REPORTE_PAGOS_IND, null);
                    break;

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
            case NameFragments.DETALLE_IND:
                if (!(current instanceof ri_detalle_fragment)){
                    ri_detalle_fragment detalle = new ri_detalle_fragment();
                    detalle.setArguments(extras);
                    transaction.replace(R.id.flMain, detalle, NameFragments.DETALLE_IND);
                    tokenFragment = NameFragments.DETALLE_IND;
                } else
                    return;
                break;
            case NameFragments.RECUPERACION_IND:
                if (!(current instanceof recuperacion_ind_fragment)){
                    recuperacion_ind_fragment recuperacion = new recuperacion_ind_fragment();
                    recuperacion.setArguments(extras);
                    transaction.replace(R.id.flMain, recuperacion, NameFragments.RECUPERACION_IND);
                    tokenFragment = NameFragments.RECUPERACION_IND;
                } else
                    return;

                break;
            case NameFragments.REPORTE_PAGOS_IND:
                if (!(current instanceof ri_pagos_fragment)){
                    ri_pagos_fragment reporte = new ri_pagos_fragment();
                    reporte.setArguments(extras);
                    transaction.replace(R.id.flMain, reporte, NameFragments.REPORTE_PAGOS_IND);
                    tokenFragment = NameFragments.REPORTE_PAGOS_IND;
                } else
                    return;
                break;

        }

        if(!tokenFragment.equals(NameFragments.RECUPERACION_IND)) {
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
