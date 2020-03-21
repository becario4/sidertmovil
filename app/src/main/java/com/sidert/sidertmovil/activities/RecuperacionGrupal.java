package com.sidert.sidertmovil.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
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

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.adapters.TabsRecentsAdapter;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.database.SidertTables;
import com.sidert.sidertmovil.fragments.view_pager.recuperacion_gpo_fragment;
import com.sidert.sidertmovil.fragments.view_pager.rg_gestion_fragment;
import com.sidert.sidertmovil.fragments.view_pager.rg_detalle_fragment;
import com.sidert.sidertmovil.fragments.view_pager.rg_pagos_fragment;
import com.sidert.sidertmovil.fragments.view_pager.ri_gestion_fragment;
import com.sidert.sidertmovil.models.ModeloGrupal;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.CustomViewPager;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.Popups;
import com.sidert.sidertmovil.utils.Validator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.sidert.sidertmovil.utils.Constants.ACTUALIZAR_TELEFONO;
import static com.sidert.sidertmovil.utils.Constants.ADDRESS;
import static com.sidert.sidertmovil.utils.Constants.ADDRESS_SECRETARIA;
import static com.sidert.sidertmovil.utils.Constants.BANCO;
import static com.sidert.sidertmovil.utils.Constants.CALLE;
import static com.sidert.sidertmovil.utils.Constants.CIUDAD;
import static com.sidert.sidertmovil.utils.Constants.CLAVE_CLIENTE;
import static com.sidert.sidertmovil.utils.Constants.CLAVE_GRUPO;
import static com.sidert.sidertmovil.utils.Constants.CODIGO_POSTAL;
import static com.sidert.sidertmovil.utils.Constants.COLONIA;
import static com.sidert.sidertmovil.utils.Constants.COMENTARIO;
import static com.sidert.sidertmovil.utils.Constants.CONDITIONALS;
import static com.sidert.sidertmovil.utils.Constants.CONTACTO;
import static com.sidert.sidertmovil.utils.Constants.DETALLE_FICHA;
import static com.sidert.sidertmovil.utils.Constants.DIAS;
import static com.sidert.sidertmovil.utils.Constants.DIAS_ATRASO;
import static com.sidert.sidertmovil.utils.Constants.DIA_SEMANA;
import static com.sidert.sidertmovil.utils.Constants.DIRECCION;
import static com.sidert.sidertmovil.utils.Constants.ENVIROMENT;
import static com.sidert.sidertmovil.utils.Constants.ESTADO;
import static com.sidert.sidertmovil.utils.Constants.ESTATUS;
import static com.sidert.sidertmovil.utils.Constants.EVIDENCIA;
import static com.sidert.sidertmovil.utils.Constants.FACHADA;
import static com.sidert.sidertmovil.utils.Constants.FECHA;
import static com.sidert.sidertmovil.utils.Constants.FECHA_AMORTIZACION_GPO;
import static com.sidert.sidertmovil.utils.Constants.FECHA_CREDITO_OTORGADO;
import static com.sidert.sidertmovil.utils.Constants.FECHA_DEFUNCION;
import static com.sidert.sidertmovil.utils.Constants.FECHA_DEPOSITO;
import static com.sidert.sidertmovil.utils.Constants.FECHA_PAGO_ESTABLECIDA;
import static com.sidert.sidertmovil.utils.Constants.FECHA_PAGO_GPO;
import static com.sidert.sidertmovil.utils.Constants.FICHAS;
import static com.sidert.sidertmovil.utils.Constants.FICHAS_T;
import static com.sidert.sidertmovil.utils.Constants.FIRMA;
import static com.sidert.sidertmovil.utils.Constants.FOLIO_TICKET;
import static com.sidert.sidertmovil.utils.Constants.FRECUENCIA;
import static com.sidert.sidertmovil.utils.Constants.GERENTE;
import static com.sidert.sidertmovil.utils.Constants.HORA_PAGO_ESTABLECIDA;
import static com.sidert.sidertmovil.utils.Constants.IMPRESORA;
import static com.sidert.sidertmovil.utils.Constants.INTEGRANTES;
import static com.sidert.sidertmovil.utils.Constants.INTEGRANTES_GRUPO;
import static com.sidert.sidertmovil.utils.Constants.KEY;
import static com.sidert.sidertmovil.utils.Constants.LATITUD;
import static com.sidert.sidertmovil.utils.Constants.LATITUDE;
import static com.sidert.sidertmovil.utils.Constants.LONGITUD;
import static com.sidert.sidertmovil.utils.Constants.LONGITUDE;
import static com.sidert.sidertmovil.utils.Constants.MEDIO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.MONTO;
import static com.sidert.sidertmovil.utils.Constants.MONTO_AMORTIZACION;
import static com.sidert.sidertmovil.utils.Constants.MONTO_PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.MONTO_TOTAL_PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.MOTIVO_ACLARACION;
import static com.sidert.sidertmovil.utils.Constants.MOTIVO_NO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.MUNICIPIO;
import static com.sidert.sidertmovil.utils.Constants.NO;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE_GRUPO;
import static com.sidert.sidertmovil.utils.Constants.NOMBRE_SECRETARIA;
import static com.sidert.sidertmovil.utils.Constants.NUEVO_TELEFONO;
import static com.sidert.sidertmovil.utils.Constants.NUMERO_AMORTIZACION;
import static com.sidert.sidertmovil.utils.Constants.NUMERO_DE_PRESTAMO;
import static com.sidert.sidertmovil.utils.Constants.ORDER_ID;
import static com.sidert.sidertmovil.utils.Constants.PAGO;
import static com.sidert.sidertmovil.utils.Constants.PAGO_REALIZADO;
import static com.sidert.sidertmovil.utils.Constants.PAGO_SEMANAL;
import static com.sidert.sidertmovil.utils.Constants.PAGO_SEMANAL_INT;
import static com.sidert.sidertmovil.utils.Constants.PARAMS;
import static com.sidert.sidertmovil.utils.Constants.RESPONSE;
import static com.sidert.sidertmovil.utils.Constants.RESPUESTA_GESTION;
import static com.sidert.sidertmovil.utils.Constants.RESULTADO_PAGO;
import static com.sidert.sidertmovil.utils.Constants.RESUMEN_INTEGRANTES;
import static com.sidert.sidertmovil.utils.Constants.SALDO_ACTUAL;
import static com.sidert.sidertmovil.utils.Constants.SALDO_CORTE;
import static com.sidert.sidertmovil.utils.Constants.SUMA_DE_PAGOS;
import static com.sidert.sidertmovil.utils.Constants.TELEFONO_CELULAR;
import static com.sidert.sidertmovil.utils.Constants.TELEFONO_DOMICILIO;
import static com.sidert.sidertmovil.utils.Constants.TEL_CELULAR_SECRETARIA;
import static com.sidert.sidertmovil.utils.Constants.TEL_DOMICILIO_SECRETARIA;
import static com.sidert.sidertmovil.utils.Constants.TERMINADO;
import static com.sidert.sidertmovil.utils.Constants.TOTAL_INTEGRANTES;
import static com.sidert.sidertmovil.utils.Constants.VALUE;

