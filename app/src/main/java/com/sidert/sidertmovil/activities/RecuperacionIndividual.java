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
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.CustomViewPager;
import com.sidert.sidertmovil.utils.Miscellaneous;
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

public class RecuperacionIndividual extends AppCompatActivity {

    private Context ctx;

    private Toolbar TBmain;
    private TabLayout mTabLayout;
    private boolean canExitApp = false;
    public boolean flagRespuesta = false;
    private CustomViewPager mViewPager;
    public ModeloIndividual ficha_ri;
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
        setContentView(R.layout.activity_recuperacion_individual);
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
        external_id = data.getString(Constants.ORDER_ID);
        Cursor row = dBhelper.getRecords(Constants.FICHAS_T, " WHERE external_id = '" + external_id + "'", "", null);
        row.moveToFirst();


        if (row.getInt(23) == 2){
            flagRespuesta = false;
            invalidateOptionsMenu();
        }
        else {
            flagRespuesta = true;
            invalidateOptionsMenu();
        }

        statusFicha = row.getInt(23);
        if (!row.getString(22).isEmpty()) {
            try {
                Log.e("temporal", "obtiene respuesta");
                jsonRes = new JSONObject(row.getString(22));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (row.getString(18).isEmpty()) {

            JSONObject val = new JSONObject();
            JSONArray params = new JSONArray();
            JSONObject values = new JSONObject();
            try {
                //Toast.makeText(ctx, "Fecha_inicio"+ Miscellaneous.ObtenerFecha(), Toast.LENGTH_LONG).show();
                values.put(Constants.KEY, "fecha_ini");
                values.put(Constants.VALUE, Miscellaneous.ObtenerFecha("timestamp"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            params.put(values);

            try {
                val.put(Constants.PARAMS, params);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                values = new JSONObject();
                values.put(Constants.KEY, "external_id");
                values.put(Constants.VALUE, external_id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            params = new JSONArray();
            params.put(values);

            try {
                val.put(Constants.CONDITIONALS, params);
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

        ficha_ri = new ModeloIndividual();

        ficha_ri.setId(row.getString(2));
        ficha_ri.setType(row.getString(3));
        ficha_ri.setAssignDate(row.getString(4));
        ficha_ri.setExpirationDate("");
        ficha_ri.setCancellationDate("");

        ModeloIndividual.Cliente cliente = new ModeloIndividual.Cliente();
        try {
            JSONObject jsonCliente = new JSONObject(row.getString(10));
            cliente.setNumeroCliente(jsonCliente.getString(Constants.NUMERO_CLIENTE));
            cliente.setNombre(jsonCliente.getString(Constants.NOMBRE));
            cliente.setTelCelular(jsonCliente.getString(Constants.TEL_CELULAR));
            cliente.setTelDomicilio(jsonCliente.getString(Constants.TEL_DOMICILIO));
            ficha_ri.setCliente(cliente);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ModeloIndividual.Aval aval = new ModeloIndividual.Aval();
        try {
            JSONObject jsonAval = new JSONObject(row.getString(11));
            aval.setNombreCompletoAval(jsonAval.getString(Constants.NOMBRE_COMPLETO_AVAL));
            aval.setTelefonoAval(jsonAval.getString(Constants.TELEFONO_AVAL));
            aval.setAddressAval(jsonAval.getString(Constants.ADDRESS_AVAL));
            aval.setParentescoAval(jsonAval.getString(Constants.PARENTESCO_AVAL));
            ficha_ri.setAval(aval);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ModeloIndividual.Prestamo prestamo = new ModeloIndividual.Prestamo();
        try {
            JSONObject jsonPrestamo = new JSONObject(row.getString(7));
            prestamo.setNumeroDePrestamo(jsonPrestamo.getString(Constants.NUMERO_DE_PRESTAMO));
            prestamo.setFechaDelCreditoOtorgado(jsonPrestamo.getString(Constants.FECHA_CREDITO_OTORGADO));
            prestamo.setMontoTotalPrestamo(jsonPrestamo.getDouble(Constants.MONTO_TOTAL_PRESTAMO));
            prestamo.setMontoPrestamo(jsonPrestamo.getDouble(Constants.MONTO_PRESTAMO));
            prestamo.setPagoSemanal(jsonPrestamo.getDouble(Constants.PAGO_SEMANAL));
            prestamo.setPagoRealizado(jsonPrestamo.getDouble(Constants.PAGO_REALIZADO));
            prestamo.setNumeroAmortizacion(jsonPrestamo.getInt(Constants.NUMERO_AMORTIZACION));
            prestamo.setMontoAmortizacion(jsonPrestamo.getDouble(Constants.MONTO_AMORTIZACION));
            prestamo.setFechaPagoEstablecida(jsonPrestamo.getString(Constants.FECHA_PAGO_ESTABLECIDA));
            prestamo.setHoraPagoEstablecida(jsonPrestamo.getString(Constants.HORA_PAGO_ESTABLECIDA));
            prestamo.setSaldoActual(jsonPrestamo.getDouble(Constants.SALDO_ACTUAL));
            prestamo.setSumaDePagos(jsonPrestamo.getDouble(Constants.SUMA_DE_PAGOS));
            prestamo.setDiasAtraso(jsonPrestamo.getInt(Constants.DIAS_ATRASO));
            prestamo.setFrecuencia(jsonPrestamo.getString(Constants.FRECUENCIA));
            prestamo.setDiaSemana(jsonPrestamo.getString(Constants.DIA_SEMANA));
            prestamo.setPerteneceGarantia(jsonPrestamo.getString(Constants.PERTENECE_GARANTIA));
            prestamo.setCuentaConGarantia(jsonPrestamo.getString(Constants.CUENTA_CON_GARANTIA));
            prestamo.setTipoGarantia(jsonPrestamo.getString(Constants.TIPO_GARANTIA));
            ficha_ri.setPrestamo(prestamo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray _omega = new JSONArray(row.getString(15));
            List<ModeloIndividual.ReporteAnaliticoOmega> _reporteOmega = new ArrayList<>();
            for (int i = 0; i < _omega.length(); i++){
                JSONObject item = _omega.getJSONObject(i);
                ModeloIndividual.ReporteAnaliticoOmega rOmega = new ModeloIndividual.ReporteAnaliticoOmega();
                rOmega.setNo(item.getInt(Constants.NO));
                rOmega.setFechaAmortizacion(item.getString(Constants.FECHA_AMORTIZACION));
                rOmega.setFechaPago(item.getString(Constants.FECHA_PAGO));
                rOmega.setEstatus(item.getString(Constants.ESTATUS));
                rOmega.setDias(item.getInt(Constants.DIAS));
                _reporteOmega.add(rOmega);
            }
            ficha_ri.setReporteAnaliticoOmega(_reporteOmega);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray _pagos = new JSONArray(row.getString(16));
            List<ModeloIndividual.TablaPagosCliente> _tablaPagos = new ArrayList<>();
            for (int i = 0; i < _pagos.length(); i++){
                JSONObject item = _pagos.getJSONObject(i);
                ModeloIndividual.TablaPagosCliente tPagos = new ModeloIndividual.TablaPagosCliente();
                tPagos.setFecha(item.getString(Constants.FECHA));
                tPagos.setBanco(item.getString(Constants.BANCO));
                tPagos.setPago(item.getDouble(Constants.PAGO));
                _tablaPagos.add(tPagos);
            }
            ficha_ri.setTablaPagosCliente(_tablaPagos);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setUpViewPager();
        Intent i_res = new Intent();
        i_res.putExtra(Constants.RESPONSE,1);
        setResult(RESULT_OK, i_res);
    }

    private void setUpViewPager() {
        adapter = new TabsRecentsAdapter(getSupportFragmentManager());

        adapter.addFragment(new ri_detalle_fragment(), "");
        //adapter.addFragment(new ri_gestion_fragment(), "");
        adapter.addFragment(new recuperacion_ind_fragment(), "");
        adapter.addFragment(new ri_pagos_fragment(), "");

        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setVisibility(View.VISIBLE);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mViewPager.setSwipeable(true);
        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_single).setContentDescription("Detalle");
        //mTabLayout.getTabAt(1).setIcon(R.drawable.give_mone_white).setContentDescription("Gestión");
        mTabLayout.getTabAt(1).setIcon(R.drawable.give_mone_white).setContentDescription("Gestión");
        mTabLayout.getTabAt(2).setIcon(R.drawable.ic_payment_log).setContentDescription("Historial de Pagos");

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
                recuperacion_ind_fragment ri_data = ((recuperacion_ind_fragment) mViewPager.getAdapter().instantiateItem(mViewPager, 1));
                GuardarGestion(ri_data);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (flagRespuesta) {
            final AlertDialog confirm_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                    R.string.guardar_cambios, R.string.accept, new Popups.DialogMessage() {
                        @Override
                        public void OnClickListener(AlertDialog dialog) {
                            loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.guardando_info);
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
        recuperacion_ind_fragment ri_data = ((recuperacion_ind_fragment) mViewPager.getAdapter().instantiateItem(mViewPager, 1));
        jsonTemp = new JSONObject();
        try {
        if (ri_data.latLngGestion != null){
            jsonTemp.put(Constants.LATITUD,ri_data.latLngGestion.latitude);
            jsonTemp.put(Constants.LONGITUD, ri_data.latLngGestion.longitude);
        }

        Miscellaneous m = new Miscellaneous();

        if (!ri_data.tvContacto.getText().toString().trim().isEmpty()){
            switch (m.ContactoCliente(ri_data.tvContacto)){
                case 0:
                    jsonTemp.put(Constants.CONTACTO, ri_data.tvContacto.getText().toString());
                    if (!ri_data.tvActualizarTelefono.getText().toString().trim().isEmpty()){
                        switch (m.ActualizarTelefono(ri_data.tvActualizarTelefono)){
                            case 0:
                                jsonTemp.put(Constants.ACTUALIZAR_TELEFONO, "SI");
                                if (!ri_data.etActualizarTelefono.getText().toString().trim().isEmpty())
                                    jsonTemp.put(Constants.NUEVO_TELEFONO, ri_data.etActualizarTelefono.getText().toString().trim());
                                break;
                            case  1:
                                jsonTemp.put(Constants.ACTUALIZAR_TELEFONO, "NO");
                                break;
                        }
                    }

                    if (!ri_data.tvResultadoGestion.getText().toString().trim().isEmpty()){
                        switch (m.ResultadoGestion(ri_data.tvResultadoGestion)){
                            case 0: //Si Pago
                                jsonTemp.put(Constants.RESULTADO_PAGO, "PAGO");
                                jsonTemp.put(Constants.MEDIO_PAGO, ri_data.tvMedioPago.getText().toString());

                                if (!ri_data.tvMedioPago.getText().toString().isEmpty()){
                                    switch (m.PagoRequerido(ri_data.tvPagaraRequerido)){
                                        case 0:
                                            jsonTemp.put(Constants.PAGO_REQUERIDO, "SI");
                                            break;
                                        case 1:
                                            jsonTemp.put(Constants.PAGO_REQUERIDO, "NO");
                                            if (!ri_data.etPagoRealizado.getText().toString().trim().isEmpty())
                                                jsonTemp.put(Constants.PAGO_REALIZADO, ri_data.etPagoRealizado.getText().toString().trim());
                                            break;
                                    }

                                    switch (m.MedioPago(ri_data.tvMedioPago)){
                                        case 0: //Banamex
                                        case 1: //Banorte
                                        case 2: //Bancomer
                                        case 3: //Oxxo
                                        case 4: //Telecom
                                        case 5: //Bansefi
                                        case 7: //Sidert
                                            if (!ri_data.tvFechaDeposito.getText().toString().trim().isEmpty())
                                                jsonTemp.put(Constants.FECHA_DEPOSITO, ri_data.tvFechaDeposito.getText().toString().trim());
                                            break;
                                        case 6: //Efectivo
                                            switch (m.Impresion(ri_data.tvImprimirRecibo)){
                                                case 0:
                                                    jsonTemp.put(Constants.IMPRESORA, "SI");
                                                    if (!ri_data.etFolioRecibo.getText().toString().trim().isEmpty())
                                                        jsonTemp.put(Constants.FOLIO_TICKET, ri_data.etFolioRecibo.getText().toString().trim());
                                                    break;
                                                case 1:
                                                    jsonTemp.put(Constants.IMPRESORA, "NO CUENTA CON BATERIA");
                                                    if (!ri_data.etFolioRecibo.getText().toString().trim().isEmpty())
                                                        jsonTemp.put(Constants.FOLIO_TICKET, ri_data.etFolioRecibo.getText().toString().trim());
                                                    break;
                                            }
                                            break;

                                    }

                                    if (ri_data.byteEvidencia != null)
                                        jsonTemp.put(Constants.EVIDENCIA, Miscellaneous.save(ri_data.byteEvidencia, 2));

                                    switch (m.Gerente(ri_data.tvGerente)){
                                        case 0:
                                            jsonTemp.put(Constants.GERENTE, "SI");
                                            if (ri_data.byteFirma != null) {
                                                jsonTemp.put(Constants.FIRMA, Miscellaneous.save(ri_data.byteFirma, 3));
                                            }
                                            break;
                                        case 1:
                                            jsonTemp.put(Constants.GERENTE, "NO");
                                            break;
                                    }
                                }
                                break;
                            case  1: // No Pago
                                jsonTemp.put(Constants.RESULTADO_PAGO, "NO PAGO");

                                //jsonTemp.put(Constants.POS_MOTIVO_NO_PAGO, ri_data.spMotivoNoPago.getSelectedItemPosition());
                                jsonTemp.put(Constants.MOTIVO_NO_PAGO, ri_data.tvMotivoNoPago.getText().toString());

                                if (ri_data.tvMotivoNoPago.getText().toString().equals("FALLECIMIENTO")){
                                    if (!ri_data.tvFechaDefuncion.getText().toString().trim().isEmpty())
                                        jsonTemp.put(Constants.FECHA_DEFUNCION, ri_data.tvFechaDefuncion.getText().toString().trim());
                                }

                                if (!ri_data.etComentario.getText().toString().trim().isEmpty())
                                    jsonTemp.put(Constants.COMENTARIO, ri_data.etComentario.getText().toString().trim());

                                if (ri_data.byteEvidencia != null)
                                    jsonTemp.put(Constants.FACHADA, Miscellaneous.save(ri_data.byteEvidencia, 1));
                                break;
                        }
                    }

                    break;
                case 1:
                    jsonTemp.put(Constants.CONTACTO, "NO");
                    if (!ri_data.etComentario.getText().toString().trim().isEmpty())
                        jsonTemp.put(Constants.COMENTARIO, ri_data.etComentario.getText().toString().trim());
                    if (ri_data.byteEvidencia != null)
                        jsonTemp.put(Constants.FACHADA, Miscellaneous.save(ri_data.byteEvidencia, 1));
                    break;
                case 2:
                    jsonTemp.put(Constants.CONTACTO, "ACLARACION");
                    if (!ri_data.tvMotivoAclaracion.getText().toString().trim().isEmpty())
                        jsonTemp.put(Constants.MOTIVO_ACLARACION, ri_data.tvMotivoAclaracion.getText().toString().trim());
                    if (!ri_data.etComentario.getText().toString().trim().isEmpty())
                        jsonTemp.put(Constants.COMENTARIO, ri_data.etComentario.getText().toString().trim());
                    break;
            }

            if (!ri_data.tvGerente.getText().toString().trim().isEmpty()){
                switch (m.Gerente(ri_data.tvGerente)){
                    case 0:
                        jsonTemp.put(Constants.GERENTE, "SI");
                        if (ri_data.byteFirma != null) {
                            jsonTemp.put(Constants.FIRMA, Miscellaneous.save(ri_data.byteFirma, 3));
                        }
                        break;
                    case 1:
                        jsonTemp.put(Constants.GERENTE, "NO");
                        break;
                }
            }
        }

        ContentValues cv = new ContentValues();
        cv.put("respuesta", jsonTemp.toString());
        cv.put("status", "1");
        if (ENVIROMENT)
            db.update(FICHAS, cv, "external_id = ?", new String[]{ficha_ri.getId()});
        else
            db.update(FICHAS_T, cv, "external_id = ?", new String[]{ficha_ri.getId()});

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.v("JsonTemp", jsonTemp.toString());

        /*JSONObject val = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject values = new JSONObject();

        try {
            values.put(Constants.KEY, "respuesta");
            values.put(Constants.VALUE, jsonTemp.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        params.put(values);


        try {
            values = new JSONObject();
            values.put(Constants.KEY, "status");
            values.put(Constants.VALUE, "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.put(values);

        try {
            val.put(Constants.PARAMS, params);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            values = new JSONObject();
            values.put(Constants.KEY, "external_id");
            values.put(Constants.VALUE, ficha_ri.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params = new JSONArray();
        params.put(values);

        try {
            val.put(Constants.CONDITIONALS, params);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            int res = dBhelper.updateRecords(ctx, Constants.FICHAS_T, val);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        Toast.makeText(ctx, "Ficha Guardada Temporalmente con éxito.", Toast.LENGTH_SHORT).show();

        finish();
        loading.dismiss();
    }

    private void GuardarGestion(recuperacion_ind_fragment data){
        Validator validator = new Validator();
        ValidatorTextView validatorTV = new ValidatorTextView();
        Miscellaneous m = new Miscellaneous();
        Bundle b = new Bundle();
        if (data.latLngGestion != null){
            b.putDouble(Constants.LATITUD, data.latLngGestion.latitude);
            b.putDouble(Constants.LONGITUD, data.latLngGestion.longitude);
            if (m.ContactoCliente(data.tvContacto) == 0) { //Si Contacto cliente
                b.putString(Constants.CONTACTO, data.tvContacto.getText().toString());
                if (!data.tvActualizarTelefono.getText().toString().isEmpty()){

                    if ((m.ActualizarTelefono(data.tvActualizarTelefono) == 0 && !validator.validate(data.etActualizarTelefono, new String[]{validator.REQUIRED, validator.PHONE})) || m.ActualizarTelefono(data.tvActualizarTelefono) == 1){
                        if (m.ActualizarTelefono(data.tvActualizarTelefono) == 0){
                            b.putString(Constants.ACTUALIZAR_TELEFONO, "SI");
                            b.putString(Constants.NUEVO_TELEFONO, data.etActualizarTelefono.getText().toString().trim());
                        }else {
                            b.putString(Constants.ACTUALIZAR_TELEFONO, "NO");
                        }
                        if (m.ResultadoGestion(data.tvResultadoGestion) == 0){ // Si pago
                            b.putString(Constants.RESULTADO_PAGO, "PAGO");
                            if (m.MedioPago(data.tvMedioPago) > 0 && m.MedioPago(data.tvMedioPago) < 6 || m.MedioPago(data.tvMedioPago) == 7 ){ // Medio de pago Bancos y Oxxo
                                b.putString(Constants.MEDIO_PAGO, data.tvMedioPago.getText().toString());
                                if (!data.tvFechaDeposito.getText().toString().trim().isEmpty()){ //Fecha de deposito capturada
                                    b.putString(Constants.FECHA_DEPOSITO, data.tvFechaDeposito.getText().toString().trim());
                                    if (!data.tvPagaraRequerido.getText().toString().isEmpty()){ //Selecionó que pagará requerido o no requerido
                                        b.putString(Constants.PAGO_REQUERIDO, "SI");
                                        if (Double.parseDouble(data.etPagoRealizado.getText().toString()) > 0){ //El pago realizado es mayor a cero
                                            b.putDouble(Constants.SALDO_CORTE, ficha_ri.getPrestamo().getSaldoActual());
                                            b.putDouble(Constants.MONTO_REQUERIDO, ficha_ri.getPrestamo().getPagoSemanal());
                                            b.putString(Constants.PAGO_REALIZADO, data.etPagoRealizado.getText().toString().trim());
                                            if (data.byteEvidencia != null){ //Ha capturado una evidencia (Fotografía al ticket)
                                                if (m.Gerente(data.tvGerente) == 0) { //Selecciono que si está el gerente
                                                    if (data.byteFirma != null) { //Capturó una firma
                                                        b.putByteArray(Constants.EVIDENCIA, data.byteEvidencia);
                                                        b.putString(Constants.GERENTE, "SI");
                                                        b.putByteArray(Constants.FIRMA, data.byteFirma);
                                                        b.putBoolean(Constants.TERMINADO, true);
                                                    } else //No ha capturado la firma
                                                        Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                                                } else if (m.Gerente(data.tvGerente) == 1) { //No se encuentra el Gerente
                                                    b.putByteArray(Constants.EVIDENCIA, data.byteEvidencia);
                                                    b.putString(Constants.GERENTE, "NO");
                                                    b.putBoolean(Constants.TERMINADO, true);
                                                } else //No ha seleccionado si está el gerente
                                                    Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                                            }
                                            else //No ha capturado fotografía evidencia
                                                Toast.makeText(ctx, "No ha capturado una fotografía al ticket", Toast.LENGTH_SHORT).show();
                                        }
                                        else //El monto ingresado es igual a cero
                                            Toast.makeText(ctx, "No se pueden capturar montos iguales a cero", Toast.LENGTH_SHORT).show();
                                    }
                                    else //No ha seleccionado si pagará el pago requerido
                                        Toast.makeText(ctx, "No ha seleccionado si se pagará el requerido", Toast.LENGTH_SHORT).show();
                                }
                                else { //No ha seleccionado la fecha de depostio
                                    data.tvFechaDeposito.setError("");
                                    Toast.makeText(ctx, "No ha seleccionado la fecha de deposito", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else if (m.MedioPago(data.tvMedioPago) == 6){ //Efectivo
                                b.putString(Constants.MEDIO_PAGO, data.tvMedioPago.getText().toString());
                                if (!data.tvPagaraRequerido.getText().toString().trim().isEmpty()){ //Selecionó que pagará requerido o no requerido
                                    b.putString(Constants.PAGO_REQUERIDO, "SI");
                                    if (Double.parseDouble(data.etPagoRealizado.getText().toString()) > 0){ //El pago realizado es mayor a cero
                                        b.putDouble(Constants.SALDO_CORTE, ficha_ri.getPrestamo().getSaldoActual());
                                        b.putDouble(Constants.MONTO_REQUERIDO, ficha_ri.getPrestamo().getPagoSemanal());
                                        b.putString(Constants.PAGO_REALIZADO, data.etPagoRealizado.getText().toString().trim());
                                        if (m.Impresion(data.tvImprimirRecibo) == 0){ //Si imprimirá recibos
                                            if (!data.etFolioRecibo.getText().toString().trim().isEmpty()){
                                                b.putString(Constants.IMPRESORA, "SI");
                                                b.putString(Constants.FOLIO_TICKET, data.etFolioRecibo.getText().toString().trim());
                                                if (data.byteEvidencia != null){ //Ha capturado una evidencia (Fotografía al ticket)
                                                    b.putByteArray(Constants.EVIDENCIA, data.byteEvidencia);
                                                    if (m.Gerente(data.tvGerente) == 0) { //Selecciono que si está el gerente
                                                        if (data.byteFirma != null) { //Capturó una firma
                                                            b.putString(Constants.GERENTE, "SI");
                                                            b.putByteArray(Constants.FIRMA, data.byteFirma);
                                                            b.putBoolean(Constants.TERMINADO, true);
                                                        } else //No ha capturado la firma
                                                            Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                                                    } else if (m.Gerente(data.tvGerente) == 1) { //No se encuentra el Gerente
                                                        b.putString(Constants.GERENTE, "NO");
                                                        b.putBoolean(Constants.TERMINADO, true);
                                                    } else //No ha seleccionado si está el gerente
                                                        Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                                                }
                                                else //No ha capturado fotografía evidencia
                                                    Toast.makeText(ctx, "No ha capturado una fotografía al ticket", Toast.LENGTH_SHORT).show();
                                            }
                                            else //No ha impreso ningun ticket
                                                Toast.makeText(ctx,"No ha realizado nignuna impresión", Toast.LENGTH_SHORT).show();
                                        }
                                        else if (m.Impresion(data.tvImprimirRecibo) == 1){ //No imprimirá recibos
                                            if (!data.etFolioRecibo.getText().toString().trim().isEmpty()){
                                                b.putString(Constants.IMPRESORA, "NO CUENTA CON BATERIA");
                                                b.putString(Constants.FOLIO_TICKET, data.etFolioRecibo.getText().toString().trim());
                                                if (data.byteEvidencia != null){ //Ha capturado una evidencia (Fotografía al ticket)
                                                    b.putByteArray(Constants.EVIDENCIA, data.byteEvidencia);
                                                    if (m.Gerente(data.tvGerente) == 0) { //Selecciono que si está el gerente
                                                        if (data.byteFirma != null) { //Capturó una firma
                                                            b.putString(Constants.GERENTE, "SI");
                                                            b.putByteArray(Constants.FIRMA, data.byteFirma);
                                                            b.putBoolean(Constants.TERMINADO, true);
                                                        } else //No ha capturado la firma
                                                            Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                                                    } else if (m.Gerente(data.tvGerente) == 1) { //No se encuentra el Gerente
                                                        b.putString(Constants.GERENTE, "NO");
                                                        b.putBoolean(Constants.TERMINADO, true);
                                                    } else //No ha seleccionado si está el gerente
                                                        Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                                                }
                                                else //No ha capturado fotografía evidencia
                                                    Toast.makeText(ctx, "No ha capturado una fotografía al ticket", Toast.LENGTH_SHORT).show();
                                            }// No ha capturado el folio del recibo
                                            else
                                                Toast.makeText(ctx, "No ha capturado el folio del recibo", Toast.LENGTH_SHORT).show();
                                        }
                                        else //No ha seleccionado si imprimirá recibos
                                            Toast.makeText(ctx, "No ha confirmado si va imprimir recibos", Toast.LENGTH_SHORT).show();
                                    }
                                    else //El monto ingresado es igual a cero
                                        Toast.makeText(ctx, "No se pueden capturar montos iguales a cero", Toast.LENGTH_SHORT).show();
                                }
                                else //No ha seleccionado si pagará el pago requerido
                                    Toast.makeText(ctx, "No ha seleccionado si se pagará el requerido", Toast.LENGTH_SHORT).show();
                            }
                            else //No ha seleccionado algun medio de pago
                                Toast.makeText(ctx, "No ha seleccionado un medio de pago", Toast.LENGTH_SHORT).show();
                        }// ================ TERMINA PAGO  ==================================
                        else if (m.ResultadoGestion(data.tvResultadoGestion) == 1){ //No pago
                            b.putString(Constants.RESULTADO_PAGO, "NO PAGO");
                            if (m.MotivoNoPago(data.tvMotivoNoPago) != 1){ //Motivo de no pago Negacion u Otra
                                b.putString(Constants.MOTIVO_NO_PAGO,data.tvMotivoNoPago.getText().toString());
                                if (!data.etComentario.getText().toString().trim().isEmpty()){ //El campo comentario es diferente de vacio
                                    b.putString(Constants.COMENTARIO, data.etComentario.getText().toString());
                                    if (data.byteEvidencia != null){
                                        b.putByteArray(Constants.EVIDENCIA, data.byteEvidencia);
                                        if (m.Gerente(data.tvGerente) == 0) { //Selecciono que si está el gerente
                                            if (data.byteFirma != null) { //Capturó una firma
                                                b.putString(Constants.GERENTE, "SI");
                                                b.putByteArray(Constants.FIRMA, data.byteFirma);
                                                b.putBoolean(Constants.TERMINADO, true);
                                            } else //No ha capturado la firma
                                                Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                                        } else if (m.Gerente(data.tvGerente) == 1) { //No se encuentra el Gerente
                                            b.putString(Constants.GERENTE, "NO");
                                            b.putBoolean(Constants.TERMINADO, true);
                                        } else //No ha seleccionado si está el gerente
                                            Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                                    }
                                    else //no ha capturado la fotografía de la fachada
                                        Toast.makeText(ctx, "La Fotografía de la fachada es requerida.", Toast.LENGTH_SHORT).show();
                                }
                                else // No ha ingresado alguno comentario
                                    Toast.makeText(ctx, "El campo Comentario es requerido.", Toast.LENGTH_SHORT).show();
                            }
                            else if(data.tvMotivoNoPago.getText().equals("FALLECIMIENTO")) { //Motivo de no pago fue Fallecimiento
                                b.getString(Constants.RESULTADO_PAGO, "NO PAGO");
                                b.putString(Constants.MOTIVO_NO_PAGO,data.tvMotivoNoPago.getText().toString());
                                if (!data.tvFechaDefuncion.getText().toString().trim().isEmpty()){ //El campo Fecha es diferente de vacio
                                    b.putString(Constants.FECHA_DEFUNCION, data.tvFechaDefuncion.getText().toString());
                                    if (!data.etComentario.getText().toString().trim().isEmpty()){ // El campo Comentario es diferente de vacio
                                        b.putString(Constants.COMENTARIO, data.etComentario.getText().toString());
                                        if (data.byteEvidencia != null){ //Capturo una fotografia de fachada
                                            b.putByteArray(Constants.EVIDENCIA, data.byteEvidencia);
                                            if (m.Gerente(data.tvGerente) == 0) { //Si está el gerente
                                                if (data.byteFirma != null) { //Capturó un firma
                                                    b.putString(Constants.GERENTE, "SI");
                                                    b.putByteArray(Constants.FIRMA, data.byteFirma);
                                                    b.putBoolean(Constants.TERMINADO, true);
                                                } else //No ha Capturado un Firma
                                                    Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                                            } else if (m.Gerente(data.tvGerente) == 1) { //No está el gerente
                                                b.putString(Constants.GERENTE, "NO");
                                                b.putBoolean(Constants.TERMINADO, true);
                                            } else //No ha seleccionado si está el gerente
                                                Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                                        }
                                        else //No ha capturado una fotografia
                                            Toast.makeText(ctx, "La Fotografía de la fachada es requerida.", Toast.LENGTH_SHORT).show();
                                    }
                                    else //No ha ingresado algun comentario
                                        Toast.makeText(ctx, "El campo Comentario es requerido.", Toast.LENGTH_SHORT).show();
                                }
                                else //No ha seleccionado la fecha de defuncion
                                    Toast.makeText(ctx, "No ha seleccionado la fecha de defunción", Toast.LENGTH_SHORT).show();
                            }
                            else  //No ha seleccionado algun motivo de no pago
                                Toast.makeText(ctx, "No ha seleccionado motivo de no pago", Toast.LENGTH_SHORT).show();
                        } // ===================== TERMINA NO PAGO  =========================================
                        else //No ha seleccionado si pagó o no el cliente
                            Toast.makeText(ctx, "No ha seleccionado el resultado de la gestion", Toast.LENGTH_SHORT).show();
                    }
                    else //No ha ingresado el nuevo teléfono
                        Toast.makeText(ctx, "No ha ingresado el nuevo teléfono", Toast.LENGTH_SHORT).show();
                }
                else //No ha seleccionado si va actualizar el telefono
                    Toast.makeText(ctx, "No ha seleccionado si va actualizar el teléfono", Toast.LENGTH_SHORT).show();
            }
            else if(m.ContactoCliente(data.tvContacto) == 1) { //No contactó al cliente
                b.putString(Constants.CONTACTO, "NO");
                if (!data.etComentario.getText().toString().trim().isEmpty()) { //El campo comentario es diferente de vacio
                    b.putString(Constants.COMENTARIO, data.etComentario.getText().toString());
                    if (data.byteEvidencia != null) { //Ha capturado una fotografia de la fachada
                        b.putByteArray(Constants.EVIDENCIA, data.byteEvidencia);
                        if (m.Gerente(data.tvGerente) == 0) { // Seleccionó que está el gerente
                            if (data.byteFirma != null) { // Ha capturado un firma
                                b.putString(Constants.GERENTE, "SI");
                                b.putByteArray(Constants.FIRMA, data.byteFirma);
                                b.putBoolean(Constants.TERMINADO, true);
                            } else //No ha capturado un firma
                                Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                        } else if (m.Gerente(data.tvGerente) == 1) { //No se encuentra el gerente
                            b.putString(Constants.GERENTE, "NO");
                            b.putBoolean(Constants.TERMINADO, true);
                        } else //No ha seleccionado si está el gerente
                            Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                    } else // No ha capturado una fotografia de fachada
                        Toast.makeText(ctx, "La Fotografía de la fachada es requerida.", Toast.LENGTH_SHORT).show();
                } else //No ha ingresado algun comentario
                    Toast.makeText(ctx, "El campo Comentario es obligatorio", Toast.LENGTH_SHORT).show();
            }
            else if(m.ContactoCliente(data.tvContacto) == 2) { //Seleccionó Aclaración
                b.putString(Constants.CONTACTO, "ACLARACION");
                if (!data.tvMotivoAclaracion.getText().toString().trim().isEmpty()) { //Motivo de aclaración es diferente de vacio
                    b.putString(Constants.MOTIVO_ACLARACION, data.tvMotivoAclaracion.getText().toString());
                    if (!data.etComentario.getText().toString().trim().isEmpty()) { // Ingresó algun comentario
                        b.putString(Constants.COMENTARIO, data.etComentario.getText().toString());
                        if (m.Gerente(data.tvGerente) == 0) { //Seleccionó que está el gerente
                            if (data.byteFirma != null) { //Ha capturado una firma
                                b.putString(Constants.GERENTE, "SI");
                                b.putByteArray(Constants.FIRMA, data.byteFirma);
                                b.putBoolean(Constants.TERMINADO, true);
                            } else //No ha capturado una firma
                                Toast.makeText(ctx, "Capture la firma del gerente", Toast.LENGTH_SHORT).show();
                        } else if (m.Gerente(data.tvGerente) == 1) { //Seleccionó que no está el gerente
                            b.putString(Constants.GERENTE, "NO");
                            b.putBoolean(Constants.TERMINADO, true);
                        } else //No ha confirmado si está el gerente
                            Toast.makeText(ctx, "Confirme si el gerente está con usted", Toast.LENGTH_SHORT).show();
                    } else //No ha ingresado algun comentario
                        Toast.makeText(ctx, "El campo Comentario es obligatorio", Toast.LENGTH_SHORT).show();
                } else //No ha seleccionado el motivo de aclaración
                    Toast.makeText(ctx, "Seleccione el motivo de aclaración", Toast.LENGTH_SHORT).show();
            }
            else //No ha seleccionado si conectado al cliente o es una aclaración
                Toast.makeText(ctx, "No ha seleccionado si contactó al cliente", Toast.LENGTH_SHORT).show();
        }
        else //No ha capturado la ubicación
            Toast.makeText(ctx,"Falta obtener la ubicación de la gestión", Toast.LENGTH_SHORT).show();

        Log.v("SIDERTMOVIL", b.toString());
        if (!b.isEmpty() && b.containsKey(Constants.TERMINADO)){
            Intent i_preview = new Intent(ctx, VistaPreviaGestion.class);
            i_preview.putExtra(Constants.PARAMS,b);
            startActivityForResult(i_preview,Constants.REQUEST_CODE_PREVIEW);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Constants.REQUEST_CODE_PREVIEW:
                if (resultCode == RESULT_OK){
                    if (data != null){
                        Log.v("resultado", data.getStringExtra(Constants.RESPUESTA_GESTION));
                        JSONObject val = new JSONObject();
                        JSONArray params = new JSONArray();
                        JSONObject values = new JSONObject();

                        try {
                            values.put(Constants.KEY, SidertTables.SidertEntry.RESPUESTA);
                            values.put(Constants.VALUE, data.getStringExtra(Constants.RESPUESTA_GESTION));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        params.put(values);

                        //Toast.makeText(ctx, "Fecha_termino"+Miscellaneous.ObtenerFecha(), Toast.LENGTH_LONG).show();
                        try {
                            values = new JSONObject();
                            values.put(Constants.KEY, SidertTables.SidertEntry.FECHA_TER);
                            values.put(Constants.VALUE, Miscellaneous.ObtenerFecha("timestamp"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        params.put(values);

                        try {
                            values = new JSONObject();
                            values.put(Constants.KEY, SidertTables.SidertEntry.STATUS);
                            values.put(Constants.VALUE, "2");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        params.put(values);

                        try {
                            val.put(Constants.PARAMS, params);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            values = new JSONObject();
                            values.put(Constants.KEY, Constants.EXTERNAL_ID);
                            values.put(Constants.VALUE, ficha_ri.getId());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        params = new JSONArray();
                        params.put(values);

                        try {
                            val.put(Constants.CONDITIONALS, params);
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