public class RecuperacionGrupal extends AppCompatActivity {

    private Context ctx;

    private Toolbar TBmain;
    private TabLayout mTabLayout;
    private boolean canExitApp = false;
    public boolean flagRespuesta = false;
    private CustomViewPager mViewPager;
    public ModeloGrupal ficha_rg;
    private TabsRecentsAdapter adapter;
    private AlertDialog loading;
    int exit = 0;
    private DBhelper dBhelper;
    private SQLiteDatabase db;
    private String external_id = "";
    public int statusFicha = 0;
    public JSONObject jsonRes;
    private JSONObject jsonTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperacion_grupal);
        ctx             = this;
        dBhelper = new DBhelper(ctx);
        db = dBhelper.getWritableDatabase();
        TBmain          = findViewById(R.id.TBmain);
        mTabLayout      = findViewById(R.id.mTabLayout);
        mViewPager      = findViewById(R.id.mViewPager);

        setSupportActionBar(TBmain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getApplicationContext().getString(R.string.order));

        Bundle data = getIntent().getExtras();
        external_id = data.getString(ORDER_ID);
        Cursor row = dBhelper.getRecords(FICHAS_T, " WHERE external_id = '" + external_id + "'", "", null);
        row.moveToFirst();


        if (row.getInt(23) == 2){
            flagRespuesta = false;
            invalidateOptionsMenu();
        }
        else {
            flagRespuesta = true;
            invalidateOptionsMenu();
        }

        Log.v("Estatus ficha", String.valueOf(flagRespuesta));

        statusFicha = row.getInt(23);
        if (!row.getString(22).isEmpty()) {
            try {
                jsonRes = new JSONObject(row.getString(22));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.v("Respuesta", row.getString(22));
        Log.v("fecha_ini_save 1", "true "+row.getString(18));
        if (row.getString(18).isEmpty()) {
            Log.v("fecha_ini_save 2", "true "+row.getString(18));
            JSONObject val = new JSONObject();
            JSONArray params = new JSONArray();
            JSONObject values = new JSONObject();
            try {
                //Toast.makeText(ctx, "Fecha_inicio"+ Miscellaneous.ObtenerFecha(), Toast.LENGTH_LONG).show();
                values.put(KEY, "fecha_ini");
                values.put(VALUE, Miscellaneous.ObtenerFecha("timestamp"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            params.put(values);

            try {
                val.put(PARAMS, params);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                values = new JSONObject();
                values.put(KEY, "external_id");
                values.put(VALUE, external_id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            params = new JSONArray();
            params.put(values);

            try {
                val.put(CONDITIONALS, params);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                int res = dBhelper.updateRecords(ctx, Constants.FICHAS_T, val);
                Log.v("PrintResponse", res+" respuesta de BD");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {

                dBhelper.updateRecords(ctx, Constants.FICHAS_T, val);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        Log.v("Fecha", "fecha ini: " + row.getString(18)+ " fecha ter: " + row.getString(19) + " Fecha: " + row.getString(20));

        ficha_rg = new ModeloGrupal();

        ficha_rg.setId(row.getString(2));
        ficha_rg.setType(row.getString(3));
        ficha_rg.setAssignDate(row.getString(4));
        ficha_rg.setExpirationDate("");
        ficha_rg.setCancellationDate("");

        ModeloGrupal.Grupo grupo = new ModeloGrupal.Grupo();
        try {
            JSONObject jsonGrupo = new JSONObject(row.getString(10));
            grupo.setClaveGrupo(jsonGrupo.getString(CLAVE_GRUPO));
            grupo.setNombreGrupo(jsonGrupo.getString(NOMBRE_GRUPO));
            grupo.setTotalIntegrantes(jsonGrupo.getInt(TOTAL_INTEGRANTES));
            //Log.v("Integrantes", jsonGrupo.getJSONArray(Constants.INTEGRANTES_GRUPO).toString());
            JSONArray jsonIntegrantes = new JSONArray(jsonGrupo.getJSONArray(INTEGRANTES_GRUPO).toString());
            ArrayList<ModeloGrupal.IntegrantesDelGrupo> _integrantes = new ArrayList<>();
            for(int i = 0; i < jsonIntegrantes.length(); i++){
                JSONObject item = jsonIntegrantes.getJSONObject(i);
                //Log.v("Integrante", item.getString(NOMBRE));
                ModeloGrupal.IntegrantesDelGrupo integrante = new ModeloGrupal.IntegrantesDelGrupo();
                integrante.setClaveCliente(item.getString(CLAVE_CLIENTE));
                integrante.setPagoRealizado(item.getDouble(PAGO_REALIZADO));
                integrante.setMonto(item.getDouble(MONTO));
                integrante.setNombre(item.getString(NOMBRE));
                integrante.setPagoSemanalInt(item.getDouble(PAGO_SEMANAL_INT));
                _integrantes.add(integrante);
            }
            grupo.setIntegrantesDelGrupo(_integrantes);
            ficha_rg.setGrupo(grupo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonPrestamo = new JSONObject(row.getString(7));
            ModeloGrupal.Prestamo prestamo = new ModeloGrupal.Prestamo();
            prestamo.setNumeroDePrestamo(jsonPrestamo.getString(NUMERO_DE_PRESTAMO));
            prestamo.setDiasAtraso(jsonPrestamo.getInt(DIAS_ATRASO));
            prestamo.setDiaSemana(jsonPrestamo.getString(DIA_SEMANA));
            prestamo.setFechaDelCreditoOtorgado(jsonPrestamo.getString(FECHA_CREDITO_OTORGADO));
            prestamo.setFechaPagoEstablecida(jsonPrestamo.getString(FECHA_PAGO_ESTABLECIDA));
            prestamo.setFrecuencia(jsonPrestamo.getString(FRECUENCIA));
            prestamo.setHoraPagoEstablecida(jsonPrestamo.getString(HORA_PAGO_ESTABLECIDA));
            prestamo.setMontoAmortizacion(jsonPrestamo.getDouble(MONTO_AMORTIZACION));
            prestamo.setMontoPrestamo(jsonPrestamo.getDouble(MONTO_PRESTAMO));
            prestamo.setMontoTotalPrestamo(jsonPrestamo.getDouble(MONTO_TOTAL_PRESTAMO));
            prestamo.setNumeroAmortizacion(jsonPrestamo.getInt(NUMERO_AMORTIZACION));
            prestamo.setPagoRealizado(jsonPrestamo.getDouble(PAGO_REALIZADO));
            prestamo.setPagoSemanal(jsonPrestamo.getDouble(PAGO_SEMANAL));
            prestamo.setSaldoActual(jsonPrestamo.getDouble(SALDO_ACTUAL));
            prestamo.setSumaDePagos(jsonPrestamo.getDouble(SUMA_DE_PAGOS));
            ficha_rg.setPrestamo(prestamo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonPresidanta = new JSONObject(row.getString(12));
            ModeloGrupal.Presidenta presidenta = new ModeloGrupal.Presidenta();
            presidenta.setNombrePresidenta(jsonPresidanta.getString(Constants.NOMBRE_PRESIDENTA));
            presidenta.setAddressPresidenta(jsonPresidanta.getString(Constants.ADDRESS_PRESIDENTA));
            presidenta.setTelCelularPresidenta(jsonPresidanta.getString(Constants.TEL_CELULAR_PRESIDENTA));
            presidenta.setTelDomicilioPresidenta(jsonPresidanta.getString(Constants.TEL_DOMICILIO_PRESIDENTA));
            ficha_rg.setPresidenta(presidenta);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            //Log.v("tesorera", row.getString(13));
            JSONObject jsonTesorera = new JSONObject(row.getString(13));
            ModeloGrupal.Tesorera tesorera = new ModeloGrupal.Tesorera();
            tesorera.setNombre(jsonTesorera.getString(NOMBRE));
            ModeloGrupal.Direccion direccion = new ModeloGrupal.Direccion();
            direccion.setAddress(jsonTesorera.getJSONObject(DIRECCION).getString(ADDRESS));
            direccion.setEstado(jsonTesorera.getJSONObject(DIRECCION).getString(ESTADO));
            direccion.setMunicipio(jsonTesorera.getJSONObject(DIRECCION).getString(MUNICIPIO));
            direccion.setCodigoPostal(jsonTesorera.getJSONObject(DIRECCION).getString(CODIGO_POSTAL));
            direccion.setCalle(jsonTesorera.getJSONObject(DIRECCION).getString(CALLE));
            direccion.setCiudad(jsonTesorera.getJSONObject(DIRECCION).getString(CIUDAD));
            direccion.setColonia(jsonTesorera.getJSONObject(DIRECCION).getString(COLONIA));
            direccion.setLatitude(jsonTesorera.getJSONObject(DIRECCION).getString(LATITUDE));
            direccion.setLongitude(jsonTesorera.getJSONObject(DIRECCION).getString(LONGITUDE));
            tesorera.setDireccion(direccion);
            tesorera.setTelefonoCelular(jsonTesorera.getString(TELEFONO_CELULAR));
            tesorera.setTelefonoDomicilio(jsonTesorera.getString(TELEFONO_DOMICILIO));
            ficha_rg.setTesorera(tesorera);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonSecretaria = new JSONObject(row.getString(14));
            ModeloGrupal.Secretaria secretaria = new ModeloGrupal.Secretaria();
            secretaria.setNombreSecretaria(jsonSecretaria.getString(NOMBRE_SECRETARIA));
            secretaria.setAddressSercretaria(jsonSecretaria.getString(ADDRESS_SECRETARIA));
            secretaria.setTelCelularSecretaria(jsonSecretaria.getString(TEL_CELULAR_SECRETARIA));
            secretaria.setTelDomicilioSecretaria(jsonSecretaria.getString(TEL_DOMICILIO_SECRETARIA));
            ficha_rg.setSecretaria(secretaria);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            //Log.v("ReporteOmega", row.getString(15));
            JSONArray _omega = new JSONArray(row.getString(15));
            List<ModeloGrupal.ReporteAnaliticoOmega> _reporteOmega = new ArrayList<>();
            for (int i = 0; i < _omega.length(); i++){
                JSONObject item = _omega.getJSONObject(i);
                ModeloGrupal.ReporteAnaliticoOmega rOmega = new ModeloGrupal.ReporteAnaliticoOmega();
                rOmega.setNo(item.getInt(NO));
                rOmega.setFechaAmortizacion(item.getString(FECHA_AMORTIZACION_GPO));
                rOmega.setFechaPago(item.getString(FECHA_PAGO_GPO));
                rOmega.setEstatus(item.getString(ESTATUS));
                rOmega.setDias(item.getInt(DIAS));
                _reporteOmega.add(rOmega);
            }
            ficha_rg.setReporteAnaliticoOmega(_reporteOmega);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray _pagos = new JSONArray(row.getString(16));
            List<ModeloGrupal.TablaPagosGrupo> _tablaPagos = new ArrayList<>();
            for (int i = 0; i < _pagos.length(); i++){
                JSONObject item = _pagos.getJSONObject(i);
                ModeloGrupal.TablaPagosGrupo tPagos = new ModeloGrupal.TablaPagosGrupo();
                tPagos.setFecha(item.getString(FECHA));
                tPagos.setBanco(item.getString(BANCO));
                tPagos.setPago(item.getDouble(PAGO));
                _tablaPagos.add(tPagos);
            }
            ficha_rg.setTablaPagosGrupo(_tablaPagos);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setUpViewPager();

        Intent i_res = new Intent();
        i_res.putExtra(RESPONSE,1);
        setResult(RESULT_OK, i_res);
    }

    private void setUpViewPager() {
        TabsRecentsAdapter adapter = new TabsRecentsAdapter(getSupportFragmentManager());

        adapter.addFragment(new rg_detalle_fragment(), "");
        //adapter.addFragment(new rg_gestion_fragment(), "");
        adapter.addFragment(new recuperacion_gpo_fragment(), "");
        adapter.addFragment(new rg_pagos_fragment(), "");

        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setVisibility(View.VISIBLE);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mViewPager.setSwipeable(true);
        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_group).setContentDescription("Detalle");
        //mTabLayout.getTabAt(1).setIcon(R.drawable.give_mone_white).setContentDescription("Gestión");
        mTabLayout.getTabAt(1).setIcon(R.drawable.give_mone_white).setContentDescription("Gestión");
        mTabLayout.getTabAt(2).setIcon(R.drawable.ic_payment_log).setContentDescription("Historial de Pagos");

    }

    @Override
    public void onBackPressed() {
        if (flagRespuesta) {
            final AlertDialog confirm_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                    R.string.guardar_cambios, R.string.accept, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            loading = Popups.showLoadingDialog(ctx,R.string.please_wait, R.string.guardando_info);
                            loading.show();
                            GuardarTemp();
                            dialog.dismiss();

                        }
                    }, R.string.cancel, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            finish();
                            dialog.dismiss();
                        }
                    });
            Objects.requireNonNull(confirm_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
            confirm_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            confirm_dlg.setCancelable(true);
            confirm_dlg.show();
        }
        else
            finish();

    }

    private void GuardarTemp (){
        recuperacion_gpo_fragment rg_data = ((recuperacion_gpo_fragment) mViewPager.getAdapter().instantiateItem(mViewPager, 1));
        Miscellaneous m = new Miscellaneous();
        jsonTemp = new JSONObject();

        try {
            if (rg_data.latLngGestion != null){
                jsonTemp.put(LATITUD,rg_data.latLngGestion.latitude);
                jsonTemp.put(LONGITUD, rg_data.latLngGestion.longitude);
            }

            if (!rg_data.tvContacto.getText().toString().trim().isEmpty()){

                switch (m.ContactoCliente(rg_data.tvContacto)){
                    case 0: //====================================================================== Si Contacto
                        jsonTemp.put(CONTACTO, rg_data.tvContacto.getText().toString());
                        if (!rg_data.tvActualizarTelefono.getText().toString().trim().isEmpty()){
                            switch (m.ActualizarTelefono(rg_data.tvActualizarTelefono)){
                                case 0:
                                    jsonTemp.put(ACTUALIZAR_TELEFONO, "SI");
                                    if (!rg_data.etActualizarTelefono.getText().toString().trim().isEmpty())
                                        jsonTemp.put(NUEVO_TELEFONO, rg_data.etActualizarTelefono.getText().toString().trim());
                                    break;
                                case  1:
                                    jsonTemp.put(ACTUALIZAR_TELEFONO, "NO");
                                    break;
                            }
                        } //======================================================================== Cierre Actualizar Telefono

                        if (!rg_data.tvResultadoGestion.getText().toString().trim().isEmpty()){
                            switch (m.ResultadoGestion(rg_data.tvResultadoGestion)){
                                case 0: //========================================================== Si Pago
                                    jsonTemp.put(RESULTADO_PAGO, rg_data.tvResultadoGestion.getText().toString());

                                    if (!rg_data.tvMedioPago.getText().toString().trim().isEmpty()){
                                        jsonTemp.put(MEDIO_PAGO, rg_data.tvMedioPago.getText().toString());
                                        switch (m.PagoRequerido(rg_data.tvDetalleFicha)){
                                            case 0:
                                                jsonTemp.put(DETALLE_FICHA, "SI");
                                                if (!rg_data.etPagoRealizado.getText().toString().trim().isEmpty())
                                                    jsonTemp.put(INTEGRANTES, rg_data._Integrantes);
                                                    jsonTemp.put(PAGO_REALIZADO, rg_data.etPagoRealizado.getText().toString().trim());
                                                break;
                                            case 1:
                                                jsonTemp.put(DETALLE_FICHA, "NO");
                                                if (!rg_data.etPagoRealizado.getText().toString().trim().isEmpty())
                                                    jsonTemp.put(PAGO_REALIZADO, rg_data.etPagoRealizado.getText().toString().trim());
                                                break;
                                        }

                                        switch (m.MedioPago(rg_data.tvMedioPago)){
                                            case 0: //Banamex
                                            case 1: //Banorte
                                            case 2: //Bancomer
                                            case 3: //Oxxo
                                            case 4: //Telecom
                                            case 5: //Bansefi
                                            case 7: //Sidert
                                                if (!rg_data.tvFechaDeposito.getText().toString().trim().isEmpty())
                                                    jsonTemp.put(FECHA_DEPOSITO, rg_data.tvFechaDeposito.getText().toString().trim());
                                                break;
                                            case 6: //Efectivo
                                                switch (m.Impresion(rg_data.tvImprimirRecibo)){
                                                    case 0:
                                                        jsonTemp.put(IMPRESORA, rg_data.tvImprimirRecibo.getText().toString());
                                                        if (!rg_data.etFolioRecibo.getText().toString().trim().isEmpty())
                                                            jsonTemp.put(FOLIO_TICKET, rg_data.etFolioRecibo.getText().toString().trim());
                                                        break;
                                                    case 1:
                                                        jsonTemp.put(IMPRESORA, rg_data.tvImprimirRecibo.getText().toString());
                                                        if (!rg_data.etFolioRecibo.getText().toString().trim().isEmpty())
                                                            jsonTemp.put(FOLIO_TICKET, rg_data.etFolioRecibo.getText().toString().trim());
                                                        break;
                                                }
                                                break;
                                        }

                                        if (rg_data.byteEvidencia != null)
                                            jsonTemp.put(EVIDENCIA, Miscellaneous.save(rg_data.byteEvidencia, 2));
                                    }
                                    break; //======================================================= Cierre Si Pago
                                case 1: //============================================== No Pago
                                    jsonTemp.put(RESULTADO_PAGO, rg_data.tvResultadoGestion.getText().toString());

                                    jsonTemp.put(MOTIVO_NO_PAGO, rg_data.tvMotivoNoPago.getText().toString());

                                    if (rg_data.tvMotivoNoPago.getText().toString().trim().equals("FALLECIMIENTO")){
                                        if (!rg_data.tvFechaDefuncion.getText().toString().trim().isEmpty())
                                            jsonTemp.put(FECHA_DEFUNCION, rg_data.tvFechaDefuncion.getText().toString().trim());
                                    }

                                    if (!rg_data.etComentario.getText().toString().trim().isEmpty())
                                        jsonTemp.put(COMENTARIO, rg_data.etComentario.getText().toString().trim());

                                    if (rg_data.byteEvidencia != null)
                                        jsonTemp.put(FACHADA, Miscellaneous.save(rg_data.byteEvidencia, 1));
                                    break; //======================================================= Cierre No Pago
                            }
                        } //======================================================================== Cierre de Resultado Pago
                        break; //=================================================================== Cierre Si Contacto
                    case 1: //====================================================================== No Contacto
                        jsonTemp.put(CONTACTO, rg_data.tvContacto.getText().toString());
                        if (!rg_data.etComentario.getText().toString().trim().isEmpty())
                            jsonTemp.put(COMENTARIO, rg_data.etComentario.getText().toString().trim());
                        if (rg_data.byteEvidencia != null)
                            jsonTemp.put(FACHADA, Miscellaneous.save(rg_data.byteEvidencia, 1));
                        break; //=================================================================== Cierre No Contacto
                    case 2: //====================================================================== Aclaración
                        jsonTemp.put(CONTACTO, rg_data.tvContacto.getText().toString());
                        if (!rg_data.tvMotivoAclaracion.getText().toString().trim().isEmpty())
                            jsonTemp.put(MOTIVO_ACLARACION, rg_data.tvMotivoAclaracion.getText().toString().trim());
                        if (!rg_data.etComentario.getText().toString().trim().isEmpty())
                            jsonTemp.put(COMENTARIO, rg_data.etComentario.getText().toString().trim());
                        break; //=================================================================== Cierre Aclaración
                }

                if (!rg_data.tvGerente.getText().toString().trim().isEmpty()){
                    switch (m.Gerente(rg_data.tvGerente)){
                        case 0:
                            jsonTemp.put(GERENTE, "SI");
                            if (rg_data.byteFirma != null) {
                                jsonTemp.put(FIRMA, Miscellaneous.save(rg_data.byteFirma, 3));
                            }
                            break;
                        case 1:
                            jsonTemp.put(GERENTE, "NO");
                            break;
                    }
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.v("JsonTemp", jsonTemp.toString());

        ContentValues cv = new ContentValues();
        cv.put("respuesta", jsonTemp.toString());
        cv.put("status", "1");
        if (ENVIROMENT)
            db.update(FICHAS, cv, "external_id = ?", new String[]{ficha_rg.getId()});
        else
            db.update(FICHAS_T, cv, "external_id = ?", new String[]{ficha_rg.getId()});

        Toast.makeText(ctx, "Ficha Guardada Temporalmente con éxito.", Toast.LENGTH_SHORT).show();

        finish();
        loading.dismiss();
    }

    private void GuardarGestion(recuperacion_gpo_fragment data){
        Validator validator = new Validator();
        Bundle b = new Bundle();
        Miscellaneous m = new Miscellaneous();
        if (data.latLngGestion != null) {
            b.putDouble(LATITUD, data.latLngGestion.latitude);
            b.putDouble(LONGITUD, data.latLngGestion.longitude);
            if (m.ContactoCliente(data.tvContacto) == 0){ //Si Contacto cliente
                b.putString(CONTACTO, data.tvContacto.getText().toString());
                if (!data.tvActualizarTelefono.getText().toString().isEmpty()){
                    if ((m.ActualizarTelefono(data.tvActualizarTelefono) == 0 && validator.validate(data.etActualizarTelefono, new String[]{validator.REQUIRED,validator.PHONE})) || m.ActualizarTelefono(data.tvActualizarTelefono) == 1){
                        b.putString(ACTUALIZAR_TELEFONO, data.tvActualizarTelefono.getText().toString());
                        if (m.ActualizarTelefono(data.tvActualizarTelefono) == 0){
                            b.putString(NUEVO_TELEFONO, data.etActualizarTelefono.getText().toString().trim());
                        }
                        Log.e("xxxxxxxxx", String.valueOf(m.ResultadoGestion(data.tvResultadoGestion)));
                        if (m.ResultadoGestion(data.tvResultadoGestion) == 0) { // Si pago
                            b.putString(RESULTADO_PAGO, data.tvResultadoGestion.getText().toString());
                            if (m.MedioPago(data.tvMedioPago) >= 0 && m.MedioPago(data.tvMedioPago) < 6 || m.MedioPago(data.tvMedioPago) == 7) { // Medio de pago Bancos, Oxxo, SIDERT
                                b.putString(MEDIO_PAGO, data.tvMedioPago.getText().toString());
                                if (!data.tvFechaDeposito.getText().toString().trim().isEmpty()) {
                                    b.putString(FECHA_DEPOSITO, data.tvFechaDeposito.getText().toString().trim());
                                    if (!data.tvDetalleFicha.getText().toString().isEmpty()) { //Selecionó que cuenta con el detalle o no
                                        b.putString(DETALLE_FICHA, data.tvDetalleFicha.getText().toString());
                                        if (!data.etPagoRealizado.getText().toString().trim().isEmpty() && Double.parseDouble(data.etPagoRealizado.getText().toString()) > 0) { //El pago realizado es mayor a cero
                                            b.putDouble(SALDO_CORTE, ficha_rg.getPrestamo().getSaldoActual());
                                            //b.putDouble(PAGO_REQUERIDO, ficha_rg.getPrestamo().getPagoSemanal());
                                            b.putString(PAGO_REALIZADO, data.etPagoRealizado.getText().toString().trim());
                                            if (data.byteEvidencia != null) { //Ha capturado una evidencia (Fotografía al ticket)
                                                b.putByteArray(EVIDENCIA, data.byteEvidencia);
                                                if (m.Gerente(data.tvGerente) == 0) { //Selecciono que si está el gerente
                                                    b.putString(GERENTE, data.tvGerente.getText().toString());
                                                    if (data.byteFirma != null) { //Capturó una firma
                                                        b.putByteArray(FIRMA, data.byteFirma);
                                                        if ((m.PagoRequerido(data.tvDetalleFicha) == 0 && data.rbIntegrantes.isChecked()) || m.PagoRequerido(data.tvDetalleFicha) == 1) {
                                                            if (m.PagoRequerido(data.tvDetalleFicha) == 0) {
                                                                b.putBoolean(RESUMEN_INTEGRANTES, true);
                                                                b.putString(INTEGRANTES, data._Integrantes);
                                                            } else {
                                                                b.putBoolean(RESUMEN_INTEGRANTES, false);
                                                            }
                                                            b.putBoolean(TERMINADO,true);

                                                        }
                                                        else
                                                            Toast.makeText(ctx, "No ha capturado el pago de los integrantes", Toast.LENGTH_SHORT).show();
                                                    } else //No ha capturado la firma
                                                        Toast.makeText(RecuperacionGrupal.this, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                                                } else if (m.Gerente(data.tvGerente) == 1) { //No se encuentra el Gerente
                                                    b.putString(GERENTE, data.tvGerente.getText().toString());
                                                    if ((m.PagoRequerido(data.tvDetalleFicha) == 0 && data.rbIntegrantes.isChecked()) || m.PagoRequerido(data.tvDetalleFicha) == 1) {

                                                        if (m.PagoRequerido(data.tvDetalleFicha) == 0) {
                                                            b.putBoolean(RESUMEN_INTEGRANTES, true);
                                                            b.putString(INTEGRANTES, data._Integrantes);
                                                        } else {
                                                            b.putBoolean(RESUMEN_INTEGRANTES, false);
                                                        }
                                                        b.putBoolean(TERMINADO, true);
                                                    }
                                                    else
                                                        Toast.makeText(ctx, "No ha capturado el pago de los integrantes", Toast.LENGTH_SHORT).show();
                                                } else //No ha seleccionado si está el gerente
                                                    Toast.makeText(RecuperacionGrupal.this, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                                            } else //No ha capturado fotografía evidencia
                                                Toast.makeText(ctx, "No ha capturado una fotografía al ticket", Toast.LENGTH_SHORT).show();
                                        } else //El monto ingresado es igual a cero
                                            Toast.makeText(ctx, "No se pueden capturar montos iguales a cero", Toast.LENGTH_SHORT).show();
                                    } else // No ha seleccionado si tiene el detalle de la ficha
                                        Toast.makeText(RecuperacionGrupal.this, "No se seleccionado si cuenta con el detalle de la ficha", Toast.LENGTH_SHORT).show();
                                } else {
                                    data.tvFechaDeposito.setError("");
                                    Toast.makeText(ctx, "No ha ingresado la fecha de depósito", Toast.LENGTH_SHORT).show();
                                }
                            } else if (m.MedioPago(data.tvMedioPago) == 6) { //Medio de pago Efectivo
                                b.putString(MEDIO_PAGO, data.tvMedioPago.getText().toString());
                                if (!data.tvDetalleFicha.getText().toString().isEmpty()) { //Selecionó que cuenta con el detalle o no
                                    b.putString(DETALLE_FICHA, data.tvDetalleFicha.getText().toString());
                                    if (!data.etPagoRealizado.getText().toString().trim().isEmpty() && Double.parseDouble(data.etPagoRealizado.getText().toString()) > 0) { //El pago realizado es mayor a cero
                                        b.putDouble(SALDO_CORTE, ficha_rg.getPrestamo().getSaldoActual());
                                        //b.putDouble(PAGO_REQUERIDO, ficha_rg.getPrestamo().getPagoSemanal());
                                        if (m.Impresion(data.tvImprimirRecibo) == 0) { //Si imprimirá recibos
                                            b.putString(IMPRESORA, data.tvImprimirRecibo.getText().toString());
                                            if (!data.etFolioRecibo.getText().toString().trim().isEmpty()) {
                                                b.putString(FOLIO_TICKET, data.etFolioRecibo.getText().toString().trim());
                                                if (data.byteEvidencia != null) { //Ha capturado una evidencia (Fotografía al ticket)
                                                    b.putByteArray(EVIDENCIA, data.byteEvidencia);
                                                    if (m.Gerente(data.tvGerente) == 0) { //Selecciono que si está el gerente
                                                        b.putString(GERENTE, data.tvGerente.getText().toString());
                                                        if (data.byteFirma != null) { //Capturó una firma
                                                            b.putByteArray(FIRMA, data.byteFirma);
                                                            if ((m.PagoRequerido(data.tvDetalleFicha) == 0 && data.rbIntegrantes.isChecked()) || m.PagoRequerido(data.tvDetalleFicha) == 1) {
                                                                b.putString(PAGO_REALIZADO, data.etPagoRealizado.getText().toString().trim());
                                                                if (m.PagoRequerido(data.tvDetalleFicha) == 0) {
                                                                    b.putString(RESUMEN_INTEGRANTES, data.tvDetalleFicha.getText().toString());
                                                                    b.putString(INTEGRANTES, data._Integrantes);
                                                                } else {
                                                                    b.putBoolean(RESUMEN_INTEGRANTES, false);
                                                                }
                                                                b.putBoolean(TERMINADO, true);
                                                            }
                                                            else
                                                                Toast.makeText(ctx, "No ha capturado el pago de los integrantes", Toast.LENGTH_SHORT).show();
                                                        } else //No ha capturado la firma
                                                            Toast.makeText(RecuperacionGrupal.this, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                                                    } else if (m.Gerente(data.tvGerente) == 1) { //No se encuentra el Gerente
                                                        if ((m.PagoRequerido(data.tvDetalleFicha) == 0 && data.rbIntegrantes.isChecked()) || m.PagoRequerido(data.tvDetalleFicha) == 1) {
                                                            if (m.PagoRequerido(data.tvDetalleFicha) == 0) {
                                                                b.putBoolean(RESUMEN_INTEGRANTES, true);
                                                                b.putString(INTEGRANTES, data._Integrantes);
                                                            } else {
                                                                b.putBoolean(RESUMEN_INTEGRANTES, false);
                                                            }
                                                            b.putString(GERENTE, data.tvGerente.getText().toString());
                                                            b.putBoolean(TERMINADO, true);
                                                        }
                                                        else
                                                            Toast.makeText(ctx, "No ha capturado el pago de los integrantes", Toast.LENGTH_SHORT).show();
                                                    } else //No ha seleccionado si está el gerente
                                                        Toast.makeText(RecuperacionGrupal.this, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                                                } else //No ha capturado fotografía evidencia
                                                    Toast.makeText(ctx, "No ha capturado una fotografía al ticket", Toast.LENGTH_SHORT).show();
                                            } else //No ha impreso ningun ticket
                                                Toast.makeText(ctx, "No ha realizado nignuna impresión", Toast.LENGTH_SHORT).show();
                                        } else if (m.Impresion(data.tvImprimirRecibo) == 1) { //No imprimirá recibos
                                            b.putString(IMPRESORA, data.tvImprimirRecibo.getText().toString());
                                            if (!data.etFolioRecibo.getText().toString().trim().isEmpty()) {
                                                b.putString(FOLIO_TICKET, data.etFolioRecibo.getText().toString().trim());
                                                if (data.byteEvidencia != null) { //Ha capturado una evidencia (Fotografía al ticket)
                                                    b.putByteArray(EVIDENCIA, data.byteEvidencia);
                                                    if (m.Gerente(data.tvGerente) == 1) { //Selecciono que si está el gerente
                                                        b.putString(GERENTE, data.tvGerente.getText().toString());
                                                        if (data.byteFirma != null) { //Capturó una firma
                                                            if ((m.PagoRequerido(data.tvDetalleFicha) == 0 && data.rbIntegrantes.isChecked()) || m.PagoRequerido(data.tvDetalleFicha) == 0 ) {
                                                                b.putString(PAGO_REALIZADO, data.etPagoRealizado.getText().toString().trim());
                                                                b.putByteArray(EVIDENCIA, data.byteEvidencia);
                                                                b.putBoolean(TERMINADO, true);
                                                            }
                                                            else
                                                                Toast.makeText(ctx, "No ha capturado el pago de los integrantes", Toast.LENGTH_SHORT).show();
                                                        } else //No ha capturado la firma
                                                            Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                                                    } else if (m.Gerente(data.tvGerente) == 1) { //No se encuentra el Gerente
                                                        if ((m.PagoRequerido(data.tvDetalleFicha) == 0 && data.rbIntegrantes.isChecked()) || m.PagoRequerido(data.tvDetalleFicha) == 0) {
                                                            b.putString(PAGO_REALIZADO, data.etPagoRealizado.getText().toString().trim());
                                                            b.putBoolean(GERENTE, false);
                                                            b.putBoolean(TERMINADO, true);
                                                        }
                                                        else
                                                            Toast.makeText(ctx, "No ha capturado el pago de los integrantes", Toast.LENGTH_SHORT).show();
                                                    } else //No ha seleccionado si está el gerente
                                                        Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                                                } else //No ha capturado fotografía evidencia
                                                    Toast.makeText(ctx, "No ha capturado una fotografía al ticket", Toast.LENGTH_SHORT).show();
                                            } else
                                                Toast.makeText(ctx, "No ha capturado el folio del recibo", Toast.LENGTH_SHORT).show();
                                        } else //No ha seleccionado si imprimirá recibos
                                            Toast.makeText(ctx, "No ha confirmado si va imprimir recibos", Toast.LENGTH_SHORT).show();
                                    } else //El monto ingresado es igual a cero
                                        Toast.makeText(ctx, "No se pueden capturar montos iguales a cero", Toast.LENGTH_SHORT).show();
                                } else // No ha seleccionado si tiene el detalle de la ficha
                                    Toast.makeText(ctx, "No se seleccionado si cuenta con el detalle de la ficha", Toast.LENGTH_SHORT).show();
                            } else //No ha seleccionado algun medio de pago
                                Toast.makeText(ctx, "No ha seleccionado un medio de pago", Toast.LENGTH_SHORT).show();
                        } // =================  Termina Si Pago  ==============================================
                        else if (m.ResultadoGestion(data.tvResultadoGestion) == 1) { //No pago
                            b.putString(RESULTADO_PAGO, data.tvResultadoGestion.getText().toString());
                            if (m.MotivoNoPago(data.tvMotivoNoPago) == 0 || m.MotivoNoPago(data.tvMotivoNoPago) == 2) { //Motivo de no pago Negacion u Otra
                                b.putString(MOTIVO_NO_PAGO, data.tvMotivoNoPago.getText().toString());
                                if (!data.etComentario.getText().toString().trim().isEmpty()) { //El campo comentario es diferente de vacio
                                    b.putString(COMENTARIO, data.etComentario.getText().toString());
                                    if (data.byteEvidencia != null) {
                                        b.putByteArray(EVIDENCIA, data.byteEvidencia);
                                        if (m.Gerente(data.tvGerente) == 0) { //Selecciono que si está el gerente
                                            b.putString(GERENTE, data.tvGerente.getText().toString());
                                            if (data.byteFirma != null) { //Capturó una firma
                                                b.putByteArray(FIRMA, data.byteFirma);
                                                b.putBoolean(TERMINADO, true);
                                            } else //No ha capturado la firma
                                                Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                                        } else if (m.Gerente(data.tvGerente) == 1) { //No se encuentra el Gerente
                                            b.putString(GERENTE, data.tvGerente.getText().toString());
                                            b.putBoolean(TERMINADO, true);
                                        } else //No ha seleccionado si está el gerente
                                            Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                                    } else //no ha capturado la fotografía de la fachada
                                        Toast.makeText(ctx, "La Fotografía de la fachada es requerida.", Toast.LENGTH_SHORT).show();
                                } else // No ha ingresado alguno comentario
                                    Toast.makeText(ctx, "El campo Comentario es requerido.", Toast.LENGTH_SHORT).show();
                            } else if (m.MotivoNoPago(data.tvMotivoNoPago) == 1) { //Motivo de no pago fue Fallecimiento
                                b.putString(MOTIVO_NO_PAGO, data.tvMotivoNoPago.getText().toString());
                                if (!data.tvFechaDefuncion.getText().toString().trim().isEmpty()) { //El campo Fecha es diferente de vacio
                                    b.putString(FECHA_DEFUNCION, data.tvFechaDefuncion.getText().toString());
                                    if (!data.etComentario.getText().toString().trim().isEmpty()) { // El campo Comentario es diferente de vacio
                                        b.putString(COMENTARIO, data.etComentario.getText().toString());
                                        if (data.byteEvidencia != null) { //Capturo una fotografia de fachada
                                            b.putByteArray(EVIDENCIA, data.byteEvidencia);
                                            if (m.Gerente(data.tvGerente) == 0) { //Si está el gerente
                                                b.putString(GERENTE, data.tvGerente.getText().toString());
                                                if (data.byteFirma != null) { //Capturó un firma
                                                    b.putByteArray(FIRMA, data.byteFirma);
                                                    b.putBoolean(TERMINADO, true);
                                                } else //No ha Capturado un Firma
                                                    Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                                            } else if (m.Gerente(data.tvGerente) == 1) { //No está el gerente
                                                b.putString(GERENTE, data.tvGerente.getText().toString());
                                                b.putBoolean(TERMINADO, true);
                                            } else //No ha seleccionado si está el gerente
                                                Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                                        } else //No ha capturado una fotografia
                                            Toast.makeText(ctx, "La Fotografía de la fachada es requerida.", Toast.LENGTH_SHORT).show();
                                    } else //No ha ingresado algun comentario
                                        Toast.makeText(ctx, "El campo Comentario es requerido.", Toast.LENGTH_SHORT).show();
                                } else //No ha seleccionado la fecha de defuncion
                                    Toast.makeText(ctx, "No ha seleccionado la fecha de defunción", Toast.LENGTH_SHORT).show();
                            } else  //No ha seleccionado algun motivo de no pago
                                Toast.makeText(ctx, "No ha seleccionado motivo de no pago", Toast.LENGTH_SHORT).show();
                        } // =================  Termina No Pago  ==============================================
                        else //No ha seleccionado si pagó o no el cliente
                            Toast.makeText(ctx, "No ha seleccionado el resultado de la gestión", Toast.LENGTH_SHORT).show();
                    }
                    else //No ha ingresado el nuevo teléfono
                        Toast.makeText(ctx, "No ha ingresado el nuevo teléfono", Toast.LENGTH_SHORT).show();
                }
                else //No ha seleccionado si va actualizar el telefono
                    Toast.makeText(ctx, "No ha seleccionado si va actualizar el teléfono", Toast.LENGTH_SHORT).show();
            }// ============  Termina Si Contacto  =============================
            else if(m.ContactoCliente(data.tvContacto) == 1) { //No contactó al cliente
                b.putInt(CONTACTO, 0);
                if (!data.etComentario.getText().toString().trim().isEmpty()) { //El campo comentario es diferente de vacio
                    b.putString(COMENTARIO, data.etComentario.getText().toString());
                    if (data.byteEvidencia != null) { //Ha capturado una fotografia de la fachada
                        b.putByteArray(EVIDENCIA, data.byteEvidencia);
                        if (m.Gerente(data.tvGerente) == 1) { // Seleccionó que está el gerente
                            if (data.byteFirma != null) { // Ha capturado un firma
                                b.putString(GERENTE, data.tvGerente.getText().toString());
                                b.putByteArray(FIRMA, data.byteFirma);
                                b.putBoolean(TERMINADO, true);
                            } else //No ha capturado un firma
                                Toast.makeText(RecuperacionGrupal.this, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                        } else if (m.Gerente(data.tvGerente) == 1) { //No se encuentra el gerente
                            b.putString(GERENTE, data.tvGerente.getText().toString());
                            b.putBoolean(TERMINADO, true);
                        } else //No ha seleccionado si está el gerente
                            Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                    } else // No ha capturado una fotografia de fachada
                        Toast.makeText(ctx, "La Fotografía de la fachada es requerida.", Toast.LENGTH_SHORT).show();
                } else //No ha ingresado algun comentario
                    Toast.makeText(ctx, "El campo Comentario es obligatorio", Toast.LENGTH_SHORT).show();
            } //============  Termina No Contacto  =============================
            else if(m.ContactoCliente(data.tvContacto) == 2) { //Seleccionó Aclaración
                b.putInt(CONTACTO, 2);
                if (!data.tvMotivoAclaracion.getText().toString().trim().isEmpty()) { //Motivo de aclaración es diferente de vacio
                    b.putString(MOTIVO_ACLARACION, data.tvMotivoAclaracion.getText().toString());
                    if (!data.etComentario.getText().toString().trim().isEmpty()) { // Ingresó algun comentario
                        b.putString(COMENTARIO, data.etComentario.getText().toString());
                        if (m.Gerente(data.tvGerente) == 0) { //Seleccionó que está el gerente
                            if (data.byteFirma != null) { //Ha capturado una firma
                                b.putString(GERENTE, data.tvGerente.getText().toString());
                                b.putByteArray(FIRMA, data.byteFirma);
                                b.putBoolean(TERMINADO, true);
                            }
                            else //No ha capturado una firma
                                Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                        }
                        else if (m.Gerente(data.tvGerente) == 1) { //Seleccionó que no está el gerente
                            b.putString(GERENTE, data.tvGerente.getText().toString());
                            b.putBoolean(TERMINADO, true);
                        }
                        else //No ha confirmado si está el gerente
                            Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                    }
                    else //No ha ingresado algun comentario
                        Toast.makeText(ctx, "El campo Comentario es obligatorio", Toast.LENGTH_SHORT).show();
                }
                else //No ha seleccionado el motivo de aclaración
                    Toast.makeText(ctx, "Seleccione el motivo de aclaración", Toast.LENGTH_SHORT).show();
            } //============  Termina Aclaración  ==============================
            else //No ha seleccionadosi conectado al cliente o es una aclaración
                Toast.makeText(ctx, "No ha seleccionado si contacto al cliente", Toast.LENGTH_SHORT).show();
        }
        else //No ha capturado la ubicación
            Toast.makeText(ctx,"Falta obtener la ubicación de la gestión", Toast.LENGTH_SHORT).show();

        if (!b.isEmpty() && b.containsKey(TERMINADO)){
            Intent i_preview = new Intent(RecuperacionGrupal.this,VistaPreviaGestion.class);
            i_preview.putExtra(PARAMS,b);
            startActivityForResult(i_preview,Constants.REQUEST_CODE_PREVIEW);
        }
        else{
            //Toast.makeText(ctx, "No contiene el parámetro TERMINADO", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save, menu);
        if (!flagRespuesta)
        {
            for (int i = 0; i < menu.size(); i++)
                menu.getItem(i).setVisible(flagRespuesta);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                final AlertDialog firma_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                        R.string.guardar_cambios, R.string.accept, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                loading = Popups.showLoadingDialog(ctx,R.string.please_wait, R.string.guardando_info);
                                loading.show();
                                GuardarTemp();
                                dialog.dismiss();

                            }
                        }, R.string.cancel, new Popups.DialogMessage() {
                            @Override
                            public void OnClickListener(AlertDialog dialog) {
                                finish();
                                dialog.dismiss();
                            }
                        });
                Objects.requireNonNull(firma_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                firma_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                firma_dlg.show();
                break;
            case R.id.save:
                recuperacion_gpo_fragment rg_data = ((recuperacion_gpo_fragment) mViewPager.getAdapter().instantiateItem(mViewPager, 1));
                GuardarGestion(rg_data);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Constants.REQUEST_CODE_PREVIEW:
                if (resultCode == RESULT_OK){
                    if (data != null){
                        Log.v("resultado", data.getStringExtra(RESPUESTA_GESTION));
                        JSONObject val = new JSONObject();
                        JSONArray params = new JSONArray();
                        JSONObject values = new JSONObject();

                        try {
                            values.put(KEY, SidertTables.SidertEntry.RESPUESTA);
                            values.put(VALUE, data.getStringExtra(RESPUESTA_GESTION));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        params.put(values);

                        //Toast.makeText(ctx, "Fecha_termino"+Miscellaneous.ObtenerFecha(), Toast.LENGTH_LONG).show();
                        try {
                            values = new JSONObject();
                            values.put(KEY, SidertTables.SidertEntry.FECHA_TER);
                            values.put(VALUE, Miscellaneous.ObtenerFecha("timestamp"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        params.put(values);

                        try {
                            values = new JSONObject();
                            values.put(KEY, SidertTables.SidertEntry.STATUS);
                            values.put(VALUE, "2");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        params.put(values);

                        try {
                            val.put(PARAMS, params);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            values = new JSONObject();
                            values.put(KEY, Constants.EXTERNAL_ID);
                            values.put(VALUE, ficha_rg.getId());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        params = new JSONArray();
                        params.put(values);

                        try {
                            val.put(CONDITIONALS, params);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            dBhelper.updateRecords(ctx, Constants.FICHAS_T, val);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(ctx, "Ficha Guardada con éxito.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                break;
        }
    }
}
